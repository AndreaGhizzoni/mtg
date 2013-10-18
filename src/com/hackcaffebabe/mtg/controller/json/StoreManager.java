package com.hackcaffebabe.mtg.controller.json;

import static com.hackcaffebabe.mtg.controller.DBCostants.*;
import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.logger.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackcaffebabe.mtg.controller.json.adapter.*;
import com.hackcaffebabe.mtg.model.*;
import com.hackcaffebabe.mtg.model.card.*;
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
		}
		catch(Exception e) {
			e.printStackTrace( Logger.getInstance().getPrintStream() );
			return null;
		}
	}

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
	private void load() throws IOException{
		for(File f: new File( JSON_PATH ).listFiles()) {
			FileReader br = new FileReader( f );
			mtgSet.add( g.fromJson( br, MTGCard.class ) );
			br.close();
		}
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
		}
		catch(Exception e) {
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

		String json = g.toJson( c, MTGCard.class );
		FileWriter f = new FileWriter( new File( getStoreJSONName( c ) ) );
		f.write( json );
		f.flush();
		f.close();
		mtgSet.add( c );

		log.write( Tag.INFO, String.format( "MTG card json file %s saved correctly.", c.getName() ) );
		return true;
	}

	/**
	 * This method read all files into data/mtg.
	 * @throws IOException if read all files fail.
	 */
	public void refresh() throws IOException{
		this.mtgSet.clear();
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
				if(c.getName().replaceAll( " ", "" ).toLowerCase().equals( m.getName().replaceAll( " ", "" ).toLowerCase() ))
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

//			if(c.getType() != null) {
//				if( m.getClass().isInstance( c.getType() ))
//					set.add( m );
//			}

			if(c.getSubType() != null) {
				if(c.getSubType().replaceAll( " ", "" ).toLowerCase().equals( m.getSubType().replaceAll( " ", "" ).toLowerCase() ))
					set.add( m );
			}

			if(c.getSeries() != null) {
				if(c.getSeries().replaceAll( " ", "" ).toLowerCase().equals( m.getSeries().replaceAll( " ", "" ).toLowerCase() ))
					set.add( m );
			}

			for(BasicColors a: c.getColors()) {
				if(m.getCardColor().getBasicColors().contains( a )) {
					set.add( m );
					break;
				}
			}

//			if(c.getTypeColor() != null) {
//				if(c.getTypeColor().equals( m.getCardColor().getType() ))
//					set.add( m );
//			}
//
//			if(c.getColor() != null) {
//				if(c.getColor().equals( m.getCardColor() ))
//					set.add( m );
//			}

			if(c.getRarity() != null) {
				if(c.getRarity() == m.getRarity())
					set.add( m );
			}

			if(c.getIsLegendary() != null) {
				if(c.getIsLegendary() == m.isLegendary())
					set.add( m );
			}
			
			if(c.getHasPrimaryEffect() != null && c.getHasPrimaryEffect() ){
				if( m.getPrimaryEffect()!=null && !m.getPrimaryEffect().isEmpty() )
					set.add( m );
			}
			
			if(c.getHasEffect() != null && c.getHasEffect() ){
				if( !m.getEffects().isEmpty() )
					set.add( m );
			}
			
			if(c.getHasAbility() != null && c.getHasAbility() ){
				if( !m.getAbilities().isEmpty() )
					set.add( m );
			}
		}
		return new ArrayList<>( set );
	}

	/**
	 * This method creates a backup file in .zip format of all stored card.
	 * @param destinationFile {@link File} the file represents .zip.
	 */
	public void createBackup(File destinationFile){
		if(destinationFile == null)
			return;

		log.write( Tag.INFO, String.format( "%s %s", "Try to backup of all stored files on", destinationFile.getAbsolutePath() ) );
		try {
			if(destinationFile.exists() && !destinationFile.delete()) {
				log.write( Tag.ERRORS, "Error on delete exists backup." );
			}

			PathUtil.makeZip( destinationFile, new File( JSON_PATH ).listFiles() );
			log.write( Tag.DEBUG, "Backup closed and create correctly." );
		}
		catch(IOException e) {
			log.write( Tag.ERRORS, e.getMessage() );
		}
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * @return {@link HashSet} of all saved card.
	 */
	public HashSet<MTGCard> getAllCards(){
		return this.mtgSet;
	}

	/**
	 * @return {@link List} of all saved card.
	 */
	public List<MTGCard> getAllCardsAsList(){
		return new ArrayList<>( this.mtgSet );
	}
}
