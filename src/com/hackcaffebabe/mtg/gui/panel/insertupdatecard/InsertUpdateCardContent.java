package com.hackcaffebabe.mtg.gui.panel.insertupdatecard;

import static com.hackcaffebabe.mtg.controller.DBCostants.normalizeForStorage;
import static com.hackcaffebabe.mtg.gui.GUIUtils.AC_ARTIFACT;
import static com.hackcaffebabe.mtg.gui.GUIUtils.AC_CREATURE;
import static com.hackcaffebabe.mtg.gui.GUIUtils.AC_ENCHANTMENT;
import static com.hackcaffebabe.mtg.gui.GUIUtils.AC_INSTANT;
import static com.hackcaffebabe.mtg.gui.GUIUtils.AC_LAND;
import static com.hackcaffebabe.mtg.gui.GUIUtils.AC_PLANESWALKER;
import static com.hackcaffebabe.mtg.gui.GUIUtils.AC_SORCERY;
import static com.hackcaffebabe.mtg.gui.GUIUtils.DIMENSION_INSERT_CARD;
import static com.hackcaffebabe.mtg.gui.GUIUtils.PNL_MTGPROPERTIES;
import static com.hackcaffebabe.mtg.gui.GUIUtils.displayError;
import static com.hackcaffebabe.mtg.gui.GUIUtils.refreshMTGTable;
import static com.hackcaffebabe.mtg.gui.GUIUtils.showAbilityDialog;
import static com.hackcaffebabe.mtg.gui.GUIUtils.showPlanesAbilityDialog;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.DisplayableObject;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.frame.InsertUpdateCard;
import com.hackcaffebabe.mtg.gui.listener.AddEffectActionListener;
import com.hackcaffebabe.mtg.gui.listener.DelAbilityActionListener;
import com.hackcaffebabe.mtg.gui.listener.DelEffectActionListener;
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
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.card.Strength;
import com.hackcaffebabe.mtg.model.color.CardColor;


/**
 * The Insertion Card Content.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 3.2
 */
public class InsertUpdateCardContent extends JPanel
{
	private static final long serialVersionUID = 1L;
	private InsertUpdateCard parent;
	private TypeCardActionListener MTGTypeListener;

	private ButtonGroup mtgCardType = new ButtonGroup();
	private JRadioButton rdbCreature = new JRadioButton( "Creature" );
	private JRadioButton rdbArtifact = new JRadioButton( "Artifact" );
	private JRadioButton rdbPlanedwalker = new JRadioButton( "PW" );
	private JRadioButton rdbLand = new JRadioButton( "Land" );
	private JRadioButton rdbEnchantment = new JRadioButton( "Enchantment" );
	private JRadioButton rdbSorcery = new JRadioButton( "Sorcery" );
	private JRadioButton rdbInstant = new JRadioButton( "Instant" );

	private MTGBasicInfo pnlMTGBasicInfo;

	private JXTable tableAbility;
	private JXTableColumnAdjuster tableAbilityColumnAdjuster;
	private JButton btnAddAbility;
	private JButton btnDelAbility;

	private JTextArea txtPrimaryEffect;

	private JXTable tableEffects;
	private JXTableColumnAdjuster tableEffectsColumnAdjuster;
	private JButton btnAddEffect;
	private JButton btnDelEffect;

	private ManaCostInfo pnlManaCost;
	private CreatureInfo pnlCreatureInfo;
	private PlaneswalkerInfo pnlPlaneswalkerInfo;

	private JButton btnSaveOrUpdate;
	private JButton btnClear;

	private Logger log = Logger.getInstance();

	private MTGCard cardToUpdate;

	/**
	 * Instance the content. Pass null to insert new card, otherwise pass a card to update it.
	 * @param cardToUpdate {@link MTGCard}
	 */
	public InsertUpdateCardContent(MTGCard cardToUpdate, InsertUpdateCard parent){
		super();
		this.parent = parent;
		setSize( DIMENSION_INSERT_CARD );
		setLayout( new MigLayout( "", "[grow][grow]", "[60!][grow][60!]" ) );
		this.cardToUpdate = cardToUpdate;
		this.initContent();
		this.chois();
	}

