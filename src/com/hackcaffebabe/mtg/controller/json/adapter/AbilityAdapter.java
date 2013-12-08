package com.hackcaffebabe.mtg.controller.json.adapter;

import static com.hackcaffebabe.mtg.controller.DBCostants.JSON_TAG_DESCRIPTION;
import static com.hackcaffebabe.mtg.controller.DBCostants.JSON_TAG_NAME;
import java.lang.reflect.Type;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hackcaffebabe.mtg.model.card.Ability;


/**
 * This is the JSON adapter for class Ability
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class AbilityAdapter implements JsonSerializer<Ability>, JsonDeserializer<Ability>
{
	@Override
	public JsonElement serialize(Ability arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		result.add( JSON_TAG_NAME, new JsonPrimitive( arg0.getName() ) );
		result.add( JSON_TAG_DESCRIPTION, new JsonPrimitive( arg0.getDescription() ) );
		return result;
	}

	@Override
	public Ability deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		Ability result = null;
		JsonObject abilityAsJsonObject = arg0.getAsJsonObject();
		String name = abilityAsJsonObject.get( JSON_TAG_NAME ).getAsString();
		String description = abilityAsJsonObject.get( JSON_TAG_DESCRIPTION ).getAsString();
		result = new Ability( name, description );
		return result;
	}
}
