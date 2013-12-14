package com.hackcaffebabe.mtg.controller.json;

import static com.hackcaffebabe.mtg.controller.DBCostants.JSON_PATH;
import it.hackcaffebabe.ioutil.file.Zipper;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackcaffebabe.mtg.controller.json.adapter.AbilityAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.CardColorAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.EffectAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.MTGCardAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.ManaCostAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.PlanesAbilityAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.StrengthAdapter;
import com.hackcaffebabe.mtg.model.Artifact;
import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.Enchantment;
import com.hackcaffebabe.mtg.model.Instant;
import com.hackcaffebabe.mtg.model.Land;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.Planeswalker;
import com.hackcaffebabe.mtg.model.Sorcery;
import com.hackcaffebabe.mtg.model.card.Ability;
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.card.ManaCost;
import com.hackcaffebabe.mtg.model.card.PlanesAbility;
import com.hackcaffebabe.mtg.model.card.Strength;
import com.hackcaffebabe.mtg.model.color.BasicColors;
import com.hackcaffebabe.mtg.model.color.CardColor;


/**
 * Class that provide to store all {@link MTGCard} on the disk in JSON format.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 2.0
 */
public class StoreManager
{
	private HashSet<MTGCard> mtgSet = new HashSet<>();
	// these are necessary for type-ahead
	private List<String> lstSeries = new ArrayList<>();
	private List<String> lstSubTypes = new ArrayList<>();

	private Gson g;
	private static StoreManager manager;

	private Logger log = Logger.getInstance();

	/**
	 * Returns the instance of Store Manager.<br>
	 * If there are some problems a log will be write on log file.
	 * @return {@link StoreManager} or null if is not open correctly.
	 */
	public static StoreManager getInstance(){
		try {
			if(manager == null)
				manager = new StoreManager();
			return manager;
		} catch(Exception e) {
			e.printStackTrace( Logger.getInstance().getPrintStream() );
			return null;
		}
	}

