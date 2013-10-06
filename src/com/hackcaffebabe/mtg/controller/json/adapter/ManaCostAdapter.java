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
import com.hackcaffebabe.mtg.model.card.ManaCost;
import com.hackcaffebabe.mtg.model.color.BasicColors;

/**
 * TODO add doc
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ManaCostAdapter implements JsonSerializer<ManaCost>, JsonDeserializer<ManaCost> 
{
	@Override
	public JsonElement serialize(ManaCost arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		for( Map.Entry<BasicColors, Integer> i : arg0.getCost().entrySet() )
			result.add( i.getKey()==null? "null" : i.getKey().toString(), new JsonPrimitive(i.getValue()) );
		return result;
	}
	
	@Override
	public ManaCost deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		ManaCost result = null;
		
		JsonObject manaCostAsJsonObject = arg0.getAsJsonObject();		
		HashMap<BasicColors, Integer> mana = new HashMap<>();
		for( Map.Entry<String, JsonElement> i : manaCostAsJsonObject.entrySet() ){
			mana.put( i.getKey().equals("null")? null: BasicColors.valueOf(i.getKey()), i.getValue().getAsInt() );
		}
		
		result = new ManaCost( mana );
		return result;
	}	
}
