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
import com.hackcaffebabe.mtg.model.card.PlanesAbility;


/**
 * This is the JSON adapter for class PlanesAbility 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class PlanesAbilityAdapter implements JsonSerializer<PlanesAbility>, JsonDeserializer<PlanesAbility>
{
	@Override
	public JsonElement serialize(PlanesAbility arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		result.add( JSON_TAG_COST, new JsonPrimitive( arg0.getCost() ) );
		result.add( JSON_TAG_DESCRIPTION, new JsonPrimitive( arg0.getDesctiption() ) );
		return result;
	}

	@Override
	public PlanesAbility deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		PlanesAbility result = null;
		Integer cost = arg0.getAsJsonObject().get( JSON_TAG_COST ).getAsInt();
		String description = arg0.getAsJsonObject().get( JSON_TAG_DESCRIPTION ).getAsString();
		result = new PlanesAbility( cost, description );
		return result;
	}
}
