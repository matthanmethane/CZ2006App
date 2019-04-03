package com.example.testapp.filter;

import com.example.testapp.DataRepository;
import com.example.testapp.db.entity.PreUniversitySchool;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.db.entity.SecondarySchool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreFilter implements Filter{
    final int level;
    private DataRepository repo;
    private List<SchoolEntity> beforeSort;
    private List<SchoolEntity> afterSort = new ArrayList<>();

    public ScoreFilter(List<SchoolEntity> beforeSort, int level, DataRepository repo) {
        this.repo = repo;
        this.beforeSort = beforeSort;
        this.level = level;
    }

    public List<SchoolEntity> getSorted() {
        scoreSort();
        return afterSort;
    }

    private void scoreSort() {
        if (level == 2) {
            List<SecondarySchool> entitiesWithScore  = new ArrayList<SecondarySchool>();
            for (SchoolEntity sekolah : beforeSort) {
                try {
                    System.out.println("From scoreSort:" + sekolah.schoolName);
                    SecondarySchool secondarySchool = repo.getSecondarySchool(sekolah.schoolName);
                    System.out.println("Alamak, getting secondary school: " + secondarySchool.schoolName + " what");
                    entitiesWithScore.add(secondarySchool);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            entitiesWithScore.sort(new SecondarySchoolComparator());
            for (SecondarySchool sekolah: entitiesWithScore) {
                try {
                    afterSort.add(repo.getSchool(sekolah.schoolName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (level == 3) {
            List<PreUniversitySchool> entitiesWithScore  = new ArrayList<PreUniversitySchool>();

            for (SchoolEntity sekolah : beforeSort) {
                try {
                    PreUniversitySchool preUniversitySchool = repo.getPreUniversitySchool(sekolah.schoolName);
                    entitiesWithScore.add(preUniversitySchool);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            entitiesWithScore.sort(new PreUniversitySchoolComparator());
            for (PreUniversitySchool sekolah : entitiesWithScore) {
                try {
                    afterSort.add(repo.getSchool(sekolah.schoolName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
};

/**
 * Get the maximum grade for each school (compare across all possible scoring requirements),
 * then compare with that of another school.
 */
class SecondarySchoolComparator implements Comparator  {

    @Override
    public int compare(Object secSchlOne, Object secSchlTwo) {
        List<Integer> secSchlOneGrades = new ArrayList<>();
        secSchlOneGrades.add(((SecondarySchool) secSchlOne).PSLEIntegratedProgramScore);
        secSchlOneGrades.add(((SecondarySchool) secSchlOne).PSLEExpressScore);
        secSchlOneGrades.add(((SecondarySchool) secSchlOne).PSLEExpressAffiliationScore);
        secSchlOneGrades.add(((SecondarySchool) secSchlOne).PSLENormalAcademicScore);
        secSchlOneGrades.add(((SecondarySchool) secSchlOne).PSLENormalTechnicalScore);
        Integer secSchlOneMaxGrade = Collections.max(secSchlOneGrades);
        List<Integer> secSchlTwoGrades = new ArrayList<>();
        secSchlTwoGrades.add(((SecondarySchool) secSchlTwo).PSLEIntegratedProgramScore);
        secSchlTwoGrades.add(((SecondarySchool) secSchlTwo).PSLEExpressScore);
        secSchlTwoGrades.add(((SecondarySchool) secSchlTwo).PSLEExpressAffiliationScore);
        secSchlTwoGrades.add(((SecondarySchool) secSchlTwo).PSLENormalAcademicScore);
        secSchlTwoGrades.add(((SecondarySchool) secSchlTwo).PSLENormalTechnicalScore);
        Integer secSchlTwoMaxGrade = Collections.max(secSchlTwoGrades);

        return secSchlOneMaxGrade.compareTo(secSchlTwoMaxGrade)*-1;  // * - 1 to reflect how higher PSLE score means school is better
    }
}

/**
 * Get the minimum L1R5 required for each school first (comparing across Science and Art streams), then
 * find which school has the lower L1R5. The lower the L1R5, the 'bigger' the school is.
 */
class PreUniversitySchoolComparator implements Comparator {

    @Override
    public int compare(Object preUniSchlOne, Object preUniSchlTwo) {
        Integer preUniSchlOneMinGrade = Math.min(   ((PreUniversitySchool) preUniSchlOne).scienceStreamScore,
                                                    ((PreUniversitySchool) preUniSchlOne).artsStreamScore  );
        Integer preUniSchlTwoMinGrade = Math.min(   ((PreUniversitySchool) preUniSchlTwo).scienceStreamScore,
                                                    ((PreUniversitySchool) preUniSchlTwo).artsStreamScore  );
        return preUniSchlOneMinGrade.compareTo(preUniSchlTwoMinGrade);
    }
}
