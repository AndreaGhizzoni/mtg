package com.hackcaffebabe.mtg.model.card;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test class of {@link Ability}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestAbility
{
	@Test
	public void canIntanceWithName(){
		String n = "name";
		Ability a = new Ability( n, "test" );
		Assert.assertEquals( n, a.getName() );
	}

	@Test
	public void canInstanceWithDescription(){
		String d = "description";
		Ability a = new Ability( "test", d );
		Assert.assertEquals( d, a.getDescription() );
	}

	@Test
	public void cannotInstanceWithNullName(){
		try {
			new Ability( null, "desc" );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithNullDescription(){
		try {
			new Ability( "name", null );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithEmptyName(){
		try {
			new Ability( "", "desc" );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithEmptyDescription(){
		try {
			new Ability( "asd", "" );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}
}
