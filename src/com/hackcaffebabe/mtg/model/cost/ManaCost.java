package com.hackcaffebabe.mtg.model.cost;

import java.util.HashSet;
import java.util.Set;
import com.hackcaffebabe.mtg.model.color._Mana;


/**
 * TODO add doc
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ManaCost implements Comparable<ManaCost>
{
	private Set<Tuple<_Mana, Integer>> cost = new HashSet<>();
	private Integer cmc = 0;

	@SafeVarargs
	public ManaCost(Tuple<_Mana, Integer>... tuples) throws IllegalArgumentException{
		for(Tuple<_Mana, Integer> t: tuples) {
			if(t == null)
				throw new IllegalArgumentException( "Mana Cost can not be null" );

			boolean TequalstoTAP = t.getFirstObj().equals( _Mana.TAP );
			boolean TequalstoX = t.getFirstObj().equals( _Mana.X );
			if(TequalstoTAP && t.getSecondObj() != -1)
				throw new IllegalArgumentException( "ManaColor malformed for tap action with value " + t.getSecondObj() );

			if(TequalstoX && t.getSecondObj() != -1)
				throw new IllegalArgumentException( "ManaColor malformed for X action " + t.getSecondObj() );

			boolean isAcolor = !TequalstoTAP && !TequalstoX;
			if(isAcolor && t.getSecondObj() <= 0)
				throw new IllegalArgumentException( "ManaColor malformed for color" );

			this.cost.add( t );
		}
		this.calculateCMC();
	}

//===========================================================================================
// SETTER
//===========================================================================================
	public void addCost(_Mana mana, Integer f){
		this.cost.add( new Tuple<_Mana, Integer>( mana, f ) );
		this.calculateCMC();
	}

//===========================================================================================
// GETTER
//===========================================================================================
	public final Set<Tuple<_Mana, Integer>> getCost(){
		return this.cost;
	}

	public final Integer getConvertedManaCost(){
		return this.cmc;
	}

//===========================================================================================
// METHOD
//===========================================================================================
	private void calculateCMC(){
		Integer res = 0;
		for(Tuple<_Mana, Integer> t: this.cost) {
			if(t.getSecondObj() != -1)
				res += t.getSecondObj();
		}
		this.cmc = res;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public String toString(){
		StringBuilder bColor = new StringBuilder();
		String tap = "";
		String x = "";

		for(Tuple<_Mana, Integer> t: this.cost) {
			if(t.getFirstObj().equals( _Mana.TAP ))
				tap = "TAP.";
			else if(t.getFirstObj().equals( _Mana.X ))
				x = "X.";
			else for(int i = 0; i < t.getSecondObj(); i++) {
				bColor.append( _Mana.getAbbraviation( t.getFirstObj() ) );
				bColor.append( "." );
			}
		}
		String res = tap + x + bColor.toString();
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
