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
            "WHERE SchoolToCCA.school_name LIKE :schoolName")
    List<SchoolToCCA> getCCAsOfASchool(String schoolName);

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

    @Query("SELECT DISTINCT ccaName FROM SchoolToCCA " +
            "WHERE school_name IN " +
            "(SELECT schoolName FROM PrimarySchool)")
    List<String> getPrimarySchoolCCAs();

    @Query("SELECT DISTINCT ccaName FROM SchoolToCCA " +
            "WHERE school_name IN " +
            "(SELECT school_name FROM SecondarySchool)")
    List<String> getSecondarySchoolCCAs();

    @Query("SELECT DISTINCT ccaName FROM SchoolToCCA " +
            "WHERE school_name IN " +
            "(SELECT school_name FROM PreUniversitySchool)")
    List<String> getJuniorCollegeCCAs();

    @Query("SELECT DISTINCT ccaName FROM SchoolToCCA")
    List<String> getAllCCAs();
}
