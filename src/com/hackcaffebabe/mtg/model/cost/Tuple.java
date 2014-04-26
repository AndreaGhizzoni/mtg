package com.hackcaffebabe.mtg.model.cost;

/**
 *TODO add doc 
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 * @param <T>
 * @param <K>
 */
public class Tuple <T, K>
{
	private T f = null;
	private K s = null;

	public Tuple(){}

	public Tuple(T firstObj, K secondObj){
		this.set( firstObj, secondObj );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	public void set(T firstObj, K secondObj){
		this.setFirstObj( firstObj );
		this.setSecondObj( secondObj );
	}

	public void setFirstObj(T firstObj){
		this.f = firstObj;
	}

	public void setSecondObj(K secondObj){
		this.s = secondObj;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	public T getFirstObj(){
		return this.f;
	}

	public K getSecondObj(){
		return this.s;
	}

//===========================================================================================
// OVERRIDE 
//===========================================================================================
	public String toString(){
		return String.format( "(%s,%s)", this.f, this.s );
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
		if(f == null) {
			if(other.f != null)
				return false;
		} else if(!f.equals( other.f ))
			return false;
		if(s == null) {
			if(other.s != null)
				return false;
		} else if(!s.equals( other.s ))
			return false;
		return true;
	}
}
