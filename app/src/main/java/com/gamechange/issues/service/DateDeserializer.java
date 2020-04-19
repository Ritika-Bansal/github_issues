package com.gamechange.issues.service;

import android.util.Log;

import com.gamechange.issues.models.Issues;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Issues> {
    @Override
    public Issues deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        json.getAsJsonObject()
                .addProperty("updated_in_epoch", isoToEpoch(json.getAsJsonObject().get("updated_at").getAsString()));

        Gson gson = new GsonBuilder().create();

        return gson.fromJson(json.toString(),
                Issues.class);
    }

    private long isoToEpoch(String dateString) {
        try {
            Log.e("date format", dateString);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss'Z'");
            Date date = format.parse(dateString);
            long epoch = date.getTime();
            Log.e("epoch", epoch + "");
            System.out.println(epoch);
            return epoch;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}