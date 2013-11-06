package com.hackcaffebabe.mtg.controller;

import it.hackcaffebabe.ioutil.file.PathUtil;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * Constants for storing data 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.2
 */
public class DBCostants
{
	/** user home directory/.mtg */
	public static final String mtgHome = PathUtil.USER_HOME+PathUtil.FILE_SEPARATOR+".mtg";
	/** user home directory/.mtg/data */
	public static final String mtgDataHome = mtgHome+PathUtil.FILE_SEPARATOR+"data";
	/** user home directory/.mtg/log.txt */
	public static final String LOG_FILE_PATH = mtgHome+PathUtil.FILE_SEPARATOR+"log.txt";
	/** user home directory/.mtg/data/card */
	public static final String JSON_PATH = mtgDataHome+PathUtil.FILE_SEPARATOR+"card";
	/** user home directory/.mtg/data/ability.json */
	public static final String ABILITY_FILE_PATH = mtgDataHome+PathUtil.FILE_SEPARATOR+"ability.json";
	
	/* Flag for logging on file */
	public static final boolean DB_LOG_ON_FILE = false;

	/* JSON tag */
	public static final String JSON_TAG_NAME = "name";
	public static final String JSON_TAG_TYPE = "type";
	public static final String JSON_TAG_CARD_COLOR = "card_color";
	public static final String JSON_TAG_RARITY = "rarity";
	public static final String JSON_TAG_SERIES = "series";
	public static final String JSON_TAG_SUB_TYPE = "sub_type";
	public static final String JSON_TAG_IS_ARTIFACT = "is_artifact";
	public static final String JSON_TAG_IS_LEGENDARY = "is_legendary";
	public static final String JSON_TAG_PRIMARY_EFFECT = "primary_effect";
	public static final String JSON_TAG_EFFECTS = "effects";
	public static final String JSON_TAG_ABILITIES = "abilitis";
	public static final String JSON_TAG_MANA_COST = "mana_cost";
	public static final String JSON_TAG_STRENGTH = "strength";
	public static final String JSON_TAG_LIFE = "life";
	public static final String JSON_TAG_PLANES_ABILITY = "planes_ability";
	public static final String JSON_TAG_DESCRIPTION = "description";
	public static final String JSON_TAG_COLORS = "colors";
	public static final String JSON_TAG_TEXT = "text";
	public static final String JSON_TAG_COST = "cost";
	public static final String JSON_TAG_POWER = "power";
	public static final String JSON_TAG_TOUGHNESS = "toughness";

//===========================================================================================
// COMMON METHODS
//===========================================================================================
	/**
	 * This method returns the name of stored file of {@link MTGCard} in format name_series.json
	 * @param c {@link MTGCard}
	 * @return {@link String} in format name_series.json
	 */
	public static String getJSONFileName(MTGCard c){
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
	
	/**
	 * This method return the name of backup file.
	 * @return {@link String} "bck.zip"
	 */
	public static String getBackupFileName(){
//		StringBuilder b = new StringBuilder();
//		b.append( "bck" );
//		b.append( ".zip" );
//		return b.toString();
		return "bck.zip";
	}
}
