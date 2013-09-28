package com.hackcaffebabe.mtg.trash;

import java.util.Arrays;
import java.util.List;


/**
 * Represents the type of Enchantment.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public enum _TypeEnchantment
{
	/** The basic Enchantment.*/
	BASIC,
	/** The Enchantment for creature.*/
	AURA,
	/** The Enchantment for player.*/
	AURA_CURSE,
	/** The Enchantment for creature.*/
	AURA_TRIBAL;
	
	
	/**
	 * Returns all type of Enchantment.
	 * @return {@link List} of Enchantment.
	 */
	public List<_TypeEnchantment> getAllType(){
		return Arrays.asList( BASIC, AURA, AURA_CURSE, AURA_TRIBAL );
	}
}
