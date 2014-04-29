package com.hackcaffebabe.mtg.model;

import java.io.Serializable;
import com.hackcaffebabe.mtg.model.card.OLD_ManaCost;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.OLD_CardColor;


/**
 * Represents the Instant sorcery card in MTG game.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class Instant extends MTGCard implements Serializable
{
	private static final long serialVersionUID = 1L;
	private OLD_ManaCost cost;

	/**
	 * Instance a MTG Instant card with all fields.
	 * @param name {@link String} the name of instant.
	 * @param cost {@link OLD_ManaCost} the name cost of instant.
	 * @param color {@link OLD_CardColor} the card color of instant.
	 * @param rarity {@link Rarity} the rarity of instant.
	 * @throws IllegalArgumentException if some argument are null, 
	 *                                  empty string or mana cost is TAP action.
	 */
	public Instant(String name, OLD_ManaCost cost, OLD_CardColor color, Rarity rarity) throws IllegalArgumentException{
		super( name, color, rarity );
		this.setManaCost( cost );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the Instant Mana cost.
	 * @param cost {@link OLD_ManaCost} the cost of mana.
	 * @throws IllegalArgumentException if argument given is null or is TAP action.
	 */
	public void setManaCost(OLD_ManaCost cost) throws IllegalArgumentException{
		if(cost == null)
			throw new IllegalArgumentException( "Mana Cost of Instant can not be null." );

		if(cost.getCost().containsKey( null ))
			throw new IllegalArgumentException( "Mana cost of Enchantment can not be TAP action." );

		this.cost = cost;
	}

	@Override
	public void setArtifact(boolean isArtifact){}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the mana cost of Instant.
	 * @return {@link OLD_ManaCost} the mana cost of Instant.
	 */
	public OLD_ManaCost getManaCost(){
		return this.cost;
	}

	@Override
	public boolean isArtifact(){
		return false;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(!super.equals( obj ))
			return false;
		if(getClass() != obj.getClass())
			return false;
		Instant other = (Instant) obj;
		if(cost == null) {
			if(other.cost != null)
				return false;
		} else if(!cost.equals( other.cost ))
			return false;
		return true;
	}

	@Override
	public Object[] getDisplayRow(){
		String color = String.format( "%s %s", getCardColor(), getCardColor().getType() );
		String type = "Instant";
		if(isLegendary())
			type += " Leg.";
		return new Object[] { getName(), color, type, getSubType() == null ? "" : getSubType(), getRarity().toString() };
	}

	@Override
	public String toString(){
		String pattern = "%s [%s %s %s %s - %s %s]";
		String type = "Instant";
		if(isLegendary())
			type += " Legendary";
		if(getSubType() != null && !getSubType().isEmpty())
			type += " - " + getSubType();
		return String.format( pattern, getName(), getCardColor(), getCardColor().getType(), getManaCost(), type,
				getRarity(), getSeries() );
	}
}
