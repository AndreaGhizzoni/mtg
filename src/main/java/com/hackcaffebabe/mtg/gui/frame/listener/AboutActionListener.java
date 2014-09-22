package main.java.com.hackcaffebabe.mtg.gui.frame.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import main.java.com.hackcaffebabe.mtg.Version;
import main.java.com.hackcaffebabe.mtg.gui.frame.MTG;
import com.hackcaffebabe.about.About;


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
			about = new About( "About MTGProject", "MTG Card Manager", Version.VERSION );
		} else {
			about.toFront();
		}
		about.setVisible( true );
	}
}