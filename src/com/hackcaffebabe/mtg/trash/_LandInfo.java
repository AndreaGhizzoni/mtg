package com.hackcaffebabe.mtg.trash;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

/**
 * Panel to get the MTG Card land info.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class _LandInfo extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JCheckBox chbBasicLand;
	private JCheckBox chbLand;
	
	/**
	 * Create the panel.
	 */
	public _LandInfo(){
		super();
		setBorder( new TitledBorder(null, "Land Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout( new MigLayout("", "[][][pref!]", "[]"));
		this.initContent();
	}
	
	private void initContent(){
		this.chbBasicLand = new JCheckBox("Basic Land");
		add( this.chbBasicLand, "cell 0 0,alignx center");
		
		this.chbLand = new JCheckBox("Land");
		add( this.chbLand, "cell 2 0,alignx center");
		
		ButtonGroup g = new ButtonGroup();
		g.add( this.chbLand );
		g.add( this.chbBasicLand );
	}
	
//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * Disable all components
	 */
	public void disableAllComponents( ){
		this.chbBasicLand.setSelected( false );
		this.chbBasicLand.setEnabled( false );
		this.chbLand.setSelected( false );
		this.chbLand.setEnabled( false );
	}
	
	/**
	 * Enable all components
	 */
	public void enableAllComponents( ){
		this.chbBasicLand.setEnabled( true );
		this.chbLand.setEnabled( true );
	}
	
//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the Type of land selected.
	 * @return {@link _TypeLand} the type land selected,null for error.
	 */
	public _TypeLand getTypeLandSelected(){ 
		if( this.chbBasicLand.isSelected() ||  this.chbLand.isSelected() ){
			return this.chbBasicLand.isSelected() ? _TypeLand.BASIC : _TypeLand.NON_BASIC;
		}else{
			return null;
		}
	}
}
