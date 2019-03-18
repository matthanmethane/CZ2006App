//package com.example.testapp.db.views;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import androidx.room.DatabaseView;
//
///**
// * TODO: to be used for the SchoolFullTextbox
// */
//@DatabaseView("select * from SchoolEntity " +
//        "INNER JOIN SchoolToCCA on SchoolEntity.schoolName=SchoolToCCA.school_name " +
//        "INNER JOIN SchoolToCourse on SchoolEntity.schoolName=SchoolToCourse.school_name " +
//        "INNER JOIN SecondarySchool on SchoolEntity.schoolName=SecondarySchool.school_name ")
////        "WHERE SchoolEntity.name LIKE :name " +
////        "AND SchoolToCCA.cca_name IN (:CCAs) " +
////        "AND SchoolToCourse.course_name IN (:Courses)")
//public class SchoolDetails {
//    /**
//     * The Name.
//     */
//    public String name;
//
//    /**
//     * The Physical address.
//     */
//    public String physicalAddress;
//
//    /**
//     * The Postal code.
//     */
//    public int postalCode;
//
//    /**
//     * The Telephone number 1.
//     */
//    public String telephoneNumber1;
//
//    /**
//     * The Telephone number 2.
//     */
//    public String telephoneNumber2;
//
//    /**
//     * The Home page address.
//     */
//    public String homePageAddress;
//
//    /**
//     * The Email address.
//     */
//    public String emailAddress;
//
//    /**
//     * The Mission.
//     */
//    public String mission;
//
//    /**
//     * The Vision.
//     */
//    public String vision;
//
//    /**
//     * The SchoolEntity autonomy type.
//     */
//    public String schoolAutonomyType;
//
//    /**
//     * The SchoolEntity gender.
//     */
//    public String schoolGender;
//
//    /**
//     * The Sap school.
//     */
//    public int SAPSchool;
//
//    /**
//     * The Autonomous school.
//     */
//    public int autonomousSchool;
//
//    /**
//     * The Integrated program.
//     */
//    public int integratedProgram;
//
//    /**
//     * The Gifted education program offered.
//     */
//    public int giftedEducationProgramOffered;
//
//    /**
//     * The Zone code.
//     */
//    public String zoneCode;
//
//    /**
//     * The Cluster code.
//     */
//    public String clusterCode;
//
//    /**
//     * The CCAs the school has and the group they are in
//     */
//    public HashMap<String, ArrayList<String>> schoolDetails;
//}
