package com.example.testapp.db.dao;


import com.example.testapp.db.entity.PreUniversitySchool;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * The interface Pre university school dao.
 */
@Dao
public interface PreUniversitySchoolDao {
    /**
     * Get pre university school.
     */
    @Query("SELECT * FROM PreUniversitySchool WHERE schoolName LIKE :schoolName")
    PreUniversitySchool getPreUniversity(String schoolName);

    /**
     * Insert pre university school.
     *
     * @param preUniversitySchool the pre university school
     */
    @Insert(onConflict = IGNORE)
    void insertPreUniversitySchool(PreUniversitySchool preUniversitySchool);

    /**
     * Update pre university school.
     *
     * @param preUniversitySchool the pre university school
     */
    @Update(onConflict = REPLACE)
    void updatePreUniversitySchool(PreUniversitySchool preUniversitySchool);

    /**
     * Delete all.
     */
    @Query("DELETE FROM PreUniversitySchool")
    void deleteAll();
}
