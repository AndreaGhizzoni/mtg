package com.hackcaffebabe.mtg;

import java.io.File;
import java.io.PrintStream;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.controller.ser.StoreManager;
import com.hackcaffebabe.mtg.gui.frame.*;

/**
 * Class to launch the program
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 *
 */
public class LauncherMTG{
	public static void main( String... args ){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				try{
					initLogger();
					initDB();
					new MTG().setVisible( true );
				}catch( Exception e ){
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
		if(DBCostants.DB_LOG_ON_FILE){
			Logger.getInstance().setPrintStream( new PrintStream( new File( DBCostants.LOG_PATH ) ) );
			Logger.getInstance().disableTag( Tag.DEBUG );
		}
	}
	
	private static void initDB() throws Exception{
		StoreManager d = StoreManager.getInstance();
		if( d == null ) throw new Exception( "Error to open database" );
		Logger.getInstance().write( Tag.INFO, "Database opened and initialized correctly" );
	}
}
