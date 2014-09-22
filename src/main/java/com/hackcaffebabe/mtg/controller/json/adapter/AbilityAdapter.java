package main.java.com.hackcaffebabe.mtg.controller.json.adapter;

import java.lang.reflect.Type;
import main.java.com.hackcaffebabe.mtg.controller.json.JSONTags;
import main.java.com.hackcaffebabe.mtg.model.card.Ability;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


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
		result.add( JSONTags.NAME, new JsonPrimitive( arg0.getName() ) );
		result.add( JSONTags.DESCRIPTION, new JsonPrimitive( arg0.getDescription() ) );
		return result;
	}

	@Override
	public Ability deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		Ability result = null;
		JsonObject abilityAsJsonObject = arg0.getAsJsonObject();
		String name = abilityAsJsonObject.get( JSONTags.NAME ).getAsString();
		String description = abilityAsJsonObject.get( JSONTags.DESCRIPTION ).getAsString();
		result = new Ability( name, description );
		return result;
	}
}
