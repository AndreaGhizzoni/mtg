package com.hackcaffebabe.mtg.model.card;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test class for {@link Strength}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestStrength
{
	@Test
	public void canInstanceAProperStrengthWithPowerX(){
		Strength s = new Strength( -1, 1 );
		Assert.assertEquals( -1, s.getPower() );
	}

	@Test
	public void canInstanceAProperStrengthWithToughnessX(){
		Strength s = new Strength( 1, -1 );
		Assert.assertEquals( -1, s.getToughness() );
	}

	@Test
	public void canInstanceAProperStrengthWithPowerOne(){
		Strength s = new Strength( 1, 1 );
		Assert.assertEquals( 1, s.getPower() );
	}

	@Test
	public void canInstanceAProperStrengthWithToughnessOne(){
		Strength s = new Strength( 1, 1 );
		Assert.assertEquals( 1, s.getToughness() );
	}

	@Test
	public void canInstanceAProperStrengthFromString(){
		Strength s = new Strength( "1/1" );
		Assert.assertEquals( "1/1", s.toString() );
	}

	@Test
	public void cannotInstanceStrengthWithPowerInvalid(){
		try {
			new Strength( -2, 1 );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceStrengthWithToughnessInvalid(){
		try {
			new Strength( 1, -2 );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

}
