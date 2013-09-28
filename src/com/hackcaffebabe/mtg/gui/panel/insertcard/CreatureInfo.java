package com.hackcaffebabe.mtg.gui.panel.insertcard;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import com.hackcaffebabe.mtg.model.card.Strength;
import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;

/**
 * Panel to get the MTG Card creature info.
 *  
 * TODO find a regex that allows the digits and "X", "x", "*" 
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
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
		setBorder(new TitledBorder(null, "Creature Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[][grow][][grow]", "[pref!]"));
		this.initContent();
	}
	
	private void initContent(){
		add( new JLabel("Power:"), "cell 0 0,alignx left");
		add( new JLabel("Toughness:"), "cell 2 0,alignx left");
		
//		DocumentFilter docFilter = new DocumentFilter(){
//			@Override
//			public void insertString( FilterBypass fb, int off, String str, AttributeSet attr ) throws BadLocationException {
//			    // remove non-digits
//			    fb.insertString(off, str.replaceAll("\\D++", ""), attr);
//			    
//			} 
//			@Override
//			public void replace( FilterBypass fb, int off, int len, String str, AttributeSet attr ) throws BadLocationException {
//			    // remove non-digits
//			    fb.replace(off, len, str.replaceAll("\\D++", ""), attr);
//			}
//		};
		
		this.txtPower = new JTextField();
		this.txtPower.setHorizontalAlignment(SwingConstants.CENTER);
//		((AbstractDocument)this.txtPower.getDocument()).setDocumentFilter( docFilter );
		add( this.txtPower, "cell 1 0,growx");
		
		
		this.txtToughness = new JTextField();
		this.txtToughness.setHorizontalAlignment(SwingConstants.CENTER);
//		((AbstractDocument)this.txtToughness.getDocument()).setDocumentFilter( docFilter );
		add( this.txtToughness, "cell 3 0,growx");
	
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * Disable all components
	 */
	public void disableAllComponents( ){
		this.txtPower.setText( "" );
		this.txtToughness.setText( "" );
		for( Component c : getComponents() ){
			c.setEnabled( false );
		}
	}
	
	/**
	 * Enable all components
	 */
	public void enableAllComponents( ){
		for( Component c : getComponents() ){
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
		if( power == null && toughness == null )
			return null;
		else if( power == null || toughness == null )
			return null;
		else if( power.isEmpty() && toughness.isEmpty() )
			return null;
		else if( power.isEmpty() || toughness.isEmpty() )
			return null;
		else {
			try{
				if( power.toLowerCase().equals( "x" ) || power.toLowerCase().equals( "*" ) )
					power = "-1";
				else if( toughness.toLowerCase().equals( "x" ) || toughness.toLowerCase().equals( "*" ) )
					toughness = "-1";
				Strength s = new Strength( String.format( "%s/%s", power,toughness ) );
				return s;
			}catch( IllegalArgumentException e ){ return null; }			
		}
	}
}
