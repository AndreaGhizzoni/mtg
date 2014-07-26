package com.hackcaffebabe.mtg.gui.frame;

import static com.hackcaffebabe.mtg.gui.GUIUtils.STATUS_BAR_MAIN_FRAME;
import static com.hackcaffebabe.mtg.gui.GUIUtils.refreshMTGTable;
import it.hackcaffebabe.jx.statusbar.JXStatusBar;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import com.hackcaffebabe.mtg.Version;
import com.hackcaffebabe.mtg.gui.FramesDimensions;
import com.hackcaffebabe.mtg.gui.FramesTitles;
import com.hackcaffebabe.mtg.gui.frame.listener.AboutActionListener;
import com.hackcaffebabe.mtg.gui.frame.listener.DeckEditorActionListener;
import com.hackcaffebabe.mtg.gui.frame.listener.ExportActionListener;
import com.hackcaffebabe.mtg.gui.frame.listener.ExportActionListener.WhatToExport;
import com.hackcaffebabe.mtg.gui.frame.listener.ImportActionListener;
import com.hackcaffebabe.mtg.gui.frame.listener.ImportActionListener.WhatToImport;
import com.hackcaffebabe.mtg.gui.listener.DeleteCardActionListener;
import com.hackcaffebabe.mtg.gui.listener.NewCardActionListener;
import com.hackcaffebabe.mtg.gui.panel.mtg.MTGContent;


/**
 * The main frame of Project
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class MTG extends JFrame
{
	private static final long serialVersionUID = 1L;
	private MTGContent content;

	/**
	 * Create the frame.
	 */
	public MTG(){
		super( FramesTitles.TITLE_MAIN_FRAME );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setMinimumSize( FramesDimensions.DIMENSION_MAIN_FRAME );
		setLocation( FramesDimensions.getCenter( FramesDimensions.DIMENSION_MAIN_FRAME ) );
		addWindowListener( new WinListener() );
		this.initContent();
		this.initMenuBar();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		STATUS_BAR_MAIN_FRAME = new JXStatusBar( this, "Ready!", Version.VERSION, 10 );
		STATUS_BAR_MAIN_FRAME.setTextFont( new Font( Font.MONOSPACED, Font.PLAIN, 11 ) );
		this.content = new MTGContent();
		this.content.setOpaque( true );
		setContentPane( this.content );
		add( STATUS_BAR_MAIN_FRAME, BorderLayout.SOUTH );
	}

	/* initialize all menu bar */
	private void initMenuBar(){
		JMenuBar bar = new JMenuBar();

		JMenu file = new JMenu( "File" );
		JMenuItem fileImport = new JMenuItem( "Import..." );
		fileImport.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_I, Event.CTRL_MASK ) );
		fileImport.addActionListener( new ImportActionListener( WhatToImport.ALL_CARDS ) );
		file.add( fileImport );

		JMenuItem fileImportSelected = new JMenuItem( "Selected Import..." );
		fileImportSelected.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_U, Event.CTRL_MASK ) );
		fileImportSelected.addActionListener( new ImportActionListener( WhatToImport.SELECTIVE_CARDS ) );
		file.add( fileImportSelected );

		JMenuItem fileExport = new JMenuItem( "Export..." );
		fileExport.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_E, Event.CTRL_MASK ) );
		fileExport.addActionListener( new ExportActionListener( WhatToExport.ALL_CARDS ) );
		file.add( fileExport );

		file.add( new JSeparator() );
		JMenuItem fileExit = new JMenuItem( "Exit" );
		fileExit.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Q, Event.CTRL_MASK ) );
		fileExit.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				close();
			}
		} );
		file.add( fileExit );

		JMenu edit = new JMenu( "Edit" );
		JMenuItem editDeckEditor = new JMenuItem( "Deck Editor" );
		editDeckEditor.addActionListener( DeckEditorActionListener.getInstance() );
		edit.add( editDeckEditor );
		edit.addSeparator();

		JMenuItem editNewCard = new JMenuItem( "New Card" );
		editNewCard.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_N, Event.CTRL_MASK ) );
		editNewCard.addActionListener( NewCardActionListener.getInstance() );
		edit.add( editNewCard );

		JMenuItem editDelCard = new JMenuItem( "Delete" );
		editDelCard.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, Event.CTRL_MASK + Event.SHIFT_MASK ) );
		editDelCard.addActionListener( DeleteCardActionListener.getInstance() );
		edit.add( editDelCard );

		JMenuItem editRefresh = new JMenuItem( "Refresh" );
		editRefresh.setAccelerator( KeyStroke.getKeyStroke( "F5" ) );
		editRefresh.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				refreshMTGTable();
			}
		} );
		edit.add( editRefresh );

		JMenu help = new JMenu( "Help" );
		JMenuItem helpAbout = new JMenuItem( "About" );
		helpAbout.setAccelerator( KeyStroke.getKeyStroke( "F1" ) );
		helpAbout.addActionListener( new AboutActionListener() );
		help.add( helpAbout );

		bar.add( file );
		bar.add( edit );
		bar.add( help );

		setJMenuBar( bar );
	}

	/* Close the frame */
	public void close(){
		dispose();
		Logger.getInstance().write( Tag.INFO, "Program will exit." );
		System.exit( 0 );
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* windows closing listener */
	private class WinListener implements WindowListener
	{
		public void windowClosing(WindowEvent e){
			close();
		}

		public void windowOpened(WindowEvent e){}

		public void windowClosed(WindowEvent e){}

		public void windowIconified(WindowEvent e){}

		public void windowDeiconified(WindowEvent e){}

		public void windowActivated(WindowEvent e){}

		public void windowDeactivated(WindowEvent e){}
	}
}
