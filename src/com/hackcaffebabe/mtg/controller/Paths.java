package com.hackcaffebabe.mtg.controller;

import it.hackcaffebabe.ioutil.file.PathUtil;


/**
 * This class holds all the path in the program.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Paths
{
	private static final String pattern = "%s%s%s";

	/** user home directory/.mtg */
	public static final String mtgHome = String.format( pattern, PathUtil.USER_HOME, PathUtil.FILE_SEPARATOR, ".mtg" );
	/** user home directory/.mtg/data */
	public static final String mtgDataHome = String.format( pattern, mtgHome, PathUtil.FILE_SEPARATOR, "data" );
	/** user home directory/.mtg/log.txt */
	public static final String LOG_FILE_PATH = String.format( pattern, mtgHome, PathUtil.FILE_SEPARATOR, "log.txt" );
	/** user home directory/.mtg/data/card */
	public static final String JSON_PATH = String.format( pattern, mtgDataHome, PathUtil.FILE_SEPARATOR, "card" );
	/** user home directory/.mtg/data/deck */
	public static final String DECKS_PATH = String.format( pattern, mtgDataHome, PathUtil.FILE_SEPARATOR, "deck" );
	/** extension of deck files */
	public static final String DECKS_EXT = "mtgdeck";
	/** user home directory/.mtg/data/ability.json */
	public static final String ABILITY_FILE_PATH = String.format( pattern, mtgDataHome, PathUtil.FILE_SEPARATOR,
			"ability.json" );

	/** cardsbck */
	public static final String BCK_CARDS_EXT = "zip";
	/** decksbck */
	public static final String BCK_DECKS_EXT = "zip";
	/** Represents the card backup file name */
	public static final String BCK_CARDS_NAME = String.format( "MTG_Cards_Backup.%s", BCK_CARDS_EXT );
	/** Represents the decks backup file name */
	public static final String BCK_DECKS_NAME = String.format( "MTG_Decks_Backup.%s", BCK_DECKS_EXT );
}
