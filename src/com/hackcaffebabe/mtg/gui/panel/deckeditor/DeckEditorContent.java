package com.hackcaffebabe.mtg.gui.panel.deckeditor;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.jx.tree.JXTree;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
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
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.gui.GUIUtils;
import com.hackcaffebabe.mtg.gui.components.deckeditor.DeckTreeNode;
import com.hackcaffebabe.mtg.gui.components.deckeditor.DeckTreeNode.TREENODE_TYPE;


/**
 * Deck editor frame content.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
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
		setSize( GUIUtils.DIMENSION_DECK_EDITOR );
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
		this.treeSavedDeck.addMouseListener( new DeckClickListener() );
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
				List<File> files = Arrays.asList( new File( DBCostants.DECK_PATH ).listFiles() );
				Collections.sort( files );

				//TEST
				DeckTreeNode g1 = new DeckTreeNode( "Group 1", TREENODE_TYPE.GROUP );
				DeckTreeNode g2 = new DeckTreeNode( "Group 2", TREENODE_TYPE.GROUP );

				for(File f: files)
					g1.add( new DeckTreeNode( f.getName().split( "[.]" )[0], TREENODE_TYPE.DECK ) );// exclude the extension *.mtgdeck
				decks.add( g1 );
				decks.add( g2 );

				treeSavedDeck.setModel( new DefaultTreeModel( decks ) );
				Logger.getInstance().write( Tag.DEBUG, "Refreshing deck list done." );
			}
		} );
	}

	//TODO write method to make a new group of decks.

	/* open a new tab with given name and content. */
	private void openTab(String tabName, String content){
		int i = tabDeckOpened.indexOfTab( tabName );
		if(i != -1) {
			tabDeckOpened.setSelectedIndex( i );
		} else {
			tabDeckOpened.add( tabName, new TabContent( this.tabDeckOpened, content ) );
			tabDeckOpened.setTabComponentAt( this.tabDeckOpened.getTabCount() - 1, new TabTopRender( tabDeckOpened ) );
			tabDeckOpened.setSelectedIndex( this.tabDeckOpened.getTabCount() - 1 );// set focus on the last tab
		}
	}

	/**
	 * Creates a new empty deck file.
	 */
	public void newEmptyDeckFile(){
		String deckName = JOptionPane.showInputDialog( this, "Insert new Deck's name:", "Deck's Name",
				JOptionPane.QUESTION_MESSAGE );
		if(deckName != null && !deckName.isEmpty()) {
			openTab( deckName, "" );
		}
	}

	/**
	 * Delete the current opened file.
	 */
	public void deleteCurrentFile(){
		if(tabDeckOpened.getTabCount() == 0)
			return;

		int index = tabDeckOpened.getSelectedIndex();
		String fileName = tabDeckOpened.getTitleAt( index );
		int r = JOptionPane.showConfirmDialog( this, "Are you really sure ?", "Delete " + fileName,
				JOptionPane.OK_CANCEL_OPTION );
		if(r == JOptionPane.OK_OPTION) {
			String f = String.format( DBCostants.DECK_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", fileName );
			new File( f ).delete();
			refreshSavedDeck();
			closeCurrentTab();
		}
	}

	/**
	 * Close the current tab.
	 */
	public void closeCurrentTab(){
		int index = tabDeckOpened.getSelectedIndex();
		if(index != -1)
			tabDeckOpened.remove( index );
	}

	/**
	 * Entry point to call the save action for all opened tabs.
	 */
	public void saveAll(){
		for(int i = 0; i < this.tabDeckOpened.getTabCount(); i++) {
			((TabContent) this.tabDeckOpened.getComponentAt( i )).save();
		}
	}

	/**
	 * Entry point to call the save action for selected tab.
	 */
	public void saveCurrent(){
		int selTab = this.tabDeckOpened.getSelectedIndex();
		if(selTab != -1) {
			((TabContent) this.tabDeckOpened.getComponentAt( selTab )).save();
		}
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
	 * @return boolean true if there is at least one tab that is has been modify but not saved.
	 */
	public boolean isAtLeastOneTabUnsaved(){
		if(!isAtLeastOneTabOpen())
			return false;
		else {
			for(int i = 0; i < this.tabDeckOpened.getTabCount(); i++) {
				TabContent c = (TabContent) this.tabDeckOpened.getComponentAt( i );
				if(c.hasBeenModify())
					return true;
			}
			return false;
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
				} else if(isGroup()) {
					performSingleRigthClickOnGroup( e );
				}
			}
		}

		/* perform the double click with the left button on decks */
		private void performDoulbleLeftClickOnLeaf(MouseEvent e){
			//TODO try to implements locked files on decks
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeSavedDeck.getLastSelectedPathComponent();
			String fileName = (String) node.getUserObject();
			String f = String.format( DBCostants.DECK_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", fileName );
			openTab( fileName, PathUtil.forceReadContent( new File( f ) ) );
		}

		/* perform the single click with the right button on the deck's root */
		private void performSingleRigthClickOnRoot(MouseEvent e){
			JPopupMenu menu = new JPopupMenu();

			JMenuItem rename = new JMenuItem( "New Group" );//TODO ...you need to finish this...

			menu.add( rename );
			menu.show( e.getComponent(), e.getX(), e.getY() );
		}

		/* perform the single click with the right button on the group of decks */
		private void performSingleRigthClickOnGroup(MouseEvent e){
			JPopupMenu menu = new JPopupMenu();

			JMenuItem rename = new JMenuItem( "Rename" );//TODO ...you need to finish this...
			JMenuItem delete = new JMenuItem( "Delete" );

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
		private boolean isGroup(){
			return ((DeckTreeNode) treeSavedDeck.getLastSelectedPathComponent()).getType() == TREENODE_TYPE.GROUP;
		}
	}
}
