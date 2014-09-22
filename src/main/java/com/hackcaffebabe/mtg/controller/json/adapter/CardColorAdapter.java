package main.java.com.hackcaffebabe.mtg.controller.json.adapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import main.java.com.hackcaffebabe.mtg.controller.json.JSONTags;
import main.java.com.hackcaffebabe.mtg.model.color.CardColor;
import main.java.com.hackcaffebabe.mtg.model.color.Mana;
import main.java.com.hackcaffebabe.mtg.model.color.TypeColor;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 * This is the JSON adapter for class CardColor 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class CardColorAdapter implements JsonSerializer<CardColor>, JsonDeserializer<CardColor>
{
	@Override
	public JsonElement serialize(CardColor arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		JsonArray colors = new JsonArray();
		for(Mana i: arg0.getColors())
			colors.add( new JsonPrimitive( i == null ? "null" : i.toString() ) );
		result.add( JSONTags.COLORS, colors );
		result.add( JSONTags.TYPE, new JsonPrimitive( arg0.getType().toString() ) );
		return result;
	}

	@Override
	public CardColor deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException{
		CardColor result = null;

		JsonObject cardColorAdJsonObject = arg0.getAsJsonObject();
		JsonArray colors = cardColorAdJsonObject.get( JSONTags.COLORS ).getAsJsonArray();
		ArrayList<Mana> list = new ArrayList<>();
		for(JsonElement i: colors)
			list.add( Mana.valueOf( i.getAsString() ) );

		TypeColor type = TypeColor.valueOf( cardColorAdJsonObject.get( JSONTags.TYPE ).getAsString() );
		switch( type ) {
			case COLOR_LESS: {
				result = new CardColor();
				break;
			}
			case HYBRID: {
				result = new CardColor( list.get( 0 ), list.get( 1 ) );
				break;
			}
			case MONO_COLOR: {
				result = new CardColor( list.get( 0 ) );
				break;
			}
			case MULTI_COLOR: {
				result = new CardColor( list );
				break;
			}
		}
		return result;
	}
}
