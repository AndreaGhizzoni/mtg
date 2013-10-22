package com.hackcaffebabe.mtg.gui.panel.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.showEffectDialog;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import com.hackcaffebabe.mtg.model.card.Effect;

/**
 * Action Listener to get new {@link Effect} from the user.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class AddEffectActionListener implements ActionListener
{
	private JXTable table;
	private JXTableColumnAdjuster tableColumnAdjuster;
	private Component parent;
	
	/**
	 * Instance a listener of {@link Effect} object.
	 * @param parent {@link Component} to anchor the pop-up menu to get {@link Effect} object.
	 * @param table {@link JXTable} to get the model
	 * @param columnAdjuster {@link JXTableColumnAdjuster} to adjust the columns with new data.
	 */
	public AddEffectActionListener(Component parent, JXTable table, JXTableColumnAdjuster columnAdjuster){
		this.parent = parent;
		this.table = table;
		this.tableColumnAdjuster = columnAdjuster;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e){
		Effect ef = showEffectDialog( this.parent );
		if(ef != null) {
			JXObjectModel<Effect> model = (JXObjectModel<Effect>) this.table.getModel();
			if(model.getRowCount() == 0) {
				this.table.setModel( new JXObjectModel<Effect>( Arrays.asList( ef ) ) );
			}
			else {
				model.addObject( ef );
			}
			this.tableColumnAdjuster.adjustColumns();
		}
	}
}
