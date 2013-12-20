package com.hackcaffebabe.mtg.gui.panel.mtg.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
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
	@SuppressWarnings("unchecked")
	public void mouseClicked(MouseEvent e){
		if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton( e )) {
			JXObjectModel<MTGCard> model = (JXObjectModel<MTGCard>) JXTABLE_MTG.getModel();
			int row = JXTABLE_MTG.getSelectedModelRow();
			if(row != -1) {
				new InsertUpdateCard( model.getObject( row ) ).setVisible( true );
			}
		}
	}
}
