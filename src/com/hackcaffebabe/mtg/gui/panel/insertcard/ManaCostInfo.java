package com.hackcaffebabe.mtg.gui.panel.insertcard;

import static com.hackcaffebabe.mtg.gui.GUIUtils.*;
import com.hackcaffebabe.mtg.model.Artifact;
import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.Enchantment;
import com.hackcaffebabe.mtg.model.Instant;
import com.hackcaffebabe.mtg.model.Land;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.Planeswalker;
import com.hackcaffebabe.mtg.model.Sorcery;
import com.hackcaffebabe.mtg.model.card.ManaCost;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;


/**
 * Panel to get the mana cost info. 
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ManaCostInfo extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JTextField txtManaCost;
	private JButton btnSetManaCost;

	private ManaCost viewManaCost = null;

	/**
	 * Create the Panel to get {@link ManaCost} from user.
	 */
	public ManaCostInfo(){
		super();
		setBorder( new TitledBorder( "Mana Cost Info" ) );
		setLayout( new MigLayout( "", "[grow][]", "[]" ) );
		this.initContent();
	}

	/* initialize all components */
	private void initContent(){
		this.txtManaCost = new JTextField();
		this.txtManaCost.setEditable( false );
		add( txtManaCost, "cell 0 0,growx" );

		this.btnSetManaCost = new JButton( "Set Mana Cost" );
		this.btnSetManaCost.addActionListener( new ManaActionListener() );
		add( this.btnSetManaCost, "cell 1 0" );
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * TODO add doc
	 * @param c
	 */
	public void setData(MTGCard c){
		if(c!=null&&!(c instanceof Land)){
			if(c instanceof Creature)
				this.viewManaCost = ((Creature)c).getManaCost();
			else if(c instanceof Artifact)
				this.viewManaCost = ((Artifact)c).getManaCost();
			else if(c instanceof Instant)
				this.viewManaCost = ((Instant)c).getManaCost();
			else if(c instanceof Sorcery)
				this.viewManaCost = ((Sorcery)c).getManaCost();
			else if(c instanceof Enchantment)
				this.viewManaCost = ((Enchantment)c).getManaCost();
			else if(c instanceof Planeswalker)
				this.viewManaCost = ((Planeswalker)c).getManaCost();
			this.txtManaCost.setText( this.viewManaCost.toString() );
		}
	}
	
	/**
	 * Disable all components
	 */
	public void disableAllComponents(){
		this.txtManaCost.setText( "" );
		this.viewManaCost = null;
		this.btnSetManaCost.setEnabled( false );
	}

	/**
	 * Enable all components
	 */
	public void enableAllComponents(){
		this.btnSetManaCost.setEnabled( true );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the mana cost get from the user.
	 * @return {@link com.hackcaffebabe.mtg.model.card.ManaCost}
	 */
	public ManaCost getManaCost(){
		return this.viewManaCost;
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/** Inner class that describe the action on btnManaCost */
	private class ManaActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			viewManaCost = showManaCost( null, true );
			txtManaCost.setText( viewManaCost.toString() );
		}
	}
}
