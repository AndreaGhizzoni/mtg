package com.hackcaffebabe.mtg.controller.json.adapter;

import static com.hackcaffebabe.mtg.controller.DBCostants.JSON_TAG_MANA_COST;
import static com.hackcaffebabe.mtg.controller.DBCostants.JSON_TAG_TEXT;
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
import com.hackcaffebabe.mtg.model.card.Effect;
import com.hackcaffebabe.mtg.model.color.Mana;
import com.hackcaffebabe.mtg.model.cost.ManaCost;
import com.hackcaffebabe.mtg.model.cost.Tuple;


/**
 * This is the JSON adapter for class Effect
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class EffectAdapter implements JsonSerializer<Effect>, JsonDeserializer<Effect>
{
	@Override
	public JsonElement serialize(Effect arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		result.add( JSON_TAG_MANA_COST, arg2.serialize( arg0.getManaCost() ) );
		result.add( JSON_TAG_TEXT, new JsonPrimitive( arg0.getText() ) );
		return result;
	}

	@Override
	public Effect deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		JsonObject effectAsJsonObject = arg0.getAsJsonObject();
		JsonObject manaCostAsJsonObject = effectAsJsonObject.get( JSON_TAG_MANA_COST ).getAsJsonObject();
		Set<Tuple<Mana, Integer>> mana = new HashSet<>();
		for(Map.Entry<String, JsonElement> i: manaCostAsJsonObject.entrySet()) {
			mana.add( new Tuple<Mana, Integer>( Mana.valueOf( i.getKey() ), i.getValue().getAsInt() ) );
		}
		String text = effectAsJsonObject.get( JSON_TAG_TEXT ).getAsString();

		return new Effect( new ManaCost( mana ), text );
	}
}