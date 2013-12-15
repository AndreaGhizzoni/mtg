package com.hackcaffebabe.mtg.gui.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.PNL_MTGPROPERTIES;
import static com.hackcaffebabe.mtg.gui.GUIUtils.displayError;
import static com.hackcaffebabe.mtg.gui.GUIUtils.refreshMTGTable;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * Event handle to delete the selected card 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DeleteCardActionListener implements ActionListener
{
	private static DeleteCardActionListener listener;

	public static DeleteCardActionListener getInstance(){
		if(listener == null)
			listener = new DeleteCardActionListener();
		return listener;
	}

	private DeleteCardActionListener(){}

	@Override
	public void actionPerformed(ActionEvent e){
		MTGCard card = PNL_MTGPROPERTIES.getDisplayedCard();
		if(card != null) {
			Logger.getInstance().write( Tag.DEBUG, "card to delete: " + card );
			String msg = String.format( "Are you sure to delete %s ?", card.getName() );
			if(JOptionPane.showConfirmDialog( null, msg, "Be careful!", JOptionPane.YES_NO_OPTION ) == 0) {
				try {
					StoreManager.getInstance().delete( card );
					PNL_MTGPROPERTIES.clearAll();
					if(e.getSource() instanceof JButton)
						((JButton) e.getSource()).setEnabled( false );
					refreshMTGTable();
					JOptionPane.showMessageDialog( null, card.getName() + " delete correctly!", "Operation complete!",
							JOptionPane.INFORMATION_MESSAGE );
				} catch(Exception ex) {
					Logger.getInstance().write( Tag.ERRORS, ex.getMessage() );
					ex.printStackTrace( Logger.getInstance().getPrintStream() );
					displayError( null, "Error to delete " + card.getName() );
				}
			}
		}
	}
}
