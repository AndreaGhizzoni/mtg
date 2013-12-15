package com.hackcaffebabe.mtg.controller.json;

import java.util.ArrayList;
import java.util.List;
import com.hackcaffebabe.mtg.model.Artifact;
import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.Enchantment;
import com.hackcaffebabe.mtg.model.Instant;
import com.hackcaffebabe.mtg.model.Land;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.Planeswalker;
import com.hackcaffebabe.mtg.model.Sorcery;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.BasicColors;


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

	private String subType = null;

	private String series = null;

	private List<BasicColors> colors = new ArrayList<>();

	private Rarity rarity = null;

	private Boolean isLegendary = null;
	private Boolean hasPrimaryEffect = null;

	private Boolean hasEffect = null;
	private Boolean hasAbility = null;//TODO maybe filter by ability

//===========================================================================================
// METHOD
//===========================================================================================
	public boolean exactlyMatch(){
		return false;
	}

	public boolean match(MTGCard mtg){
		if(mtg == null || isEmpty())
			return true;// if no criteria was set, every card match whit this criteria

		if(name != null) {
			if(mtg.getName().equals( name ))
				return true;
		}

		if(convertedManaCost != null) {
			if(!(mtg instanceof Land)) {
				int mc = -1;
				if(mtg instanceof Creature)
					mc = ((Creature) mtg).getManaCost().getConvertedManaCost();
				else if(mtg instanceof Sorcery)
					mc = ((Sorcery) mtg).getManaCost().getConvertedManaCost();
				else if(mtg instanceof Instant)
					mc = ((Instant) mtg).getManaCost().getConvertedManaCost();
				else if(mtg instanceof Enchantment)
					mc = ((Enchantment) mtg).getManaCost().getConvertedManaCost();
				else if(mtg instanceof Planeswalker)
					mc = ((Planeswalker) mtg).getManaCost().getConvertedManaCost();
				else mc = ((Artifact) mtg).getManaCost().getConvertedManaCost();
				if(convertedManaCost.equals( mc ))
					return true;
			}
		}

		if(subType != null) {
			if(mtg.getSubType().equals( subType ))
				return true;
		}

		if(series != null) {
			if(mtg.getSeries().equals( series ))
				return true;
		}

		for(BasicColors b: colors) {
			if(mtg.getCardColor().getBasicColors().contains( b ))
				return true;
		}

		if(rarity != null) {
			if(mtg.getRarity().equals( rarity ))
				return true;
		}

		if(isLegendary != null) {
			if(mtg.isLegendary() == isLegendary)
				return true;
		}

		if(hasPrimaryEffect != null && mtg.getPrimaryEffect() != null) {
			if(!mtg.getPrimaryEffect().isEmpty() == hasPrimaryEffect)
				return true;
		}

		if(hasEffect != null) {
			if(!mtg.getEffects().isEmpty())
				return true;
		}

		if(hasAbility != null) {
			boolean hasPWAbilitis = false;
			if(mtg instanceof Planeswalker)
				hasPWAbilitis = true;// if is a PW, it has abilities
			if(!mtg.getAbilities().isEmpty() || hasPWAbilitis)
				return true;
		}

		return false;
	}

	/**
	 * This method set the criteria by name.
	 * @param n {@link String} the name to search.
	 * @return {@link Criteria} with the name flag set.
	 */
	public Criteria byName(String n){
		if(n != null && !n.isEmpty())
			this.name = n;
		return this;
	}

	/**
	 * This method set the criteria by converted mana cost.
	 * @param c {@link Integer} the converted mana cost to search.
	 * @return {@link Criteria} with the converted mana cost flag set.
	 */
	public Criteria byConvertedManaCost(Integer c){
		if(c == null) {
			this.convertedManaCost = null;
			return this;
		} else {
			if(c >= 0)
				this.convertedManaCost = c;
			return this;
		}
	}

	/**
	 * This method set the criteria by sub type.
	 * @param t {@link String} the sub type to search.
	 * @return {@link Criteria} with the sub type flag set.
	 */
	public Criteria bySubType(String t){
		if(t != null && !t.isEmpty())
			this.subType = t;
		return this;
	}

	/**
	 * This method set the criteria by series.<br>
	 * Set to null to cancel the series criteria.
	 * @param s {@link String} the series to search.
	 * @return {@link Criteria} with the series flag set.
	 */
	public Criteria bySeries(String s){
		this.series = s;
		return this;
	}

	/**
	 * This method set the criteria by {@link BasicColors}.
	 * @param b {@link BasicColors} the basic to search.
	 * @return
	 */
	public Criteria byBasiColors(BasicColors b){
		if(b != null) {
			if(this.colors.contains( b )) {
				this.colors.remove( b );
			} else {
				this.colors.add( b );
			}
		}
		return this;
	}

	/**
	 * This method set the criteria by rarity. Set to null to cancel the rarity criteria.
	 * @param r {@link Rarity} the rarity to search.
	 * @return {@link Criteria} with the rarity flag set.
	 */
	public Criteria byRarity(Rarity r){
		this.rarity = r;
		return this;
	}

	/**
	 * This method set the criteria by legendary card.<br>
	 * Set to null to cancel the isLegendary criteria.
	 * @param isLegendary {@link Boolean}.
	 * @return {@link Criteria} with the legendary flag set.
	 */
	public Criteria byIsLegendary(Boolean isLegendary){
		this.isLegendary = isLegendary;
		return this;
	}

	/**
	 * This method set the criteria by if has a primary effect.<br>
	 * Set to null to cancel the has primary effect criteria.
	 * @param hasPrimaryEffect {@link Boolean} .
	 * @return {@link Criteria} with has primary effect flag set.
	 */
	public Criteria byHasPrimaryEffect(Boolean hasPrimaryEffect){
		this.hasPrimaryEffect = hasPrimaryEffect;
		return this;
	}

	/**
	 * This method set the criteria by if has at least one effect.<br>
	 * Set to null to cancel the has effect criteria.
	 * @param hasEffect {@link Boolean}.
	 * @return {@link Criteria} with has at least one effect flag set.
	 */
	public Criteria byHasEffect(Boolean hasEffect){
		this.hasEffect = hasEffect;
		return this;
	}

	/**
	 * This method set the criteria by if has at least one ability.<br>
	 * Set to null to cancel the has ability criteria.
	 * @param hasAblility {@link Boolean}.
	 * @return {@link Criteria} with has at least one ability flag set.
	 */
	public Criteria byHasAbility(Boolean hasAblility){
		this.hasAbility = hasAblility;
		return this;
	}

	/**
	 * Check if criteria is void.
	 * @return {@link Boolean} if there is no criteria inserted.
	 */
	public boolean isEmpty(){
		return (name == null) && (convertedManaCost == null) && (subType == null) && (series == null)
				&& (colors.isEmpty()) && (rarity == null) && (isLegendary == null) && (hasPrimaryEffect == null)
				&& (hasEffect == null) && (hasAbility == null);
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link String} the name */
	public String getName(){
		return name;
	}

	/** @return {@link Integer} the converted mana cost */
	public Integer getConvertedManaCost(){
		return convertedManaCost;
	}

	/** @return {@link String} the subType */
	public String getSubType(){
		return subType;
	}

	/** @return {@link String} the series */
	public String getSeries(){
		return series;
	}

	/** @return {@link List} of {@link BasicColors} */
	public List<BasicColors> getColors(){
		return this.colors;
	}

	/** @return {@link Rarity} the rarity */
	public Rarity getRarity(){
		return rarity;
	}

	/** @return {@link Boolean} the isLegendary flag */
	public Boolean getIsLegendary(){
		return isLegendary;
	}

	/** @return {@link Boolean} the has primary effect flag */
	public Boolean getHasPrimaryEffect(){
		return hasPrimaryEffect;
	}

	/** @return {@link Boolean} the has effect flag */
	public Boolean getHasEffect(){
		return hasEffect;
	}

	/** @return {@link Boolean} the has ability flag */
	public Boolean getHasAbility(){
		return hasAbility;
	}
}
