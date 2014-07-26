package com.hackcaffebabe.mtg.gui.frame;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import com.hackcaffebabe.mtg.gui.ActionCommand;
import com.hackcaffebabe.mtg.gui.FramesDimensions;
import com.hackcaffebabe.mtg.gui.FramesTitles;
import com.hackcaffebabe.mtg.gui.panel.insertupdatecard.InsertUpdateCardContent;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * This is the main frame that contains all components to insert the MTG card data.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class InsertUpdateCard extends JFrame
{
	private static final long serialVersionUID = 1L;
	private InsertUpdateCardContent content;
	private MTGCard cardToView;

	/**
	 * Instance a frame to view or update a {@link MTGCard}
	 * @param cardToView {@link MTG} or null if user want to insert a card.
	 */
	public InsertUpdateCard(MTGCard cardToView){
		super( cardToView == null ? FramesTitles.TITLE_INSERT_CARD : FramesTitles.TITLE_UPDATE_CARD );
		setMinimumSize( FramesDimensions.DIMENSION_INSERT_CARD );
		setLocation( FramesDimensions.getCenter( FramesDimensions.DIMENSION_INSERT_CARD ) );
		this.cardToView = cardToView;
		this.initMenuBar();
		this.initContent( cardToView );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(MTGCard cardToView){
		this.content = new InsertUpdateCardContent( cardToView, this );
		this.content.setOpaque( true );
		setContentPane( this.content );
	}

	/* Initialize the menu bar */
	private void initMenuBar(){
		JMenuBar bar = new JMenuBar();

		JMenu file = new JMenu( "File" );
		JMenuItem fileClose = new JMenuItem( "Close" );
		fileClose.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_Q, Event.CTRL_MASK ) );
		fileClose.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				close();
			}
		} );
		file.add( fileClose );

		bar.add( file );

		// generate the Type menu of type ( if is in insert mode )
		// based on the ActionCommand name if is not in update mode.
		if(this.cardToView == null) {
			JMenu type = new JMenu( "Type" );

			ButtonGroup buttonGroup = new ButtonGroup();
			List<Integer> vks = Arrays.asList( KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4,
					KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7 );
			int i = 0;
			for(final String s: Arrays.asList( ActionCommand.CREATURE, ActionCommand.ARTIFACT,
					ActionCommand.PLANESWALKER, ActionCommand.LAND, ActionCommand.ENCHANTMENT,
					ActionCommand.SORCERY, ActionCommand.INSTANT )) {
				JRadioButtonMenuItem currentTypeSelector = new JRadioButtonMenuItem( s );
				currentTypeSelector.addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent e){
						content.selectCardType( s );
					}
				} );
				currentTypeSelector.setAccelerator( KeyStroke.getKeyStroke( vks.get( i++ ), Event.CTRL_MASK ) );
				buttonGroup.add( currentTypeSelector );
				type.add( currentTypeSelector );
			}
			type.addSeparator();

			JMenuItem typeClear = new JMenuItem( "Clear" );
			typeClear.addActionListener( new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					content.disableAllInPanel();
					content.clearEffectsAndAbilityTable();
				}
			} );
			type.add( typeClear );

			bar.add( type );
		}

		setJMenuBar( bar );
	}

	/**
	 * Close the frame
	 */
	public void close(){
		dispose();
	}
}
