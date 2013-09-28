package com.hackcaffebabe.mtg.trash.ormlite;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.TableUtils;


/**
 * ORM class using ORMLite
 * 
 * 
 * Copyright © 2012. All rights reserved. 
 * @author Andrea Ghizzoni
 *
 */
public class _DBConnection
{
	private static final String LOG_FOLDER = "log";
	private static final String LOG_FILE = "db.log";
	private static final String LOG_PATH = LOG_FOLDER+System.getProperties().getProperty( "file.separator" )+LOG_FILE;
	
	private JdbcConnectionSource connectionSource;
	private String dbPath;

	
	/**
	 * Instance an object to make persistent the object using ORMLite and SQLite.
	 * This class generates log for information and debugging. 
	 * If logOnFile is true the log is written on log/db.log file, otherwise is written on standard out.
	 * 
	 * @param dbPath {@link String} the path of Database.
	 * @param logOnFile {@link Boolean} true means that the log is written on file, otherwise is on standard out.
	 * @throws IllegalArgumentException {@link Exception} if argument given is null or empty.
	 * @throws SQLException {@link Exception} if there are errors executing the sql statement.
	 */
	public _DBConnection( String dbPath, boolean logOnFile ) throws IllegalArgumentException, SQLException{
		if( logOnFile )
			this.initLogFolder();
		
		this.setPath( dbPath );
		this.connectionSource = new JdbcConnectionSource( "jdbc:sqlite:"+this.dbPath );
	}
	
//====================================================================================================
// METHODS	
//====================================================================================================
	/**
	 * Initialize log folder.
	 */
	private void initLogFolder(){
		if( !new File( _DBConnection.LOG_FOLDER ).exists() )
			new File( _DBConnection.LOG_FOLDER ).mkdir();
		
		System.setProperty( LocalLog.LOCAL_LOG_FILE_PROPERTY, LOG_PATH );
	}
	
	
	/**
	 * This method creates the table (if it doesen't exists ) from class given.
	 * @param list {@link Class} of class to creates the appropriate database tables.
	 * @throws SQLException {@link Exception} if sql statement fail.
	 */
	public void setup( Class<?> ... list ) throws IllegalArgumentException, SQLException{
		for( Class<?> c: list )
			TableUtils.createTableIfNotExists( this.connectionSource, c );		
	}
	
	
	/**
	 * USE <code>setup()</code> method before this method.
	 * 
	 * This method make one Object persistent on set DataBase.
	 * If object given already exists, it's throws SQLException.
	 * 
	 * @param obj {@link Object} the Object to make persistent.
	 * @param I {@link <I>} class of Primary key.
	 * @throws SQLException {@link Exception} if the queries returns an error 
	 * ( i.e. if data type specified in the class doesn't match with database type ).
	 */
	@SuppressWarnings( "unchecked" )
	public <T,I> void makePersistent( Object obj, Class<I> I ) throws SQLException{
		Dao<T, I> dao = (Dao<T, I>) DaoManager.createDao( this.connectionSource, obj.getClass() );
		dao.create( (T)obj );
	}
	
	
	/**
	 * This method updates a single field of Object given.
	 * @param objectClass {@link Class} class of object that contains the field to update.
	 * @param primaryKeyValue {@link Integer} the primary key value of Object into the database.
	 * @param fieldToUpdate {@link String} the name of updating field.
	 * @param updates {@link Object} the new value of field. Set this at null to sets the NULL value into the database.
	 * @throws SQLException {@link Exception} if there are some errors while the executing the sql statement.
	 */
	@SuppressWarnings( "unchecked" )
	public <T,I> void updateObject( Class<I> objectClass, Integer primaryKeyValue, 
									String fieldToUpdate, Object updates ) throws SQLException{
		Dao<T, I> dao = (Dao<T, I>) DaoManager.createDao( this.connectionSource, objectClass );
		UpdateBuilder<T, I> updateBuilder = dao.updateBuilder();
		updateBuilder.updateColumnValue( fieldToUpdate, updates );
		updateBuilder.where().eq( "id", primaryKeyValue );		
		updateBuilder.update();
	}
		
	
	/**
	 * This method delete specific row of table with his primary key value.
	 * @param objectClass {@link Class} class representing the table of object.
	 * @param primaryKeyValue {@link Integer} the primary key of table.
	 * @throws SQLException {@link Exception} if there are some errors while the executing the sql statement.
	 */
	@SuppressWarnings( "unchecked" )
	public <T,I> void deleteObject( Class<I> objectClass, Integer primaryKeyValue ) throws SQLException{
		Dao<T,I> dao = (Dao<T, I>) DaoManager.createDao( this.connectionSource, objectClass );
		DeleteBuilder<T, I> deleteBuilder = dao.deleteBuilder();
		deleteBuilder.where().eq( "id", primaryKeyValue );		
		deleteBuilder.delete();
	}
	
	
	/**
	 * This method close all connection statement to/from database.
	 * @throws SQLException {@link Exception} if some transactions are not committed or they are still running.
	 */
	public void closeConnection() throws SQLException{
		this.connectionSource.close();
	}
	
	
//====================================================================================================
// SETTER
//====================================================================================================
	/**
	 * Sets the Database file path.
	 */
	private void setPath( String path ) throws IllegalArgumentException{
		if( path == null || path.isEmpty() )
			throw new IllegalArgumentException( "Path can not be null or empty." );
		
		this.dbPath = path;
	}
	
	
//====================================================================================================
// GETTER
//====================================================================================================
	/**
	 * This method takes objects from the database of the same class specified as an argument with idFieldName == id.
	 * @param obj {@link Class} of objects to takes from the Database.
	 * @param idFieldName {@link String} of database id field name.
	 * @param id {@link Integer} of object in the database to take.
	 * @return {@link List} of <T> Objects where T is the class specified as an argument.
	 * @throws SQLException {@link Exception} if sql statement fail or database field doesn't exists.
	 */
	public <T,I> List<T> getObjectsFromId( Class<T> obj, String idFieldName, Integer id ) throws SQLException{
		Dao<T, I> dao = DaoManager.createDao( this.connectionSource, obj );
		return dao.queryBuilder().where().eq( idFieldName, id.toString() ).query();
	}
	
	
	/**
	 * This method takes objects from the database of the same class specified as an argument with fieldName == value. 
	 * @param obj {@link Class} class of Object to takes from Database.
	 * @param fieldName {@link String} field of Database's Object.
	 * @param value {@link String} the value of fieldName.
	 * @return {@link List} of <T> Objects where T is the class specified as an argument.
	 * @throws SQLException {@link Exception} if sql statement fail or database field doesn't exists.
	 */
	public <T,I> List<T> getObjectsFromField( Class<T> obj, String fieldName, String value ) throws SQLException{
		Dao<T, I> dao = DaoManager.createDao( this.connectionSource, obj );
		return dao.queryBuilder().where().eq( fieldName, value ).query();
	}
	
	
	/**
	 * This method returns all <T> Object into the Database.
	 * @param obj {@link Class<T>} class of object to gets into Database.
	 * @return {@link List<T>} list of <T> objects gets from Database.
	 * @throws SQLException {@link Exception} if there are errors while the sql statement.
	 */
	public <T,I> List<T> getObjects( Class<T> obj ) throws SQLException{
		Dao<T, I> dao = DaoManager.createDao( this.connectionSource, obj );
		return dao.queryForAll();
	}
}
