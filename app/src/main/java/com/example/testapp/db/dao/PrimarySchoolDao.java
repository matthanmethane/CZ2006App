package com.example.testapp.db.dao;

import com.example.testapp.db.entity.PrimarySchool;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * The interface Primary school dao.
 */
@Dao
public interface PrimarySchoolDao {
    /**
     * Insert primary school.
     *
     * @param primarySchool the primary school
     */
    @Insert(onConflict = IGNORE)
    void insertPrimarySchool(PrimarySchool primarySchool);

    /**
     * Update primary school.
     *
     * @param primarySchool the primary school
     */
    @Update(onConflict = REPLACE)
    void updatePrimarySchool(PrimarySchool primarySchool);

    /**
     * Delete all.
     */
    @Query("DELETE FROM PrimarySchool")
    void deleteAll();
}
