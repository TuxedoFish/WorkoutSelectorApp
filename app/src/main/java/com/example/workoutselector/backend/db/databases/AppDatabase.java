package com.example.workoutselector.backend.db.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.workoutselector.backend.db.entities.SettingTable;
import com.example.workoutselector.backend.db.migrations.Converters;
import com.example.workoutselector.backend.db.queries.AppDao;

@Database(entities = {SettingTable.class}, views = {}, version=2, exportSchema=false)
@TypeConverters({Converters.class})

public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();
}
