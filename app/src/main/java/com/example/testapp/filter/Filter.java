package com.example.testapp.filter;

import com.example.testapp.db.entity.SchoolEntity;

import java.util.List;

/**
 * Interface for sort school.
 */
public interface Filter {
    /**
     * Sort list of schools.
     * @return list of schools
     */
    List<SchoolEntity> getSorted();
}
