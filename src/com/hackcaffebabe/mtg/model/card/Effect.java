package com.hackcaffebabe.mtg.model.card;

import it.hackcaffebabe.jx.table.model.DisplayableObject;
import java.io.Serializable;


/**
 * Represents a single effect of MTG card.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class Effect extends DisplayableObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ManaCost cost;
	private String text;

	/**
	 * Instance a card Effect. If Effect has no Mana cost, set it to null.
	 * @param cost {@link ManaCost} the cost of effect.
	 * @param text {@link String} the text of effect.
	 * @throws IllegalArgumentException if text given is null or empty string.
	 */
	public Effect(ManaCost cost, String text) throws IllegalArgumentException{
		super( Effect.class );
		setColumnNames( new String[] { "Cost", "Description" } );
		this.setManaCost( cost );
		this.setText( text );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	/**
	 * Set the mana cost of ability.
	 */
	private void setManaCost(ManaCost c) /*throws IllegalArgumentException*/{
//		if( c == null )
//			throw new IllegalArgumentException( "Mana cost of Effect can not be null" );

		this.cost = c;
	}

	/**
	 * Set the text of Effect.
	 * @param text {@link String} the Effect text.
	 * @throws IllegalArgumentException if argument given is null.
	 */
	private void setText(String text) throws IllegalArgumentException{
		if(text == null || text.isEmpty())
			throw new IllegalArgumentException( "Text of Effect can not be null." );

		this.text = text;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Returns the Mana cost of effect.
	 * @return {@link ManaCost} the Mana cost of effect.
	 */
	public final ManaCost getManaCost(){
		return this.cost;
	}

	/**
	 * Returns the text of effect.
	 * @return {@link String} the text of effect.
	 */
	public final String getText(){
		return this.text;
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public String toString(){
		return String.format( "%s: %s", this.cost.toString(), this.text );
	}

	@Override
	public Object[] getDisplayRow(){
		return new Object[] { this.cost.toString(), this.text };
	}
}
