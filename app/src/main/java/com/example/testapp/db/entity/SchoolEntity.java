package com.example.testapp.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * The type SchoolEntity. Main place to store data for all schools.
 */
@Entity
public class SchoolEntity {
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
     * The longitude of the school - instantiated only when required
     */
    public double longitude;

    /**
     * The latitude of the school - instantiated only when required
     */
    public double latitude;

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
    public int getId() {
        return this.id;
    }

    /**
     * Get school name.
     * @return school name
     */
    public String getSchoolName() {
        return this.schoolName;
    }

    /**
     * Get school's physical address.
     * @return physical address
     */
    public String getPhysicalAddress() { return  this.physicalAddress;}

    /**
     * Get school's main telephone number.
     * @return main telephone number
     */
    public String getTelephoneNumber1() {return this.telephoneNumber1;}

    /**
     * Get school's alternative telephone number.
     * @return alternative telephone number
     */
    public String getTelephoneNumber2() {return  this.telephoneNumber2;}

    /**
     * Get school's website url.
     * @return website url
     */
    public String getHomePageAddress() {return  this.homePageAddress;}

    /**
     * Get school's email address.
     * @return email address
     */
    public String getEmailAddress() {return this.emailAddress;}

    /**
     * Get school's mission.
     * @return mission
     */
    public String getMission() {return this.mission;}

    /**
     * Get school's vision.
     * @return vision
     */
    public String getVision() {return this.vision;}

    /**
     * Get school's autonomy type.
     * @return autonomy type
     */
    public String getSchoolAutonomyType() {return this.schoolAutonomyType;}

    /**
     * Get school's gender intake type.
     * @return gender
     */
    public String getSchoolGender() {return  this.schoolGender;}

    /**
     * Get school's zone code.
     * @return zone code
     */
    public String getZoneCode() {return this.zoneCode;}

    /**
     * Get school's cluster code.
     * @return cluster code
     */
    public String getClusterCode() {return  this.clusterCode;}

    /**
     * Get school's latitude.
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Get school's longitude.
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Get confirmation of whether school is autonomous.
     * @return autonomous
     */
    public int getAutonomousSchool() {
        return autonomousSchool;
    }

    /**
     * Get confirmation of whether the school offers gifted education program.
     * @return confirmation of gifted education program
     */
    public int getGiftedEducationProgramOffered() {
        return giftedEducationProgramOffered;
    }

    /**
     * Get confirmation of whether the school offers integrated program.
     * @return confirmation of integrated program
     */
    public int getIntegratedProgram() {
        return integratedProgram;
    }

    /**
     * Get school's postal code.
     * @return postal code
     */
    public int getPostalCode() {
        return postalCode;
    }

    /**
     * Get confirmation whether the school is a SAP school
     * @return confirmation of SAP
     */
    public int getSAPSchool() {
        return SAPSchool;
    }
}