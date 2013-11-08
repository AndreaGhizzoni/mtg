package com.hackcaffebabe.mtg.trash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Rarity;


/**
 * This represents a report of all data saved on the disk.
 * 
 * TODO finish the report with new ideas.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Report
{
	//total card saved. oldsSet.size();
	private HashSet<MTGCard> mtgSet;

	//total card for each Rarity. hash set (Rarity, Integer)
	private final Set<Entry<Rarity, Integer>> setMTGRarity = new HashSet<>();
	
	//total card for each Series. hash set (String, Integer)
	private final Set<Entry<String, Integer>> setMTGSeries = new HashSet<>();

	/**
	 * Instance the report with an {@link HashMap} given.
	 * @param set {@link Set} of {@link MTGCard}
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public Report(HashSet<MTGCard> set) throws IllegalArgumentException{
		if(set == null)
			throw new IllegalArgumentException( "Set of MTG card can not be null." );
		this.mtgSet = set;
	}

//===========================================================================================
// METHOD
//===========================================================================================
//	/* initialize hash map */
//	private void init(){
//		for(Rarity r: Rarity.getAllRarity()) {
//			this.mapMTGRatity.put( r, 0 );
//		}
//		for(String s: this.series) {
//			this.mapMTGSeries.put( s, 0 );
//		}
//	}
}
