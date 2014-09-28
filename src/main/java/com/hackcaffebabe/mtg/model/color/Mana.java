package com.hackcaffebabe.mtg.model.color;

/**
 * Represents the MTG card color.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public enum Mana
{
	BLACK, RED, WHITE, GREEN, BLUE, COLOR_LESS, X, TAP, STAP;

	/**
	 * This method converts a {@link Mana} in short string.
	 * @param c {@link Mana} to convert.
	 * @return {@link String} the string of {@link Mana} given.
	 */
	public static String getAbbraviation(Mana c){
		switch( c ) {
			case BLACK:
				return "B";
			case BLUE:
				return "U";
			case GREEN:
				return "G";
			case RED:
				return "R";
			case WHITE:
				return "W";
			case COLOR_LESS:
				return "CL";
			case X:
				return "X";
			case TAP:
				return "TAP";
			case STAP:
				return "STAP";
			default:
				return null;
		}
	}
}
