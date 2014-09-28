package com.hackcaffebabe.mtg.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.hackcaffebabe.mtg.gui.frame.InsertUpdateCard;


/**
 * Event handle to insert new card.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.2
 */
public class NewCardActionListener implements ActionListener
{
	private static NewCardActionListener listener;
	private InsertUpdateCard frame;

	public static NewCardActionListener getInstance(){
		if(listener == null)
			listener = new NewCardActionListener();
		return listener;
	}

	private NewCardActionListener(){}

	@Override
	public void actionPerformed(ActionEvent e){
		if(frame == null)
			frame = new InsertUpdateCard( null );
		frame.setVisible( true );
	}
}
