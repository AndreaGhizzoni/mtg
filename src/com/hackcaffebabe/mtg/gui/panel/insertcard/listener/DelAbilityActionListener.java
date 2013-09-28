package com.hackcaffebabe.mtg.gui.panel.insertcard.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import it.hackcaffebabe.viewutils.table.JXTable;
import it.hackcaffebabe.viewutils.table.JXTableColumnAdjuster;
import it.hackcaffebabe.viewutils.table.model.DisplayableObject;
import it.hackcaffebabe.viewutils.table.model.JXObjectModel;

/**
 * class that describe the action on btnDelActivity in InsertCardConten.java
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DelAbilityActionListener implements ActionListener
{
	private JXTable tableAbility;
	private JXTableColumnAdjuster tableAbilityColumnAdjuster;
	
	public DelAbilityActionListener( JXTable tableEffects, JXTableColumnAdjuster tableEffectsColumnAdjuster ){
		this.tableAbility = tableEffects;
		this.tableAbilityColumnAdjuster = tableEffectsColumnAdjuster;
	}

	@Override@SuppressWarnings( "unchecked" )
	public void actionPerformed( ActionEvent e ){
		int i = tableAbility.getSelectedModelRow();
		if( i != -1 ){
			JXObjectModel<DisplayableObject> model = (JXObjectModel<DisplayableObject>)tableAbility.getModel();
			model.removeObject( i );
			tableAbilityColumnAdjuster.adjustColumns();
		}
	}
}
