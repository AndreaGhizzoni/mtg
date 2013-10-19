package com.hackcaffebabe.mtg.gui.panel.advancesearch;

import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import com.hackcaffebabe.mtg.controller.json.Criteria;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.frame.AdvanceSearch;
import com.hackcaffebabe.mtg.gui.panel.FocusTraversalOnArray;
import com.hackcaffebabe.mtg.model.*;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.BasicColors;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.Component;


/**
 * Content of {@link AdvanceSearch}.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class AdvanceSearchContent extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JXTable table;
	private Criteria criteria = new Criteria();

	private ColorsActionListener colorsActionListener;
	private JCheckBox chbRed;
	private JCheckBox chbBlack;
	private JCheckBox chbGreen;
	private JCheckBox chbBlue;
	private JCheckBox chbWhite;

	private JComboBox<String> cmbRarity;

	private JComboBox<String> cmbSeries;

	private JSpinner spinManaCost;

	private JCheckBox chbIsLegendary;
	private JCheckBox chbHasPrimaryeffect;
	private JCheckBox chbHasEffect;
	private JCheckBox chbHasAbility;

	private JComboBox<String> cmbType;

	/**
	 * Create the panel.
	 */
	public AdvanceSearchContent(JXTable t){
		super();
		this.table = t;
		setLayout( new MigLayout( "", "[52.00][100.00][83.00][27.00][190.00]", "[][][][]" ) );
		this.initContent();
		setFocusTraversalPolicy( new FocusTraversalOnArray( new Component[] { chbBlack, chbGreen, chbRed, chbBlue, chbWhite, cmbRarity, cmbSeries,
				chbIsLegendary, chbHasPrimaryeffect, chbHasEffect, chbHasAbility, spinManaCost } ) );//TODO this doesn't work properly
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* Initialize the content */
	private void initContent(){
		// Color
		JPanel pnlCardColor = new JPanel();
		pnlCardColor.setBorder( new TitledBorder( null, "Color", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		pnlCardColor.setLayout( new MigLayout( "", "[][][][][]", "[]" ) );
		add( pnlCardColor, "cell 0 0 4 1,grow" );

		this.colorsActionListener = new ColorsActionListener();
		this.chbRed = new JCheckBox( "Red" );
		this.chbRed.setActionCommand( BasicColors.getAbbraviation( BasicColors.RED ) );
		this.chbRed.addActionListener( colorsActionListener );
		pnlCardColor.add( this.chbRed, "cell 0 0" );

		this.chbBlack = new JCheckBox( "Black" );
		this.chbBlack.setActionCommand( BasicColors.getAbbraviation( BasicColors.BLACK ) );
		this.chbBlack.addActionListener( colorsActionListener );
		pnlCardColor.add( this.chbBlack, "cell 1 0" );

		this.chbGreen = new JCheckBox( "Green" );
		this.chbGreen.setActionCommand( BasicColors.getAbbraviation( BasicColors.GREEN ) );
		this.chbGreen.addActionListener( colorsActionListener );
		pnlCardColor.add( this.chbGreen, "cell 2 0" );

		this.chbBlue = new JCheckBox( "Blue" );
		this.chbBlue.setActionCommand( BasicColors.getAbbraviation( BasicColors.BLUE ) );
		this.chbBlue.addActionListener( colorsActionListener );
		pnlCardColor.add( this.chbBlue, "cell 3 0" );

		this.chbWhite = new JCheckBox( "White" );
		this.chbWhite.setActionCommand( BasicColors.getAbbraviation( BasicColors.WHITE ) );
		this.chbWhite.addActionListener( colorsActionListener );
		pnlCardColor.add( this.chbWhite, "cell 4 0" );

		// Rarity
		JPanel pnlRarity = new JPanel();
		pnlRarity.setBorder( new TitledBorder( null, "Rarity", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		pnlRarity.setLayout( new MigLayout( "", "[]", "[]" ) );
		add( pnlRarity, "cell 4 0,grow" );

		this.cmbRarity = new JComboBox<>( getRartyCB() );
		this.cmbRarity.addActionListener( new RarityActionListener() );
		pnlRarity.add( this.cmbRarity, "cell 0 0,growx" );

		// Series
		add( new JLabel( "Series:" ), "cell 0 1,alignx trailing" );
		this.cmbSeries = new JComboBox<String>(getSeriesCB());
		this.cmbSeries.addActionListener( new SeriesActionListener() );
		add( this.cmbSeries, "cell 1 1 2 1,growx" );

		// Type
//		add(new JLabel("Type:"), "cell 3 1,alignx trailing");
//		this.cmbType = new JComboBox<>(getTypeCB());
//		this.cmbType.addActionListener( new TypeActionListener() );
//		add(this.cmbType, "cell 4 1,alignx center,growy");

		// Converted Mana Cost
		JPanel pnlConvertedManaCost = new JPanel();
		pnlConvertedManaCost.setBorder( new TitledBorder( null, "Conv. Mana Cost", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		pnlConvertedManaCost.setLayout( new MigLayout( "", "[168.00]", "[]" ) );
		add( pnlConvertedManaCost, "cell 4 2 1 2,grow" );

		this.spinManaCost = new JSpinner( new SpinnerNumberModel( 0, 0, 100, 1 ) );
		this.spinManaCost.addChangeListener( new ManaCostChangeListener() );
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) this.spinManaCost.getEditor();
		editor.getTextField().setEnabled( true );
		editor.getTextField().setEditable( false );
		pnlConvertedManaCost.add( this.spinManaCost, "cell 0 0,growx,aligny center" );

		// Flag
		this.chbIsLegendary = new JCheckBox( "Is Legendary?" );
		this.chbIsLegendary.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				criteria = criteria.byIsLegendary( chbIsLegendary.isSelected() ? true : null );
				applyCriteriaChanges();
			}
		} );
		add( this.chbIsLegendary, "cell 0 2 2 1" );

		this.chbHasPrimaryeffect = new JCheckBox( "Has Primary Effect?" );
		this.chbHasPrimaryeffect.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				criteria = criteria.byHasPrimaryEffect( chbHasPrimaryeffect.isSelected() ? true : null );
				applyCriteriaChanges();
			}
		} );
		add( this.chbHasPrimaryeffect, "cell 2 2 2 1" );

		this.chbHasEffect = new JCheckBox( "Has Effect?" );
		this.chbHasEffect.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				criteria = criteria.byHasEffect( chbHasEffect.isSelected() ? true : null );
				applyCriteriaChanges();
			}
		} );
		add( this.chbHasEffect, "cell 0 3 2 1" );

		this.chbHasAbility = new JCheckBox( "Has Ability?" );
		this.chbHasAbility.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				criteria = criteria.byHasAbility( chbHasAbility.isSelected() ? true : null );
				applyCriteriaChanges();
			}
		} );
		add( this.chbHasAbility, "cell 2 3 2 1" );
	}

	/* return the rarity as combo box model. */
	private DefaultComboBoxModel<String> getRartyCB(){
		DefaultComboBoxModel<String> s = new DefaultComboBoxModel<>();
		s.addElement( "-------------" );
		for(Rarity r: Rarity.getAllRarity())
			s.addElement( r.toString() );
		return s;
	}

	/* return the series as combo box model. */
	private DefaultComboBoxModel<String> getSeriesCB(){
		DefaultComboBoxModel<String> s = new DefaultComboBoxModel<>();
		s.addElement( "-------------" );
		for(String a : StoreManager.getInstance().getInsertedSeries() )
			s.addElement( a );
		return s;
	}
	
