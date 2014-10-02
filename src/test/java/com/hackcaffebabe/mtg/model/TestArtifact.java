package com.hackcaffebabe.mtg.model;

import org.junit.Assert;
import org.junit.Test;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


/**
 * Test class of {@link Artifact}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestArtifact
{
	@Test
	public void cannnotInstanceWithNullName(){
		try {
			CardColor color = new CardColor();
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			new Artifact( null, cost, color, r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithEmptyName(){
		try {
			CardColor color = new CardColor();
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			new Artifact( "", cost, color, r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithNullColor(){
		try {
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			new Artifact( "n", cost, null, r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithNullManaCost(){
		try {
			CardColor color = new CardColor();
			Rarity r = Rarity.COMMON;
			new Artifact( "n", null, color, r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithSTAPMana(){
		try {
			ManaCost m = new ManaCost();
			m.addSTAP();
			new Artifact( "a", m, new CardColor(), Rarity.COMMON );
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithTAPMana(){
		try {
			ManaCost m = new ManaCost();
			m.addTAP();
			new Artifact( "a", m, new CardColor(), Rarity.COMMON );
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithNullRarity(){
		try {
			CardColor color = new CardColor();
			ManaCost cost = new ManaCost();
			new Artifact( "n", cost, color, null );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}
}
