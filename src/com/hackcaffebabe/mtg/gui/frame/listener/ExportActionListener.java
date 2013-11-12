package com.hackcaffebabe.mtg.gui.frame.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.displayError;
import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.logger.Logger;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.frame.MTG;

/**
 * Export event of {@link JMenuItem} in {@link MTG} frame
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.5
 */
public class ExportActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e){
		JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
		f.setDialogTitle( "Select export folder" );
		f.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		if(f.showDialog( null, "OK" ) == JFileChooser.APPROVE_OPTION) {
			JMenuItem src = (JMenuItem)e.getSource();
			src.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
			File backupFile = new File( f.getSelectedFile().getPath() + PathUtil.FILE_SEPARATOR + DBCostants.BCK_NAME );
			try {
				StoreManager.getInstance().createBackup( backupFile );
				
				String msg = String.format( "Backup file saved correctly on %s", backupFile.getAbsolutePath() );
				JOptionPane.showMessageDialog( src, msg, "Success!", JOptionPane.INFORMATION_MESSAGE );
			} catch(IOException ex) {
				displayError( null, "Error to export.\nLog is reported." );
				ex.printStackTrace( Logger.getInstance().getPrintStream() );
			}
			src.setCursor( null );
		}
	}
}
