package com.example.testapp.db.utils;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.testapp.db.AppDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Interface to fetch information on schools.
 */
public interface Fetcher {
    /**
     * Fetch and store information into the database.
     * @param database user's database
     * @return json object request
     */
    JsonObjectRequest fetchData(final AppDatabase database);
    /**
     * Convert retrieved information into a JSON array.
     * @param rawJson retrieved information
     * @return JSON array
     * @throws JSONException
     */
    JSONArray getResultsAsJSONArray(JSONObject rawJson) throws JSONException;

    /**
     * Store JSON array into the database.
     * @param database user's database
     * @param allSchoolToCCA_AsJSONArray processed information
     * @throws JSONException
     */
    void parseJSONArrayAndStoreInDatabase(final AppDatabase database, JSONArray allSchoolToCCA_AsJSONArray) throws JSONException;
}
