// code graveyard, leave this here first just in case

//package com.example.testapp.db.utils;
//
//import android.os.AsyncTask;
//import android.support.annotation.NonNull;
////import android.util.Log;
//
//import com.example.testapp.db.AppDatabase;
//import com.example.testapp.db.entity.SchoolEntity;
//import com.example.testapp.db.entity.SchoolToCCA;
//import com.example.testapp.db.entity.SchoolToCourse;
//import com.example.testapp.db.entity.SecondarySchool;
//
////import java.util.Date;
//
//public class DatabaseInitializer {
//
//    public static void populateSync(@NonNull final AppDatabase db) {
//        populateWithTestData(db);
//    }
//
//    private static CCA addCCA(final AppDatabase db, final String ccaName, final String ccaGroup) {
//        CCA cca = new CCA();
//        cca.name = ccaName;
//        cca.group = ccaGroup;
//        db.CCAModel().insertCCA(cca);
//        return cca;
//    }
//
//    private static Course addCourse(final AppDatabase db, final String courseName) {
//        Course course = new Course();
//        course.name = courseName;
//        db.CourseModel().insertCourse(course);
//        return course;
//    }
//
//    private static SchoolEntity addSchool(final AppDatabase db, final String schoolName,
//                                  final String physicalAddress, final int postalCode, final String telephoneNumber1,
//                                  final String telephoneNumber2, final String homePageAddress, final String emailAddress,
//                                  final String mission, final String vision, final String schoolAutonomyType,
//                                  final String schoolGender, final int SAPSchool, final int autonomousSchool,
//                                  final int integratedProgram, final int giftedEducationProgramOffered, final String zoneCode,
//                                  final String clusterCode) {
//        SchoolEntity school = new SchoolEntity();
//        school.name = schoolName;
//        school.physicalAddress = physicalAddress;
//        school.postalCode = postalCode;
//        school.telephoneNumber1 = telephoneNumber1;
//        school.telephoneNumber2 = telephoneNumber2;
//        school.homePageAddress = homePageAddress;
//        school.emailAddress = emailAddress;
//        school.mission = mission;
//        school.vision = vision;
//        school.schoolAutonomyType = schoolAutonomyType;
//        school.schoolGender = schoolGender;
//        school.SAPSchool = SAPSchool;
//        school.autonomousSchool = autonomousSchool;
//        school.integratedProgram = integratedProgram;
//        school.giftedEducationProgramOffered = giftedEducationProgramOffered;
//        school.zoneCode = zoneCode;
//        school.clusterCode = clusterCode;
//        db.SchoolModel().insertSchool(school);
//
//        return school;
//    };
//
//    private static SecondarySchool addSecondarySchool(final AppDatabase db, final SchoolEntity school,
//                                           int PSLEIntegratedProgramScore, int PSLEExpressScore,
//                                           int PSLEExpressAffiliationScore, int PSLENormalAcademicScore,
//                                           int PSLENormalTechnicalScore
//                                           ) {
//        SecondarySchool secondarySchool = new SecondarySchool();
//        secondarySchool.schoolName = school.name;
//        secondarySchool.PSLEExpressAffiliationScore = PSLEExpressAffiliationScore;
//        secondarySchool.PSLEExpressScore = PSLEExpressScore;
//        secondarySchool.PSLEIntegratedProgramScore = PSLEIntegratedProgramScore;
//        secondarySchool.PSLENormalAcademicScore = PSLENormalAcademicScore;
//        secondarySchool.PSLENormalTechnicalScore = PSLENormalTechnicalScore;
//        db.SecondarySchoolModel().insertSecondarySchool(secondarySchool);
//
//        return secondarySchool;
//    }
//
//    private static void addSchoolToCCA(final AppDatabase db, final SchoolEntity school, final CCA cca) {
//        SchoolToCCA schoolToCCA = new SchoolToCCA();
//        schoolToCCA.ccaName = cca.name;
//        schoolToCCA.schoolName = school.name;
//
//        db.SchoolToCCAModel().insertSchoolToCCA(schoolToCCA);
//    };
//
//    private static void addSchoolToCourse(final AppDatabase db, final SchoolEntity school, final Course course) {
//        SchoolToCourse schoolToCourse = new SchoolToCourse();
//        schoolToCourse.courseName = course.name;
//        schoolToCourse.schoolName = school.name;
//
//        db.SchoolToCourseModel().insertSchoolToCourse(schoolToCourse);
//    };
//
//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final AppDatabase mDb;
//
//        PopulateDbAsync(AppDatabase db) {
//            mDb = db;
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//            populateWithTestData(mDb);
//            return null;
//        }
//
//    }
//
//
//    private static void populateWithTestData(AppDatabase db) {
//        db.CCAModel().deleteAll();
//        db.CourseModel().deleteAll();
//        db.PreUniversitySchoolModel().deleteAll();
//        db.PrimarySchoolModel().deleteAll();
//        db.SecondarySchoolModel().deleteAll();
//        db.SchoolModel().deleteAll();
//        db.SchoolToCCAModel().deleteAll();
//        db.SchoolToCourseModel().deleteAll();
//
//        CCA cca = addCCA(db, "NCC(LAND)", "UNIFORMED GROUPS");
//        Course course = addCourse(db, "COMPUTING");
//        SchoolEntity school = addSchool(db, "CLEMENTI TOWN SECONDARY SCHOOL", "10 CLEMENTI AVE 3", 129904,
//                "67777362", "", "http://www.clementitownsec.moe.edu.sg",
//                "ctss@moe.edu.sg", "To develop in every Clementeen the passion to learn, the confidence to lead, and the humility to serve",
//                "To be a vibrant community of learners, with the heart to value every individual and the spirit to serve and to excel.",
//                "GOVERNMENT SCHOOL", "CO-ED SCHOOL", 0,
//                0, 0, 0, "WEST", "WEST1");
//        SecondarySchool secondarySchool = addSecondarySchool(db, school, -1, 230,
//                                        -1, 175, 69);
//        addSchoolToCCA(db, school, cca);
//        addSchoolToCourse(db, school, course);
//////        User user1 = addUser(db, "1", "Jason", "Seaver", 40);
//////        User user2 = addUser(db, "2", "Mike", "Seaver", 12);
//////        addUser(db, "3", "Carol", "Seaver", 15);
//////
//////        Book book1 = addBook(db, "1", "Dune");
//////        Book book2 = addBook(db, "2", "1984");
//////        Book book3 = addBook(db, "3", "The War of the Worlds");
//////        Book book4 = addBook(db, "4", "Brave New World");
//////        addBook(db, "5", "Foundation");
////        try {
////            // Loans are added with a delay, to have time for the UI to react to changes.
////
////            Date today = getTodayPlusDays(0);
////            Date yesterday = getTodayPlusDays(-1);
////            Date twoDaysAgo = getTodayPlusDays(-2);
////            Date lastWeek = getTodayPlusDays(-7);
////            Date twoWeeksAgo = getTodayPlusDays(-14);
////
////            addLoan(db, "1", user1, book1, twoWeeksAgo, lastWeek);
////            Thread.sleep(DELAY_MILLIS);
////            addLoan(db, "2", user2, book1, lastWeek, yesterday);
////            Thread.sleep(DELAY_MILLIS);
////            addLoan(db, "3", user2, book2, lastWeek, today);
////            Thread.sleep(DELAY_MILLIS);
////            addLoan(db, "4", user2, book3, lastWeek, twoDaysAgo);
////            Thread.sleep(DELAY_MILLIS);
////            addLoan(db, "5", user2, book4, lastWeek, today);
////            Log.d("DB", "Added loans");
////
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//    }
//}
