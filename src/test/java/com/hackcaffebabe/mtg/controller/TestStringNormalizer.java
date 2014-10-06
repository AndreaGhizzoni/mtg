package com.hackcaffebabe.mtg.controller;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test class of {@link StringNormalizer}
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class TestStringNormalizer
{
	@Test
	public void canRemoveAccentCharacters(){
		String t = StringNormalizer.removeAccentCharacters( "testàèéùìò" );
		String exp = "testaeeuio";
		Assert.assertEquals( exp, t );
	}

	@Test
	public void canRemoveFileExtension(){
		String t = StringNormalizer.removeExtension( "test.ext" );
		String exp = "test";
		Assert.assertEquals( exp, t );
	}

	@Test
	public void canRemovePathCharacters(){
		String t = StringNormalizer.removePathCharacters( "\\/\t|-\n--asd" );
		String exp = "asd";
		Assert.assertEquals( exp, t );
	}

	@Test
	public void canRemoveAccentedCharactersFromEmptyString(){
		String t = StringNormalizer.removeAccentCharacters( "" );
		Assert.assertTrue( t.isEmpty() );
	}

	@Test
	public void canRemoveAccentedCharactersFromNullString(){
		String t = StringNormalizer.removeAccentCharacters( null );
		Assert.assertTrue( t == null );
	}

	@Test
	public void canRemoveFileExtensionFromEmptyString(){
		String t = StringNormalizer.removeExtension( "" );
		Assert.assertTrue( t.isEmpty() );
	}

	@Test
	public void canRemoveFileExtensionFromNullString(){
		String t = StringNormalizer.removeExtension( null );
		Assert.assertTrue( t == null );
	}

	@Test
	public void canRemovePathCharactersFromEmptyString(){
		String t = StringNormalizer.removePathCharacters( "" );
		Assert.assertTrue( t.isEmpty() );
	}

	@Test
	public void canRemovePathCharactersFromNullString(){
		String t = StringNormalizer.removePathCharacters( null );
		Assert.assertTrue( t == null );
	}
}
