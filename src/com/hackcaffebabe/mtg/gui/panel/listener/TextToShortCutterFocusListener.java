package com.hackcaffebabe.mtg.gui.panel.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;


/**
 * this class is used to set a short cutter string.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TextToShortCutterFocusListener implements FocusListener
{
	private String key;

	public TextToShortCutterFocusListener(String key){
		this.key = key;
	}

	@Override
	public void focusLost(FocusEvent e){
		Object src = e.getSource();
		if(src instanceof JTextField) {
			JTextField t = ((JTextField) src);
			String content = t.getText();
			if(content != null && !content.isEmpty())
				ShortCutterV2.getInstance().put( key, content, "%s" );
			else ShortCutterV2.getInstance().remove( key );
		}
	}

	@Override
	public void focusGained(FocusEvent e){}
}
