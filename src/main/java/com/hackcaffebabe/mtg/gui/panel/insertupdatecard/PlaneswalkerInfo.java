package com.hackcaffebabe.mtg.gui.panel.insertupdatecard;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.model.MTGCard;
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
	public PlaneswalkerInfo(int start){
		super();
		setBorder( new TitledBorder( "Planeswalker Info" ) );
		setLayout( new MigLayout( "", "[][grow]", "[]" ) );
		setOpaque( true );
		this.initContent( start );
	}

	/* initialize all components */
	private void initContent(int s){
		add( new JLabel( "Life:" ), "cell 0 0,alignx trailing" );

		this.spinLife = new JSpinner( new SpinnerNumberModel( s, s, 100, 1 ) );
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) this.spinLife.getEditor();
		editor.getTextField().setEnabled( true );
		editor.getTextField().setEditable( false );
		add( this.spinLife, "cell 1 0,growx" );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * This method display the planes walker life from {@link MTGCard} given.
	 * @param c {@link MTGCard} if null or not instance of Planeswalker.class nothing happen;
	 */
	public void setData(MTGCard c){
		if(c != null && c instanceof Planeswalker) {
			this.spinLife.setValue( ((Planeswalker) c).getLife() );
		}
	}

	/**
	 * Disable all components
	 */
	public void disableAllComponents(){
		((SpinnerNumberModel) this.spinLife.getModel()).setValue( 1 );
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
	 * Returns the {@link Planeswalker} life.
	 * @return {@link Integer} the planes walker life.
	 */
	public int getPlaneswalkerLife(){
		return (Integer) this.spinLife.getValue();
	}
}
