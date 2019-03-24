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

    private DataRepository(final AppDatabase database) { // same point as above. Notice how the constructor for DataRepository is private.
        mDatabase = database;
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
     * Get the list of all schools from the database.
     *
     * @return the schools
     */
    public LiveData<List<SchoolEntity>> getSchools() {
        System.out.println("From DataRepository: fetched all schools");
        return mDatabase.SchoolModel().loadAllSchoolsAsLiveData();
    }

    /**
     * Get list of primary schools
     */
    public LiveData<List<SchoolEntity>> getPrimarySchools() {
        System.out.println("From DataRepository: fetched all primary schools");
        return mDatabase.SchoolModel().getPrimarySchoolsAsLiveData();
    }

    /**
     * Get list of secondary schools
     */
    public LiveData<List<SchoolEntity>> getSecondarySchools() {
        System.out.println("From DataRepository: fetched all secondary schools");
        return mDatabase.SchoolModel().getSecondarySchoolsAsLiveData();
    }

    /**
     * Get list of junior colleges
     */
    public LiveData<List<SchoolEntity>> getJuniorColleges() {
        System.out.println("From DataRepository: fetched all junior colleges");
        return mDatabase.SchoolModel().getJuniorCollegesAsLiveData();
    }

    /**
     * Get the list of schools from the database based on the search pattern specified by the user.
     *
     * @param pattern the pattern
     * @return the schools by search pattern
     */
    public LiveData<List<SchoolEntity>> getSchoolsBySearchPattern(String pattern, List<String> ccas, List<String> courses, int schoolLevel) {
        if (ccas.size() > 0 && courses.size() > 0)
        {
            if (schoolLevel == 1)
                return mDatabase.SchoolModel().getPrimarySchoolsBySearchPattern("%" + pattern + "%", ccas, courses);
            else if (schoolLevel == 2)
                return mDatabase.SchoolModel().getSecondarySchoolsBySearchPattern("%" + pattern + "%", ccas, courses);
            else if (schoolLevel == 3)
                return mDatabase.SchoolModel().getJuniorCollegesBySearchPattern("%" + pattern + "%", ccas, courses);
        }
        else if (ccas.size() > 0)
        {
            if (schoolLevel == 1)
                return mDatabase.SchoolModel().getPrimarySchoolsByNameAndCCAs("%" + pattern + "%", ccas);
            else if (schoolLevel == 2)
                return mDatabase.SchoolModel().getSecondarySchoolsByNameAndCCAs("%" + pattern + "%", ccas);
            else if (schoolLevel == 3)
                return mDatabase.SchoolModel().getJuniorCollegesByNameAndCCAs("%" + pattern + "%", ccas);
        }
        else if (courses.size() > 0)
        {
            if (schoolLevel == 1)
                return mDatabase.SchoolModel().getPrimarySchoolsByNameAndCourses("%" + pattern + "%", courses);
            else if (schoolLevel == 2)
                return mDatabase.SchoolModel().getSecondarySchoolsByNameAndCourses("%" + pattern + "%", courses);
            else if (schoolLevel == 3)
                return mDatabase.SchoolModel().getJuniorCollegesByNameAndCourses("%" + pattern + "%", courses);
        }
        else
        {
            if (schoolLevel == 1)
                return mDatabase.SchoolModel().getPrimarySchoolsByName("%" + pattern + "%");
            else if (schoolLevel == 2)
                return mDatabase.SchoolModel().getSecondarySchoolsByName("%" + pattern + "%");
            else if (schoolLevel == 3)
                return mDatabase.SchoolModel().getJuniorCollegesByName("%" + pattern + "%");
        }

        return null;
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

    public List<String> getPrimarySchoolCCAs() {
        return mDatabase.SchoolToCCAModel().getPrimarySchoolCCAs();
    }

    public List<String> getSecondarySchoolCCAs() {
        return mDatabase.SchoolToCCAModel().getSecondarySchoolCCAs();
    }

    public List<String> getJuniorCollegeCCAs() {
        return mDatabase.SchoolToCCAModel().getJuniorCollegeCCAs();
    }

    public List<String> getAllCCAs() {
        return mDatabase.SchoolToCCAModel().getAllCCAs();
    }

    public List<String> getCourses(int schoolLevel) {
        if (schoolLevel == 1)
        {
            return mDatabase.SchoolToCourseModel().getPrimarySchoolCourses();
        } else if (schoolLevel == 2) {
            return mDatabase.SchoolToCourseModel().getSecondarySchoolCourses();
        } else if (schoolLevel == 3) {
            return mDatabase.SchoolToCourseModel().getJuniorCollegeCourses();
        }
        return null;
    }
}
