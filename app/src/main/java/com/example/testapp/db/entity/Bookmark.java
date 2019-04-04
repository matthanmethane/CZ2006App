package com.example.testapp.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * An entity denoting a bookmark by the user
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = SchoolEntity.class,
                parentColumns = "schoolName",
                childColumns = "schoolName")
},
        primaryKeys = {"schoolName"}
)
public class Bookmark {
    /**
     * The SchoolEntity name.
     */
    @NonNull
    @ColumnInfo(name = "schoolName")
    private String schoolName;

    /**
     * Instantiates a new Bookmark
     *
     * @param schoolName the name of the bookmarked school
     */
    public Bookmark(String schoolName) {
        this.schoolName = schoolName;
    }

    @NonNull
    public String getSchoolName() {
        return schoolName;
    }
}
