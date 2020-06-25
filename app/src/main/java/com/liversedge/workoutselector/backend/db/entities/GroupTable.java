package com.liversedge.workoutselector.backend.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "groups")
public class GroupTable {

    public GroupTable(String name) {
        this.name = name;
    }

    @PrimaryKey (autoGenerate = true)
    @NonNull
    public int id;

    /**
     *  SETTINGS
     */

    @ColumnInfo(name="name")
    public String name;

}
