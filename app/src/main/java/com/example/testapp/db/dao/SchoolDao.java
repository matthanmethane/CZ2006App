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
     * Get a list of all schools.
     * @return all schools in database
     */
    @Query("SELECT * FROM SchoolEntity ")
    List<SchoolEntity> loadAllSchoolsAsList();

    /**
     * Get a school using its school name. It must be an exact match.
     */
    @Query("select * from SchoolEntity " +
            "WHERE SchoolEntity.schoolName LIKE :schoolName")
    SchoolEntity findSchoolByName(String schoolName);

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

    /**
     * Search patterns
     * @param s
     * @param ccas
     * @param numberOfCCAs
     * @param courses
     * @param numberOfCourses
     * @return
     */
    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PrimarySchool) " +
            "AND schoolName IN (SELECT schoolName FROM " +
                                "(SELECT * FROM SchoolToCCA " +
                                " WHERE ccaName IN (:ccas)" +
                                " GROUP BY schoolName" +
                                " HAVING COUNT(*) = :numberOfCCAs))" +
            "AND schoolName IN (SELECT schoolName FROM " +
                                "(SELECT * FROM SchoolToCourse" +
                                " WHERE courseName IN (:courses)" +
                                " GROUP BY schoolName" +
                                " HAVING COUNT(*) = :numberOfCourses))" +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getPrimarySchoolsBySearchPattern(String s, List<String> ccas, int numberOfCCAs, List<String> courses, int numberOfCourses);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM SecondarySchool " +
                                    "WHERE " +
                                    "PSLEIntegratedProgramScore > :entryScore " +
                                    "OR PSLEExpressScore > :entryScore " +
                                    "OR PSLEExpressAffiliationScore > :entryScore " +
                                    "OR PSLENormalAcademicScore > :entryScore " +
                                    "OR PSLENormalTechnicalScore > :entryScore ) " +
            "AND schoolName IN (SELECT schoolName FROM " +
            "(SELECT * FROM SchoolToCCA " +
            " WHERE ccaName IN (:ccas)" +
            " GROUP BY schoolName" +
            " HAVING COUNT(*) = :numberOfCCAs))" +
            "AND schoolName IN (SELECT schoolName FROM " +
            "(SELECT * FROM SchoolToCourse" +
            " WHERE courseName IN (:courses)" +
            " GROUP BY schoolName" +
            " HAVING COUNT(*) = :numberOfCourses))" +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getSecondarySchoolsBySearchPattern(String s, List<String> ccas, int numberOfCCAs, List<String> courses, int numberOfCourses, int entryScore);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PreUniversitySchool" +
            "                       WHERE scienceStreamScore > :entryScore " +
            "                       OR artsStreamScore > :entryScore) " +
            "AND schoolName IN (SELECT schoolName FROM " +
            "(SELECT * FROM SchoolToCCA " +
            " WHERE ccaName IN (:ccas)" +
            " GROUP BY schoolName" +
            " HAVING COUNT(*) = :numberOfCCAs))" +
            "AND schoolName IN (SELECT schoolName FROM " +
            "(SELECT * FROM SchoolToCourse" +
            " WHERE courseName IN (:courses)" +
            " GROUP BY schoolName" +
            " HAVING COUNT(*) = :numberOfCourses))" +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getJuniorCollegesBySearchPattern(String s, List<String> ccas, int numberOfCCAs, List<String> courses, int numberOfCourses, int entryScore);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PrimarySchool) " +
            "AND schoolName IN (SELECT schoolName FROM " +
            "                   (SELECT * FROM SchoolToCCA" +
            "                    WHERE ccaName IN (:ccas)" +
            "                    GROUP BY schoolName" +
            "                    HAVING COUNT(*) = :numberOfCCAs))" +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getPrimarySchoolsByNameAndCCAs(String s, List<String> ccas, int numberOfCCAs);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM SecondarySchool " +
                                    "WHERE " +
                                    "PSLEIntegratedProgramScore > :entryScore " +
                                    "OR PSLEExpressScore > :entryScore " +
                                    "OR PSLEExpressAffiliationScore > :entryScore " +
                                    "OR PSLENormalAcademicScore > :entryScore " +
                                    "OR PSLENormalTechnicalScore > :entryScore ) " +
            "AND schoolName IN (SELECT schoolName FROM" +
            "                   (SELECT * FROM SchoolToCCA" +
            "                    WHERE ccaName IN (:ccas)" +
            "                    GROUP BY schoolName" +
            "                    HAVING COUNT(*) = :numberOfCCAs))" +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getSecondarySchoolsByNameAndCCAs(String s, List<String> ccas, int numberOfCCAs, int entryScore);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PreUniversitySchool" +
            "                       WHERE scienceStreamScore > :entryScore " +
            "                       OR artsStreamScore > :entryScore) " +
            "AND schoolName IN (SELECT schoolName FROM" +
            "                   (SELECT * FROM SchoolToCCA" +
            "                    WHERE ccaName IN (:ccas)" +
            "                    GROUP BY schoolName" +
            "                    HAVING COUNT(*) = :numberOfCCAs))" +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getJuniorCollegesByNameAndCCAs(String s, List<String> ccas, int numberOfCCAs, int entryScore);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PrimarySchool) " +
            "AND schoolName IN (SELECT schoolName FROM " +
            "                   (SELECT * FROM SchoolToCourse " +
            "                    WHERE courseName IN(:courses)" +
            "                    GROUP BY schoolName" +
            "                    HAVING COUNT(*) = :numberOfCourses))" +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getPrimarySchoolsByNameAndCourses(String s, List<String> courses, int numberOfCourses);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM SecondarySchool " +
            "                     WHERE " +
            "PSLEIntegratedProgramScore > :entryScore " +
            "OR PSLEExpressScore > :entryScore " +
            "OR PSLEExpressAffiliationScore > :entryScore " +
            "OR PSLENormalAcademicScore > :entryScore " +
            "OR PSLENormalTechnicalScore > :entryScore ) " +
            "AND schoolName IN (SELECT schoolName FROM " +
            "                   (SELECT * FROM SchoolToCourse " +
            "                    WHERE courseName IN(:courses)" +
            "                    GROUP BY schoolName" +
            "                    HAVING COUNT(*) = :numberOfCourses))" +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getSecondarySchoolsByNameAndCourses(String s, List<String> courses, int numberOfCourses, int entryScore);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PreUniversitySchool" +
            "                       WHERE " +
            "                       scienceStreamScore > :entryScore" +
            "                       OR artsStreamScore > :entryScore) " +
            "AND schoolName IN (SELECT schoolName FROM " +
            "                   (SELECT * FROM SchoolToCourse " +
            "                    WHERE courseName IN(:courses)" +
            "                    GROUP BY schoolName" +
            "                    HAVING COUNT(*) = :numberOfCourses))" +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getJuniorCollegesByNameAndCourses(String s, List<String> courses, int numberOfCourses, int entryScore);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PrimarySchool) " +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getPrimarySchoolsByName(String s);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM SecondarySchool" +
            "                       WHERE " +
            "PSLEIntegratedProgramScore > :entryScore " +
            "OR PSLEExpressScore > :entryScore " +
            "OR PSLEExpressAffiliationScore > :entryScore " +
            "OR PSLENormalAcademicScore > :entryScore " +
            "OR PSLENormalTechnicalScore > :entryScore ) " +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getSecondarySchoolsByName(String s, int entryScore);

    @Query("SELECT DISTINCT * FROM SchoolEntity " +
            "WHERE schoolName IN (SELECT schoolName FROM PreUniversitySchool" +
            "                       WHERE " +
            "                       scienceStreamScore > :entryScore" +
            "                       OR artsStreamScore > :entryScore) " +
            "AND schoolName LIKE :s")
    List<SchoolEntity> getJuniorCollegesByName(String s, int entryScore);
}
