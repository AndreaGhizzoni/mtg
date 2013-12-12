package com.hackcaffebabe.mtg.gui.frame;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
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
		this.initContent();
		this.initToolBar();
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
		JToolBar toolBar = new JToolBar( "Actions" );
		toolBar.setFloatable( false );
		toolBar.setRollover( true );

		JButton newFile = new JButton( "+" );
		newFile.addActionListener( new EmptyFileActionListener() );

		JButton save = new JButton( "s" );
		JButton saveAll = new JButton( "sa" );

		JButton delFile = new JButton( "-" );
		delFile.addActionListener( new DeleteFileActionListener() );

		JButton renameFile = new JButton( "r" );

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

		JMenuItem menuFileClose = new JMenuItem( "Close" );
		menuFileClose.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Q, Event.CTRL_MASK ) );

		menuFile.add( menuFileNewEmptyFile );
		menuFile.addSeparator();
		menuFile.add( menuFileClose );

		JMenu menuEdit = new JMenu( "Edit" );
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
}
