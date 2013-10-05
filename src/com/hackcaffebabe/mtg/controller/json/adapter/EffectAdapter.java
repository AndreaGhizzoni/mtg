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
import com.hackcaffebabe.mtg.model.card.Effect;

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
		result.add( "mana_cost", arg2.serialize( arg0.getManaCost() ) );
		result.add( "text", new JsonPrimitive( arg0.getText() ) );
		return result;
	}

	
	@Override
	public Effect deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		try {
			return arg2.deserialize(new JsonPrimitive("Effect"), Class.forName("com.hackcaffebabe.mtg.model.card.Effect"));
		} catch (ClassNotFoundException e) {
			throw new JsonParseException("Unknown element type: Effect", e);
		}
	}
}


