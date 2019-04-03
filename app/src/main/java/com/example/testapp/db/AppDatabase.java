package com.example.testapp.db;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.testapp.AppExecutors;
import com.example.testapp.R;
import com.example.testapp.db.dao.BookmarkDao;
import com.example.testapp.db.dao.PreUniversitySchoolDao;
import com.example.testapp.db.dao.PrimarySchoolDao;
import com.example.testapp.db.dao.SchoolDao;
import com.example.testapp.db.dao.SchoolToCCADao;
import com.example.testapp.db.dao.SchoolToCourseDao;
import com.example.testapp.db.dao.SecondarySchoolDao;
import com.example.testapp.db.entity.Bookmark;
import com.example.testapp.db.entity.PreUniversitySchool;
import com.example.testapp.db.entity.PrimarySchool;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.db.entity.SchoolToCCA;
import com.example.testapp.db.entity.SchoolToCourse;
import com.example.testapp.db.entity.SecondarySchool;
import com.example.testapp.db.utils.CCAFetcher;
import com.example.testapp.db.utils.CourseFetcher;
import com.example.testapp.db.utils.EntryScoreFetcher;
import com.example.testapp.db.utils.Fetcher;
import com.example.testapp.db.utils.GeneralInfoFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * The abstraction over the actual database. Used to register the database and all views and entities associated with it,
 * as well as all the data access objects (DAOs) for each entity.
 */
@Database(entities =
        {SchoolEntity.class, PreUniversitySchool.class,
                PrimarySchool.class, SchoolToCCA.class,
                SchoolToCourse.class, SecondarySchool.class,
                Bookmark.class
        }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "EmPathy DB v5";

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
     * The collection which serves as an interface to Bookmark objects.
     */
    public abstract BookmarkDao BookmarkModel();

    /**
     * Gets the single instance of the database.
     *
     * @param context   the context
     * @param executors the executors
     * @return the instance
     */
    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        System.out.println("CallingFromGetInstanceInAppDatabase");
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    System.out.println("BuildingInGetInstanceInAppDatabase");
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    executors.diskIO().execute(new SchoolDataUpdater(context.getApplicationContext(), sInstance));
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
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
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

            // Instantiate all requests
            List<JsonObjectRequest> allRequests = new ArrayList<>();

            // create the JSON request for school general information.
            // For this we should create a RequestFuture as well, so it blocks other requests before this is completed.
            JSONObject response = null;
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            GeneralInfoFetcher generalFetcher = new GeneralInfoFetcher();
            queue.add(generalFetcher.fetchData(database, future));

            // block the damn request

            try {

                response = future.get(10, TimeUnit.SECONDS); // Blocks for at most 10 seconds.
                JSONArray results = generalFetcher.getResultsAsJSONArray(response);
                generalFetcher.parseJSONArrayAndStoreInDatabase(database, results);

                // okay lets continue

                // create the JSON request for CCAs offered by each school
                Fetcher ccaFetcher = new CCAFetcher();
                queue.add(ccaFetcher.fetchData(database));

                // create the JSON request for courses offered by each schoool
                Fetcher courseFetcher = new CourseFetcher();
                queue.add(courseFetcher.fetchData(database));

                // create the JSON request for grades
                EntryScoreFetcher entryScoreFetcher = new EntryScoreFetcher();
                queue.add(entryScoreFetcher.fetchData(database));

                // add geolocation of schools
                List<SchoolEntity> allSchools = database.SchoolModel().loadAllSchoolsAsList();
                for (SchoolEntity school : allSchools) {
                    addGeocodingForSchool(school, queue);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }





            // notify that the database was created and it's ready to be used
            database.setDatabaseCreated();
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
    }
}