package com.hackcaffebabe.mtg.gui;

import it.hackcaffebabe.jx.statusbar.JXStatusBar;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.panel.mtg.MTGProperties;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.*;
import com.hackcaffebabe.mtg.model.color.BasicColors;


/**
 * Utility class that contains gui costants.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class GUIUtils
{
	/** Current software version */
	public static final String VERSION = "BETA_0.3";

	/** Title of main frame */
	public static final String TITLE_MAIN_FRAME = "MTG Card Manager!";
	/** Title of insert card frame */
	public static final String TITLE_INSERT_CARD = "MTG Insert Card!";
	/** Title of update card frame */
	public static final String TITLE_UPDATE_CARD = "MTG Update Card!";
	/** Title of advance search frame */
	public static final String TITLE_ADVANCE_SEARCH = "Advance Search";

	/** Dimension of main frame */
	public static final Dimension DIMENSION_MAIN_FRAME = new Dimension( 1150, 655 );
	/** Dimension of insert card frame */
	public static final Dimension DIMENSION_INSERT_CARD = new Dimension( 660, 700 );
	/** Dimension of advance search frame */
	public static final Dimension DIMENSION_ADVANCE_SEARCH = new Dimension( 485, 190 );

	/* Action Command */
	public static final String AC_CREATURE = "Creature";
	public static final String AC_ARTIFACT = "Artifact";
	public static final String AC_PLANESWALKER = "Planeswalker";
	public static final String AC_LAND = "Land";
	public static final String AC_INSTANT = "Instant";
	public static final String AC_SORCERY = "Sorcery";
	public static final String AC_ENCHANTMENT = "Enchantment";

	/* Some document property used in MTGProperties */
//	public static final String DP_NAME = "name";
//	public static final String DP_MANA_COST = "mana_cost";
//	public static final String DP_CARD_COLOR = "card_color";
//	public static final String DP_RARITY = "rarity";
//	public static final String DP_PLANESWALKER_LIFE = "planeswalker_file";
//	public static final String DP_SERIES = "series";
//	public static final String DP_STRENGTH = "strength";
//	public static final String DP_TYPE = "type";
//	public static final String DP_SUB_TYPE = "sub_type";
//	public static final String DP_PRIMARY_EFFECT = "primary_effect";

	/* Tool Tip TODO maybe add some TP around the program */
	public static final String TP_PANEL_CREATURE_INFO = "Creature strength like \"2/2\". Use * to indicate \"*/*\" or \"X/X\".";

	/** Public access of JXStatsBar of Main frame */
	public static JXStatusBar STATUS_BAR_MAIN_FRAME;
	/** Public access of JXTable of Main frame */
	public static JXTable JXTABLE_MTG;
	public static JXTableColumnAdjuster JXTABLE_MTG_COLUMN_ADJUSTER;
	
	/** Public access of MTGProperties panel */
	public static MTGProperties PNL_MTGPROPERTIES;

