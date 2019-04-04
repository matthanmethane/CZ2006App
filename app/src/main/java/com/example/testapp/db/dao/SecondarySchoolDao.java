package com.example.testapp.db.dao;

import com.example.testapp.db.entity.SecondarySchool;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * The interface Secondary school dao.
 */
@Dao
public interface SecondarySchoolDao {
    /**
     * Get a secondary school by its name.
     */
    @Query("SELECT * FROM SecondarySchool WHERE schoolName LIKE :name")
    SecondarySchool getSecondarySchool(String name);
    /**
     * Insert secondary school.
     *
     * @param secondarySchool the secondary school
     */
    @Insert(onConflict = IGNORE)
    void insertSecondarySchool(SecondarySchool secondarySchool);

    /**
     * Update secondary school.
     *
     * @param secondarySchool the secondary school
     */
    @Update(onConflict = REPLACE)
    void updateSecondarySchool(SecondarySchool secondarySchool);

    /**
     * Delete all.
     */
    @Query("DELETE FROM SecondarySchool")
    void deleteAll();
}
