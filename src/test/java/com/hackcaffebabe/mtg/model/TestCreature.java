package com.hackcaffebabe.mtg.model;

import org.junit.Assert;
import org.junit.Test;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.card.Strength;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


/**
 * Test class to {@link Creature}
 * 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestCreature
{
	@Test
	public void cannnotInstanceWithNullName(){
		try {
			CardColor color = new CardColor();
			Strength strength = new Strength( "1/1" );
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			new Creature( null, color, strength, cost, "s", r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithEmptyName(){
		try {
			CardColor color = new CardColor();
			Strength strength = new Strength( "1/1" );
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			new Creature( "", color, strength, cost, "s", r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithNullColor(){
		try {
			Strength strength = new Strength( "1/1" );
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			new Creature( "n", null, strength, cost, "s", r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithNullStrength(){
		try {
			CardColor color = new CardColor();
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			new Creature( "n", color, null, cost, "s", r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithNullManaCost(){
		try {
			CardColor color = new CardColor();
			Strength strength = new Strength( "1/1" );
			Rarity r = Rarity.COMMON;
			new Creature( "n", color, strength, null, "s", r );
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
			new Creature( "a", new CardColor(), new Strength( "1/1" ), m, "r", Rarity.COMMON );
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithTAPMana(){
		try {
			ManaCost m = new ManaCost();
			m.addTAP();
			new Creature( "a", new CardColor(), new Strength( "1/1" ), m, "r", Rarity.COMMON );
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithNullSubType(){
		try {
			CardColor color = new CardColor();
			Strength strength = new Strength( "1/1" );
			Rarity r = Rarity.COMMON;
			ManaCost cost = new ManaCost();
			new Creature( "n", color, strength, cost, null, r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void canInstanceWithEmptySubType(){
		CardColor color = new CardColor();
		Strength strength = new Strength( "1/1" );
		Rarity r = Rarity.COMMON;
		ManaCost cost = new ManaCost();
		new Creature( "n", color, strength, cost, "", r );
	}

	@Test
	public void cannnotInstanceWithNullRarity(){
		try {
			CardColor color = new CardColor();
			Strength strength = new Strength( "1/1" );
			ManaCost cost = new ManaCost();
			new Creature( "n", color, strength, cost, "s", null );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}
}
