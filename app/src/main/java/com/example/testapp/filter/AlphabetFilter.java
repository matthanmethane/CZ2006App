package com.example.testapp.filter;

import com.example.testapp.db.entity.SchoolEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to sort schools in alphabetical order.
 */
public class AlphabetFilter implements Filter{
    private List<SchoolEntity> beforeSort;
    private List<SchoolEntity> afterSort;

    /**
     * Class constructor.
     * @param beforeSort list of schools before sorted
     */
    public AlphabetFilter(List<SchoolEntity> beforeSort) {
        this.beforeSort = beforeSort;
    }

    /**
     * Get list of sorted schools in alphabetical order.
     * @return list of sorted schools
     */
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