	/* initialize all the type adapter and load from JSON_PATH all json files */
	private StoreManager() throws IOException{
		this.init();
		this.load();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/** Initialize the entire class **/
	private void init() throws IOException{
		File store = new File( JSON_PATH );
		if(!store.exists())
			store.mkdirs();
		if(!store.canWrite())
			throw new IOException( String.format( "Storage can't write on %s", store.getAbsolutePath() ) );
		if(!store.canRead())
			throw new IOException( String.format( "Storage can't read on %s", store.getAbsolutePath() ) );

		GsonBuilder b = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization();
		b.registerTypeAdapter( PlanesAbility.class, new PlanesAbilityAdapter() );// register the JSON adapter for PlanesAbility class
		b.registerTypeAdapter( Strength.class, new StrengthAdapter() );// register the JSON adapter for Strength class
		b.registerTypeAdapter( CardColor.class, new CardColorAdapter() );// register the JSON adapter for CardColor class
		b.registerTypeAdapter( ManaCost.class, new ManaCostAdapter() );// register the JSON adapter for ManaCost class
		b.registerTypeAdapter( MTGCard.class, new MTGCardAdapter() );// register the JSON adapter for MTGCard class
		b.registerTypeAdapter( Effect.class, new EffectAdapter() );// register the JSON adapter for Effect class
		b.registerTypeAdapter( Ability.class, new AbilityAdapter() );// register the JSON adapter for Ability class		
		g = b.create();
	}

	/* Load existing JSON file */
	private void load(){
		EventQueue.invokeLater( new Runnable(){
			@Override
			public void run(){
				for(File f: new File( JSON_PATH ).listFiles()) {
					MTGCard c = loadFile( f );
					if(c != null) {//if null continue
						mtgSet.add( c );
						addSeriesToList( c.getSeries() );
						addSubTypeToList( c.getSubType() );
					}
				}
			}
		} );
	}

	/**
	 * This method read a single file and cast it in {@link MTGCard}.
	 * @param jsonMTGFile {@link File} a JSON file represents the {@link MTGCard}.
	 * @return {@link MTGCard} or null if file is not a MTGCard.
	 */
	public MTGCard loadFile(File jsonMTGFile){
		try {
			FileReader f = new FileReader( jsonMTGFile );
			MTGCard toReturn = g.fromJson( f, MTGCard.class );
			f.close();
			return toReturn;
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * This method save the give {@link MTGCard} on disk.
	 * @param c {@link MTGCard} to save
	 * @throws IllegalArgumentException if argument given is null
	 * @throws IOException if save on disk fail.
	 */
	public boolean store(MTGCard c) throws IllegalArgumentException, IOException{
		if(c == null)
			throw new IllegalArgumentException( "MTG card to save can not be null" );
		if(mtgSet.contains( c ))
			return false;

		log.write( Tag.DEBUG, "store initialized." );

		String json = g.toJson( c, MTGCard.class );
		FileWriter f = new FileWriter( new File( c.getJSONFileName() ) );
		f.write( json );
		f.flush();
		f.close();
		mtgSet.add( c );
		addSeriesToList( c.getSeries() );
		addSubTypeToList( c.getSubType() );

		log.write( Tag.INFO, String.format( "MTG card json file %s saved correctly.", c.getName() ) );
		return true;
	}

	/**
	 * This method delete given card from the store path.
	 * @param c {@link MTGCard} to delete.
	 * @return {@link Boolean} false if card is not saved, otherwise true.
	 * @throws IllegalArgumentException if argument passed is null.
	 */
	public boolean delete(MTGCard c) throws IllegalArgumentException{
		if(c == null)
			throw new IllegalArgumentException( "Card to delete can not to be null." );
		if(!this.mtgSet.contains( c ))
			return false;

		log.write( Tag.DEBUG, "delete card initialized." );

		this.mtgSet.remove( c );
		log.write( Tag.DEBUG, c.getName() + " removed correctly from data structure." );

		//update series list
		if(searchBy( new Criteria().bySeries( c.getSeries() ) ).isEmpty()) {
			this.lstSeries.remove( c.getSeries() );
			log.write( Tag.DEBUG, c.getSeries() + " removed because its frequency == 1" );
		}

		String path = c.getJSONFileName();
		new File( path ).delete();

		log.write( Tag.INFO, path + " deleted correctly." );
		return true;
	}

	/**
	 * This method update an existing {@link MTGCard} (old) with the difference between the second argument (nevv).
	 * @param old {@link MTGCard} the older card without the user update.
	 * @param nevv {@link MTGCard} the new card with the user update.
	 * @return {@link Boolean} true if changes are applied on the card correctly, false if old.equals(nevv) ||
	 * this.mtgSet.contains( nevv ) || !this.mtgSet.contains( old ).
	 * @throws IllegalArgumentException if argument passed are null.
	 * @throws IOException if write new json file fail.
	 */
	public boolean applyDifference(MTGCard old, MTGCard nevv) throws IllegalArgumentException, IOException{
		if(old == null || nevv == null)
			throw new IllegalArgumentException( "Update MTG card can not be null" );

		// this operation is computationally expensive
		if(old.equals( nevv ))
			return false;

		// if the new card is already contained into MTGset or
		// the oldest card is not contained into MTGset, do nothing.
		if(this.mtgSet.contains( nevv ) || !this.mtgSet.contains( old ))
			return false;

		log.write( Tag.DEBUG, "apply difference initialized." );

		delete( old );
		store( nevv );

		log.write( Tag.INFO, "Card was updated correctly." );
		return true;
	}

	/* this method add the series string if and only if isn't already inserted into this.lstSeries */
	private void addSeriesToList(String s){
		if(s != null && !s.isEmpty()) {
			Collections.sort( this.lstSeries );
			if(this.lstSeries.isEmpty()) {
				this.lstSeries.add( s );
			} else {
				int r = Collections.binarySearch( this.lstSeries, s );
				if(r < 0)
					this.lstSeries.add( s );
			}
		}
	}

	//TODO maybe merge this two methods

	/* this method add the primary effect string if and only if isn't already inserted into this.lstSubTypes */
	private void addSubTypeToList(String pe){
		if(pe != null && !pe.isEmpty()) {
			Collections.sort( this.lstSubTypes );
			if(this.lstSubTypes.isEmpty()) {
				this.lstSubTypes.add( pe );
			} else {
				int r = Collections.binarySearch( this.lstSubTypes, pe );
				if(r < 0)
					this.lstSubTypes.add( pe );
			}
		}
	}

	/**
	 * This method read all files into data/mtg.
	 */
	public void refresh(){
		this.mtgSet.clear();
		this.lstSeries.clear();
		this.lstSubTypes.clear();
		this.load();
	}

	/**
	 * This method search a card with some {@link Criteria}.
	 * @param c {@link Criteria} to search the card.
	 * @return {@link List} of {@link MTGCard}
	 */
	public List<MTGCard> searchBy(Criteria c){
		if(c == null || c.isEmpty())
			return getAllCardsAsList();

		HashSet<MTGCard> set = new HashSet<>();
		for(MTGCard m: this.mtgSet) {
			if(c.getName() != null) {
				if(c.getName().replaceAll( " ", "" ).toLowerCase()
						.equals( m.getName().replaceAll( " ", "" ).toLowerCase() ))
					set.add( m );
			}

			if(c.getConvertedManaCost() != null) {
				if(!(m instanceof Land)) {
					int mc = -1;
					if(m instanceof Creature)
						mc = ((Creature) m).getManaCost().getConvertedManaCost();
					else if(m instanceof Sorcery)
						mc = ((Sorcery) m).getManaCost().getConvertedManaCost();
					else if(m instanceof Instant)
						mc = ((Instant) m).getManaCost().getConvertedManaCost();
					else if(m instanceof Enchantment)
						mc = ((Enchantment) m).getManaCost().getConvertedManaCost();
					else if(m instanceof Planeswalker)
						mc = ((Planeswalker) m).getManaCost().getConvertedManaCost();
					else mc = ((Artifact) m).getManaCost().getConvertedManaCost();

					if(c.getConvertedManaCost().equals( mc ))
						set.add( m );
				}
			}

			if(c.getSubType() != null) {
				if(c.getSubType().replaceAll( " ", "" ).toLowerCase()
						.equals( m.getSubType().replaceAll( " ", "" ).toLowerCase() ))
					set.add( m );
			}

			if(c.getSeries() != null) {
				if(c.getSeries().replaceAll( " ", "" ).toLowerCase()
						.equals( m.getSeries().replaceAll( " ", "" ).toLowerCase() ))
					set.add( m );
			}

			for(BasicColors a: c.getColors()) {
				if(m.getCardColor().getBasicColors().contains( a )) {
					set.add( m );
					break;
				}
			}

			if(c.getRarity() != null) {
				if(c.getRarity() == m.getRarity())
					set.add( m );
			}

			if(c.getIsLegendary() != null) {
				if(c.getIsLegendary() && m.isLegendary())
					set.add( m );
			}

			if(c.getHasPrimaryEffect() != null && c.getHasPrimaryEffect()) {
				if(m.getPrimaryEffect() != null && !m.getPrimaryEffect().isEmpty())
					set.add( m );
			}

			if(c.getHasEffect() != null && c.getHasEffect()) {
				if(!m.getEffects().isEmpty())
					set.add( m );
			}

			if(c.getHasAbility() != null && c.getHasAbility()) {
				if(!m.getAbilities().isEmpty())
					set.add( m );
			}
		}
		return new ArrayList<>( set );
	}

	/**
	 * This method creates a backup file in .zip format of all stored card.
	 * @param destinationFile {@link File} the file represents .zip.
	 * @throws IOException if backup creation fail.
	 */
	public void createBackup(File destinationFile) throws IOException{
		if(destinationFile == null)
			return;

		log.write( Tag.INFO,
				String.format( "%s %s", "Try to backup of all stored files on", destinationFile.getAbsolutePath() ) );
		if(destinationFile.exists() && !destinationFile.delete()) {
			log.write( Tag.ERRORS, "Error on delete exists backup." );
		}

		Zipper zip = new Zipper( destinationFile, new File( JSON_PATH ).listFiles() );
		zip.forceZip();
		log.write( Tag.INFO, "Backup closed and create correctly." );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link HashSet} of all saved card. */
	public HashSet<MTGCard> getAllCards(){
		return this.mtgSet;
	}

	/** @return {@link List} of all saved card. */
	public List<MTGCard> getAllCardsAsList(){
		return new ArrayList<>( this.mtgSet );
	}

	/** @return {@link List} list of inserted series. */
	public List<String> getInsertedSeries(){
		return this.lstSeries;
	}

	/** @return {@link List} list of inserted sun types. */
	public List<String> getInsertedSubTypes(){
		return this.lstSubTypes;
	}
}
