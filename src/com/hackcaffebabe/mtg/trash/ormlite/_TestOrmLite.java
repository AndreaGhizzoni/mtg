package com.hackcaffebabe.mtg.trash.ormlite;

import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Ability;
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.card.PlanesAbility;
import com.hackcaffebabe.mtg.model.color.CardColor;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;

/**
 * 
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class _TestOrmLite
{
	public static void main( String[] args ){
		try{
			initLogger();
			@SuppressWarnings( "unused" )
			_DBManager db = initDB();
			
			
		}catch( Exception e ){
			Logger.getInstance().write( Tag.ERRORS, e.getMessage() );
			e.printStackTrace();
		}
	}
	
	private static void initLogger(){
		Logger.getInstance();
	}
	
	private static _DBManager initDB() throws Exception{
		_DBManager d = _DBManager.getInstance();
		if( d == null ) 
			throw new Exception( "Error to open database" );
		 
		Logger.getInstance().write( Tag.INFO, "Database opened correctly" );
		d.initDB( CardColor.class, Ability.class, Effect.class, PlanesAbility.class, MTGCard.class, Creature.class );
		Logger.getInstance().write( Tag.INFO, "Database initialized correctly" );
		
		return d;
	}
}
