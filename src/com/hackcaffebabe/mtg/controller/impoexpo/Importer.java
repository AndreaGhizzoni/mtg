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
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import com.hackcaffebabe.mtg.controller.Paths;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.GUIUtils;


/**
 * TODO add doc
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Importer extends SwingWorker<Void, String>
{
	private Logger log = Logger.getInstance();

	private What whatToImport;
	private JTextArea textArea;
	private JProgressBar bar;

	/**
	 * TODO add doc
	 * @param what
	 * @param textArea
	 * @param bar
	 */
	public Importer(What what, JTextArea textArea, JProgressBar bar){
		addPropertyChangeListener( new PCL() );
		this.whatToImport = what;
		this.textArea = textArea;
		this.bar = bar;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	protected Void doInBackground() throws Exception{
		File zipFile = showUserLocationChooser();
		int totMTGCards, count;
		ZipInputStream zis;
		ZipEntry ze;
		File tmp;
		FileOutputStream fos;
		if(zipFile != null) {
			switch( this.whatToImport ) {
				case ALL_CARDS:
					log.write( Tag.INFO, String.format( "Try to Selected Import from backup: %s", zipFile.getName() ) );
					totMTGCards = count( zipFile );
					count = 1;
					zis = new ZipInputStream( new FileInputStream( zipFile ) );
					ze = zis.getNextEntry();
					while( ze != null ) {
						tmp = new File( getRigthPath( ze.getName() ) );
						fos = new FileOutputStream( tmp );
						int len;
						byte[] buffer = new byte[1024];
						while( (len = zis.read( buffer )) > 0 ) {
							fos.write( buffer, 0, len );
						}
						fos.close();
						ze = zis.getNextEntry();

						publish( tmp.getName() );
						setProgress( (count++ * 100) / totMTGCards );
					}
					zis.closeEntry();
					zis.close();
					break;
				case ALL_DECKS:
					break;
				case SELECTIVE_CARDS:
					log.write( Tag.INFO, String.format( "Try to Selected Import from backup: %s", zipFile.getName() ) );
					DefaultListModel<JXCheckListEntry<String>> model = JXCheckList.convertToModel( listFile( zipFile ),
							true );
					JXCheckList<String> lstCheckList = new JXCheckList<>();
					lstCheckList.setModel( model );
					JComponent[] input = { new JLabel( "Select wich card you want to import:" ),
							new JScrollPane( lstCheckList ) };
					int r = JOptionPane
							.showConfirmDialog( null, input, "Selected Import", JOptionPane.OK_CANCEL_OPTION );
					if(r == JOptionPane.OK_OPTION) {
						List<String> list = lstCheckList.getCheckedObjects();
						totMTGCards = list.size();
						if(totMTGCards == 0)
							return null;
						count = 1;
						zis = new ZipInputStream( new FileInputStream( zipFile ) );
						ze = zis.getNextEntry();
						while( ze != null ) {
							if(list.contains( ze.getName() )) {
								tmp = new File( getRigthPath( ze.getName() ) );
								fos = new FileOutputStream( tmp );
								int len;
								byte[] buffer = new byte[1024];
								while( (len = zis.read( buffer )) > 0 ) {
									fos.write( buffer, 0, len );
								}
								fos.close();
								publish( tmp.getName() );
								setProgress( (count++ * 100) / totMTGCards );
							}
							ze = zis.getNextEntry();
						}
						zis.closeEntry();
						zis.close();
					}
					break;
				case SELECTIVE_DECKS:
					break;
				default:
					break;
			}

		} else {
			publish( "Nothing to do." );
		}
		return null;
	}

	@Override
	protected void process(List<String> chunks){
		for(String i: chunks)
			textArea.append( String.format( "%s\n", i ) );
	}

//===========================================================================================
// METHOD
//===========================================================================================
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
		if(whatToImport.equals( What.ALL_CARDS ) || whatToImport.equals( What.SELECTIVE_CARDS ))
			return String.format( "%s%s%s", Paths.JSON_PATH, PathUtil.FILE_SEPARATOR, name );
		else if(whatToImport.equals( What.ALL_DECKS ) || whatToImport.equals( What.SELECTIVE_DECKS ))
			return String.format( "%s%s%s", Paths.DECKS_PATH, PathUtil.FILE_SEPARATOR, name );
		else return "";
	}

	/* show the JFileChooser */
	private File showUserLocationChooser(){
		JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
		f.setDialogTitle( "Select backup file" );
		f.setFileFilter( new FileFilter(){
			@Override
			public String getDescription(){
				return "Zip files";
			}

			@Override
			public boolean accept(File f){
				if(whatToImport.equals( What.ALL_CARDS ) || whatToImport.equals( What.SELECTIVE_CARDS ))
					return f.isDirectory() || f.getName().endsWith( Paths.BCK_CARDS_EXT );

				if(whatToImport.equals( What.ALL_DECKS ) || whatToImport.equals( What.SELECTIVE_DECKS ))
					return f.isDirectory() || f.getName().endsWith( Paths.BCK_DECKS_EXT );

				return false;
			}
		} );
		if(f.showDialog( null, "Open" ) == JFileChooser.APPROVE_OPTION) {
			return f.getSelectedFile();
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
							StoreManager.getInstance().refresh();
							GUIUtils.refreshMTGTable();
							publish( "Cards has been Imported!" );
							break;
						case PENDING:
							bar.setVisible( true );
							bar.setIndeterminate( true );
							break;
						case STARTED:
							publish( "Importing files..." );
							log.write( Tag.INFO, "Importing cards complete!" );
							break;
						default:
							break;
					}
			}
		}
	}
}
