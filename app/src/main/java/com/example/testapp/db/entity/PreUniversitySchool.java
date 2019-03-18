package com.example.testapp.db.entity;

import androidx.annotation.NonNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * The type Pre university school. Consists of JC, Poly and Millenia Institute. Subclass of SchoolEntity.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = SchoolEntity.class,
                parentColumns = "schoolName",
                childColumns = "school_name")
})
public class PreUniversitySchool {
    /**
     * The SchoolEntity name.
     */
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="school_name")
    public String schoolName;

    /**
     * The Entry score 1.
     */
// L1R5/L1B4 for most courses / JCs Science Stream
    @NonNull
    public int entryScore1;

    /**
     * The Entry score 2.
     */
// L1R5 requirements for arts stream if JC
    public int entryScore2;

    /**
     * Instantiates a new Pre university school.
     *
     * @param schoolName  the school name
     * @param entryScore1 the entry score 1
     * @param entryScore2 the entry score 2
     */
    public PreUniversitySchool(String schoolName,
                               int entryScore1,
                               int entryScore2)
    {
        this.schoolName = schoolName;
        this.entryScore1 = entryScore1;
        this.entryScore2 = entryScore2;
    }
}
