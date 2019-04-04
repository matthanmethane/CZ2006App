package com.example.testapp.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The type Pre university school. Consists of JC, Poly and Millenia Institute. Subclass of SchoolEntity.
 */
@Entity
public class PreUniversitySchool {
    /**
     * The SchoolEntity name.
     */
    @PrimaryKey
    @NonNull
    public String schoolName;

    /**
     * The Entry score 1.
     */
// L1R5/L1B4 for most courses / JCs Science Stream
    @NonNull
    public int scienceStreamScore;

    /**
     * The Entry score 2.
     */
// L1R5 requirements for arts stream if JC
    public int artsStreamScore;

    /**
     * Instantiates a new Pre university school.
     *
     * @param schoolName  the school name
     * @param scienceStreamScore the entry score 1
     * @param artsStreamScore the entry score 2
     */
    public PreUniversitySchool(String schoolName,
                               int scienceStreamScore,
                               int artsStreamScore) {
        this.schoolName = schoolName;
        this.scienceStreamScore = scienceStreamScore;
        this.artsStreamScore = artsStreamScore;
    }
}
