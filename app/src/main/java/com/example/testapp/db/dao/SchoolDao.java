package com.example.testapp.db.dao;


import com.example.testapp.db.entity.SchoolEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * The interface SchoolEntity dao.
 */
@Dao
public interface SchoolDao {
    /**
     * Load all schools live data.
     *
     * @return the live data
     */
    @Query("SELECT * FROM SchoolEntity ")
    LiveData<List<SchoolEntity>> loadAllSchoolsAsLiveData();

    @Query("SELECT * FROM SchoolEntity ")
    List<SchoolEntity> loadAllSchoolsAsList();

    @Query("SELECT longitude FROM SchoolEntity WHERE schoolName LIKE :pattern")
    Double getLongitudeOfSchool(String pattern);

    @Query("SELECT latitude FROM SchoolEntity WHERE schoolName LIKE :pattern")
    Double getLatitudeOfSchool(String pattern);

    @Query("SELECT * FROM SchoolEntity " +
            "WHERE schoolName IN " +
            " (SELECT schoolName FROM PrimarySchool)")
    LiveData<List<SchoolEntity>> getPrimarySchoolsAsLiveData();

    @Query("SELECT * FROM SchoolEntity " +
            "WHERE schoolName IN " +
            " (SELECT school_name FROM SecondarySchool)")
    LiveData<List<SchoolEntity>> getSecondarySchoolsAsLiveData();

    @Query("SELECT * FROM SchoolEntity " +
            "WHERE schoolName IN " +
            " (SELECT school_name FROM PreUniversitySchool)")
    LiveData<List<SchoolEntity>> getJuniorCollegesAsLiveData();

    /**
     * Find schools by name pattern live data.
     *
     * @param pattern the pattern
     * @return the live data
     */
    @Query("select * from SchoolEntity where schoolName LIKE :pattern")
    LiveData<List<SchoolEntity>> findSchoolsByNamePattern(String pattern);

    /**
     * Find secondary school names using name and cc as and courses live data.
     *
     * @param name    the name
     * @param CCAs    the cc as
     * @param Courses the courses
     * @return the live data
     */
    @Query("select schoolName from SchoolEntity " +
            "INNER JOIN SchoolToCCA on SchoolEntity.schoolName=SchoolToCCA.school_name " +
            "INNER JOIN SchoolToCourse on SchoolEntity.schoolName=SchoolToCourse.school_name " +
            "INNER JOIN SecondarySchool on SchoolEntity.schoolName=SecondarySchool.school_name " +
            "WHERE SchoolEntity.schoolName LIKE :name " +
            "AND SchoolToCCA.ccaName IN (:CCAs) " +
            "AND SchoolToCourse.courseName IN (:Courses)")
    LiveData<List<String>> findSecondarySchoolNamesUsingNameAndCCAsAndCourses(String name, List<String> CCAs, List<String> Courses);

    /**
     * Get a school using its school name. It must be an exact match.
     */
    @Query("select * from SchoolEntity " +
            "WHERE SchoolEntity.schoolName LIKE :schoolName")
    SchoolEntity findSchoolByName(String schoolName);

//    LiveData<SchoolEntity> findSchoolByName(String schoolName);

    /**
     * Insert schoolEntity.
     *
     * @param schoolEntity the schoolEntity
     */
    @Insert(onConflict = IGNORE)
    void insertSchool(SchoolEntity schoolEntity);

    /**
     * Update schoolEntity.
     *
     * @param schoolEntity the schoolEntity
     */
    @Update(onConflict = REPLACE)
    void updateSchool(SchoolEntity schoolEntity);

    /**
     * Delete all.
     */
    @Query("DELETE FROM SchoolEntity")
    void deleteAll();

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PrimarySchool) " +
            "AND schoolName IN (SELECT school_name FROM SchoolToCCA" +
            "                   WHERE ccaName IN (:ccas))" +
            "AND schoolName IN (SELECT school_name FROM SchoolToCourse " +
            "                   WHERE courseName IN(:courses))" +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getPrimarySchoolsBySearchPattern(String s, List<String> ccas, List<String> courses);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT school_name FROM SecondarySchool) " +
            "AND schoolName IN (SELECT school_name FROM SchoolToCCA" +
            "                   WHERE ccaName IN (:ccas))" +
            "AND schoolName IN (SELECT school_name FROM SchoolToCourse " +
            "                   WHERE courseName IN(:courses))" +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getSecondarySchoolsBySearchPattern(String s, List<String> ccas, List<String> courses);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT school_name FROM PreUniversitySchool) " +
            "AND schoolName IN (SELECT school_name FROM SchoolToCCA" +
            "                   WHERE ccaName IN (:ccas))" +
            "AND schoolName IN (SELECT school_name FROM SchoolToCourse " +
            "                   WHERE courseName IN(:courses))" +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getJuniorCollegesBySearchPattern(String s, List<String> ccas, List<String> courses);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PrimarySchool) " +
            "AND schoolName IN (SELECT school_name FROM SchoolToCCA" +
            "                   WHERE ccaName IN (:ccas))" +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getPrimarySchoolsByNameAndCCAs(String s, List<String> ccas);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT school_name FROM SecondarySchool) " +
            "AND schoolName IN (SELECT school_name FROM SchoolToCCA" +
            "                   WHERE ccaName IN (:ccas))" +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getSecondarySchoolsByNameAndCCAs(String s, List<String> ccas);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT school_name FROM PreUniversitySchool) " +
            "AND schoolName IN (SELECT school_name FROM SchoolToCCA" +
            "                   WHERE ccaName IN (:ccas))" +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getJuniorCollegesByNameAndCCAs(String s, List<String> ccas);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PrimarySchool) " +
            "AND schoolName IN (SELECT school_name FROM SchoolToCourse " +
            "                   WHERE courseName IN(:courses))" +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getPrimarySchoolsByNameAndCourses(String s, List<String> courses);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT school_name FROM SecondarySchool) " +
            "AND schoolName IN (SELECT school_name FROM SchoolToCourse " +
            "                   WHERE courseName IN(:courses))" +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getSecondarySchoolsByNameAndCourses(String s, List<String> courses);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT school_name FROM PreUniversitySchool) " +
            "AND schoolName IN (SELECT school_name FROM SchoolToCourse " +
            "                   WHERE courseName IN(:courses))" +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getJuniorCollegesByNameAndCourses(String s, List<String> courses);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PrimarySchool) " +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getPrimarySchoolsByName(String s);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT school_name FROM SecondarySchool) " +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getSecondarySchoolsByName(String s);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT school_name FROM PreUniversitySchool) " +
            "AND schoolName LIKE :s")
    LiveData<List<SchoolEntity>> getJuniorCollegesByName(String s);
}
