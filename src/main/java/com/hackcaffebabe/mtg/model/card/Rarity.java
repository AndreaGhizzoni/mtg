package main.java.com.hackcaffebabe.mtg.model.card;

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
	COMMON(0),
	/**The MTG Non-Common Rarity*/
	NON_COMMON(1),
	/**The MTG Rare Rarity*/
	RARE(2),
	/**The MTG Mythical Rarity*/
	MYTHICAL(3);

	private int value;

	Rarity(int i){
		this.value = i;
	}

	/**
	 * Returns the amount of Rarity.
	 * @return {@link Integer} the amount value.
	 */
	public int getValue(){
		return this.value;
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
		return Arrays.asList( COMMON.toString(), NON_COMMON.toString(), RARE.toString(), MYTHICAL.toString() );
	}
}
