package com.hackcaffebabe.mtg.gui.panel.mtg.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG;
import static com.hackcaffebabe.mtg.gui.GUIUtils.PNL_MTGPROPERTIES;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import com.hackcaffebabe.mtg.gui.frame.InsertUpdateCard;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * Event on click on JXTABLE_MTG
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ClickMTGListMouseAdapter extends MouseAdapter
{
	@Override
	public void mouseClicked(MouseEvent e){
		if(SwingUtilities.isLeftMouseButton( e )) {
			if(e.getClickCount() == 1) {
				doSingleLeftClick( e );
			}
			if(e.getClickCount() == 2) {
				doDoubleLeftClick( e );
			}
		}

		if(SwingUtilities.isRightMouseButton( e )) {
			if(e.getClickCount() == 1) {
//				doSingleLeftClick( e );
//				doSingleRightClick( e );
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void doSingleLeftClick(MouseEvent e){
		int r = JXTABLE_MTG.rowAtPoint( e.getPoint() );
		if(r != -1) {
			MTGCard c = ((JXObjectModel<MTGCard>) JXTABLE_MTG.getModel()).getObject( r );
			PNL_MTGPROPERTIES.setMTGCardToView( c );
		}
	}

	@SuppressWarnings("unchecked")
	private void doDoubleLeftClick(MouseEvent e){
		JXObjectModel<MTGCard> model = (JXObjectModel<MTGCard>) JXTABLE_MTG.getModel();
		int row = JXTABLE_MTG.getSelectedModelRow();
		if(row != -1) {
			new InsertUpdateCard( model.getObject( row ) ).setVisible( true );
		}
	}

//	private void doSingleRightClick(MouseEvent e){
//		JPopupMenu menu = new JPopupMenu();
//		menu.add( new JMenuItem( "Click Me!" ) );
//		menu.show( e.getComponent(), e.getX(), e.getY() );
//	}
}
