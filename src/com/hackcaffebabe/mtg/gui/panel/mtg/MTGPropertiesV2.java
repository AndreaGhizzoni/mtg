package com.hackcaffebabe.mtg.gui.panel.mtg;

import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.DisplayableObject;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.Component;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import net.miginfocom.swing.MigLayout;
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
 * This panel shows the {@link MTGCard} properties.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class MTGPropertiesV2 extends JPanel
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
	public MTGPropertiesV2(){
		super();
		setBorder( new TitledBorder( null, "MTG Properties", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		setLayout( new MigLayout( "", "[44.00][38.00][180.00][100.00][grow]", "[][][][][][][][150px,grow][][:96.00:100,grow][150px][]" ) );
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
		add( this.txtManaCost, "cell 1 1 3 1,growx" );

		add( new JLabel( "Card Color:" ), "cell 0 2,alignx right" );
		this.txtCardColor = new JTextField();
		add( this.txtCardColor, "cell 1 2 3 1,growx" );

		add( new JLabel( "Rarity:" ), "cell 0 3,alignx right" );
		this.txtRarity = new JTextField();
		add( this.txtRarity, "cell 1 3 2 1,growx" );

		add( new JLabel( "PW Life:" ), "cell 3 3,alignx trailing" );
		this.txtPlaneswalkerLife = new JTextField();
		add( this.txtPlaneswalkerLife, "cell 4 3,growx" );

		add( new JLabel( "Series:" ), "cell 0 4,alignx trailing" );
		this.txtSeries = new JTextField();
		add( this.txtSeries, "cell 1 4 2 1,growx" );

		add( new JLabel( "Strength:" ), "cell 3 4,alignx trailing" );
		this.txtStrenght = new JTextField();
		add( this.txtStrenght, "cell 4 4,growx" );

		add( new JLabel( "Type:" ), "cell 0 5,alignx right" );
		this.txtType = new JTextField();
		add( this.txtType, "cell 1 5 3 1,growx" );

		add( new JLabel( "Sub Type:" ), "cell 0 6,alignx right" );
		this.txtSubType = new JTextField();
		add( this.txtSubType, "cell 1 6 3 1,growx" );

		JPanel pnlAbility = new JPanel();
		pnlAbility.setBorder( new TitledBorder( null, "Ability:", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		pnlAbility.setLayout( new MigLayout( "", "[grow][50!]", "[grow][grow]" ) );
		this.tableAbility = new JXTable( new JXObjectModel<DisplayableObject>() );
		this.tableColumnAdjusterAbility = new JXTableColumnAdjuster( this.tableAbility );
		pnlAbility.add( new JScrollPane( this.tableAbility ), "cell 0 0 1 2,grow" );
		add( pnlAbility, "cell 0 7 5 1,grow" );

		this.btnAddAbility = new JButton( "+" );
		this.btnAddAbility.setEnabled( false );
		pnlAbility.add( this.btnAddAbility, "cell 1 0,growx,aligny center" );

		this.btnDelAbility = new JButton( "X" );
		this.btnDelAbility.setEnabled( false );
		pnlAbility.add( this.btnDelAbility, "cell 1 1,growx,aligny center" );

		add( new JLabel( "Primary Effect:" ), "cell 0 8 2 1" );
		this.textPrimaryEffects = new JTextArea();
		this.textPrimaryEffects.setLineWrap( true );
		JScrollPane scrollPane = new JScrollPane( this.textPrimaryEffects );
		add( scrollPane, "cell 2 8 3 2,grow" );

		JPanel pnlOtherEffects = new JPanel();
		pnlOtherEffects.setBorder( new TitledBorder( null, "Other Effects:", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		pnlOtherEffects.setLayout( new MigLayout( "", "[grow][50!]", "[grow][grow]" ) );
		this.tableEffects = new JXTable( new JXObjectModel<Effect>() );
		this.tableColumnAdjusterEffects = new JXTableColumnAdjuster( this.tableEffects );
		pnlOtherEffects.add( new JScrollPane( this.tableEffects ), "cell 0 0 1 2,grow" );
		add( pnlOtherEffects, "cell 0 10 5 1,grow" );

		this.btnAddEffect = new JButton( "+" );
		this.btnAddEffect.setEnabled( false );
		pnlOtherEffects.add( this.btnAddEffect, "cell 1 0,growx,aligny center" );

		this.btnDelEffect = new JButton( "X" );
		this.btnDelEffect.setEnabled( false );
		pnlOtherEffects.add( this.btnDelEffect, "cell 1 1,growx,aligny center" );

		this.btnApplyUpdate = new JButton( "Apply Update" );
		this.btnApplyUpdate.setEnabled( false );
		add( this.btnApplyUpdate, "cell 0 11 5 1,grow" );
	}

	/* Clear all the form */
	private void clearAll(){
		this.txtName.setText( "" );
		this.txtManaCost.setText( "" );
		this.txtManaCost.setEnabled( true );
		this.txtCardColor.setText( "" );
		this.txtRarity.setText( "" );
		this.txtSeries.setText( "" );
		this.txtType.setText( "" );
		this.txtSubType.setText( "" );
		this.txtPlaneswalkerLife.setText( "" );
		this.txtPlaneswalkerLife.setEnabled( true );;
		this.txtStrenght.setText( "" );
		this.txtStrenght.setEnabled( true );

		this.tableAbility.setModel( new JXObjectModel<>() );
		this.textPrimaryEffects.setText( "" );
		this.tableEffects.setModel( new JXObjectModel<>() );
	}

	private <T extends DisplayableObject> void populateTables(Class<T> abilityClaxx, Set<T> setOfAbilityClaxx, Set<Effect> setOfEffects){
		if(!setOfAbilityClaxx.isEmpty()) {
			JXObjectModel<T> model = new JXObjectModel<>();
			for(T a: setOfAbilityClaxx)
				model.addObject( a );
			this.tableAbility.setModel( model );
			this.tableAbility.getColumnModel().getColumn( 1 ).setCellRenderer( new MyCellRenderer() );
			this.tableColumnAdjusterAbility.adjustColumns();
		}
		
		if(!setOfEffects.isEmpty()) {
			JXObjectModel<Effect> model = new JXObjectModel<>();
			for(Effect e: setOfEffects)
				model.addObject( e );
			this.tableEffects.setModel( model );
			this.tableEffects.getColumnModel().getColumn( 1 ).setCellRenderer( new MyCellRenderer() );
			this.tableColumnAdjusterEffects.adjustColumns();
		}
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * TODO first version. MAKE THIS BETTER!
	 * 
	 * Set the {@link MTGCard} to view.
	 * @param c {@link MTGCard} to view
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public void setMTGCardToView(MTGCard c) throws IllegalArgumentException{
		if(c == null)
			throw new IllegalArgumentException( "Card to view is null." );

		this.clearAll();// reset the form from the previous MTG card view

		String name = c.getName();
		Strength creatureStrength = null;
		Integer planeswalkerLife = -1;
		ManaCost manaCost = null;
		String type = null;
		if(c instanceof Creature) {
			manaCost = ((Creature) c).getManaCost();
			creatureStrength = ((Creature) c).getStrength();
			type = Creature.class.getSimpleName();
			if(c.isArtifact()) type+=" Artifact";
			if(c.isLegendary()) type+=" Legendary";
		}
		else if(c instanceof Sorcery) {
			manaCost = ((Sorcery) c).getManaCost();
			type = Sorcery.class.getSimpleName();
			if(c.isLegendary()) type+=" Legendary";
		}
		else if(c instanceof Instant) {
			manaCost = ((Instant) c).getManaCost();
			type = Instant.class.getSimpleName();
			if(c.isLegendary()) type+=" Legendary";		
		}else if(c instanceof Enchantment) {
			manaCost = ((Enchantment) c).getManaCost();
			type = Enchantment.class.getSimpleName();
			if(c.isLegendary()) type+=" Legendary";	
		}
		else if(c instanceof Planeswalker) {
			manaCost = ((Planeswalker) c).getManaCost();
			planeswalkerLife = ((Planeswalker) c).getLife();
			type = Planeswalker.class.getSimpleName();
			if(c.isLegendary()) type+=" Legendary";	
		}
		else if(c instanceof Artifact) {
			manaCost = ((Artifact) c).getManaCost();
			type = Artifact.class.getSimpleName();
			if(c.isLegendary()) type+=" Legendary";
		}
		else {
			type = Land.class.getSimpleName();
			if(c.isLegendary()) type+=" Legendary";
		}
		CardColor color = c.getCardColor();
		String series = c.getSeries();
		Rarity rarity = c.getRarity();
		String subType = c.getSubType();
		String primaryEffects = c.getPrimaryEffect();

		this.txtName.setText( name );
		if(manaCost==null) 
			this.txtManaCost.setEnabled( false );
		else 
			this.txtManaCost.setText( manaCost.toString() );
		this.txtSeries.setText( series );
		this.txtCardColor.setText( String.format( "%s %s", color.toString(), color.getType() ) );
		this.txtRarity.setText( rarity.toString() );
		this.txtType.setText( type );
		this.txtSubType.setText( subType );
		this.textPrimaryEffects.setText( primaryEffects );
		if(creatureStrength==null)
			this.txtStrenght.setEnabled( false );
		else
			this.txtStrenght.setText( creatureStrength.toString() );
		if(planeswalkerLife==-1)
			this.txtPlaneswalkerLife.setEnabled( false );
		else 
			this.txtPlaneswalkerLife.setText( planeswalkerLife.toString() );

		if(type.equals( Planeswalker.class.getSimpleName() ))
			populateTables( PlanesAbility.class, ((Planeswalker) c).getPlanesAbilities(), c.getEffects() );
		else populateTables( Ability.class, c.getAbilities(), c.getEffects() );

		this.btnAddAbility.setEnabled( true );
		this.btnAddEffect.setEnabled( true );
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* inner class that wrap the lines into description column of table ability and effects */
	public class MyCellRenderer extends JTextArea implements TableCellRenderer
	{
		private static final long serialVersionUID = 1L;

		public MyCellRenderer(){
			setLineWrap( true );
			setWrapStyleWord( true );
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			setText( (String) value );
			setSize( table.getColumnModel().getColumn( column ).getWidth(), getPreferredSize().height );
			if(table.getRowHeight( row ) != getPreferredSize().height)
				table.setRowHeight( row, getPreferredSize().height );
			return this;
		}
	}
}
