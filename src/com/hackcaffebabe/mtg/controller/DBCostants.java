package com.hackcaffebabe.mtg.controller;

import it.hackcaffebabe.ioutil.file.PathUtil;


/**
 * Constants for storing data 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.2
 */
public class DBCostants
{
	/** user home directory/.mtg */
	public static final String mtgHome = PathUtil.USER_HOME + PathUtil.FILE_SEPARATOR + ".mtg";
	/** user home directory/.mtg/data */
	public static final String mtgDataHome = mtgHome + PathUtil.FILE_SEPARATOR + "data";
	/** user home directory/.mtg/log.txt */
	public static final String LOG_FILE_PATH = mtgHome + PathUtil.FILE_SEPARATOR + "log.txt";
	/** user home directory/.mtg/data/card */
	public static final String JSON_PATH = mtgDataHome + PathUtil.FILE_SEPARATOR + "card";
	/** user home directory/.mtg/data/ability.json */
	public static final String ABILITY_FILE_PATH = mtgDataHome + PathUtil.FILE_SEPARATOR + "ability.json";

	/** Flag for logging on file */
	public static final boolean DB_LOG_ON_FILE = false;

	private static final String BCK_FILE_NAME = "MTG_Cards_Backup";
	private static final String BCK_FILE_EXTENSION = "zip";
	/** Represents the backup name */
	public static final String BCK_NAME = BCK_FILE_NAME + "." + BCK_FILE_EXTENSION;

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
	 * This method replace from the given string all the accent characters whit the respective non-accent.
	 * @param s {@link String} to normalize.
	 * @return {@link String} normalized.
	 */
	public static String removeAccentCharacters(String s){
		if(s != null && !s.isEmpty()) {
			return s.replace( "à", "a" ).replace( "è", "e" ).replace( "é", "e" ).replace( "ù", "u" ).replace( "ì", "" )
					.replaceAll( "ò", "o" );
		} else {
			return s;
		}
	}

	/**
	 * This method remove from the given string the follow characters: \t,\n and |<br>
	 * Also replace the Accent Characters whit the respective non-accent.
	 * @param s {@link String} to normalize.
	 * @return {@link String} normalized.
	 */
	public static String normalize(String s){
		if(s != null && !s.isEmpty()) {
			String norm = removeAccentCharacters( s );
			if(!norm.isEmpty()) {
				return norm.replace( "\t", "" ).replace( "|", "" ).replace( "-", "" ).replace( "\n", "" );
			} else {
				return "";
			}
		} else {
			return s;
		}
	}

	/**
	 * This method remove from the given string the follow characters: \t,\n, |, -, \ and /<br>
	 * Also removes all the '.' characters from the bottom of the string and replace the Accent Characters
	 * whit the respective non-accent.
	 * @param s {@link String} to normalize.
	 * @return {@link String} normalized.
	 */
	public static String normalizeForStorage(String s){
		if(s != null && !s.isEmpty()) {
			String norm = normalize( s );
			if(!norm.isEmpty()) {
				String a = norm.replace( "\\", "" ).replace( "/", "" );
				if(a.endsWith( "." ))
					return a.replaceAll( "[*.]", "" );
				return a;
			} else {
				return "";
			}
		} else {
			return s;
		}
	}

	/**
	 * This method remove all the white spaces before and after a given string.<br>
	 * For example this string <code>"**my*fantastic*string******"</code> where "*" (no quotes) are spaces,
	 * by calling <code>removeSpaceStartEnd("**my*fantastic*string******")</code> will return: 
	 * <code>my*fantastic*string</code>.
	 * @param s {@link String} the string to normalize.
	 * @return {@link String} without spaces before and after.
	 */
	public static String removeSpaceStartEnd(String s){
		if(s != null && !s.isEmpty()) {
			int i;// string index of the first non-white-space character.
			for(i = 0; i < s.length() - 1; i++) {
				if(!Character.isWhitespace( s.charAt( i ) ))
					break;
			}
			if(i != 0)// if index != 0 remove all the white space before
				s = s.substring( i, s.length() - 1 );

			// if string is empty don't continue 
			// but returns empty string
			if(s.isEmpty())
				return s;

			int cont = 0;// white space counter from the bottom of the string
			for(int j = s.length() - 1; j >= 0; j--) {
				if(Character.isWhitespace( s.charAt( j ) ))
					cont++;
			}
			if(cont == 0) {// if no white space are countered
				return s;// return the string
			} else {
				return s.substring( 0, s.length() - cont );// otherwise cut the string.
			}
		} else {
			return s;
		}
	}
}
