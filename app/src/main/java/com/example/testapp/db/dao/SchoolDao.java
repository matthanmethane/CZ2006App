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
    LiveData<List<SchoolEntity>> loadAllSchools();

    // prototyping methods. Rmb to throw away
    @Query("SELECT * FROM SchoolEntity WHERE id=1")
    LiveData<SchoolEntity> loadARandomSchool();

    @Query("SELECT * FROM SchoolEntity WHERE id=1")
    SchoolEntity loadARandomSchoolAsEntity();
    // end of prototyping methods

//    @Query("SELECT schoolName FROM SchoolEntity ")
//    LiveData<List<String>> loadAllSchools();

//    @Query("select schoolName from SchoolEntity where schoolName LIKE :pattern")
//    LiveData<List<String>> findSchoolNamesByNamePattern(String pattern);

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
}
