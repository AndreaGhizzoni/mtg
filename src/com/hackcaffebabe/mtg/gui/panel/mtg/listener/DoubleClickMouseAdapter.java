package com.hackcaffebabe.mtg.gui.panel.mtg.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.hackcaffebabe.mtg.gui.frame.InsertUpdateCard;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * Event on double click on JXTABLE_MTG
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DoubleClickMouseAdapter extends MouseAdapter
{
	private int selRow = -2;

	@SuppressWarnings("unchecked")
	public void mouseClicked(MouseEvent e){
		if(e.getClickCount() == 2) {
			JXObjectModel<MTGCard> model = (JXObjectModel<MTGCard>) JXTABLE_MTG.getModel();
			int row = JXTABLE_MTG.getSelectedModelRow();
			if(selRow != -1 && selRow != row) {
				new InsertUpdateCard( model.getObject( row ) ).setVisible( true );
				selRow = row;
			}
		}
	}
}
