package com.hackcaffebabe.mtg.trash;

import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.DisplayableObject;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.model.Artifact;
import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.Enchantment;
import com.hackcaffebabe.mtg.model.Instant;
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
 * This panel shows the {@link MTGCard} properties.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class _MTGProperties extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JTextField txtName;
	private JTextField txtManaCost;
	private JTextField txtCardColor;
	private JTextField txtRarity;
	private JTextField txtType;
	private JTextField txtSubType;
	private JTextField txtPlaneswalkerLife;
	private JTextField txtStrenght;
	private JTextField txtSeries;
	private JTextArea textPrimaryEffects;

	private JXTable tableAbility;
	private JXTableColumnAdjuster tableColumnAdjusterAbility;
	private JButton btnAddAbility;
	private JButton btnDelAbility;

	private JXTable tableEffects;
	private JXTableColumnAdjuster tableColumnAdjusterEffects;
	private JButton btnAddEffect;
	private JButton btnDelEffect;

	private JButton btnApplyUpdate;

	/**
	 * Create the panel.
	 */
	public _MTGProperties(){
		super();
		setBorder( new TitledBorder( null, "MTG Properties", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		setLayout( new MigLayout( "", "[44.00][38.00][159.00][][20.00,grow]", "[][][][][][][grow][][96.00,grow 200][grow][]" ) );
		this.initContent();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	private void initContent(){
		add( new JLabel( "Name:" ), "cell 0 0,alignx right" );
		this.txtName = new JTextField();
		add( this.txtName, "cell 1 0 4 1,growx" );

		add( new JLabel( "Mana Cost:" ), "cell 0 1,alignx right" );
		this.txtManaCost = new JTextField();
		add( this.txtManaCost, "cell 1 1 2 1,growx" );

		add( new JLabel( "Series:" ), "cell 3 1,alignx trailing" );
		this.txtSeries = new JTextField();
		add( this.txtSeries, "cell 4 1,growx" );

		add( new JLabel( "Card Color:" ), "cell 0 2,alignx right" );
		this.txtCardColor = new JTextField();
		add( this.txtCardColor, "cell 1 2 2 1,growx" );

		add( new JLabel( "PW Life:" ), "cell 3 2,alignx trailing" );
		this.txtPlaneswalkerLife = new JTextField();
		add( this.txtPlaneswalkerLife, "cell 4 2,growx" );

		add( new JLabel( "Rarity:" ), "cell 0 3,alignx right" );
		this.txtRarity = new JTextField();
		add( this.txtRarity, "cell 1 3 2 1,growx" );

		add( new JLabel( "Strength:" ), "cell 3 3,alignx trailing" );
		this.txtStrenght = new JTextField();
		add( this.txtStrenght, "cell 4 3,growx" );

		add( new JLabel( "Type:" ), "cell 0 4,alignx right" );
		this.txtType = new JTextField();
		add( this.txtType, "cell 1 4 3 1,growx" );

		add( new JLabel( "Sub Type:" ), "cell 0 5,alignx right" );
		this.txtSubType = new JTextField();
		add( this.txtSubType, "cell 1 5 3 1,growx" );

		JPanel pnlAbility = new JPanel();
		pnlAbility.setBorder( new TitledBorder( null, "Ability:", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		pnlAbility.setLayout( new MigLayout( "", "[grow][50!]", "[::50,grow][::50,grow]" ) );
		this.tableAbility = new JXTable(new JXObjectModel<DisplayableObject>());
		this.tableColumnAdjusterAbility = new JXTableColumnAdjuster( this.tableAbility );
		pnlAbility.add( new JScrollPane( this.tableAbility ), "cell 0 0 1 2,grow" );
		add( pnlAbility, "cell 0 6 5 1,grow" );

		this.btnAddAbility = new JButton( "+" );
		this.btnAddAbility.setEnabled( false );
		pnlAbility.add( this.btnAddAbility, "cell 1 0,grow" );

		this.btnDelAbility = new JButton( "X" );
		this.btnDelAbility.setEnabled( false );
		pnlAbility.add( this.btnDelAbility, "cell 1 1,grow" );

		add( new JLabel( "Primary Effect:" ), "cell 0 7 2 1" );
		this.textPrimaryEffects = new JTextArea();
		this.textPrimaryEffects.setLineWrap( true );
		JScrollPane scrollPane = new JScrollPane( this.textPrimaryEffects );
		add( scrollPane, "cell 2 7 3 2,grow" );

		JPanel pnlOtherEffects = new JPanel();
		pnlOtherEffects.setBorder( new TitledBorder( null, "Other Effects:", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		pnlOtherEffects.setLayout( new MigLayout( "", "[grow][50!]", "[::50,grow][::50,grow]" ) );
		this.tableEffects = new JXTable(new JXObjectModel<Effect>());
		this.tableColumnAdjusterEffects = new JXTableColumnAdjuster( this.tableEffects );
		pnlOtherEffects.add( new JScrollPane( this.tableEffects ), "cell 0 0 1 2,grow" );
		add( pnlOtherEffects, "cell 0 9 5 1,grow" );

		this.btnAddEffect = new JButton( "+" );
		this.btnAddEffect.setEnabled( false );
		pnlOtherEffects.add( this.btnAddEffect, "cell 1 0,grow" );

		this.btnDelEffect = new JButton( "X" );
		this.btnDelEffect.setEnabled( false );
		pnlOtherEffects.add( this.btnDelEffect, "cell 1 1,grow" );

		this.btnApplyUpdate = new JButton( "Apply Update" );
		this.btnApplyUpdate.setEnabled( false );
		add( this.btnApplyUpdate, "cell 0 10 5 1,grow" );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the {@link MTGCard} to view.
	 * @param c {@link MTGCard} to view
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public void setMTGCardToView(MTGCard c) throws IllegalArgumentException{
		if(c == null)
			throw new IllegalArgumentException( "Card to view is null." );

		String name = c.getName();
		Strength creatureStrength = null;
		Integer planeswalkerLife = -1;
		ManaCost manaCost = null;
		String type = null;
		if(c instanceof Creature) {
			manaCost = ((Creature) c).getManaCost();
			creatureStrength = ((Creature) c).getStrength();
			type = "Creature";
			
			Set<Ability> ab = c.getAbilities();
			if(!ab.isEmpty()){
				JXObjectModel<Ability> model = new JXObjectModel<>();
				for( Ability a : ab ) model.addObject( a );
				this.tableAbility.setModel( model );
				this.tableColumnAdjusterAbility.adjustColumns();
			}
		}
		else if(c instanceof Sorcery) {
			manaCost = ((Sorcery) c).getManaCost();
			type = "Sorcery";
			
			Set<Ability> ab = c.getAbilities();
			if(!ab.isEmpty()){
				JXObjectModel<Ability> model = new JXObjectModel<>();
				for( Ability a : ab ) model.addObject( a );
				this.tableAbility.setModel( model );
				this.tableColumnAdjusterAbility.adjustColumns();
			}
		}
		else if(c instanceof Instant) {
			manaCost = ((Instant) c).getManaCost();
			type = "Instant";
			
			Set<Ability> ab = c.getAbilities();
			if(!ab.isEmpty()){
				JXObjectModel<Ability> model = new JXObjectModel<>();
				for( Ability a : ab ) model.addObject( a );
				this.tableAbility.setModel( model );
				this.tableColumnAdjusterAbility.adjustColumns();
			}
		}
		else if(c instanceof Enchantment) {
			manaCost = ((Enchantment) c).getManaCost();
			type = "Enchantment";
			
			Set<Ability> ab = c.getAbilities();
			if(!ab.isEmpty()){
				JXObjectModel<Ability> model = new JXObjectModel<>();
				for( Ability a : ab ) model.addObject( a );
				this.tableAbility.setModel( model );
				this.tableColumnAdjusterAbility.adjustColumns();
			}
		}
		else if(c instanceof Planeswalker) {
			manaCost = ((Planeswalker) c).getManaCost();
			planeswalkerLife = ((Planeswalker) c).getLife();
			type = "Planeswalker";
			
			Set<PlanesAbility> ab = ((Planeswalker) c).getPlanesAbilities();
			if(!ab.isEmpty()){
				JXObjectModel<PlanesAbility> model = new JXObjectModel<>();
				for( PlanesAbility a : ab ) model.addObject( a );
				this.tableAbility.setModel( model );
				this.tableColumnAdjusterAbility.adjustColumns();
			}
		}
		else if(c instanceof Artifact) {
			manaCost = ((Artifact) c).getManaCost();
			type = "Artifact";
			
			Set<Ability> ab = c.getAbilities();
			if(!ab.isEmpty()){
				JXObjectModel<Ability> model = new JXObjectModel<>();
				for( Ability a : ab ) model.addObject( a );
				this.tableAbility.setModel( model );
				this.tableColumnAdjusterAbility.adjustColumns();
			}
		}
		else {
			type = "Land";
			
			Set<Ability> ab = c.getAbilities();
			if(!ab.isEmpty()){
				JXObjectModel<Ability> model = new JXObjectModel<>();
				for( Ability a : ab ) model.addObject( a );
				this.tableAbility.setModel( model );
				this.tableColumnAdjusterAbility.adjustColumns();
			}
		}
		CardColor color = c.getCardColor();
		String series = c.getSeries();
		Rarity rarity = c.getRarity();
		String subType = c.getSubType();
		String primaryEffects = c.getPrimaryEffect();

		this.txtName.setText( name );
		this.txtManaCost.setText( manaCost == null ? "------" : manaCost.toString() );
		this.txtSeries.setText( series );
		this.txtCardColor.setText( String.format( "%s %s", color.toString(), color.getType() ) );
		this.txtRarity.setText( rarity.toString() );
		this.txtType.setText( type );
		this.txtSubType.setText( subType );
		this.textPrimaryEffects.setText( primaryEffects );
		this.txtStrenght.setText( creatureStrength == null ? "------" : creatureStrength.toString() );
		this.txtPlaneswalkerLife.setText( planeswalkerLife == -1 ? "------" : planeswalkerLife.toString() );

		if( !c.getEffects().isEmpty() ){
			JXObjectModel<Effect> model = new JXObjectModel<>();
			for( Effect e : c.getEffects() ) model.addObject( e );
			this.tableEffects.setModel( model );
			this.tableColumnAdjusterEffects.adjustColumns();
		}
		
		this.btnAddAbility.setEnabled( true );
		this.btnAddEffect.setEnabled( true );
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
}
