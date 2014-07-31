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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;
import com.hackcaffebabe.mtg.controller.Paths;
import com.hackcaffebabe.mtg.gui.GUIUtils;
import com.hackcaffebabe.mtg.gui.components.deckeditor.DeckTreeNode;
import com.hackcaffebabe.mtg.gui.components.deckeditor.DeckTreeNode.TREENODE_TYPE;


/**
 * TODO add description  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DeckManager
{
	private static DeckManager instance = null;

	private Logger log;
	private List<File> savedDecks;

	/**
	 * TODO add doc
	 * @return
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
		this.refreshDecks( null );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * TODO add doc
	 * @param tree
	 */
	public void refreshDecks(final JXTree tree){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				log.write( Tag.DEBUG, "Refreshing deck list..." );

				savedDecks = Arrays.asList( new File( Paths.DECKS_PATH ).listFiles() );
				Collections.sort( savedDecks );
				if(tree != null) {
					printDecks( tree );
				}

				log.write( Tag.DEBUG, "Refreshing deck list done." );
			}
		} );
	}

	/**
	 * TODO add doc
	 * @param tree
	 */
	public void printDecks(JXTree tree){
		DeckTreeNode decks = new DeckTreeNode( "Decks", TREENODE_TYPE.ROOT );
		for(File f: savedDecks)
			decks.add( new DeckTreeNode( f.getName().split( "[.]" )[0], TREENODE_TYPE.DECK ) );// exclude the extension *.mtgdeck
		tree.setModel( new DefaultTreeModel( decks ) );
	}

	/**
	 * TODO add doc and finish exceptions
	 * @param nameOfDeck
	 * @param content
	 * @throws IllegalArgumentException
	 */
	public void save(final String nameOfDeck, final String content) throws IllegalArgumentException{
		if(nameOfDeck == null || nameOfDeck.isEmpty())
			throw new IllegalArgumentException( "" );
		if(content == null)
			throw new IllegalArgumentException( "" );

		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				log.write( Tag.DEBUG, "Save action called..." );

				String deckPath = String.format( "%s%s%s.mtgdeck", Paths.DECKS_PATH, PathUtil.FILE_SEPARATOR,
						nameOfDeck );
				File f = new File( deckPath );
				if(!savedDecks.contains( f ))
					savedDecks.add( f );

				try {
					Writer w = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( deckPath ), "utf-8" ) );
					w.write( content );
					w.flush();
					w.close();
				} catch(IOException e) {
					GUIUtils.displayError( null, e );
				}

				log.write( Tag.DEBUG, String.format( "Deck %s saved correctly.", nameOfDeck ) );
			}
		} );
	}

	/**
	 * 
	 * @param deckName
	 * @throws IllegalArgumentException
	 */
	public void delete(String deckName) throws IllegalArgumentException{
		if(deckName == null || deckName.isEmpty())
			throw new IllegalArgumentException( "" );

		log.write( Tag.DEBUG, "Delete deck called..." );
		String path = String.format( "%s%s%s.mtgdeck", Paths.DECKS_PATH, PathUtil.FILE_SEPARATOR, deckName );
		File fileName = new File( path );
		if(this.savedDecks.contains( fileName )) {
			this.savedDecks.remove( fileName );
			fileName.delete();
			log.write( Tag.DEBUG, fileName + " delete properly." );
		}
		log.write( Tag.DEBUG, "No file name found for deck " + fileName );
	}

	/**
	 * TODO add doc and finish exception
	 * @param deckName
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void rename(String deckName, String newName) throws IllegalArgumentException{
		if(deckName == null || deckName.isEmpty())
			throw new IllegalArgumentException( "" );

		log.write( Tag.DEBUG, "Rename deck called..." );
		String f = String.format( "%s%s%s.mtgdeck", Paths.DECKS_PATH, PathUtil.FILE_SEPARATOR, deckName );
		String n = String.format( "%s%s%s.mtgdeck", Paths.DECKS_PATH, PathUtil.FILE_SEPARATOR, newName );
		new File( f ).renameTo( new File( n ) );
		log.write( Tag.DEBUG, "Rename " + deckName + " -> " + newName );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * TODO add doc and finish exception
	 * @param deckName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public String get(String deckName) throws IllegalArgumentException, IOException{
		if(deckName == null || deckName.isEmpty())
			throw new IllegalArgumentException( "" );

		String path = String.format( "%s%s%s.mtgdeck", Paths.DECKS_PATH, PathUtil.FILE_SEPARATOR, deckName );
		File f = new File( path );

		if(this.savedDecks.contains( f )) {
			return PathUtil.readContent( f );
		} else {
			return null;
		}
	}
}
