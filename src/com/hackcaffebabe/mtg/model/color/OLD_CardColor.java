package com.hackcaffebabe.mtg.model.color;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Represents the color of MTG card.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.1
 */
public class OLD_CardColor implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Set<OLD_BasicColors> colors = new HashSet<>();
	private TypeColor type = null;

	/**
	 * Instance an Color Less card.
	 */
	public OLD_CardColor(){
		this.type = TypeColor.COLOR_LESS;
	}

	/**
	 * Instance a Mono color card.
	 * @param c {@link OLD_BasicColors} the basic color.
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public OLD_CardColor(OLD_BasicColors c) throws IllegalArgumentException{
		if(c == null)
			throw new IllegalArgumentException( "Basic color given can not be null." );

		this.colors.add( c );
		this.type = TypeColor.MONO_COLOR;
	}

	/**
	 * Instance an Hybrid color card.<br>
	 * These two colors can not be the same and can not be a COLOR_LESS.
	 * @param first {@link OLD_BasicColors} the first basic color.
	 * @param second {@link OLD_BasicColors} the second basic color.
	 * @throws IllegalArgumentException if arguments given are null.
	 */
	public OLD_CardColor(OLD_BasicColors first, OLD_BasicColors second) throws IllegalArgumentException{
		if(first == null || second == null)
			throw new IllegalArgumentException( "Basics colors given can not be null." );

		if(first == second)
			throw new IllegalArgumentException( "Basics colors can not be the same." );

		if(first == OLD_BasicColors.COLOR_LESS || second == OLD_BasicColors.COLOR_LESS)
			throw new IllegalArgumentException( "Basics Colors can not be COLOR_LESS with two arguments constructor." );

		this.colors.add( first );
		this.colors.add( second );
		this.type = TypeColor.IBRID;
	}

	/**
	 * Instance an Multicolor card.<br>
	 * These two colors can not be the same and can not be a COLOR_LESS.
	 * @param colors all {@link OLD_BasicColors} of Multicolor card.
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public OLD_CardColor(List<OLD_BasicColors> colors) throws IllegalArgumentException{
		if(colors == null || colors.size() == 0)
			throw new IllegalArgumentException( "Basics colors given can not be null." );

		if(colors.contains( OLD_BasicColors.COLOR_LESS ))
			throw new IllegalArgumentException( "Basics Colors can not be COLOR_LESS with two arguments constructor." );

		for(OLD_BasicColors c: colors)
			this.colors.add( c );
		this.type = TypeColor.MULTI_COLOR;
	}

//===========================================================================================
// METHOD
//===========================================================================================

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Return the color type of a card.
	 * @return {@link TypeColor} the color type of a card.
	 */
	public TypeColor getType(){
		return this.type;
	}

	/**
	 * Returns the Basics Colors of a card.
	 * @return {@link Set} of Basics Colors.
	 */
	public final Set<OLD_BasicColors> getBasicColors(){
		return this.colors;
	}

//===========================================================================================
// OVVERIDE
//===========================================================================================
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(OLD_BasicColors b: this.colors) {
			builder.append( OLD_BasicColors.getAbbraviation( b ) + "/" );
		}
		return builder.toString().isEmpty() ? "CL" : builder.deleteCharAt( builder.toString().length() - 1 ).toString();
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colors == null) ? 0 : colors.hashCode());
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

		OLD_CardColor other = (OLD_CardColor) obj;
		if(colors == null) {
			if(other.colors != null)
				return false;
		} else if(!colors.equals( other.colors ))
			return false;
		else if(type != other.type)
			return false;
		return true;
	}
}
