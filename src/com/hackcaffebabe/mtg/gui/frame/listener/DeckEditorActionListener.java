package com.hackcaffebabe.mtg.gui.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.hackcaffebabe.mtg.gui.frame.DeckEditor;


/**
 * Action listener to open {@link DeckEditor}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DeckEditorActionListener implements ActionListener
{
	private static DeckEditorActionListener listener;

	/**
	 * @return {@link DeckEditorActionListener} 
	 * return the instance of deck editor action listener
	 */
	public static DeckEditorActionListener getInstance(){
		if(listener == null)
			listener = new DeckEditorActionListener();
		return listener;
	}

	private DeckEditorActionListener(){}

	@Override
	public void actionPerformed(ActionEvent e){
		new DeckEditor().setVisible( true );
	}
}
