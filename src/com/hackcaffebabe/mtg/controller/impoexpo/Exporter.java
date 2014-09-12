package com.hackcaffebabe.mtg.controller.impoexpo;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import com.hackcaffebabe.mtg.controller.Paths;
import com.hackcaffebabe.mtg.controller.StringNormalizer;
import com.hackcaffebabe.mtg.gui.frame.ExporterGUI;


/**
 * TODO add doc
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Exporter extends SwingWorker<Void, String>
{
	private Logger log = Logger.getInstance();

	private ImpoExpoWhat whatToExport;
	private JTextArea textArea;
	private JProgressBar bar;
	private JButton btnClosedButton;

	public Exporter(ImpoExpoWhat what, ExporterGUI parent){
		addPropertyChangeListener( new PCL() );
		this.whatToExport = what;
		this.textArea = parent.getTextArea();
		this.bar = parent.getProgressBar();
		this.btnClosedButton = parent.getClosedButton();
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	protected Void doInBackground() throws Exception{
		File directory = ImpoExpoUtils.showUserLocationChooser( 0, whatToExport );
		if(directory != null) {
			File whereToExport = makeBackupFile( directory );
			switch( whatToExport ) {
				case ALL_CARDS:
					createAllCardsBackup( whereToExport );
					break;
				case ALL_DECKS:
					//TODO here goes the export all decks action.
					break;
				case SELECTIVE_CARDS:
					//TODO here goes the export selective cards action.
					break;
				case SELECTIVE_DECKS:
					//TODO here goes the export selective decks action.
					break;
				default:
					break;
			}

			publish( "Exported Complete!" );
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
	/* create the backup of all the cards */
	private void createAllCardsBackup(File destinationFile) throws IOException{
		long start = System.currentTimeMillis();
		log.write( Tag.INFO, "Try to backup of all stored files on : " + destinationFile.getAbsolutePath() );
		if(destinationFile.exists() && !destinationFile.delete()) {
			log.write( Tag.ERRORS, "Error on delete exists backup." );
		} else {
			log.write( Tag.INFO, "Old backup file has been delete." );
		}

		File[] files = new File( Paths.JSON_PATH ).listFiles( new FileFilter(){
			@Override
			public boolean accept(File pathname){
				if(pathname.getName().endsWith( ".json" ))
					return true;
				return false;
			}
		} );
		int tot = files.length;
		int count = 1;
		ZipOutputStream zos = new ZipOutputStream( new FileOutputStream( destinationFile ) );
		FileInputStream in;
		for(File f: files) {
			if(f != null) {
				zos.putNextEntry( new ZipEntry( f.getName() ) );
				in = new FileInputStream( f );
				byte[] buffer = new byte[1024];
				int len;
				while( (len = in.read( buffer )) > 0 ) {
					zos.write( buffer, 0, len );
				}
				in.close();
				zos.closeEntry();

				publish( StringNormalizer.removeExtension( f.getName() ) );
				setProgress( (count++ * 100) / tot );
			}
		}
		zos.close();

		long end = System.currentTimeMillis();
		log.write( Tag.INFO, String.format( "Backup closed and create correctly in %dms", (end - start) ) );
	}

	/* this method creates the appropriate File object according with the file passed as arguemnt*/
	private File makeBackupFile(File base){
		if(whatToExport.equals( ImpoExpoWhat.ALL_DECKS ) || whatToExport.equals( ImpoExpoWhat.SELECTIVE_DECKS )) {
			return new File( base + PathUtil.FILE_SEPARATOR + Paths.BCK_DECKS_NAME );
		} else if(whatToExport.equals( ImpoExpoWhat.ALL_CARDS ) || whatToExport.equals( ImpoExpoWhat.SELECTIVE_CARDS )) {
			return new File( base + PathUtil.FILE_SEPARATOR + Paths.BCK_CARDS_NAME );
		} else {
			return null;
		}
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
							btnClosedButton.setEnabled( true );
							break;
						case PENDING:
							bar.setVisible( true );
							bar.setIndeterminate( true );
							break;
						case STARTED:
							publish( "Exporting files..." );
							break;
						default:
							break;
					}
			}
		}
	}
}
