package com.hackcaffebabe.mtg.controller.json.adapter;

import static com.hackcaffebabe.mtg.controller.DBCostants.*;
import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hackcaffebabe.mtg.model.card.Strength;


/**
 * This is the JSON adapter for class Strength 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class StrengthAdapter implements JsonSerializer<Strength>, JsonDeserializer<Strength>
{
	@Override
	public JsonElement serialize(Strength arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		result.add( JSON_TAG_POWER, new JsonPrimitive( arg0.getPower() ) );
		result.add( JSON_TAG_TOUGHNESS, new JsonPrimitive( arg0.getToughness() ) );
		return result;
	}

	@Override
	public Strength deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		Strength resutl = null;
		Integer p = arg0.getAsJsonObject().get( JSON_TAG_POWER ).getAsInt();
		Integer t = arg0.getAsJsonObject().get( JSON_TAG_TOUGHNESS ).getAsInt();
		resutl = new Strength( p, t );
		return resutl;
	}
}
