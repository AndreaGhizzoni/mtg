package com.hackcaffebabe.mtg.model.color;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test for class {@link Mana}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestMana
{
	@Test
	public void canGetAbbrevietionForManaBLACK(){
		Mana m = Mana.BLACK;
		Assert.assertEquals( "B", Mana.getAbbraviation( m ) );
	}

	@Test
	public void canGetAbbrevietionForManaRED(){
		Mana m = Mana.RED;
		Assert.assertEquals( "R", Mana.getAbbraviation( m ) );
	}

	@Test
	public void canGetAbbrevietionForManaBLUE(){
		Mana m = Mana.BLUE;
		Assert.assertEquals( "U", Mana.getAbbraviation( m ) );
	}

	@Test
	public void canGetAbbrevietionForManaGREEN(){
		Mana m = Mana.GREEN;
		Assert.assertEquals( "G", Mana.getAbbraviation( m ) );
	}

	@Test
	public void canGetAbbrevietionForManaWHITE(){
		Mana m = Mana.WHITE;
		Assert.assertEquals( "W", Mana.getAbbraviation( m ) );
	}

	@Test
	public void canGetAbbrevietionForManaX(){
		Mana m = Mana.X;
		Assert.assertEquals( "X", Mana.getAbbraviation( m ) );
	}

	@Test
	public void canGetAbbrevietionForManaSTAP(){
		Mana m = Mana.STAP;
		Assert.assertEquals( "STAP", Mana.getAbbraviation( m ) );
	}

	@Test
	public void canGetAbbrevietionForManaTAP(){
		Mana m = Mana.TAP;
		Assert.assertEquals( "TAP", Mana.getAbbraviation( m ) );
	}

}
