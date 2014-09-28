package com.hackcaffebabe.mtg.model.card;

import it.hackcaffebabe.jx.table.model.DisplayableObject;
import java.io.Serializable;
import com.hackcaffebabe.mtg.controller.StringNormalizer;


/**
 * Represents a single MTG card ability.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Ability extends DisplayableObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name, description;

	/**
	 * Instance a new ability with name and description.
	 * @param name {@link String} the name of ability.
	 * @param description {@link String} the description of ability.
	 * @throws IllegalArgumentException if arguments given are null or empty string.
	 */
	public Ability(String name, String description) throws IllegalArgumentException{
		super( Ability.class );
		setColumnNames( new String[] { "Name", "Description" } );

		String nameN = StringNormalizer.normalizeForStorage( name );
		String descriptionN = StringNormalizer.normalizeForStorage( description );
		this.setName( nameN );
		this.setDescription( descriptionN );
		AbilityFactory.getInstance().add( nameN, descriptionN );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the name of ability 
	 */
	private void setName(String name) throws IllegalArgumentException{
		if(name == null || name.isEmpty())
			throw new IllegalArgumentException( "Name of ability can not be null or empty string." );

		this.name = name;
	}

	/**
	 * Set the description of ability
	 */
	private void setDescription(String description) throws IllegalArgumentException{
		if(description == null || description.isEmpty())
			throw new IllegalArgumentException( "Description of ability can not be null." );

		this.description = description;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the ability name.
	 * @return {@link String} the ability name.
	 */
	public final String getName(){
		return this.name;
	}

	/**
	 * Return the description text.
	 * @return {@link String} the description text.
	 */
	public final String getDescription(){
		return this.description;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public String toString(){
		return String.format( "%s: %s", this.name, this.description );
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Ability other = (Ability) obj;
		if(name == null) {
			if(other.name != null)
				return false;
		} else if(!name.trim().toLowerCase().equals( other.name.trim().toLowerCase() ))
			return false;
		return true;
	}

	@Override
	public Object[] getDisplayRow(){
		return new Object[] { this.name, this.description };
	}
}
