package com.hackcaffebabe.mtg.gui.panel.insertcard;

import static com.hackcaffebabe.mtg.gui.GUIUtils.*;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.util.Arrays;
import java.util.List;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.panel.mtg.MTGContent;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.gui.listener.*;
import com.hackcaffebabe.mtg.model.*;
import com.hackcaffebabe.mtg.model.card.*;


/**
 * The Insertion Card Content.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 2.1
 */
public class InsertCardContent extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JPanel pnlMTG = new JPanel();
	private MTGTypeCardActionListener MTGTypeListener;
	private ButtonGroup mtgCardType;

	private MTGBasicInfo pnlMTGBasicInfo = new MTGBasicInfo();

	private JXTable tableAbility;
	private JXTableColumnAdjuster tableAbilityColumnAdjuster;
	private JButton btnAddAbility;
	private JButton btnDelAbility;

	private JTextArea txtPrimaryEffect;

	private JXTable tableEffects;
	private JXTableColumnAdjuster tableEffectsColumnAdjuster;
	private JButton btnAddEffect;
	private JButton btnDelEffect;

	private ManaCostInfo pnlManaCost = new ManaCostInfo();
	private CreatureInfo pnlCreatureInfo = new CreatureInfo();
	private PlaneswalkerInfo pnlPlaneswalkerInfo = new PlaneswalkerInfo( 1 );

	private JButton btnSave;
	private JButton btnClear;

	private Logger log = Logger.getInstance();
	private MTGContent mtgList;

	/**
	 * Create the panel.
	 */
	public InsertCardContent(MTGContent c){
		super();
		setSize( DIMENSION_INSERT_CARD );
		setLayout( new MigLayout( "", "[grow][grow]", "[60!][grow][60!]" ) );
		this.initContent();
		this.disableAllInPanel();
		mtgList = c;
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		// =========================== TYPE CARD PANEL ===========================
		JPanel pnlTypeCard = new JPanel();
		pnlTypeCard.setBorder( new TitledBorder( "Type" ) );
		pnlTypeCard.setLayout( new MigLayout( "", "[grow][grow][grow][grow][grow][grow][grow]", "[]" ) );
		add( pnlTypeCard, "cell 0 0 2 1,grow" );

		this.MTGTypeListener = new MTGTypeCardActionListener();

		JRadioButton rdbCreature = new JRadioButton( "Creature" );
		rdbCreature.setActionCommand( AC_CREATURE );
		rdbCreature.addActionListener( MTGTypeListener );
		pnlTypeCard.add( rdbCreature, "cell 0 0" );

		JRadioButton rdbArtifact = new JRadioButton( "Artifact" );
		rdbArtifact.setActionCommand( AC_ARTIFACT );
		rdbArtifact.addActionListener( MTGTypeListener );
		pnlTypeCard.add( rdbArtifact, "cell 1 0" );

		JRadioButton rdbPlanedwalker = new JRadioButton( "PW" );
		rdbPlanedwalker.setActionCommand( AC_PLANESWALKER );
		rdbPlanedwalker.addActionListener( MTGTypeListener );
		pnlTypeCard.add( rdbPlanedwalker, "cell 2 0" );

		JRadioButton rdbLand = new JRadioButton( "Land" );
		rdbLand.setActionCommand( AC_LAND );
		rdbLand.addActionListener( MTGTypeListener );
		pnlTypeCard.add( rdbLand, "cell 3 0" );

		JRadioButton rdbEnchantment = new JRadioButton( "Enchantment" );
		rdbEnchantment.setActionCommand( AC_ENCHANTMENT );
		rdbEnchantment.addActionListener( MTGTypeListener );
		pnlTypeCard.add( rdbEnchantment, "cell 4 0" );

		JRadioButton rdbSorcery = new JRadioButton( "Sorcery" );
		rdbSorcery.setActionCommand( AC_SORCERY );
		rdbSorcery.addActionListener( MTGTypeListener );
		pnlTypeCard.add( rdbSorcery, "cell 5 0" );

		JRadioButton rdbInstant = new JRadioButton( "Instant" );
		rdbInstant.setActionCommand( AC_INSTANT );
		rdbInstant.addActionListener( MTGTypeListener );
		pnlTypeCard.add( rdbInstant, "cell 6 0" );

		this.mtgCardType = new ButtonGroup();
		mtgCardType.add( rdbCreature );
		mtgCardType.add( rdbArtifact );
		mtgCardType.add( rdbPlanedwalker );
		mtgCardType.add( rdbLand );
		mtgCardType.add( rdbEnchantment );
		mtgCardType.add( rdbSorcery );
		mtgCardType.add( rdbInstant );

		// =========================== MTG PANEL ===========================
		pnlMTG.setBorder( new TitledBorder( "Card Info" ) );
		pnlMTG.setLayout( new MigLayout( "", "[grow][grow][grow][grow][grow][grow][100px:n,grow][29.00px:n]",
				"[][][][][::100,grow][::100,grow][28!][][::100,grow][::100,grow][][][grow]" ) );

		pnlMTG.add( this.pnlMTGBasicInfo, "cell 0 0 8 3,grow" );

		pnlMTG.add( new JLabel( "Ability:" ), "cell 0 3" );
		this.tableAbility = new JXTable( new JXObjectModel<>() );
		this.tableAbilityColumnAdjuster = new JXTableColumnAdjuster( this.tableAbility );
		pnlMTG.add( new JScrollPane( this.tableAbility ), "cell 0 4 7 2,grow" );
		this.btnAddAbility = new JButton( "+" );
		this.btnAddAbility.addActionListener( new AddAbilityActionListener() );
		pnlMTG.add( this.btnAddAbility, "cell 7 4,grow" );
		this.btnDelAbility = new JButton( "X" );
		this.btnDelAbility.addActionListener( new DelAbilityActionListener( tableAbility, tableAbilityColumnAdjuster ) );
		pnlMTG.add( this.btnDelAbility, "cell 7 5,alignx center,growy" );

		pnlMTG.add( new JLabel( "Primary Effect:" ), "cell 0 6" );
		this.txtPrimaryEffect = new JTextArea();
		this.txtPrimaryEffect.setLineWrap( true );
		pnlMTG.add( new JScrollPane( txtPrimaryEffect ), "cell 1 6 6 2,grow" );

		pnlMTG.add( new JLabel( "Other Effects:" ), "cell 0 7" );
		this.tableEffects = new JXTable( new JXObjectModel<>() );
		this.tableEffectsColumnAdjuster = new JXTableColumnAdjuster( this.tableEffects );
		pnlMTG.add( new JScrollPane( this.tableEffects ), "cell 0 8 7 2,grow" );
		this.btnAddEffect = new JButton( "+" );
		this.btnAddEffect.addActionListener( new AddEffectActionListener( this, tableEffects, tableEffectsColumnAdjuster ) );
		pnlMTG.add( this.btnAddEffect, "cell 7 8,alignx center,growy" );
		this.btnDelEffect = new JButton( "X" );
		this.btnDelEffect.addActionListener( new DelEffectActionListener( tableEffects, tableEffectsColumnAdjuster ) );
		pnlMTG.add( this.btnDelEffect, "cell 7 9,alignx center,growy" );

		pnlMTG.add( this.pnlManaCost, "cell 0 10 8 1,grow" );
		pnlMTG.add( this.pnlCreatureInfo, "cell 0 11 6 1,grow" );
		pnlMTG.add( this.pnlPlaneswalkerInfo, "cell 6 11 2 1,grow" );

		add( pnlMTG, "cell 0 1 2 1,grow" );

		JPanel pnlOptions = new JPanel();
		pnlOptions.setLayout( new MigLayout( "", "[grow][][grow]", "[]" ) );

		this.btnClear = new JButton( "Clear" );
		this.btnClear.addActionListener( new ClearActionListener() );
		pnlOptions.add( this.btnClear, "cell 0 0,growx" );

		this.btnSave = new JButton( "Save" );
		this.btnSave.addActionListener( new SaveActionListener() );
		pnlOptions.add( this.btnSave, "cell 2 0,growx" );

		add( pnlOptions, "cell 0 2 2 1,grow" );
	}

