package com.hackcaffebabe.mtg.gui.panel.mtg;

import static com.hackcaffebabe.mtg.gui.GUIUtils.STATUS_BAR_MAIN_FRAME;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.DisplayableObject;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.gui.frame.InsertUpdateCard;
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
import com.hackcaffebabe.mtg.model.card.Strength;


/**
 * This panel shows the {@link MTGCard} properties.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class MTGProperties extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JTextField txtName = new JTextField();
	private JTextField txtManaCost = new JTextField();
	private JTextField txtCardColor = new JTextField();
	private JTextField txtRarity = new JTextField();
	private JTextField txtType = new JTextField();
	private JTextField txtSubType = new JTextField();
	private JTextField txtPlaneswalkerLife = new JTextField();
	private JTextField txtStrenght = new JTextField();
	private JTextField txtSeries = new JTextField();
	private JTextArea textPrimaryEffects = new JTextArea();

	private JXTable tableAbility;
	private JXTableColumnAdjuster tableColumnAdjusterAbility;

	private JXTable tableEffects;
	private JXTableColumnAdjuster tableColumnAdjusterEffects;

	private JButton btnUpdate;

	private MTGCard displayedMTGCard = null;

	/**
	 * Create the panel.
	 */
	public MTGProperties(){
		super();
		setBorder( new TitledBorder( new LineBorder( new Color( 184, 207, 229 ) ), "MTG Properties",
				TitledBorder.RIGHT, TitledBorder.TOP, null, null ) );
		setLayout( new MigLayout( "", "[44.00][38.00][150.00][80.00][grow]",
				"[][][][][][][150px,grow][][:96.00:100,grow][150px][]" ) );
		this.initContent();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		add( new JLabel( "Name:" ), "cell 0 0,alignx right" );
		this.txtName.setEditable( false );
		add( this.txtName, "cell 1 0 4 1,growx" );

		add( new JLabel( "Card Color:" ), "cell 0 1,alignx right" );
		this.txtCardColor.setEditable( false );
		add( this.txtCardColor, "cell 1 1 2 1,growx" );

		add( new JLabel( "Mana Cost:" ), "cell 3 1,alignx trailing" );
		this.txtManaCost.setEditable( false );
		add( this.txtManaCost, "cell 4 1,growx" );

		add( new JLabel( "Rarity:" ), "cell 0 2,alignx right" );
		this.txtRarity.setEditable( false );
		add( this.txtRarity, "cell 1 2 2 1,growx" );

		add( new JLabel( "PW Life:" ), "cell 3 2,alignx trailing" );
		this.txtPlaneswalkerLife.setEditable( false );
		add( this.txtPlaneswalkerLife, "cell 4 2,growx" );

		add( new JLabel( "Series:" ), "cell 0 3,alignx trailing" );
		this.txtSeries.setEditable( false );
		add( this.txtSeries, "cell 1 3 2 1,growx" );

		add( new JLabel( "Strength:" ), "cell 3 3,alignx trailing" );
		this.txtStrenght.setEditable( false );
		add( this.txtStrenght, "cell 4 3,growx" );

		add( new JLabel( "Type:" ), "cell 0 4,alignx right" );
		this.txtType.setEditable( false );
		add( this.txtType, "cell 1 4 4 1,growx" );

		add( new JLabel( "Sub Type:" ), "cell 0 5,alignx right" );
		this.txtSubType.setEditable( false );
		add( this.txtSubType, "cell 1 5 4 1,growx" );

		JPanel pnlAbility = new JPanel();
		pnlAbility.setBorder( new TitledBorder( "Ability:" ) );
		pnlAbility.setLayout( new MigLayout( "", "[grow]", "[grow][grow]" ) );
		this.tableAbility = new JXTable( new JXObjectModel<DisplayableObject>() );
		this.tableColumnAdjusterAbility = new JXTableColumnAdjuster( this.tableAbility );
		pnlAbility.add( new JScrollPane( this.tableAbility ), "cell 0 0 1 2,grow" );
		add( pnlAbility, "cell 0 6 5 1,grow" );

		add( new JLabel( "Primary Effect:" ), "cell 0 7 2 1" );
		this.textPrimaryEffects.setLineWrap( true );
		this.textPrimaryEffects.setWrapStyleWord( true );
		this.textPrimaryEffects.setEditable( false );
		add( new JScrollPane( this.textPrimaryEffects, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), "cell 2 7 3 2,grow" );

		JPanel pnlOtherEffects = new JPanel();
		pnlOtherEffects.setBorder( new TitledBorder( "Other Effects:" ) );
		pnlOtherEffects.setLayout( new MigLayout( "", "[grow]", "[grow][grow]" ) );
		this.tableEffects = new JXTable( new JXObjectModel<Effect>() );
		this.tableColumnAdjusterEffects = new JXTableColumnAdjuster( this.tableEffects );
		pnlOtherEffects.add( new JScrollPane( this.tableEffects, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), "cell 0 0 1 2,grow" );
		add( pnlOtherEffects, "cell 0 9 5 1,grow" );

		this.btnUpdate = new JButton( "Update MTG Card" );
		this.btnUpdate.setEnabled( false );
		this.btnUpdate.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new InsertUpdateCard( displayedMTGCard ).setVisible( true );
			}
		} );
		add( this.btnUpdate, "cell 0 10 5 1,grow" );
	}

	/**
	 * Clear all the form
	 */
	public void clearAll(){
		this.displayedMTGCard = null;// this is necessary for all the documents listener.
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

	/* this method populate the table ability and effects */
	private <T extends DisplayableObject> void populateTables(Class<T> abilityClaxx, Set<T> setOfAbilityClaxx,
			Set<Effect> setOfEffects){
		if(!setOfAbilityClaxx.isEmpty()) {
			JXObjectModel<T> model = new JXObjectModel<>();
			for(T a: setOfAbilityClaxx)
				model.addObject( a );
			this.tableAbility.setModel( model );
			this.tableAbility.getColumnModel().getColumn( 1 ).setCellRenderer( new CellRendererAsTextArea() );
			this.tableColumnAdjusterAbility.adjustColumns();
		}

		if(!setOfEffects.isEmpty()) {
			JXObjectModel<Effect> model = new JXObjectModel<>();
			for(Effect e: setOfEffects)
				model.addObject( e );
			this.tableEffects.setModel( model );
			this.tableEffects.getColumnModel().getColumn( 1 ).setCellRenderer( new CellRendererAsTextArea() );
			this.tableColumnAdjusterEffects.adjustColumns();
		}
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

		//do not display the same card that is already displayed.
		if(this.displayedMTGCard != null && this.displayedMTGCard.hashCode() == c.hashCode())
			return;

		Logger.getInstance().write( Tag.DEBUG, c.toString() );

		this.clearAll();// reset the form from the previous MTG card view

		Strength creatureStrength = null;
		Integer planeswalkerLife = -1;
		ManaCost manaCost = null;
		String type = null;
		if(c instanceof Creature) {
			manaCost = ((Creature) c).getManaCost();
			creatureStrength = ((Creature) c).getStrength();
			type = Creature.class.getSimpleName();
			if(c.isArtifact())
				type += " Artifact";
			if(c.isLegendary())
				type += " Legendary";
		} else if(c instanceof Sorcery) {
			manaCost = ((Sorcery) c).getManaCost();
			type = Sorcery.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		} else if(c instanceof Instant) {
			manaCost = ((Instant) c).getManaCost();
			type = Instant.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		} else if(c instanceof Enchantment) {
			manaCost = ((Enchantment) c).getManaCost();
			type = Enchantment.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		} else if(c instanceof Planeswalker) {
			manaCost = ((Planeswalker) c).getManaCost();
			planeswalkerLife = ((Planeswalker) c).getLife();
			type = Planeswalker.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		} else if(c instanceof Artifact) {
			manaCost = ((Artifact) c).getManaCost();
			type = Artifact.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		} else { // this type is land
			type = Land.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		}

		this.txtName.setText( c.getName() );
		this.txtSubType.setText( c.getSubType() );
		this.txtSeries.setText( c.getSeries() );
		this.txtRarity.setText( c.getRarity().toString() );
		this.txtCardColor.setText( String.format( "%s %s", c.getCardColor().toString(), c.getCardColor().getType() ) );
		this.textPrimaryEffects.setText( c.getPrimaryEffect() );
		this.txtType.setText( type );
		if(manaCost == null)
			this.txtManaCost.setEnabled( false );
		else this.txtManaCost.setText( manaCost.toString() );
		if(creatureStrength == null)
			this.txtStrenght.setEnabled( false );
		else this.txtStrenght.setText( creatureStrength.toString() );
		if(planeswalkerLife == -1)
			this.txtPlaneswalkerLife.setEnabled( false );
		else this.txtPlaneswalkerLife.setText( planeswalkerLife.toString() );

		if(type.equals( Planeswalker.class.getSimpleName() ))
			populateTables( PlanesAbility.class, ((Planeswalker) c).getPlanesAbilities(), c.getEffects() );
		else populateTables( Ability.class, c.getAbilities(), c.getEffects() );

		this.displayedMTGCard = c;// this is necessary for all the documents listener.
		STATUS_BAR_MAIN_FRAME.setStatus( c.getName() );
		this.btnUpdate.setEnabled( true );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the displayed card. if no card is displayed return null.
	 * @return {@link MTGCard} or null.
	 */
	public MTGCard getDisplayedCard(){
		return this.displayedMTGCard;
	}

	/**
	 * Return true if this panel is displaying a {@link MTGCard}.
	 * @return {@link Boolean}
	 */
	public boolean isDisplayingACard(){
		return this.displayedMTGCard == null ? false : true;
	}
}
