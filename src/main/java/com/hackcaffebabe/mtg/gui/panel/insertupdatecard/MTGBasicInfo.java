package com.hackcaffebabe.mtg.gui.panel.insertupdatecard;

import it.hackcaffebabe.jx.typeahead.CommitAction;
import it.hackcaffebabe.jx.typeahead.JXAutocomplete;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.listener.BasicColorActionListener;
import com.hackcaffebabe.mtg.gui.panel.listener.TextToShortCutterFocusListener;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.color.Mana;


/**
 * Panel to get the baisc info of MTG card. 
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class MTGBasicInfo extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JComboBox<Rarity> cmbRarity;

	private BasicColorActionListener basicColorActionListener;
	private JCheckBox chbRed;
	private JCheckBox chbBlack;
	private JCheckBox chbGreen;
	private JCheckBox chbBlue;
	private JCheckBox chbWhite;

	private JTextField txtSeries;
	private JXAutocomplete txtSeriesAutocomplete;

	private JTextField txtSubType;
	private JXAutocomplete txtSubTypeAutocomplete;

	private JCheckBox chbIsLegendary;
	private JCheckBox chbIsArtifact;

	/**
	 * Create the panel.
	 */
	public MTGBasicInfo(){
		super();
		setBorder( new TitledBorder( "Basic Info" ) );
		setLayout( new MigLayout( "", "[][][][][][grow][][47.00,grow][grow]", "[][][]" ) );
		this.initContent();
		this.initShortCutter();
	}

	/* initialize all components */
	private void initContent(){
		add( new JLabel( "Name:" ), "cell 0 0,alignx trailing" );
		this.txtName = new JTextField();
		add( this.txtName, "cell 1 0 5 1,growx" );

		add( new JLabel( "Rarity:" ), "cell 6 0,alignx trailing" );
		this.cmbRarity = new JComboBox<Rarity>();
		this.cmbRarity.setModel( new DefaultComboBoxModel<>( Rarity.getAllRarity().toArray( new Rarity[] {} ) ) );
		add( cmbRarity, "cell 7 0 2 1,growx" );

		add( new JLabel( "Color:" ), "cell 0 1" );
		this.basicColorActionListener = new BasicColorActionListener();
		this.chbRed = new JCheckBox( "Red" );
		this.chbRed.setActionCommand( Mana.getAbbraviation( Mana.RED ) );
		this.chbRed.addActionListener( basicColorActionListener );
		add( chbRed, "cell 1 1" );
		this.chbBlack = new JCheckBox( "Black" );
		this.chbBlack.setActionCommand( Mana.getAbbraviation( Mana.BLACK ) );
		this.chbBlack.addActionListener( basicColorActionListener );
		add( chbBlack, "cell 2 1" );
		this.chbGreen = new JCheckBox( "Green" );
		this.chbGreen.setActionCommand( Mana.getAbbraviation( Mana.GREEN ) );
		this.chbGreen.addActionListener( basicColorActionListener );
		add( chbGreen, "cell 3 1" );
		this.chbBlue = new JCheckBox( "Blue" );
		this.chbBlue.setActionCommand( Mana.getAbbraviation( Mana.BLUE ) );
		this.chbBlue.addActionListener( basicColorActionListener );
		add( chbBlue, "cell 4 1" );
		this.chbWhite = new JCheckBox( "White" );
		this.chbWhite.setActionCommand( Mana.getAbbraviation( Mana.WHITE ) );
		this.chbWhite.addActionListener( basicColorActionListener );
		add( chbWhite, "cell 5 1" );

		add( new JLabel( "Series:" ), "cell 6 1,alignx trailing" );
		this.txtSeries = new JTextField();
		this.txtSeriesAutocomplete = new JXAutocomplete( this.txtSeries, StoreManager.getInstance().getInsertedSeries() );
		this.txtSeries.getInputMap().put( KeyStroke.getKeyStroke( "TAB" ), "commit" );
		this.txtSeries.getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ), "commit" );
		this.txtSeries.getActionMap().put( "commit", new CommitAction( this.txtSeriesAutocomplete ) );
		add( this.txtSeries, "cell 7 1 2 1,growx" );

		add( new JLabel( "Flags:" ), "cell 0 2" );
		this.chbIsLegendary = new JCheckBox( "is Legendary?" );
		add( chbIsLegendary, "cell 1 2 2 1" );
		this.chbIsArtifact = new JCheckBox( "Is Artifact ?" );
		add( chbIsArtifact, "cell 3 2 2 1" );

		add( new JLabel( "Sub Type:" ), "cell 6 2,alignx trailing" );
		this.txtSubType = new JTextField();
		this.txtSubTypeAutocomplete = new JXAutocomplete( this.txtSubType, StoreManager.getInstance()
				.getInsertedSubTypes() );
		this.txtSubType.getInputMap().put( KeyStroke.getKeyStroke( "TAB" ), "commit" );
		this.txtSubType.getInputMap().put( KeyStroke.getKeyStroke( "ENTER" ), "commit" );
		this.txtSubType.getActionMap().put( "commit", new CommitAction( this.txtSubTypeAutocomplete ) );
		add( this.txtSubType, "cell 7 2 2 1,growx" );
	}

	private void initShortCutter(){
		this.txtName.addFocusListener( new TextToShortCutterFocusListener( "name" ) );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * Reset the panel content
	 */
	public void reset(){
		this.basicColorActionListener.reset();
		this.txtName.setText( "" );

		this.cmbRarity.setSelectedIndex( 0 );

		this.chbBlack.setSelected( false );
		this.chbBlue.setSelected( false );
		this.chbGreen.setSelected( false );
		this.chbRed.setSelected( false );
		this.chbWhite.setSelected( false );
		this.chbIsArtifact.setSelected( false );
		this.chbIsLegendary.setSelected( false );

		this.txtSeries.setText( "" );
		this.txtSubType.setText( "" );

		this.chbIsArtifact.setSelected( false );
		this.chbIsArtifact.setEnabled( true );
		this.chbIsLegendary.setSelected( false );
		this.chbIsLegendary.setEnabled( true );

		// this is necessary to update the auto complete textFiled with new inserted series
		this.txtSeriesAutocomplete.setKeywords( StoreManager.getInstance().getInsertedSeries() );
		this.txtSubTypeAutocomplete.setKeywords( StoreManager.getInstance().getInsertedSubTypes() );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * This method display basic data from {@link MTGCard} given.
	 * @param c {@link MTGCard} if null nothing happen;
	 */
	public void setData(MTGCard c){
		if(c != null) {
			this.txtName.setText( c.getName() );
			this.cmbRarity.setSelectedItem( c.getRarity() );
			this.txtSeries.setText( c.getSeries() );
			this.txtSubType.setText( c.getSubType() );
			for(Mana s: c.getCardColor().getColors()) {
				if(s.equals( Mana.RED ))
					chbRed.setSelected( true );
				else if(s.equals( Mana.BLACK ))
					chbBlack.setSelected( true );
				else if(s.equals( Mana.BLUE ))
					chbBlue.setSelected( true );
				else if(s.equals( Mana.GREEN ))
					chbGreen.setSelected( true );
				else if(s.equals( Mana.WHITE ))
					chbWhite.setSelected( true );
			}
			this.basicColorActionListener.setCardColor( c.getCardColor() );
			this.chbIsLegendary.setSelected( c.isLegendary() );
			this.chbIsArtifact.setSelected( c.isArtifact() );
		}
	}

	/**
	 * Set the component enable or not
	 */
	public void setIsLegendaryEnable(boolean t){
		this.chbIsLegendary.setEnabled( t );
	}

	/**
	 * Set the component enable or not
	 */
	public void setIsArtifactEnable(boolean t){
		this.chbIsArtifact.setEnabled( t );
	}

	/**
	 * Enable/Disable all colors
	 */
	public void setAllColorsEnable(boolean t){
		this.chbBlack.setEnabled( t );
		this.chbBlue.setEnabled( t );
		this.chbGreen.setEnabled( t );
		this.chbRed.setEnabled( t );
		this.chbWhite.setEnabled( t );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Return the MTG name inserted.
	 * @return {@link String} the name or null if is not inserted.
	 */
	public String getNames(){
		String s = this.txtName.getText();
		if(s == null || s.isEmpty())
			return null;
		else return s;
	}

	/**
	 * Return the selected Rarity.
	 * @return {@link Rarity} the selected rarity.
	 */
	public Rarity getRarity(){
		return (Rarity) this.cmbRarity.getSelectedItem();
	}

	/**
	 * Returns the MTG card color
	 * @return {@link OLD_CardColor}
	 */
	public CardColor getCardColor(){
		return basicColorActionListener.getCardColor();
	}

	/**
	 * Return the MTG series inserted.
	 * @return {@link String} the series. if not inserted return empty string.
	 */
	public String getSeries(){
		String s = this.txtSeries.getText();
		if(s == null)
			return "";
		else return s.replaceAll( "\t", "" ).replace( ".", "" );
	}

	/**
	 * Return the MTG sub type inserted.
	 * @return {@link String} the sub type or null if is not inserted.
	 */
	public String getSubType(){
		String s = this.txtSubType.getText();
		if(s == null || s.isEmpty())
			return null;
		else return s;
	}

	/**
	 * @return {@link Boolean} if is legendary is selected.
	 */
	public boolean isLegendarySelected(){
		return this.chbIsLegendary.isSelected();
	}

	/**
	 * @return {@link Boolean} if is artifact is selected.
	 */
	public boolean isArtifactSelected(){
		return this.chbIsArtifact.isSelected();
	}
}
