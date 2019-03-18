package com.example.testapp.db.dao;

import com.example.testapp.db.entity.SchoolToCourse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

/**
 * The interface SchoolEntity to course dao.
 */
@Dao
public interface SchoolToCourseDao {

    @Query("SELECT * FROM SchoolToCourse " +
            "WHERE SchoolToCourse.school_name = :schoolName")
    LiveData<List<SchoolToCourse>> getCoursesOfASchool(String schoolName);

    /**
     * Insert school to course.
     *
     * @param schoolToCourse the school to course
     */
    @Insert(onConflict = IGNORE)
    void insertSchoolToCourse(SchoolToCourse schoolToCourse);

    /**
     * Update school to course.
     *
     * @param schoolToCourse the school to course
     */
    @Update(onConflict = REPLACE)
    void updateSchoolToCourse(SchoolToCourse schoolToCourse);

    /**
     * Delete all.
     */
    @Query("DELETE FROM SchoolToCourse")
    void deleteAll();
}
