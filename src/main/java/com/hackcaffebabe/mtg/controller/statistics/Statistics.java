package com.hackcaffebabe.mtg.controller.statistics;

import java.util.LinkedHashMap;
import java.util.Set;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.Mana;
import com.hackcaffebabe.mtg.model.color.TypeColor;


/**
 * This object collects statistics from given cards.
 * 
 * TODO this statistics object can detect when a card is update or deleted. 
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class Statistics
{
	private static Statistics instance;
	// this map contains the statistics has tuple.
	// the key is a fancy name and the value is the occurrence of the key into MTGCars's set
	private LinkedHashMap<StatConstants, Integer> stat = new LinkedHashMap<>();

	/** @return {@link Statistics} return the instance of Statistics object */
	public static Statistics getInstance(){
		if(instance == null)
			instance = new Statistics();
		return instance;
	}

	/* Instance the Statistics card collector */
	private Statistics(){
		init();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * This method collects data from {@link MTGCard} given.
	 * @param m {@link MTGCard} the card to collect data.
	 */
	public void collect(MTGCard m){
		if(m == null)
			return;
		count( m.getRarity() );
		count( m.getCardColor().getColors() );
		count( m.getCardColor().getType() );
		countLeggendary( m.isLegendary() );
	}

	/**
	 * Set the total saved card.
	 * @param tot int
	 */
	public void tot(int tot){
		this.stat.replace( StatConstants.CREATURE, tot );
	}

	/** reinitialize the map */
	public void clear(){
		init();
	}

	/* initialize the map */
	private void init(){
		this.stat.clear();
		for(StatConstants i: StatConstants.values())
			stat.put( i, 0 );
	}

	/* count the rarity */
	private void count(Rarity r){
		int tmp;
		switch( r ) {
			case COMMON:
				tmp = this.stat.get( StatConstants.COMMONS );
				this.stat.replace( StatConstants.COMMONS, tmp + 1 );
				break;
			case MYTHICAL:
				tmp = this.stat.get( StatConstants.MYTHICAL );
				this.stat.replace( StatConstants.MYTHICAL, tmp + 1 );
				break;
			case UNCOMMON:
				tmp = this.stat.get( StatConstants.NON_COMMON );
				this.stat.replace( StatConstants.NON_COMMON, tmp + 1 );
				break;
			case RARE:
				tmp = this.stat.get( StatConstants.RARE );
				this.stat.replace( StatConstants.RARE, tmp + 1 );
				break;
		}
	}

	/* count the mana color */
	private void count(Set<Mana> l){
		int tmp;
		if(l.contains( Mana.RED )) {
			tmp = this.stat.get( StatConstants.RED );
			this.stat.replace( StatConstants.RED, tmp + 1 );
		}
		if(l.contains( Mana.BLACK )) {
			tmp = this.stat.get( StatConstants.BLACK );
			this.stat.replace( StatConstants.BLACK, tmp + 1 );
		}
		if(l.contains( Mana.BLUE )) {
			tmp = this.stat.get( StatConstants.BLUE );
			this.stat.replace( StatConstants.BLUE, tmp + 1 );
		}
		if(l.contains( Mana.WHITE )) {
			tmp = this.stat.get( StatConstants.WHITE );
			this.stat.replace( StatConstants.WHITE, tmp + 1 );
		}
		if(l.contains( Mana.GREEN )) {
			tmp = this.stat.get( StatConstants.GREEN );
			this.stat.replace( StatConstants.GREEN, tmp + 1 );
		}
	}

	/* count the color type */
	private void count(TypeColor t){
		int tmp;
		switch( t ) {
			case COLOR_LESS:
				tmp = this.stat.get( StatConstants.COLOR_LESS );
				this.stat.replace( StatConstants.COLOR_LESS, tmp + 1 );
				break;
			case HYBRID:
				tmp = this.stat.get( StatConstants.HYBRID );
				this.stat.replace( StatConstants.HYBRID, tmp + 1 );
				break;
			case MONO_COLOR:
				tmp = this.stat.get( StatConstants.MONO );
				this.stat.replace( StatConstants.MONO, tmp + 1 );
				break;
			case MULTI_COLOR:
				tmp = this.stat.get( StatConstants.MULTICOLOR );
				this.stat.replace( StatConstants.MULTICOLOR, tmp + 1 );
				break;
		}
	}

	/* count the legendary cards */
	private void countLeggendary(boolean leg){
		if(leg) {
			int tmp = this.stat.get( StatConstants.LEGENDARY );
			this.stat.replace( StatConstants.LEGENDARY, tmp + 1 );
		}
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the map of collected statistics.
	 * @return {@link LinkedHashMap} the map with all the statistics.
	 */
	public LinkedHashMap<StatConstants, Integer> getStats(){
		return this.stat;
	}
}
