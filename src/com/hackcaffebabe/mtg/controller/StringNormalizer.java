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
}
