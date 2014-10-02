package com.hackcaffebabe.mtg.model;

import org.junit.Assert;
import org.junit.Test;
import com.hackcaffebabe.mtg.model.card.Rarity;


/**
 * Test class of {@link Land}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestLand
{
	@Test
	public void cannnotInstanceWithNullName(){
		try {
			Rarity r = Rarity.COMMON;
			new Land( null, r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithEmptyName(){
		try {
			Rarity r = Rarity.COMMON;
			new Land( "", r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithNullRarity(){
		try {
			new Land( "n", null );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}
}
