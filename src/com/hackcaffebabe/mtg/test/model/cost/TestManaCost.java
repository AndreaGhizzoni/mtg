package com.hackcaffebabe.mtg.test.model.cost;

import com.hackcaffebabe.mtg.model.color.Mana;
import com.hackcaffebabe.mtg.model.cost.ManaCost;
import com.hackcaffebabe.mtg.model.cost.Tuple;


public class TestManaCost
{
	public static void main(String... args){
		Tuple<Mana, Integer> tap = new Tuple<>( Mana.TAP, -1 );
		Tuple<Mana, Integer> cl = new Tuple<>( Mana.COLOR_LESS, 2 );
		Tuple<Mana, Integer> x = new Tuple<>( Mana.X, 2 );
		Tuple<Mana, Integer> blue = new Tuple<>( Mana.BLUE, 2 );
		Tuple<Mana, Integer> blue2 = new Tuple<>( Mana.BLUE, 2 );

		ManaCost c = new ManaCost( tap, cl, x, x, blue, blue, blue2 );
		System.out.println( c.toString() );
	}
}
