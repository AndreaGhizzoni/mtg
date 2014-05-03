package com.hackcaffebabe.mtg.model;

import java.io.Serializable;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


/**
 * Represents the sorcery sorcery card in MTG game.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class Sorcery extends MTGCard implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ManaCost cost;

	/**
	 * Instance a MTG sorcery card with all fields.
	 * @param name {@link String} the name of sorcery.
	 * @param cost {@link ManaCost} the name cost of sorcery.
	 * @param color {@link CardColor} the card color of sorcery.
	 * @param rarity {@link Rarity} the rarity of sorcery.
	 * @throws IllegalArgumentException if some argument are null, 
	 *                                  empty string or mana cost is TAP action.
	 */
	public Sorcery(String name, ManaCost cost, CardColor color, Rarity rarity) throws IllegalArgumentException{
		super( name, color, rarity );
		this.setManaCost( cost );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the sorcery Mana cost.
	 * @param cost {@link ManaCost} the cost of mana.
	 * @throws IllegalArgumentException if argument given is null or is TAP action.
	 */
	public void setManaCost(ManaCost cost) throws IllegalArgumentException{
		if(cost == null)
			throw new IllegalArgumentException( "Mana Cost of sorceryt can not be null." );

		if(cost.containsTAP())
			throw new IllegalArgumentException( "Mana cost of Enchantment can not be TAP action." );

		this.cost = cost;
	}

	@Override
	public void setArtifact(boolean isArtifact){}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the mana cost of sorcery.
	 * @return {@link ManaCost} the mana cost of sorcery.
	 */
	public ManaCost getManaCost(){
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
		Sorcery other = (Sorcery) obj;
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
		String type = "Sorcery";
		if(isLegendary())
			type += " Leg";
		return new Object[] { getName(), color, type, getSubType() == null ? "" : getSubType(), getRarity().toString() };
	}

	@Override
	public String toString(){
		String pattern = "%s [%s %s %s %s - %s %s]";
		String type = "Sorcery";
		if(isLegendary())
			type += " Legendary";
		if(getSubType() != null && !getSubType().isEmpty())
			type += " - " + getSubType();
		return String.format( pattern, getName(), getCardColor(), getCardColor().getType(), getManaCost(), type,
				getRarity(), getSeries() );
	}
}
