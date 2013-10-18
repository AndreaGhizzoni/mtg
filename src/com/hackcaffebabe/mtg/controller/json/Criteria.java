package com.hackcaffebabe.mtg.controller.json;

import java.util.ArrayList;
import java.util.List;
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
	
//	private Class<? extends MTGCard> type = null;
	private String subType = null;
	
	private String series = null;
	
	private List<BasicColors> colors = new ArrayList<>();
	
//	private TypeColor typeColor = null;
//	private CardColor color = null;
	
	private Rarity rarity = null;
	
	private Boolean isLegendary = null;
	private Boolean hasPrimaryEffect = null;
	private Boolean hasEffect = null;
	private Boolean hasAbility = null;
	
	
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
	public Criteria byConvertedManaCost(Integer c){
		if(c==null){
			this.convertedManaCost = null;
			return this;
		}else{
			if(c>=0) 
				this.convertedManaCost = c;
			return this;
		}
	}
	
//	/**
//	 * This method set the criteria by type. Set to null to cancel the type criteria.
//	 * @param c {@link Class} the type to search.
//	 * @return {@link Criteria} with the type flag set.
//	 */
//	public Criteria byType( Class<? extends MTGCard> c){
//		this.type = c;
//		return this;
//	}
	
	/**
	 * This method set the criteria by sub type.
	 * @param t {@link String} the sub type to search.
	 * @return {@link Criteria} with the sub type flag set.
	 */
	public Criteria bySubType(String t){
		if(t!=null&&!t.isEmpty()) this.subType = t;
		return this;
	}
	
	/**
	 * This method set the criteria by series.
	 * @param s {@link String} the series to search.
	 * @return {@link Criteria} with the series flag set.
	 */
	public Criteria bySeries(String s){
		if(s!=null&&!s.isEmpty()) this.series = s;
		return this;
	}
	
	/**
	 * This method set the criteria by {@link BasicColors}.
	 * @param b {@link BasicColors} the basic to search.
	 * @return
	 */
	public Criteria byBasiColors(BasicColors b){
		if(b!=null){
			if(this.colors.contains(b)){
				this.colors.remove(b);
			}else{
				this.colors.add(b);
			}
		}
		return this;
	}
	
//	/**
//	 * This method set the criteria by color type.
//	 * @param t {@link TypeColor} the color type to search.
//	 * @return {@link Criteria} with the color type flag set.
//	 */
//	public Criteria byTypeColor(TypeColor t){
//		if(t!=null) this.typeColor = t;
//		return this;
//	}
//	
//	/**
//	 * This method set the criteria by color.
//	 * @param c {@link String} the color to search.
//	 * @return {@link Criteria} with the color flag set.
//	 */
//	public Criteria byColor(CardColor c){
//		if(c!=null) this.color = c;
//		return this;
//	}
	
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
	 * This method set the criteria by legendary card. Set to null to cancel the isLegendary criteria.
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
		return (name==null) && (convertedManaCost==null) /*&& (type==null)*/ &&
			   (subType==null) && (series==null) && (colors.isEmpty()) &&
			   (rarity==null) && (isLegendary==null) && (hasPrimaryEffect==null) && 
			   (hasEffect==null) && (hasAbility==null);
	}
	
//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link String} the name */
	public String getName(){ return name; }

	/** @return {@link Integer} the converted mana cost */
	public Integer getConvertedManaCost(){ return convertedManaCost; }
	
//	/** @return {@link Class} the type */
//	public Class<?extends MTGCard> getType(){ return type; }

	/** @return {@link String} the subType */
	public String getSubType(){ return subType; }
	
	/** @return {@link String} the series */
	public String getSeries(){ return series; }

	/** @return {@link List} of {@link BasicColors} */
	public List<BasicColors> getColors(){ return this.colors; }
	
//	/** @return {@link TypeColor} the color type */
//	public final TypeColor getTypeColor(){ return typeColor; }
//
//	/** @return {@link CardColor} the color */
//	public final CardColor getColor(){ return color; }

	/** @return {@link Rarity} the rarity */
	public Rarity getRarity(){ return rarity; }

	/** @return {@link Boolean} the isLegendary flag */
	public Boolean getIsLegendary(){ return isLegendary; }

	/** @return {@link Boolean} the has primary effect flag */
	public Boolean getHasPrimaryEffect(){ return hasPrimaryEffect; }

    /** @return {@link Boolean} the has effect flag */
	public Boolean getHasEffect(){ return hasEffect; }
	
	/** @return {@link Boolean} the has ability flag */
	public Boolean getHasAbility(){ return hasAbility; }
}
