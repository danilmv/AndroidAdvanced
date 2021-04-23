package com.andriod.lesson4.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.andriod.lesson4.model.History;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHistory(History history);

    @Update
    void updateHistory(History history);

    @Delete
    void deleteHistory(History history);

    @Query("SELECT * from history")
    List<History> getAllHistory();

    @Query("DELETE FROM history WHERE id = :id")
    void deleteHistoryById(long id);

    @Query("SELECT * FROM History WHERE id = :id")
    History getHistoryById(long id);

    @Query("SELECT COUNT() FROM history")
    long getCountHistories();
}