//===========================================================================================
// COMMON METHODS
//===========================================================================================
	/**
	 * This method refresh the JXTABLE_MTG of Main frame
	 */
	public static void refreshMTGTable(){
		SwingUtilities.invokeLater( new Runnable(){
			@Override
			public void run(){
				Logger.getInstance().write( Tag.DEBUG, "Refreshing mtg card list" );;
				STATUS_BAR_MAIN_FRAME.setStatus( "Refreshing mtg card list..." );
				List<MTGCard> lst = StoreManager.getInstance().getAllCardsAsList();
				if(!lst.isEmpty()) {
					JXTABLE_MTG.setModel( new JXObjectModel<MTGCard>( lst ) );

					//update sorter and text search
					JXTABLE_MTG.refreshRowSorter();
					JXTABLE_MTG_COLUMN_ADJUSTER.adjustColumns();
				} else {
					JXTABLE_MTG.setModel( new JXObjectModel<>() );
				}
				STATUS_BAR_MAIN_FRAME.setStatus( "MTG Cards list refreshed correctly!" );
			}
		} );
	}

	/**
	 * Display an error message.
	 * @param parent {@link Component} the parent component of error message.
	 * @param msg {@link String} the message error.
	 */
	public static void displayError(Component parent, String msg){
		JOptionPane.showMessageDialog( parent, msg, "Error", JOptionPane.ERROR_MESSAGE );
	}

	/**
	 * This method display a list of file.
	 * @param lst {@link List} of File.
	 */
	public static void displayUnzippedFiles(List<File> lst){
		if(lst.isEmpty()) {
			JOptionPane.showMessageDialog( null, "No file to import", "Done!", JOptionPane.INFORMATION_MESSAGE );
		} else {
			JList<File> lstFiles = new JList<>();
			lstFiles.setEnabled( false );
			DefaultListModel<File> model = new DefaultListModel<>();
			for(File f : lst )
				model.addElement( f );
			lstFiles.setModel( model );

			JComponent[] input = { new JScrollPane( lstFiles ) };
			JOptionPane.showMessageDialog( null, input, "Imported Files:", JOptionPane.INFORMATION_MESSAGE );
		}
	}

	/**
	 * This method show a pop up to get the chose between hybrid or multicolor.
	 * @return -1 if user select Hybrid, 0 if user select Multicolor and -1 otherwise.
	 */
	public static int showHybridMulticolorDialog(){
		JRadioButton isHybrid = new JRadioButton( "Hybrid" );
		JRadioButton isMulticolor = new JRadioButton( "Multicolor" );

		ButtonGroup g = new ButtonGroup();
		g.add( isHybrid );
		g.add( isMulticolor );

		JPanel a = new JPanel();
		a.add( isHybrid );
		a.add( new JLabel( "or" ) );
		a.add( isMulticolor );

		JComponent[] input = { new JLabel( "You have chosen two colors. Your card is:" ), a };

		JOptionPane.showMessageDialog( null, input, "Hybrid or Multicolor?", JOptionPane.INFORMATION_MESSAGE );

		if(isHybrid.isSelected())
			return 1;
		else if(isMulticolor.isSelected())
			return 0;
		else return -1;
	}

	/**
	 * This method show a pop up to get the effect info.
	 * @return {@link Effect}
	 */
	public static Effect showEffectDialog(final Component parent){
		class myActionListener implements ActionListener
		{
			private JTextField txt;
			private ManaCost cost;

			public myActionListener(JTextField t){
				txt = t;
			}

			@Override
			public void actionPerformed(ActionEvent e){
				cost = showManaCost( parent, false );
				txt.setText( cost.toString() );
			}

			public ManaCost getManaCost(){
				return cost;
			}
		}

		JTextField txtMana = new JTextField( 10 );
		txtMana.setEditable( false );

		JButton btnSetMana = new JButton( "Set effect's cost" );
		myActionListener action = new myActionListener( txtMana );
		btnSetMana.addActionListener( action );

		JTextArea txtDescription = new JTextArea( 10, 20 );
		txtDescription.setLineWrap( true );

		JPanel p = new JPanel();
		p.add( txtMana );
		p.add( btnSetMana );

		JComponent[] input = { p, new JScrollPane( txtDescription ) };

		int i = JOptionPane.showConfirmDialog( parent, input, "Insert Effects", JOptionPane.OK_CANCEL_OPTION );
		if(i == JOptionPane.CANCEL_OPTION || i == JOptionPane.CLOSED_OPTION)
			return null;
		try {
			return new Effect( action.cost, txtDescription.getText().replaceAll( "\n", " " ) );
		} catch(Exception ex) {
			return null;
		}
	}

	/**
	 * This method show a pop up to get the ability info.
	 * @return {@link Ability}
	 */
	public static Ability showAbilityDialog(Component parent){
		final JTextField txtAbilty = new JTextField();
		final JTextArea txtDescription = new JTextArea( 10, 20 );
		txtDescription.setLineWrap( true );

		final JCheckBox chbNewAbility = new JCheckBox( "New Ability" );

		final JComboBox<String> cmbAbility = new JComboBox<String>();
		final Set<Map.Entry<String, String>> set = AbilityFactory.getInstance().getAbilities().entrySet();
		if(set.size() == 0) {
			cmbAbility.setEnabled( false );
			chbNewAbility.setEnabled( false );
		} else {
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
			for(Map.Entry<String, String> s: set) {
				model.addElement( s.getKey() );
			}
			cmbAbility.setModel( model );

			txtAbilty.setEnabled( false );
			txtDescription.setEnabled( false );
		}

		ActionListener l = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(chbNewAbility.isSelected()) {
					txtAbilty.setEnabled( true );
					txtDescription.setEnabled( true );
					cmbAbility.setEnabled( false );
				} else {
					txtAbilty.setEnabled( false );
					txtDescription.setEnabled( false );
					cmbAbility.setEnabled( true );
				}
			}
		};
		chbNewAbility.addActionListener( l );

		JComponent[] input = { cmbAbility, chbNewAbility, new JLabel( "Ability name:" ), txtAbilty, new JLabel( "Description:" ),
				new JScrollPane( txtDescription ) };

		int i = JOptionPane.showConfirmDialog( parent, input, "Insert Ability", JOptionPane.OK_CANCEL_OPTION );
		if(i == JOptionPane.CANCEL_OPTION || i == JOptionPane.CLOSED_OPTION)
			return null;
		try {
			if(set.isEmpty())// no ability saved
				return new Ability( txtAbilty.getText(), txtDescription.getText().replaceAll( "\n", " " ) );
			else {
				if(chbNewAbility.isSelected()) {
					return new Ability( txtAbilty.getText(), txtDescription.getText().replaceAll( "\n", " " ) );
				} else {
					String name = (String) cmbAbility.getSelectedItem();
					String description = AbilityFactory.getInstance().getAbilities().get( name );
					return new Ability( name, description );
				}
			}
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * This method show a pop up to get the planes walker ability info.
	 * @return {@link PlanesAbility}
	 */
	public static PlanesAbility showPlanesAbilityDialog(Component parent){
		JSpinner spinLifeCost = new JSpinner( new SpinnerNumberModel( 0, -100, 100, 1 ) );
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinLifeCost.getEditor();
		editor.getTextField().setEnabled( true );
		editor.getTextField().setEditable( false );

		JTextArea txtDescription = new JTextArea( 5, 10 );
		txtDescription.setLineWrap( true );

		JPanel a = new JPanel();
		a.add( new JLabel( "Life Cost:" ) );
		a.add( spinLifeCost );

		JComponent[] input = { a, new JLabel( "Description:" ), new JScrollPane( txtDescription ) };

		JOptionPane.showMessageDialog( parent, input, "Insert Planes Ability", JOptionPane.INFORMATION_MESSAGE );

		try {
			return new PlanesAbility( (Integer) spinLifeCost.getValue(), txtDescription.getText().replaceAll( "\n", " " ) );
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * This method show a pop up to get the mana cost.
	 * @param isForCardCost {@link Boolean} true if is for card cost, otherwise false if is for effects.
	 * @return {@link ManaCost}
	 */
	public static ManaCost showManaCost(Component parent, boolean isForCardCost){
		DocumentFilter docFilter = new DocumentFilter(){
			@Override
			public void insertString(FilterBypass fb, int off, String str, AttributeSet attr) throws BadLocationException{
				// remove non-digits
				fb.insertString( off, str.replaceAll( "\\D++", "" ), attr );
			}

			@Override
			public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr) throws BadLocationException{
				// remove non-digits
				fb.replace( off, len, str.replaceAll( "\\D++", "" ), attr );
			}
		};

		JTextField txtManaColorLess = new JTextField( "0" );
		((AbstractDocument) txtManaColorLess.getDocument()).setDocumentFilter( docFilter );

		JTextField txtManaRed = new JTextField( "0" );
		((AbstractDocument) txtManaRed.getDocument()).setDocumentFilter( docFilter );

		JTextField txtManaBlack = new JTextField( "0" );
		((AbstractDocument) txtManaBlack.getDocument()).setDocumentFilter( docFilter );

		JTextField txtManaGreen = new JTextField( "0" );
		((AbstractDocument) txtManaGreen.getDocument()).setDocumentFilter( docFilter );

		JTextField txtManaWhite = new JTextField( "0" );
		((AbstractDocument) txtManaWhite.getDocument()).setDocumentFilter( docFilter );

		JTextField txtManaBlue = new JTextField( "0" );
		((AbstractDocument) txtManaBlue.getDocument()).setDocumentFilter( docFilter );

		JCheckBox chbTap = new JCheckBox( "TAP" );
		JCheckBox chbX = new JCheckBox( "X" );

		JComponent[] input;
		if(isForCardCost) {
			input = new JComponent[] { new JLabel( "Color Less:" ), txtManaColorLess, new JLabel( "Red:" ), txtManaRed, new JLabel( "Black:" ),
					txtManaBlack, new JLabel( "Green:" ), txtManaGreen, new JLabel( "White:" ), txtManaWhite, new JLabel( "Blue:" ), txtManaBlue,
					chbX };
		} else {
			input = new JComponent[] { new JLabel( "Color Less:" ), txtManaColorLess, new JLabel( "Red:" ), txtManaRed, new JLabel( "Black:" ),
					txtManaBlack, new JLabel( "Green:" ), txtManaGreen, new JLabel( "White:" ), txtManaWhite, new JLabel( "Blue:" ), txtManaBlue,
					chbTap, chbX };
		}

		JOptionPane.showMessageDialog( parent, input, "Insert Mana Cost", JOptionPane.INFORMATION_MESSAGE );

		Integer manaColorLess = Integer.parseInt( txtManaColorLess.getText() );
		Integer manaRed = Integer.parseInt( txtManaRed.getText() );
		Integer manaBlack = Integer.parseInt( txtManaBlack.getText() );
		Integer manaGreen = Integer.parseInt( txtManaGreen.getText() );
		Integer manaWhite = Integer.parseInt( txtManaWhite.getText() );
		Integer manaBlue = Integer.parseInt( txtManaBlue.getText() );

		Map.Entry<BasicColors, Integer> cl = new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.COLOR_LESS, manaColorLess );
		Map.Entry<BasicColors, Integer> red = new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.RED, manaRed );
		Map.Entry<BasicColors, Integer> bck = new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.BLACK, manaBlack );
		Map.Entry<BasicColors, Integer> green = new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.GREEN, manaGreen );
		Map.Entry<BasicColors, Integer> white = new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.WHITE, manaWhite );
		Map.Entry<BasicColors, Integer> blue = new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.BLUE, manaBlue );

		Map.Entry<BasicColors, Integer> x = null;
		if(chbX.isSelected())
			x = new AbstractMap.SimpleEntry<BasicColors, Integer>( BasicColors.COLOR_LESS, -1 );

		if(isForCardCost) {
			if(chbX.isSelected())
				return new ManaCost( cl, red, bck, green, white, blue, x );
			else return new ManaCost( cl, red, bck, green, white, blue );
		} else {
			Map.Entry<BasicColors, Integer> tap = null;

			if(chbTap.isSelected())
				tap = new AbstractMap.SimpleEntry<BasicColors, Integer>( null, -1 );

			if(chbTap.isSelected() && chbX.isSelected())
				return new ManaCost( cl, red, bck, green, white, blue, tap, x );
			else if(chbTap.isSelected())
				return new ManaCost( cl, red, bck, green, white, blue, tap );
			else if(chbX.isSelected())
				return new ManaCost( cl, red, bck, green, white, blue, x );
			else return new ManaCost( cl, red, bck, green, white, blue );
		}
	}
}
