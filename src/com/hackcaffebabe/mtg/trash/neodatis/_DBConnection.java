package com.hackcaffebabe.mtg.trash.neodatis;

import static it.hackcaffebabe.logger.Tag.*;
import static com.hackcaffebabe.mtg.controller.DBCostants.*;
import java.math.BigInteger;
import java.util.Collection;
import it.hackcaffebabe.logger.Logger;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.ODBRuntimeException;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import com.hackcaffebabe.mtg.model.*;

/**
 * This class manage all cards saved.<br>
 * The root directory of all cards is <program directory>/data/store/
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class _DBConnection
{
	private static _DBConnection connection;
	private Logger log = Logger.getInstance();
	private ODB database;
	
	/**
	 * Returns the instance of Database connection.
	 * @return {@link _DBConnection} the Database connection.
	 * @throws ODBRuntimeException if file is already opened by other program.
	 */
	public static _DBConnection getInstance() throws ODBRuntimeException{
		if(connection==null)
			connection = new _DBConnection();
		return connection;
	}
	
	private _DBConnection() throws ODBRuntimeException {
		this.database = ODBFactory.open( DB_PATH );
		log.write( INFO, "DB opened correctly" );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * 
	 * @param card
	 * @throws IllegalArgumentException
	 */
	public void store( MTGCard card ) throws IllegalArgumentException{
		if( card == null )
			throw new IllegalArgumentException( "Card to store can not to be null." );

		String c = "";
		if( card instanceof Creature ){
			this.database.store( (Creature)card );
			c = Creature.class.toString();
		}else if( card instanceof Artifact ){
			this.database.store( (Artifact)card );
			c = Artifact.class.toString();
		}else if( card instanceof Enchantment ){
			this.database.store( (Enchantment)card );
			c = Enchantment.class.toString();
		}else if( card instanceof Instant ){
			this.database.store( (Instant)card );
			c = Instant.class.toString();
		}else if( card instanceof Land ){
			this.database.store( (Land)card );
			c = Land.class.toString();
		}else if( card instanceof Planeswalker ){
			this.database.store( (Planeswalker)card );
			c = Planeswalker.class.toString();
		}else if( card instanceof Sorcery ){
			this.database.store( (Sorcery)card );
			c = Sorcery.class.toString();
		}
		log.write( INFO, String.format( "Store object of %s", c ) );
	}
	
	
	/**
	 * Close the database connection.
	 */
	public void close(){
		if(database != null){
			database.close();
			log.write( INFO, "DB closed correctly" );
			connection = null;
		}
	}
	
//===========================================================================================
// GETTER
//===========================================================================================
	public <T extends MTGCard> Collection<T> get( Class<T> cardType ) throws IllegalArgumentException{
		if( cardType == null )
			throw new IllegalArgumentException( "Object to get can not be null." );
		
		log.write( INFO, "Retrive class "+cardType+" from DB" );
		return this.database.getObjects( cardType );
	}

	public <T extends MTGCard> BigInteger count( Class<T> cardType ) throws IllegalArgumentException{
		if( cardType == null )
			throw new IllegalArgumentException( "Object to count can not be null." );
		
		log.write( INFO, "Retrive number of class "+cardType+" from DB" );
		return this.database.count( new CriteriaQuery( cardType ) );
	}
}
