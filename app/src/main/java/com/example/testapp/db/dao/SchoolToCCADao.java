package com.example.testapp.db.dao;

import com.example.testapp.db.entity.SchoolToCCA;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * The interface SchoolEntity to cca dao.
 */
@Dao
public interface SchoolToCCADao {

    /**
     * Gets the list of CCAs associated with a school using the school name.
     * @param schoolName
     * @return
     */
    @Query("SELECT * FROM SchoolToCCA " +
            "WHERE SchoolToCCA.school_name = :schoolName")
    LiveData<List<SchoolToCCA>> getCCAsOfASchool(String schoolName);

    /**
     * Insert school to cca.
     *
     * @param schoolToCCA the school to cca
     */
    @Insert(onConflict = IGNORE)
    void insertSchoolToCCA(SchoolToCCA schoolToCCA);

    /**
     * Update school to cca.
     *
     * @param schoolToCCA the school to cca
     */
    @Update(onConflict = REPLACE)
    void updateSchoolToCCA(SchoolToCCA schoolToCCA);

    /**
     * Delete all.
     */
    @Query("DELETE FROM SchoolToCCA")
    void deleteAll();
}
