package com.hackcaffebabe.mtg.controller.impoexpo;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.jx.checklist.JXCheckList;
import it.hackcaffebabe.jx.checklist.JXCheckListEntry;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import com.hackcaffebabe.mtg.controller.Paths;
import com.hackcaffebabe.mtg.controller.StringNormalizer;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.GUIUtils;
import com.hackcaffebabe.mtg.gui.frame.ImporterGUI;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * Class to perform the import from zip backup file.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Importer extends SwingWorker<Void, String>
{
	private Logger log = Logger.getInstance();

	private ImpoExpoWhat whatToImport;
	private JTextArea textArea;
	private JProgressBar bar;
	private JButton btnClosedButton;

	public Importer(ImpoExpoWhat what, ImporterGUI parent){
		addPropertyChangeListener( new PCL() );
		this.whatToImport = what;
		this.textArea = parent.getTextArea();
		this.bar = parent.getProgressBar();
		this.btnClosedButton = parent.getClosedButton();
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	protected Void doInBackground() throws Exception{
		File zipFile = ImpoExpoUtils.showUserLocationChooser( 1, whatToImport );
		if(zipFile != null) {
			switch( this.whatToImport ) {
				case ALL_CARDS:
					importAllCard( zipFile );
					break;
				case ALL_DECKS:
					//TODO import all deck goes here.
					break;
				case SELECTIVE_CARDS:
					importSelectedCards( zipFile );
					break;
				case SELECTIVE_DECKS:
					//TODO import selective deck goes here.
					break;
				default:
					break;
			}
			publish( "Imported Complete!" );
			return null;
		} else {
			publish( "Nothing to do." );
			return null;
		}
	}

	@Override
	protected void process(List<String> chunks){
		for(String i: chunks)
			textArea.append( String.format( "%-51sOK\n", i ) );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* method to import selected cards. */
	private void importSelectedCards(File fromZip) throws Exception{
		long start = System.currentTimeMillis();
		log.write( Tag.INFO, String.format( "Try to selected cards Import from backup: %s", fromZip.getName() ) );

		DefaultListModel<JXCheckListEntry<String>> model = JXCheckList.convertToModel( listFile( fromZip ), true );
		JXCheckList<String> lstCheckList = new JXCheckList<>();
		lstCheckList.setModel( model );
		JComponent[] input = { new JLabel( "Select wich card you want to import:" ), new JScrollPane( lstCheckList ) };
		int r = JOptionPane.showConfirmDialog( null, input, "Selected Import", JOptionPane.OK_CANCEL_OPTION );
		if(r == JOptionPane.OK_OPTION) {
			List<String> list = lstCheckList.getCheckedObjects();
			int tot = list.size();
			if(tot == 0)
				return;
			int count = 1;
			ZipInputStream zis = new ZipInputStream( new FileInputStream( fromZip ) );
			ZipEntry ze = zis.getNextEntry();
			File tmp;
			MTGCard c;
			while( ze != null ) {
				if(list.contains( ze.getName() )) {
					tmp = read( ze, zis );
					c = StoreManager.getInstance().loadFile( tmp );
					if(c != null) {
						StoreManager.getInstance().add( c );
						publish( StringNormalizer.removeExtension( tmp.getName() ) );
						setProgress( (count++ * 100) / tot );
					}
				}
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		}

		long end = System.currentTimeMillis();
		log.write( Tag.INFO, String.format( "Selected import from backup zip file correctly in : %dms", (end - start) ) );
	}

	/* method to import all the cards */
	private void importAllCard(File fromZip) throws Exception{
		long start = System.currentTimeMillis();
		log.write( Tag.INFO, String.format( "Try to Import from backup: %s", fromZip.getName() ) );

		int tot = count( fromZip );
		int count = 1;
		ZipInputStream zis = new ZipInputStream( new FileInputStream( fromZip ) );
		ZipEntry ze = zis.getNextEntry();
		File tmp;
		MTGCard c;
		while( ze != null ) {
			tmp = read( ze, zis );
			c = StoreManager.getInstance().loadFile( tmp );
			if(c != null) {
				StoreManager.getInstance().add( c );
			}

			publish( StringNormalizer.removeExtension( tmp.getName() ) );
			setProgress( (count++ * 100) / tot );
			ze = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();

		long end = System.currentTimeMillis();
		log.write( Tag.INFO, String.format( "Import from backup zip file correctly in : %dms", (end - start) ) );
	}

	/* read the zip entry from zip input stream and extract his content into new file. */
	private File read(ZipEntry ze, ZipInputStream zis) throws Exception{
		File tmp = new File( getRigthPath( ze.getName() ) );
		FileOutputStream fos = new FileOutputStream( tmp );
		int len;
		byte[] buffer = new byte[1024];
		while( (len = zis.read( buffer )) > 0 ) {
			fos.write( buffer, 0, len );
		}
		fos.close();
		return tmp;
	}

	/* count the file into zip file. */
	private int count(File f){
		int tot = 0;
		try {
			ZipInputStream zis = new ZipInputStream( new FileInputStream( f ) );
			ZipEntry ze = zis.getNextEntry();
			while( ze != null ) {
				tot++;
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch(IOException e) {
			return 0;
		}
		return tot;
	}

	/* returns the list of the files into a zip file. */
	private List<String> listFile(File f){
		List<String> toReturn = new ArrayList<>();
		try {
			ZipInputStream zis = new ZipInputStream( new FileInputStream( f ) );
			ZipEntry ze = zis.getNextEntry();
			while( ze != null ) {
				toReturn.add( ze.getName() );
				ze = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
		} catch(IOException e) {
			return null;
		}
		return toReturn;
	}

	/* return the right path of the file according to whatToImport enum. */
	private String getRigthPath(String name){
		if(whatToImport.equals( ImpoExpoWhat.ALL_CARDS ) || whatToImport.equals( ImpoExpoWhat.SELECTIVE_CARDS ))
			return String.format( "%s%s%s", Paths.JSON_PATH, PathUtil.FILE_SEPARATOR, name );
		else if(whatToImport.equals( ImpoExpoWhat.ALL_DECKS ) || whatToImport.equals( ImpoExpoWhat.SELECTIVE_DECKS ))
			return String.format( "%s%s%s", Paths.DECKS_PATH, PathUtil.FILE_SEPARATOR, name );
		else return "";
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* property change listener to determinate when swing worker is finish. */
	private class PCL implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt){
			switch( evt.getPropertyName() ) {
				case "progress":
					bar.setIndeterminate( false );
					bar.setValue( (Integer) evt.getNewValue() );
					break;
				case "state":
					switch( (StateValue) evt.getNewValue() ) {
						case DONE:
							if(whatToImport.equals( ImpoExpoWhat.ALL_CARDS )
									|| whatToImport.equals( ImpoExpoWhat.SELECTIVE_CARDS )) {
//								StoreManager.getInstance().refresh();
								GUIUtils.refreshMTGTable();
							} else if(whatToImport.equals( ImpoExpoWhat.ALL_DECKS )
									|| whatToImport.equals( ImpoExpoWhat.SELECTIVE_DECKS )) {
								//refreshing decks goes here
							}
							btnClosedButton.setEnabled( true );
							break;
						case PENDING:
							bar.setVisible( true );
							bar.setIndeterminate( true );
							break;
						case STARTED:
							publish( "Importing files..." );
							break;
						default:
							break;
					}
			}
		}
	}
}
