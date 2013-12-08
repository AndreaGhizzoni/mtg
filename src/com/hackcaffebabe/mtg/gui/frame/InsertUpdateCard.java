package com.hackcaffebabe.mtg.gui.frame;

import static com.hackcaffebabe.mtg.gui.GUIUtils.TITLE_UPDATE_CARD;
import static com.hackcaffebabe.mtg.gui.GUIUtils.TITLE_INSERT_CARD;
import static com.hackcaffebabe.mtg.gui.GUIUtils.DIMENSION_INSERT_CARD;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import com.hackcaffebabe.mtg.gui.panel.insertupdatecard.InsertUpdateCardContent;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * This is the main frame that contains all components to insert the MTG card data.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class InsertUpdateCard extends JFrame
{
	private static final long serialVersionUID = 1L;
	private InsertUpdateCardContent content;

	/**
	 * Instance a frame to view or update a {@link MTGCard}
	 * @param cardToView {@link MTG} or null if user want to insert a card.
	 */
	public InsertUpdateCard(MTGCard cardToView){
		super( cardToView == null ? TITLE_INSERT_CARD : TITLE_UPDATE_CARD );
		setMinimumSize( DIMENSION_INSERT_CARD );
		setLocation( (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (DIMENSION_INSERT_CARD.width / 2),
				(Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (DIMENSION_INSERT_CARD.height / 2) );
		this.initContent( cardToView );
		this.initMenuBar();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(MTGCard cardToView){
		this.content = new InsertUpdateCardContent( cardToView, this );
		this.content.setOpaque( true );
		setContentPane( this.content );
	}

	/* Initialize the menu bar */
	private void initMenuBar(){
		JMenuBar bar = new JMenuBar();

		JMenu file = new JMenu( "File" );
		JMenuItem fileClose = new JMenuItem( "Close" );
		fileClose.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Q, Event.CTRL_MASK ) );
		fileClose.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				close();
			}
		} );
		file.add( fileClose );

		JMenu edit = new JMenu( "Edit" );

		bar.add( file );
		bar.add( edit );
		setJMenuBar( bar );
	}

	/**
	 * Close the frame
	 */
	public void close(){
		dispose();
	}
}
