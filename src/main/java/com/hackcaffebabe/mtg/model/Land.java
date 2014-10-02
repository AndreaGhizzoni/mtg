package com.hackcaffebabe.mtg.model;

import java.io.Serializable;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.CardColor;


/**
 * Represents the Land card in MTG game.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class Land extends MTGCard implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Instance a MTG Land card with all his fields.
	 * @param name {@link String} the name of Land.
	 * @param rarity {@link Rarity} the rarity of Land.
	 * @throws IllegalArgumentException if some argument are null or empty string.
	 */
	public Land(String name, Rarity rarity) throws IllegalArgumentException{
		super( name, new CardColor(), rarity );
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public Object[] getDisplayRow(){
		String color = String.format( "%s %s", getCardColor(), getCardColor().getType() );
		StringBuilder type = new StringBuilder();
		type.append( "Land" );
		if(isArtifact())
			type.append( " Art." );
		if(isLegendary())
			type.append( " Leg." );
		return new Object[] { getName(), color, type.toString(), getRarity().getFancy() };
	}

	@Override
	public String toString(){
		String pattern = "%s [%s %s %s - %s %s]";
		StringBuilder type = new StringBuilder();
		type.append( "Land" );
		if(isArtifact())
			type.append( " Art." );
		if(isLegendary())
			type.append( " Leg." );
		if(getSubType() != null && !getSubType().isEmpty())
			type.append( String.format( " - %s", getSubType() ) );
		return String.format( pattern, getName(), getCardColor(), getCardColor().getType(), type.toString(),
				getRarity(), getSeries() );
	}
}
