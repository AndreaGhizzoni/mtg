package com.hackcaffebabe.mtg.model;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.jx.table.model.DisplayableObject;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.model.card.Ability;
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.card.PlanesAbility;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.CardColor;


/**
 * Abstract class that represents the MTG card.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public abstract class MTGCard extends DisplayableObject implements Serializable
{
	private static final long serialVersionUID = 1L;

	//These fields are used for equal and hash code.
	private String name;
	private CardColor color;
	private Rarity rarity;
	private String series;
	private String subType;
	private boolean isLegendary = false;
	private boolean isArtifact = false;

	private String primaryEffect;
	private Set<Effect> effects = new HashSet<>();
	private Set<Ability> abilitis = new HashSet<>();

	/**
	 * Creates the common information of MTG Card.
	 * @param name {@link String} the name of card.
	 * @param color {@link CardColor} the color of of card.
	 * @param rarity {@link Rarity} the rarity of card.
	 * @throws IllegalArgumentException if some argument are null or empty string.
	 */
	public MTGCard(String name, CardColor color, Rarity rarity) throws IllegalArgumentException{
		super( MTGCard.class );
		setColumnNames( new String[] { "Name", "Card Color", "Type", "Sub Type", "Rarity" } );

		this.setName( name );
		this.setCardColor( color );
		this.setRarity( rarity );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * This method adds an ability at MTG Card.<b>
	 * The ability can not to be added on {@link Planeswalker}, 
	 * for this you need to use {@link PlanesAbility}.
	 * @param ability {@link Ability} the ability to add.
	 * @throws IllegalArgumentException if argument is null.
	 */
	public void addAbility(Ability ability) throws IllegalArgumentException{
		if(ability == null)
			throw new IllegalArgumentException( "Ability to add can not be null" );

		this.abilitis.add( ability );
	}

	/**
	 * This method adds an Effect at MTG Card.<br>
	 * The ability can not to be added on {@link Planeswalker}.
	 * @param effect {@link Effect} the effect to add.
	 * @throws IllegalArgumentException if argument is null.
	 */
	public void addEffect(Effect effect) throws IllegalArgumentException{
		if(effect == null)
			throw new IllegalArgumentException( "Effect to add can not be null." );

		this.effects.add( effect );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the name of MTG Card.
	 * @param name {@link String} The name of MTG Card.
	 * @throws IllegalArgumentException if argument given is null of empty string.
	 */
	public void setName(String name) throws IllegalArgumentException{
		if(name == null || name.isEmpty())
			throw new IllegalArgumentException( "Name can not be null or empty String." );

		this.name = name;
	}

	/**
	 * Set the Color of MTG Card.
	 * @param color {@link CardColor} the Color of MTG Card.
	 * @throws IllegalArgumentException if color is null.
	 */
	public void setCardColor(CardColor color) throws IllegalArgumentException{
		if(color == null)
			throw new IllegalArgumentException( "Card Color can not be null." );
		this.color = color;
	}

	/**
	 * Set the rarity of MTG Card.
	 * @param rarity {@link Rarity} the rarity of MTG Card.
	 * @throws IllegalArgumentException if rarity is null.
	 */
	public void setRarity(Rarity rarity) throws IllegalArgumentException{
		if(rarity == null)
			throw new IllegalArgumentException( "Rartity can not be null." );
		this.rarity = rarity;
	}

	/**
	 * Set the series of MTG Card.
	 * @param series {@link String} that represents the series of MTG Card.
	 * @throws IllegalArgumentException if string given is null or empty.
	 */
	public void setSeries(String series) throws IllegalArgumentException{
		if(series == null || series.isEmpty())
			throw new IllegalArgumentException( "Series can not be null." );
		this.series = series;
	}

	/**
	 * Set the sub type of MTG Card.
	 * @param subType {@link String} the sub type of MTG Card.
	 * @throws IllegalArgumentException if string is null or empty.
	 */
	public void setSubType(String subType) throws IllegalArgumentException{
		if(subType == null)
			throw new IllegalArgumentException( "Sub Type can not be null." );
		this.subType = subType;
	}

	/**
	 * This method set if the card is legendary.
	 * @param isLegendary {@link Boolean} if the card is legendary.
	 */
	public void setLegendary(boolean isLegendary){
		this.isLegendary = isLegendary;
	}

	/**
	 * This method set if the card is an artifact.<br>
	 * If the MTGCard is instance of {@link Artifact}, {@link Instant}, {@link Enchantment} ,{@link Land}, 
	 * {@link Planeswalker} and {@link Sorcery} this method is void.
	 * @param isArtifact {@link Boolean}
	 */
	public void setArtifact(boolean isArtifact){
		this.isArtifact = isArtifact;
	}

	/**
	 * This method set the primary effect of MTG Card.<br>
	 * On {@link Planeswalker} object this method is void.
	 * @param primaryEffect {@link String} the primary effect. Set to null or empty string to cancel the effect.
	 */
	public void setPrimaryEffect(String primaryEffect){
		this.primaryEffect = primaryEffect;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link String} the name of MTG Card.*/
	public String getName(){
		return name;
	}

	/** @return {@link CardColor} the color of MTG Card. */
	public CardColor getCardColor(){
		return color;
	}

	/** @return {@link Rarity} the rarity of MTG Card. */
	public Rarity getRarity(){
		return rarity;
	}

	/** @return {@link String} the series of MTG card. */
	public String getSeries(){
		return this.series;
	}

	/** @return {@link String} the sub type of MTG Card. */
	public String getSubType(){
		return this.subType;
	}

	/** @return {@link Set} the set of ability. */
	public Set<Ability> getAbilities(){
		return this.abilitis;
	}

	/** @return {@link Set} the set of effects. */
	public Set<Effect> getEffects(){
		return this.effects;
	}

	/** @return {@link String} the primary effect of MTG Card. This is always null on {@link Planeswalker} object. */
	public String getPrimaryEffect(){
		return this.primaryEffect;
	}

	/**
	 * Check if the creature is legendary.
	 * @return {@link Boolean} if the card is legendary.
	 */
	public boolean isLegendary(){
		return this.isLegendary;
	}

	/**
	 * Check if the creature is an artifact.
	 * @return {@link Boolean} if the card is an artifact.
	 */
	public boolean isArtifact(){
		return this.isArtifact;
	}

	/**
	 * This method returns the name of stored file of {@link MTGCard} in format name_series.json
	 * @param c {@link MTGCard}
	 * @return {@link String} in format name_series.json
	 */
	public String getJSONFileName(){
		StringBuilder r = new StringBuilder();
		String name = DBCostants.normalizeForStorage( getName().toLowerCase().replaceAll( " ", "" ) );
		String series = DBCostants.normalizeForStorage( getSeries().toLowerCase().replaceAll( " ", "" ) );
		String name_series = String.format( "%s_%s.json", name, series );
		r.append( DBCostants.JSON_PATH );
		r.append( PathUtil.FILE_SEPARATOR );
		r.append( name_series );

		return r.toString();
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (isArtifact ? 1231 : 1237);
		result = prime * result + (isLegendary ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rarity == null) ? 0 : rarity.hashCode());
		result = prime * result + ((subType == null) ? 0 : subType.hashCode());
		result = prime * result + ((series == null) ? 0 : series.hashCode());
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
		MTGCard other = (MTGCard) obj;

		//check name
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals( other.name ))
			return false;

		//check series
		if(series == null) {
			if(other.series != null)
				return false;
		} else if(!series.equals( other.series ))
			return false;

		//check sub type
		if(subType == null) {
			if(other.subType != null)
				return false;
		} else if(!subType.equals( other.subType ))
			return false;

		//check color
		if(color == null) {
			if(other.color != null)
				return false;
		} else if(!color.equals( other.color ))
			return false;

		//check is artifact
		if(isArtifact != other.isArtifact)
			return false;

		//check is legendary
		if(isLegendary != other.isLegendary)
			return false;

		//check rarity
		if(rarity != other.rarity)
			return false;

		//check ability
		if(abilitis == null) {
			if(other.abilitis != null)
				return false;
		} else if(!abilitis.equals( other.abilitis ))
			return false;

		//check effects
		if(effects == null) {
			if(other.effects != null)
				return false;
		} else if(!effects.equals( other.effects ))
			return false;

		//check primary effects
		if(primaryEffect == null) {
			if(other.primaryEffect != null)
				return false;
		} else if(!primaryEffect.equals( other.primaryEffect ))
			return false;

		return true;
	}

}
