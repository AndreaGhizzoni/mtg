package com.hackcaffebabe.mtg.gui.frame;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import com.hackcaffebabe.mtg.gui.GUIUtils;
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

		MyButton delFile = new MyButton( "-" );
		delFile.addActionListener( new DeleteFileActionListener() );

		MyButton renameFile = new MyButton( "r" );

		toolBar.add( newFile );
		toolBar.add( save );
		toolBar.add( saveAll );
		toolBar.addSeparator();
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
		JMenuItem menuEditDelCurrent = new JMenuItem( "Delete deck" );
		menuEditDelCurrent.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, Event.CTRL_MASK
				+ Event.SHIFT_MASK ) );
		menuEditDelCurrent.addActionListener( new DeleteFileActionListener() );

		JMenuItem menuEditRename = new JMenuItem( "Rename" );
		menuEditRename.setAccelerator( KeyStroke.getKeyStroke( "F2" ) );

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
		menuEdit.add( menuEditRefresh );

		JMenu menuHelp = new JMenu( "Help" );

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
			content.newEmptyDeckFile();
		}
	}

	/* action listener to delete current file */
	class DeleteFileActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			content.deleteCurrentFile();
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
}
