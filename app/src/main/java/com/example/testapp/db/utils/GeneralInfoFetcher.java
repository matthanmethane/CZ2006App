package com.example.testapp.db.utils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.testapp.db.AppDatabase;
import com.example.testapp.db.entity.PreUniversitySchool;
import com.example.testapp.db.entity.PrimarySchool;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.db.entity.SecondarySchool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeneralInfoFetcher {

    public JsonObjectRequest fetchData(AppDatabase database, RequestFuture<JSONObject> future) {

//        Response.Listener GeneralInfoFetcherResponseListener = new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    // parse results as json array
//                    JSONArray allSchoolsAsJSONArray = getResultsAsJSONArray(response);
//
//                    parseJSONArrayAndStoreInDatabase(database, allSchoolsAsJSONArray);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        };

        Response.ErrorListener el = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                System.out.println("Rabak la bro: " + error.toString());
            }
        };

        return new JsonObjectRequest
                (Request.Method.GET,
                        "https://data.gov.sg/api/action/datastore_search?resource_id=ede26d32-01af-4228-b1ed-f05c45a1d8ee&limit=500000",
                        null,
                        future,
                        el
                );
    }

    public JSONArray getResultsAsJSONArray(JSONObject rawJson) throws JSONException {
        return rawJson.getJSONObject("result").getJSONArray("records");
    }

    public void parseJSONArrayAndStoreInDatabase(final AppDatabase database,
                                                       JSONArray allSchoolsAsJSONArray) throws JSONException {
        for (int i = 0; i < allSchoolsAsJSONArray.length(); i++) {

            JSONObject school = allSchoolsAsJSONArray.getJSONObject(i);
            String schoolName = school.getString("school_name");
            String physicalAddress = school.getString("address");
            int postalCode = school.getInt("postal_code");
            String telephoneNumber1 = school.getString("telephone_no");
            String telephoneNumber2 = school.getString("telephone_no_2");
            String homePageAddress = school.getString("url_address");
            String emailAddress = school.getString("email_address");
            String mission = school.getString("missionstatement_desc");
            String vision = school.getString("visionstatement_desc");
            String schoolAutonomyType = school.getString("type_code");
            String schoolGender = school.getString("nature_code");
            int SAPSchool = school.getString("sap_ind").equalsIgnoreCase("Yes") ? 1 : 0;
            int autonomousSchool = school.getString("autonomous_ind").equalsIgnoreCase("Yes") ? 1 : 0;
            int integratedProgram = school.getString("ip_ind").equalsIgnoreCase("Yes") ? 1 : 0;
            int giftedEducationProgramOffered = school.getString("gifted_ind").equalsIgnoreCase("Yes") ? 1 : 0;
            String zoneCode = school.getString("zone_code");
            String clusterCode = school.getString("cluster_code");


            // for determining which level to put the school
            String level = school.getString("mainlevel_code");

            System.err.println("ParsingLevelCode: " + level);
            System.err.println("EqualToPrimary: " + level.equalsIgnoreCase("primary"));

            // for primary school
            String sessionCode = school.getString("session_code");

            // store school in database
            SchoolEntity parsedSchoolEntity = new SchoolEntity(i + 1,
                    schoolName,
                    physicalAddress,
                    postalCode,
                    telephoneNumber1,
                    telephoneNumber2,
                    homePageAddress,
                    emailAddress,
                    mission,
                    vision,
                    schoolAutonomyType,
                    schoolGender,
                    SAPSchool,
                    autonomousSchool,
                    integratedProgram,
                    giftedEducationProgramOffered,
                    zoneCode,
                    clusterCode);
            database.SchoolModel().insertSchool(parsedSchoolEntity);

            // check which other school table to insert data into
            if (level.equalsIgnoreCase("primary")) {
                PrimarySchool parsedPrimarySchool = new PrimarySchool(schoolName, sessionCode);
                database.PrimarySchoolModel().insertPrimarySchool(parsedPrimarySchool);
            } else if (level.equalsIgnoreCase("secondary")) {
                SecondarySchool parsedSecondarySchool = new SecondarySchool(schoolName, 0, 0, 0, 0, 0);
                database.SecondarySchoolModel().insertSecondarySchool(parsedSecondarySchool);
            } else if (level.equalsIgnoreCase("junior college") || level.equalsIgnoreCase("CENTRALISED INSTITUTE")) {
                PreUniversitySchool parsedPreUniversitySchool = new PreUniversitySchool(schoolName, 10, 10);
                database.PreUniversitySchoolModel().insertPreUniversitySchool(parsedPreUniversitySchool);
            } else if (level.equalsIgnoreCase("mixed level")) {
                // TODO: Load exceptional cases of mixed levels from another file
                if (schoolName.equalsIgnoreCase("MARIS STELLA HIGH SCHOOL") ||
                        schoolName.equalsIgnoreCase("CHIJ ST. NICHOLAS GIRLS' SCHOOL") ||
                        schoolName.equalsIgnoreCase("CATHOLIC HIGH SCHOOL")) {
                    PrimarySchool parsedPrimarySchool = new PrimarySchool(schoolName, sessionCode);
                    database.PrimarySchoolModel().insertPrimarySchool(parsedPrimarySchool);
                } else {
                    PreUniversitySchool parsedPreUniversitySchool = new PreUniversitySchool(schoolName, 10, 10);
                    database.PreUniversitySchoolModel().insertPreUniversitySchool(parsedPreUniversitySchool);
                }
                SecondarySchool parsedSecondarySchool = new SecondarySchool(schoolName, 0, 0, 0, 0, 0);
                database.SecondarySchoolModel().insertSecondarySchool(parsedSecondarySchool);
            }
        }
    }
}
