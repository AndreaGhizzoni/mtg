package com.hackcaffebabe.mtg.gui.panel.mtg.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.hackcaffebabe.mtg.gui.frame.InsertCard;
import com.hackcaffebabe.mtg.gui.panel.mtg.MTGContent;

/**
 * Event handle on button btnNewCard in {@link MTGContent}
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class NewCardActionListener implements ActionListener
{
	private InsertCard frame;

	@Override
	public void actionPerformed(ActionEvent e){
		if(frame == null) {
			InsertCard card = new InsertCard( null );
			card.setVisible( true );
			card.toFront();
			frame = card;
		} else {
			frame.setVisible( true );
			frame.toFront();
		}
	}
}
