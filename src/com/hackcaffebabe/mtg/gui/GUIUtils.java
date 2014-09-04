package com.hackcaffebabe.mtg.gui;

import it.hackcaffebabe.jx.statusbar.JXStatusBar;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
import com.hackcaffebabe.mtg.controller.StringNormalizer;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.panel.listener.ShortCutterV2;
import com.hackcaffebabe.mtg.gui.panel.mtg.MTGProperties;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Ability;
import com.hackcaffebabe.mtg.model.card.AbilityFactory;
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.card.PlanesAbility;
import com.hackcaffebabe.mtg.model.color.Mana;
import com.hackcaffebabe.mtg.model.cost.ManaCost;
import com.hackcaffebabe.mtg.model.cost.Tuple;


/**
 * Utility class that contains gui costants.
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class GUIUtils
{
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
				long start = System.currentTimeMillis();
				Logger.getInstance().write( Tag.DEBUG, "Refreshing mtg card list..." );
				STATUS_BAR_MAIN_FRAME.setStatus( "Refreshing mtg card list..." );
				List<MTGCard> lst = StoreManager.getInstance().getAllCardsAsList();
				if(!lst.isEmpty()) {
					JXTABLE_MTG.setModel( new JXObjectModel<MTGCard>( lst ) );
					JXTABLE_MTG.refreshRowSorter();
					JXTABLE_MTG_COLUMN_ADJUSTER.adjustColumns();
				} else {
					JXTABLE_MTG.setModel( new JXObjectModel<>() );
				}
				long end = System.currentTimeMillis();
				Logger.getInstance().write( Tag.DEBUG,
						String.format( "Refreshing mtg card list done %d ms", (end - start) ) );
				STATUS_BAR_MAIN_FRAME.setStatus( "MTG Cards list refreshed correctly!" );
			}
		} );
	}

	/**
	 * This method display an exception and report the that on log.
	 * @param parent {@link Component} the parent component of error message.
	 * @param ex {@link Exception} to log.
	 */
	public static void displayError(Component parent, Exception ex){
		String m = String.format( "%s\nLog is Reported.", ex.getMessage() );
		JOptionPane.showMessageDialog( parent, m, "Error", JOptionPane.ERROR_MESSAGE );

		Logger.getInstance().write( Tag.ERRORS, ex.getMessage() );
		ex.printStackTrace( Logger.getInstance().getPrintStream() );
	}

	/**
	 * This method display a success message
	 * @param parent {@link Component} the parent component of error message.
	 * @param msg {@link String} the message.
	 */
	public static void displaySuccessMessage(Component parent, String msg){
		JOptionPane.showMessageDialog( parent, msg, "Succes!", JOptionPane.INFORMATION_MESSAGE );
	}

	/**
	 * This method display a warning message
	 * @param parent {@link Component} the parent component of error message.
	 * @param msg {@link String} the message.
	 */
	public static void displayWarningMessage(Component parent, String msg){
		JOptionPane.showMessageDialog( parent, msg, "Bad Luck!", JOptionPane.WARNING_MESSAGE );
	}

	/**
	 * This method display a list of file.
	 * @param lst {@link List} of File.
	 */
	public static void displayFiles(List<File> lst){
		if(lst.isEmpty()) {
			JOptionPane.showMessageDialog( null, "No file to import", "Done!", JOptionPane.INFORMATION_MESSAGE );
		} else {
			JList<File> lstFiles = new JList<>();
			lstFiles.setEnabled( false );
			DefaultListModel<File> model = new DefaultListModel<>();
			for(File f: lst)
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
	 * @param parent {@link Component} to show the pop up
	 * @param e {@link Effect} to edit, null to get new one
	 * @return {@link Effect} if argument give are correct, null otherwise.
	 */
	public static Effect showEffectDialog(final Component parent, Effect e){
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
		}

		JTextField txtMana = new JTextField( 10 );
		txtMana.setText( e == null ? "" : e.getManaCost().toString() );
		txtMana.setEditable( false );

		JButton btnSetMana = new JButton( "Set effect's cost" );
		myActionListener action = new myActionListener( txtMana );
		action.cost = e == null ? null : e.getManaCost();
		btnSetMana.addActionListener( action );

		JTextArea txtDescription = new JTextArea( 10, 20 );
		txtDescription.setWrapStyleWord( true );
		txtDescription.setLineWrap( true );
		txtDescription.setText( e == null ? "" : e.getText() );
		ShortCutterV2.getInstance().add( txtDescription );
		txtDescription.getInputMap().put( ShortCutterV2.KEYSTROKE, ShortCutterV2.KEY );
		txtDescription.getActionMap().put( ShortCutterV2.KEY, ShortCutterV2.getInstance() );

		JPanel p = new JPanel();
		p.add( txtMana );
		p.add( btnSetMana );

		JComponent[] input = { p, new JScrollPane( txtDescription ) };

		int i = JOptionPane.showConfirmDialog( parent, input, "Insert Effects", JOptionPane.OK_CANCEL_OPTION );
		if(i == JOptionPane.CANCEL_OPTION || i == JOptionPane.CLOSED_OPTION)
			return null;
		try {
			return new Effect( action.cost, StringNormalizer.removeAccentCharacters( txtDescription.getText() ) );
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
		txtDescription.setWrapStyleWord( true );
		txtDescription.setLineWrap( true );
		ShortCutterV2.getInstance().add( txtDescription );
		txtDescription.getInputMap().put( ShortCutterV2.KEYSTROKE, ShortCutterV2.KEY );
		txtDescription.getActionMap().put( ShortCutterV2.KEY, ShortCutterV2.getInstance() );

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

		JComponent[] input = { cmbAbility, chbNewAbility, new JLabel( "Ability name:" ), txtAbilty,
				new JLabel( "Description:" ), new JScrollPane( txtDescription ) };

		int i = JOptionPane.showConfirmDialog( parent, input, "Insert Ability", JOptionPane.OK_CANCEL_OPTION );
		if(i == JOptionPane.CANCEL_OPTION || i == JOptionPane.CLOSED_OPTION)
			return null;

		String ability = StringNormalizer.removeAccentCharacters( txtAbilty.getText() );
		String description = StringNormalizer.removeAccentCharacters( txtDescription.getText() );
		try {
			if(set.isEmpty()) {// no ability saved
				return new Ability( ability, description );
			} else {
				if(chbNewAbility.isSelected()) {
					return new Ability( ability, description );
				} else {
					String n = (String) cmbAbility.getSelectedItem();
					String d = AbilityFactory.getInstance().getAbilities().get( n );
					return new Ability( n, d );
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
		ShortCutterV2.getInstance().add( txtDescription );
		txtDescription.getInputMap().put( ShortCutterV2.KEYSTROKE, ShortCutterV2.KEY );
		txtDescription.getActionMap().put( ShortCutterV2.KEY, ShortCutterV2.getInstance() );

		JPanel a = new JPanel();
		a.add( new JLabel( "Life Cost:" ) );
		a.add( spinLifeCost );

		JComponent[] input = { a, new JLabel( "Description:" ), new JScrollPane( txtDescription ) };
		JOptionPane.showMessageDialog( parent, input, "Insert Planes Ability", JOptionPane.INFORMATION_MESSAGE );

		try {
			String description = StringNormalizer.removeAccentCharacters( txtDescription.getText() );
			return new PlanesAbility( (Integer) spinLifeCost.getValue(), description );
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
			public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
					throws BadLocationException{
				// remove non-digits
				fb.insertString( off, str.replaceAll( "\\D++", "" ), attr );
			}

			@Override
			public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr)
					throws BadLocationException{
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

		JTextField txtX = new JTextField( "0" );
		((AbstractDocument) txtX.getDocument()).setDocumentFilter( docFilter );

		JCheckBox chbTap = new JCheckBox( "TAP" );

		JComponent[] input;
		if(isForCardCost) {
			input = new JComponent[] { new JLabel( "Color Less:" ), txtManaColorLess, new JLabel( "Red:" ), txtManaRed,
					new JLabel( "Black:" ), txtManaBlack, new JLabel( "Green:" ), txtManaGreen, new JLabel( "White:" ),
					txtManaWhite, new JLabel( "Blue:" ), txtManaBlue, new JLabel( "X" ), txtX };
		} else {
			input = new JComponent[] { new JLabel( "Color Less:" ), txtManaColorLess, new JLabel( "Red:" ), txtManaRed,
					new JLabel( "Black:" ), txtManaBlack, new JLabel( "Green:" ), txtManaGreen, new JLabel( "White:" ),
					txtManaWhite, new JLabel( "Blue:" ), txtManaBlue, new JLabel( "X" ), txtX, chbTap };
		}

		JOptionPane.showMessageDialog( parent, input, "Insert Mana Cost", JOptionPane.INFORMATION_MESSAGE );

		Integer manaColorLess = Integer.parseInt( txtManaColorLess.getText() );
		Integer manaRed = Integer.parseInt( txtManaRed.getText() );
		Integer manaBlack = Integer.parseInt( txtManaBlack.getText() );
		Integer manaGreen = Integer.parseInt( txtManaGreen.getText() );
		Integer manaWhite = Integer.parseInt( txtManaWhite.getText() );
		Integer manaBlue = Integer.parseInt( txtManaBlue.getText() );
		Integer manaX = Integer.parseInt( txtX.getText() );

		Tuple<Mana, Integer> cl = new Tuple<Mana, Integer>( Mana.COLOR_LESS, manaColorLess );
		Tuple<Mana, Integer> red = new Tuple<Mana, Integer>( Mana.RED, manaRed );
		Tuple<Mana, Integer> bck = new Tuple<Mana, Integer>( Mana.BLACK, manaBlack );
		Tuple<Mana, Integer> green = new Tuple<Mana, Integer>( Mana.GREEN, manaGreen );
		Tuple<Mana, Integer> white = new Tuple<Mana, Integer>( Mana.WHITE, manaWhite );
		Tuple<Mana, Integer> blue = new Tuple<Mana, Integer>( Mana.BLUE, manaBlue );
		Tuple<Mana, Integer> x = new Tuple<Mana, Integer>( Mana.X, manaX );

		if(isForCardCost) {
			return new ManaCost( cl, red, bck, green, white, blue, x );
		} else {
			if(chbTap.isSelected())
				return new ManaCost( cl, red, bck, green, white, blue, x, new Tuple<>( Mana.TAP, -1 ) );
			else return new ManaCost( cl, red, bck, green, white, blue, x );
		}
	}
}
