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
	private JTextArea textDeckContent = new JTextArea();

	/**
	 * Instance the tab content whit his string content.
	 * @param contOfTextArea {@link String} the content.
	 */
	public TabContent(JTabbedPane parent, String contOfTextArea){
		super();
		setLayout( new MigLayout( "", "[grow]", "[grow]" ) );
		this.initContent();
		this.tabbedpaneParent = parent;
		this.textDeckContent.setText( (contOfTextArea == null || contOfTextArea.isEmpty()) ? "" : contOfTextArea );
		this.textDeckContent.getDocument().addDocumentListener( new MyDocumentListener( contOfTextArea ) );
		this.textDeckContent.getDocument().putProperty( "src", this.textDeckContent );
		this.textDeckContent.getDocument().putProperty( "parent", this.tabbedpaneParent );
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
		// don't uncomment this because crtl+s is already bind on frame that TabContent is content in.
		//this.textDeckContent.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_S, Event.CTRL_MASK ), SAVE_KEY );
		this.textDeckContent.getActionMap().put( SAVE_KEY, new SaveAction() );
		add( new JScrollPane( this.textDeckContent ), "cell 0 0,grow" );
	}

	/**
	 * Entry point of tab pane to SaveAll action
	 */
	public void save(){
		this.textDeckContent.getActionMap().get( SAVE_KEY ).actionPerformed( new ActionEvent( this, 1, SAVE_KEY ) );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link JTextArea} */
	public JTextArea getTextArea(){
		return this.textDeckContent;
	}

	/** @return {@link String} the content of {@link TabContent} as string*/
	public String getText(){
		return getTextArea().getText();
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
			needToSave = (initalText.hashCode() != src.getText().hashCode());
			updateTabTopRender( (JTabbedPane) e.getDocument().getProperty( "parent" ) );
			Logger.getInstance().write( Tag.DEBUG, "insert called. need to save ? " + needToSave );
		}

		@Override
		public void removeUpdate(DocumentEvent e){
			JTextArea src = ((JTextArea) e.getDocument().getProperty( "src" ));
			needToSave = (initalText.hashCode() != src.getText().hashCode());
			updateTabTopRender( (JTabbedPane) e.getDocument().getProperty( "parent" ) );
			Logger.getInstance().write( Tag.DEBUG, "remove called. need to save ? " + needToSave );

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
	}
}
