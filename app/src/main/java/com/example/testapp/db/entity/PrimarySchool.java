package com.example.testapp.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * The type Primary school. Subclass of SchoolEntity.
 */
@Entity
public class PrimarySchool {
    /**
     * The SchoolEntity name.
     */
    @PrimaryKey
    @NonNull
    public String schoolName;

    /**
     * The Session code.
     */
    @NonNull
    public String sessionCode;

    /**
     * Instantiates a new Primary school.
     *
     * @param schoolName  the school name
     * @param sessionCode the session code
     */
    public PrimarySchool(String schoolName, String sessionCode) {
        this.schoolName = schoolName;
        this.sessionCode = sessionCode;
    }
}
