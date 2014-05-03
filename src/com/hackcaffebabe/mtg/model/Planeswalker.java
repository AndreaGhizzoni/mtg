package com.hackcaffebabe.mtg.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.hackcaffebabe.mtg.model.card.Ability;
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.card.PlanesAbility;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


/**
 * Represents the planes walker MTG card.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.2
 */
public class Planeswalker extends MTGCard implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ManaCost cost;
	private int life;
	private Set<PlanesAbility> planesAbility = new HashSet<>();

	/**
	 * Instance a planes walker card.
	 * @param name {@link String} the name of planes walker.
	 * @param cost {@link ManaCost} the mana cost of planes walker.
	 * @param life {@link Integer} the file of planes walker.
	 * @param color {@link CardColor}  the color of planes walker.
	 * @param rarity {@link Rarity} the rarity of planes walker.
	 * @throws IllegalArgumentException if some arguments are null or empty string.
	 */
	public Planeswalker(String name, ManaCost cost, int life, CardColor color, Rarity rarity)
			throws IllegalArgumentException{
		super( name, color, rarity );
		this.setLife( life );
		this.setManaCost( cost );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * Add a planes walker ability.
	 * @param ability {@link PlanesAbility} the planes walker ability.
	 * @throws IllegalArgumentException if argument is null.
	 */
	public void addAbility(PlanesAbility ability) throws IllegalArgumentException{
		if(ability == null)
			throw new IllegalArgumentException( "Planes ability can not be null." );

		this.planesAbility.add( ability );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the Mana cost of Artifact.
	 * @param cost {@link ManaCost} the mana cost of Artifact.
	 * @throws IllegalArgumentException if argument given is null or is TAP action.
	 */
	public void setManaCost(ManaCost cost) throws IllegalArgumentException{
		if(cost == null)
			throw new IllegalArgumentException( "Mana cost of Artifact can not be null." );

		if(cost.containsTAP() || cost.containsX())
			throw new IllegalArgumentException( "Mana cost of Artifact can not be TAP action." );

		this.cost = cost;
	}

	/**
	 * Set the life of planes walker.
	 * @param life {@link Integer} the life of planes walker.
	 * @throws IllegalArgumentException if argument given is equal or less than zero.
	 */
	public void setLife(int life) throws IllegalArgumentException{
		if(life <= 0)
			throw new IllegalArgumentException( "Life of planes walker can not be equal or less than zero." );

		this.life = life;
	}

	public void setPlanesAbilities(Set<PlanesAbility> s){
		this.planesAbility = s;
	}

	@Override
	public void setArtifact(boolean isArtifact){}

	@Override
	public void setPrimaryEffect(String primaryEffect){}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the mana cost of Planes walker.
	 * @return {@link ManaCost} the mana cost of Planes walker.
	 */
	public ManaCost getManaCost(){
		return this.cost;
	}

	/**
	 * Returns a {@link Set} of planes walker ability.
	 * @return {@link Set} of {@link PlanesAbility}
	 */
	public Set<PlanesAbility> getPlanesAbilities(){
		return this.planesAbility;
	}

	/**
	 * Returns the life of planes walker.
	 * @return {@link Integer} the life of planes walker.
	 */
	public int getLife(){
		return this.life;
	}

	@Override
	public boolean isArtifact(){
		return false;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public void addAbility(Ability ability) throws IllegalArgumentException{}

	@Override
	public void addEffect(Effect effect) throws IllegalArgumentException{}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + life;
		result = prime * result + ((planesAbility == null) ? 0 : planesAbility.hashCode());
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
		Planeswalker other = (Planeswalker) obj;
		if(cost == null) {
			if(other.cost != null)
				return false;
		} else if(!cost.equals( other.cost ))
			return false;
		if(life != other.life)
			return false;
		if(planesAbility == null) {
			if(other.planesAbility != null)
				return false;
		} else if(!planesAbility.equals( other.planesAbility ))
			return false;
		return true;
	}

	@Override
	public Object[] getDisplayRow(){
		String color = String.format( "%s %s", getCardColor(), getCardColor().getType() );
		String type = "Planeswalker";
		if(isLegendary())
			type += " Leg.";
		return new Object[] { getName(), color, type, getSubType() == null ? "" : getSubType(), getRarity().toString() };
	}

	@Override
	public String toString(){
		String pattern = "%s [%s %s %s %s - %s %s %s]";
		String type = "Planeswalker";
		if(isLegendary())
			type += " Legendary";
		if(getSubType() != null && !getSubType().isEmpty())
			type += " - " + getSubType();
		return String.format( pattern, getName(), getCardColor(), getCardColor().getType(), getManaCost(), type,
				getLife(), getRarity(), getSeries() );
	}
}
