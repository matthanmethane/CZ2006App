package com.example.testapp.db.entity;

import com.example.testapp.db.model.School;

import androidx.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The type SchoolEntity. Main place to store data for all schools.
 */
@Entity
public class SchoolEntity implements School {
    /**
     * The Id.
     */
    @NonNull
    public int id; // need this so that SchoolsAdapter's RecycleView can get item id.

    /**
     * The SchoolEntity name.
     */
    @PrimaryKey
    @NonNull
    public String schoolName;

    /**
     * The Physical address.
     */
    @NonNull
    public String physicalAddress;

    /**
     * The Postal code.
     */
    @NonNull
    public int postalCode;

    /**
     * The Telephone number 1.
     */
    @NonNull
    public String telephoneNumber1;

    /**
     * The Telephone number 2.
     */
    public String telephoneNumber2;

    /**
     * The Home page address.
     */
    @NonNull
    public String homePageAddress;

    /**
     * The Email address.
     */
    @NonNull
    public String emailAddress;

    /**
     * The Mission.
     */
    @NonNull
    public String mission;

    /**
     * The Vision.
     */
    @NonNull
    public String vision;

    /**
     * The SchoolEntity autonomy type.
     */
    @NonNull
    public String schoolAutonomyType;

    /**
     * The SchoolEntity gender.
     */
    @NonNull
    public String schoolGender;

    /**
     * The Sap school.
     */
    @NonNull
    public int SAPSchool;

    /**
     * The Autonomous school.
     */
    @NonNull
    public int autonomousSchool;

    /**
     * The Integrated program.
     */
    @NonNull
    public int integratedProgram;

    /**
     * The Gifted education program offered.
     */
    @NonNull
    public int giftedEducationProgramOffered;

    /**
     * The Zone code.
     */
    @NonNull
    public String zoneCode;

    /**
     * The Cluster code.
     */
    @NonNull
    public String clusterCode;


    /**
     * Instantiates a new SchoolEntity.
     *
     * @param id                            the id
     * @param schoolName                    the school name
     * @param physicalAddress               the physical address
     * @param postalCode                    the postal code
     * @param telephoneNumber1              the telephone number 1
     * @param telephoneNumber2              the telephone number 2
     * @param homePageAddress               the home page address
     * @param emailAddress                  the email address
     * @param mission                       the mission
     * @param vision                        the vision
     * @param schoolAutonomyType            the school autonomy type
     * @param schoolGender                  the school gender
     * @param SAPSchool                     the sap school
     * @param autonomousSchool              the autonomous school
     * @param integratedProgram             the integrated program
     * @param giftedEducationProgramOffered the gifted education program offered
     * @param zoneCode                      the zone code
     * @param clusterCode                   the cluster code
     */
    public SchoolEntity(int id,
                        String schoolName,
                        String physicalAddress,
                        int postalCode,
                        String telephoneNumber1,
                        String telephoneNumber2,
                        String homePageAddress,
                        String emailAddress,
                        String mission,
                        String vision,
                        String schoolAutonomyType,
                        String schoolGender,
                        int SAPSchool,
                        int autonomousSchool,
                        int integratedProgram,
                        int giftedEducationProgramOffered,
                        String zoneCode,
                        String clusterCode) {
        this.id = id;
        this.schoolName = schoolName;
        this.physicalAddress = physicalAddress;
        this.postalCode = postalCode;
        this.telephoneNumber1 = telephoneNumber1;
        this.telephoneNumber2 = telephoneNumber2;
        this.homePageAddress = homePageAddress;
        this.emailAddress = emailAddress;
        this.mission = mission;
        this.vision = vision;
        this.schoolAutonomyType = schoolAutonomyType;
        this.schoolGender = schoolGender;
        this.SAPSchool = SAPSchool;
        this.autonomousSchool = autonomousSchool;
        this.integratedProgram = integratedProgram;
        this.giftedEducationProgramOffered = giftedEducationProgramOffered;
        this.zoneCode = zoneCode;
        this.clusterCode = clusterCode;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getSchoolName() { return this.schoolName; }
}