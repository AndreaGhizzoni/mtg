package com.hackcaffebabe.mtg.model.color;

/**
 * Represents the MTG card color.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public enum _Mana
{
	BLACK, RED, WHITE, GREEN, BLUE, COLOR_LESS, X, TAP;

	/**
	 * This method converts a {@link _Mana} in short string.
	 * @param c {@link _Mana} to convert.
	 * @return {@link String} the string of {@link _Mana} given.
	 */
	public static String getAbbraviation(_Mana c){
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
			default:
				return null;
		}
	}
}
