package com.hackcaffebabe.mtg.model.color;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the MTG card color.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public enum BasicColors
{
	BLACK, RED, WHITE, GREEN, BLUE, COLOR_LESS;

	/**
	 * Returns all possible colors of MTG cards.
	 * @return {@link List} all possible color of MTG cards.
	 */
	public static List<BasicColors> getColorsList(){
		return Arrays.asList( BLACK, RED, WHITE, GREEN, BLUE, COLOR_LESS );
	}

	/**
	 * Return all possible colors of MTG Cards as a list of String.
	 * @return {@link List} all possible colors of MTG Card as String.
	 */
	public static List<String> getColorsStrings(){
		return Arrays.asList( BLACK.toString(), RED.toString(), GREEN.toString(), BLUE.toString(), COLOR_LESS.toString() );
	}

	/**
	 * This method converts a {@link BasicColors} in short string.
	 * @param c {@link BasicColors} to convert.
	 * @return {@link String} the string of {@link BasicColors} given.
	 */
	public static String getAbbraviation(BasicColors c){
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
			default:
				return null;
		}
	}
}
