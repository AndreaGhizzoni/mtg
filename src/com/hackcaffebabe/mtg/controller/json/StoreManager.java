package com.hackcaffebabe.mtg.controller.json;

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
import com.hackcaffebabe.mtg.controller.Paths;
import com.hackcaffebabe.mtg.controller.json.adapter.AbilityAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.CardColorAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.EffectAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.MTGCardAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.ManaCostAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.PlanesAbilityAdapter;
import com.hackcaffebabe.mtg.controller.json.adapter.StrengthAdapter;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Ability;
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.card.PlanesAbility;
import com.hackcaffebabe.mtg.model.card.Strength;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


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
	private HashSet<String> setSeries = new HashSet<>();
	private HashSet<String> setSubType = new HashSet<>();

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
		File store = new File( Paths.JSON_PATH );
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
				MTGCard tmp = null;
				File[] files = new File( Paths.JSON_PATH ).listFiles();
				for(File f: files) {
					tmp = loadFile( f );
					if(tmp != null) {//if null continue
						add( tmp );
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
	 * This method simply add a {@link MTGCard} card given.
	 * @param card {@link MTGCard} to add if not null.
	 */
	public void add(MTGCard card){
		if(card != null) {
			mtgSet.add( card );
			setSeries.add( card.getSeries() );
			setSubType.add( card.getSubType() );
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

		long start = System.currentTimeMillis();
		log.write( Tag.DEBUG, "store initialized." );

		String json = g.toJson( c, MTGCard.class );
		FileWriter f = new FileWriter( new File( c.getJSONFileName() ) );
		f.write( json );
		f.flush();
		f.close();
		add( c );

		long end = System.currentTimeMillis();
		log.write( Tag.INFO, String.format( "MTG card file %s saved correctly in %dms.", c.getName(), (end - start) ) );
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

		long start = System.currentTimeMillis();
		log.write( Tag.DEBUG, "delete card initialized." );

		this.mtgSet.remove( c );
		log.write( Tag.DEBUG, String.format( "%s removed correctly from data structure.", c.getName() ) );

		//update series list
		if(searchBy( new Criteria().bySeries( c.getSeries() ), Criteria.Mode.LAZY ).isEmpty()) {
			this.setSeries.remove( c.getSeries() );
			log.write( Tag.DEBUG, String.format( "%s removed because its frequency == 1", c.getSeries() ) );
		}
		//update sub type list
		if(c.getSubType() != null && !c.getSubType().isEmpty()) {
			if(searchBy( new Criteria().bySubType( c.getSubType() ), Criteria.Mode.LAZY ).isEmpty()) {
				this.setSubType.remove( c.getSubType() );
				log.write( Tag.DEBUG, String.format( "%s removed because its frequency == 1", c.getSubType() ) );
			}
		}

		String path = c.getJSONFileName();
		new File( path ).delete();

		long end = System.currentTimeMillis();
		log.write( Tag.INFO, String.format( "%s deleted correctly in %dms.", path, (end - start) ) );
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

		long start = System.currentTimeMillis();
		log.write( Tag.DEBUG, "apply difference initialized." );

		delete( old );
		store( nevv );

		long end = System.currentTimeMillis();
		log.write( Tag.INFO, String.format( "Card was updated correctly in %dms", (end - start) ) );
		return true;
	}

	/**
	 * This method read all files into data/mtg.
	 */
	public void refresh(){
		this.mtgSet.clear();
		this.setSeries.clear();
		this.setSubType.clear();
		this.load();
	}

	/**
	 * This method search a card with some {@link Criteria}.
	 * @param criteria {@link Criteria} to search the card. 
	 * @param mode {@link Criteria.Mode} to search by
	 * @return {@link List} of {@link MTGCard}
	 */
	public List<MTGCard> searchBy(Criteria criteria, Criteria.Mode mode){
		if(criteria == null || criteria.isCriteriaEmpty())
			return getAllCardsAsList();

		HashSet<MTGCard> set = new HashSet<>();
		for(MTGCard m: this.mtgSet) {
			if(criteria.match( m, mode ))
				set.add( m );
		}
		return new ArrayList<>( set );
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
		ArrayList<String> lst = new ArrayList<>( this.setSeries );
		Collections.sort( lst );
		return lst;
	}

	/** @return {@link List} list of inserted sun types. */
	public List<String> getInsertedSubTypes(){
		ArrayList<String> lst = new ArrayList<>( this.setSubType );
		Collections.sort( lst );
		return lst;
	}
}
