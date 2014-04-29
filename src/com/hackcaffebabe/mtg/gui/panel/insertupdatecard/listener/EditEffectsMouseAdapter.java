package com.hackcaffebabe.mtg.gui.panel.insertupdatecard.listener;

import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import com.hackcaffebabe.mtg.gui.GUIUtils;
import com.hackcaffebabe.mtg.model.card.OLD_Effect;


/**
 * Mouse adapter to double click on table effects.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class EditEffectsMouseAdapter extends MouseAdapter
{
	@SuppressWarnings("unchecked")
	@Override
	public void mouseClicked(MouseEvent e){
		JXTable table = (JXTable) e.getSource();
		if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton( e )) {
			int r = table.getSelectedModelRow();
			if(r != -1) {
				JXObjectModel<OLD_Effect> model = (JXObjectModel<OLD_Effect>) table.getModel();
				OLD_Effect oldEffect = model.getObject( r );
				OLD_Effect editEffect = GUIUtils.showEffectDialog( null, oldEffect );

				// edit effect != null because if equals to null, something went wrong with user data.
				// if the oldest is not equal to the edited one, remove the oldest and add the edited.
				if(editEffect != null && !oldEffect.equals( editEffect )) {
					model.removeObject( oldEffect );
					model.addObject( editEffect );
				}
			}
		}
	}
}
