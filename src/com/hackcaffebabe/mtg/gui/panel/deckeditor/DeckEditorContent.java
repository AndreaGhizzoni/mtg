package com.hackcaffebabe.mtg.gui.panel.deckeditor;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.gui.GUIUtils;


/**
 * Deck editor frame content.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DeckEditorContent extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JList<String> lstSavedDeck = new JList<>();
	private JTabbedPane tabDeckOpened = new JTabbedPane( JTabbedPane.TOP );
	private final JPanel pnlInfo = new JPanel();

	/**
	 * Initialize the content of deck editor.
	 */
	public DeckEditorContent(){
		super();
		setSize( GUIUtils.DIMENSION_DECK_EDITOR );
		setLayout( new MigLayout( "", "[grow][120:120:120,grow][120:120:120]", "[grow][150]" ) );
		this.initContent();
		this.refreshSavedDeck();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize the content */
	private void initContent(){
		add( tabDeckOpened, "cell 0 0 1 2,grow" );

		JPanel pnlSavedDeck = new JPanel();
		lstSavedDeck.addMouseListener( new ListDeckClickListener() );
		lstSavedDeck.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		pnlSavedDeck.setBorder( new TitledBorder( "Saved Deck" ) );
		pnlSavedDeck.setLayout( new MigLayout( "", "[grow]", "[grow]" ) );
		pnlSavedDeck.add( new JScrollPane( lstSavedDeck ), "cell 0 0,grow" );
		add( pnlSavedDeck, "cell 1 0 2 1,grow" );
		pnlInfo.setBorder( new TitledBorder( "Deck's info" ) );

		add( pnlInfo, "cell 1 1 2 1,grow" );
	}

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
	 * Refresh and read all the saved deck into.
	 */
	public void refreshSavedDeck(){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				DefaultListModel<String> model = new DefaultListModel<>();
				for(File f: new File( DBCostants.DECK_PATH ).listFiles())
					model.addElement( f.getName().split( "[.]" )[0] );// exclude the extension *.mtgdeck
				lstSavedDeck.setModel( model );
			}
		} );
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
	class ListDeckClickListener extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e){
			if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton( e )) {
				performDoulbleLeftClick( e );
			} else if(e.getClickCount() == 1 && SwingUtilities.isRightMouseButton( e )) {
				performSingleRigthClick( e );
			}
		}

		/* perform the double click with the left button */
		private void performDoulbleLeftClick(MouseEvent e){
			//TODO try to implements locked files on decks
			String fileName = lstSavedDeck.getSelectedValue();
			String f = String.format( DBCostants.DECK_PATH + PathUtil.FILE_SEPARATOR + "%s.mtgdeck", fileName );

			openTab( lstSavedDeck.getSelectedValue(), PathUtil.forceReadContent( new File( f ) ) );
		}

		/* perform the single click with the right button */
		private void performSingleRigthClick(MouseEvent e){
			lstSavedDeck.clearSelection();

			Logger.getInstance().write( Tag.DEBUG, lstSavedDeck.locationToIndex( e.getPoint() ) );
			JPopupMenu menu = new JPopupMenu();

			JMenuItem rename = new JMenuItem( "Rename" );//TODO ...you need to finish this...
			JMenuItem delete = new JMenuItem( "Delete" );

			menu.add( rename );
			menu.add( delete );
			menu.show( e.getComponent(), e.getX(), e.getY() );

			lstSavedDeck.setSelectedIndex( lstSavedDeck.locationToIndex( e.getPoint() ) );

		}
	}
}
