package com.liversedge.workoutselector.backend.db.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.liversedge.workoutselector.backend.db.entities.Exercise;
import com.liversedge.workoutselector.backend.db.entities.ExerciseGroupTable;
import com.liversedge.workoutselector.backend.db.entities.GroupTable;
import com.liversedge.workoutselector.backend.db.entities.SettingTable;
import com.liversedge.workoutselector.backend.db.entities.WorkoutTable;
import com.liversedge.workoutselector.backend.db.migrations.Converters;
import com.liversedge.workoutselector.backend.db.queries.AppDao;

@Database(entities = {SettingTable.class, Exercise.class,
        ExerciseGroupTable.class, WorkoutTable.class, GroupTable.class},
        views = {}, version=5, exportSchema=false)
@TypeConverters({Converters.class})

public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();
}
