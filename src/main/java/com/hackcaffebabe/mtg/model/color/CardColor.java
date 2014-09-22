package main.java.com.hackcaffebabe.mtg.model.color;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Represents the color of MTG card.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 2.0
 */
public class CardColor implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Set<Mana> colors = new HashSet<>();
	private TypeColor type = null;

	/**
	 * Instance an Color Less card.
	 */
	public CardColor(){
		this.type = TypeColor.COLOR_LESS;
	}

	/**
	 * Instance a Mono color card.
	 * @param c {@link Mana} the mana color.
	 * @throws if c equals Mana.TAP or Mana.X of Mana.COLOR_LESS
	 */
	public CardColor(Mana c) throws IllegalArgumentException{
		this.checkMana( c );
		this.colors.add( c );
		this.type = TypeColor.MONO_COLOR;
	}

	/**
	 * Instance an Hybrid color card.
	 * @param first {@link Mana} the first color of hybrid card.
	 * @param second {@link Mana} the second color of hybrid card.
	 * @throws if c equals Mana.TAP or Mana.X of Mana.COLOR_LESS or the arguments are the same.
	 */
	public CardColor(Mana first, Mana second) throws IllegalArgumentException{
		this.checkMana( first );
		this.checkMana( second );

		if(first.equals( second ))
			throw new IllegalArgumentException( "The two Mana given can not be the same." );

		this.colors.add( first );
		this.colors.add( second );
		this.type = TypeColor.HYBRID;
	}

	/**
	 * Instance an Multicolor card.
	 * @param c {@link List} of Mana.
	 * @throws if c equals Mana.TAP or Mana.X of Mana.COLOR_LESS or list is null or size == 0.
	 */
	public CardColor(List<Mana> c) throws IllegalArgumentException{
		if(c == null || c.size() == 0)
			throw new IllegalArgumentException( "Mana given can not be null." );

		for(Mana m: c) {
			this.checkMana( m );
		}

		this.colors.addAll( c );
		this.type = TypeColor.MULTI_COLOR;
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/**
	 * Check if mana given is not equal of TAP, X or COLOR_LESS
	 */
	private void checkMana(Mana m) throws IllegalArgumentException{
		if(m == null)
			throw new IllegalArgumentException( "Mana given can not be null." );
		else {
			String msg = null;
			if(m.equals( Mana.TAP ))
				msg = "Mana malformed for color card: TAP action";
			else if(m.equals( Mana.X ))
				msg = "Mana malformed for color card: X action";
			else if(m.equals( Mana.COLOR_LESS ))
				msg = "Mana given can not be COLOR_LESS for CardColor whit this constructor.";

			if(msg != null)
				throw new IllegalArgumentException( msg );
		}
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/**
	 * Return the color type of a card.
	 * @return {@link TypeColor} the color type of a card.
	 */
	public TypeColor getType(){
		return this.type;
	}

	/**
	 * Returns the Colors of a card.
	 * @return {@link Set} of {@link Mana}
	 */
	public final Set<Mana> getColors(){
		return this.colors;
	}

//===========================================================================================
// OVVERIDE
//===========================================================================================
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for(Mana c: this.colors) {
			String tmp = String.format( "%s/", Mana.getAbbraviation( c ) );
			builder.append( tmp );
		}
		return builder.toString().isEmpty() ? "CL" : builder.deleteCharAt( builder.toString().length() - 1 ).toString();
	}

	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colors == null) ? 0 : colors.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;

		CardColor other = (CardColor) obj;
		if(colors == null) {
			if(other.colors != null)
				return false;
		} else if(!colors.equals( other.colors ))
			return false;
		else if(type != other.type)
			return false;
		return true;
	}
}
