package com.hackcaffebabe.mtg.model;
import java.io.Serializable;
import java.util.AbstractMap;
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.card.ManaCost;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.BasicColors;
import com.hackcaffebabe.mtg.model.color.CardColor;

/**
 * Represents the Land card in MTG game.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.3
 */
public class Land extends MTGCard implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instance a MTG Land card with all his fields.
	 * @param name {@link String} the name of Land.
	 * @param rarity {@link Rarity} the rarity of Land.
	 * @throws IllegalArgumentException if some argument are null or empty string.
	 */
	public Land( String name, Rarity rarity ) throws IllegalArgumentException {
		super( name, new CardColor(), rarity );
	}

//===========================================================================================
// SETTER
//===========================================================================================
	@Override
	public void setArtifact( boolean isArtifact ){}
	
//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * This method returns the basic land associated with passed basic color.
	 * @param b {@link BasicColors} the color of basic land.
	 * @return {@link Land} the basic land.
	 * @throws IllegalArgumentException if argument given is null or is COLOR_LESS.
	 */
	public static Land getBasicLand( BasicColors b ) throws IllegalArgumentException {
		if( b == null )
			throw new IllegalArgumentException( "Color of Basic Land Can not be null." );
		if( b == BasicColors.COLOR_LESS )
			throw new IllegalArgumentException( "COLOR_LESS can not be a basic Land" );
	
		Land l = null;
		ManaCost tap = new ManaCost( new AbstractMap.SimpleEntry<BasicColors, Integer>( null, -1 ) );
		switch( b ){
			case BLACK:{
				l = new Land( "Swamp", Rarity.COMMON );
				l.addEffect( new Effect( tap, "Add black mana to your mana pool." ) );
				break;
			}
			case BLUE:{
				l = new Land( "Island", Rarity.COMMON );
				l.addEffect( new Effect( tap, "Add blu mana to your mana pool." ) );
				break;
			}
			case GREEN:{
				l = new Land( "Forest", Rarity.COMMON );
				l.addEffect( new Effect( tap, "Add green mana to your mana pool." ) );
				break;
			}
			case RED:{
				l = new Land( "Mountain", Rarity.COMMON );
				l.addEffect( new Effect( tap, "Add red mana to your mana pool." ) );
				break;
			}
			case WHITE:{
				l = new Land( "Plains", Rarity.COMMON );
				l.addEffect( new Effect( tap, "Add white mana to your mana pool." ) );
				break;
			}
			default: break;		
		}
		return l;
	}
	
	@Override
	public boolean isArtifact(){ return false; }
//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	public Object[] getDisplayRow(){
		String color = String.format( "%s %s", getCardColor(), getCardColor().getType() );
		String type ="Land";
		if(isLegendary()) type+=" Leg.";
		return new Object[]{getName(), color, type, getSubType()==null?"":getSubType(), getRarity().toString()};
	}
	
	@Override
	public String toString(){
		String pattern = "%s [%s %s %s - %s %s]";
		String type ="Land";
		if(isLegendary()) type+=" Legendary";
		if(getSubType()!=null && !getSubType().isEmpty()) type+=" - "+getSubType();
		return String.format( pattern, getName(), getCardColor(), getCardColor().getType(), 
							  type, getRarity(), getSeries() );
	}
}
