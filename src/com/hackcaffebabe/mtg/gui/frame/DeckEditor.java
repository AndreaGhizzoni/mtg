package com.hackcaffebabe.mtg.gui.frame;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultEditorKit;
import com.hackcaffebabe.mtg.gui.GUIUtils;
import com.hackcaffebabe.mtg.gui.frame.listener.AboutActionListener;
import com.hackcaffebabe.mtg.gui.panel.deckeditor.DeckEditorContent;


/**
 * Main Frame of deck editor.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DeckEditor extends JFrame
{
	private static final long serialVersionUID = 1L;
	private DeckEditorContent content = new DeckEditorContent();

	/**
	 * Instance the frame and all his content.
	 */
	public DeckEditor(){
		super( GUIUtils.TITLE_DECK_EDITOR );
		setLayout( new BorderLayout() );
		setSize( GUIUtils.DIMENSION_DECK_EDITOR );
		setMinimumSize( GUIUtils.DIMENSION_DECK_EDITOR );
		setDefaultCloseOperation( WindowConstants.DO_NOTHING_ON_CLOSE );
		addWindowListener( new WinListener() );

		this.initMenuBar();
		this.initToolBar();
		this.initContent();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize the content */
	private void initContent(){
		setContentPane( this.content );
	}

	/* initialize the tool bar */
	private void initToolBar(){
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable( false );
		toolBar.setRollover( true );

		MyButton newFile = new MyButton( "+" );
		newFile.addActionListener( new EmptyFileActionListener() );

		MyButton save = new MyButton( "s" );
		save.addActionListener( new SaveCurrentActionListener() );

		MyButton saveAll = new MyButton( "sa" );
		saveAll.addActionListener( new SaveAllActionListener() );

		MyButton copy = new MyButton( "copy" );
		copy.addActionListener( new DefaultEditorKit.CopyAction() );

		MyButton cut = new MyButton( "cut" );
		cut.addActionListener( new DefaultEditorKit.CutAction() );

		MyButton paste = new MyButton( "paste" );
		paste.addActionListener( new DefaultEditorKit.PasteAction() );

		MyButton delFile = new MyButton( "del" );
		delFile.addActionListener( new DeleteSelectedDeckActionListener() );

		MyButton renameFile = new MyButton( "r" );

		toolBar.add( newFile );
		toolBar.add( save );
		toolBar.add( saveAll );
		toolBar.addSeparator();
		toolBar.add( copy );
		toolBar.add( cut );
		toolBar.add( paste );
		toolBar.add( Box.createGlue() );
		toolBar.add( delFile );
		toolBar.addSeparator();
		toolBar.add( renameFile );
		this.content.add( toolBar, BorderLayout.NORTH );
	}

	/* initialize the menu bar */
	private void initMenuBar(){
		JMenuBar menuBar = new JMenuBar();

		JMenu menuFile = new JMenu( "File" );
		JMenuItem menuFileNewEmptyFile = new JMenuItem( "New Deck" );
		menuFileNewEmptyFile.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_N, Event.CTRL_MASK ) );
		menuFileNewEmptyFile.addActionListener( new EmptyFileActionListener() );

