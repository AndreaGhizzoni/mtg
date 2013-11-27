package com.hackcaffebabe.mtg.gui.frame.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.displayError;
import static com.hackcaffebabe.mtg.gui.GUIUtils.displayFiles;
import static com.hackcaffebabe.mtg.gui.GUIUtils.refreshMTGTable;
import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.ioutil.file.UnZipper;
import it.hackcaffebabe.jx.checklist.JXCheckList;
import it.hackcaffebabe.jx.checklist.JXCheckListEntry;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.controller.json.StoreManager;


/**
 * Action listener to selection import
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class SelectedImportActionListener implements ActionListener
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
			Logger.getInstance().write( Tag.INFO, "Try to Selected Import from backup: " + f.getSelectedFile() );
			try {
				UnZipper unzip = new UnZipper( f.getSelectedFile(), new File( DBCostants.JSON_PATH ) );
				Logger.getInstance().write( Tag.DEBUG, "unzipper created correctly" );

				DefaultListModel<JXCheckListEntry<String>> model = JXCheckList.convertToModel( unzip.listZipContent(), true );
				JXCheckList<String> lstCheckList = new JXCheckList<>();
				lstCheckList.setModel( model );

				JComponent[] input = { new JLabel( "Select wich card you want to import:" ), new JScrollPane( lstCheckList ) };
				int r = JOptionPane.showConfirmDialog( null, input, "Selected Import", JOptionPane.OK_CANCEL_OPTION );
				if(r == JOptionPane.OK_OPTION) {
					Logger.getInstance().write( Tag.DEBUG, "Selected Objects: " + lstCheckList.getCheckedObjects() );
					List<File> lstFileUnzipped = unzip.unZipSelective( lstCheckList.getCheckedObjects() );
					Logger.getInstance().write( Tag.DEBUG, "File unzipped: " + lstFileUnzipped );

					StoreManager.getInstance().refresh();
					refreshMTGTable();
					displayFiles( lstFileUnzipped );
				}
			} catch(IOException ex) {
				displayError( null, "Error while reading backup file.\nLog is reported." );
				ex.printStackTrace( Logger.getInstance().getPrintStream() );
			}
		}
	}
}
