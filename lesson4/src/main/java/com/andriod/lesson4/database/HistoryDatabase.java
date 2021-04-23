package com.andriod.lesson4.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.andriod.lesson4.dao.HistoryDao;
import com.andriod.lesson4.model.History;

@Database(entities = {History.class}, version = 1)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract HistoryDao getHistoryDao();
}
