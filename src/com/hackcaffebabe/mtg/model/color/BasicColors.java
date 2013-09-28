package com.hackcaffebabe.mtg.model.color;

import java.util.Arrays;
import java.util.List;

/**
 * Represents the MTG card color.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 *
 */
public enum BasicColors
{
	BLACK,
	RED,
	WHITE,
	GREEN,
	BLUE,
	COLOR_LESS;
	
	
	/**
	 * Returns all possible colors of MTG cards.
	 * @return {@link List} all possible color of MTG cards.
	 */
	public static List<BasicColors> getColorList(){
		return Arrays.asList( BLACK, RED, WHITE, GREEN, BLUE, COLOR_LESS );
	}
	
	
	/**
	 * This method converts a {@link BasicColors} in short string.
	 * @param c {@link BasicColors} to convert.
	 * @return {@link String} the string of {@link BasicColors} given.
	 */
	public static String getAbbraviation( BasicColors c ){
		switch( c ){
			case BLACK: return "B";
			case BLUE: return "U";
			case GREEN: return "G";
			case RED: return "R";
			case WHITE: return "W";
			case COLOR_LESS: return "CL";
			default: return null;
		}
	}
}
