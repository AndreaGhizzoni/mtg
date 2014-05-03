package com.hackcaffebabe.mtg.trash;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents the mana cost of a MTG card.
 * TODO [!!!] figure out how to implement mana cost like this: X.X.R
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class OLD_ManaCost implements Comparable<OLD_ManaCost>, Serializable
{
	private static final long serialVersionUID = 1L;
	private Map<OLD_BasicColors, Integer> cost = new HashMap<>();

	/**
	 * Instance a mana cost with entry of {@link OLD_BasicColors} and 
	 * an {@link Integer} that represents the amount of basic color.<br>
	 * Pass zero arguments to specify the cost equal zero.<br>
	 * Use:<br>
	 * - ( "BasicColor", N ) 		   -> to specify classic mana cost,<br>
	 * - ( BasicColor.COLOR_LESS, -1 ) -> to specify X mana cost,<br> 
	 * - ( null, -1 ) 				   -> to specify the TAP action<br>
	 * 
	 * @param entries {@link Map.Entry} of {@link OLD_BasicColors} and {@link Integer}.
	 * @throws IllegalArgumentException if some argument passed are null.
	 */
	@SafeVarargs
	public OLD_ManaCost(Map.Entry<OLD_BasicColors, Integer>... entries) throws IllegalArgumentException{
		for(Map.Entry<OLD_BasicColors, Integer> e: entries) {
			if(e == null)
				throw new IllegalArgumentException( "Mana cost can not be null." );

			if(e.getValue() == null || e.getValue() < -1)
				throw new IllegalArgumentException( "Amount of mana can not be null or less of zero" );

			if(e.getKey() == null && e.getValue() != -1)
				throw new IllegalArgumentException(
						"(null,%s) is not acceptable, use (null,-1) to indicate TAP action." );

			if(e.getValue() == 0)
				continue;

			this.cost.put( e.getKey(), e.getValue() );
		}
	}

	/**
	 * Instance a mana cost with a {@link Map} of {@link OLD_BasicColors} and 
	 * an {@link Integer} that represents the amount of basic color.<br>
	 * Pass zero arguments to specify the cost equal zero.<br>
	 * Use:<br>
	 * - ( "BasicColor", N ) 		   -> to specify classic mana cost,<br>
	 * - ( BasicColor.COLOR_LESS, -1 ) -> to specify X mana cost,<br>
	 * - ( null, -1 ) 				   -> to specify the TAP action<br>
	 * 
	 * @param map {@link Map} of {@link OLD_BasicColors} and {@link Integer}.
	 * @throws IllegalArgumentException if some argument passed are null.
	 */
	public OLD_ManaCost(Map<OLD_BasicColors, Integer> map) throws IllegalArgumentException{
		for(Map.Entry<OLD_BasicColors, Integer> e: map.entrySet()) {
			if(e == null)
				throw new IllegalArgumentException( "Mana cost can not be null." );

			if(e.getValue() == null || e.getValue() < -1)
				throw new IllegalArgumentException( "Amount of mana can not be null or less of zero" );

			if(e.getKey() == null && e.getValue() != -1)
				throw new IllegalArgumentException(
						"(null,%s) is not acceptable, use (null,-1) to indicate TAP action." );

			if(e.getValue() == 0)
				continue;
		}
		this.cost = map;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the Cost of MTG card mana cost as map of {@link OLD_BasicColors} and {@link Integer}.
	 * e.g (RED, 2) (BLACK, 2) ...
	 * @return {@link Map} of {@link OLD_BasicColors} and {@link Integer}.
	 */
	public final Map<OLD_BasicColors, Integer> getCost(){
		return this.cost;
	}

//===========================================================================================
// METHOD
//===========================================================================================	
	/**
	 * Return the converted mana cost.
	 * @return {@link Integer} the converted mana cost.
	 */
	public int getConvertedManaCost(){
		int res = 0;
		if(this.cost.containsValue( -1 ))
			return res;

		for(Map.Entry<OLD_BasicColors, Integer> e: this.cost.entrySet()) {
			res += e.getValue();
		}
		return res;
	}

//===========================================================================================
// OVVERIDE
//===========================================================================================	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();

		if(this.cost.containsKey( OLD_BasicColors.COLOR_LESS )) {
			if(this.cost.get( OLD_BasicColors.COLOR_LESS ) > -1) {
				builder.append( this.cost.get( OLD_BasicColors.COLOR_LESS ) + "." );
			} else {
				builder.append( "X." );
			}
		}

		for(Map.Entry<OLD_BasicColors, Integer> e: this.cost.entrySet()) {
			if(e.getKey() == null) {
				builder.append( "TAP." );
			} else {
				if(e.getKey() != OLD_BasicColors.COLOR_LESS) {
					String bc = OLD_BasicColors.getAbbraviation( e.getKey() );
					for(int i = 1; i <= e.getValue(); i++)
						builder.append( bc + "." );
				}
			}
		}

		return builder.toString().isEmpty() ? "0" : builder.deleteCharAt( builder.toString().length() - 1 ).toString();
	}

	/**
	 * Compare the converted mana cost.
	 */
	@Override
	public int compareTo(OLD_ManaCost o){
		int cmthis = this.getConvertedManaCost();
		int cmo = o.getConvertedManaCost();

		if(cmthis < cmo)
			return -1;
		else if(cmthis > cmo)
			return 1;
		else return 0;
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;

		OLD_ManaCost other = (OLD_ManaCost) obj;
		if(cost == null) {
			if(other.cost != null)
				return false;
		} else if(this.getConvertedManaCost() != other.getConvertedManaCost())
			return false;
		return true;
	}
}