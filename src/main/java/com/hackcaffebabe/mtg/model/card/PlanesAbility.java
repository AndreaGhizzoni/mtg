package com.hackcaffebabe.mtg.model.card;

import it.hackcaffebabe.jx.table.model.DisplayableObject;
import java.io.Serializable;


/**
 * Represents a single planes walker ability.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class PlanesAbility extends DisplayableObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String description;
	private int cost;

	/**
	 * Instance a new planes walker ability with cost and description.
	 * @param cost {@link String} the cost of ability.
	 * @param description {@link String} the description of ability.
	 * @throws IllegalArgumentException if arguments given are null.
	 */
	public PlanesAbility(int cost, String description) throws IllegalArgumentException{
		super( PlanesAbility.class );
		setColumnNames( new String[] { "Life", "Description" } );

		this.setCost( cost );
		this.setDescription( description );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the cost of ability 
	 */
	private void setCost(int cost){
		this.cost = cost;
	}

	/**
	 * Set the description of ability
	 */
	private void setDescription(String description) throws IllegalArgumentException{
		if(description == null || description.isEmpty())
			throw new IllegalArgumentException( "Description of planes walker ability can not be null." );

		this.description = description;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the ability cost.
	 * @return {@link Integer} the ability cost.
	 */
	public final int getCost(){
		return this.cost;
	}

	/**
	 * Return the description.
	 * @return {@link String} the description.
	 */
	public final String getDesctiption(){
		return this.description;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public String toString(){
		return String.format( "%s: %s", this.cost, this.description );
	}

	@Override
	public Object[] getDisplayRow(){
		return new Object[] { this.cost, this.description };
	}
}
