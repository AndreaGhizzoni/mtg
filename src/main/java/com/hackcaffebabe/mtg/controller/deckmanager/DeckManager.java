package com.hackcaffebabe.mtg.controller.deckmanager;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.jx.tree.JXTree;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;
import com.hackcaffebabe.mtg.controller.Paths;
import com.hackcaffebabe.mtg.gui.GUIUtils;
import com.hackcaffebabe.mtg.gui.components.deckeditor.DeckTreeNode;
import com.hackcaffebabe.mtg.gui.components.deckeditor.DeckTreeNode.TREENODE_TYPE;


/**
 * This class manage the saving, deleting and renaming of all decks saved on disk.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DeckManager
{
	private static DeckManager instance = null;

	private Logger log;
	private ArrayList<File> savedDecks;

	/**
	 * Returns the instance of deck manager.
	 * @return {@link DeckManager} the instance of deck manager.
	 */
	public static DeckManager getInstance(){
		if(instance == null)
			instance = new DeckManager();
		return instance;
	}

	/* Instance the deck manager */
	private DeckManager(){
		this.log = Logger.getInstance();
		this.savedDecks = new ArrayList<>();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * This method check the deck folder and read only the mtgdeck file.<br>
	 * If is passed an instance of {@link JXTree}, all the deck folder will be printed on the tree.
	 * @param tree {@link JXTree} the tree to print the deck folder.
	 */
	public void refreshDecks(final JXTree tree){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				long start = System.currentTimeMillis();
				log.write( Tag.INFO, "Refreshing deck list..." );

				savedDecks.clear();
				File[] files = new File( Paths.DECKS_PATH ).listFiles();
				for(File f: files) {
					if(f.getName().endsWith( "mtgdeck" ))
						savedDecks.add( f );
				}
				Collections.sort( savedDecks );
				if(tree != null) {
					printDecks( tree );
				}

				long end = System.currentTimeMillis();
				log.write( Tag.INFO, String.format( "Refreshing deck list done in %dms", (end - start) ) );
			}
		} );
	}

	/**
	 * This method prints only the current list of decks on a {@link JXTree}.
	 * @param tree {@link JXTree} the tree to print the deck folder.
	 */
	public void printDecks(JXTree tree){
		DeckTreeNode decks = new DeckTreeNode( "Decks", TREENODE_TYPE.ROOT );
		for(File f: savedDecks)
			decks.add( new DeckTreeNode( f.getName().split( "[.]" )[0], TREENODE_TYPE.DECK ) );// exclude the extension *.mtgdeck
		tree.setModel( new DefaultTreeModel( decks ) );
	}

	/**
	 * This method save a deck with a name and his content.<br>
	 * This method spawn a a thread that effectually do the save.<br>
	 * If there is a deck with the same name, this method WILL OVERRIDE the oldest!.
	 * @param nameOfDeck {@link String} the name of the file deck.
	 * @param content {@link String} the deck content.
	 * @throws IllegalArgumentException if argument given are null or empty string.
	 */
	public void save(final String nameOfDeck, final String content) throws IllegalArgumentException{
		if(nameOfDeck == null || nameOfDeck.isEmpty())
			throw new IllegalArgumentException( "Deck name can not be null or empty string." );
		if(content == null)
			throw new IllegalArgumentException( "New name can not be null or empty string." );

		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				long start = System.currentTimeMillis();
				log.write( Tag.INFO, "Save action called..." );

				File f = new File( getPath( nameOfDeck ) );
				if(!savedDecks.contains( f ))
					savedDecks.add( f );

				try {
					Writer w = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( f ), "utf-8" ) );
					w.write( content );
					w.flush();
					w.close();
				} catch(IOException e) {
					GUIUtils.displayError( null, e );
				}

				long end = System.currentTimeMillis();
				log.write( Tag.INFO, String.format( "Deck %s saved correctly in %dms.", nameOfDeck, (end - start) ) );
			}
		} );
	}

	/**
	 * This method delete a deck giving the deck name. If there isn't deck whit that name, this method do nothing.
	 * @param deckName {@link String} the deck name to delete.
	 * @throws IllegalArgumentException if deck name is null or empty string.
	 */
	public void delete(String deckName) throws IllegalArgumentException{
		if(deckName == null || deckName.isEmpty())
			throw new IllegalArgumentException( "Deck name can not be null or empty string." );

		long start = System.currentTimeMillis();
		log.write( Tag.INFO, "Delete deck called..." );
		String path = getPath( deckName );
		File fileName = new File( path );
		if(this.savedDecks.contains( fileName )) {
			this.savedDecks.remove( fileName );
			fileName.delete();
			long end = System.currentTimeMillis();
			log.write( Tag.INFO, String.format( "%s delete properly in %dms", deckName, (end - start) ) );
		} else {
			log.write( Tag.INFO, String.format( "No file name found for deck %s", deckName ) );
		}
	}

	/**
	 * This method rename the deck name as first argument with the name as second argument.
	 * @param deckName {@link String} the name of file to rename.
	 * @param newName {@link String} the new name of the file.
	 * @throws IllegalArgumentException if some argument is null or empty string.
	 */
	public void rename(String deckName, String newName) throws IllegalArgumentException{
		if(deckName == null || deckName.isEmpty())
			throw new IllegalArgumentException( "Deck name can not be null or empty string." );

		if(newName == null || newName.isEmpty())
			throw new IllegalArgumentException( "New name can not be null or empty string." );

		long start = System.currentTimeMillis();
		log.write( Tag.INFO, "Rename deck called..." );
		String f = getPath( deckName );
		String n = getPath( newName );
		new File( f ).renameTo( new File( n ) );
		long end = System.currentTimeMillis();
		log.write( Tag.INFO, String.format( "Rename %s -> %s in %dms", deckName, newName, (end - start) ) );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * This method returns the deck content from a name of the deck.<br>
	 * If there is no deck with this name, null is returned.
	 * @param deckName {@link String} the deck name.
	 * @return {@link String} the content of deck passed as argument, or null.
	 * @throws IllegalArgumentException if arguments are null or empty string.
	 * @throws IOException if errors encounter while reading the file.
	 */
	public String get(String deckName) throws IllegalArgumentException, IOException{
		if(deckName == null || deckName.isEmpty())
			throw new IllegalArgumentException( "Deck name can not be null or empty string." );

		String path = getPath( deckName );
		File f = new File( path );

		if(this.savedDecks.contains( f )) {
			return PathUtil.readContent( f );
		} else {
			return null;
		}
	}

	/**
	 * This method check if specific deck is already saved or not.
	 * @param deckName {@link String} the deck's name.
	 * @return true if decks already exists on disk, otherwise false.
	 */
	public boolean exists(String deckName){
		if(deckName == null || deckName.isEmpty() || !this.savedDecks.contains( new File( getPath( deckName ) ) ))
			return false;
		return true;
	}

	/* helper method */
	private String getPath(String s){
		return String.format( "%s%s%s.%s", Paths.DECKS_PATH, PathUtil.FILE_SEPARATOR, s, Paths.DECKS_EXT );
	}
}
