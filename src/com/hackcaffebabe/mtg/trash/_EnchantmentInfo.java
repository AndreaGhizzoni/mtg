package com.hackcaffebabe.mtg.trash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

/**
 * Panel to get the MTG Card enchantment info.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class _EnchantmentInfo extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JCheckBox chbBasicEnchantment;
	private JCheckBox chbAura;
	private JCheckBox chbAuraCurse;
	private JCheckBox chbAuraTribal;
	
	private TypeEnchantmentActionListener actionListener = new TypeEnchantmentActionListener();
	
	/**
	 * Create the panel.
	 */
	public _EnchantmentInfo(){
		super();
		setBorder(new TitledBorder(null, "Enchantment Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(new MigLayout("", "[grow][grow][grow][grow]", "[][]"));
		this.initContent();
	}
	
	private void initContent(){
		this.chbBasicEnchantment = new JCheckBox("Basic");
		this.chbBasicEnchantment.setActionCommand( "basic" );
		this.chbBasicEnchantment.addActionListener( actionListener );
		add( this.chbBasicEnchantment, "cell 0 0,alignx left");
		
		this.chbAura = new JCheckBox("Aura");
		this.chbAura.setActionCommand( "aura" );
		this.chbAura.addActionListener( actionListener );
		add( this.chbAura, "cell 1 0,alignx left");
		
		this.chbAuraTribal = new JCheckBox("Aura Tribal");
		this.chbAuraTribal.setActionCommand( "tribal" );
		this.chbAuraTribal.addActionListener( actionListener );
		add( this.chbAuraTribal, "cell 2 0,alignx left");
		
		this.chbAuraCurse = new JCheckBox("Aura Curse");
		this.chbAuraCurse.setActionCommand( "curse" );
		this.chbAuraCurse.addActionListener( actionListener );
		add( this.chbAuraCurse, "cell 3 0,alignx left");
		
		ButtonGroup g = new ButtonGroup();
		g.add( this.chbAura );
		g.add( this.chbAuraCurse );
		g.add( this.chbAuraTribal );
		g.add( this.chbBasicEnchantment );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * Disable all components
	 */
	public void disableAllComponents( ){
		this.chbAura.setSelected( false );
		this.chbAuraCurse.setSelected( false );
		this.chbAuraTribal.setSelected( false );
		this.chbBasicEnchantment.setSelected( false );
		
		this.chbAura.setEnabled( false );
		this.chbAuraCurse.setEnabled( false );
		this.chbAuraTribal.setEnabled( false );
		this.chbBasicEnchantment.setEnabled( false );
	}
	
	/**
	 * Enable all components
	 */
	public void enableAllComponents( ){
		this.chbAura.setEnabled( true );
		this.chbAuraCurse.setEnabled( true );
		this.chbAuraTribal.setEnabled( true );
		this.chbBasicEnchantment.setEnabled( true );
	}
	
//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the type Enchantment selected.
	 * @return {@link _TypeEnchantment} the type Enchantment selected, null for error.
	 */
	public _TypeEnchantment getTypeEnchantmentSelected(){
		return this.actionListener.getType();
	}
	
//===========================================================================================
// INNER CLASS
//===========================================================================================
	/**
    * inner class that describe the action on change enchantment type;
    */
	private class TypeEnchantmentActionListener implements ActionListener{
		private String lastActionCommand = "";
		private _TypeEnchantment type = null;
		
		@Override
		public void actionPerformed( ActionEvent e ){
			String ac = ((JCheckBox)e.getSource()).getActionCommand();
			if( !ac.equals( lastActionCommand ) ){
				switch( ac ){
					case "basic": {
						type = _TypeEnchantment.BASIC;
						break;
					}
					case "aura": {
						type = _TypeEnchantment.AURA;
						break;
					}
					case "curse": {
						type = _TypeEnchantment.AURA_CURSE;
						break;
					}
					case "tribal": {
						type = _TypeEnchantment.AURA_TRIBAL;
						break;
					}
					default: type = null;
				}
			}
		}
		
		/**
		 * Return the selected Type of Enchantment
		 * @return {@link _TypeEnchantment}
		 */
		public _TypeEnchantment getType(){ return this.type; }
	}
}
