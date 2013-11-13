package com.hackcaffebabe.mtg.gui.panel.mtg.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.hackcaffebabe.mtg.gui.frame.AdvanceSearch;
import com.hackcaffebabe.mtg.gui.panel.mtg.MTGContent;


/**
 * Event handle on button btnAdvanceSearch in {@link MTGContent}
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class AdvanceSearchActionListener implements ActionListener
{
	private AdvanceSearch ads = null;

	@Override
	public void actionPerformed(ActionEvent e){
		new AdvanceSearch().setVisible( true );
	}
}
