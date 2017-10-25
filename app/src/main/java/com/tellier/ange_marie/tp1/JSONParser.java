package com.tellier.ange_marie.tp1;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ange-marie on 25/10/17.
 */

public class JSONParser {
    public static String getToken(String json) {
        try{
            return new JSONObject(json).optString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
