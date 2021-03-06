package com.example.testapp.db.dao;

import com.example.testapp.db.entity.SchoolToCCA;

import java.util.List;

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
     *
     * @param schoolName
     * @return
     */
    @Query("SELECT * FROM SchoolToCCA " +
            "WHERE SchoolToCCA.schoolName LIKE :schoolName")
    List<SchoolToCCA> getSchoolCCAs(String schoolName);

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

    /**
     * Get all primary school CCAs.
     * @return list of primary school CCAs
     */
    @Query("SELECT DISTINCT ccaName FROM SchoolToCCA " +
            "WHERE schoolName IN " +
            "(SELECT schoolName FROM PrimarySchool)")
    List<String> getPrimarySchoolCCAs();

    /**
     * Get all secondary school CCAs.
     * @return list of secondary school CCAs
     */
    @Query("SELECT DISTINCT ccaName FROM SchoolToCCA " +
            "WHERE schoolName IN " +
            "(SELECT schoolName FROM SecondarySchool)")
    List<String> getSecondarySchoolCCAs();

    /**
     * Get all junior college CCAs.
     * @return list of junior college CCAs
     */
    @Query("SELECT DISTINCT ccaName FROM SchoolToCCA " +
            "WHERE schoolName IN " +
            "(SELECT schoolName FROM PreUniversitySchool)")
    List<String> getJuniorCollegeCCAs();
}
