package main.java.com.hackcaffebabe.mtg.controller.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import main.java.com.hackcaffebabe.mtg.model.Artifact;
import main.java.com.hackcaffebabe.mtg.model.Creature;
import main.java.com.hackcaffebabe.mtg.model.Enchantment;
import main.java.com.hackcaffebabe.mtg.model.Instant;
import main.java.com.hackcaffebabe.mtg.model.Land;
import main.java.com.hackcaffebabe.mtg.model.MTGCard;
import main.java.com.hackcaffebabe.mtg.model.Planeswalker;
import main.java.com.hackcaffebabe.mtg.model.Sorcery;
import main.java.com.hackcaffebabe.mtg.model.card.Ability;
import main.java.com.hackcaffebabe.mtg.model.card.AbilityFactory;
import main.java.com.hackcaffebabe.mtg.model.card.Rarity;
import main.java.com.hackcaffebabe.mtg.model.color.Mana;


/**
 * Criteria to query the MTGCard.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Criteria
{
	public enum Mode
	{
		/** Whit this mode the card match if at least one criteria matches */
		LAZY,

		/** Whit this mode the card match if and only if all the criteria are verify. */
		SPECIFIC;
	}

	private String name = null;
	private Integer convertedManaCost = null;
	private String subTypes = null;
	private Set<String> series = new HashSet<>();
	private Set<Mana> colors = new HashSet<>();
	private Set<Rarity> rarity = new HashSet<>();
	private Set<String> aiblities = new HashSet<>();

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * This method check if a card given match whit the criteria set.
	 * @param mtg {@link MTGCard} to check.
	 * @param mode {@link Criteria.Mode} the mode to match the card.
	 * @return {@link Boolean} if the card given match with the criteria.
	 */
	public boolean match(MTGCard mtg, Mode mode){
		if(mode.equals( Mode.LAZY ))
			return lazyMatch( mtg );
		else if(mode.equals( Mode.SPECIFIC ))
			return exactlyMatch( mtg );
		else return false;
	}

	/* match the given card in specific mode */
	private boolean exactlyMatch(MTGCard mtg){
		if(mtg == null || isCriteriaEmpty())
			return true;// if no criteria was set, every card matches

		ArrayList<Boolean> listOfChecking = new ArrayList<>();

		if(name != null) {
			listOfChecking.add( mtg.getName().equals( name ) );
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
				listOfChecking.add( convertedManaCost.equals( mc ) );
			}
		}

		if(this.subTypes != null) {
			listOfChecking.add( mtg.getSubType().equals( this.subTypes ) );
		}

		if(!this.series.isEmpty()) {
			boolean find = false;
			if(this.series.contains( mtg.getSeries() )) {
				listOfChecking.add( true );
				find = true;
			}
			if(!find)
				listOfChecking.add( false );
		}

		if(!this.colors.isEmpty()) {
			listOfChecking.add( colors.equals( mtg.getCardColor().getColors() ) );
		}

		if(!this.rarity.isEmpty()) {
			boolean find = false;
			if(this.rarity.contains( mtg.getRarity() )) {
				listOfChecking.add( true );
				find = true;
			}
			if(!find)
				listOfChecking.add( false );
		}

		if(!this.aiblities.isEmpty()) {
			boolean find = false;
			for(String s: this.aiblities) {
				if(mtg.getAbilities().contains( AbilityFactory.getInstance().getAbility( s ) )) {
					listOfChecking.add( true );
					find = true;
				}
			}
			if(!find)
				listOfChecking.add( false );
		}

		if(listOfChecking.isEmpty())
			return false;
		else if(listOfChecking.contains( false ))
			return false;
		else return true;
	}

	/* match the card given in lazy mode */
	private boolean lazyMatch(MTGCard mtg){
		if(mtg == null || isCriteriaEmpty())
			return true;// if no criteria was set, every card matches

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

		if(this.subTypes != null) {
			if(mtg.getSubType().equals( this.subTypes ))
				return true;
		}

		if(!this.series.isEmpty()) {
			for(String s: this.series) {
				if(mtg.getSeries().equals( s ))
					return true;
			}
		}

		if(!this.aiblities.isEmpty()) {
			for(String s: this.aiblities) {
				for(Ability a: mtg.getAbilities()) {
					if(s.equals( a.getName() )) {
						return true;
					}
				}
			}
		}

		for(Mana b: colors) {
			if(mtg.getCardColor().getColors().contains( b ))
				return true;
		}

		if(!this.rarity.isEmpty()) {
			for(Rarity r: this.rarity) {
				if(mtg.getRarity().equals( r ))
					return true;
			}
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
			this.subTypes = t;
		return this;
	}

	/**
	 * This method set the criteria by series.<br>
	 * Set to null to cancel the series criteria.
	 * @param s {@link String} the series to search.
	 * @return {@link Criteria} with the series flag set.
	 */
	public Criteria bySeries(String s){
		if(s != null) {
			if(!this.series.add( s ))
				this.series.remove( s );
		} else {
			this.series.clear();
		}
		return this;
	}

	/**
	 * This method set the criteria by {@link Mana}.
	 * @param b {@link Mana} the basic to search.
	 * @return {@link Criteria} with the basic color flag set.
	 */
	public Criteria byColors(Mana b){
		if(b != null) {
			if(!this.colors.add( b ))
				this.colors.remove( b );
		} else {
			this.colors.clear();
		}
		return this;
	}

	/**
	 * This method set the criteria by rarity. Set to null to cancel the rarity criteria.
	 * @param r {@link Rarity} the rarity to search.
	 * @return {@link Criteria} with the rarity flag set.
	 */
	public Criteria byRarity(Rarity r){
		if(r != null) {
			if(!this.rarity.add( r ))
				this.rarity.remove( r );
		} else {
			this.rarity.clear();
		}
		return this;
	}

	/**
	 * This method set the criteria by abilities. Set to null to cancel the abilities criteria.
	 * @param a {@link String} the Abilities to search.
	 * @return {@link Criteria} with the abilities set.
	 */
	public Criteria byAbilities(String a){
		if(a != null) {
			if(!this.aiblities.add( a ))
				this.aiblities.remove( a );
		} else {
			this.aiblities.clear();
		}
		return this;
	}

	/**
	 * Check if criteria is void.
	 * @return {@link Boolean} if there is no criteria inserted.
	 */
	public boolean isCriteriaEmpty(){
		return (name == null) && (convertedManaCost == null) && (subTypes == null)
				&& (series.isEmpty() && (aiblities.isEmpty())) && (colors.isEmpty()) && (rarity.isEmpty());
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
		return subTypes;
	}

	/** @return {@link Set} of string of the series */
	public Set<String> getSeries(){
		return series;
	}

	/** @return {@link List} of {@link Mana} */
	public List<Mana> getColors(){
		return new ArrayList<>( colors );
	}

	/** @return {@link Set} the rarity */
	public Set<Rarity> getRarity(){
		return rarity;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aiblities == null) ? 0 : aiblities.hashCode());
		result = prime * result + ((colors == null) ? 0 : colors.hashCode());
		result = prime * result + ((convertedManaCost == null) ? 0 : convertedManaCost.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((rarity == null) ? 0 : rarity.hashCode());
		result = prime * result + ((series == null) ? 0 : series.hashCode());
		result = prime * result + ((subTypes == null) ? 0 : subTypes.hashCode());
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
		Criteria other = (Criteria) obj;
		if(aiblities == null) {
			if(other.aiblities != null)
				return false;
		} else if(!aiblities.equals( other.aiblities ))
			return false;
		if(colors == null) {
			if(other.colors != null)
				return false;
		} else if(!colors.equals( other.colors ))
			return false;
		if(convertedManaCost == null) {
			if(other.convertedManaCost != null)
				return false;
		} else if(!convertedManaCost.equals( other.convertedManaCost ))
			return false;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.equals( other.name ))
			return false;
		if(rarity == null) {
			if(other.rarity != null)
				return false;
		} else if(!rarity.equals( other.rarity ))
			return false;
		if(series == null) {
			if(other.series != null)
				return false;
		} else if(!series.equals( other.series ))
			return false;
		if(subTypes == null) {
			if(other.subTypes != null)
				return false;
		} else if(!subTypes.equals( other.subTypes ))
			return false;
		return true;
	}
}
