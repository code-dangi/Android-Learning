package com.bt.newsfeddappusinghandler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Monika on 12/6/2016.
 * Utility Methods used in app
 */

public class UtilityMethods {
    /**
     * To convert the input stream
     * @param in input stream from the http connection
     * @return converted string from input stream
     */
    public static String readInputStream(InputStream in) {
        String result = "";
        // Convert response to string using String Builder
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(in, "utf-8"), 8);
            StringBuilder sBuilder = new StringBuilder();
            String line;
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line);
            }
            in.close();
            result = sBuilder.toString();
        } catch (UnsupportedEncodingException ue) {
            throw new RuntimeException("Unsupported utf-8 encoding");
        } catch (IOException ie) {
            throw new RuntimeException("exception while reading buffer");
        }
        return result;
    }
    /**
     * Converting the input string in array of the News
     */
    public static  ArrayList<News> readJsonInNewsArray(String result) {
        ArrayList<News> tempList = new ArrayList<>();
        //parse JSON data
        try {
            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                tempList.add(new News(jObject.getString("title"), jObject.getString("date"),
                        jObject.getString("place")));
            } // End Loop
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        }
        return tempList;
    }
}
