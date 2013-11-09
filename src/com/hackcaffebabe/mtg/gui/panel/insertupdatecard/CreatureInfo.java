package com.hackcaffebabe.mtg.gui.panel.insertupdatecard;

import static com.hackcaffebabe.mtg.gui.GUIUtils.TP_PANEL_CREATURE_INFO;
import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.card.Strength;
import net.miginfocom.swing.MigLayout;
import java.awt.Component;
import javax.swing.text.AttributeSet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.SwingConstants;


/**
 * Panel to get the MTG Card creature info.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.1
 */
public class CreatureInfo extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JTextField txtPower;
	private JTextField txtToughness;

	/**
	 * Create the panel.
	 */
	public CreatureInfo(){
		super();
		setBorder( new TitledBorder( "Creature Info" ) );
		setLayout( new MigLayout( "", "[][grow][][grow]", "[pref!]" ) );
		setToolTipText( TP_PANEL_CREATURE_INFO );
		this.initContent();
	}

	/* initialize all components */
	private void initContent(){
		add( new JLabel( "Power:" ), "cell 0 0,alignx left" );
		add( new JLabel( "Toughness:" ), "cell 2 0,alignx left" );

		DocumentFilter docFilter = new DocumentFilter(){
			@Override
			//TODO find a regular expression to avoid "***" or "XXXX" or "xxxx"
			public void insertString(FilterBypass fb, int off, String str, AttributeSet attr) throws BadLocationException{
				fb.insertString( off, str.replaceAll( "[^0-9 X x *]", "" ), attr );
			}

			@Override
			public void replace(FilterBypass fb, int off, int len, String str, AttributeSet attr) throws BadLocationException{
				fb.replace( off, len, str.replaceAll( "[^0-9 X x *]", "" ), attr );
			}
		};

		this.txtPower = new JTextField();
		this.txtPower.setHorizontalAlignment( SwingConstants.CENTER );
		((AbstractDocument) this.txtPower.getDocument()).setDocumentFilter( docFilter );
		add( this.txtPower, "cell 1 0,growx" );

		this.txtToughness = new JTextField();
		this.txtToughness.setHorizontalAlignment( SwingConstants.CENTER );
		((AbstractDocument) this.txtToughness.getDocument()).setDocumentFilter( docFilter );
		add( this.txtToughness, "cell 3 0,growx" );

	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * This method display the {@link Strength} object from {@link MTGCard} given.
	 * @param c {@link MTGCard} if null or not instance of Creature.class nothing happen;
	 */
	public void setData(MTGCard c){
		if(c != null && c instanceof Creature) {
			Creature creature = ((Creature) c);
			this.txtPower.setText( String.valueOf( creature.getStrength().getPower() ) );
			this.txtToughness.setText( String.valueOf( creature.getStrength().getToughness() ) );
		}
	}

	/**
	 * Disable all components
	 */
	public void disableAllComponents(){
		this.txtPower.setText( "" );
		this.txtToughness.setText( "" );
		for(Component c: getComponents()) {
			c.setEnabled( false );
		}
	}

	/**
	 * Enable all components
	 */
	public void enableAllComponents(){
		for(Component c: getComponents()) {
			c.setEnabled( true );
		}
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Return the creature strength as {@link Strength}.
	 * @return {@link Strength} the creature strength.
	 */
	public Strength getStrength(){
		String power = this.txtPower.getText();
		String toughness = this.txtToughness.getText();
		if(power == null && toughness == null)
			return null;
		else if(power == null || toughness == null)
			return null;
		else if(power.isEmpty() && toughness.isEmpty())
			return null;
		else if(power.isEmpty() || toughness.isEmpty())
			return null;
		else {
			try {
				if(power.toLowerCase().equals( "x" ) || power.toLowerCase().equals( "*" ))
					power = "-1";
				else if(toughness.toLowerCase().equals( "x" ) || toughness.toLowerCase().equals( "*" ))
					toughness = "-1";
				Strength s = new Strength( String.format( "%s/%s", power, toughness ) );
				return s;
			} catch(IllegalArgumentException e) {
				return null;
			}
		}
	}
}