//	/* return the type as combo box model. */
//	private DefaultComboBoxModel<String> getTypeCB(){
//		DefaultComboBoxModel<String> s = new DefaultComboBoxModel<>();
//		s.addElement( "-------------" );
//		s.addElement( Creature.class.getSimpleName() );
//		s.addElement( Artifact.class.getSimpleName() );
//		s.addElement( Instant.class.getSimpleName() );
//		s.addElement( Sorcery.class.getSimpleName() );
//		s.addElement( Enchantment.class.getSimpleName() );
//		s.addElement( Land.class.getSimpleName() );
//		s.addElement( Planeswalker.class.getSimpleName() );
//		return s;
//	}

	/* apply on the table the criteria */
	@SuppressWarnings("unchecked")
	private void applyCriteriaChanges(){
		List<MTGCard> lst = StoreManager.getInstance().searchBy( this.criteria );
		if(!lst.isEmpty()) {
			JXObjectModel<MTGCard> model = (JXObjectModel<MTGCard>) this.table.getModel();
			model.removeAll();
			model.addObjects( lst );
			this.table.setModel( model );
		}
		else {
			((JXObjectModel<MTGCard>) this.table.getModel()).removeAll();
		}
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* event on click on Colors */
	private class ColorsActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			String ac = e.getActionCommand();
			if(ac.equals( BasicColors.getAbbraviation( BasicColors.RED ) )) {
				criteria = criteria.byBasiColors( BasicColors.RED );
			}
			else if(ac.equals( BasicColors.getAbbraviation( BasicColors.BLACK ) )) {
				criteria = criteria.byBasiColors( BasicColors.BLACK );
			}
			else if(ac.equals( BasicColors.getAbbraviation( BasicColors.GREEN ) )) {
				criteria = criteria.byBasiColors( BasicColors.GREEN );
			}
			else if(ac.equals( BasicColors.getAbbraviation( BasicColors.BLUE ) )) {
				criteria = criteria.byBasiColors( BasicColors.BLUE );
			}
			else {// WHITE
				criteria = criteria.byBasiColors( BasicColors.WHITE );
			}
			applyCriteriaChanges();
		}
	}

	/* event on click on Series */
	private class SeriesActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			String sel = (String) cmbSeries.getSelectedItem();
			criteria = criteria.bySeries( sel.equals( "-------------" )?null:sel );
			applyCriteriaChanges();
		}
	}
	
	/* event on change the rarity */
	private class RarityActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			String sel = (String) cmbRarity.getSelectedItem();
			if(sel.equals( Rarity.COMMON.toString() )) {
				criteria = criteria.byRarity( Rarity.COMMON );
			}
			else if(sel.equals( Rarity.NON_COMMON.toString() )) {
				criteria = criteria.byRarity( Rarity.NON_COMMON );
			}
			else if(sel.equals( Rarity.RARE.toString() )) {
				criteria = criteria.byRarity( Rarity.RARE );
			}
			else if(sel.equals( Rarity.MYTHIC.toString() )) {
				criteria = criteria.byRarity( Rarity.MYTHIC );
			}
			else {// "--------"
				criteria = criteria.byRarity( null );
			}
			applyCriteriaChanges();
		}
	}

	/* event on change mana cost */
	private class ManaCostChangeListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e){
			JSpinner s = (JSpinner) e.getSource();
			int val = (Integer) s.getValue();
			criteria = criteria.byConvertedManaCost( val == 0 ? null : val );
			applyCriteriaChanges();
		}
	}

//	/* event on change the type */
//	private class TypeActionListener implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e){
//			String sel = (String)cmbType.getSelectedItem();
//			if( sel.equals(Creature.class.getSimpleName()) ){
//				criteria = criteria.byType( Creature.class );
//			}else if( sel.equals(Artifact.class.getSimpleName()) ){
//				criteria = criteria.byType( Artifact.class );
//			}else if( sel.equals(Instant.class.getSimpleName()) ){
//				criteria = criteria.byType( Instant.class );
//			}else if( sel.equals(Sorcery.class.getSimpleName()) ){
//				criteria = criteria.byType( Sorcery.class );
//			}else if( sel.equals(Enchantment.class.getSimpleName()) ){
//				criteria = criteria.byType( Enchantment.class );
//			}else if( sel.equals(Land.class.getSimpleName()) ){
//				criteria = criteria.byType( Land.class );
//			}else if( sel.equals(Planeswalker.class.getSimpleName()) ){
//				criteria = criteria.byType( Planeswalker.class );
//			}else{// "--------"
//				criteria = criteria.byType( null );
//			}
//		}		
//	}
}
