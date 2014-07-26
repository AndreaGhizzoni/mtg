package com.hackcaffebabe.mtg.gui.panel.deckeditor;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.jx.tree.JXTree;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.controller.Paths;
import com.hackcaffebabe.mtg.gui.FramesDimensions;
import com.hackcaffebabe.mtg.gui.components.deckeditor.DeckTreeNode;
import com.hackcaffebabe.mtg.gui.components.deckeditor.DeckTreeNode.TREENODE_TYPE;


/**
 * Deck editor frame content.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.5
 */
public class DeckEditorContent extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JXTree treeSavedDeck;
	private JTabbedPane tabDeckOpened = new JTabbedPane( JTabbedPane.TOP );

	/**
	 * Initialize the content of deck editor.
	 */
	public DeckEditorContent(){
		super();
		setSize( FramesDimensions.DIMENSION_DECK_EDITOR );
		setLayout( new MigLayout( "", "[grow][120:120:120,grow][120:120:120]", "[grow]" ) );
		this.initContent();
		this.refreshSavedDeck();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize the content */
	private void initContent(){
		add( tabDeckOpened, "cell 0 0,grow" );

		JPanel pnlSavedDeck = new JPanel();
		pnlSavedDeck.setBorder( new TitledBorder( "Saved Deck" ) );
		pnlSavedDeck.setLayout( new MigLayout( "", "[grow]", "[grow]" ) );

		this.treeSavedDeck = new JXTree( new DefaultTreeModel( new DefaultMutableTreeNode( "Decks:" ) ) );
		this.treeSavedDeck.setBackground( UIManager.getColor( "windowBorder" ) );
		((DefaultTreeCellRenderer) this.treeSavedDeck.getCellRenderer()).setBackgroundNonSelectionColor( UIManager
				.getColor( "windowBorder" ) );//TODO switch to JXTreeRender when finish
		this.treeSavedDeck.getSelectionModel().setSelectionMode( TreeSelectionModel.SINGLE_TREE_SELECTION );
		this.treeSavedDeck.addMouseListener( new DeckClickListener() );//TODO finish it
		JScrollPane scrollPane = new JScrollPane( this.treeSavedDeck );
		scrollPane.setBorder( null );

		pnlSavedDeck.add( scrollPane, "grow" );
		add( pnlSavedDeck, "cell 1 0 2 1,grow" );
	}

	/**
	 * Refresh and read all the saved deck into.
	 */
	public void refreshSavedDeck(){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				DeckTreeNode decks = new DeckTreeNode( "Decks", TREENODE_TYPE.ROOT );
				List<File> files = Arrays.asList( new File( Paths.DECK_PATH ).listFiles() );
				Collections.sort( files );

//				//TEST
//				DeckTreeNode g1 = new DeckTreeNode( "Group 1", TREENODE_TYPE.GROUP );
//				DeckTreeNode g2 = new DeckTreeNode( "Group 2", TREENODE_TYPE.GROUP );
//				for(File f: files)
//					g1.add( new DeckTreeNode( f.getName().split( "[.]" )[0], TREENODE_TYPE.DECK ) );// exclude the extension *.mtgdeck
//				decks.add( g1 );
//				decks.add( g2 );

				for(File f: files)
					decks.add( new DeckTreeNode( f.getName().split( "[.]" )[0], TREENODE_TYPE.DECK ) );// exclude the extension *.mtgdeck

				treeSavedDeck.setModel( new DefaultTreeModel( decks ) );
				Logger.getInstance().write( Tag.DEBUG, "Refreshing deck list done." );
			}
		} );
	}

