package com.hackcaffebabe.mtg.gui.listener;

import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.hackcaffebabe.mtg.model.card.Effect;


/**
 * class that describe the action on btnDelEffect in InsertCardConten.java
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DelEffectActionListener implements ActionListener
{
	private JXTable tableEffects;
	private JXTableColumnAdjuster tableEffectsColumnAdjuster;

	public DelEffectActionListener(JXTable tableEffects,
			JXTableColumnAdjuster tableEffectsColumnAdjuster){
		this.tableEffects = tableEffects;
		this.tableEffectsColumnAdjuster = tableEffectsColumnAdjuster;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e){
		int i = tableEffects.getSelectedModelRow();
		if(i != -1) {
			JXObjectModel<Effect> model = (JXObjectModel<Effect>) tableEffects.getModel();
			model.removeObject( i );
			tableEffectsColumnAdjuster.adjustColumns();
		}
	}
}
