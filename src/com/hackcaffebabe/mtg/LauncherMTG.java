package com.hackcaffebabe.mtg;

import java.io.File;
import java.io.PrintStream;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.gui.frame.*;
import com.hackcaffebabe.mtg.controller.json.StoreManager;


/**
 * Class to launch the program
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 *
 */
public class LauncherMTG
{
	public static void main(String... args){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				try {
					initLogger();
					initDB();
					initUIManager();
					new MTG().setVisible( true );
				}
				catch(Exception e) {
					String s = String.format( "%s\nLog is reported.", e.getMessage() );
					Logger.getInstance().write( Tag.ERRORS, e.getMessage() );
					JOptionPane.showMessageDialog( null, s, "Error", JOptionPane.ERROR_MESSAGE );
					e.printStackTrace();
				}
			}
		} );
	}

	private static void initLogger() throws Exception{
		Logger.getInstance();
		if(DBCostants.DB_LOG_ON_FILE) {
			Logger.getInstance().setPrintStream( new PrintStream( new File( DBCostants.LOG_PATH ) ) );
			Logger.getInstance().disableTag( Tag.DEBUG );
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
