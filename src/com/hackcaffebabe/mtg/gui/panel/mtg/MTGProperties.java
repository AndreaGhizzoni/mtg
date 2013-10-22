package com.hackcaffebabe.mtg.gui.panel.mtg;

import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.DisplayableObject;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.gui.GUIUtils;
import com.hackcaffebabe.mtg.gui.listener.*;
import com.hackcaffebabe.mtg.model.*;
import com.hackcaffebabe.mtg.model.card.*;


/**
 * This panel shows the {@link MTGCard} properties.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class MTGProperties extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private CheckerCard checkerCardDL = new CheckerCard();

	private JTextField txtName = new JTextField();
	private JTextField txtManaCost = new JTextField();
	private JTextField txtCardColor = new JTextField();
	private JTextField txtRarity = new JTextField();// TODO for rarity and series find a way to add multiple Document Listener
	private JTextField txtType = new JTextField();
	private JTextField txtSubType = new JTextField();
	private JTextField txtPlaneswalkerLife = new JTextField();
	private JTextField txtStrenght = new JTextField();
	private JTextField txtSeries = new JTextField();
	private JTextArea textPrimaryEffects = new JTextArea();

	private JXTable tableAbility;
	private JXTableColumnAdjuster tableColumnAdjusterAbility;
	private JButton btnAddAbility;
	private JButton btnDelAbility;

	private JXTable tableEffects;
	private JXTableColumnAdjuster tableColumnAdjusterEffects;
	private JButton btnAddEffect;
	private JButton btnDelEffect;

	private JButton btnApplyUpdate;

	private MTGCard displayedMTGCard = null;
	
	private Logger log = Logger.getInstance();

	/**
	 * Create the panel.
	 */
	public MTGProperties(){
		super();
		setBorder( new TitledBorder( "MTG Properties" ) );
		setLayout( new MigLayout( "", "[44.00][38.00][180.00][100.00][grow]", "[][][][][][][][150px,grow][][:96.00:100,grow][150px][]" ) );
		setOpaque( true );
		this.initContent();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		add( new JLabel( "Name:" ), "cell 0 0,alignx right" );
		this.txtName.getDocument().addDocumentListener( this.checkerCardDL );
		this.txtName.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_NAME );
		add( this.txtName, "cell 1 0 4 1,growx" );

		add( new JLabel( "Mana Cost:" ), "cell 0 1,alignx right" );
		this.txtManaCost.getDocument().addDocumentListener( this.checkerCardDL );
		this.txtManaCost.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_MANA_COST );
		add( this.txtManaCost, "cell 1 1 3 1,growx" );

		add( new JLabel( "Card Color:" ), "cell 0 2,alignx right" );
		this.txtCardColor.getDocument().addDocumentListener( this.checkerCardDL );
		this.txtCardColor.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_MANA_COST );
		add( this.txtCardColor, "cell 1 2 3 1,growx" );

		add( new JLabel( "Rarity:" ), "cell 0 3,alignx right" );
		this.txtRarity.getDocument().addDocumentListener( this.checkerCardDL );
		this.txtRarity.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_RARITY );
		add( this.txtRarity, "cell 1 3 2 1,growx" );

		add( new JLabel( "PW Life:" ), "cell 3 3,alignx trailing" );
		this.txtPlaneswalkerLife.getDocument().addDocumentListener( this.checkerCardDL );
		this.txtPlaneswalkerLife.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_PLANESWALKER_LIFE );
		add( this.txtPlaneswalkerLife, "cell 4 3,growx" );

		add( new JLabel( "Series:" ), "cell 0 4,alignx trailing" );
		this.txtSeries.getDocument().addDocumentListener( this.checkerCardDL );
		this.txtSeries.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_SERIES );
		add( this.txtSeries, "cell 1 4 2 1,growx" );

		add( new JLabel( "Strength:" ), "cell 3 4,alignx trailing" );
		this.txtStrenght.getDocument().addDocumentListener( this.checkerCardDL );
		this.txtStrenght.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_STRENGTH );
		add( this.txtStrenght, "cell 4 4,growx" );

		add( new JLabel( "Type:" ), "cell 0 5,alignx right" );
		this.txtType.getDocument().addDocumentListener( this.checkerCardDL );
		this.txtType.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_TYPE );
		add( this.txtType, "cell 1 5 3 1,growx" );

		add( new JLabel( "Sub Type:" ), "cell 0 6,alignx right" );
		this.txtSubType.getDocument().addDocumentListener( this.checkerCardDL );
		this.txtSubType.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_SUB_TYPE );
		add( this.txtSubType, "cell 1 6 3 1,growx" );

		JPanel pnlAbility = new JPanel();
		pnlAbility.setBorder( new TitledBorder( "Ability:" ) );
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
		this.btnDelAbility.addActionListener( new DelAbilityActionListener( this.tableAbility, this.tableColumnAdjusterAbility ) );
		pnlAbility.add( this.btnDelAbility, "cell 1 1,growx,aligny center" );

		add( new JLabel( "Primary Effect:" ), "cell 0 8 2 1" );
		this.textPrimaryEffects.getDocument().addDocumentListener( this.checkerCardDL );
		this.textPrimaryEffects.getDocument().putProperty( CheckerCard.DOCUMENT_NAME, GUIUtils.DP_PRIMARY_EFFECT );
		this.textPrimaryEffects.setLineWrap( true );
		add( new JScrollPane( this.textPrimaryEffects ), "cell 2 8 3 2,grow" );

		JPanel pnlOtherEffects = new JPanel();
		pnlOtherEffects.setBorder( new TitledBorder( "Other Effects:" ) );
		pnlOtherEffects.setLayout( new MigLayout( "", "[grow][50!]", "[grow][grow]" ) );
		this.tableEffects = new JXTable( new JXObjectModel<Effect>() );
		this.tableColumnAdjusterEffects = new JXTableColumnAdjuster( this.tableEffects );
		pnlOtherEffects.add( new JScrollPane( this.tableEffects ), "cell 0 0 1 2,grow" );
		add( pnlOtherEffects, "cell 0 10 5 1,grow" );

		this.btnAddEffect = new JButton( "+" );
		this.btnAddEffect.addActionListener( new AddEffectActionListener( this, tableEffects, tableColumnAdjusterEffects ) );
		this.btnAddEffect.setEnabled( false );
		pnlOtherEffects.add( this.btnAddEffect, "cell 1 0,growx,aligny center" );

		this.btnDelEffect = new JButton( "X" );
		this.btnDelEffect.setEnabled( false );
		this.btnDelEffect.addActionListener( new DelEffectActionListener( this.tableEffects, this.tableColumnAdjusterEffects ) );
		pnlOtherEffects.add( this.btnDelEffect, "cell 1 1,growx,aligny center" );

		this.btnApplyUpdate = new JButton( "Apply Update" );
		this.btnApplyUpdate.setEnabled( false );
		add( this.btnApplyUpdate, "cell 0 11 5 1,grow" );
	}

	/* Clear all the form */
	private void clearAll(){
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
	private <T extends DisplayableObject> void populateTables(Class<T> abilityClaxx, Set<T> setOfAbilityClaxx, Set<Effect> setOfEffects){
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
		}
		else if(c instanceof Sorcery) {
			manaCost = ((Sorcery) c).getManaCost();
			type = Sorcery.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		}
		else if(c instanceof Instant) {
			manaCost = ((Instant) c).getManaCost();
			type = Instant.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		}
		else if(c instanceof Enchantment) {
			manaCost = ((Enchantment) c).getManaCost();
			type = Enchantment.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		}
		else if(c instanceof Planeswalker) {
			manaCost = ((Planeswalker) c).getManaCost();
			planeswalkerLife = ((Planeswalker) c).getLife();
			type = Planeswalker.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		}
		else if(c instanceof Artifact) {
			manaCost = ((Artifact) c).getManaCost();
			type = Artifact.class.getSimpleName();
			if(c.isLegendary())
				type += " Legendary";
		}
		else { // this type is land
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
		else 
			this.txtManaCost.setText( manaCost.toString() );
		if(creatureStrength == null)
			this.txtStrenght.setEnabled( false );
		else 
			this.txtStrenght.setText( creatureStrength.toString() );
		if(planeswalkerLife == -1)
			this.txtPlaneswalkerLife.setEnabled( false );
		else 
			this.txtPlaneswalkerLife.setText( planeswalkerLife.toString() );

		if(type.equals( Planeswalker.class.getSimpleName() ))
			populateTables( PlanesAbility.class, ((Planeswalker) c).getPlanesAbilities(), c.getEffects() );
		else 
			populateTables( Ability.class, c.getAbilities(), c.getEffects() );

		this.btnAddAbility.setEnabled( true );
		this.btnAddEffect.setEnabled( true );
		this.displayedMTGCard = c;// this is necessary for all the documents listener.
		GUIUtils.STATUS_BAR_MAIN_FRAME.setStatus( c.getName() );
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* 
	 * Document that listen on all JTextField in the panel 
	 * TODO maybe this Document Listener can moved on separate file? 
	 * TODO maybe this class can implements ActionListener too. In this way i can add this btnApplyUpdate and manage the update here.
	 */
	private class CheckerCard implements DocumentListener
	{
		public static final String DOCUMENT_NAME = "name";

		@Override
		public void insertUpdate(DocumentEvent e){
			if(displayedMTGCard!=null){
				log.write( Tag.DEBUG, String.format( "insertUpdate() called on %s", e.getDocument().getProperty( DOCUMENT_NAME ) ) );
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e){
			if(displayedMTGCard!=null){
				log.write( Tag.DEBUG, String.format( "removeUpdate() called on %s", e.getDocument().getProperty( DOCUMENT_NAME ) ) );
				
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e){
			if(displayedMTGCard!=null){
				log.write( Tag.DEBUG, String.format( "changedUpdate() called on %s", e.getDocument().getProperty( DOCUMENT_NAME ) ) );
			}
		}
	}

	/* inner class that wrap the lines into description column of table ability and effects */
	public class CellRendererAsTextArea extends JTextArea implements TableCellRenderer
	{
		private static final long serialVersionUID = 1L;

		public CellRendererAsTextArea(){
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