	/* choose insert mode or update mode */
	private void chois(){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				if(cardToUpdate == null) { // if card to update is null, user want to insert new card
					disableAllInPanel();
					btnSaveOrUpdate.setText( "Save" );
				} else { // otherwise user want to update passing card
					populateContent();
					btnSaveOrUpdate.setText( "Update" );
					disableUnnecessaryComponentsForUpdates();
				}
			}
		} );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		// =========================== TYPE CARD PANEL ===========================
		JPanel pnlTypeCard = new JPanel();
		pnlTypeCard.setBorder( new TitledBorder( "Type" ) );
		pnlTypeCard.setLayout( new MigLayout( "", "[grow][grow][grow][grow][grow][grow][grow]",
				"[]" ) );
		add( pnlTypeCard, "cell 0 0 2 1,grow" );

		this.MTGTypeListener = new TypeCardActionListener();
		this.rdbCreature.setActionCommand( AC_CREATURE );
		this.rdbCreature.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbCreature, "cell 0 0" );

		this.rdbArtifact.setActionCommand( AC_ARTIFACT );
		this.rdbArtifact.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbArtifact, "cell 1 0" );

		this.rdbPlanedwalker.setActionCommand( AC_PLANESWALKER );
		this.rdbPlanedwalker.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbPlanedwalker, "cell 2 0" );

		this.rdbLand.setActionCommand( AC_LAND );
		this.rdbLand.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbLand, "cell 3 0" );

		this.rdbEnchantment.setActionCommand( AC_ENCHANTMENT );
		this.rdbEnchantment.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbEnchantment, "cell 4 0" );

		this.rdbSorcery.setActionCommand( AC_SORCERY );
		this.rdbSorcery.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbSorcery, "cell 5 0" );

		this.rdbInstant.setActionCommand( AC_INSTANT );
		this.rdbInstant.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbInstant, "cell 6 0" );

		mtgCardType.add( this.rdbCreature );
		mtgCardType.add( this.rdbArtifact );
		mtgCardType.add( this.rdbPlanedwalker );
		mtgCardType.add( this.rdbLand );
		mtgCardType.add( this.rdbEnchantment );
		mtgCardType.add( this.rdbSorcery );
		mtgCardType.add( this.rdbInstant );

		// =========================== MTG PANEL ===========================
		JPanel pnlMTG = new JPanel();
		pnlMTG.setBorder( new TitledBorder( "Card Info" ) );
		pnlMTG.setLayout( new MigLayout( "",
				"[grow][grow][grow][grow][grow][grow][100px:n,grow][29.00px:n]",
				"[][][][][::100,grow][::100,grow][28!][][::100,grow][::100,grow][][][grow]" ) );

		this.pnlMTGBasicInfo = new MTGBasicInfo();
		pnlMTG.add( this.pnlMTGBasicInfo, "cell 0 0 8 3,grow" );

		//================== Table Ability
		pnlMTG.add( new JLabel( "Ability:" ), "cell 0 3" );
		this.tableAbility = new JXTable( new JXObjectModel<>() );
		this.tableAbilityColumnAdjuster = new JXTableColumnAdjuster( this.tableAbility );
		pnlMTG.add( new JScrollPane( this.tableAbility, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), "cell 0 4 7 2,grow" );
		this.btnAddAbility = new JButton( "+" );
		this.btnAddAbility.addActionListener( new AddAbilityActionListener() );
		pnlMTG.add( this.btnAddAbility, "cell 7 4,grow" );
		this.btnDelAbility = new JButton( "X" );
		this.btnDelAbility.addActionListener( new DelAbilityActionListener( tableAbility,
				tableAbilityColumnAdjuster ) );
		pnlMTG.add( this.btnDelAbility, "cell 7 5,alignx center,growy" );

		//================== Primary Effect
		pnlMTG.add( new JLabel( "Primary Effect:" ), "cell 0 6" );
		this.txtPrimaryEffect = new JTextArea();
		this.txtPrimaryEffect.setLineWrap( true );
		this.txtPrimaryEffect.setWrapStyleWord( true );
		pnlMTG.add( new JScrollPane( txtPrimaryEffect, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), "cell 1 6 6 2,grow" );

		//================== Other Effect
		pnlMTG.add( new JLabel( "Other Effects:" ), "cell 0 7" );
		this.tableEffects = new JXTable( new JXObjectModel<>() );
		this.tableEffectsColumnAdjuster = new JXTableColumnAdjuster( this.tableEffects );
		pnlMTG.add( new JScrollPane( this.tableEffects, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), "cell 0 8 7 2,grow" );
		this.btnAddEffect = new JButton( "+" );
		this.btnAddEffect.addActionListener( new AddEffectActionListener( this, tableEffects,
				tableEffectsColumnAdjuster ) );
		pnlMTG.add( this.btnAddEffect, "cell 7 8,alignx center,growy" );
		this.btnDelEffect = new JButton( "X" );
		this.btnDelEffect.addActionListener( new DelEffectActionListener( tableEffects,
				tableEffectsColumnAdjuster ) );
		pnlMTG.add( this.btnDelEffect, "cell 7 9,alignx center,growy" );

		//================== Other Panels
		this.pnlManaCost = new ManaCostInfo();
		pnlMTG.add( this.pnlManaCost, "cell 0 10 8 1,grow" );
		this.pnlCreatureInfo = new CreatureInfo();
		pnlMTG.add( this.pnlCreatureInfo, "cell 0 11 6 1,grow" );
		this.pnlPlaneswalkerInfo = new PlaneswalkerInfo( 1 );
		pnlMTG.add( this.pnlPlaneswalkerInfo, "cell 6 11 2 1,grow" );

		add( pnlMTG, "cell 0 1 2 1,grow" );

		JPanel pnlOptions = new JPanel();
		pnlOptions.setLayout( new MigLayout( "", "[grow][][grow]", "[]" ) );
		pnlOptions.setBorder( new TitledBorder( "Options:" ) );
		this.btnClear = new JButton( "Clear" );
		this.btnClear.addActionListener( new ClearActionListener() );
		pnlOptions.add( this.btnClear, "cell 0 0,growx" );

		this.btnSaveOrUpdate = new JButton();// strings of button is set later than initContent
		this.btnSaveOrUpdate.addActionListener( new SaveOrUpdateActionListener() );
		pnlOptions.add( this.btnSaveOrUpdate, "cell 2 0,growx" );

		add( pnlOptions, "cell 0 2 2 1,grow" );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* in the case of cardToUpdate != null, selection card type MUST be disable */
	private void disableUnnecessaryComponentsForUpdates(){
		this.rdbCreature.setEnabled( false );
		this.rdbArtifact.setEnabled( false );
		this.rdbInstant.setEnabled( false );
		this.rdbSorcery.setEnabled( false );
		this.rdbEnchantment.setEnabled( false );
		this.rdbLand.setEnabled( false );
		this.rdbPlanedwalker.setEnabled( false );
		this.btnClear.setEnabled( false );
	}

	/* Reset all the panel and components into the panel */
	private void disableAllInPanel(){
		this.pnlMTGBasicInfo.reset();
		this.pnlManaCost.disableAllComponents();
		this.pnlCreatureInfo.disableAllComponents();
		this.pnlPlaneswalkerInfo.disableAllComponents();
		this.btnAddEffect.setEnabled( true );
		this.btnDelEffect.setEnabled( true );
		this.btnAddAbility.setEnabled( true );
		this.btnDelAbility.setEnabled( true );
		this.txtPrimaryEffect.setText( "" );
		this.txtPrimaryEffect.setEditable( true );
	}

	/* Reset the table effects and table abilities */
	private void clearEffectsAndAbilityTable(){
		tableAbility.setModel( new JXObjectModel<>() );
		tableEffects.setModel( new JXObjectModel<>() );
	}

	/* this method populate all the content with data of cardToUpdate */
	private void populateContent(){
		//this stuff is for select appropriate mtgType button
		switch( cardToUpdate.getClass().getSimpleName() ) {
			case AC_CREATURE: {
				rdbCreature.setSelected( true );
				break;
			}
			case AC_ARTIFACT: {
				rdbArtifact.setSelected( true );
				break;
			}
			case AC_INSTANT: {
				rdbInstant.setSelected( true );
				break;
			}
			case AC_SORCERY: {
				rdbSorcery.setSelected( true );
				break;
			}
			case AC_ENCHANTMENT: {
				rdbEnchantment.setSelected( true );
				break;
			}
			case AC_LAND: {
				rdbLand.setSelected( true );
				break;
			}
			case AC_PLANESWALKER: {
				rdbPlanedwalker.setSelected( true );
				break;
			}
		}
		//this is to call action event
		this.MTGTypeListener.actionPerformed( new ActionEvent( new Object(), 1, cardToUpdate
				.getClass().getSimpleName() ) );

		this.pnlMTGBasicInfo.setData( cardToUpdate );
		this.txtPrimaryEffect.setText( cardToUpdate.getPrimaryEffect() );

		if(!(cardToUpdate instanceof Planeswalker)) {
			if(cardToUpdate instanceof Creature)
				this.pnlCreatureInfo.setData( cardToUpdate );
			populateTables( Ability.class, cardToUpdate.getAbilities(), cardToUpdate.getEffects() );
		} else {
			populateTables( PlanesAbility.class,
					((Planeswalker) cardToUpdate).getPlanesAbilities(), cardToUpdate.getEffects() );
			this.pnlPlaneswalkerInfo.setData( cardToUpdate );
		}

		if(!(cardToUpdate instanceof Land)) {
			this.pnlManaCost.setData( this.cardToUpdate );
		}
	}

	/* this method populate the table ability and effects */
	private <T extends DisplayableObject> void populateTables(Class<T> abilityClaxx,
			Set<T> setOfAbilityClaxx, Set<Effect> setOfEffects){
		if(!setOfAbilityClaxx.isEmpty()) {
			JXObjectModel<T> model = new JXObjectModel<>();
			for(T a: setOfAbilityClaxx)
				model.addObject( a );
			this.tableAbility.setModel( model );
			this.tableAbilityColumnAdjuster.adjustColumns();
		}

		if(!setOfEffects.isEmpty()) {
			JXObjectModel<Effect> model = new JXObjectModel<>();
			for(Effect e: setOfEffects)
				model.addObject( e );
			this.tableEffects.setModel( model );
			this.tableEffectsColumnAdjuster.adjustColumns();
		}
	}

	/* save the MTG card with appropriate message */
	private void storeOrUpdate(MTGCard m){
		try {
			if(cardToUpdate == null) {
				if(StoreManager.getInstance().store( m )) {
					//reset all the form
					disableAllInPanel();
					clearEffectsAndAbilityTable();
					mtgCardType.clearSelection();
					MTGTypeListener.lastActionCommand = "";
					refreshMTGTable();

					JOptionPane.showMessageDialog( this, "Card saved correctly!", "Succes!",
							JOptionPane.INFORMATION_MESSAGE );
				} else {
					JOptionPane.showMessageDialog( this, "Card is already saved!", "Bad Luck!",
							JOptionPane.INFORMATION_MESSAGE );
				}
			} else {
				// cardToUpdate is the oldest card, m is the newest.
				if(StoreManager.getInstance().applyDifference( cardToUpdate, m )) {
					JOptionPane.showMessageDialog( this, "Card updated correctly!", "Succes!",
							JOptionPane.INFORMATION_MESSAGE );
					refreshMTGTable();
					PNL_MTGPROPERTIES.clearAll();
					this.parent.close();
				} else {
					JOptionPane.showMessageDialog( this, "No changes found.\nNothing to update.",
							"Bad Luck!", JOptionPane.INFORMATION_MESSAGE );
				}
			}
		} catch(Exception e) {
			String s = String.format( "%s\nLog is reported.", e.getMessage() );
			log.write( Tag.ERRORS, e.getMessage() );
			displayError( this, s );
		}
	}

	/**
	 * This method check the data from the user.
	 * @return {@link Boolean}
	 */
	private boolean checkUserData(){
		String mtgCardType = MTGTypeListener.lastActionCommand;
		if(mtgCardType.equals( "" ))
			return false;

		String mtgName = pnlMTGBasicInfo.getNames();
		if(mtgName == null || mtgName.isEmpty()) {
			displayError( this, "Name of MTG Card can not be void." );
			log.write( Tag.ERRORS, "Name of MTG card missing." );
			pnlMTGBasicInfo.requestFocus();
			return false;
		}
		if(normalizeForStorage( mtgName ).isEmpty()) {// if user insert "...." or "/////" as name.
			displayError( this, "Name not valid" );
			log.write( Tag.ERRORS, String.format( "Name %s not valid", mtgName ) );
			pnlMTGBasicInfo.requestFocus();
			return false;
		}

		String mtgSeries = pnlMTGBasicInfo.getSeries();
		if(mtgSeries == null || mtgSeries.isEmpty()) {
			displayError( this, "Series of MTG can not be void." );
			log.write( Tag.ERRORS, "Series of MTG card missing." );
			pnlMTGBasicInfo.requestFocus();
			return false;
		}
		if(normalizeForStorage( mtgSeries ).isEmpty()) {// if user insert "...." or "/////" as series.
			displayError( this, "Series not valid" );
			log.write( Tag.ERRORS, String.format( "Series %s not valid", mtgSeries ) );
			pnlMTGBasicInfo.requestFocus();
			return false;
		}

		if(!mtgCardType.equals( AC_LAND )) {
			ManaCost mtgManaCost = pnlManaCost.getManaCost();
			if(mtgManaCost == null) {
				displayError( this, "Mana cost of MTG Card can not be void." );
				log.write( Tag.ERRORS, "Mana cost of MTG card missing." );
				pnlManaCost.requestFocus();
				return false;
			}
		}

		if(mtgCardType.equals( AC_CREATURE )) {
			Strength creatureStrength = pnlCreatureInfo.getStrength();
			if(creatureStrength == null) {
				displayError( this, "Creature info missing." );
				log.write( Tag.ERRORS, "Creature strength missing." );
				pnlCreatureInfo.requestFocus();
				return false;
			}
		}

		if(mtgCardType.equals( AC_INSTANT ) || mtgCardType.equals( AC_SORCERY )
				|| mtgCardType.equals( AC_ENCHANTMENT )) {
			String mtgPrimaryEffect = normalizeForStorage( txtPrimaryEffect.getText() );
			if(mtgPrimaryEffect == null) {
				displayError( this, mtgCardType + " have a primary effect." );
				log.write( Tag.ERRORS, mtgCardType + "primary effect of MTG card missing." );
				txtPrimaryEffect.requestFocus();
				return false;
			}
		}

		if(mtgCardType.equals( AC_LAND )) {
			@SuppressWarnings("unchecked")
			List<Effect> mtgEffects = ((JXObjectModel<Effect>) tableEffects.getModel())
					.getObjects();
			if(mtgEffects.isEmpty()) {
				displayError( this, "Land must have at least one effect." );
				log.write( Tag.ERRORS, "Land primary effect of MTG card missing." );
				tableEffects.requestFocus();
				return false;
			}
		}

		return true;
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* inner class that describe the action on change mtgType; */
	private class TypeCardActionListener implements ActionListener
	{
		private String lastActionCommand = "";

		@Override
		public void actionPerformed(ActionEvent ev){
			String ac = ev.getActionCommand();
			if(!ac.equals( lastActionCommand )) {
				disableAllInPanel();
				clearEffectsAndAbilityTable();
				switch( ac ) { // enable appropriate panel.
					case AC_CREATURE: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlManaCost.enableAllComponents();
						pnlCreatureInfo.enableAllComponents();
						break;
					}
					case AC_ARTIFACT: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						pnlManaCost.enableAllComponents();
						break;
					}
					case AC_PLANESWALKER: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						pnlManaCost.enableAllComponents();
						pnlPlaneswalkerInfo.enableAllComponents();
						btnAddEffect.setEnabled( false );
						btnDelEffect.setEnabled( false );
						txtPrimaryEffect.setEditable( false );
						break;
					}
					case AC_LAND: {
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						pnlMTGBasicInfo.setAllColorsEnable( false );
						btnAddAbility.setEnabled( false );
						btnDelAbility.setEnabled( false );
						break;
					}
					case AC_INSTANT: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						pnlManaCost.enableAllComponents();
						btnAddEffect.setEnabled( false );
						btnDelEffect.setEnabled( false );
						break;
					}
					case AC_SORCERY: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlManaCost.enableAllComponents();
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						btnAddEffect.setEnabled( false );
						btnDelEffect.setEnabled( false );
						break;
					}
					case AC_ENCHANTMENT: {
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlManaCost.enableAllComponents();
						break;
					}
				}
				lastActionCommand = ac;
			}
		}
	}

	/* inner class that describe the action on btnClear */
	private class ClearActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			//reset all the form
			disableAllInPanel();
			clearEffectsAndAbilityTable();
			mtgCardType.clearSelection();
			MTGTypeListener.lastActionCommand = "";
		}
	}

	/* inner class that describe the action on btnSave */
	private class SaveOrUpdateActionListener implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e){
			if(!checkUserData())
				return;

			String mtgCardType = MTGTypeListener.lastActionCommand;

			log.write( Tag.INFO, "New card start to save..." );
			log.write( Tag.DEBUG, "type to save = " + mtgCardType );

			String mtgName = pnlMTGBasicInfo.getNames();
			log.write( Tag.DEBUG, "name = " + mtgName );

			Rarity mtgRarity = pnlMTGBasicInfo.getRarity();
			log.write( Tag.DEBUG, "rarity = " + mtgRarity );

			CardColor mtgCardColor = pnlMTGBasicInfo.getCardColor();
			log.write( Tag.DEBUG, "card color = " + mtgCardColor.toString() );

			boolean isLegendary = pnlMTGBasicInfo.isLegendarySelected();
			log.write( Tag.DEBUG, "is legendary = " + isLegendary );

			String mtgSeries = pnlMTGBasicInfo.getSeries();
			log.write( Tag.DEBUG, "series = " + mtgSeries );

			String mtgSubType = pnlMTGBasicInfo.getSubType();
			if(mtgSubType == null)
				mtgSubType = "";
			log.write( Tag.DEBUG, "sub type = " + mtgSubType );

			String mtgPrimaryEffect = txtPrimaryEffect.getText();// if no text is inserted, "" is returned by getText()
			log.write( Tag.DEBUG, "primary effect = " + mtgPrimaryEffect );

			List<Effect> mtgEffects = ((JXObjectModel<Effect>) tableEffects.getModel())
					.getObjects();
			log.write( Tag.DEBUG, "effects = " + mtgEffects.toString() );

			ManaCost mtgManaCost = null;
			if(!mtgCardType.equals( AC_LAND )) {
				mtgManaCost = pnlManaCost.getManaCost();
				log.write( Tag.DEBUG, "mana cost = " + mtgManaCost );
			}

			switch( mtgCardType ) {
				case AC_CREATURE: {
					Strength creatureStrength = pnlCreatureInfo.getStrength();
					log.write( Tag.DEBUG, "creature strength = " + creatureStrength );

					boolean isArtifact = pnlMTGBasicInfo.isArtifactSelected();
					log.write( Tag.DEBUG, "is artifact = " + isArtifact );

					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel())
							.getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Creature finalCreature = new Creature( mtgName, mtgCardColor, creatureStrength,
							mtgManaCost, mtgSubType, mtgRarity );
					finalCreature.setArtifact( isArtifact );
					finalCreature.setSeries( mtgSeries );
					finalCreature.setSubType( mtgSubType );
					finalCreature.setLegendary( isLegendary );
					finalCreature.setPrimaryEffect( mtgPrimaryEffect );
					for(Effect eff: mtgEffects) {
						finalCreature.addEffect( eff );
					}
					for(Ability abi: mtgAbility) {
						finalCreature.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalCreature.toString() );
					storeOrUpdate( finalCreature );

					break;
				}
				case AC_ARTIFACT: {
					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel())
							.getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Artifact finalArtifact = new Artifact( mtgName, mtgManaCost, mtgCardColor,
							mtgRarity );
					finalArtifact.setLegendary( isLegendary );
					finalArtifact.setSeries( mtgSeries );
					finalArtifact.setSubType( mtgSubType );
					finalArtifact.setPrimaryEffect( mtgPrimaryEffect );
					for(Effect eff: mtgEffects) {
						finalArtifact.addEffect( eff );
					}
					for(Ability abi: mtgAbility) {
						finalArtifact.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalArtifact.toString() );
					storeOrUpdate( finalArtifact );

					break;
				}
				case AC_PLANESWALKER: {
					int mtgLife = pnlPlaneswalkerInfo.getPlaneswalkerLife();
					log.write( Tag.DEBUG, "planeswalker life = " + mtgLife );

					List<PlanesAbility> mtgAbility = ((JXObjectModel<PlanesAbility>) tableAbility
							.getModel()).getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Planeswalker finalPlaneswalker = new Planeswalker( mtgName, mtgManaCost,
							mtgLife, mtgCardColor, mtgRarity );
					finalPlaneswalker.setSeries( mtgSeries );
					finalPlaneswalker.setSubType( mtgSubType );
					for(PlanesAbility abi: mtgAbility) {
						finalPlaneswalker.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalPlaneswalker.toString() );
					storeOrUpdate( finalPlaneswalker );

					break;
				}
				case AC_INSTANT: {
					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel())
							.getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Instant finalInstant = new Instant( mtgName, mtgManaCost, mtgCardColor,
							mtgRarity );
					finalInstant.setPrimaryEffect( mtgPrimaryEffect );
					finalInstant.setSeries( mtgSeries );
					finalInstant.setSubType( mtgSubType );
					finalInstant.setLegendary( isLegendary );
					for(Ability abi: mtgAbility) {
						finalInstant.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalInstant.toString() );
					storeOrUpdate( finalInstant );

					break;
				}
				case AC_SORCERY: {
					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel())
							.getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Sorcery finalSorcery = new Sorcery( mtgName, mtgManaCost, mtgCardColor,
							mtgRarity );
					finalSorcery.setPrimaryEffect( mtgPrimaryEffect );
					finalSorcery.setSeries( mtgSeries );
					finalSorcery.setSubType( mtgSubType );
					finalSorcery.setLegendary( isLegendary );
					for(Ability abi: mtgAbility) {
						finalSorcery.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalSorcery.toString() );
					storeOrUpdate( finalSorcery );

					break;
				}
				case AC_ENCHANTMENT: {
					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel())
							.getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Enchantment finalEnchantment = new Enchantment( mtgName, mtgManaCost,
							mtgCardColor, mtgRarity );
					finalEnchantment.setPrimaryEffect( mtgPrimaryEffect );
					finalEnchantment.setSeries( mtgSeries );
					finalEnchantment.setSubType( mtgSubType );
					finalEnchantment.setLegendary( isLegendary );
					for(Ability abi: mtgAbility) {
						finalEnchantment.addAbility( abi );
					}
					for(Effect eff: mtgEffects) {
						finalEnchantment.addEffect( eff );
					}

					log.write( Tag.DEBUG, finalEnchantment.toString() );
					storeOrUpdate( finalEnchantment );

					break;
				}
				case AC_LAND: {
					Land finalLand = new Land( mtgName, mtgRarity );
					finalLand.setPrimaryEffect( mtgPrimaryEffect );
					finalLand.setSeries( mtgSeries );
					finalLand.setSubType( mtgSubType );
					for(Effect eff: mtgEffects) {
						finalLand.addEffect( eff );
					}

					log.write( Tag.DEBUG, finalLand.toString() );
					storeOrUpdate( finalLand );

					break;
				}
			}
		}
	}

	/* inner class that describe the action on btnAddActivity; */
	private class AddAbilityActionListener implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e){
			if(MTGTypeListener.lastActionCommand.equals( AC_PLANESWALKER )) {
				PlanesAbility p = showPlanesAbilityDialog( InsertUpdateCardContent.this );
				if(p != null) {
					JXObjectModel<PlanesAbility> model = (JXObjectModel<PlanesAbility>) tableAbility
							.getModel();
					if(model.getRowCount() == 0) {
						tableAbility
								.setModel( new JXObjectModel<PlanesAbility>( Arrays.asList( p ) ) );
					} else {
						model.addObject( p );
					}
					tableAbilityColumnAdjuster.adjustColumns();
				}
			} else {
				Ability a = showAbilityDialog( InsertUpdateCardContent.this );
				if(a != null) {
					JXObjectModel<Ability> model = (JXObjectModel<Ability>) tableAbility.getModel();
					if(model.getRowCount() == 0) {
						tableAbility.setModel( new JXObjectModel<Ability>( Arrays.asList( a ) ) );
					} else {
						model.addObject( a );
					}
					tableAbilityColumnAdjuster.adjustColumns();
				}
			}
		}
	}
}
