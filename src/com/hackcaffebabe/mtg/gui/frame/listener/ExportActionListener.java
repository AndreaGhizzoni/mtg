package com.hackcaffebabe.mtg.gui.frame.listener;

import it.hackcaffebabe.ioutil.file.PathUtil;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
 * @version 1.0
 */
public class ExportActionListener implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e){
		JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
		f.setDialogTitle( "Select folder to save backup file" );
		f.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		int r = f.showDialog( null, "OK" );
		if(r == JFileChooser.APPROVE_OPTION) {
			JMenuItem src = (JMenuItem)e.getSource();
			src.setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
			String destination = f.getSelectedFile().toString();
			File backupFile = new File( destination + PathUtil.FILE_SEPARATOR + DBCostants.getBackupFileName() );
			StoreManager.getInstance().createBackup( backupFile );
			src.setCursor( null );

			String msg = "Backup file saved correctly on: " + backupFile.getAbsolutePath();
			JOptionPane.showMessageDialog( src, msg, "Success!", JOptionPane.INFORMATION_MESSAGE );
		}
	}
}
