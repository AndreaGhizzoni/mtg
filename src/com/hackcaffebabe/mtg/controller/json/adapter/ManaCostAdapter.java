package com.hackcaffebabe.mtg.controller.json.adapter;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hackcaffebabe.mtg.model.color.Mana;
import com.hackcaffebabe.mtg.model.cost.ManaCost;
import com.hackcaffebabe.mtg.model.cost.Tuple;


/**
 * This is the JSON adapter for class ManaCost 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ManaCostAdapter implements JsonSerializer<ManaCost>, JsonDeserializer<ManaCost>
{
	@Override
	public JsonElement serialize(ManaCost arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		for(Tuple<Mana, Integer> t: arg0.getCost()) {
			result.add( t.getFirstObj().toString(), new JsonPrimitive( t.getSecondObj() ) );
		}
		return result;

//		for(Map.Entry<OLD_BasicColors, Integer> i: arg0.getCost().entrySet())
//			result.add( i.getKey() == null ? "null" : i.getKey().toString(), new JsonPrimitive( i.getValue() ) );
	}

	@Override
	public ManaCost deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		JsonObject manaCostAsJsonObject = arg0.getAsJsonObject();
		Set<Tuple<Mana, Integer>> mana = new HashSet<>();
		for(Map.Entry<String, JsonElement> i: manaCostAsJsonObject.entrySet()) {
			mana.add( new Tuple<Mana, Integer>( Mana.valueOf( i.getKey() ), i.getValue().getAsInt() ) );
		}
		return new ManaCost( mana );

//		HashMap<OLD_BasicColors, Integer> mana = new HashMap<>();
//		for(Map.Entry<String, JsonElement> i: manaCostAsJsonObject.entrySet()) {
//			mana.put( i.getKey().equals( "null" ) ? null : OLD_BasicColors.valueOf( i.getKey() ), i.getValue()
//					.getAsInt() );
//		}
//		result = new ManaCost( mana );
//		return result;
	}
}