//	/**
//	 * Crate a new Group of decks
//	 */
//	public void newGroup(){
//		Logger.getInstance().write( Tag.DEBUG, "TODO" );
//	}

	/* open a new tab with given name and content. */
	private void openTab(String tabName, String content){
		int i = tabDeckOpened.indexOfTab( tabName );
		if(i != -1) {
			tabDeckOpened.setSelectedIndex( i );
		} else {
			tabDeckOpened.addTab( tabName, new TabContent( this.tabDeckOpened, content ) );
			tabDeckOpened.setTabComponentAt( this.tabDeckOpened.getTabCount() - 1, new TabTopRender( tabDeckOpened ) );
			tabDeckOpened.setSelectedIndex( this.tabDeckOpened.getTabCount() - 1 );// set focus on the last tab
		}
	}

	/**
	 * Creates a new empty deck file.
	 */
	public void newEmptyDeck(){
		String deckName = JOptionPane.showInputDialog( this, "Insert new Deck's name:", "Deck's Name",
				JOptionPane.QUESTION_MESSAGE );
		if(deckName != null && !deckName.isEmpty()) {
			openTab( deckName, "" );
		}
	}

	/**
	 * Delete the current opened file.
	 */
	public void deleteCurrentDeck(){
		Logger.getInstance().write( Tag.DEBUG, "Delete current deck called" );
		if(tabDeckOpened.getTabCount() == 0)
			return;

		int index = tabDeckOpened.getSelectedIndex();
		String fileName = tabDeckOpened.getTitleAt( index );
		int r = JOptionPane.showConfirmDialog( this, "Are you really sure ?", "Delete " + fileName,
				JOptionPane.OK_CANCEL_OPTION );
		if(r == JOptionPane.OK_OPTION) {
			String f = String.format( Paths.DECK_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", fileName );
			File file = new File( f );
			if(file.exists())
				file.delete();
			refreshSavedDeck();
			closeCurrentTab();
			Logger.getInstance().write( Tag.DEBUG, fileName + " delete properly." );
		}
	}

	/**
	 * Delete the selected deck from the saved tree deck
	 */
	public void deleteSelectedDeck(){
		Logger.getInstance().write( Tag.DEBUG, "Delete selected deck called" );
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeSavedDeck.getLastSelectedPathComponent();
		if(node == null)
			return;

		//0 = leaf, 1 = root with two row tree
		if(node.getDepth() == 0) { //TODO when implementing group, change depth
			String fileName = node.getUserObject().toString();
			int r = JOptionPane.showConfirmDialog( this, "Are you really sure ?", "Delete " + fileName,
					JOptionPane.OK_CANCEL_OPTION );
			if(r == JOptionPane.OK_OPTION) {
				String f = String.format( Paths.DECK_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", fileName );
				File file = new File( f );
				if(file.exists())
					file.delete();
				refreshSavedDeck();
				closeTab( fileName );
				Logger.getInstance().write( Tag.DEBUG, fileName + " delete properly." );
			}
		}
	}

	/**
	 * Rename the selected deck.
	 */
	public void renameCurrentDeck(){
		Logger.getInstance().write( Tag.DEBUG, "Rename current deck called" );
		if(tabDeckOpened.getTabCount() == 0)
			return;

		String newName = JOptionPane.showInputDialog( this, "Insert new deck's name:" );
		if(newName != null) {
			String oldName = tabDeckOpened.getTitleAt( tabDeckOpened.getSelectedIndex() );

			String f = String.format( Paths.DECK_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", oldName );
			File file = new File( f );
			if(file.exists())
				file.delete();
			this.tabDeckOpened.setTitleAt( tabDeckOpened.getSelectedIndex(), newName );
			((TabContent) this.tabDeckOpened.getComponentAt( tabDeckOpened.getSelectedIndex() )).forceSave();

			Logger.getInstance().write( Tag.DEBUG, "Rename " + oldName + " -> " + newName );
			refreshSavedDeck();
		}
	}

	/**
	 * Rename the selected deck on saved deck
	 */
	public void renameSelectedDeck(){
		Logger.getInstance().write( Tag.DEBUG, "Rename selected deck called" );
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeSavedDeck.getLastSelectedPathComponent();
		if(node == null)
			return;

		//0 = leaf, 1 = root with two row tree
		if(node.getDepth() == 0) {
			String oldName = node.getUserObject().toString();
			String newName = JOptionPane.showInputDialog( this, "Insert new deck's name:" );

			String f = String.format( Paths.DECK_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", oldName );
			String n = String.format( Paths.DECK_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", newName );
			new File( f ).renameTo( new File( n ) );
			closeTab( oldName );

			Logger.getInstance().write( Tag.DEBUG, "Rename " + oldName + " -> " + newName );
			refreshSavedDeck();
		}
	}

	/**
	 * Close the current tab. THIS METHOD DOES NOT CHECK IF IS UNSAVED.
	 */
	public void closeCurrentTab(){
		int index = tabDeckOpened.getSelectedIndex();
		if(index != -1)
			tabDeckOpened.remove( index );
	}

	/**
	 * Close a tab with his name given. THIS METHOD DOES NOT CHECK IF IS UNSAVED.
	 * @param name {@link String} the name of the tab.
	 */
	public void closeTab(String name){
		if(name != null && !name.isEmpty() && this.tabDeckOpened.getTabCount() != 0) {
			int index = this.tabDeckOpened.indexOfTab( name );
			if(index != -1) {
				this.tabDeckOpened.remove( index );
			}
		}
	}

	/**
	 * Entry point to call the save action for all opened tabs.
	 */
	public void saveAll(){
		for(int i = 0; i < this.tabDeckOpened.getTabCount(); i++) {
			this.tabDeckOpened.setSelectedIndex( i );
			((TabContent) this.tabDeckOpened.getSelectedComponent()).save();
		}
		refreshSavedDeck();
	}

	/**
	 * Entry point to call the save action for selected tab.
	 */
	public void saveCurrent(){
		int selTab = this.tabDeckOpened.getSelectedIndex();
		if(selTab != -1) {
			((TabContent) this.tabDeckOpened.getComponentAt( selTab )).save();
		}
		refreshSavedDeck();
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * @return boolean true if there is at least one tab open, otherwise false.
	 */
	public boolean isAtLeastOneTabOpen(){
		return this.tabDeckOpened.getTabCount() == 0 ? false : true;
	}

	/**
	 * @return integer the index of tab unsaved if there is at least one otherwise -1.
	 */
	public int isAtLeastOneTabUnsaved(){
		if(!isAtLeastOneTabOpen())
			return -1;
		else {
			for(int i = 0; i < this.tabDeckOpened.getTabCount(); i++) {
				TabContent c = (TabContent) this.tabDeckOpened.getComponentAt( i );
				if(c.hasBeenModify())
					return i;
			}
			return -1;
		}
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* open selected tab on double click on saved deck. */
	class DeckClickListener extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton( e ) && isDeck()) {
				performDoulbleLeftClickOnLeaf( e );
			} else if(e.getClickCount() == 1 && SwingUtilities.isRightMouseButton( e )) {
				treeSavedDeck.clearSelection();
				treeSavedDeck.setSelectionPath( treeSavedDeck.getPathForLocation( e.getPoint().x, e.getPoint().y ) );
				if(isRoot()) {
					performSingleRigthClickOnRoot( e );
				} else if(isDeck()) {
					performSingleRigthClickOnDeck( e );
				}/*else if(isGroup()) {
					performSingleRigthClickOnGroup( e );
					}*/
			}
		}

		/* perform the double click with the left button on decks */
		private void performDoulbleLeftClickOnLeaf(MouseEvent e){
			//TODO try to implements locked files on decks
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeSavedDeck.getLastSelectedPathComponent();
			String fileName = (String) node.getUserObject();
			String f = String.format( Paths.DECK_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", fileName );
			openTab( fileName, PathUtil.forceReadContent( new File( f ) ) );
		}

		/* perform the single click with the right button on the deck's root */
		private void performSingleRigthClickOnRoot(MouseEvent e){
//			JPopupMenu menu = new JPopupMenu();
//			JMenuItem rename = new JMenuItem( "New Group" );
//			menu.add( rename );
//			menu.show( e.getComponent(), e.getX(), e.getY() );
		}

		/* perform the single click with the right button on the group of decks */
		private void performSingleRigthClickOnDeck(MouseEvent e){
			JPopupMenu menu = new JPopupMenu();

			JMenuItem rename = new JMenuItem( "Rename" );
			rename.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					renameSelectedDeck();
				}
			} );
			JMenuItem delete = new JMenuItem( "Delete" );
			delete.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					deleteSelectedDeck();
				}
			} );

			menu.add( rename );
			menu.add( delete );
			menu.show( e.getComponent(), e.getX(), e.getY() );
		}

		/* check if current selected node is a deck */
		private boolean isDeck(){
			return ((DeckTreeNode) treeSavedDeck.getLastSelectedPathComponent()).getType() == TREENODE_TYPE.DECK;
		}

		/* check if current selected node is the tree root */
		private boolean isRoot(){
			return ((DeckTreeNode) treeSavedDeck.getLastSelectedPathComponent()).getType() == TREENODE_TYPE.ROOT;
		}

		/* check if current selected node is a group of deck */
//		private boolean isGroup(){
//			return ((DeckTreeNode) treeSavedDeck.getLastSelectedPathComponent()).getType() == TREENODE_TYPE.GROUP;
//		}
	}
}
