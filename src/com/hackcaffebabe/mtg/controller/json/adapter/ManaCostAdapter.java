package com.hackcaffebabe.mtg.controller.json.adapter;

import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
public class ManaCostAdapter implements JsonSerializer<ManaCost>/*, JsonDeserializer<ManaCost>*/ 
{
	@Override
	public JsonElement serialize(ManaCost arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		for( Map.Entry<BasicColors, Integer> i : arg0.getCost().entrySet() )
			result.add( i.getKey()==null? "null" : i.getKey().toString(), new JsonPrimitive(i.getValue()) );
		return result;
	}
	
//	@Override
//	public ManaCost deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
//		try {
//			return arg2.deserialize(arg0, Class.forName("com.hackcaffebabe.mtg.model.card.ManaCost"));
//		} catch (ClassNotFoundException e) {
//			throw new JsonParseException("Unknown element type: ManaCost", e);
//		}
//	}
}
