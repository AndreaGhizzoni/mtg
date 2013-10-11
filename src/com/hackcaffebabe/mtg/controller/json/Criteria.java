package com.hackcaffebabe.mtg.controller.json;

import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.color.TypeColor;


/**
 * Criteria to query the MTGCard.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Criteria
{
	private String name = null;
	private Integer convertedManaCost = null;
	
	private Class<? extends MTGCard> type = null;
	private String subType = null;
	
	private String series = null;
	
	private TypeColor typeColor = null;
	private CardColor color = null;
	
	private Rarity rarity = null;
	
	private Boolean isLegendary = null;
	
	
//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * This method set the criteria by name.
	 * @param n {@link String} the name to search.
	 * @return {@link Criteria} with the name flag set.
	 */
	public Criteria byName(String n){
		if(n!=null&&!n.isEmpty()) this.name = n;
		return this;
	}
	
	/**
	 * This method set the criteria by converted mana cost.
	 * @param c {@link Integer} the converted mana cost to search.
	 * @return {@link Criteria} with the converted mana cost flag set.
	 */
	public Criteria byConvertedManaCost(int c){
		if(c>=0) this.convertedManaCost = c;
		return this;
	}
	
	/**
	 * This method set the criteria by type.
	 * @param c {@link Class} the type to search.
	 * @return {@link Criteria} with the type flag set.
	 */
	public Criteria byType( Class<? extends MTGCard> c){
		if(c!=null) this.type = c;
		return this;
	}
	
	/**
	 * This method set the criteria by sub type.
	 * @param t {@link String} the sub type to search.
	 * @return {@link Criteria} with the sub type flag set.
	 */
	private Criteria bySubType(String t){
		if(t!=null&&!t.isEmpty()) this.subType = t;
		return this;
	}
	
	/**
	 * This method set the criteria by series.
	 * @param s {@link String} the series to search.
	 * @return {@link Criteria} with the series flag set.
	 */
	private Criteria bySeries(String s){
		if(s!=null&&!s.isEmpty()) this.series = s;
		return this;
	}
	
	/**
	 * This method set the criteria by color type.
	 * @param t {@link TypeColor} the color type to search.
	 * @return {@link Criteria} with the color type flag set.
	 */
	public Criteria byTypeColor(TypeColor t){
		if(t!=null) this.typeColor = t;
		return this;
	}
	
	/**
	 * This method set the criteria by color.
	 * @param c {@link String} the color to search.
	 * @return {@link Criteria} with the color flag set.
	 */
	public Criteria byColor(CardColor c){
		if(c!=null) this.color = c;
		return this;
	}
	
	/**
	 * This method set the criteria by rarity.
	 * @param r {@link Rarity} the rarity to search.
	 * @return {@link Criteria} with the rarity flag set.
	 */
	public Criteria byRarity(Rarity r){
		if(r!=null) this.rarity = r;
		return this;
	}
	
	/**
	 * This method set the criteria by legendary card.
	 * @param n {@link Boolean} the legendary card to search.
	 * @return {@link Criteria} with the legendary flag set.
	 */
	public Criteria byLegendary(boolean isLegendary){
		this.isLegendary = isLegendary;
		return this;
	}
	
//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link String} the name */
	public final String getName(){ return name; }

	/** @return {@link Integer} the converted mana cost */
	public final Integer getConvertedManaCost(){ return convertedManaCost; }
	
	/** @return {@link Class} the type */
	public final Class<?extends MTGCard> getType(){ return type; }

	/** @return {@link String} the subType */
	public final String getSubType(){ return subType; }
	
	/** @return {@link String} the series */
	public final String getSeries(){ return series; }

	/** @return {@link TypeColor} the color type */
	public final TypeColor getTypeColor(){ return typeColor; }

	/** @return {@link CardColor} the color */
	public final CardColor getColor(){ return color; }

	/** @return {@link Rarity} the rarity */
	public final Rarity getRarity(){ return rarity; }

	/** @return {@link Boolean} the isLegendary */
	public final Boolean getIsLegendary(){ return isLegendary; }
}
