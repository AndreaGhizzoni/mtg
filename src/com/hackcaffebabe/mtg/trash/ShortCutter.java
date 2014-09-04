package com.hackcaffebabe.mtg.trash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import com.hackcaffebabe.mtg.model.color.Mana;


/**
 * This class will perform the action CTRL+SPACE to show a pop up menu to insert
 * a word like [x] where x is a color or a number.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ShortCutter extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	// [!!!] this keystroke works with CRTL+SPACE and SPACE+CTRL [!!!]
	public static final KeyStroke KEYSTROKE = KeyStroke.getKeyStroke( KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK );
	// for simplicity the key of action is the name of this class
	public static final String KEY = ShortCutter.class.getName();

	private JTextComponent text;
	private JPopupMenu menu;

	/**
	 * Instance a KeyboardAutocompleteShortcuts object with his {@link JTextComponent}.
	 * @param t {@link JTextComponent} to launch the menu.
	 */
	public ShortCutter(JTextComponent t) throws IllegalArgumentException{
		if(t == null)
			throw new IllegalArgumentException( "JTextComponent given can not be null." );

		text = t;
		menu = this.getMenu();
	}

	@Override
	public void actionPerformed(ActionEvent e){
		try {
			int f = this.text.getFont().getSize();
			int x = this.text.modelToView( text.getCaretPosition() ).x;
			int y = this.text.modelToView( text.getCaretPosition() ).y + f;

			//create the JPopupMenu whit a list of shortcut to insert.
			this.menu.show( this.text, x, y );

		} catch(BadLocationException e1) {
			//hope never catch this
			e1.printStackTrace();
		}
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
				if(text instanceof JTextArea) {
					((JTextArea) text).append( s );
				} else if(text instanceof JTextField) {
					JTextField f = (JTextField) text;
					String t = f.getText();
					f.setText( String.format( "%s%s", t, s ) );
				} else if(text instanceof JTextPane) {
					JTextPane f = (JTextPane) text;
					String t = f.getText();
					f.setText( String.format( "%s%s", t, s ) );
				}
			}
		};
	}
}
