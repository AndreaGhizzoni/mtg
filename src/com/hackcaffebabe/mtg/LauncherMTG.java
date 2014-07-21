package com.hackcaffebabe.mtg;

import static com.hackcaffebabe.mtg.gui.GUIUtils.displayError;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.io.File;
import java.io.PrintStream;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.frame.MTG;


/**
 * Class to launch the program
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 2.0
 */
public class LauncherMTG
{
	public static void main(String... args){
		try {
			//TODO check JDK 8!
			initHomeFolder();
			initLogger();
			initSubFolderStructure();
			initDB();
			initUIManager();
			SwingUtilities.invokeLater( new Runnable(){
				@Override
				public void run(){
					MTG g = new MTG();
					g.pack();
					g.setVisible( true );
				}
			} );

		} catch(Exception e) {
			displayError( null, e );
		}
	}

	private static void initLogger() throws Exception{
		Logger.getInstance();
		if(DBCostants.DB_LOG_ON_FILE) {
			Logger.getInstance().setPrintStream( new PrintStream( new File( DBCostants.LOG_FILE_PATH ) ) );
			Logger.getInstance().disableTag( Tag.DEBUG );
		}
		Logger.getInstance().write( Tag.INFO, "Logger initialized correctly." );
	}

	private static void initHomeFolder(){
		File mtgHome = new File( DBCostants.mtgHome );
		if(!mtgHome.exists()) {
			mtgHome.mkdirs();
		}
	}

	private static void initSubFolderStructure(){
		File mtgDataFile = new File( DBCostants.mtgDataHome );
		if(!mtgDataFile.exists()) {
			mtgDataFile.mkdirs();
			Logger.getInstance().write( Tag.INFO, "creating path: " + mtgDataFile.getAbsolutePath() );
		}

		File mtgJsonPath = new File( DBCostants.JSON_PATH );
		if(!mtgJsonPath.exists()) {
			mtgJsonPath.mkdirs();
			Logger.getInstance().write( Tag.INFO, "creating path: " + mtgJsonPath.getAbsolutePath() );
		}

		File mtgDeckPath = new File( DBCostants.DECK_PATH );
		if(!mtgDeckPath.exists()) {
			mtgDeckPath.mkdirs();
			Logger.getInstance().write( Tag.INFO, "creating path: " + mtgDeckPath.getAbsolutePath() );
		}
	}

	private static void initDB() throws Exception{
		StoreManager d = StoreManager.getInstance();
		if(d == null)
			throw new Exception( "Error to open database" );
		Logger.getInstance().write( Tag.INFO, "Store manager initialized correctly." );
	}

	private static void initUIManager(){
		UIManager.put( "swing.boldMetal", Boolean.FALSE );
	}
}
