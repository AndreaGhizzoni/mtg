package com.hackcaffebabe.mtg.model;

import org.junit.Assert;
import org.junit.Test;
import com.hackcaffebabe.mtg.model.card.PlanesAbility;
import com.hackcaffebabe.mtg.model.card.Rarity;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


/**
 * Test class of {@link Planeswalker}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestPlaneswalker
{
	@Test
	public void cannnotInstanceWithNullName(){
		try {
			CardColor color = new CardColor();
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			new Planeswalker( null, cost, 1, color, r );
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
			new Planeswalker( "", cost, 1, color, r );
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
			new Planeswalker( "n", null, 1, color, r );
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
			new Planeswalker( "a", m, 1, new CardColor(), Rarity.COMMON );
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithTAPMana(){
		try {
			ManaCost m = new ManaCost();
			m.addTAP();
			new Planeswalker( "a", m, 1, new CardColor(), Rarity.COMMON );
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithNullRarity(){
		try {
			CardColor color = new CardColor();
			ManaCost cost = new ManaCost();
			new Planeswalker( "n", cost, 1, color, null );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannnotInstanceWithLessThenZeroLife(){
		try {
			CardColor color = new CardColor();
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			new Planeswalker( "n", cost, -1, color, r );
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
			new Planeswalker( "n", cost, 1, null, r );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}

	@Test
	public void cannotAddNullPlanesAbility(){
		try {
			CardColor color = new CardColor();
			ManaCost cost = new ManaCost();
			Rarity r = Rarity.COMMON;
			Planeswalker p = new Planeswalker( "n", cost, 1, color, r );
			PlanesAbility pl = null;
			p.addAbility( pl );
			Assert.fail();
		} catch(IllegalArgumentException e) {
			return;
		}
	}
}
