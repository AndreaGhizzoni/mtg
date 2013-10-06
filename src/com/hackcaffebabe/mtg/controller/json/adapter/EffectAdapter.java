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
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.card.ManaCost;
import com.hackcaffebabe.mtg.model.color.BasicColors;

/**
 * TODO add doc
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class EffectAdapter implements JsonSerializer<Effect>, JsonDeserializer<Effect> 
{
	@Override
	public JsonElement serialize(Effect arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		result.add( "mana_cost", arg2.serialize(arg0.getManaCost()) );
		result.add( "text", new JsonPrimitive(arg0.getText()) );
		return result;
	}
	
	
	@Override
	public Effect deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		Effect result = null;
		
		JsonObject effectAsJsonObject = arg0.getAsJsonObject();
		JsonObject manacost = effectAsJsonObject.get( "mana_cost" ).getAsJsonObject();
		HashMap<BasicColors, Integer> cost = new HashMap<>();
		for( Map.Entry<String, JsonElement> i : manacost.entrySet() ){
			cost.put( i.getKey().equals("null")? null:BasicColors.valueOf(i.getKey()), i.getValue().getAsInt() );
		}
		String text = effectAsJsonObject.get("text").getAsString();
		
		result = new Effect( new ManaCost( cost ), text );
		return result;		
	}
}