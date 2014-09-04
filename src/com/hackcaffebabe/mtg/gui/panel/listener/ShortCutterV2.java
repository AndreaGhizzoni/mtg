package com.hackcaffebabe.mtg.gui.panel.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
import com.hackcaffebabe.mtg.trash.ShortCutter;


/**
 * This class will perform the action CTRL+SPACE to show a pop up menu to insert
 * a word like [x] where x is a color or a number.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class ShortCutterV2 extends AbstractAction
{
	private static final long serialVersionUID = 1L;
	private static ShortCutterV2 instance;
	private final String DEFAULT_PATTERN = "[%s]";

	public static final KeyStroke KEYSTROKE = KeyStroke.getKeyStroke( KeyEvent.VK_SPACE, InputEvent.CTRL_DOWN_MASK );
	public static final String KEY = ShortCutter.class.getName();

	private HashSet<JTextComponent> textToDisplay = new HashSet<>();
	private JTextComponent lastComponentWithFocus;
	private HashMap<String, JMenuItem> whatToPopup = new HashMap<>();

	/**
	 * Return the instance of ShortCutter class.
	 * @return {@link ShortCutterV2}
	 */
	public static ShortCutterV2 getInstance(){
		if(instance == null)
			instance = new ShortCutterV2();
		return instance;
	}

	private ShortCutterV2(){
		putMana();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	public void add(JTextComponent t){
		if(t != null)
			this.textToDisplay.add( t );
	}

	public void remove(String key){
		this.whatToPopup.remove( key );
	}

	public void remove(JTextComponent t){
		this.textToDisplay.remove( t );
	}

	public void put(String key, String value, String pattern){
		if(this.whatToPopup.containsKey( key ))
			remove( key );
		final String s = String.format( pattern, value );
		JMenuItem i = new JMenuItem( s );
		i.addActionListener( new AC( s ) );
		this.whatToPopup.put( key, i );
	}

	public void put(String key, String value){
		put( key, value, DEFAULT_PATTERN );
	}

	public void putMana(){
		String abb = "";
		for(Mana m: Mana.values()) {
			abb = Mana.getAbbraviation( m );
			put( abb, abb );
		}
	}

	public void clearAll(){
		this.textToDisplay.clear();
		this.lastComponentWithFocus = null;
		clearMenu();
	}

	public void clearMenu(){
		this.whatToPopup.clear();
		putMana();
	}

	private JPopupMenu getMenu(){
		JPopupMenu p = new JPopupMenu();
		for(Map.Entry<String, JMenuItem> i: this.whatToPopup.entrySet())
			p.add( i.getValue() );
		return p;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	public Set<JTextComponent> getTextComponents(){
		return this.textToDisplay;
	}

	/** this method returns the last component that has showed the shortcut menu */
	public JTextComponent getLastComponentWithFocus(){
		return this.lastComponentWithFocus;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public void actionPerformed(ActionEvent e){
		int f;
		int y = 0;
		int x = 0;
		for(JTextComponent i: textToDisplay) {
			if(i.hasFocus()) {
				try {
					if(i instanceof JTextArea) {
						JTextArea text = (JTextArea) i;
						f = text.getFont().getSize();
						x = text.modelToView( text.getCaretPosition() ).x;
						y = text.modelToView( text.getCaretPosition() ).y + f;
					} else if(i instanceof JTextField) {
						JTextField text = (JTextField) i;
						f = text.getFont().getSize();
						x = text.modelToView( text.getCaretPosition() ).x;
						y = text.modelToView( text.getCaretPosition() ).y + f;
					} else if(i instanceof JTextPane) {
						JTextPane text = (JTextPane) i;
						f = text.getFont().getSize();
						x = text.modelToView( text.getCaretPosition() ).x;
						y = text.modelToView( text.getCaretPosition() ).y + f;
					}
					this.lastComponentWithFocus = i;
					getMenu().show( i, x, y );
				} catch(BadLocationException e1) {
					e1.printStackTrace(); //hope never catch this TODO add error message
				}
			}
		}
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/** action listener for each pop up menu */
	private class AC implements ActionListener
	{
		String d;

		public AC(String d){
			this.d = d;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			if(lastComponentWithFocus != null) {
				if(lastComponentWithFocus instanceof JTextArea) {
					((JTextArea) lastComponentWithFocus).append( this.d );
				} else if(lastComponentWithFocus instanceof JTextField) {
					JTextField f = (JTextField) lastComponentWithFocus;
					String oldTxt = f.getText();
					f.setText( String.format( "%s%s", oldTxt, this.d ) );
				} else if(lastComponentWithFocus instanceof JTextPane) {
					JTextPane f = (JTextPane) lastComponentWithFocus;
					String oldTxt = f.getText();
					f.setText( String.format( "%s%s", oldTxt, this.d ) );
				}
			}
		}
	}
}
