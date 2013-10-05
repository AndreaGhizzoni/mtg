package com.hackcaffebabe.mtg.controller;

import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * Constants for storing data 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.1
 */
public class DBCostants
{
	/** The storing data Path */
	public static final String STORE_PATH = "data/store";
	public static final String JSON_PATH = "data/mtg";
	/** Flag for logging on file */
	public static final boolean DB_LOG_ON_FILE = false;
	public static final String LOG_PATH = "data/log";

//===========================================================================================
// COMMON METHODS
//===========================================================================================
	/**
	 * TODO remove this when completely switch on JSON.
	 * 
	 * This method returns the name of stored file of {@link MTGCard} in format name_series.
	 * @param c {@link MTGCard}
	 * @return {@link String} in format name_series.
	 */
	public static String getStoreFileName(MTGCard c){
		if(c == null)
			return "";

		StringBuilder r = new StringBuilder();
		String name = c.getName().replaceAll( " ", "" ).toLowerCase();
		String series = c.getSeries().replaceAll( " ", "" ).toLowerCase();
		String name_series = String.format( "%s_%s", name, series );
		r.append( STORE_PATH );
		r.append( System.getProperties().getProperty( "file.separator" ) );
		r.append( name_series );

		return r.toString();
	}
	
	/**
	 * This method returns the name of stored file of {@link MTGCard} in format name_series.json
	 * @param c {@link MTGCard}
	 * @return {@link String} in format name_series.json
	 */
	public static String getStoreJSONName(MTGCard c){
		if(c == null)
			return "";

		StringBuilder r = new StringBuilder();
		String name = c.getName().replaceAll( " ", "" ).toLowerCase();
		String series = c.getSeries().replaceAll( " ", "" ).toLowerCase();
		String name_series = String.format( "%s_%s.json", name, series );
		r.append( JSON_PATH );
		r.append( System.getProperties().getProperty( "file.separator" ) );
		r.append( name_series );

		return r.toString();
	}
}
