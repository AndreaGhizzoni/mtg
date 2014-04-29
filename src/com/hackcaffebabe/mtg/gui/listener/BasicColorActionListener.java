package com.hackcaffebabe.mtg.gui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import com.hackcaffebabe.mtg.gui.GUIUtils;
import com.hackcaffebabe.mtg.model.color.OLD_BasicColors;
import com.hackcaffebabe.mtg.model.color.OLD_CardColor;
import com.hackcaffebabe.mtg.model.color.TypeColor;


/**
 * This action listener is used to get the {@link OLD_CardColor} from the user.<br>
 * Add this listener for each {@link JComboBox} of {@link OLD_BasicColors}.
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class BasicColorActionListener implements ActionListener
{
	List<OLD_BasicColors> colors = new ArrayList<>();
	private int isHybrid = -1;

	@Override
	public void actionPerformed(ActionEvent e){
		String command = e.getActionCommand();
		doEqualsAndCheck( command, OLD_BasicColors.RED );
		doEqualsAndCheck( command, OLD_BasicColors.BLACK );
		doEqualsAndCheck( command, OLD_BasicColors.GREEN );
		doEqualsAndCheck( command, OLD_BasicColors.WHITE );
		doEqualsAndCheck( command, OLD_BasicColors.BLUE );

		if(colors.size() == 0)
			isHybrid = -1;// color less
		if(colors.size() == 1)
			isHybrid = 2;// mono color
		if(colors.size() > 2)
			isHybrid = 0;// multicolor

		if(colors.size() == 2) {// hybrid o multicolor
			int i;
			do {
				i = GUIUtils.showHybridMulticolorDialog();
			} while( i == -1 );
			isHybrid = i;
		}
	}

	/* this method do the equals and check if basic colors is in the list */
	private void doEqualsAndCheck(String command, OLD_BasicColors b){
		if(command.equals( OLD_BasicColors.getAbbraviation( b ) )) {
			if(colors.contains( b ))
				colors.remove( b );
			else colors.add( b );
		}
	}

	/**
	 * This method reset the selection. But not the graphical selection.
	 */
	public void reset(){
		isHybrid = -1;
		colors = new ArrayList<>();
	}

	/**
	 * This method set this action listener on given {@link OLD_CardColor}.
	 * @param c {@link OLD_CardColor} if c==null nothing happened.
	 */
	public void setCardColor(OLD_CardColor c){
		if(c != null) {
			this.colors = new ArrayList<>( c.getBasicColors() );
			if(c.getType() == TypeColor.MULTI_COLOR)
				isHybrid = 0;
			else if(c.getType() == TypeColor.IBRID)
				isHybrid = 1;
			else if(c.getType() == TypeColor.MONO_COLOR)
				isHybrid = 2;
			else isHybrid = -1;
		}
	}

	/**
	 * Returns the selected Card Color selected by the user.
	 * @return {@link OLD_CardColor}
	 */
	public OLD_CardColor getCardColor(){
		OLD_CardColor mtgCardColor = null;
		if(isHybrid == 0) { // multicolor card
			mtgCardColor = new OLD_CardColor( this.colors );
		} else if(isHybrid == 1) { // hybrid card
			mtgCardColor = new OLD_CardColor( this.colors.get( 0 ), this.colors.get( 1 ) );
		} else if(isHybrid == 2) { // mono color card
			mtgCardColor = new OLD_CardColor( this.colors.get( 0 ) );
		} else { // color less card
			mtgCardColor = new OLD_CardColor();
		}
		return mtgCardColor;
	}
}
