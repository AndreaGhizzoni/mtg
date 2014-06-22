package com.hackcaffebabe.mtg.gui.panel.insertupdatecard.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import com.hackcaffebabe.mtg.model.color.Mana;


/**
 * This class will perform the action CTRL+SPACE to show a pop up menu to insert
 * a word like [x] where x is a color or a number.
 * 
 * TODO maybe change the class name whit one more appropriate.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class KeyboardAutoCompleteShortcuts extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	// [!!!] this keystroke works with CRTL+SPACE and SPACE+CTRL [!!!]
	public static final KeyStroke KEYSTROKE = KeyStroke.getKeyStroke( KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK );
	// for simplicity the key of action is the name of this class
	public static final String KEY = KeyboardAutoCompleteShortcuts.class.getName();

	private JTextArea text;
	private JPopupMenu menu;

	/**
	 * Instance a KeyboardAutocompleteShortcuts object with his {@link JTextArea}.
	 * @param t {@link JTextArea} to launch the menu.
	 */
	public KeyboardAutoCompleteShortcuts(JTextArea t) throws IllegalArgumentException{
		if(t == null)
			throw new IllegalArgumentException( "JTextArea given can not be null." );

		text = t;
		menu = this.getMenu();
	}

	@Override
	public void actionPerformed(ActionEvent e){
		//get the current position in the JTextArea
		int x = 0;
		int y = 0;
		try {
			x = this.text.modelToView( text.getCaretPosition() ).x;
			y = this.text.modelToView( text.getCaretPosition() ).y + 15;
		} catch(BadLocationException e1) {
			//hope never catch this
			e1.printStackTrace();
		}

		//create the JPopupMenu whit a list of shortcut to insert.
		this.menu.show( this.text, x, y );
	}

	/**
	 * Create the instance of menu to display.
	 */
	private JPopupMenu getMenu(){
		JPopupMenu menu = new JPopupMenu();

		String pattern = "[%s]";
		String s = "";
		for(Mana m: Mana.values()) {
			s = String.format( pattern, Mana.getAbbraviation( m ) );
			JMenuItem current = new JMenuItem( s );
			// add action to current
			current.addActionListener( getProperActionListener( s ) );
			menu.add( current );
		}

		return menu;
	}

	/**
	 * Return the action listener related to the string given. 
	 */
	private ActionListener getProperActionListener(final String s){
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				text.append( s );
			}
		};
	}
}
