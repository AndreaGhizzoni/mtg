package com.hackcaffebabe.mtg.trash.ormlite;

import static com.hackcaffebabe.mtg.controller.DBCostants.*;
import it.hackcaffebabe.logger.Logger;
import java.sql.SQLException;

/**
 * 
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class _DBManager
{
	private static _DBManager manager;
	private _DBConnection connection;
	
	private Logger log = Logger.getInstance();
	
	/**
	 * Return the instance of database connection.
	 * @return {@link _DBManager} or null if database is not opened correctly.
	 */
	public static _DBManager getInstance(){
		try{
			if(manager==null)
				manager = new _DBManager();
			return manager;
		}catch( SQLException e ){
			return null;
		}
	}
	
	private _DBManager() throws SQLException{ this.initDB(); }
	
//====================================================================================================
// METHODS	
//====================================================================================================
	/** Initialize database */
	private void initDB() throws SQLException{
		connection = new _DBConnection( DB_PATH, DB_LOG_ON_FILE );
	}
	
	
	/**
	 * This method close the connection with database.
	 * @throws SQLException if there are statement running.
	 */
	public void close() throws SQLException{
		if(connection!=null){
			connection.closeConnection();
			connection=null;
			manager=null;
		}
	}
	
	/**
	 * This method Initialize the Database by creating all Tables of classes given.
	 * Note: Initialization DOES NOT re-creates the existing tables.
	 * @throws SQLException if there are some issuers with creation statement.
	 * @throws IllegalArgumentException if some classes given are null or empty.
	 */
	public void initDB( Class<?>... classes ) throws IllegalArgumentException, SQLException{
		if( classes == null || classes.length == 0 )
			throw new IllegalArgumentException( "List of class can not be null or empty." );
		connection.setup( classes );
	}
	
//====================================================================================================
// SETTER
//====================================================================================================

//====================================================================================================
// GETTER	
//====================================================================================================
}
