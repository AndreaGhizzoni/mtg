package com.hackcaffebabe.mtg.model.color;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Represents the color of MTG card.
 * TODO add doc
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.1
 */
public class _CardColor implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Set<_Mana> colors = new HashSet<>();
	private TypeColor type = null;

	/**
	 * Instance an Color Less card.
	 */
	public _CardColor(){
		this.type = TypeColor.COLOR_LESS;
	}

	/**
	 * Instance a Mono color card.
	 */
	public _CardColor(_Mana c) throws IllegalArgumentException{
		this.checkMana( c );
		this.colors.add( c );
		this.type = TypeColor.MONO_COLOR;
	}

	/**
	 * Instance an Hybrid color card.<br>
	 * These two colors can not be the same and can not be a COLOR_LESS || X || TAP.
	 */
	public _CardColor(_Mana first, _Mana second) throws IllegalArgumentException{
		this.checkMana( first );
		this.checkMana( second );

		if(first.equals( second ))
			throw new IllegalArgumentException( "Mana given can not be the same." );

		this.colors.add( first );
		this.colors.add( second );
		this.type = TypeColor.IBRID;
	}

	/**
	 * Instance an Multicolor card.<br>
	 * These two colors can not be the same and can not be a COLOR_LESS.
	 */
	public _CardColor(List<_Mana> c) throws IllegalArgumentException{
		if(c == null || c.size() == 0)
			throw new IllegalArgumentException( "Mana given can not be null." );

		if(c.contains( _Mana.COLOR_LESS ) && c.contains( _Mana.TAP ) && c.contains( _Mana.X ))
			throw new IllegalArgumentException( "Basics Colors can not be COLOR_LESS with two arguments constructor." );

		for(_Mana i: c)
			this.colors.add( i );
		this.type = TypeColor.MULTI_COLOR;
	}

//===========================================================================================
// METHOD
//===========================================================================================
	private void checkMana(_Mana m) throws IllegalArgumentException{
		if(m == null)
			throw new IllegalArgumentException( "Mana given can not be null." );
		else {
			String msg = null;
			if(m.equals( _Mana.TAP ))
				msg = "Mana malformed for color card: TAP action";
			else if(m.equals( _Mana.X ))
				msg = "Mana malformed for color card: X action";
			else if(m.equals( _Mana.COLOR_LESS ))
				msg = "Mana given can not be COLOR_LESS for CardColor whit this constructor.";

			if(msg != null)
				throw new IllegalArgumentException( msg );
		}
	}

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
	 */
	public final Set<_Mana> getBasicColors(){
		return this.colors;
	}

//===========================================================================================
// OVVERIDE
//===========================================================================================
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(_Mana c: this.colors) {
			String tmp = String.format( "%s/", _Mana.getAbbraviation( c ) );
			builder.append( tmp );
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

		_CardColor other = (_CardColor) obj;
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
