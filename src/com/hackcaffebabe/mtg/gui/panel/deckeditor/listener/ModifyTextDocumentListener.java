package com.hackcaffebabe.mtg.gui.panel.deckeditor.listener;

import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ModifyTextDocumentListener implements DocumentListener
{
	private String initalText;
	private JTextArea textSource;
	private JTabbedPane parent;
	private boolean needToSave = false;

	public ModifyTextDocumentListener(String initialText){
		this.initalText = initialText;
	}

	@Override
	public void insertUpdate(DocumentEvent e){
		installIfAttrAreNull( e );

		needToSave = checkIfTextHasBeenModify();
		updateTabTopRender();

		Logger.getInstance().write( Tag.DEBUG, "insert called. need to save ? " + needToSave );
	}

	@Override
	public void removeUpdate(DocumentEvent e){
		installIfAttrAreNull( e );

		needToSave = checkIfTextHasBeenModify();
		updateTabTopRender();

		Logger.getInstance().write( Tag.DEBUG, "remove called. need to save ? " + needToSave );
	}

	@Override
	/* plane text document never call this method */
	public void changedUpdate(DocumentEvent e){}

//===========================================================================================
// METHOD
//===========================================================================================
	/* this method check if the text from JTextArea has been modify according to the initial text */
	private boolean checkIfTextHasBeenModify(){
		return initalText.hashCode() != textSource.getText().hashCode();
	}

	/* this method set the properties of this document listener if aren't already set */
	private void installIfAttrAreNull(DocumentEvent e){
		if(textSource == null)
			textSource = ((JTextArea) e.getDocument().getProperty( "src" ));
		if(parent == null)
			parent = ((JTabbedPane) e.getDocument().getProperty( "parent" ));
	}

	/* method to append "*" string on the top of the title top bar */
	private void updateTabTopRender(){
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

	/**
	 * This method reset the document listener: 
	 *  - the initial text to check the modify is set whit the content of getProperty( "src" ) (JTextArea)
	 * call this method after save the changes.
	 */
	public void updateInitialText(){
		this.initalText = textSource.getText();
		this.needToSave = false;
		updateTabTopRender();
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link Boolean} true if file has been modify, otherwhise false */
	public boolean isFindingChanges(){
		return needToSave;
	}
}
