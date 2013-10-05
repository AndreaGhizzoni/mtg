package com.hackcaffebabe.mtg.controller.json.adapter;

import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hackcaffebabe.mtg.model.*;
import com.hackcaffebabe.mtg.model.card.ManaCost;
import com.hackcaffebabe.mtg.model.card.Strength;
import com.hackcaffebabe.mtg.model.color.CardColor;

/**
 * TODO add doc
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class MTGCardAdapter implements JsonSerializer<MTGCard>, JsonDeserializer<MTGCard>
{
	@Override
	public JsonElement serialize(MTGCard c, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		result.add( "type", new JsonPrimitive(c.getClass().getSimpleName()) );
		result.add( "name", new JsonPrimitive(c.getName()) );
		result.add( "card_color", arg2.serialize(c.getCardColor(), CardColor.class ) );
		result.add( "rarity", arg2.serialize(c.getRarity()) );
		result.add( "series", new JsonPrimitive(c.getSeries()) );
		result.add( "sub_type", new JsonPrimitive(c.getSubType()) );
		result.add( "is_artifact", new JsonPrimitive(c.isArtifact()) );
		result.add( "is_legendary", new JsonPrimitive(c.isLegendary()) );
		result.add( "primary_effect", new JsonPrimitive(c.getPrimaryEffect()) );
		result.add( "effects", arg2.serialize(c.getEffects()) );
		result.add( "abilitis", arg2.serialize(c.getAbilities()) );
		
		switch(c.getClass().getSimpleName()){
			case "Creature":{
				Creature x=((Creature)c);
				result.add( "mana_cost", arg2.serialize(x.getManaCost(), ManaCost.class) );
				result.add( "streangth", arg2.serialize(x.getStrength(), Strength.class) );
				break;
			}
			case "Artifact":{
				Artifact x=((Artifact)c);
				result.add( "mana_cost", arg2.serialize(x.getManaCost(), ManaCost.class) );
				break;
			}
			case "Enchantment":{
				Enchantment x=((Enchantment)c);
				result.add( "mana_cost", arg2.serialize(x.getManaCost(), ManaCost.class) );
				break;
			}
			case "Instant":{
				Instant x=((Instant)c);
				result.add( "mana_cost", arg2.serialize(x.getManaCost(),ManaCost.class) );
				break;
			}
			case "Planeswalker":{
				Planeswalker x=((Planeswalker)c);
				result.add( "mana_cost", arg2.serialize(x.getManaCost(), ManaCost.class) );
				result.add( "life", new JsonPrimitive(x.getLife()) );
				result.add( "planes_ability", arg2.serialize(x.getPlanesAbilities()) );// TODO maybe create an adapter for this.
				break;
			}
			case "Sorcery":{
				Sorcery x=((Sorcery)c);
				result.add( "mana_cost", arg2.serialize(x.getManaCost(), ManaCost.class) );
				break;
			}			
		}
		
		return result;
	}
	
	@Override
	public MTGCard deserialize(JsonElement json, Type arg1, JsonDeserializationContext context) throws JsonParseException{
		try {
			return context.deserialize(new JsonPrimitive("MTGCard"), Class.forName("com.hackcaffebabe.mtg.model.MTGCard"));
		} catch (ClassNotFoundException cnfe) {
			throw new JsonParseException("Unknown element type: MTGCard", cnfe);
		}
	}

	
//	@Override
//	public MTGCard deserialize(JsonElement json, Type arg1, JsonDeserializationContext context) throws JsonParseException{
//		String type = json.getAsJsonObject().get("type").getAsString();
//		try {
//			return context.deserialize(new JsonPrimitive(type), Class.forName("com.hackcaffebabe.mtg.model."+type));
//		} catch (ClassNotFoundException cnfe) {
//			throw new JsonParseException("Unknown element type: "+type, cnfe);
//		}
//	}
}
