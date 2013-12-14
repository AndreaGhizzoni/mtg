package com.hackcaffebabe.mtg.gui.panel.deckeditor;

import java.awt.Event;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.gui.panel.deckeditor.listener.SaveAction;


/**
 * Tab content of TabbedPane
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
class TabContent extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final String SAVE_KEY = "save";
	private JTextArea textDeckContent = new JTextArea();

	/**
	 * Instance the tab content whit his string content.
	 * @param contOfTextArea {@link String} the content.
	 */
	public TabContent(String contOfTextArea){
		super();
		setLayout( new MigLayout( "", "[grow]", "[grow]" ) );
		this.initContent();
		this.textDeckContent.setText( (contOfTextArea == null || contOfTextArea.isEmpty()) ? "" : contOfTextArea );
		this.requestFocus();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* Initialize all the content. */
	private void initContent(){
		this.textDeckContent.setWrapStyleWord( true );
		this.textDeckContent.setLineWrap( true );
		this.textDeckContent.setFont( new Font( Font.MONOSPACED, Font.PLAIN, 12 ) );
		this.textDeckContent.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_S, Event.CTRL_MASK ), SAVE_KEY );
		this.textDeckContent.getActionMap().put( SAVE_KEY, new SaveAction() );
		add( new JScrollPane( this.textDeckContent ), "cell 0 0,grow" );
	}

	/**
	 * Entry point of tab pane to SaveAll action
	 */
	public void save(){
		this.textDeckContent.getActionMap().get( SAVE_KEY ).actionPerformed( new ActionEvent( this, 1, SAVE_KEY ) );
	}
}
