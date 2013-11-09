package com.hackcaffebabe.mtg.gui.frame.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.VERSION;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import com.hackcaffebabe.about.About;
import com.hackcaffebabe.mtg.gui.frame.MTG;


/**
 * About event of {@link JMenuItem} in {@link MTG} frame
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class AboutActionListener implements ActionListener
{
	private About about;

	@Override
	public void actionPerformed(ActionEvent e){
		if(about == null) {
			about = new About( "About MTGProject", "MTG Card Manager", VERSION );
		} else {
			about.toFront();
		}
		about.setVisible( true );
	}
}