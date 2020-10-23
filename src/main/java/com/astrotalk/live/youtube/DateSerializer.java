package com.astrotalk.live.youtube;

import com.google.api.client.util.DateTime;
import com.google.gson.*;

import java.lang.reflect.Type;


public class DateSerializer implements JsonSerializer<DateTime>
    {
        public JsonElement serialize(DateTime dateStr, Type typeOfSrc, JsonSerializationContext context)
        {

                return new JsonPrimitive(dateStr.toString());
        }
    }

