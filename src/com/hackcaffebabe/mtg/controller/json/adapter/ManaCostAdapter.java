package com.hackcaffebabe.mtg.controller.json.adapter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hackcaffebabe.mtg.model.card.OLD_ManaCost;
import com.hackcaffebabe.mtg.model.color.OLD_BasicColors;


/**
 * This is the JSON adapter for class ManaCost 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ManaCostAdapter implements JsonSerializer<OLD_ManaCost>, JsonDeserializer<OLD_ManaCost>
{
	@Override
	public JsonElement serialize(OLD_ManaCost arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		for(Map.Entry<OLD_BasicColors, Integer> i: arg0.getCost().entrySet())
			result.add( i.getKey() == null ? "null" : i.getKey().toString(), new JsonPrimitive( i.getValue() ) );
		return result;
	}

	@Override
	public OLD_ManaCost deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		OLD_ManaCost result = null;
		JsonObject manaCostAsJsonObject = arg0.getAsJsonObject();
		HashMap<OLD_BasicColors, Integer> mana = new HashMap<>();
		for(Map.Entry<String, JsonElement> i: manaCostAsJsonObject.entrySet()) {
			mana.put( i.getKey().equals( "null" ) ? null : OLD_BasicColors.valueOf( i.getKey() ), i.getValue().getAsInt() );
		}
		result = new OLD_ManaCost( mana );
		return result;
	}
}
