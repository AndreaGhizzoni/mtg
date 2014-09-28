package com.hackcaffebabe.mtg.gui.panel.deckeditor;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.jx.tree.JXTree;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
import com.hackcaffebabe.mtg.controller.deckmanager.DeckManager;
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
	private DeckManager manager = DeckManager.getInstance();

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
		this.manager.refreshDecks( this.treeSavedDeck );
	}

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
			if(DeckManager.getInstance().exists( deckName )) {
				String msg = "You have another deck with the same name!\nThe oldest %s deck file will be overwritten!\nDo you want to continue?";
				String title = "Duplication of %s found!";
				int r = JOptionPane.showConfirmDialog( this, String.format( msg, deckName ),
						String.format( title, deckName ), JOptionPane.YES_NO_OPTION );
				if(r == JOptionPane.NO_OPTION)
					return;
			}
			openTab( deckName, "" );
		}
	}

	/**
	 * Delete the current opened file.
	 */
	public void deleteCurrentDeck(){
		if(tabDeckOpened.getTabCount() == 0)
			return;

		int index = tabDeckOpened.getSelectedIndex();
		String fileName = tabDeckOpened.getTitleAt( index );
		int r = JOptionPane.showConfirmDialog( this, "Are you really sure ?", String.format( "Delete %s", fileName ),
				JOptionPane.OK_CANCEL_OPTION );
		if(r == JOptionPane.OK_OPTION) {
			this.manager.delete( fileName );
			refreshSavedDeck();
			closeCurrentTab();
		}
	}

	/**
	 * Delete the selected deck from the saved tree deck
	 */
	public void deleteSelectedDeck(){
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeSavedDeck.getLastSelectedPathComponent();
		if(node == null)
			return;

		//0 = leaf, 1 = root with two row tree
		if(node.getDepth() == 0) { //TODO when implementing group, change depth
			String fileName = node.getUserObject().toString();
			int r = JOptionPane.showConfirmDialog( this, "Are you really sure ?",
					String.format( "Delete %s", fileName ), JOptionPane.OK_CANCEL_OPTION );
			if(r == JOptionPane.OK_OPTION) {
				this.manager.delete( fileName );
				refreshSavedDeck();
				closeTab( fileName );
			}
		}
	}

	/**
	 * Rename the selected deck.
	 */
	public void renameCurrentDeck(){
		if(tabDeckOpened.getTabCount() == 0)
			return;

		String newName = JOptionPane.showInputDialog( this, "Insert new deck's name:" );
		if(newName != null) {
			String oldName = tabDeckOpened.getTitleAt( tabDeckOpened.getSelectedIndex() );
			this.manager.rename( oldName, newName );
			this.tabDeckOpened.setTitleAt( tabDeckOpened.getSelectedIndex(), newName );
			refreshSavedDeck();
		}
	}

	/**
	 * Rename the selected deck on saved deck
	 */
	public void renameSelectedDeck(){
		DeckTreeNode node = (DeckTreeNode) treeSavedDeck.getLastSelectedPathComponent();
		if(node == null)
			return;

		//0 = leaf, 1 = root with two row tree
		if(node.getDepth() == 0) {
			String oldName = node.getUserObject().toString();
			String newName = JOptionPane.showInputDialog( this, "Insert new deck's name:" );
			this.manager.rename( oldName, newName );
			closeTab( oldName );
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
				doulbleLeftClickOnLeaf( e );
			} else if(e.getClickCount() == 1 && SwingUtilities.isRightMouseButton( e )) {
				treeSavedDeck.clearSelection();
				treeSavedDeck.setSelectionPath( treeSavedDeck.getPathForLocation( e.getPoint().x, e.getPoint().y ) );
				if(isRoot()) {
					singleRigthClickOnRoot( e );
				} else if(isDeck()) {
					singleRigthClickOnDeck( e );
				}/*else if(isGroup()) {
					performSingleRigthClickOnGroup( e );
					}*/
			}
		}

		/* perform the double click with the left button on decks */
		private void doulbleLeftClickOnLeaf(MouseEvent e){
			//TODO try to implements locked files on decks
			DeckTreeNode node = (DeckTreeNode) treeSavedDeck.getLastSelectedPathComponent();
			String fileName = (String) node.getUserObject();
			String f = String.format( Paths.DECKS_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", fileName );
			openTab( fileName, PathUtil.forceReadContent( new File( f ) ) );
		}

		/* perform the single click with the right button on the deck's root */
		private void singleRigthClickOnRoot(MouseEvent e){
//			JPopupMenu menu = new JPopupMenu();
//			JMenuItem rename = new JMenuItem( "New Group" );
//			menu.add( rename );
//			menu.show( e.getComponent(), e.getX(), e.getY() );
		}

		/* perform the single click with the right button on the group of decks */
		private void singleRigthClickOnDeck(MouseEvent e){
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
