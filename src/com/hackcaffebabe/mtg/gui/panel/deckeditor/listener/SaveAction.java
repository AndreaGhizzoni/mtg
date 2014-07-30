package com.hackcaffebabe.mtg.gui.panel.deckeditor.listener;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;
import com.hackcaffebabe.mtg.controller.Paths;
import com.hackcaffebabe.mtg.gui.GUIUtils;
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
			log.write( Tag.DEBUG, "Save action called." );

			String nameOfDeck = ((TabContent) e.getSource()).getTabName();
			log.write( Tag.DEBUG, "Modify has been detected on deck => " + nameOfDeck );

			//TODO check if already exists deck with the same name.
			doSave( ((TabContent) e.getSource()), nameOfDeck );
		}
	}

	/* method that write on stream */
	private void doSave(final TabContent src, final String name){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				String fileName = String.format( "%s.mtgdeck", name );
				String deckPath = String.format( "%s" + PathUtil.FILE_SEPARATOR + "%s", Paths.DECKS_PATH, fileName );
				try {
					Writer w = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( deckPath ), "utf-8" ) );
					w.write( src.getText() );
					w.flush();
					w.close();
				} catch(IOException e) {
					GUIUtils.displayError( null, e );
				}
			}
		} );
	}
}
