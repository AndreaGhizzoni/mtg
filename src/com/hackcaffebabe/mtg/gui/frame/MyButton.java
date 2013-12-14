package com.hackcaffebabe.mtg.gui.frame;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


/**
 * TODO add doc and add constructor with icon.
 * TODO when finished add to JXComponents
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
class MyButton extends JButton
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new tool-bar button.
	 */
	public MyButton(){
		initialize();
	}

	/**
	 * Creates a new tool-bar button.
	 * @param txt {@link String} The button text.
	 */
	public MyButton(String txt){
		super( txt );
		initialize();
	}

	/* Initializes the button. */
	private void initialize(){
		setOpaque( false );
		setBackground( new java.awt.Color( 0, 0, 0, 0 ) );
		setBorderPainted( false );
		setMargin( new Insets( 1, 1, 1, 1 ) );
		addMouseListener( new MouseAdapter(){
			public void mouseEntered(MouseEvent ev){
				setBorderPainted( true );
			}

			public void mouseExited(MouseEvent ev){
				setBorderPainted( false );
			}
		} );
	}
}
