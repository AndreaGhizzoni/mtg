package com.hackcaffebabe.mtg.model.card;

import org.junit.Assert;
import org.junit.Test;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


/**
 * Test class of {@link Effect}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestEffect
{
	@Test
	public void canInstanceAProperEffectWithMana(){
		ManaCost c = new ManaCost();
		c.addTAP();
		Effect f = new Effect( c, "test" );
		Assert.assertEquals( c, f.getManaCost() );
	}

	@Test
	public void canInstanceAProperEffectWithNullMana(){
		String t = "test";
		Effect f = new Effect( null, t );
		Assert.assertEquals( t, f.getText() );
	}

	@Test
	public void cannotInstanceWithNullText(){
		try {
			new Effect( null, null );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithEmptyText(){
		try {
			new Effect( null, "" );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}
}
