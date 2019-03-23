package com.example.testapp.db;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.testapp.AppExecutors;
import com.example.testapp.R;
import com.example.testapp.db.dao.PreUniversitySchoolDao;
import com.example.testapp.db.dao.PrimarySchoolDao;
import com.example.testapp.db.dao.SchoolDao;
import com.example.testapp.db.dao.SchoolToCCADao;
import com.example.testapp.db.dao.SchoolToCourseDao;
import com.example.testapp.db.dao.SecondarySchoolDao;
import com.example.testapp.db.entity.PreUniversitySchool;
import com.example.testapp.db.entity.PrimarySchool;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.db.entity.SchoolToCCA;
import com.example.testapp.db.entity.SchoolToCourse;
import com.example.testapp.db.entity.SecondarySchool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * The abstraction over the actual database. Used to register the database and all views and entities associated with it,
 * as well as all the data access objects (DAOs) for each entity.
 */
@Database(entities =
        {SchoolEntity.class, PreUniversitySchool.class,
                PrimarySchool.class, SchoolToCCA.class,
                SchoolToCourse.class, SecondarySchool.class,
        }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "EmPathy DB";

    // have a single reference to its object (singleton principle) to ensure data integrity.
    private static AppDatabase sInstance;

    // a boolean variable to save the state of the database (i.e. whether it has been created or not)
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    /**
     * Pre university school model pre university school dao.
     *
     * @return the pre university school dao
     */
    public abstract PreUniversitySchoolDao PreUniversitySchoolModel();

    /**
     * Primary school model primary school dao.
     *
     * @return the primary school dao
     */
    public abstract PrimarySchoolDao PrimarySchoolModel();

    /**
     * Secondary school model secondary school dao.
     *
     * @return the secondary school dao
     */
    public abstract SecondarySchoolDao SecondarySchoolModel();

    /**
     * SchoolEntity model school dao.
     *
     * @return the school dao
     */
    public abstract SchoolDao SchoolModel();

    /**
     * SchoolEntity to cca model school to cca dao.
     *
     * @return the school to cca dao
     */
    public abstract SchoolToCCADao SchoolToCCAModel();

    /**
     * SchoolEntity to course model school to course dao.
     *
     * @return the school to course dao
     */
    public abstract SchoolToCourseDao SchoolToCourseModel();

    /**
     * Gets the single instance of the database.
     *
     * @param context   the context
     * @param executors the executors
     * @return the instance
     */
    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        // TODO: Prevent main thread queries from being run.
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                        executors.diskIO().execute(new SchoolDataUpdater(appContext, AppDatabase.getInstance(appContext, executors)));

                    }
                })
                .build();
    }

    /**
     * Destroy instance.
     */
    public static void destroyInstance() {
        sInstance = null;
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    /**
     * Gets the status of the database.
     *
     * @return whether the database is created
     */
    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    /**
     * A private class extending Runnable, which is used for multithreading.
     * Used as a task in a seperate thread to fetch data for the application.
     */
    private static class SchoolDataUpdater implements Runnable {
        private Context appContext;
        private AppDatabase database;

        /**
         * Instantiates a new SchoolEntity data updater.
         *
         * @param appContext the app context
         * @param database   the database
         */
        public SchoolDataUpdater(Context appContext, AppDatabase database) {
            this.appContext = appContext;
            this.database = database;
        }

        /**
         * This whole chunk of code is used to instantiate the app's data on first run.
         */
        @Override
        public void run() {
            // Moves the current Thread into the background
            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(appContext);

            // create the JSON request for school general information
            JsonObjectRequest schoolGeneralInfoJsonRequest = new JsonObjectRequest
                    (Request.Method.GET, appContext.getString(R.string.SCHOOL_GENERAL_INFORMATION_URL), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                // parse results as json array
                                JSONArray allSchoolsAsJSONArray = getResultsAsJSONArray(response);

                                parseSchoolJSONArrayAndStoreInDatabase(database, allSchoolsAsJSONArray);

                                // notify that the database was created and it's ready to be used
                                database.setDatabaseCreated();

                                // add geolocation of schools
                                List<SchoolEntity> allSchools = database.SchoolModel().loadAllSchoolsAsList();
                                for (SchoolEntity school : allSchools) {
                                    addGeocodingForSchool(school, queue);
                                }
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

            // create the JSON request for CCAs offered by each school
            JsonObjectRequest schoolToCCAJsonRequest = new JsonObjectRequest
                    (Request.Method.GET, appContext.getString(R.string.CCAS_OFFERED_URL), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // parse results as json array
                                JSONArray allSchoolToCCA_AsJSONArray = getResultsAsJSONArray(response);

                                // get each entry in results and store in database
                                parseSchoolToCCAJSONArrayAndStoreInDatabase(database, allSchoolToCCA_AsJSONArray);
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

            // create the JSON request for courses offered by each school
            JsonObjectRequest schoolToCourseJsonRequest = new JsonObjectRequest
                    (Request.Method.GET, appContext.getString(R.string.SUBJECTS_OFFERED_URL), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // parse results as json array
                                JSONArray allSchoolToCourseAsJSONArray = getResultsAsJSONArray(response);

                                // get each entry in results and store in database
                                parseSchoolToCourseJSONArrayAndStoreInDatabase(database, allSchoolToCourseAsJSONArray);
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
            // Access the RequestQueue through your singleton class. Add each request into the queue.
            queue.add(schoolGeneralInfoJsonRequest);
            queue.add(schoolToCourseJsonRequest);
            queue.add(schoolToCCAJsonRequest);


        }

        // geocode school
        private void addGeocodingForSchool(SchoolEntity school, RequestQueue queue) {
            String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";
            JsonObjectRequest geoCodingRequest =
                    new JsonObjectRequest(
                            Request.Method.GET,
                            BASE_URL +
                                    "?address=" +
                                    school.physicalAddress +
                                    "&key=" +
                                    appContext.getString(R.string.google_geocoding_key) +
                                    "&region=sg",
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONObject location =
                                                response.getJSONArray("results")
                                                        .getJSONObject(0)
                                                        .getJSONObject("geometry")
                                                        .getJSONObject("location");
                                        Double latitude = location.getDouble("lat");
                                        Double longitude = location.getDouble("lng");
                                        school.latitude = latitude;
                                        school.longitude = longitude;
                                        database.SchoolModel().updateSchool(school);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    System.out.println("Rabak la bro: " + error.toString());
                                }
                            });
            queue.add(geoCodingRequest);
        }

        // this parsing format is specific to data.gov.sg's API
        private JSONArray getResultsAsJSONArray(JSONObject rawJson) throws JSONException {
            return rawJson.getJSONObject("result").getJSONArray("records");
        }

        private void parseSchoolJSONArrayAndStoreInDatabase(final AppDatabase database,
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

        private void parseSchoolToCCAJSONArrayAndStoreInDatabase(final AppDatabase database,
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

        private void parseSchoolToCourseJSONArrayAndStoreInDatabase(final AppDatabase database,
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
}