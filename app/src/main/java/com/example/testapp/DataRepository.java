package com.example.testapp;

import android.content.Context;

import com.example.testapp.db.AppDatabase;
import com.example.testapp.db.entity.Bookmark;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.db.entity.SchoolToCCA;
import com.example.testapp.db.entity.SchoolToCourse;

import java.util.List;

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
     * Get the list of schools from the database based on the search pattern specified by the user.
     *
     * @param pattern the pattern
     * @param entryScore
     * @return the schools by search pattern
     */
    public List<SchoolEntity> findSchools(String pattern, List<String> ccas, List<String> courses, int schoolLevel, int entryScore) {
        if (ccas.size() > 0 && courses.size() > 0)
        {
            if (schoolLevel == 1)
                return mDatabase.SchoolModel().getPrimarySchoolsBySearchPattern("%" + pattern + "%", ccas, ccas.size(), courses, courses.size());
            else if (schoolLevel == 2)
                return mDatabase.SchoolModel().getSecondarySchoolsBySearchPattern("%" + pattern + "%", ccas, ccas.size(), courses, courses.size(), entryScore);
            else if (schoolLevel == 3)
                return mDatabase.SchoolModel().getJuniorCollegesBySearchPattern("%" + pattern + "%", ccas, ccas.size(), courses, courses.size(), entryScore);
        }

        else if (ccas.size() > 0)
        {
            if (schoolLevel == 1)
                return mDatabase.SchoolModel().getPrimarySchoolsByNameAndCCAs("%" + pattern + "%", ccas, ccas.size());
            else if (schoolLevel == 2)
                return mDatabase.SchoolModel().getSecondarySchoolsByNameAndCCAs("%" + pattern + "%", ccas, ccas.size(), entryScore);
            else if (schoolLevel == 3)
                return mDatabase.SchoolModel().getJuniorCollegesByNameAndCCAs("%" + pattern + "%", ccas, ccas.size(), entryScore);
        }

        else if (courses.size() > 0)
        {
            if (schoolLevel == 1)
                return mDatabase.SchoolModel().getPrimarySchoolsByNameAndCourses("%" + pattern + "%", courses, courses.size());
            else if (schoolLevel == 2)
                return mDatabase.SchoolModel().getSecondarySchoolsByNameAndCourses("%" + pattern + "%", courses, courses.size(), entryScore);
            else if (schoolLevel == 3)
                return mDatabase.SchoolModel().getJuniorCollegesByNameAndCourses("%" + pattern + "%", courses, courses.size(), entryScore);
        }

        else
        {
            if (schoolLevel == 1)
                return mDatabase.SchoolModel().getPrimarySchoolsByName("%" + pattern + "%");
            else if (schoolLevel == 2)
                return mDatabase.SchoolModel().getSecondarySchoolsByName("%" + pattern + "%", entryScore);
            else if (schoolLevel == 3)
                return mDatabase.SchoolModel().getJuniorCollegesByName("%" + pattern + "%", entryScore);
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
     * Get all bookmarks in the database.
     */
    public List<Bookmark> getBookmarks()
    {
        return mDatabase.BookmarkModel().getBookmarks();
    }

    /**
     * Add a new bookmark to the database, based on the school name provided
     * @param schoolName
     */
    public void addNewBookmark(String schoolName)
    {
        Bookmark newBookmark = new Bookmark(schoolName);
        mDatabase.BookmarkModel().insertBookmark(newBookmark);
    }

    public void deleteBookmark(String schoolName) {
        Bookmark newBookmark = new Bookmark(schoolName);
        mDatabase.BookmarkModel().deleteBookmark(newBookmark);
    }

    public boolean isBookmark(String schoolName) {
        Bookmark result = mDatabase.BookmarkModel().getBookmark(schoolName);
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get the CCAs for a school.
     *
     * @param schoolName
     * @return
     */
    public List<SchoolToCCA> getSchoolCCAs(String schoolName) {
        return mDatabase.SchoolToCCAModel().getSchoolCCAs(schoolName);
    }

    /**
     * Get the Courses offered by a school.
     *
     * @param schoolName
     * @return
     */
    public List<SchoolToCourse> getSchoolCourses(String schoolName) {
        return mDatabase.SchoolToCourseModel().getSchoolCourses(schoolName);
    }

    /**
     * Get all CCAs offered in that education level
     * @param level
     * @return
     */
    public List<String> getCCAsForLevel(int level) {
        if (level == 1) {
            return mDatabase.SchoolToCCAModel().getPrimarySchoolCCAs();
        } else if (level == 2) {
            return mDatabase.SchoolToCCAModel().getSecondarySchoolCCAs();
        } else if (level == 3) {
            return mDatabase.SchoolToCCAModel().getJuniorCollegeCCAs();
        }
        return null;
    }

    /**
     * Get all Courses offered in that education level
     * @param schoolLevel
     * @return
     */
    public List<String> getCoursesForLevel(int schoolLevel) {
        if (schoolLevel == 1) {
            return mDatabase.SchoolToCourseModel().getPrimarySchoolCourses();
        } else if (schoolLevel == 2) {
            return mDatabase.SchoolToCourseModel().getSecondarySchoolCourses();
        } else if (schoolLevel == 3) {
            return mDatabase.SchoolToCourseModel().getJuniorCollegeCourses();
        }
        return null;
    }
}
