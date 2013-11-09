package com.hackcaffebabe.mtg.gui.frame.listener;

import static com.hackcaffebabe.mtg.gui.GUIUtils.displayUnzippedFiles;
import static com.hackcaffebabe.mtg.gui.GUIUtils.refreshMTGTable;
import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
 * @version 1.0
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
			JMenuItem src = (JMenuItem) e.getSource();
			src.setCursor( new Cursor( Cursor.WAIT_CURSOR ) );
			List<File> lst = unzip( f.getSelectedFile(), new File( DBCostants.JSON_PATH ) );
			StoreManager.getInstance().refresh();
			refreshMTGTable();
			displayUnzippedFiles( lst );
			src.setCursor( null );
		}

	}

	private List<File> unzip(File srcZip, File dstFolder){
		Logger.getInstance().write( Tag.DEBUG, "upzip called." );

		List<File> lstFileLoded = new ArrayList<>();
		try {
			ZipInputStream zis = new ZipInputStream( new FileInputStream( srcZip ) );
			Logger.getInstance().write( Tag.DEBUG, "zip opened correctly" );

			ZipEntry ze = zis.getNextEntry();
			while( ze != null ) {
				File newFile = new File( dstFolder + PathUtil.FILE_SEPARATOR + ze.getName() );

				//create the file. if file already exists, skip it
				if(new File( newFile.getParent() ).mkdirs()) {
					FileOutputStream fos = new FileOutputStream( newFile );
					int len;
					byte[] buffer = new byte[1024];
					while( (len = zis.read( buffer )) > 0 ) {
						fos.write( buffer, 0, len );
					}
					fos.close();
					lstFileLoded.add( newFile );
				}

				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch(IOException ex) {
			ex.printStackTrace( Logger.getInstance().getPrintStream() );
			return null;
		}

		Logger.getInstance().write( Tag.DEBUG, "done" );
		return lstFileLoded;
	}
}