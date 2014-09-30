package com.hackcaffebabe.mtg.model.card;

import java.util.Arrays;
import java.util.List;


/**
 * Represents the rarity of MTG card.
 *   
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public enum Rarity
{
	/**The MTG Common Rarity*/
	COMMON(0, "Common"),
	/**The MTG Non-Common Rarity*/
	NON_COMMON(1, "Not Common"),
	/**The MTG Rare Rarity*/
	RARE(2, "Rare"),
	/**The MTG Mythical Rarity*/
	MYTHICAL(3, "Mythical");

	private int value;
	private String fancy;

	Rarity(int i, String fancy){
		this.value = i;
		this.fancy = fancy;
	}

	/** @return {@link Integer} the amount value. */
	public int getValue(){
		return this.value;
	}

	/** @return {@link String} the fancy string for the rarity. */
	public String getFancy(){
		return this.fancy;
	}

	/**
	 * Return all the Rarity of MTG cards. 
	 * @return {@link List} of the Rarity of MTG card.
	 */
	public static List<Rarity> getAllRarity(){
		return Arrays.asList( COMMON, NON_COMMON, RARE, MYTHICAL );
	}

	/**
	 * Return all the Rarity of MTG cards as a list of Strings.
	 * @return {@link List} of the Rarity of MTG card.
	 */
	public static List<String> getAllRarityAsStrings(){
		return Arrays.asList( COMMON.getFancy(), NON_COMMON.getFancy(), RARE.getFancy(), MYTHICAL.getFancy() );
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public String toString(){
		return this.fancy;
	}
}
