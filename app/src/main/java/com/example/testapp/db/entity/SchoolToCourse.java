package com.example.testapp.db.entity;

import androidx.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * An entity which maps SchoolEntity to course (many to many relationship)
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = SchoolEntity.class,
                parentColumns = "schoolName",
                childColumns = "school_name")
        },
        primaryKeys = {"school_name", "courseName"}
)
public class SchoolToCourse {
    /**
     * The SchoolEntity name.
     */
    @NonNull
    @ColumnInfo(name = "school_name")
    public String schoolName;

    /**
     * The Course name.
     */
    @NonNull
    public String courseName;

    /**
     * Instantiates a new SchoolEntity to course.
     *
     * @param schoolName the school name
     * @param courseName the course name
     */
    public SchoolToCourse(String schoolName,
                          String courseName)
    {
        this.schoolName = schoolName;
        this.courseName = courseName;
    }
}
