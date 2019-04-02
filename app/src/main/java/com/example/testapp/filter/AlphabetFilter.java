package com.example.testapp.filter;

import com.example.testapp.db.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.List;

public class AlphabetFilter implements Filter{
    private List<SchoolEntity> beforeSort;
    private List<SchoolEntity> afterSort;

    public AlphabetFilter(List<SchoolEntity> beforeSort) {
        this.beforeSort = beforeSort;
    }

    public List<SchoolEntity> getSorted() {
        alphabetSort();
        return afterSort;
    }

    private void alphabetSort() {
        List<SchoolEntity> sorted = new ArrayList<SchoolEntity>();
        for (int i = 0; i < beforeSort.size(); i++) {
            int j;
            for (j = 0; j < i; j++) {
                if (beforeSort.get(i).getSchoolName().compareTo(sorted.get(j).getSchoolName()) < 0)
                    break;
            }
            sorted.add(j, beforeSort.get(i));
        }
        afterSort = sorted;
    }
}
