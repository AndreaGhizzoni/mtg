package com.hackcaffebabe.mtg.gui.panel.insertcard;

import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.model.Planeswalker;

/**
 * Panel to get the MTG Card planes walker info.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class PlaneswalkerInfo extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JSpinner spinLife;
	
	/**
	 * Create the panel.
	 */
	public PlaneswalkerInfo(){
		super();
		setBorder(new TitledBorder(null, "Planeswalker Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[][grow]", "[]"));
		this.initContent();
	}
	
	private void initContent(){
		add( new JLabel("Life:"), "cell 0 0,alignx trailing");

		this.spinLife = new JSpinner( new SpinnerNumberModel( 1, 1, 100, 1 ) );
		JSpinner.DefaultEditor editor = ( JSpinner.DefaultEditor ) this.spinLife.getEditor();
		editor.getTextField().setEnabled( true );
		editor.getTextField().setEditable( false );
		add(this.spinLife, "cell 1 0,growx");
	}
	
//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * Disable all components
	 */
	public void disableAllComponents( ){
		((SpinnerNumberModel)this.spinLife.getModel()).setValue( 1 );
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
	 * Returns the {@link Planeswalker} life.
	 * @return {@link Integer} the planes walker life.
	 */
	public int getPlaneswalkerLife(){
		return (Integer)this.spinLife.getValue();
	}
}
