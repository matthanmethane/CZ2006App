package com.example.testapp.db.utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.testapp.db.AppDatabase;
import com.example.testapp.db.entity.SchoolToCourse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseFetcher implements Fetcher {
    @Override
    public JsonObjectRequest fetchData(AppDatabase database) {
         // create the JSON request for courses offered by each school
        return new JsonObjectRequest
        (Request.Method.GET, "https://data.gov.sg/api/action/datastore_search?resource_id=3bb9e6b0-6865-4a55-87ba-cc380bc4df39&limit=500000", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    // parse results as json array
                    JSONArray allSchoolToCourseAsJSONArray = getResultsAsJSONArray(response);

                    // get each entry in results and store in database
                    parseJSONArrayAndStoreInDatabase(database, allSchoolToCourseAsJSONArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                System.out.println("Rabak la bro: " + error.toString());
            }
        });
    }

    @Override
    public JSONArray getResultsAsJSONArray(JSONObject rawJson) throws JSONException {
        return rawJson.getJSONObject("result").getJSONArray("records");
    }

    @Override
    public void parseJSONArrayAndStoreInDatabase(final AppDatabase database,
                                                               JSONArray allSchoolToCourseAsJSONArray) throws JSONException {
        for (int i = 0; i < allSchoolToCourseAsJSONArray.length(); i++) {
            JSONObject record = allSchoolToCourseAsJSONArray.getJSONObject(i);
            String schoolName = record.getString("school_name");
            String courseName = record.getString("subject_desc");

            SchoolToCourse parsedSchoolToCourse = new SchoolToCourse(schoolName, courseName);
            database.SchoolToCourseModel().insertSchoolToCourse(parsedSchoolToCourse);
        }
    }
}
