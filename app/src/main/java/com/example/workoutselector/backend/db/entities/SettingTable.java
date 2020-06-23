package com.example.workoutselector.backend.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings")
public class SettingTable {

    public SettingTable(String name, boolean activated) {
        this.name = name;
        this.activated = activated;
    }

    @PrimaryKey (autoGenerate = true)
    @NonNull
    public int id;

    /**
     *  SETTINGS
     */

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="activated")
    public boolean activated;

}
