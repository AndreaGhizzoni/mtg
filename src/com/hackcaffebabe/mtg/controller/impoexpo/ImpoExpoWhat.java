package com.hackcaffebabe.mtg.controller.impoexpo;

/**
 * Enumerator to select what to import/export
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public enum ImpoExpoWhat
{
	/** import/export all cards */
	ALL_CARDS,
	/** import/export only the cards that are choose by the user. */
	SELECTIVE_CARDS,
	/** import/export all decks */
	ALL_DECKS,
	/** import/export only the decks that are choose by the user. */
	SELECTIVE_DECKS
}
