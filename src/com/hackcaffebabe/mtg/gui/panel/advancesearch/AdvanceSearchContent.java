package com.hackcaffebabe.mtg.gui.panel.advancesearch;

import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.controller.json.Criteria;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.frame.AdvanceSearch;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.Mana;


/**
 * Content of {@link AdvanceSearch}.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class AdvanceSearchContent extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JRadioButton rdbtnLazy;
	private JRadioButton rdbtnSpecific;

	private JCheckBox chbRed;
	private JCheckBox chbBlack;
	private JCheckBox chbGreen;
	private JCheckBox chbBlue;
	private JCheckBox chbWhite;

	private JComboBox<String> cmbRarity;
	private JComboBox<String> cmbSeries;
	private JComboBox<String> cmbSubType;

	private JSpinner spinManaCost;

	/**
	 * Create the panel.
	 */
	public AdvanceSearchContent(){
		super();
		setLayout( new MigLayout( "", "[209.00,grow][148.00][130.00,grow]", "[25.00][][][][30,grow]" ) );
		this.initContent();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		JPanel pnlModeSelector = new JPanel();
		pnlModeSelector.setBorder( new TitledBorder( new LineBorder( new Color( 184, 207, 229 ) ), "Search Mode:",
				TitledBorder.CENTER, TitledBorder.TOP, null, null ) );
		pnlModeSelector.setLayout( new MigLayout( "", "[][grow]", "[:15:15][]" ) );
		this.rdbtnLazy = new JRadioButton( "Lazy" );
		this.rdbtnLazy.setSelected( true );// default selection
		this.rdbtnSpecific = new JRadioButton( "Specific" );
		ButtonGroup g = new ButtonGroup();
		g.add( this.rdbtnLazy );
		g.add( this.rdbtnSpecific );
		pnlModeSelector.add( this.rdbtnLazy, "cell 0 0" );
		pnlModeSelector.add( this.rdbtnSpecific, "cell 1 0,alignx right" );
		add( pnlModeSelector, "cell 0 0 3 1,grow" );

		// Color
		JPanel pnlCardColor = new JPanel();
		pnlCardColor.setBorder( new TitledBorder( "Color" ) );
		pnlCardColor.setLayout( new MigLayout( "", "[][][][][]", "[]" ) );

		this.chbRed = new JCheckBox( "Red" );
		this.chbRed.setActionCommand( Mana.getAbbraviation( Mana.RED ) );
		pnlCardColor.add( this.chbRed, "cell 0 0" );

		this.chbBlack = new JCheckBox( "Black" );
		this.chbBlack.setActionCommand( Mana.getAbbraviation( Mana.BLACK ) );
		pnlCardColor.add( this.chbBlack, "cell 1 0" );

		this.chbGreen = new JCheckBox( "Green" );
		this.chbGreen.setActionCommand( Mana.getAbbraviation( Mana.GREEN ) );
		pnlCardColor.add( this.chbGreen, "cell 2 0" );

		this.chbBlue = new JCheckBox( "Blue" );
		this.chbBlue.setActionCommand( Mana.getAbbraviation( Mana.BLUE ) );
		pnlCardColor.add( this.chbBlue, "cell 3 0" );

		this.chbWhite = new JCheckBox( "White" );
		this.chbWhite.setActionCommand( Mana.getAbbraviation( Mana.WHITE ) );
		pnlCardColor.add( this.chbWhite, "cell 4 0" );
		add( pnlCardColor, "cell 0 1 2 1,grow" );

		// Rarity
		JPanel pnlRarity = new JPanel();
		pnlRarity.setBorder( new TitledBorder( null, "Rarity", TitledBorder.RIGHT, TitledBorder.TOP, null, null ) );
		pnlRarity.setLayout( new MigLayout( "", "[84.00,grow]", "[]" ) );
		this.cmbRarity = new JComboBox<>( getCB( Rarity.getAllRarity() ) );
		pnlRarity.add( this.cmbRarity, "cell 0 0,growx" );
		add( pnlRarity, "cell 2 1,grow" );

		// Converted Mana Cost
		JPanel pnlConvertedManaCost = new JPanel();
		pnlConvertedManaCost.setBorder( new TitledBorder( null, "Conv. Mana Cost", TitledBorder.RIGHT,
				TitledBorder.TOP, null, null ) );
		pnlConvertedManaCost.setLayout( new MigLayout( "", "[168.00]", "[]" ) );
		this.spinManaCost = new JSpinner( new SpinnerNumberModel( 0, 0, 100, 1 ) );
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) this.spinManaCost.getEditor();
		editor.getTextField().setEditable( false );
		pnlConvertedManaCost.add( this.spinManaCost, "cell 0 0,growx,aligny center" );
		add( pnlConvertedManaCost, "cell 2 2,grow" );

		// Series
		JPanel pnlSeries = new JPanel();
		pnlSeries.setBorder( new TitledBorder( "Series" ) );
		pnlSeries.setLayout( new MigLayout( "", "[grow]", "[]" ) );
		this.cmbSeries = new JComboBox<>( getCB( StoreManager.getInstance().getInsertedSeries() ) );
		pnlSeries.add( this.cmbSeries, "cell 0 0,growx" );
		add( pnlSeries, "cell 0 2 2 1,growx" );

		// Sub Type
		JPanel pnlSubType = new JPanel();
		pnlSubType.setBorder( new TitledBorder( "Sub Type" ) );
		pnlSubType.setLayout( new MigLayout( "", "[grow]", "[]" ) );
		this.cmbSubType = new JComboBox<>( getCB( StoreManager.getInstance().getInsertedSubTypes() ) );
		pnlSubType.add( this.cmbSubType, "cell 0 0,growx" );
		add( pnlSubType, "cell 0 3 3 1,grow" );

		JButton btnClear = new JButton( "Clear" );
		btnClear.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				applyCriteriaChanges( null );
			}
		} );

		btnClear.setMnemonic( KeyEvent.VK_C );
		add( btnClear, "cell 0 4,growx,aligny bottom" );

		JButton btnApply = new JButton( "Apply" );
		btnApply.addActionListener( new ApplyActionListener() );
		btnApply.setMnemonic( KeyEvent.VK_A );
		add( btnApply, "cell 1 4 2 1,growx,aligny bottom" );
	}

	/* returns the appropriate combo box model for the list of object given */
	private DefaultComboBoxModel<String> getCB(List< ? > lst){
		DefaultComboBoxModel<String> s = new DefaultComboBoxModel<>();
		s.addElement( "-------------" );
		for(Object a: lst)
			s.addElement( a.toString() );
		return s;
	}

	/* returns the criteria mode */
	private Criteria.Mode getCriteriaMode(){
		if(this.rdbtnLazy.isSelected())
			return Criteria.Mode.LAZY;
		else if(this.rdbtnSpecific.isSelected())
			return Criteria.Mode.SPECIFIC;
		else return Criteria.Mode.LAZY;//if no selected returns the default > LAZY
	}

	/* apply on the table the criteria */
	@SuppressWarnings("unchecked")
	private void applyCriteriaChanges(Criteria c){
		List<MTGCard> lst = StoreManager.getInstance()
				.searchBy( c == null ? new Criteria() : c, this.getCriteriaMode() );
		if(!lst.isEmpty()) {
			JXObjectModel<MTGCard> model = (JXObjectModel<MTGCard>) JXTABLE_MTG.getModel();
			model.removeAll();
			model.addObjects( lst );
			JXTABLE_MTG.setModel( model );
		} else {
			((JXObjectModel<MTGCard>) JXTABLE_MTG.getModel()).removeAll();
		}
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* event handle of apply button */
	private class ApplyActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			Criteria criteria = new Criteria();

			if(chbRed.isSelected())
				criteria.byColors( Mana.RED );
			if(chbBlack.isSelected())
				criteria.byColors( Mana.BLACK );
			if(chbGreen.isSelected())
				criteria.byColors( Mana.GREEN );
			if(chbBlue.isSelected())
				criteria.byColors( Mana.BLUE );
			if(chbWhite.isSelected())
				criteria.byColors( Mana.WHITE );
			boolean noColorSel = !chbRed.isSelected() && !chbBlack.isSelected() && !chbGreen.isSelected()
					&& !chbBlue.isSelected() && !chbWhite.isSelected();
			if(noColorSel)
				criteria.byColors( null );

			String rar = (String) cmbRarity.getSelectedItem();
			if(rar.equals( Rarity.COMMON.toString() )) {
				criteria.byRarity( Rarity.COMMON );
			} else if(rar.equals( Rarity.NON_COMMON.toString() )) {
				criteria.byRarity( Rarity.NON_COMMON );
			} else if(rar.equals( Rarity.RARE.toString() )) {
				criteria.byRarity( Rarity.RARE );
			} else if(rar.equals( Rarity.MYTHIC.toString() )) {
				criteria.byRarity( Rarity.MYTHIC );
			} else {// "--------"
				criteria.byRarity( null );
			}

			String s = (String) cmbSeries.getSelectedItem();
			criteria.bySeries( s.equals( "-------------" ) ? null : s );

			int val = (Integer) spinManaCost.getValue();
			criteria.byConvertedManaCost( val == 0 ? null : val );

			String i = (String) cmbSubType.getSelectedItem();
			criteria.bySubType( i.equals( "-------------" ) ? null : i );

			applyCriteriaChanges( criteria );
		}
	}
}
