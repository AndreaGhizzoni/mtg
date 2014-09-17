package com.hackcaffebabe.mtg.controller.json.adapter;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hackcaffebabe.mtg.controller.json.JSONTags;
import com.hackcaffebabe.mtg.model.Artifact;
import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.Enchantment;
import com.hackcaffebabe.mtg.model.Instant;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.Planeswalker;
import com.hackcaffebabe.mtg.model.Sorcery;
import com.hackcaffebabe.mtg.model.card.PlanesAbility;
import com.hackcaffebabe.mtg.model.card.Strength;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.model.cost.ManaCost;


/**
 * This is the JSON adapter for class MTGCard
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class MTGCardAdapter implements JsonSerializer<MTGCard>, JsonDeserializer<MTGCard>
{
	@Override
	public JsonElement serialize(MTGCard c, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		result.add( JSONTags.TYPE, new JsonPrimitive( c.getClass().getSimpleName() ) );
		result.add( JSONTags.NAME, new JsonPrimitive( c.getName() ) );
		result.add( JSONTags.COLOR, arg2.serialize( c.getCardColor(), CardColor.class ) );
		result.add( JSONTags.RARITY, arg2.serialize( c.getRarity() ) );
		result.add( JSONTags.SERIES, new JsonPrimitive( c.getSeries() ) );
		result.add( JSONTags.SUB_TYPE, new JsonPrimitive( c.getSubType() ) );
		result.add( JSONTags.ARTIFACT, new JsonPrimitive( c.isArtifact() ) );
		result.add( JSONTags.LEGENDARY, new JsonPrimitive( c.isLegendary() ) );
		result.add( JSONTags.PRIMARY_EFFECT,
				new JsonPrimitive( c.getPrimaryEffect() == null ? "" : c.getPrimaryEffect() ) );
		result.add( JSONTags.EFFECTS, arg2.serialize( c.getEffects() ) );
		result.add( JSONTags.ABILITIES, arg2.serialize( c.getAbilities() ) );

		switch( c.getClass().getSimpleName() ) {
			case "Creature": {
				Creature x = ((Creature) c);
				result.add( JSONTags.MANA_COST, arg2.serialize( x.getManaCost(), ManaCost.class ) );
				result.add( JSONTags.STRENGTH, arg2.serialize( x.getStrength(), Strength.class ) );
				break;
			}
			case "Artifact": {
				Artifact x = ((Artifact) c);
				result.add( JSONTags.MANA_COST, arg2.serialize( x.getManaCost(), ManaCost.class ) );
				break;
			}
			case "Enchantment": {
				Enchantment x = ((Enchantment) c);
				result.add( JSONTags.MANA_COST, arg2.serialize( x.getManaCost(), ManaCost.class ) );
				break;
			}
			case "Instant": {
				Instant x = ((Instant) c);
				result.add( JSONTags.MANA_COST, arg2.serialize( x.getManaCost(), ManaCost.class ) );
				break;
			}
			case "Planeswalker": {
				Planeswalker x = ((Planeswalker) c);
				result.add( JSONTags.MANA_COST, arg2.serialize( x.getManaCost(), ManaCost.class ) );
				result.add( JSONTags.LIFE, new JsonPrimitive( x.getLife() ) );
				result.add( JSONTags.PLANES_ABILITY, arg2.serialize( x.getPlanesAbilities() ) );
				break;
			}
			case "Sorcery": {
				Sorcery x = ((Sorcery) c);
				result.add( JSONTags.MANA_COST, arg2.serialize( x.getManaCost(), ManaCost.class ) );
				break;
			}
		}

		return result;
	}

	@Override
	public MTGCard deserialize(JsonElement json, Type arg1, JsonDeserializationContext context)
			throws JsonParseException{
		String type = json.getAsJsonObject().get( JSONTags.TYPE ).getAsString();
		json.getAsJsonObject().remove( JSONTags.TYPE );// remove this otherwise class dosn't full fit the class.
		try {
			MTGCard c = context.deserialize( json, Class.forName( "com.hackcaffebabe.mtg.model." + type ) );
			CardColor cc = context.deserialize( json.getAsJsonObject().get( JSONTags.COLOR ),
					Class.forName( "com.hackcaffebabe.mtg.model.color.CardColor" ) );
			c.setCardColor( cc );
			c.setColumnNames( new String[] { "Name", "Card Color", "Type", "Rarity" } );
			c.setLegendary( json.getAsJsonObject().get( JSONTags.LEGENDARY ).getAsBoolean() );
			c.setArtifact( json.getAsJsonObject().get( JSONTags.ARTIFACT ).getAsBoolean() );
			String subType = json.getAsJsonObject().get( JSONTags.SUB_TYPE ).getAsString();
			if(subType != null)
				c.setSubType( subType );
			String pe = json.getAsJsonObject().get( JSONTags.PRIMARY_EFFECT ).getAsString();
			if(c != null)
				c.setPrimaryEffect( pe );
			if(!type.equals( "Land" )) {
				ManaCost cos = context.deserialize( json.getAsJsonObject().get( JSONTags.MANA_COST ),
						Class.forName( "com.hackcaffebabe.mtg.model.cost.ManaCost" ) );
				if(c instanceof Creature) {
					Strength s = context.deserialize( json.getAsJsonObject().get( JSONTags.STRENGTH ),
							Class.forName( "com.hackcaffebabe.mtg.model.card.Strength" ) );
					((Creature) c).setManaCost( cos );
					((Creature) c).setStrength( s );
				} else if(c instanceof Artifact) {
					((Artifact) c).setManaCost( cos );
				} else if(c instanceof Enchantment) {
					((Enchantment) c).setManaCost( cos );
				} else if(c instanceof Instant) {
					((Instant) c).setManaCost( cos );
				} else if(c instanceof Planeswalker) {
					Set<PlanesAbility> set = new HashSet<>();
					for(JsonElement i: json.getAsJsonObject().get( JSONTags.PLANES_ABILITY ).getAsJsonArray()) {
						set.add( (PlanesAbility) context.deserialize( i,
								Class.forName( "com.hackcaffebabe.mtg.model.card.PlanesAbility" ) ) );
					}
					((Planeswalker) c).setPlanesAbilities( set );
					((Planeswalker) c).setManaCost( cos );
				} else if(c instanceof Sorcery) {
					((Sorcery) c).setManaCost( cos );
				}
			}
			return c;
		} catch(ClassNotFoundException cnfe) {
			throw new JsonParseException( "Unknown element type: MTGCard", cnfe );
		}
	}
}
