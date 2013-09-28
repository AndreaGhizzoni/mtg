package com.hackcaffebabe.mtg.gui.frame;

import static com.hackcaffebabe.mtg.gui.GUIUtils.*;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
		super( TITLE_MAIN_FRAME );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setMinimumSize( DIMENSION_MAIN_FRAME );
		setLocation( (Toolkit.getDefaultToolkit().getScreenSize().width/2)-(DIMENSION_MAIN_FRAME.width/2),
				     (Toolkit.getDefaultToolkit().getScreenSize().height/2)-(DIMENSION_MAIN_FRAME.height/2));
		addWindowListener( new WinListener() );
		this.initContent();
		this.initMenuBar();
	}
	
//===========================================================================================
// METHOD
//===========================================================================================
	private void initContent(){
		this.content = new MTGContent();
		setContentPane( this.content );
	}

	
	private void initMenuBar(){
		JMenuBar bar = new JMenuBar();
		
		JMenu file = new JMenu( "File" );
		JMenuItem fileExit = new JMenuItem( "Exit" );
		file.add( fileExit );
		
		JMenu edit = new JMenu( "Edit" );
		
		JMenu help = new JMenu( "Help" );
		JMenuItem helpAbout = new JMenuItem( "About" );
		help.add( helpAbout );
		
		bar.add( file );
		bar.add( edit );
		bar.add( help );
		setJMenuBar( bar );
	}
	
	/*
	 * Close the frame
	 */
	public void close(){ 
		dispose(); 
	}
	
//===========================================================================================
// INNER CLASS
//===========================================================================================
	private class WinListener implements WindowListener
	{
		public void windowClosing( WindowEvent e ){ close(); }
		public void windowOpened( WindowEvent e ){}
		public void windowClosed( WindowEvent e ){}
		public void windowIconified( WindowEvent e ){}
		public void windowDeiconified( WindowEvent e ){}
		public void windowActivated( WindowEvent e ){}
		public void windowDeactivated( WindowEvent e ){}
	}
}
