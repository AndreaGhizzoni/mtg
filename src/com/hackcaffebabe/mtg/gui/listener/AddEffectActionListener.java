package com.hackcaffebabe.mtg.gui.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.showEffectDialog;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import com.hackcaffebabe.mtg.model.card.OLD_Effect;


/**
 * Action Listener to get new {@link OLD_Effect} from the user.
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
	 * Instance a listener of {@link OLD_Effect} object.
	 * @param parent {@link Component} to anchor the pop-up menu to get {@link OLD_Effect} object.
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
		OLD_Effect ef = showEffectDialog( this.parent, null );
		if(ef != null) {
			JXObjectModel<OLD_Effect> model = (JXObjectModel<OLD_Effect>) this.table.getModel();
			if(model.getRowCount() == 0) {
				this.table.setModel( new JXObjectModel<OLD_Effect>( Arrays.asList( ef ) ) );
			} else {
				model.addObject( ef );
			}
			this.tableColumnAdjuster.adjustColumns();
		}
	}
}
