/*
The application class which is first initialized when the application is created.

Adapted from https://github.com/googlesamples/android-architecture-components/tree/master/BasicSample

AppExecutors is a derived class which represents a task in the application.

 */
package com.example.testapp;

import android.app.Application;

import com.example.testapp.db.AppDatabase;

/**
 * The main reason why this extension of the Application class exists is to initialize the database when the application first starts.
 * <p>
 * Android knows that you have a custom Application class when you specify the android:name property in the <application> node
 * in AndroidManifest.xml.
 * <p>
 * Note: never store mutable shared data inside the Application object - that data might disappear or become invalid at anytime,
 * so store them in persistence strategies.
 * <p>
 * For more notes on the parent Application class, please see:
 * https://github.com/codepath/android_guides/wiki/Understanding-the-Android-Application-Class
 */
public class EmpathyApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    /**
     * Gets database.
     *
     * @return the database
     */
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    /**
     * Gets repository.
     *
     * @return the repository
     */
    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
