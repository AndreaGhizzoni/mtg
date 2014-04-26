package com.hackcaffebabe.mtg.model.cost;

import com.hackcaffebabe.mtg.model.color._Mana;


public class TestManaCost
{
	public static void main(String[] args){
		Tuple<_Mana, Integer> blue = new Tuple<_Mana, Integer>( _Mana.BLUE, 2 );
		Tuple<_Mana, Integer> black = new Tuple<_Mana, Integer>( _Mana.BLACK, 2 );
		Tuple<_Mana, Integer> black2 = new Tuple<_Mana, Integer>( _Mana.BLACK, 2 );
		Tuple<_Mana, Integer> tap = new Tuple<_Mana, Integer>( _Mana.TAP, 2 );
		Tuple<_Mana, Integer> x = new Tuple<_Mana, Integer>( _Mana.X, -1 );

		ManaCost c = new ManaCost( x, blue, black, black2, tap );

		System.out.println( c.toString() );
	}
}
