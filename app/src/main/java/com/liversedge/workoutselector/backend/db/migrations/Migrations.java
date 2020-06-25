package com.liversedge.workoutselector.backend.db.migrations;

import android.content.Context;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.liversedge.workoutselector.backend.db.databases.AppDatabase;

public class Migrations {
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // No need to carry out migrations whilst in pre production
        }
    };

    // static variable single_instance of type Singleton
    private static Migrations single_instance = null;

    public AppDatabase db;

    /** To put into production must remove the fallbackToDestructiveMigration through**/
    private Migrations(Context context)
    {
        db = Room.databaseBuilder(context, AppDatabase.class, "db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
//                .addMigrations(MIGRATION_1_2)
                .build();
    }

    public void closeDb() throws Exception {
        db.close();
    }

    // static method to create instance of Singleton class
    public static Migrations getInstance(Context context)
    {
        if (single_instance == null)
            single_instance = new Migrations(context);

        return single_instance;
    }
}
