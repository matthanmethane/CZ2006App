package com.example.testapp.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * An entity that maps Schools to CCAs (many to many relationship)
 */
@Entity(primaryKeys = {"school_name", "ccaName"})
public class SchoolToCCA {
    /**
     * The SchoolEntity name.
     */
    @NonNull
    @ColumnInfo(name = "school_name")
    public String schoolName;

    /**
     * The Cca name.
     */
    @NonNull
    public String ccaName;

    /**
     * The Cca group.
     */
    @NonNull
    public String ccaGroup;

    /**
     * Instantiates a new SchoolEntity to cca.
     *
     * @param schoolName the school name
     * @param ccaName    the cca name
     * @param ccaGroup   the cca group
     */
    public SchoolToCCA(String schoolName,
                       String ccaName,
                       String ccaGroup) {
        this.schoolName = schoolName;
        this.ccaName = ccaName;
        this.ccaGroup = ccaGroup;
    }
}
