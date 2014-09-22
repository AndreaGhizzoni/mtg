package main.java.com.hackcaffebabe.mtg.controller.statistics;

/**
 * This class provide all the constants that is used to collect card's informations
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
enum StatConstants
{
	// common statistics
	CARDS("Number of Cards"),
	LEGENDARY("Number of Legendary cards"),
	CREATURE("Number of Creatutrs"),
	INSTANT("Number of Instants"),
	SORCERY("Number of Sorcery"),
	ENCHANTMENT("Number of Enchantments"),
	LAND("Number of Lands"),
	PLANESWALKER("Number of Planeswalker"),
	
	// rarity
	COMMONS("Number of Common cards"),
	NON_COMMON("Number of NON Common cards"),
	RARE("Number of Rare cards"),
	MYTHIC("Number of Mythic cards"),
	
	// colors
	RED("Number of Red cards"),
	BLACK("Number of Black cards"),
	BLUE("Number of Blue cards"),
	WHITE("Number of White cards"),
	GREEN("Number of Green cards"),
	MONO("Number of Mono color cars"),
	HYBRID("Number of Hybrid cards"),
	MULTICOLOR("Number of Multicolor cards");
	

	private String name;

	StatConstants(String i){
		this.name = i;
	}
	
	/** @return {@link String} the fancy name of the constant */
	public String getName(){
		return this.name;
	}
}
