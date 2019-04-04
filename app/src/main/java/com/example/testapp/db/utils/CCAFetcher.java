package com.example.testapp.db.utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.testapp.db.AppDatabase;
import com.example.testapp.db.entity.SchoolToCCA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class that fetches CCAs of schools.
 */
public class CCAFetcher implements Fetcher{
    /**
     * Fetch and store information into the database.
     * @param database
     * @return json object request
     */
    public JsonObjectRequest fetchData(final AppDatabase database) {
        // create the JSON request for CCAs offered by each school
        return new JsonObjectRequest
                (Request.Method.GET, "https://data.gov.sg/api/action/datastore_search?resource_id=dd7a056a-49fa-4854-bd9a-c4e1a88f1181&limit=500000", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // parse results as json array
                            JSONArray allSchoolToCCA_AsJSONArray = getResultsAsJSONArray(response);

                            // get each entry in results and store in database
                            parseJSONArrayAndStoreInDatabase(database, allSchoolToCCA_AsJSONArray);
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

    // this parsing format is specific to data.gov.sg's API
    public JSONArray getResultsAsJSONArray(JSONObject rawJson) throws JSONException {
        return rawJson.getJSONObject("result").getJSONArray("records");
    }

    // store into the database
    public void parseJSONArrayAndStoreInDatabase(final AppDatabase database,
                                                             JSONArray allSchoolToCCA_AsJSONArray) throws JSONException {
        for (int i = 0; i < allSchoolToCCA_AsJSONArray.length(); i++) {
            JSONObject record = allSchoolToCCA_AsJSONArray.getJSONObject(i);
            String schoolName = record.getString("school_name");
            String ccaGroup = record.getString("cca_grouping_desc");
            String ccaName = record.getString("cca_generic_name");

            SchoolToCCA parsedSchoolToCCA = new SchoolToCCA(schoolName, ccaName, ccaGroup);
            database.SchoolToCCAModel().insertSchoolToCCA(parsedSchoolToCCA);
        }
    }
}