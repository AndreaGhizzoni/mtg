package com.hackcaffebabe.mtg.trash.neodatis;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.Instant;
import com.hackcaffebabe.mtg.model.card.ManaCost;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.card.Strength;
import com.hackcaffebabe.mtg.model.color.BasicColors;
import com.hackcaffebabe.mtg.model.color.CardColor;


public class _Test
{
	public static void main( String... args ){
		_DBConnection.getInstance();
	}

	public static void testDBcount(){
		try{
			_DBConnection c = _DBConnection.getInstance();
			
			System.out.println( c.count( Creature.class ) );
			System.out.println( c.count( Instant.class ) );

			c.close();
		}catch( Exception e ){
			e.printStackTrace();
		}
	}	
	
	public static void testDBget(){
		try{
			_DBConnection c = _DBConnection.getInstance();
			
			Collection<Instant> lI = c.get( Instant.class );
			Iterator<Instant> itI = lI.iterator();
			while( itI.hasNext() ){
				System.out.println( itI.next().toString() );
			}
			
			Collection<Creature> lC = c.get( Creature.class );
			Iterator<Creature> itC = lC.iterator();
			while( itC.hasNext() ){
				System.out.println( itC.next().toString() );
			}
			
			c.close();
		}catch( Exception e ){
			e.printStackTrace();
		}		
	}
	
	
	public static void testDBstore(){
		try{
			_DBConnection c = _DBConnection.getInstance();
			
			c.store( getInstant() );
			c.store( getCreature( "Predatore Fulminante", "3/3", "Bestia Zombie" ) );
			
			c.close();
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
		
	
	public static Instant getInstant(){
		Map.Entry<BasicColors, Integer> cl = 
				new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.COLOR_LESS, 1 );
		Map.Entry<BasicColors, Integer> red = 
				new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.RED, 1 );
		
		return new Instant( "Shock Improvviso", new ManaCost( cl, red ), new CardColor( BasicColors.RED ), Rarity.NON_COMMON );
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
		
		return new Creature( name, color, str, cost, type, rarity );		
	}
}
