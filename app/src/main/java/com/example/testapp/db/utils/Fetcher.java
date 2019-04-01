package com.example.testapp.db.utils;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.testapp.db.AppDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface Fetcher {
    JsonObjectRequest fetchData(final AppDatabase database);
    JSONArray getResultsAsJSONArray(JSONObject rawJson) throws JSONException;
    void parseJSONArrayAndStoreInDatabase(final AppDatabase database, JSONArray allSchoolToCCA_AsJSONArray) throws JSONException;
}
