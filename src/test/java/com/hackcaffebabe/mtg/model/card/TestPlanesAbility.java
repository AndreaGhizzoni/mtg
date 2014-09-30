package com.hackcaffebabe.mtg.model.card;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test class to {@link PlanesAbility}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestPlanesAbility
{
	@Test
	public void canInstanceAPlanesAbilityWithCost(){
		PlanesAbility s = new PlanesAbility( 1, "test" );
		Assert.assertEquals( 1, s.getCost() );
	}

	@Test
	public void canInstanceAPlanesAbilityWithDescription(){
		PlanesAbility s = new PlanesAbility( 1, "test" );
		Assert.assertEquals( "test", s.getDesctiption() );
	}

	@Test
	public void cannotInstanceWithDescriptionNull(){
		try {
			new PlanesAbility( 1, null );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithDescriptionEmpty(){
		try {
			new PlanesAbility( 1, "" );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}
}
