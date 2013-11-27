package com.hackcaffebabe.mtg.gui.frame.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.displayError;
import static com.hackcaffebabe.mtg.gui.GUIUtils.displayFiles;
import static com.hackcaffebabe.mtg.gui.GUIUtils.refreshMTGTable;
import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.ioutil.file.UnZipper;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.frame.MTG;


/**
 * Import event of {@link JMenuItem} in {@link MTG} frame
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.5
 */
public class ImportActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e){
		JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
		f.setDialogTitle( "Select backup file" );
		f.setFileFilter( new FileFilter(){
			@Override
			public String getDescription(){
				return "Zip files";
			}

			@Override
			public boolean accept(File f){
				return f.isDirectory() || f.getName().endsWith( ".zip" );
			}
		} );

		if(f.showDialog( null, "Open" ) == JFileChooser.APPROVE_OPTION) {
			Logger.getInstance().write( Tag.INFO, "Try to Import from backup: " + f.getSelectedFile() );
			JMenuItem src = (JMenuItem) e.getSource();
			src.setCursor( new Cursor( Cursor.WAIT_CURSOR ) );
			try {
				List<File> lst = new UnZipper( f.getSelectedFile(), new File( DBCostants.JSON_PATH ) ).unZipAll( false );//TODO maybe let user to decide.
				StoreManager.getInstance().refresh();
				refreshMTGTable();
				displayFiles( lst );
			} catch(IOException ex) {
				displayError( null, "Error while reading backup file.\nLog is reported." );
				ex.printStackTrace( Logger.getInstance().getPrintStream() );
			}
			src.setCursor( null );
		}
	}
}