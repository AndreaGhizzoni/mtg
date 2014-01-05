package com.hackcaffebabe.mtg.gui.panel.deckeditor.listener;

import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import com.hackcaffebabe.mtg.gui.panel.deckeditor.TabContent;


/**
 * Save action that is performed when "Save" or "SaveAll" button is clicked.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class SaveAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;

	private Logger log = Logger.getInstance();

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof TabContent) {
//			TabContent src = ((TabContent) e.getSource());
			log.write( Tag.INFO, "Save action called..." );
		}
	}
}
