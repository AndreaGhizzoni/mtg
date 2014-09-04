package com.hackcaffebabe.mtg.gui.panel.insertupdatecard;

import static com.hackcaffebabe.mtg.gui.GUIUtils.PNL_MTGPROPERTIES;
import static com.hackcaffebabe.mtg.gui.GUIUtils.displayError;
import static com.hackcaffebabe.mtg.gui.GUIUtils.displaySuccessMessage;
import static com.hackcaffebabe.mtg.gui.GUIUtils.displayWarningMessage;
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
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.controller.StringNormalizer;
import com.hackcaffebabe.mtg.controller.json.JSONTags;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.ActionCommand;
import com.hackcaffebabe.mtg.gui.FramesDimensions;
import com.hackcaffebabe.mtg.gui.frame.InsertUpdateCard;
import com.hackcaffebabe.mtg.gui.listener.AddEffectActionListener;
import com.hackcaffebabe.mtg.gui.listener.DelAbilityActionListener;
import com.hackcaffebabe.mtg.gui.listener.DelEffectActionListener;
import com.hackcaffebabe.mtg.gui.panel.insertupdatecard.listener.EditEffectsMouseAdapter;
import com.hackcaffebabe.mtg.gui.panel.listener.ShortCutterV2;
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
import com.hackcaffebabe.mtg.model.card.PlanesAbility;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.card.Strength;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


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
		setSize( FramesDimensions.DIMENSION_INSERT_CARD );
		setLayout( new MigLayout( "", "[grow][grow]", "[60!][grow][60!]" ) );
		this.cardToUpdate = cardToUpdate;
		this.initContent();
		this.initShortcut();
		this.chois();
	}

	/* choose insert mode or update mode */
	private void chois(){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				if(cardToUpdate == null) { // if card to update is null, user want to insert new card
					disableAllInPanel();
				} else { // otherwise user want to update passing card
					populateContent();
					disableUnnecessaryComponentsForUpdates();
				}
				btnSaveOrUpdate.setText( cardToUpdate == null ? "Save" : "Update" );
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
		pnlTypeCard.setLayout( new MigLayout( "", "[grow][grow][grow][grow][grow][grow][grow]", "[]" ) );
		add( pnlTypeCard, "cell 0 0 2 1,grow" );

		this.MTGTypeListener = new TypeCardActionListener();
		this.rdbCreature.setActionCommand( ActionCommand.CREATURE );
		this.rdbCreature.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbCreature, "cell 0 0" );

		this.rdbArtifact.setActionCommand( ActionCommand.ARTIFACT );
		this.rdbArtifact.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbArtifact, "cell 1 0" );

		this.rdbPlanedwalker.setActionCommand( ActionCommand.PLANESWALKER );
		this.rdbPlanedwalker.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbPlanedwalker, "cell 2 0" );

		this.rdbLand.setActionCommand( ActionCommand.LAND );
		this.rdbLand.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbLand, "cell 3 0" );

		this.rdbEnchantment.setActionCommand( ActionCommand.ENCHANTMENT );
		this.rdbEnchantment.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbEnchantment, "cell 4 0" );

		this.rdbSorcery.setActionCommand( ActionCommand.SORCERY );
		this.rdbSorcery.addActionListener( MTGTypeListener );
		pnlTypeCard.add( this.rdbSorcery, "cell 5 0" );

		this.rdbInstant.setActionCommand( ActionCommand.INSTANT );
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
		pnlMTG.setLayout( new MigLayout( "", "[grow][grow][grow][grow][grow][grow][100px:n,grow][29.00px:n]",
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
		this.btnDelAbility.addActionListener( new DelAbilityActionListener( tableAbility, tableAbilityColumnAdjuster ) );
		pnlMTG.add( this.btnDelAbility, "cell 7 5,alignx center,growy" );

		//================== Primary Effect
		JLabel lblPrimaryEffect = new JLabel( "Primary Effect:" );
		lblPrimaryEffect.setDisplayedMnemonic( KeyEvent.VK_P );
		pnlMTG.add( lblPrimaryEffect, "cell 0 6" );
		this.txtPrimaryEffect = new JTextArea();
		this.txtPrimaryEffect.setLineWrap( true );
		this.txtPrimaryEffect.setWrapStyleWord( true );

		pnlMTG.add( new JScrollPane( txtPrimaryEffect, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), "cell 1 6 6 2,grow" );

		//================== Other Effect
		pnlMTG.add( new JLabel( "Other Effects:" ), "cell 0 7" );
		this.tableEffects = new JXTable( new JXObjectModel<>() );
		this.tableEffects.addMouseListener( new EditEffectsMouseAdapter() );
		this.tableEffectsColumnAdjuster = new JXTableColumnAdjuster( this.tableEffects );
		pnlMTG.add( new JScrollPane( this.tableEffects, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), "cell 0 8 7 2,grow" );
		this.btnAddEffect = new JButton( "+" );
		this.btnAddEffect.addActionListener( new AddEffectActionListener( this, tableEffects,
				tableEffectsColumnAdjuster ) );
		pnlMTG.add( this.btnAddEffect, "cell 7 8,alignx center,growy" );
		this.btnDelEffect = new JButton( "X" );
		this.btnDelEffect.addActionListener( new DelEffectActionListener( tableEffects, tableEffectsColumnAdjuster ) );
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

	/* this method initialize the shortcut */
	private void initShortcut(){
		ShortCutterV2.getInstance().add( this.txtPrimaryEffect );
		this.txtPrimaryEffect.getInputMap().put( ShortCutterV2.KEYSTROKE, ShortCutterV2.KEY );
		this.txtPrimaryEffect.getActionMap().put( ShortCutterV2.KEY, ShortCutterV2.getInstance() );
		this.txtPrimaryEffect.setFocusAccelerator( 'p' );

		this.btnAddAbility.setMnemonic( KeyEvent.VK_A );
		this.btnAddEffect.setMnemonic( KeyEvent.VK_E );
		this.btnSaveOrUpdate.setMnemonic( cardToUpdate == null ? KeyEvent.VK_S : KeyEvent.VK_U );
		this.btnClear.setMnemonic( KeyEvent.VK_L );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * Select the appropriate type card for the view component.
	 * @param ac {@link String} from one of GUIUtils.AC_*, otherwise nothing.
	 */
	public void selectCardType(String ac){
		if(ac == null || ac.isEmpty())
			return;
		//this stuff is for select appropriate mtgType button
		switch( ac ) {
			case ActionCommand.CREATURE: {
				rdbCreature.setSelected( true );
				rdbCreature.requestFocus();
				break;
			}
			case ActionCommand.ARTIFACT: {
				rdbArtifact.setSelected( true );
				rdbArtifact.requestFocus();
				break;
			}
			case ActionCommand.INSTANT: {
				rdbInstant.setSelected( true );
				rdbInstant.requestFocus();
				break;
			}
			case ActionCommand.SORCERY: {
				rdbSorcery.setSelected( true );
				rdbSorcery.requestFocus();
				break;
			}
			case ActionCommand.ENCHANTMENT: {
				rdbEnchantment.setSelected( true );
				rdbEnchantment.requestFocus();
				break;
			}
			case ActionCommand.LAND: {
				rdbLand.setSelected( true );
				rdbLand.requestFocus();
				break;
			}
			case ActionCommand.PLANESWALKER: {
				rdbPlanedwalker.setSelected( true );
				rdbPlanedwalker.requestFocus();
				break;
			}
			default:
				return;
		}
		//this is to call action event
		this.MTGTypeListener.actionPerformed( new ActionEvent( this, 1, ac ) );
	}

	/**
	 * Reset all the panel and components into the panel
	 */
	public void disableAllInPanel(){
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
		ShortCutterV2.getInstance().clearMenu();
	}

	/**
	 * Reset the table effects and table abilities
	 */
	public void clearEffectsAndAbilityTable(){
		this.tableAbility.setModel( new JXObjectModel<>() );
		this.tableEffects.setModel( new JXObjectModel<>() );
	}

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

	/* reset ALL the form */
	private void resetTheForm(){
		disableAllInPanel();
		clearEffectsAndAbilityTable();
		mtgCardType.clearSelection();
		MTGTypeListener.lastActionCommand = "";
	}

	/* this method populate all the content with data in cardToUpdate */
	private void populateContent(){
		selectCardType( cardToUpdate.getClass().getSimpleName() );

		this.pnlMTGBasicInfo.setData( cardToUpdate );
		this.txtPrimaryEffect.setText( cardToUpdate.getPrimaryEffect() );

		if(!(cardToUpdate instanceof Planeswalker)) {
			if(cardToUpdate instanceof Creature)
				this.pnlCreatureInfo.setData( cardToUpdate );
			populateTables( Ability.class, cardToUpdate.getAbilities(), cardToUpdate.getEffects() );
		} else {
			populateTables( PlanesAbility.class, ((Planeswalker) cardToUpdate).getPlanesAbilities(),
					cardToUpdate.getEffects() );
			this.pnlPlaneswalkerInfo.setData( cardToUpdate );
		}

		if(!(cardToUpdate instanceof Land)) {
			this.pnlManaCost.setData( this.cardToUpdate );
		}
	}

	/* this method populate the table ability and effects */
	private <T extends DisplayableObject> void populateTables(Class<T> abilityClaxx, Set<T> setOfAbilityClaxx,
			Set<Effect> setOfEffects){
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
					case ActionCommand.CREATURE: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlManaCost.enableAllComponents();
						pnlCreatureInfo.enableAllComponents();
						break;
					}
					case ActionCommand.ARTIFACT: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						pnlManaCost.enableAllComponents();
						break;
					}
					case ActionCommand.PLANESWALKER: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						pnlManaCost.enableAllComponents();
						pnlPlaneswalkerInfo.enableAllComponents();
						btnAddEffect.setEnabled( false );
						btnDelEffect.setEnabled( false );
						txtPrimaryEffect.setEditable( false );
						break;
					}
					case ActionCommand.LAND: {
						pnlMTGBasicInfo.setAllColorsEnable( false );
						btnAddAbility.setEnabled( false );
						btnDelAbility.setEnabled( false );
						break;
					}
					case ActionCommand.INSTANT: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						pnlManaCost.enableAllComponents();
						btnAddEffect.setEnabled( true );
						btnDelEffect.setEnabled( true );
						break;
					}
					case ActionCommand.SORCERY: {
						pnlMTGBasicInfo.setAllColorsEnable( true );
						pnlManaCost.enableAllComponents();
						pnlMTGBasicInfo.setIsArtifactEnable( false );
						btnAddEffect.setEnabled( true );
						btnDelEffect.setEnabled( true );
						break;
					}
					case ActionCommand.ENCHANTMENT: {
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
			resetTheForm();
		}
	}

	/* inner class that describe the action on btnSave */
	private class SaveOrUpdateActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			if(!checkUserCard())
				return;
			HashMap<String, Object> map = getInsertData();
			MTGCard c = getCardFromData( map );
			storeOrUpdate( c );
			map.clear();
			map = null;
		}

		/* This method check the data from the user. */
		private boolean checkUserCard(){
			String mtgCardType = MTGTypeListener.lastActionCommand;
			if(mtgCardType.equals( "" ))
				return false;

			// =============== check the name
			String mtgName = pnlMTGBasicInfo.getNames();
			if(mtgName == null || mtgName.isEmpty()) {
				displayError( null, new Exception( "Name of MTG Card can not be void." ) );
				pnlMTGBasicInfo.requestFocus();
				return false;
			}
			if(StringNormalizer.normalizeForStorage( mtgName ).isEmpty()) {// if user insert "...." or "/////" as name.
				displayError( null, new Exception( "Name not valid" ) );
				pnlMTGBasicInfo.requestFocus();
				return false;
			}

			// =============== check the series
			String mtgSeries = pnlMTGBasicInfo.getSeries();
			if(mtgSeries == null || mtgSeries.isEmpty()) {
				displayError( null, new Exception( "Series of MTG can not be void." ) );
				pnlMTGBasicInfo.requestFocus();
				return false;
			}
			if(StringNormalizer.normalizeForStorage( mtgSeries ).isEmpty()) {// if user insert "...." or "/////" as series.
				displayError( null, new Exception( "Series not valid" ) );
				pnlMTGBasicInfo.requestFocus();
				return false;
			}

			// =============== get mana cost
			if(!mtgCardType.equals( ActionCommand.LAND )) {
				ManaCost mtgManaCost = pnlManaCost.getManaCost();
				if(mtgManaCost == null) {
					displayError( null, new Exception( "Mana cost of MTG Card can not be void." ) );
					pnlManaCost.requestFocus();
					return false;
				}
			}

			// =============== get creature strength
			if(mtgCardType.equals( ActionCommand.CREATURE )) {
				Strength creatureStrength = pnlCreatureInfo.getStrength();
				if(creatureStrength == null) {
					displayError( null, new Exception( "Creature info missing." ) );
					pnlCreatureInfo.requestFocus();
					return false;
				}
			}

			// =============== get primary effect
			if(mtgCardType.equals( ActionCommand.INSTANT ) || mtgCardType.equals( ActionCommand.SORCERY )
					|| mtgCardType.equals( ActionCommand.ENCHANTMENT )) {
				String mtgPrimaryEffect = StringNormalizer.normalizeForStorage( txtPrimaryEffect.getText() );
				if(mtgPrimaryEffect == null) {
					displayError( null, new Exception( mtgCardType + " have a primary effect." ) );
					txtPrimaryEffect.requestFocus();
					return false;
				}
			}

			// =============== get land basic effect
			if(mtgCardType.equals( ActionCommand.LAND )) {
				@SuppressWarnings("unchecked")
				List<Effect> mtgEffects = ((JXObjectModel<Effect>) tableEffects.getModel()).getObjects();
				if(mtgEffects.isEmpty()) {
					displayError( null, new Exception( "Land must have at least one effect." ) );
					tableEffects.requestFocus();
					return false;
				}
			}
			return true;
		}

		/* save the MTG card with appropriate message */
		private void storeOrUpdate(MTGCard m){
			try {
				if(cardToUpdate == null) {
					if(StoreManager.getInstance().store( m )) {
						resetTheForm();
						refreshMTGTable();

						displaySuccessMessage( null, "Card saved correctly!" );
					} else {
						displayWarningMessage( null, "Card is already saved!" );
					}
				} else {
					// cardToUpdate is the oldest card, m is the newest.
					if(StoreManager.getInstance().applyDifference( cardToUpdate, m )) {
						refreshMTGTable();
						PNL_MTGPROPERTIES.clearAll();

						displaySuccessMessage( null, "Card updated correctly!" );
						parent.close();
					} else {
						displayWarningMessage( null, "No changes found.\nNothing to update." );
					}
				}
			} catch(Exception e) {
				displayError( null, e );
			}
		}

		@SuppressWarnings("unchecked")
		private HashMap<String, Object> getInsertData(){
			HashMap<String, Object> map = new HashMap<>();

			//at this point all the data are correct and ready to store or update.
			map.put( JSONTags.TYPE, MTGTypeListener.lastActionCommand );

			log.write( Tag.INFO, "New card start to save..." );
			log.write( Tag.DEBUG, String.format( "type to save = %s", map.get( JSONTags.TYPE ) ) );

			map.put( JSONTags.NAME, StringNormalizer.normalizeForStorage( pnlMTGBasicInfo.getNames() ).trim() );
			log.write( Tag.DEBUG, String.format( "name = %s", map.get( JSONTags.NAME ) ) );

			map.put( JSONTags.RARITY, pnlMTGBasicInfo.getRarity() );
			log.write( Tag.DEBUG, String.format( "rarity = %s", (Rarity) map.get( JSONTags.RARITY ) ) );

			map.put( JSONTags.COLOR, pnlMTGBasicInfo.getCardColor() );
			log.write( Tag.DEBUG, String.format( "card color = %s", (CardColor) map.get( JSONTags.COLOR ) ) );

			map.put( JSONTags.LEGENDARY, pnlMTGBasicInfo.isLegendarySelected() );
			log.write( Tag.DEBUG, String.format( "is legendary = %s", (boolean) map.get( JSONTags.LEGENDARY ) ) );

			map.put( JSONTags.SERIES, StringNormalizer.normalizeForStorage( pnlMTGBasicInfo.getSeries() ).trim() );
			log.write( Tag.DEBUG, String.format( "series = %s", map.get( JSONTags.SERIES ) ) );

			String tmp = StringNormalizer.removeAccentCharacters( pnlMTGBasicInfo.getSubType() );
			if(tmp == null)
				map.put( JSONTags.SUB_TYPE, "" );
			else map.put( JSONTags.SUB_TYPE, tmp.trim() );
			log.write( Tag.DEBUG, String.format( "sub type = %s", map.get( JSONTags.SUB_TYPE ) ) );

			// if no text is inserted, "" is returned by getText()
			map.put( JSONTags.PRIMARY_EFFECT, StringNormalizer.removeAccentCharacters( txtPrimaryEffect.getText() ) );
			log.write( Tag.DEBUG, String.format( "primary effect = %s", map.get( JSONTags.PRIMARY_EFFECT ) ) );

			map.put( JSONTags.EFFECTS, ((JXObjectModel<Effect>) tableEffects.getModel()).getObjects() );
			log.write( Tag.DEBUG,
					String.format( "effects = %s", ((List<Effect>) map.get( JSONTags.EFFECTS )).toString() ) );

			map.put( JSONTags.MANA_COST, null );
			if(!((String) map.get( JSONTags.TYPE )).equals( ActionCommand.LAND )) {
				map.put( JSONTags.MANA_COST, pnlManaCost.getManaCost() );
				log.write( Tag.DEBUG,
						String.format( "mana cost = %s", ((ManaCost) map.get( JSONTags.MANA_COST )).toString() ) );
			}

			if((String) map.get( JSONTags.TYPE ) == ActionCommand.CREATURE) {
				map.put( JSONTags.STRENGTH, pnlCreatureInfo.getStrength() );
				log.write( Tag.DEBUG, String.format( "creature strength = %s", (Strength) map.get( JSONTags.STRENGTH ) ) );

				map.put( JSONTags.ARTIFACT, pnlMTGBasicInfo.isArtifactSelected() );
				log.write( Tag.DEBUG, String.format( "is artifact = %s", map.get( JSONTags.ARTIFACT ) ) );
			}

			if((String) map.get( JSONTags.TYPE ) != ActionCommand.ARTIFACT
					|| (String) map.get( JSONTags.TYPE ) != ActionCommand.PLANESWALKER) {
				map.put( JSONTags.ARTIFACT, pnlMTGBasicInfo.isArtifactSelected() );
				log.write( Tag.DEBUG, String.format( "is artifact = %s", map.get( JSONTags.ARTIFACT ) ) );
			}

			if((String) map.get( JSONTags.TYPE ) == ActionCommand.PLANESWALKER) {
				map.put( JSONTags.LIFE, pnlPlaneswalkerInfo.getPlaneswalkerLife() );
				log.write( Tag.DEBUG, String.format( "planeswalker life = %d", (int) map.get( JSONTags.LIFE ) ) );

				map.put( JSONTags.PLANES_ABILITY, ((JXObjectModel<PlanesAbility>) tableAbility.getModel()).getObjects() );
				log.write( Tag.DEBUG,
						String.format( "ability = %s", (List<PlanesAbility>) map.get( JSONTags.PLANES_ABILITY ) ) );
			}

			if((String) map.get( JSONTags.TYPE ) != ActionCommand.PLANESWALKER
					|| (String) map.get( JSONTags.TYPE ) != ActionCommand.LAND) {
				map.put( JSONTags.ABILITIES, ((JXObjectModel<Ability>) tableAbility.getModel()).getObjects() );
				log.write( Tag.DEBUG,
						String.format( "ability = %s", ((List<Ability>) map.get( JSONTags.ABILITIES )).toString() ) );
			}
			return map;
		}

		@SuppressWarnings("unchecked")
		public MTGCard getCardFromData(HashMap<String, Object> map){
			switch( (String) map.get( JSONTags.TYPE ) ) {
				case ActionCommand.CREATURE: {
					Creature finalCreature = new Creature( (String) map.get( JSONTags.NAME ),
							(CardColor) map.get( JSONTags.COLOR ), (Strength) map.get( JSONTags.STRENGTH ),
							(ManaCost) map.get( JSONTags.MANA_COST ), (String) map.get( JSONTags.SUB_TYPE ),
							(Rarity) map.get( JSONTags.RARITY ) );
					finalCreature.setArtifact( (boolean) map.get( JSONTags.ARTIFACT ) );
					finalCreature.setSeries( (String) map.get( JSONTags.SERIES ) );
					finalCreature.setSubType( (String) map.get( JSONTags.SUB_TYPE ) );
					finalCreature.setLegendary( (boolean) map.get( JSONTags.LEGENDARY ) );
					finalCreature.setPrimaryEffect( (String) map.get( JSONTags.PRIMARY_EFFECT ) );
					for(Effect eff: (List<Effect>) map.get( JSONTags.EFFECTS )) {
						finalCreature.addEffect( eff );
					}
					for(Ability abi: (List<Ability>) map.get( JSONTags.ABILITIES )) {
						finalCreature.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalCreature.toString() );
					return finalCreature;
				}
				case ActionCommand.ARTIFACT: {
					Artifact finalArtifact = new Artifact( (String) map.get( JSONTags.NAME ),
							(ManaCost) map.get( JSONTags.MANA_COST ), (CardColor) map.get( JSONTags.COLOR ),
							(Rarity) map.get( JSONTags.RARITY ) );
					finalArtifact.setLegendary( (boolean) map.get( JSONTags.LEGENDARY ) );
					finalArtifact.setSeries( (String) map.get( JSONTags.SERIES ) );
					finalArtifact.setSubType( (String) map.get( JSONTags.SUB_TYPE ) );
					finalArtifact.setPrimaryEffect( (String) map.get( JSONTags.PRIMARY_EFFECT ) );
					for(Effect eff: (List<Effect>) map.get( JSONTags.EFFECTS )) {
						finalArtifact.addEffect( eff );
					}
					for(Ability abi: (List<Ability>) map.get( JSONTags.ABILITIES )) {
						finalArtifact.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalArtifact.toString() );
					return finalArtifact;
				}
				case ActionCommand.PLANESWALKER: {
					Planeswalker finalPlaneswalker = new Planeswalker( (String) map.get( JSONTags.NAME ),
							(ManaCost) map.get( JSONTags.MANA_COST ), (int) map.get( JSONTags.LIFE ),
							(CardColor) map.get( JSONTags.COLOR ), (Rarity) map.get( JSONTags.RARITY ) );
					finalPlaneswalker.setSeries( (String) map.get( JSONTags.SERIES ) );
					finalPlaneswalker.setSubType( (String) map.get( JSONTags.SUB_TYPE ) );
					for(PlanesAbility abi: (List<PlanesAbility>) map.get( JSONTags.PLANES_ABILITY )) {
						finalPlaneswalker.addAbility( abi );
					}

					log.write( Tag.DEBUG, finalPlaneswalker.toString() );
					return finalPlaneswalker;
				}
				case ActionCommand.INSTANT: {
					Instant finalInstant = new Instant( (String) map.get( JSONTags.NAME ),
							(ManaCost) map.get( JSONTags.MANA_COST ), (CardColor) map.get( JSONTags.COLOR ),
							(Rarity) map.get( JSONTags.RARITY ) );
					finalInstant.setPrimaryEffect( (String) map.get( JSONTags.PRIMARY_EFFECT ) );
					finalInstant.setSeries( (String) map.get( JSONTags.SERIES ) );
					finalInstant.setSubType( (String) map.get( JSONTags.SUB_TYPE ) );
					finalInstant.setLegendary( (boolean) map.get( JSONTags.LEGENDARY ) );
					for(Ability abi: (List<Ability>) map.get( JSONTags.ABILITIES )) {
						finalInstant.addAbility( abi );
					}
					for(Effect ef: (List<Effect>) map.get( JSONTags.EFFECTS )) {
						finalInstant.addEffect( ef );
					}

					log.write( Tag.DEBUG, finalInstant.toString() );
					return finalInstant;
				}
				case ActionCommand.SORCERY: {
					Sorcery finalSorcery = new Sorcery( (String) map.get( JSONTags.NAME ),
							(ManaCost) map.get( JSONTags.MANA_COST ), (CardColor) map.get( JSONTags.COLOR ),
							(Rarity) map.get( JSONTags.RARITY ) );
					finalSorcery.setPrimaryEffect( (String) map.get( JSONTags.PRIMARY_EFFECT ) );
					finalSorcery.setSeries( (String) map.get( JSONTags.SERIES ) );
					finalSorcery.setSubType( (String) map.get( JSONTags.SUB_TYPE ) );
					finalSorcery.setLegendary( (boolean) map.get( JSONTags.LEGENDARY ) );
					for(Ability abi: (List<Ability>) map.get( JSONTags.ABILITIES )) {
						finalSorcery.addAbility( abi );
					}
					for(Effect ef: (List<Effect>) map.get( JSONTags.EFFECTS )) {
						finalSorcery.addEffect( ef );
					}

					log.write( Tag.DEBUG, finalSorcery.toString() );
					return finalSorcery;
				}
				case ActionCommand.ENCHANTMENT: {
					Enchantment finalEnchantment = new Enchantment( (String) map.get( JSONTags.NAME ),
							(ManaCost) map.get( JSONTags.MANA_COST ), (CardColor) map.get( JSONTags.COLOR ),
							(Rarity) map.get( JSONTags.RARITY ) );
					finalEnchantment.setPrimaryEffect( (String) map.get( JSONTags.PRIMARY_EFFECT ) );
					finalEnchantment.setSeries( (String) map.get( JSONTags.SERIES ) );
					finalEnchantment.setSubType( (String) map.get( JSONTags.SUB_TYPE ) );
					finalEnchantment.setLegendary( (boolean) map.get( JSONTags.LEGENDARY ) );
					for(Ability abi: (List<Ability>) map.get( JSONTags.ABILITIES )) {
						finalEnchantment.addAbility( abi );
					}
					for(Effect eff: (List<Effect>) map.get( JSONTags.EFFECTS )) {
						finalEnchantment.addEffect( eff );
					}

					log.write( Tag.DEBUG, finalEnchantment.toString() );
					return finalEnchantment;
				}
				case ActionCommand.LAND: {
					Land finalLand = new Land( (String) map.get( JSONTags.NAME ), (Rarity) map.get( JSONTags.RARITY ) );
					finalLand.setArtifact( (boolean) map.get( JSONTags.ARTIFACT ) );
					finalLand.setPrimaryEffect( (String) map.get( JSONTags.PRIMARY_EFFECT ) );
					finalLand.setSeries( (String) map.get( JSONTags.SERIES ) );
					finalLand.setSubType( (String) map.get( JSONTags.SUB_TYPE ) );
					for(Effect eff: (List<Effect>) map.get( JSONTags.EFFECTS )) {
						finalLand.addEffect( eff );
					}

					log.write( Tag.DEBUG, finalLand.toString() );
					return finalLand;
				}
				default:
					return null;
			}
		}
	}

	/* inner class that describe the action on btnAddActivity; */
	private class AddAbilityActionListener implements ActionListener
	{
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent e){
			if(MTGTypeListener.lastActionCommand.equals( ActionCommand.PLANESWALKER )) {
				PlanesAbility p = showPlanesAbilityDialog( InsertUpdateCardContent.this );
				if(p != null) {
					JXObjectModel<PlanesAbility> model = (JXObjectModel<PlanesAbility>) tableAbility.getModel();
					if(model.getRowCount() == 0) {
						tableAbility.setModel( new JXObjectModel<PlanesAbility>( Arrays.asList( p ) ) );
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
