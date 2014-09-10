package com.hackcaffebabe.mtg.gui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;


/**
 * This class holds all the frames dimensions ONLY!
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class FramesDimensions
{
	/** Dimension of main frame */
	public static final Dimension DIMENSION_MAIN_FRAME = new Dimension( 1150, 655 );
	/** Dimension of insert card frame */
	public static final Dimension DIMENSION_INSERT_CARD = new Dimension( 660, 700 );
	/** Dimension of advance search frame */
	public static final Dimension DIMENSION_ADVANCE_SEARCH = new Dimension( 655, 150 );
	/** Dimension of Deck editor */
	public static final Dimension DIMENSION_DECK_EDITOR = new Dimension( 700, 720 );
	/** Dimension of importer/exporter */
	public static final Dimension DIMENSION_IMPOEXPO = new Dimension( 400, 250 );

	/**
	 * This method returns a point witch is the starting point to paint a frame in the center of the screen<br> 
	 * according to the dimension given.
	 * @param d {@link Dimension} the frame dimension.
	 * @return {@link Point} the starting point to paint a frame in the center of the screen.
	 */
	public static Point getCenter(Dimension d){
		int sw = Toolkit.getDefaultToolkit().getScreenSize().width;
		int sh = Toolkit.getDefaultToolkit().getScreenSize().height;
		return new Point( (sw / 2) - (d.width / 2), (sh / 2) - (d.height / 2) );
	}
}
