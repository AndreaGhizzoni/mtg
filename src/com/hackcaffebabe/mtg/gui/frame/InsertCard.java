package com.hackcaffebabe.mtg.gui.frame;

import static com.hackcaffebabe.mtg.gui.GUIUtils.*;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import com.hackcaffebabe.mtg.gui.panel.insertcard.InsertCardContent;
import com.hackcaffebabe.mtg.gui.panel.mtg.MTGContent;


/**
 * This is the main frame that contains all components to insert the MTG card data.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class InsertCard extends JFrame
{
	private static final long serialVersionUID = 1L;
	private InsertCardContent content;
	
	/**
	 * Create the frame.
	 */
	public InsertCard(MTGContent c){
		super( TITLE_INSERT_CARD );
		setMinimumSize( DIMENSION_INSERT_CARD );
		setLocation( (Toolkit.getDefaultToolkit().getScreenSize().width/2)-(DIMENSION_INSERT_CARD.width/2),
				     (Toolkit.getDefaultToolkit().getScreenSize().height/2)-(DIMENSION_INSERT_CARD.height/2));
		this.initContent(c);
		this.initMenuBar();
	}
	
//===========================================================================================
// METHOD
//===========================================================================================
	/*
	 * Initialize the content
	 */
	private void initContent(MTGContent c){
		this.content = new InsertCardContent(c);
		setContentPane( this.content );
	}
	
	/*
	 * Initialize the menu bar
	 */
	private void initMenuBar(){
		JMenuBar bar = new JMenuBar();
		
		JMenu file = new JMenu( "File" );
		JMenuItem fileExit = new JMenuItem( "Exit" );
		fileExit.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				close();
			}
		} );
		file.add( fileExit );
		
		JMenu edit = new JMenu( "Edit" );
		
		bar.add( file );
		bar.add( edit );
		setJMenuBar( bar );
	}
	
	/*
	 * Close the frame
	 */
	public void close(){ dispose(); }
	
//===========================================================================================
// INNER CLASS
//===========================================================================================
}
