package com.example.testapp.filter;

import com.example.testapp.db.entity.SchoolEntity;

import java.util.List;

public interface Filter {
    List<SchoolEntity> getSorted();
}
