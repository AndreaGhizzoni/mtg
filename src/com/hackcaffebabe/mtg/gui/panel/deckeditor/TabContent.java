package com.hackcaffebabe.mtg.gui.panel.deckeditor;

import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.miginfocom.swing.MigLayout;
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
	private MyDocumentListener textDeckDocumentListener;
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
		this.textDeckDocumentListener = new MyDocumentListener( contOfTextArea );
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
		if(this.textDeckDocumentListener.isChangingFind())
			this.textDeck.getActionMap().get( SAVE_KEY ).actionPerformed( new ActionEvent( this, 1, SAVE_KEY ) );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link JTextArea} */
	public JTextArea getTextArea(){
		return this.textDeck;
	}

	/** @return {@link String} the content of {@link TabContent} as string*/
	public String getText(){
		return getTextArea().getText();
	}

	/** @return {@link JTabbedPane} return the parent tabbed pane reference */
	public JTabbedPane getTabbedPane(){
		return this.tabbedpaneParent;
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	class MyDocumentListener implements DocumentListener
	{
		private final String initalText;
		private boolean needToSave = false;

		public MyDocumentListener(String initialText){
			this.initalText = initialText;
		}

		@Override
		public void insertUpdate(DocumentEvent e){
			JTextArea src = ((JTextArea) e.getDocument().getProperty( "src" ));
			JTabbedPane pane = ((JTabbedPane) e.getDocument().getProperty( "parent" ));
			needToSave = checkHasBeenModify( src );
			updateTabTopRender( pane );
			Logger.getInstance().write( Tag.DEBUG, "insert called. need to save ? " + needToSave );
		}

		@Override
		public void removeUpdate(DocumentEvent e){
			JTextArea src = ((JTextArea) e.getDocument().getProperty( "src" ));
			JTabbedPane pane = ((JTabbedPane) e.getDocument().getProperty( "parent" ));
			needToSave = checkHasBeenModify( src );
			updateTabTopRender( pane );
			Logger.getInstance().write( Tag.DEBUG, "remove called. need to save ? " + needToSave );

		}

		/* this method check if the text from JTextArea given has been modify according to the initial text */
		private boolean checkHasBeenModify(JTextArea src){
			return initalText.hashCode() != src.getText().hashCode();
		}

		/* method to append "*" string on the top of the title top bar */
		private void updateTabTopRender(JTabbedPane parent){
			int i = parent.getSelectedIndex();
			String oldTitle = parent.getTitleAt( i );
			if(needToSave) {
				//need to append "*" on the title
				if(!oldTitle.startsWith( "*" ))
					parent.setTitleAt( i, "*" + oldTitle );
			} else {
				//remove "*" from the title
				parent.setTitleAt( i, oldTitle.substring( 1, oldTitle.length() ) );
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e){}

		/** @return {@link Boolean} true if file has been modify, otherwhise false */
		public boolean isChangingFind(){
			return needToSave;
		}
	}
}
