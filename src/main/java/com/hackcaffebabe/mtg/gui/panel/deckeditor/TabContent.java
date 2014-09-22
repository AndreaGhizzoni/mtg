package main.java.com.hackcaffebabe.mtg.gui.panel.deckeditor;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import main.java.com.hackcaffebabe.mtg.controller.deckmanager.DeckManager;
import main.java.com.hackcaffebabe.mtg.gui.panel.deckeditor.listener.ModifyTextDocumentListener;
import net.miginfocom.swing.MigLayout;


/**
 * Tab content of TabbedPane
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TabContent extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JTabbedPane parent;
	private ModifyTextDocumentListener textDeckDocumentListener;
	private JTextArea textDeck = new JTextArea();

	/**
	 * Instance the tab content whit his string content.
	 * @param parent {@link JTabbedPane} the tab pane content.
	 * @param contOfTextArea {@link String} the content.
	 */
	public TabContent(JTabbedPane parent, String contOfTextArea){
		super();
		setLayout( new MigLayout( "", "[grow]", "[grow]" ) );
		this.initContent();
		this.parent = parent;
		this.textDeck.setText( (contOfTextArea == null || contOfTextArea.isEmpty()) ? "" : contOfTextArea );
		this.textDeckDocumentListener = new ModifyTextDocumentListener( contOfTextArea, this.textDeck, this.parent );
		this.textDeck.getDocument().addDocumentListener( this.textDeckDocumentListener );
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
		add( new JScrollPane( this.textDeck ), "cell 0 0,grow" );
	}

	/**
	 * Entry point of tab pane to Save and SaveAll action
	 */
	public void save(){
		if(hasBeenModify()) {
			DeckManager.getInstance().save( getTabName(), getText() );
			this.textDeckDocumentListener.updateInitialText();
		}
	}

	/**
	 * Force the save action.
	 */
	public void forceSave(){
		DeckManager.getInstance().save( getTabName(), getText() );
		this.textDeckDocumentListener.updateInitialText();
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * This method set the font of {@link JTextArea}.
	 * @param f {@link Font} the font to set.
	 */
	public void modifyFont(Font f) throws IllegalArgumentException{
		if(f == null)
			throw new IllegalArgumentException( "Font to set cane not be null." );
		if(f != this.textDeck.getFont())
			this.textDeck.setFont( f );
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

	/** @return {@link JTabbedPane} return the parent tab pane reference. */
	public JTabbedPane getTabbedPane(){
		return this.parent;
	}

	/** @return {@link String} return the tab name of this tab content is in. */
	public String getTabName(){
		return this.parent.getTitleAt( this.parent.getSelectedIndex() );
	}

	/** @return boolean if this tab has a changed text that is not already saved. */
	public boolean hasBeenModify(){
		return this.textDeckDocumentListener.isFindingChanges();
	}
}
