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

/**
 * The interface Bookmark dao.
 */
@Dao
public interface BookmarkDao {
    /**
     * Get Bookmarked schools saved by the user.
     * @return bookmarked schools
     */
    @Query("SELECT * FROM Bookmark")
    List<Bookmark> getBookmarks();

    /**
     * Add a Bookmark.
     * @param bookmark bookmarked school
     */
    @Insert(onConflict = IGNORE)
    void insertBookmark(Bookmark bookmark);

    /**
     * Remove a particular bookmark.
     * @param bookmark previously bookmarked school
     */
    @Delete
    void deleteBookmark(Bookmark bookmark);

    /**
     * Get a bookmark based on given school name.
     * @param schoolName
     * @return bookmark
     */
    @Query("SELECT * FROM Bookmark WHERE schoolName LIKE :schoolName")
    Bookmark getBookmark(String schoolName);

    /**
     * Delete all.
     */
    @Query("DELETE FROM SecondarySchool")
    void deleteAll();
}
