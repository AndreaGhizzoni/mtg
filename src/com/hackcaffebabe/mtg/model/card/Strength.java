package com.hackcaffebabe.mtg.model.card;

import java.io.Serializable;
import java.util.StringTokenizer;


/**
 * This class represents the creature's strength of MTG Card Game.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Strength implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int power;
	private int toughness;
		
	/**
	 * Instance the strength of Creatures.<br>
	 * Pass -1 as argument to set power or toughness to X.
	 * @param power {@link Integer} the power of creature.
	 * @param toughness {@link Integer} the toughness of creature.
	 * @throws IllegalArgumentException if any arguments are less of -1.
	 */
	public Strength( int power, int toughness ) throws IllegalArgumentException{
		this.setPower( power );
		this.setToughness( toughness );
	}
	
	
	/**
	 * Instance the creature's strength as a string in format "power/toughness" 
	 * where p is the power and t is the toughness.
	 * @param strength {@link String} in format "p/t".
	 * @throws IllegalArgumentException if argument given is null, empty string, or p/t are not digits.
	 */
	public Strength( String strength ) throws IllegalArgumentException {
		if( strength == null || strength.isEmpty() )
			throw new IllegalArgumentException( "Strength given can not be null or empty string" );
		
		StringTokenizer token = new StringTokenizer( strength, "/" );
		int p,t;
		try{
			p = new Integer( token.nextToken() );
			t = new Integer( token.nextToken() );			
		}catch( Exception e ){
			throw new IllegalArgumentException( "Strength string is not in format \"p/t\"" );
		}
		this.setPower( p );
		this.setToughness( t );
	}
	
	
//===========================================================================================
// METHOD
//===========================================================================================
//	/**
//	 * This method compare two Creature Power in case of fight.<br>
//	 * If is returns:<br>
//	 * -  0: means that if two creatures fight together, none dies.<br>
//	 * -  1: means that if two creatures fight together, the first win and the second creature dies.<br>
//	 * -  2: means that if two creatures fight together, the second win and the first creature dies.<br>
//	 * - -1: means that if two creatures fight together, each creatures dies.
//	 * 
//	 * @param o {@link Strength} the power of the second creature.
//	 * @return {@link Integer} represents the result of fight.
//	 */
//	public int fight( Strength o ){
//		if( power == o.power && toughness == o.toughness && power == toughness && o.power == o.toughness )
//			return -1;
//		else if( power < o.toughness && o.power < toughness )
//				return 0;
//		else if( power >= o.toughness && o.power < toughness )
//				return 1;
//		else return 2;// if( power < o.toughness && o.power >= toughness )
//	}
	
	
//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the power of Creature.
	 * @param power {@link Integer} the power of Creature.
	 * @throws IllegalArgumentException if argument given is less of -1.
	 */
	public void setPower( int power ) throws IllegalArgumentException{
		if( power < -1 )
			throw new IllegalArgumentException( "The power can not be less of zero." );
		
		this.power = power;
	}
	
	
	/**
	 * Set the toughness of Creature.
	 * @param toughness {@link Integer} the toughness of Creature.
	 * @throws IllegalArgumentException if argument given is less of -1.
	 */
	public void setToughness( int toughness ) throws IllegalArgumentException{
		if( toughness <-1 )
			throw new IllegalArgumentException( "The toughness can not be less of zero." );
		
		this.toughness = toughness;
	}
	
	
//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the Power of Creature.
	 * @return {@link Integer} the Power of Creature.
	 */
	public final int getPower(){ return this.power; }
	
	/**
	 * Returns the Toughness of Creature.
	 * @return {@link Integer} the Toughness of Creature.
	 */
	public final int getToughness(){ return this.toughness; }
	
	
//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public String toString(){
		return String.format( "%s/%s", power == -1 ? "*": power, 
				                       toughness == -1 ? "*" : toughness );
	}
}


