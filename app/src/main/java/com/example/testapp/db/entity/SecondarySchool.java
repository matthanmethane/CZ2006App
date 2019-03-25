package com.example.testapp.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * The Secondary SchoolEntity entity, which should be a subclass of SchoolEntity.
 */
@Entity(foreignKeys = {
        @ForeignKey(entity = SchoolEntity.class,
                parentColumns = "schoolName",
                childColumns = "school_name")
})
public class SecondarySchool {
    /**
     * The SchoolEntity name.
     */
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "school_name")
    public String schoolName;

    /**
     * The Psle integrated program score.
     */
    public int PSLEIntegratedProgramScore;

    /**
     * The Psle express score.
     */
    public int PSLEExpressScore;

    /**
     * The Psle express affiliation score.
     */
    public int PSLEExpressAffiliationScore;

    /**
     * The Psle normal academic score.
     */
    public int PSLENormalAcademicScore;

    /**
     * The Psle normal technical score.
     */
    public int PSLENormalTechnicalScore;

    /**
     * Instantiates a new Secondary school.
     *
     * @param schoolName                  the school name
     * @param PSLEIntegratedProgramScore  the psle integrated program score
     * @param PSLEExpressScore            the psle express score
     * @param PSLEExpressAffiliationScore the psle express affiliation score
     * @param PSLENormalAcademicScore     the psle normal academic score
     * @param PSLENormalTechnicalScore    the psle normal technical score
     */
    public SecondarySchool(String schoolName,
                           int PSLEIntegratedProgramScore,
                           int PSLEExpressScore,
                           int PSLEExpressAffiliationScore,
                           int PSLENormalAcademicScore,
                           int PSLENormalTechnicalScore) {
        this.schoolName = schoolName;
        this.PSLEIntegratedProgramScore = PSLEIntegratedProgramScore;
        this.PSLEExpressScore = PSLEExpressScore;
        this.PSLEExpressAffiliationScore = PSLEExpressAffiliationScore;
        this.PSLENormalAcademicScore = PSLENormalAcademicScore;
        this.PSLENormalTechnicalScore = PSLENormalTechnicalScore;
    }
}
