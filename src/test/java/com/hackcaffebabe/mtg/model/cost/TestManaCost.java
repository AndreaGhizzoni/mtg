package com.hackcaffebabe.mtg.model.cost;

import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import com.hackcaffebabe.mtg.model.color.Mana;


/**
 * Test class to {@link ManaCost}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestManaCost
{
	@Test
	public void cannotInstanceWithNullSet(){
		try {
			Set<Tuple<Mana, Integer>> s = null;
			new ManaCost( s );
			Assert.fail();
		} catch(Exception e) {
			return;
		}
	}

	@Test
	public void cannotInstanceWithNullAsMana(){
		try {
			new ManaCost( null, null, null );
			Assert.fail();
		} catch(Exception e) {
			return;
		}
	}

	@Test
	public void canRetriveCorrectConvertedManaCost(){
		ManaCost m = new ManaCost();
		m.addCost( Mana.BLACK, 1 );
		Assert.assertEquals( 1, m.getConvertedManaCost() );
	}

	@Test
	public void canRetriveColors(){
		ManaCost m = new ManaCost();
		m.addCost( Mana.BLACK, 1 );
		m.addCost( Mana.BLUE, 1 );
		m.addCost( Mana.WHITE, 1 );
		Assert.assertEquals( 3, m.getCost().size() );
	}

	@Test
	public void canAddTAPAsMana(){
		ManaCost m = new ManaCost();
		m.addTAP();
		Assert.assertTrue( m.containsTAP() );
	}

	@Test
	public void canAddSTAPAsMana(){
		ManaCost m = new ManaCost();
		m.addSTAP();
		Assert.assertTrue( m.containsSTAP() );
	}

	@Test
	public void canAddXAsMana(){
		ManaCost m = new ManaCost();
		m.addCost( Mana.X, 1 );
		Assert.assertTrue( m.containsX() );
	}

	@Test
	public void cannotAddMoreThenOneManaColor(){
		ManaCost m = new ManaCost();
		m.addCost( Mana.BLACK, 1 );
		m.addCost( Mana.BLACK, 1 );
		Assert.assertEquals( 1, m.getCost().size() );
	}

	@Test
	public void cannotAddNullManaWithFrequencyOne(){
		try {
			ManaCost m = new ManaCost();
			m.addCost( null, 1 );
			Assert.fail();
		} catch(Exception e) {
			return;
		}
	}

	@Test
	public void cannotAddManaWithHisFrequencyNull(){
		try {
			ManaCost m = new ManaCost();
			m.addCost( Mana.BLACK, null );
			Assert.fail();
		} catch(Exception e) {
			return;
		}
	}
}
