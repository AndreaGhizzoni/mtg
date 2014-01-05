package com.hackcaffebabe.mtg.gui.panel.deckeditor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.gui.panel.deckeditor.listener.ModifyTextDocumentListener;
import com.hackcaffebabe.mtg.gui.panel.deckeditor.listener.SaveAction;


/**
 * Tab content of TabbedPane
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TabContent extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final String SAVE_KEY = "save";

	private JTabbedPane tabbedpaneParent;
	private ModifyTextDocumentListener textDeckDocumentListener;
	private JTextArea textDeck = new JTextArea();

	/**
	 * Instance the tab content whit his string content.
	 * @param contOfTextArea {@link String} the content.
	 */
	public TabContent(JTabbedPane parent, String contOfTextArea){
		super();
		setLayout( new MigLayout( "", "[grow]", "[grow]" ) );
		this.initContent();
		this.tabbedpaneParent = parent;
		this.textDeck.setText( (contOfTextArea == null || contOfTextArea.isEmpty()) ? "" : contOfTextArea );
		this.textDeckDocumentListener = new ModifyTextDocumentListener( contOfTextArea );
		this.textDeck.getDocument().addDocumentListener( this.textDeckDocumentListener );
		this.textDeck.getDocument().putProperty( "src", this.textDeck );
		this.textDeck.getDocument().putProperty( "parent", this.tabbedpaneParent );
		this.requestFocus();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* Initialize all the content. */
	private void initContent(){
		this.textDeck.setWrapStyleWord( true );
		this.textDeck.setLineWrap( true );
		this.textDeck.setFont( new Font( Font.MONOSPACED, Font.PLAIN, 12 ) );
		// don't uncomment this because crtl+s is already bind on frame that TabContent is content in.
		//this.textDeckContent.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_S, Event.CTRL_MASK ), SAVE_KEY );
		this.textDeck.getActionMap().put( SAVE_KEY, new SaveAction() );
		add( new JScrollPane( this.textDeck ), "cell 0 0,grow" );
	}

	/**
	 * Entry point of tab pane to SaveAll action
	 */
	public void save(){
		if(hasBeenModify())
			this.textDeck.getActionMap().get( SAVE_KEY ).actionPerformed( new ActionEvent( this, 1, SAVE_KEY ) );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link JTextArea} */
	public JTextArea getTextArea(){
		return this.textDeck;
	}

	/** @return {@link String} the content of {@link TabContent} as string. */
	public String getText(){
		return getTextArea().getText();
	}

	/** @return {@link JTabbedPane} return the parent tabbed pane reference. */
	public JTabbedPane getTabbedPane(){
		return this.tabbedpaneParent;
	}

	/** @return boolean if this tab has a changed text that is not already saved. */
	public boolean hasBeenModify(){
		return this.textDeckDocumentListener.isFindingChanges();
	}
}
