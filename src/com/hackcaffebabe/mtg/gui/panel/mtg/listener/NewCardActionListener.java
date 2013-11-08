package com.hackcaffebabe.mtg.gui.panel.mtg.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.hackcaffebabe.mtg.gui.frame.InsertCard;
import com.hackcaffebabe.mtg.gui.panel.mtg.MTGContent;

/**
 * Event handle on button btnNewCard in {@link MTGContent}
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.2
 */
public class NewCardActionListener implements ActionListener
{
	private InsertCard frame;

	@Override
	public void actionPerformed(ActionEvent e){
		if(frame == null) {
			frame = new InsertCard( null );
		} else {
			frame.toFront();
		}
		frame.setVisible( true );
	}
}