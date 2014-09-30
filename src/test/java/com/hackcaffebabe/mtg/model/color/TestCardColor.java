package com.hackcaffebabe.mtg.model.color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test for class {@link CardColor}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestCardColor
{
	@Test
	public void canInstanceColorLess(){
		CardColor c = new CardColor();
		Assert.assertEquals( TypeColor.COLOR_LESS, c.getType() );
	}

	@Test
	public void canInstanceMonoColor(){
		CardColor c = new CardColor( Mana.BLACK );
		Assert.assertEquals( TypeColor.MONO_COLOR, c.getType() );
	}

	@Test
	public void canInstanceHybrid(){
		CardColor c = new CardColor( Mana.BLACK, Mana.BLUE );
		Assert.assertEquals( TypeColor.HYBRID, c.getType() );
	}

	@Test
	public void canInstanceMultiColor(){
		List<Mana> l = Arrays.asList( Mana.BLACK, Mana.BLUE, Mana.GREEN );
		CardColor c = new CardColor( l );
		Assert.assertEquals( TypeColor.MULTI_COLOR, c.getType() );
	}

	@Test
	public void canInstanceMulticolorWithSameColorInList(){
		List<Mana> l = Arrays.asList( Mana.BLUE, Mana.BLUE );
		CardColor c = new CardColor( l );
		Assert.assertEquals( TypeColor.MONO_COLOR, c.getType() );
	}

	@Test
	public void cannotInstanceWithManaColorLess(){
		try {
			new CardColor( Mana.COLOR_LESS );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithManaX(){
		try {
			new CardColor( Mana.X );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithManaSTAP(){
		try {
			new CardColor( Mana.STAP );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithManaTAP(){
		try {
			new CardColor( Mana.TAP );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithTheSameMana(){
		try {
			new CardColor( Mana.RED, Mana.RED );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithAListOfNullMana(){
		try {
			Mana i = null;
			List<Mana> e = Arrays.asList( i );
			new CardColor( e );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithAEmptyList(){
		try {
			List<Mana> e = new ArrayList<>();
			new CardColor( e );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

}
