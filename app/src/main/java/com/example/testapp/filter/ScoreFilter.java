package com.example.testapp.filter;

import com.example.testapp.DataRepository;
import com.example.testapp.db.entity.PreUniversitySchool;
import com.example.testapp.db.entity.SchoolEntity;
import com.example.testapp.db.entity.SecondarySchool;

import java.util.List;

public class ScoreFilter implements Filter{
    final int level;
    private DataRepository repo;
    private List<SchoolEntity> beforeSort;
    private List<SchoolEntity> afterSort;
    private List entitiesWithScore;

    public ScoreFilter(List<SchoolEntity> beforeSort, int level) {
        this.beforeSort = beforeSort;
        this.level = level;


    }

    public List<SchoolEntity> getSorted() {
        scoreSort();
        return afterSort;
    }

    private void scoreSort() {
        if (level == 2) {
            for (SchoolEntity sekolah : beforeSort) {
                try {
                    SecondarySchool secondarySchool = repo.getSecondarySchool(sekolah.schoolName);
                    entitiesWithScore.add(secondarySchool);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (level == 3) {
            for (SchoolEntity sekolah : beforeSort) {
                try {
                    PreUniversitySchool preUniversitySchool = repo.getPreUniversitySchool(sekolah.schoolName);
                    entitiesWithScore.add(preUniversitySchool);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        afterSort = sorted;
    }
}
