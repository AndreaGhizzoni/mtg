package com.hackcaffebabe.mtg.model.card;

import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hackcaffebabe.mtg.controller.DBCostants;


/**
 * This class provide the common method to save the abilities on JSON file an manage them.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class AbilityFactory
{
	private static AbilityFactory factory;

	private File abilityFile = new File( DBCostants.ABILITY_FILE_PATH );
	private HashMap<String, String> abilities = new HashMap<>();
	private JsonObject jsonAbilities = new JsonObject();

	/**
	 * Return the instance of factory.
	 * @return {@link AbilityFactory} the instance of factory.
	 */
	public static AbilityFactory getInstance(){
		if(factory == null)
			factory = new AbilityFactory();
		return factory;
	}

	private AbilityFactory(){
		this.init();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * This method add the ability on a json file.
	 * @param name {@link String} the name of ability.
	 * @param descrption {@link String} the description of ability.
	 * @throws IllegalArgumentException if name or description is null or empty string.
	 */
	public void add(String name, String descrption) throws IllegalArgumentException{
		if(name == null || name.isEmpty())
			throw new IllegalArgumentException( "Name of ability to save can not be null." );

		if(descrption == null || descrption.isEmpty())
			throw new IllegalArgumentException( "Description of ability to save can not be null." );

		if(this.abilities.containsKey( name ))
			return;

		this.abilities.put( name, descrption );
		this.jsonAbilities.addProperty( name, descrption );
		this.flush();
		Logger.getInstance().write( Tag.INFO, "ability saved = " + name );
	}

	/**
	 * This method remove an existing ability given by name. 
	 * if the name is not saved is skipped.
	 * @param name {@link String} the name of ability.
	 * @throws IllegalArgumentException if name is null or empty string.
	 */
	public void remove(String name) throws IllegalArgumentException{
		if(name == null || name.isEmpty())
			throw new IllegalArgumentException( "Name of ability to remove can not be null." );

		if(!this.abilities.containsKey( name ))
			return;

		this.abilities.remove( name );
		this.jsonAbilities.remove( name );
		this.flush();
		Logger.getInstance().write( Tag.INFO, "ability removed = " + name );
	}

	/**
	 * This method load data from JSON file.
	 */
	private void init(){
		try {
			if(this.abilityFile.exists()) {
				this.jsonAbilities = (JsonObject) new JsonParser().parse( new FileReader( this.abilityFile ) );
				if(this.jsonAbilities != null) {
					for(Map.Entry<String, JsonElement> s: jsonAbilities.entrySet()) {
						this.abilities.put( s.getKey(), s.getValue().getAsString() );
					}
				}
			}
		} catch(Exception e) {
			Logger.getInstance().write( Tag.ERRORS, "error to open " + this.abilityFile.getAbsolutePath() );
		}
	}

	/**
	 * flush all the json object on file.
	 */
	private void flush(){
		try {
			FileWriter file = new FileWriter( this.abilityFile );
			file.write( this.jsonAbilities.toString() );
			file.flush();
			file.close();
		} catch(Exception e) {
			Logger.getInstance().write( Tag.ERRORS, "error to flush the stream abilities" );
		}
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Return the map of all abilities saved.
	 * @return {@link HashMap} of all abilities saved.
	 */
	public final HashMap<String, String> getAbilities(){
		return this.abilities;
	}
}
