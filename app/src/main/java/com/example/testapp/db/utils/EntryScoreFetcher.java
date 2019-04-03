package com.example.testapp.db.utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.testapp.db.AppDatabase;
import com.example.testapp.db.entity.PreUniversitySchool;
import com.example.testapp.db.entity.SecondarySchool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EntryScoreFetcher implements Fetcher {
    @Override
    public JsonObjectRequest fetchData(AppDatabase database) {
        return new JsonObjectRequest
                (Request.Method.GET,
                        "https://raw.githubusercontent.com/datagovsg/school-picker/master/public/data/entityList.json",
                        null,
                        new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // parse results as json array
                            JSONArray entryScoreAsJSONArray = new JSONArray(response) ;

                            // get each entry in results and store in database
                            parseJSONArrayAndStoreInDatabase(database, entryScoreAsJSONArray);

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
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < rawJson.length(); i ++) {
            jsonArray.put(rawJson);
        }
        return jsonArray;
    }

    @Override
    public void parseJSONArrayAndStoreInDatabase(AppDatabase database, JSONArray entryScore_AsJSONArray) throws JSONException {
        for (int i = 0; i < entryScore_AsJSONArray.length(); i++) {
            JSONObject record = entryScore_AsJSONArray.getJSONObject(i);
            String schoolName = record.getString("name").toUpperCase(); // standardize such that all names are uppercase;
            String level = record.getString("levelOfEducation");
            if (level.contains("P")) {
                // do nothing
            }
            if (level.contains("S")) {
                /**
                 * add all these scores into each secondary school
                 *                 int PSLEExpressScore;
                 *                 int PSLENormalAcademicScore;
                 *                 int PSLENormalTechnicalScore;
                 *                 int PSLEExpressAffilationScore;
                 *                 int PSLEIntegratedProgramScore;
                 *
                 */
                SecondarySchool secondarySchool = database.SecondarySchoolModel().getSecondarySchool(schoolName);

                JSONArray allPSLEScores = record.getJSONArray("psleAggregate");

                for (int j = 0; j < allPSLEScores.length(); j++)
                {
                    JSONObject eachScore = allPSLEScores.getJSONObject(i);

                    String programme = eachScore.getString("programme");

                    if (programme.equals("Integrated Programme")) {
                        secondarySchool.PSLEIntegratedProgramScore = eachScore.getInt("lower");
                    } else if (programme.equals("'O' Level Programme") || programme.equals("Express")) {
                        secondarySchool.PSLEExpressScore = eachScore.getInt("lower");
                        if (eachScore.get("lowerAffiliated") != null) {
                            secondarySchool.PSLEExpressAffiliationScore = eachScore.getInt("lowerAffiliated");
                        }
                    } else if (programme.equals("Normal Academic")) {
                        secondarySchool.PSLENormalAcademicScore = eachScore.getInt("lower");
                    } else if (programme.equals("Normal Technical")) {
                        secondarySchool.PSLENormalTechnicalScore = eachScore.getInt("lower");
                    }
                }

                database.SecondarySchoolModel().updateSecondarySchool(secondarySchool);
            }
            if (level.contains("J")) {
                PreUniversitySchool preUniversitySchool = database.PreUniversitySchoolModel().getPreUniversity(schoolName);

                JSONArray allJCScores = record.getJSONArray("l1r5Aggregate");

                for (int j = 0; j < allJCScores.length(); j++)
                {
                    JSONObject eachScore = allJCScores.getJSONObject(j);

                    String programme = eachScore.getString("programme");

                    if (programme.equals("Arts")) {
                        preUniversitySchool.scienceStreamScore = eachScore.getInt("upper");
                    } else if (programme.equals("Science/IB")) {
                        preUniversitySchool.artsStreamScore = eachScore.getInt("upper");
                    }
                }

                database.PreUniversitySchoolModel().updatePreUniversitySchool(preUniversitySchool);
            }
        }
    }
}

