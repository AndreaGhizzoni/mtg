package com.hackcaffebabe.mtg.controller.json;

import static com.hackcaffebabe.mtg.controller.DBCostants.*;
import it.hackcaffebabe.logger.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackcaffebabe.mtg.controller.Criteria;
import com.hackcaffebabe.mtg.controller.json.adapter.*;
import com.hackcaffebabe.mtg.model.*;
import com.hackcaffebabe.mtg.model.card.Ability;
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.card.ManaCost;

/**
 * TODO add doc
 * TODO maybe parameterize all JSON key
 * TODO try to do an adapter for the entire class MTGCard.  
 *  
 * http://stackoverflow.com/questions/15731215/get-key-names-from-json-object-using-gson
 * http://www.mkyong.com/java/gson-streaming-to-read-and-write-json/
 * http://www.javacodegeeks.com/2012/04/json-with-gson-and-abstract-classes.html
 * http://examples.javacodegeeks.com/core-java/gson/stream/jsonwriter/gson-streaming-to-read-and-write-json-in-java-example/
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
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
		catch( Exception e ) {
			Logger.getInstance().write( Tag.ERRORS, e.getMessage() );
			e.printStackTrace();
			return null;
		}
	}
	
	private StoreManager() throws IOException{
		this.init();
		this.load();
		log.write( Tag.INFO, "Store manager initialized correctly." );
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
		b.registerTypeAdapter(ManaCost.class, new ManaCostAdapter());
		b.registerTypeAdapter(MTGCard.class, new MTGCardAdapter());// register the JSON adapter for MTGCard class
		b.registerTypeAdapter(Effect.class, new EffectAdapter());// register the JSON adapter for Effect class
		b.registerTypeAdapter(Ability.class, new AbilityAdapter());// register the JSON adapter for Ability class		
		g = b.create();	
	}
	
	/** 
	 * Load existing JSON file 
	 * @throws IOException 
	 */
	private void load() throws IOException{
		for(File f: new File(JSON_PATH).listFiles() ){
			FileReader br = new FileReader(f);
			mtgSet.add( g.fromJson( br, MTGCard.class ) );
			br.close();
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
		FileWriter f = new FileWriter( new File( getStoreJSONName(c)) );
		f.write( json );
		f.flush();
		f.close();		
		mtgSet.add( c );
		
		log.write( Tag.INFO, String.format( "MTG card json file %s saved correctly.", c.getName() ) );
		return true;
	}
	
	/**
	 * This method search a card with some {@link Criteria}.
	 * @param c {@link Criteria} to search the card.
	 * @return {@link HashSet} of {@link MTGCard}
	 */
	public HashSet<MTGCard> searchBy(Criteria c){
		if(c == null)
			return getAllCards();

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

			if(c.getType() != null) {
				if(c.getType().isInstance( m ))
					set.add( m );
			}

			if(c.getSubType() != null) {
				if(c.getSubType().replaceAll( " ", "" ).toLowerCase().equals( m.getSubType().replaceAll( " ", "" ).toLowerCase() ))
					set.add( m );
			}

			if(c.getSeries() != null) {
				if(c.getSeries().replaceAll( " ", "" ).toLowerCase().equals( m.getSeries().replaceAll( " ", "" ).toLowerCase() ))
					set.add( m );
			}

			if(c.getTypeColor() != null) {
				if(c.getTypeColor().equals( m.getCardColor().getType() ))
					set.add( m );
			}

			if(c.getColor() != null) {
				if(c.getColor().equals( m.getCardColor() ))
					set.add( m );
			}

			if(c.getRarity() != null) {
				if(c.getRarity().equals( m.getRarity() ))
					set.add( m );
			}

			if(c.getIsLegendary() != null) {
				if(c.getIsLegendary() == m.isLegendary())
					set.add( m );
			}
		}
		return set;
	}
	
	/**
	 * TODO maybe use the method on IOUtil ?
	 * This method creates a backup file in .zip format of all stored card.
	 * @param destinationFile {@link File} the file represents .zip.
	 */
	public void createBackup(File destinationFile){
		if(destinationFile == null)
			return;
		
		log.write( Tag.INFO, "Backup of all stored files called." );
		try {
			if(destinationFile.exists() && !destinationFile.delete()){
				log.write( Tag.ERRORS, "Error on delete exists backup." );
			}
			
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destinationFile));
			log.write( Tag.DEBUG, "Backup creation initialize." );
			for(File f : new File( STORE_PATH ).listFiles()){
				zos.putNextEntry( new ZipEntry(f.getName()) );
				
				FileInputStream in = new FileInputStream(f);
				byte[] buffer = new byte[1024];
				int len;
	    		while ((len = in.read(buffer)) > 0) {
	    			zos.write(buffer, 0, len);
	    		}
	    		in.close();
	    		zos.closeEntry();
			}
			log.write( Tag.DEBUG, "Files loaded on backup completely." );
			zos.close();
			log.write( Tag.DEBUG, "Backup closed correctly." );
		}
		catch( IOException e ) {
			log.write( Tag.ERRORS, e.getMessage() );
		}
	}
	
//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * @return {@link HashSet} of all saved card.
	 */
	public final HashSet<MTGCard> getAllCards(){
		return this.mtgSet;
	}
}