//		JMenuItem menuFileNewGroup = new JMenuItem( "New Group" );
//		menuFileNewGroup.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_G, Event.CTRL_MASK ) );
//		menuFileNewGroup.addActionListener(  );

		JMenuItem menuFileSave = new JMenuItem( "Save" );
		menuFileSave.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_S, Event.CTRL_MASK ) );
		menuFileSave.addActionListener( new SaveCurrentActionListener() );

		JMenuItem menuFileSaveAll = new JMenuItem( "Save all" );
		menuFileSaveAll.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_S, Event.CTRL_MASK + Event.SHIFT_MASK ) );
		menuFileSaveAll.addActionListener( new SaveAllActionListener() );

		JMenuItem menuFileClose = new JMenuItem( "Close" );
		menuFileClose.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Q, Event.CTRL_MASK ) );
		menuFileClose.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		} );

		menuFile.add( menuFileNewEmptyFile );
		menuFile.addSeparator();
		menuFile.add( menuFileSave );
		menuFile.add( menuFileSaveAll );
		menuFile.addSeparator();
		menuFile.add( menuFileClose );

		JMenu menuEdit = new JMenu( "Edit" );
		JMenuItem menuEditDelCurrent = new JMenuItem( "Delete" );
		menuEditDelCurrent.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, Event.CTRL_MASK
				+ Event.SHIFT_MASK ) );
		menuEditDelCurrent.addActionListener( new DeleteCurrentDeckActionListener() );

		JMenuItem menuEditRename = new JMenuItem( "Rename" );
		menuEditRename.setAccelerator( KeyStroke.getKeyStroke( "F2" ) );

		JMenuItem menuEditCopy = new JMenuItem( "Copy" );
		menuEditCopy.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_C, Event.CTRL_MASK ) );
		menuEditCopy.addActionListener( new DefaultEditorKit.CopyAction() );

		JMenuItem menuEditCut = new JMenuItem( "Cut" );
		menuEditCut.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_X, Event.CTRL_MASK ) );
		menuEditCut.addActionListener( new DefaultEditorKit.CutAction() );

		JMenuItem menuEditPaste = new JMenuItem( "Paste" );
		menuEditPaste.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_V, Event.CTRL_MASK ) );
		menuEditPaste.addActionListener( new DefaultEditorKit.PasteAction() );

		JMenuItem menuEditRefresh = new JMenuItem( "Refresh" );
		menuEditRefresh.setAccelerator( KeyStroke.getKeyStroke( "F5" ) );
		menuEditRefresh.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				content.refreshSavedDeck();
			}
		} );

		menuEdit.add( menuEditDelCurrent );
		menuEdit.add( menuEditRename );
		menuEdit.addSeparator();
		menuEdit.add( menuEditCopy );
		menuEdit.add( menuEditCut );
		menuEdit.add( menuEditPaste );
		menuEdit.addSeparator();
		menuEdit.add( menuEditRefresh );

		JMenu menuHelp = new JMenu( "Help" );
		JMenuItem helpAbout = new JMenuItem( "About" );
		helpAbout.setAccelerator( KeyStroke.getKeyStroke( "F1" ) );
		helpAbout.addActionListener( new AboutActionListener() );
		menuHelp.add( helpAbout );

		menuBar.add( menuFile );
		menuBar.add( menuEdit );
		menuBar.add( menuHelp );
		setJMenuBar( menuBar );
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* action listener to add new empty file */
	class EmptyFileActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			content.newEmptyDeck();
		}
	}

	/* action listener to add new group */
	class NewGroupActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			content.newGroup();
		}
	}

	/* action listener to delete current file */
	class DeleteCurrentDeckActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			content.deleteCurrentDeck();
		}
	}

	class DeleteSelectedDeckActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			content.deleteSelectedDeck();
		}
	}

	/* action listener to save all current opened tabs */
	class SaveAllActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			content.saveAll();
		}
	}

	/* action listener to save selected tab */
	class SaveCurrentActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			content.saveCurrent();
		}
	}

	/* inner class that renders a JButton whit no border for JToolBar */
	class MyButton extends JButton
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Creates a new tool-bar button whit a text.
		 * @param text {@link String}.
		 */
		public MyButton(String text){
			super( text );
			initialize();
		}

		/**
		 * Creates a new tool-bar button with only an icon.
		 * @param icon {@link Icon}
		 */
		public MyButton(Icon icon){
			super( icon );
			initialize();
		}

		/**
		 * Creates a new tool-bar button with a icon and a text.
		 * @param text {@link String}
		 * @param icon {@link Icon}
		 */
		public MyButton(String text, Icon icon){
			super( text, icon );
			initialize();
		}

		/* Initializes the button. */
		private void initialize(){
			setOpaque( false );
			setBackground( new java.awt.Color( 0, 0, 0, 0 ) );
			setBorderPainted( false );
			setMargin( new Insets( 1, 1, 1, 1 ) );
			addMouseListener( new MouseAdapter(){
				public void mouseEntered(MouseEvent ev){
					setBorderPainted( true );
				}

				public void mouseExited(MouseEvent ev){
					setBorderPainted( false );
				}
			} );
		}
	}

	/* windows closing listener */
	private class WinListener implements WindowListener
	{
		@Override
		public void windowClosing(WindowEvent e){
			if(content.isAtLeastOneTabUnsaved() != -1) {
				int r = JOptionPane
						.showConfirmDialog(
								content,
								"There are unsaved deck! If you close this window you modify will be lost!\nWould you continue?",
								"Unsaved deck!", JOptionPane.OK_CANCEL_OPTION );
				if(r == JOptionPane.OK_OPTION) {
					dispose();
				}
			} else {
				dispose();
			}
		}

		@Override
		public void windowOpened(WindowEvent e){}

		@Override
		public void windowClosed(WindowEvent e){}

		@Override
		public void windowIconified(WindowEvent e){}

		@Override
		public void windowDeiconified(WindowEvent e){}

		@Override
		public void windowActivated(WindowEvent e){}

		@Override
		public void windowDeactivated(WindowEvent e){}
	}
}
