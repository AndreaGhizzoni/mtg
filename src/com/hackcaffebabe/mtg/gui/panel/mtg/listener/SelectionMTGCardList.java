package com.hackcaffebabe.mtg.gui.panel.mtg.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG;
import static com.hackcaffebabe.mtg.gui.GUIUtils.PNL_MTGPROPERTIES;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import com.hackcaffebabe.mtg.gui.frame.InsertUpdateCard;
import com.hackcaffebabe.mtg.gui.listener.DeleteCardActionListener;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * Event on click on JXTABLE_MTG
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class SelectionMTGCardList extends MouseAdapter implements ListSelectionListener
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
				doSingleLeftClick( e );
				doSingleRightClick( e );
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(ListSelectionEvent e){
		int r = JXTABLE_MTG.getSelectedModelRow();
		if(r != -1) {
			MTGCard c = ((JXObjectModel<MTGCard>) JXTABLE_MTG.getModel()).getObject( r );
			PNL_MTGPROPERTIES.setMTGCardToView( c );
		}
	}

	/* dispatcher event when mouse do left click on JXTABLE_MTG */
	@SuppressWarnings("unchecked")
	private void doSingleLeftClick(MouseEvent e){
		int r = JXTABLE_MTG.rowAtPoint( e.getPoint() );
		if(r != -1) {
			JXTABLE_MTG.getSelectionModel().setSelectionInterval( r, r );
			MTGCard c = ((JXObjectModel<MTGCard>) JXTABLE_MTG.getModel()).getObject( r );
			PNL_MTGPROPERTIES.setMTGCardToView( c );
		}
	}

	/* dispatcher event when mouse do double left click on JXTABLE_MTG */
	@SuppressWarnings("unchecked")
	private void doDoubleLeftClick(MouseEvent e){
		JXObjectModel<MTGCard> model = (JXObjectModel<MTGCard>) JXTABLE_MTG.getModel();
		int row = JXTABLE_MTG.getSelectedModelRow();
		if(row != -1) {
			new InsertUpdateCard( model.getObject( row ) ).setVisible( true );
		}
	}

	/* dispatcher event when mouse do right click on JXTABLE_MTG */
	private void doSingleRightClick(MouseEvent e){
		JPopupMenu menu = new JPopupMenu();

		JMenuItem update = new JMenuItem( "Update" );
		update.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				doDoubleLeftClick( null );
			}
		} );
		menu.add( update );

		JMenuItem delete = new JMenuItem( "Delete" );
		delete.addActionListener( DeleteCardActionListener.getInstance() );
		menu.add( delete );

		menu.show( e.getComponent(), e.getX(), e.getY() );
	}
}
