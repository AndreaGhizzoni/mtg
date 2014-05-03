package com.hackcaffebabe.mtg.model.cost;

import java.util.HashSet;
import java.util.Set;
import com.hackcaffebabe.mtg.model.color.Mana;


/**
 * Represents the Mana Cost.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 2.0
 */
public class ManaCost implements Comparable<ManaCost>
{
	private Set<Tuple<Mana, Integer>> cost = new HashSet<>();
	private Integer cmc = 0;

	/**
	 * Instance a mana cost with {@link Tuple} of Mana and his occurrences.
	 * @param tuples {@link Tuple}.
	 * @throws IllegalArgumentException if arguments is null or Tuple is malformed.
	 */
	@SafeVarargs
	public ManaCost(Tuple<Mana, Integer>... tuples) throws IllegalArgumentException{
		for(Tuple<Mana, Integer> t: tuples) {
			if(t.getSecondObj() == 0)// this is necessary for GUIUtils.showManaCost
				continue;
			this.checkTuple( t );
			this.cost.add( t );
		}
		this.calculateCMC();
	}

	/**
	 * Instance a mana cost with a set of {@link Tuple} of Mana and his occurrences.
	 * @param cost {@link Set} of Tuple.
	 * @throws IllegalArgumentException if arguments is null or Tuple is malformed.
	 */
	public ManaCost(Set<Tuple<Mana, Integer>> cost) throws IllegalArgumentException{
		for(Tuple<Mana, Integer> t: cost) {
			if(t.getSecondObj() == 0)// this is necessary for GUIUtils.showManaCost
				continue;
			this.checkTuple( t );
			this.cost.add( t );
		}
		this.calculateCMC();
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Add an additional cost.
	 * @param mana {@link Mana} to add.
	 * @param f {@link Integer} the occurrences of mana.
	 * @throws IllegalArgumentException if mana or f are null.
	 */
	public void addCost(Mana mana, Integer f) throws IllegalArgumentException{
		if(mana == null)
			throw new IllegalArgumentException( "Mana to add at cost can not be null." );
		if(f == null)
			throw new IllegalArgumentException( "Frequency of Mana to add at cost can not be null." );

		if(f != 0) {
			Tuple<Mana, Integer> t = new Tuple<Mana, Integer>( mana, f );
			this.checkTuple( t );
			this.cost.add( t );
			this.calculateCMC();
		}
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Check if contains Mana.TAP action.
	 * @return {@link Boolean}
	 */
	public boolean containsTAP(){
		return getCost().contains( new Tuple<Mana, Integer>( Mana.TAP, -1 ) );
	}

	/**
	 * Check if contains Mana.X action.
	 * @return {@link Boolean}
	 */
	public boolean containsX(){
		return getCost().contains( new Tuple<Mana, Integer>( Mana.X, -1 ) );
	}

	/**
	 * Returns the cost of ManaCost as a Set of Tuple. Each Tuple contains the mana and his occurrences
	 * @return {@link Set} of {@link Tuple} of {@link Mana} and {@link Integer}
	 */
	public final Set<Tuple<Mana, Integer>> getCost(){
		return this.cost;
	}

	/**
	 * Return an integer represents the converted mana cost.
	 * @return {@link Integer}
	 */
	public final Integer getConvertedManaCost(){
		return this.cmc;
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* calculate the converted mana cost. */
	private void calculateCMC(){
		Integer res = 0;
		for(Tuple<Mana, Integer> t: this.cost) {
			if(t.getSecondObj() != -1)
				res += t.getSecondObj();
		}
		this.cmc = res;
	}

	/* check if tuple given is malformed */
	private void checkTuple(Tuple<Mana, Integer> t) throws IllegalArgumentException{
		if(t == null)
			throw new IllegalArgumentException( "Mana can not be null" );
		if(t.getFirstObj() == null || t.getSecondObj() == null)
			throw new IllegalArgumentException( "Mana contails null value" );

		boolean TequalstoTAP = t.getFirstObj().equals( Mana.TAP );
		if(TequalstoTAP && t.getSecondObj() != -1)
			throw new IllegalArgumentException( "Mana malformed for tap action with value " + t.getSecondObj() );

		boolean isAcolorOrX = !TequalstoTAP /*&& !TequalstoX*/;
		if(isAcolorOrX && t.getSecondObj() < 0)
			throw new IllegalArgumentException( "Mana malformed for color" );
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public String toString(){
		StringBuilder bColor = new StringBuilder();
		StringBuilder x = new StringBuilder();
		String tap = "";

		for(Tuple<Mana, Integer> t: this.cost) {
			if(t.getFirstObj().equals( Mana.TAP ))
				tap = "TAP.";
			else for(int i = 0; i < t.getSecondObj(); i++) {
				String abb = Mana.getAbbraviation( t.getFirstObj() );
				if(abb.equals( "X" )) {
					x.append( "X." );
				} else {
					bColor.append( Mana.getAbbraviation( t.getFirstObj() ) );
					bColor.append( "." );
				}
			}
		}
		String res = tap + x.toString() + bColor.toString();
		return res.isEmpty() ? "0" : res.substring( 0, res.length() - 1 );
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
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

		ManaCost other = (ManaCost) obj;
		if(cost == null) {
			if(other.cost != null)
				return false;
		} else if(!cost.equals( other.cost ))
			return false;
		return true;
	}

	@Override
	public int compareTo(ManaCost o){
		if(this.cmc < o.cmc)
			return -1;
		else if(this.cmc > o.cmc)
			return 1;
		else return 0;
	}
}
