package com.hackcaffebabe.mtg.model;

import java.io.Serializable;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


/**
 * Represents the Enchantment MTG card type.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class Enchantment extends MTGCard implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ManaCost cost;

	/**
	 * Instance a Enchantment with all filed.
	 * @param name {@link String} the name of Enchantment.
	 * @param cost {@link ManaCost} the mana cost of Enchantment.
	 * @param color {@link CardColor} the color of Enchantment.
	 * @param rarity {@link Rarity} the rarity of Enchantment.
	 * @throws IllegalArgumentException if some arguments are null, 
	 *                                  empty string or mana cost is TAP action.
	 */
	public Enchantment(String name, ManaCost cost, CardColor color, Rarity rarity) throws IllegalArgumentException{
		super( name, color, rarity );
		this.setManaCost( cost );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the Mana cost of Enchantment.
	 * @param cost {@link ManaCost} the mana cost of Enchantment.
	 * @throws IllegalArgumentException if argument given is null or is TAP action.
	 */
	public void setManaCost(ManaCost cost) throws IllegalArgumentException{
		if(cost == null)
			throw new IllegalArgumentException( "Mana cost of Enchantment can not be null." );

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
	 * Returns the mana cost of Enchantment.
	 * @return {@link ManaCost} the mana cost of Enchantment.
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
		Enchantment other = (Enchantment) obj;
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
		String type = "Enchantment";
		if(isLegendary())
			type += " Leg.";
		return new Object[] { getName(), color, type, getSubType() == null ? "" : getSubType(), getRarity().toString() };
	}

	@Override
	public String toString(){
		String pattern = "%s [%s %s %s %s - %s %s]";
		String type = "Enchantment";
		if(isLegendary())
			type += " Legendary";
		if(getSubType() != null && !getSubType().isEmpty())
			type += " - " + getSubType();
		return String.format( pattern, getName(), getCardColor(), getCardColor().getType(), getManaCost(), type,
				getRarity(), getSeries() );
	}
}
