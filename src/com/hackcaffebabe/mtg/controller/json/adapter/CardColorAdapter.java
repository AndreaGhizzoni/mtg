package com.hackcaffebabe.mtg.controller.json.adapter;

import static com.hackcaffebabe.mtg.controller.DBCostants.JSON_TAG_COLORS;
import static com.hackcaffebabe.mtg.controller.DBCostants.JSON_TAG_TYPE;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.hackcaffebabe.mtg.model.color.OLD_BasicColors;
import com.hackcaffebabe.mtg.model.color.OLD_CardColor;
import com.hackcaffebabe.mtg.model.color.TypeColor;


/**
 * This is the JSON adapter for class CardColor 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class CardColorAdapter implements JsonSerializer<OLD_CardColor>, JsonDeserializer<OLD_CardColor>
{
	@Override
	public JsonElement serialize(OLD_CardColor arg0, Type arg1, JsonSerializationContext arg2){
		JsonObject result = new JsonObject();
		JsonArray colors = new JsonArray();
		for(OLD_BasicColors i: arg0.getBasicColors())
			colors.add( new JsonPrimitive( i == null ? "null" : i.toString() ) );
		result.add( JSON_TAG_COLORS, colors );
		result.add( JSON_TAG_TYPE, new JsonPrimitive( arg0.getType().toString() ) );
		return result;
	}

	@Override
	public OLD_CardColor deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException{
		OLD_CardColor result = null;

		JsonObject cardColorAdJsonObject = arg0.getAsJsonObject();
		JsonArray colors = cardColorAdJsonObject.get( JSON_TAG_COLORS ).getAsJsonArray();
		ArrayList<OLD_BasicColors> list = new ArrayList<>();
		for(JsonElement i: colors)
			list.add( OLD_BasicColors.valueOf( i.getAsString() ) );

		TypeColor type = TypeColor.valueOf( cardColorAdJsonObject.get( JSON_TAG_TYPE ).getAsString() );
		switch( type ) {
			case COLOR_LESS: {
				result = new OLD_CardColor();
				break;
			}
			case IBRID: {
				result = new OLD_CardColor( list.get( 0 ), list.get( 1 ) );
				break;
			}
			case MONO_COLOR: {
				result = new OLD_CardColor( list.get( 0 ) );
				break;
			}
			case MULTI_COLOR: {
				result = new OLD_CardColor( list );
				break;
			}
		}
		return result;
	}
}
