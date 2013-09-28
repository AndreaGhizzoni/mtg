package com.hackcaffebabe.mtg.trash;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import com.hackcaffebabe.mtg.controller.ser.StoreManager;
import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.card.Ability;
import com.hackcaffebabe.mtg.model.card.AbilityFactory;
import com.hackcaffebabe.mtg.model.card.ManaCost;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.card.Strength;
import com.hackcaffebabe.mtg.model.color.BasicColors;
import com.hackcaffebabe.mtg.model.color.CardColor;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;

/**
 * 
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class _TestStore
{
	public static void main( String[] args ){
		try{
			initLogger();
			StoreManager s=initDB();
			
			//s.store( getCreature( "LOL", "2/2", "Goblin" ) );
			System.out.println( s.getAllCards().toString() );
			
		}catch( Exception e ){
			Logger.getInstance().write( Tag.ERRORS, e.getMessage() );
			e.printStackTrace();
		}
	}

	
	private static void initLogger(){ Logger.getInstance(); }
	
	private static StoreManager initDB() throws Exception{
		StoreManager d = StoreManager.getInstance();
		if( d == null ) throw new Exception( "Error to open database" );
		Logger.getInstance().write( Tag.INFO, "Database opened and initialized correctly" );
		return d;
	}
	
	
	public static Creature getCreature( String name, String strength, String type ){
		Map.Entry<BasicColors, Integer> cl = 
				new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.COLOR_LESS, 3 );
		Map.Entry<BasicColors, Integer> black = 
				new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.BLACK, 1 );
		Map.Entry<BasicColors, Integer> red = 
				new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.RED, 1 );
		ManaCost cost = new ManaCost( cl,red,black );
		
		Rarity rarity = Rarity.RARE;
		
		CardColor color = new CardColor( Arrays.asList( BasicColors.RED, BasicColors.BLACK ) );
		
		Strength str = new Strength( strength );
		
		String d = AbilityFactory.getInstance().getAbilities().get( "Volare" );
		Ability ability = new Ability( "Volare", d );
		
		Creature c = new Creature( name, color, str, cost, type, rarity );		
		c.addAbility( ability );
		c.setSeries( "m14" );
		return c;
	}
}
