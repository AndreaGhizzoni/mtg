package com.hackcaffebabe.mtg.controller;

/**
 * This class holds all method that needs to save a card in JSON.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class StringNormalizer
{
	/**
	 * This method replace from the given string all the accent characters whit the respective non-accent.
	 * @param s {@link String} to normalize.
	 * @return {@link String} normalized.
	 */
	public static String removeAccentCharacters(String s){
		if(s != null && !s.isEmpty()) {
			return s.replace( "à", "a" ).replace( "è", "e" ).replace( "é", "e" ).replace( "ù", "u" ).replace( "ì", "i" )
					.replaceAll( "ò", "o" );
		} else {
			return s;
		}
	}

	/**
	 * Remove extension from a file name.
	 * @param s {@link String} that represents a filename
	 * @return {@link String} the name of the file without the extension.
	 */
	public static String removeExtension(String s){
		if(s == null || s.isEmpty())
			return s;

		return s.split( "[.]" )[0];
	}

	/**
	 * This method remove from the given string the follow characters: -, \t, \n, |, \ and /<br>
	 * @param s {@link String} to normalize.
	 * @return {@link String} normalized.
	 */
	public static String removePathCharacters(String s){
		if(s != null && !s.isEmpty()) {
			return s.replace( "\t", "" ).replace( "|", "" ).replace( "-", "" ).replace( "\n", "" ).replace( "\\", "" )
					.replace( "/", "" );
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
			String tmp = removeAccentCharacters( s );
			tmp = removeExtension( tmp );
			tmp = removePathCharacters( tmp );
			return tmp;
		} else {
			return s;
		}
	}
}
