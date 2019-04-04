package com.example.testapp.db.dao;

import com.example.testapp.db.entity.Bookmark;
import com.example.testapp.db.entity.SecondarySchool;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM Bookmark")
    List<Bookmark> getBookmarks();

    @Insert(onConflict = IGNORE)
    void insertBookmark(Bookmark bookmark);

    @Delete
    void deleteBookmark(Bookmark bookmark);

    @Query("SELECT * FROM Bookmark WHERE schoolName LIKE :schoolName")
    Bookmark getBookmark(String schoolName);

    /**
     * Delete all.
     */
    @Query("DELETE FROM SecondarySchool")
    void deleteAll();
}
