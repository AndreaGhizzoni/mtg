package com.hackcaffebabe.mtg.controller.json.adapter;

import static com.hackcaffebabe.mtg.controller.DBCostants.*;
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
import com.hackcaffebabe.mtg.model.card.OLD_Effect;
import com.hackcaffebabe.mtg.model.card.OLD_ManaCost;
import com.hackcaffebabe.mtg.model.color.OLD_BasicColors;


/**
 * This is the JSON adapter for class Effect
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class EffectAdapter implements JsonSerializer<OLD_Effect>, JsonDeserializer<OLD_Effect>
{
	@Override
	public JsonElement serialize(OLD_Effect arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		result.add( JSON_TAG_MANA_COST, arg2.serialize( arg0.getManaCost() ) );
		result.add( JSON_TAG_TEXT, new JsonPrimitive( arg0.getText() ) );
		return result;
	}

	@Override
	public OLD_Effect deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		OLD_Effect result = null;

		JsonObject effectAsJsonObject = arg0.getAsJsonObject();
		JsonObject manacost = effectAsJsonObject.get( JSON_TAG_MANA_COST ).getAsJsonObject();
		HashMap<OLD_BasicColors, Integer> cost = new HashMap<>();
		for(Map.Entry<String, JsonElement> i: manacost.entrySet()) {
			cost.put( i.getKey().equals( "null" ) ? null : OLD_BasicColors.valueOf( i.getKey() ), i.getValue().getAsInt() );
		}
		String text = effectAsJsonObject.get( JSON_TAG_TEXT ).getAsString();

		result = new OLD_Effect( new OLD_ManaCost( cost ), text );
		return result;
	}
}