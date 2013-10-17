package com.hackcaffebabe.mtg.controller.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * This represents a report of all data saved on the disk.
 * 
 * TODO finish the report with new ideas.
 * TODO maybe write an utility class that calculate the union, intersection, and difference of two Collection<T>. 
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Report
{
	private HashSet<MTGCard> mtgSet;

	/* map of (String,Integer) as (series-name,count of serie's mtgCard) */
	private HashMap<String, Integer> mapCreature = new HashMap<>();
	private HashMap<String, Integer> mapArtifact = new HashMap<>();
	private HashMap<String, Integer> mapEnchantment = new HashMap<>();
	private HashMap<String, Integer> mapInstant = new HashMap<>();
	private HashMap<String, Integer> mapLand = new HashMap<>();
	private HashMap<String, Integer> mapPlaneswalker = new HashMap<>();
	private HashMap<String, Integer> mapSorcery = new HashMap<>();

	/**
	 * Instance the report of data given.
	 * @param mtgSet {@link Set} of {@link MTGCard}
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public Report(HashSet<MTGCard> mtgSet) throws IllegalArgumentException{
		if(mtgSet == null)
			throw new IllegalArgumentException( "Set of MTG card can not be null." );
		this.mtgSet = mtgSet;
		this.calculate(0,this.mtgSet.size()-1);
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* count all field starting from the first argument to the end ( start -> end ) */
	public void calculate(int start, int end){
		if(!this.mtgSet.isEmpty()) {

		}
	}

	public void recalculate(HashSet<MTGCard> newMTGSet) throws IllegalArgumentException{
		if(mtgSet == null)
			throw new IllegalArgumentException( "Set of MTG card can not be null." );

		// Z = get the difference between newMTGSet and this.mtgSet.
		// if Z is not empty {
		//    this.mtgSet.addall( Z )
		//    calculate( Z.size()-1, this.mtgSet.size() )
		//}

	}

//===========================================================================================
// GETTER
//===========================================================================================
}
