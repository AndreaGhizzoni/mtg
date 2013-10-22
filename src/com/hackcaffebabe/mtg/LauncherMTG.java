package com.hackcaffebabe.mtg;

import static com.hackcaffebabe.mtg.gui.GUIUtils.displayError;
import java.io.File;
import java.io.PrintStream;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.gui.frame.*;
import com.hackcaffebabe.mtg.controller.json.StoreManager;


/**
 * asd
 * Class to launch the program
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class LauncherMTG
{
	public static void main(String... args){
		try {
			initLogger();
			initDB();
			initUIManager();
			SwingUtilities.invokeLater( new Runnable(){
				@Override
				public void run(){
					new MTG().setVisible( true );
				}
			} );
			
		}
		catch(Exception e) {
			String s = String.format( "%s\nLog is reported.", e.getMessage() );
			Logger.getInstance().write( Tag.ERRORS, e.getMessage() );
			displayError( null, s );
			e.printStackTrace(Logger.getInstance().getPrintStream());
		}
	}

	private static void initLogger() throws Exception{
		Logger.getInstance();
		if(DBCostants.DB_LOG_ON_FILE) {
			Logger.getInstance().setPrintStream( new PrintStream( new File( DBCostants.LOG_PATH ) ) );
			Logger.getInstance().disableTag( Tag.DEBUG );
		}
		Logger.getInstance().write( Tag.INFO, "Logger initialized correctly." );
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
