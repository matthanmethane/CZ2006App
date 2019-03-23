package com.example.testapp;

import com.example.testapp.db.AppDatabase;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.db.entity.SchoolToCCA;
import com.example.testapp.db.entity.SchoolToCourse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

/**
 * Repository handling the work with each possible database type.
 * Utilizes the Strategy pattern so that if other sources of data is used,
 * upper level modules (activities, fragments etc) do not need to know about the
 * details regarding each source of data, because it only has to communicate with the DataRepository.
 */
public class DataRepository {

    private static DataRepository sInstance; // adhering to the Singleton pattern, only one instance of DataRepository can exist in the lifecycle of the application.

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<SchoolEntity>> mObservableSchools; // TODO: find out why MediatorLiveData is used

    private DataRepository(final AppDatabase database) { // same point as above. Notice how the constructor for DataRepository is private.
        mDatabase = database;
        mObservableSchools = new MediatorLiveData<>();

        // TODO: find out what does this block of code do
        mObservableSchools.addSource(mDatabase.SchoolModel().loadAllSchoolsAsLiveData(),
                schoolEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableSchools.postValue(schoolEntities);
                    }
                });
    }

    /**
     * Gets instance of the data repository. This method is important because Singleton pattern is used for DataRepository, which means
     * the constructor should only be called once in the lifecycle of the application.
     *
     * @param database the database
     * @return the instance
     */
    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of schools from the database and get notified when the data changes.
     * mObservableSchools gets typecasted to LiveData instead of MediatorLiveData.
     *
     * @return the schools
     */
    public LiveData<List<SchoolEntity>> getSchools() {
        return mObservableSchools;
    }

    /**
     * Get the list of schools from the database based on the search pattern specified by the user.
     *
     * @param pattern the pattern
     * @return the schools by search pattern
     */
    public LiveData<List<SchoolEntity>> getSchoolsBySearchPattern(String pattern) {
        return mDatabase.SchoolModel().findSchoolsByNamePattern("%" + pattern + "%");
    }

    /**
     * Get a school by its name. Must be an exact match.
     *
     * @param schoolName
     * @return
     */
    public SchoolEntity getSchool(String schoolName) throws Exception {
        SchoolEntity result = mDatabase.SchoolModel().findSchoolByName(schoolName);

        if (result == null) {
            throw new Exception("Cannot find a school with that name");
        } else {
            return mDatabase.SchoolModel().findSchoolByName(schoolName);
        }
    }


    /**
     * Get the CCAs for a school.
     *
     * @param schoolName
     * @return
     */
    public List<SchoolToCCA> getCCAsOfASchool(String schoolName) {
        return mDatabase.SchoolToCCAModel().getCCAsOfASchool(schoolName);
    }

    /**
     * Get the Courses offered by a school.
     *
     * @param schoolName
     * @return
     */
    public List<SchoolToCourse> getCoursesOfASchool(String schoolName) {
        return mDatabase.SchoolToCourseModel().getCoursesOfASchool(schoolName);
    }


    // TODO: make a method which returns the list of schools from the database based on search pattern and other criteria, such as level and CCAs.
//    public LiveData<List<String>> findSecondarySchoolNames(String schoolName, List<String> CCAs, List<String> Courses) {
//        return mDatabase.SchoolModel().findSecondarySchoolNamesUsingNameAndCCAsAndCourses(schoolName, CCAs, Courses);
//    }

}