//===========================================================================================
// METHOD
//===========================================================================================
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

	/* save the MTG card with appropriate message */
	private void store(MTGCard m){
		try {
			if(StoreManager.getInstance().store( m )) {
				//reset all the form
				disableAllInPanel();
				clearEffectsAndAbilityTable();
				mtgCardType.clearSelection();
				MTGTypeListener.lastActionCommand = "";
				mtgList.refreshMTGTable();

				JOptionPane.showMessageDialog( pnlMTG, "Card saved correctly!", "Succes!", JOptionPane.INFORMATION_MESSAGE );
			}
			else {
				JOptionPane.showMessageDialog( pnlMTG, "Card is already saved!", "Bad Luck!", JOptionPane.INFORMATION_MESSAGE );
			}
		}
		catch(Exception e) {
			String s = String.format( "%s\nLog is reported.", e.getMessage() );
			log.write( Tag.ERRORS, e.getMessage() );
			displayError( pnlMTG, s );
		}
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/**
	 * inner class that describe the action on change mtgType;
	 */
	private class MTGTypeCardActionListener implements ActionListener
	{
		private String lastActionCommand = "";

		@Override
		public void actionPerformed(ActionEvent ev){
			String ac = ((JRadioButton) ev.getSource()).getActionCommand();
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

	/**
	 * inner class that describe the action on btnClear
	 */
	private class ClearActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			//reset all the form
			disableAllInPanel();
			clearEffectsAndAbilityTable();
			mtgCardType.clearSelection();
			MTGTypeListener.lastActionCommand = "";
//			mtgList.refreshMTGTable();
		}
	}

	/**
	 * inner class that describe the action on btnSave
	 */
	private class SaveActionListener implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e){
			String mtgCardType = MTGTypeListener.lastActionCommand;
			if(mtgCardType.equals( "" ))
				return; // if no type selection do nothing

			log.write( Tag.INFO, "New card start to save..." );
			log.write( Tag.DEBUG, "type to save = " + mtgCardType );

			String mtgName = pnlMTGBasicInfo.getNames();
			if(mtgName == null || mtgName.isEmpty()) {
				displayError( pnlMTG, "Name of MTG Card can not be void." );
				log.write( Tag.ERRORS, "Name of MTG card missing." );
				pnlMTGBasicInfo.requestFocus();
				return;
			}
			log.write( Tag.DEBUG, "name = " + mtgName );

			Rarity mtgRarity = pnlMTGBasicInfo.getRarity();
			log.write( Tag.DEBUG, "rarity = " + mtgRarity );

			CardColor mtgCardColor = pnlMTGBasicInfo.getCardColor();
			log.write( Tag.DEBUG, "card color = " + mtgCardColor.toString() );

			boolean isLegendary = pnlMTGBasicInfo.isLegendarySelected();
			log.write( Tag.DEBUG, "is legendary = " + isLegendary );

			String mtgSeries = pnlMTGBasicInfo.getSeries();
			if(mtgSeries == null || mtgSeries.isEmpty()) {
				displayError( pnlMTG, "Series of MTG can not be void." );
				log.write( Tag.ERRORS, "Series of MTG card missing." );
				pnlMTGBasicInfo.requestFocus();
				return;
			}
			log.write( Tag.DEBUG, "series = " + mtgSeries );

			String mtgSubType = pnlMTGBasicInfo.getSubType();
			if(mtgSubType == null)
				mtgSubType = "";
			log.write( Tag.DEBUG, "sub type = " + mtgSubType );

			String mtgPrimaryEffect = txtPrimaryEffect.getText();// if no text is inserted, "" is returned by getText()
			log.write( Tag.DEBUG, "primary effect = " + mtgPrimaryEffect );

			List<Effect> mtgEffects = ((JXObjectModel<Effect>) tableEffects.getModel()).getObjects();
			log.write( Tag.DEBUG, "effects = " + mtgEffects.toString() );

			switch( mtgCardType ) {
				case AC_CREATURE: {
					ManaCost mtgManaCost = pnlManaCost.getManaCost();
					if(mtgManaCost == null) {
						displayError( pnlMTG, "Mana cost of MTG Card can not be void." );
						log.write( Tag.ERRORS, "Mana cost of MTG card missing." );
						pnlManaCost.requestFocus();
						return;
					}
					log.write( Tag.DEBUG, "mana cost = " + mtgManaCost );

					Strength creatureStrength = pnlCreatureInfo.getStrength();
					if(creatureStrength == null) {
						displayError( pnlMTG, "Creature info missing." );
						log.write( Tag.ERRORS, "Creature strength missing." );
						pnlCreatureInfo.requestFocus();
						return;
					}
					log.write( Tag.DEBUG, "creature strength = " + creatureStrength );

					boolean isArtifact = pnlMTGBasicInfo.isArtifactSelected();
					log.write( Tag.DEBUG, "is artifact = " + isArtifact );

					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel()).getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Creature finalCreature = new Creature( mtgName, mtgCardColor, creatureStrength, mtgManaCost, mtgSubType, mtgRarity );
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
					store( finalCreature );

					break;
				}
				case AC_ARTIFACT: {
					ManaCost mtgManaCost = pnlManaCost.getManaCost();
					if(mtgManaCost == null) {
						displayError( pnlMTG, "Mana cost of MTG Card can not be void." );
						log.write( Tag.ERRORS, "Mana cost of MTG card missing." );
						pnlManaCost.requestFocus();
						return;
					}
					log.write( Tag.DEBUG, "mana cost = " + mtgManaCost );

					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel()).getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Artifact finalArtifact = new Artifact( mtgName, mtgManaCost, mtgCardColor, mtgRarity );
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
					store( finalArtifact );

					break;
				}
				case AC_PLANESWALKER: {
					ManaCost mtgManaCost = pnlManaCost.getManaCost();
					if(mtgManaCost == null) {
						displayError( pnlMTG, "Mana cost of MTG Card can not be void." );
						log.write( Tag.ERRORS, "Mana cost of MTG card missing." );
						pnlManaCost.requestFocus();
						return;
					}
					log.write( Tag.DEBUG, "mana cost = " + mtgManaCost );

					int mtgLife = pnlPlaneswalkerInfo.getPlaneswalkerLife();
					log.write( Tag.DEBUG, "planeswalker life = " + mtgLife );

					List<PlanesAbility> mtgAbility = ((JXObjectModel<PlanesAbility>) tableAbility.getModel()).getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Planeswalker finalPlaneswalker = new Planeswalker( mtgName, mtgManaCost, mtgLife, mtgCardColor, mtgRarity );
					finalPlaneswalker.setSeries( mtgSeries );
					finalPlaneswalker.setSubType( mtgSubType );
					for(PlanesAbility abi: mtgAbility) {
						finalPlaneswalker.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalPlaneswalker.toString() );
					store( finalPlaneswalker );

					break;
				}
				case AC_INSTANT: {
					ManaCost mtgManaCost = pnlManaCost.getManaCost();
					if(mtgManaCost == null) {
						displayError( pnlMTG, "Mana cost of MTG Card can not be void." );
						log.write( Tag.ERRORS, "Mana cost of MTG card missing." );
						pnlManaCost.requestFocus();
						return;
					}
					log.write( Tag.DEBUG, "mana cost = " + mtgManaCost );

					if(mtgPrimaryEffect == null) {
						displayError( pnlMTG, "Instant must have a primary effect." );
						log.write( Tag.ERRORS, "Instant primary effect of MTG card missing." );
						txtPrimaryEffect.requestFocus();
						return;
					}

					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel()).getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Instant finalInstant = new Instant( mtgName, mtgManaCost, mtgCardColor, mtgRarity );
					finalInstant.setPrimaryEffect( mtgPrimaryEffect );
					finalInstant.setSeries( mtgSeries );
					finalInstant.setSubType( mtgSubType );
					finalInstant.setLegendary( isLegendary );
					for(Ability abi: mtgAbility) {
						finalInstant.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalInstant.toString() );
					store( finalInstant );

					break;
				}
				case AC_SORCERY: {
					ManaCost mtgManaCost = pnlManaCost.getManaCost();
					if(mtgManaCost == null) {
						displayError( pnlMTG, "Mana cost of MTG Card can not be void." );
						log.write( Tag.ERRORS, "Mana cost of MTG card missing." );
						pnlManaCost.requestFocus();
						return;
					}
					log.write( Tag.DEBUG, "mana cost = " + mtgManaCost );

					if(mtgPrimaryEffect == null) {
						displayError( pnlMTG, "Sorcery must have a primary effect." );
						log.write( Tag.ERRORS, "Sorcery primary effect of MTG card missing." );
						txtPrimaryEffect.requestFocus();
						return;
					}

					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel()).getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Sorcery finalSorcery = new Sorcery( mtgName, mtgManaCost, mtgCardColor, mtgRarity );
					finalSorcery.setPrimaryEffect( mtgPrimaryEffect );
					finalSorcery.setSeries( mtgSeries );
					finalSorcery.setSubType( mtgSubType );
					finalSorcery.setLegendary( isLegendary );
					for(Ability abi: mtgAbility) {
						finalSorcery.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalSorcery.toString() );
					store( finalSorcery );

					break;
				}
				case AC_ENCHANTMENT: {
					ManaCost mtgManaCost = pnlManaCost.getManaCost();
					if(mtgManaCost == null) {
						displayError( pnlMTG, "Mana cost of MTG Card can not be void." );
						log.write( Tag.ERRORS, "Mana cost of MTG card missing." );
						pnlManaCost.requestFocus();
						return;
					}
					log.write( Tag.DEBUG, "mana cost = " + mtgManaCost );

					if(mtgPrimaryEffect == null) {
						displayError( pnlMTG, "Enchantment must have a primary effect." );
						log.write( Tag.ERRORS, "Enchantment primary effect of MTG card missing." );
						txtPrimaryEffect.requestFocus();
						return;
					}

					List<Ability> mtgAbility = ((JXObjectModel<Ability>) tableAbility.getModel()).getObjects();
					log.write( Tag.DEBUG, "ability = " + mtgAbility.toString() );

					Enchantment finalEnchantment = new Enchantment( mtgName, mtgManaCost, mtgCardColor, mtgRarity );
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
					store( finalEnchantment );

					break;
				}
				case AC_LAND: {
					if(mtgEffects.isEmpty()) {
						displayError( pnlMTG, "Land must have at least one effect." );
						log.write( Tag.ERRORS, "Land primary effect of MTG card missing." );
						tableEffects.requestFocus();
						return;
					}

					Land finalLand = new Land( mtgName, mtgRarity );
					finalLand.setPrimaryEffect( mtgPrimaryEffect );
					finalLand.setSeries( mtgSeries );
					finalLand.setSubType( mtgSubType );
					for(Effect eff: mtgEffects) {
						finalLand.addEffect( eff );
					}

					log.write( Tag.DEBUG, finalLand.toString() );
					store( finalLand );

					break;
				}
			}
		}
	}

	/**
	 * inner class that describe the action on btnAddActivity;
	 */
	private class AddAbilityActionListener implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e){
			if(MTGTypeListener.lastActionCommand.equals( AC_PLANESWALKER )) {
				PlanesAbility p = showPlanesAbilityDialog( pnlMTG );
				if(p != null) {
					JXObjectModel<PlanesAbility> model = (JXObjectModel<PlanesAbility>) tableAbility.getModel();
					if(model.getRowCount() == 0) {
						tableAbility.setModel( new JXObjectModel<PlanesAbility>( Arrays.asList( p ) ) );
					}
					else {
						model.addObject( p );
					}
					tableAbilityColumnAdjuster.adjustColumns();
				}
			}
			else {
				Ability a = showAbilityDialog( pnlMTG );
				if(a != null) {
					JXObjectModel<Ability> model = (JXObjectModel<Ability>) tableAbility.getModel();
					if(model.getRowCount() == 0) {
						tableAbility.setModel( new JXObjectModel<Ability>( Arrays.asList( a ) ) );
					}
					else {
						model.addObject( a );
					}
					tableAbilityColumnAdjuster.adjustColumns();
				}
			}
		}
	}
}
