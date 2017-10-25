package com.tellier.ange_marie.tp1;

import com.tellier.ange_marie.tp1.model.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

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

    public static List<Message> getMessages(String json) throws JSONException {
        List<Message> messages = new LinkedList<>();
        JSONArray array = new JSONArray(json);
        JSONObject obj;
        Message msg;
        for(int i=0; i < array.length(); i++){
            obj = array.getJSONObject(i);
            msg = new Message(obj.optString("username"), obj.optString("message"), obj.optLong("date"));
            messages.add(msg);
        }

        return messages;
    }
}
