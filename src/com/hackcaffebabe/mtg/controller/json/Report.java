package com.hackcaffebabe.mtg.controller.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
	private final HashSet<MTGCard> oldSet;
	
	//total card for each Rarity. hash map (Rarity, Integer)
	private final HashMap<Rarity, Integer> mapMTGRatity = new HashMap<>();
	
	//total card for each Series. hash map (String, Integer)
	private final HashMap<String, Integer> mapMTGSeries = new HashMap<>();

	
	
	/**
	 * Instance the report with an {@link HashMap} given.
	 * @param set {@link Set} of {@link MTGCard}
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public Report(final HashSet<MTGCard> set) throws IllegalArgumentException{
		if(set == null)
			throw new IllegalArgumentException( "Set of MTG card can not be null." );
		this.oldSet = set;
		//init
		this.calculate( this.oldSet );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* count all field starting from the first argument to the end ( start -> end ) */
	private void calculate(final HashSet<MTGCard> set){
		if(!set.isEmpty()) {
			
		}
	}

	public void update(final HashSet<MTGCard> newMTGSet) throws IllegalArgumentException{
		if(newMTGSet == null)
			throw new IllegalArgumentException( "Set of MTG card can not be null." );

	}

//===========================================================================================
// GETTER
//===========================================================================================
	//maybe return a JOptionPane with all data
}
