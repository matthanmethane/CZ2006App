package com.example.testapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapp.R;

import androidx.annotation.Nullable;

/**
 * The Main activity.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add school list fragment if this is first creation (subsequent creations will have it inside savedInstanceState)
        if (savedInstanceState == null) {
            SchoolListFragment fragment = new SchoolListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, SchoolListFragment.TAG).commit();
        }
    }

//    /**
//     * Replaces...
//     *
//     * @param schoolName the school name
//     */
//    public void show(String schoolName) {
//
//
//    }

    //    /** Shows the product detail fragment */
    //    public void show(Product product) {
    //
    //        ProductFragment productFragment = ProductFragment.forProduct(product.getId());
    //
    //        getSupportFragmentManager()
    //                .beginTransaction()
    //                .addToBackStack("product")
    //                .replace(R.id.fragment_container,
    //                        productFragment, null).commit();
    //    }
}

// code graveyard

//package com.example.testapp.ui;
//
//import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.example.testapp.R;
//import com.example.testapp.db.AppDatabase;
//import com.example.testapp.db.entity.SchoolEntity;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MainActivity extends AppCompatActivity {
//    private AppDatabase db;
//
//    private Context context;
//    private static final String SCHOOL_GENERAL_INFORMATION_FILENAME = "SchoolGeneralInformation.json";
//    private static final String SCHOOL_GENERAL_INFORMATION_URL = "https://data.gov.sg/api/action/datastore_search?resource_id=ede26d32-01af-4228-b1ed-f05c45a1d8ee&limit=500000";
//    private static final String SUBJECTS_OFFERED_FILENAME = "SubjectsOffered.json";
//    private static final String SUBJECTS_OFFERED_URL = "https://data.gov.sg/api/action/datastore_search?resource_id=3bb9e6b0-6865-4a55-87ba-cc380bc4df39&limit=500000";
//    private static final String CCAS_OFFERED_FILENAME = "CCAsOffered.json";
//    private static final String CCAS_OFFERED_URL = "https://data.gov.sg/api/action/datastore_search?resource_id=dd7a056a-49fa-4854-bd9a-c4e1a88f1181&limit=500000";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        context = this;
//        final Button refreshButton = findViewById(R.id.refreshButton);
//        refreshButton.setOnClickListener(new SearchSchool());
////        db = AppDatabase.buildDatabase(context);
////        db = AppDatabase.getInMemoryDatabase(context);
////        DatabaseInitializer.populateSync(db);
////        runDataFetcher(); // this gets run last because its a background thread.
//    }
//
//    // searches the database for sth
//    private class SearchSchool implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            final TextView textView = findViewById(R.id.text);
//            final EditText editText = findViewById(R.id.editText);
//            String schoolNameSearched = editText.getText().toString();
//            schoolNameSearched = "%" + schoolNameSearched + "%";
//            ArrayList<String> ccas = new ArrayList<String>();
//            ccas.add("NCC(Land)");
//            ArrayList<String> courses = new ArrayList<String>();
//            courses.add("COMPUTING");
//            List<SchoolEntity> sekolahs = db.SchoolModel().findSecondarySchoolUsingNameAndCCAsAndCourses(schoolNameSearched, ccas, courses);
//            System.out.println(schoolNameSearched);
////            List<SchoolEntity> sekolahs = db.SchoolModel().getSchoolsByName(schoolNameSearched);
//            System.out.println(sekolahs.get(0).name);
//        }
//    }
//
//    // listener which searches a school based on its name
//    private class SearchSchoolViaName implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            final TextView textView = findViewById(R.id.text);
//            final EditText editText = findViewById(R.id.editText);
//            String schoolNameSearched = editText.getText().toString();
//            // read from the json file
//            try {
//                FileInputStream fileInputStream = context.openFileInput(SCHOOL_GENERAL_INFORMATION_FILENAME);
//                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String rawJSONString = bufferedReader.readLine();
//                JSONObject parsedJSON = new JSONObject(rawJSONString);
////                JSONObject results = new JSONObject((String) parsedJSON.get("result"));
//                JSONArray allSchools = parsedJSON   .getJSONObject("result")
//                                                    .getJSONArray("records");
//                JSONArray searchResults = new JSONArray();
//
//                if (schoolNameSearched.length() > 0)
//                {
//                    for (int i = 0; i < allSchools.length(); i++)
//                    {
//                        JSONObject currentSchool = allSchools.getJSONObject(i);
//                        // standardize the names to lower case otherwise the search will not be successful.
//                        String currentSchoolName = currentSchool.getString("school_name").toLowerCase();
//                        System.out.println(currentSchoolName);
//                        if (currentSchoolName.contains(schoolNameSearched.toLowerCase()))
//                        {
//                            searchResults.put(currentSchool);
//                        }
//                        if (searchResults.length() > 100)
//                        {
//                            textView.setText("Too many search results, only showing first 100. Please refine your search criteria.");
//                            break;
//                        }
//                    }
//                    // SET CONTENTS OF TEXT VIEW TO Search Results
//                    if (searchResults.length() == 0)
//                        textView.setText("No results found!");
//                    else
//                    {
//                        textView.setText(searchResults.toString());
//                    }
//                }
//                else
//                {
//                    textView.setText("Please input a name!");
//                }
//            } catch (IOException | JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    // just a random function that craps out school general information as a string.
//    private class UpdateTextbox implements View.OnClickListener {
//        @Override
//        public void onClick(View view) {
//            final TextView textView = (TextView) findViewById(R.id.text);
//
//            // read from the json file
//            try {
//                FileInputStream fileInputStream = context.openFileInput(SCHOOL_GENERAL_INFORMATION_FILENAME);
//                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String lineData = bufferedReader.readLine();
//
//                // SET CONTENTS OF TEXT VIEW TO CONTENTS OF JSON FILE
//                textView.setText(lineData);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    };
//}
