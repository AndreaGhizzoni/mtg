package com.hackcaffebabe.mtg.gui.panel.advancesearch;

import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.controller.json.Criteria;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.frame.AdvanceSearch;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.BasicColors;


/**
 * Content of {@link AdvanceSearch}.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class AdvanceSearchContent extends JPanel
{
	private static final long serialVersionUID = 1L;
	private Criteria criteria = new Criteria();

	private JCheckBox chbRed;
	private JCheckBox chbBlack;
	private JCheckBox chbGreen;
	private JCheckBox chbBlue;
	private JCheckBox chbWhite;

	private JComboBox<String> cmbRarity;

	private JComboBox<String> cmbSeries;

	private JSpinner spinManaCost;

	/**
	 * Create the panel.
	 */
	public AdvanceSearchContent(){
		super();
		setLayout( new MigLayout( "", "[209.00][148.00][190.00]", "[][][]" ) );
		this.initContent();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		// Color
		JPanel pnlCardColor = new JPanel();
		pnlCardColor.setBorder( new TitledBorder( "Color" ) );
		pnlCardColor.setLayout( new MigLayout( "", "[][][][][]", "[]" ) );

		this.chbRed = new JCheckBox( "Red" );
		this.chbRed.setActionCommand( BasicColors.getAbbraviation( BasicColors.RED ) );
		pnlCardColor.add( this.chbRed, "cell 0 0" );

		this.chbBlack = new JCheckBox( "Black" );
		this.chbBlack.setActionCommand( BasicColors.getAbbraviation( BasicColors.BLACK ) );
		pnlCardColor.add( this.chbBlack, "cell 1 0" );

		this.chbGreen = new JCheckBox( "Green" );
		this.chbGreen.setActionCommand( BasicColors.getAbbraviation( BasicColors.GREEN ) );
		pnlCardColor.add( this.chbGreen, "cell 2 0" );

		this.chbBlue = new JCheckBox( "Blue" );
		this.chbBlue.setActionCommand( BasicColors.getAbbraviation( BasicColors.BLUE ) );
		pnlCardColor.add( this.chbBlue, "cell 3 0" );

		this.chbWhite = new JCheckBox( "White" );
		this.chbWhite.setActionCommand( BasicColors.getAbbraviation( BasicColors.WHITE ) );
		pnlCardColor.add( this.chbWhite, "cell 4 0" );
		add( pnlCardColor, "cell 0 0 2 1,grow" );

		// Rarity
		JPanel pnlRarity = new JPanel();
		pnlRarity.setBorder( new TitledBorder( "Rarity" ) );
		pnlRarity.setLayout( new MigLayout( "", "[]", "[]" ) );
		this.cmbRarity = new JComboBox<>( getRartyCB() );
		pnlRarity.add( this.cmbRarity, "cell 0 0,growx" );
		add( pnlRarity, "cell 2 0,grow" );

		// Converted Mana Cost
		JPanel pnlConvertedManaCost = new JPanel();
		pnlConvertedManaCost.setBorder( new TitledBorder( "Conv. Mana Cost" ) );
		pnlConvertedManaCost.setLayout( new MigLayout( "", "[168.00]", "[]" ) );
		this.spinManaCost = new JSpinner( new SpinnerNumberModel( 0, 0, 100, 1 ) );
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) this.spinManaCost.getEditor();
		editor.getTextField().setEditable( false );
		pnlConvertedManaCost.add( this.spinManaCost, "cell 0 0,growx,aligny center" );
		add( pnlConvertedManaCost, "cell 2 1,grow" );

		// Series
		JPanel pnlSeries = new JPanel();
		pnlSeries.setBorder( new TitledBorder( "Series" ) );
		pnlSeries.setLayout( new MigLayout( "", "[grow]", "[]" ) );
		this.cmbSeries = new JComboBox<String>( getSeriesCB() );
		pnlSeries.add( this.cmbSeries, "cell 0 0,growx" );
		add( pnlSeries, "cell 0 1 2 1,growx" );

		JButton btnClear = new JButton( "Clear" );
		btnClear.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				criteria = new Criteria();
				applyCriteriaChanges();
			}
		} );
		add( btnClear, "cell 0 2,growx" );

		JButton btnApply = new JButton( "Apply" );
		btnApply.addActionListener( new ApplyActionListener() );
		add( btnApply, "cell 1 2 2 1,growx" );
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
		for(String a: StoreManager.getInstance().getInsertedSeries())
			s.addElement( a );
		return s;
	}

	/* apply on the table the criteria */
	@SuppressWarnings("unchecked")
	private void applyCriteriaChanges(){
		List<MTGCard> lst = StoreManager.getInstance().searchBy( this.criteria );
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
			if(chbRed.isSelected())
				criteria.byBasiColors( BasicColors.RED );
			if(chbBlack.isSelected())
				criteria.byBasiColors( BasicColors.BLACK );
			if(chbGreen.isSelected())
				criteria.byBasiColors( BasicColors.GREEN );
			if(chbBlue.isSelected())
				criteria.byBasiColors( BasicColors.BLUE );
			if(chbWhite.isSelected())
				criteria.byBasiColors( BasicColors.WHITE );
			boolean noColorSel = !chbRed.isSelected() && !chbBlack.isSelected() && !chbGreen.isSelected()
					&& !chbBlue.isSelected() && !chbWhite.isSelected();
			if(noColorSel)
				criteria.byBasiColors( null );

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

			applyCriteriaChanges();
		}
	}
}
