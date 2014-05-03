package com.hackcaffebabe.mtg.model.cost;

/**
 * Represents the Tuple object.
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

	/**
	 * Instance an empty Tuple.
	 */
	public Tuple(){}

	/**
	 * Instance an empty tuple with two value given.
	 * @param firstObj
	 * @param secondObj
	 */
	public Tuple(T firstObj, K secondObj){
		this.set( firstObj, secondObj );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the two values of the tuple.
	 * @param firstObj
	 * @param secondObj
	 */
	public void set(T firstObj, K secondObj){
		this.setFirstObj( firstObj );
		this.setSecondObj( secondObj );
	}

	/**
	 * Set the first object in the tuple.
	 * @param firstObj
	 */
	public void setFirstObj(T firstObj){
		this.f = firstObj;
	}

	/**
	 * Set the second  object in the tuple.
	 * @param secondObj
	 */
	public void setSecondObj(K secondObj){
		this.s = secondObj;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * returns the first object in the tuple.
	 */
	public T getFirstObj(){
		return this.f;
	}

	/**
	 * returns the second object in the tuple.
	 */
	public K getSecondObj(){
		return this.s;
	}

//===========================================================================================
// OVERRIDE 
//===========================================================================================
	@Override
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
