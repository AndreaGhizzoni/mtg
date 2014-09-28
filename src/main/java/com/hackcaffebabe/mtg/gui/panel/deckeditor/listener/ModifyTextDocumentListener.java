package com.hackcaffebabe.mtg.gui.panel.deckeditor.listener;

import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.hackcaffebabe.mtg.gui.panel.deckeditor.TabTopRender;


/**
 * This class is used by Deck Editor.<br>
 * This class provide the listener to determinate if the document needs to be saved or not.<br>
 * If the document needs to be saved, this class will change the title color of tab pane (witch is contained) in red,<br>
 * otherwise will be black.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ModifyTextDocumentListener implements DocumentListener
{
	private String initalText;
	private JTextArea textSource;
	private JTabbedPane parent;
	private boolean needToSave = false;

	/**
	 * Initialize the Modify Text Document Listener to detect if the text is changed in text source given.
	 * @param initialText {@link String} the initial text.
	 * @param textSource {@link JTextArea} the text area to check if the text has been modify.
	 * @param parent {@link JTabbedPane} the tab pane witch the text source is containe in.
	 */
	public ModifyTextDocumentListener(String initialText, JTextArea textSource, JTabbedPane parent){
		this.textSource = textSource;
		this.parent = parent;
		this.initalText = initialText;
	}

	@Override
	public void insertUpdate(DocumentEvent e){
		needToSave = checkIfTextHasBeenModify();
		updateTabTopRender();
	}

	@Override
	public void removeUpdate(DocumentEvent e){
		needToSave = checkIfTextHasBeenModify();
		updateTabTopRender();
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

	/* this method change the color of the top label if it detects a text modification. */
	private void updateTabTopRender(){
		int i = parent.getSelectedIndex();
		JLabel topLabel = ((TabTopRender) parent.getTabComponentAt( i )).getLabel();
		Color JLabelDefaultColor = (Color) UIManager.get( "Label.foreground", JLabel.getDefaultLocale() );
		if(needToSave) {
			if(topLabel.getForeground() == JLabelDefaultColor)
				topLabel.setForeground( Color.RED );
		} else {
			topLabel.setForeground( JLabelDefaultColor );
		}
	}

	/**
	 * This method reset the document listener:<br>
	 *  - the initial text to check the modify is set whit the content of getProperty( "src" ) (JTextArea)<br>
	 * call this method after save the changes.
	 */
	public void updateInitialText(){
		this.initalText = textSource.getText();
		this.needToSave = false;
		Logger.getInstance().write( Tag.DEBUG, "reset initial text in modify text document listener" );
		updateTabTopRender();
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link Boolean} true if file has been modify, otherwise false */
	public boolean isFindingChanges(){
		return needToSave;
	}
}
