package main.java.com.hackcaffebabe.mtg.model;

import java.io.Serializable;
import main.java.com.hackcaffebabe.mtg.model.card.Rarity;
import main.java.com.hackcaffebabe.mtg.model.card.Strength;
import main.java.com.hackcaffebabe.mtg.model.color.CardColor;
import main.java.com.hackcaffebabe.mtg.model.cost.ManaCost;


/**
 * Represents the creature card in MTG game.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class Creature extends MTGCard implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ManaCost cost;
	private Strength strength;

	/**
	 * Instance a MTG Creature card with all his fields.
	 * @param name {@link String} the name of Creature.
	 * @param color {@link CardColor} the color of Creature.
	 * @param strength {@link Strength} the strength of Creature. 
	 * @param cost {@link ManaCost} the mana cost of Creature.
	 * @param subType {@link String} the type's name of Creature.
	 * @param rarity {@link Rarity} the rarity of Creature.
	 * @throws IllegalArgumentException if some arguments are null, 
	 *                                  empty string or mana cost is TAP action.
	 */
	public Creature(String name, CardColor color, Strength strength, ManaCost cost, String subType, Rarity rarity)
			throws IllegalArgumentException{
		super( name, color, rarity );
		this.setManaCost( cost );
		this.setSubType( subType );
		this.setStrength( strength );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the creature Mana cost.
	 * @param cost {@link ManaCost} the cost of mana.
	 * @throws IllegalArgumentException if argument given is null or is a TAP action.
	 */
	public void setManaCost(ManaCost cost) throws IllegalArgumentException{
		if(cost == null)
			throw new IllegalArgumentException( "Mana Cost of Creature can not be null." );

		if(cost.containsTAP())
			throw new IllegalArgumentException( "Mana cost of Enchantment can not be TAP action." );

		this.cost = cost;
	}

	/**
	 * Set the creature's strength.
	 * @param strength {@link Strength} the creature's strength.
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public void setStrength(Strength strength) throws IllegalArgumentException{
		if(strength == null)
			throw new IllegalArgumentException( "Strength of creature can not be null." );

		this.strength = strength;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the mana cost of creature.
	 * @return {@link ManaCost} the mana cost of creature.
	 */
	public ManaCost getManaCost(){
		return this.cost;
	}

	/**
	 * Returns the creature's strength.
	 * @return {@link Strength} the creature's strength.
	 */
	public Strength getStrength(){
		return this.strength;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((strength == null) ? 0 : strength.hashCode());
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
		Creature other = (Creature) obj;
		if(cost == null) {
			if(other.cost != null)
				return false;
		} else if(!cost.equals( other.cost ))
			return false;
		if(strength == null) {
			if(other.strength != null)
				return false;
		} else if(!strength.equals( other.strength ))
			return false;
		return true;
	}

	public Object[] getDisplayRow(){
		String color = String.format( "%s %s", getCardColor(), getCardColor().getType() );
		StringBuilder type = new StringBuilder();
		type.append( "Creature" );
		if(isArtifact())
			type.append( " Art." );
		if(isLegendary())
			type.append( " Leg." );
		return new Object[] { getName(), color, type.toString(), getRarity().toString() };
	}

	@Override
	public String toString(){
		String pattern = "%s [%s %s %s %s - %s %s %s]";
		StringBuilder type = new StringBuilder();
		type.append( "Creature" );
		if(isArtifact())
			type.append( " Art." );
		if(isLegendary())
			type.append( " Leg." );
		if(getSubType() != null && !getSubType().isEmpty())
			type.append( String.format( " - %s", getSubType() ) );
		return String.format( pattern, getName(), getCardColor(), getCardColor().getType(), getManaCost(),
				type.toString(), getStrength(), getRarity(), getSeries() );
	}
}
