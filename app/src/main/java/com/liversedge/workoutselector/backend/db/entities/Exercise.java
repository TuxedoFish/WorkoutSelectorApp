package com.liversedge.workoutselector.backend.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercises")
public class Exercise {

    public Exercise(int workoutID, int groupID, String equipment, String groupMajor,
                    String groupMinor, String name, String weight, int duration, boolean isTimed,
                    boolean hasEnding, String ending) {
        this.groupID = groupID;
        this.workoutID = workoutID;
        this.equipment = equipment;
        this.groupMajor = groupMajor;
        this.groupMinor = groupMinor;
        this.name = name;
        this.weight = weight;
        this.duration = duration;
        this.isTimed = isTimed;
        this.hasEnding = hasEnding;
        this.ending = ending;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    /**
     *  EXERCISE GROUP
     */

    @ColumnInfo(name="workoutID")
    public int workoutID;

    @ColumnInfo(name="group_id")
    public int groupID;

    @ColumnInfo(name="equipment")
    public String equipment;

    @ColumnInfo(name="group_major")
    public String groupMajor;

    @ColumnInfo(name="group_minor")
    public String groupMinor;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="weight")
    public String weight;

    @ColumnInfo(name="ending")
    public String ending;

    @ColumnInfo(name="duration")
    public int duration;

    @ColumnInfo(name="is_timed")
    public boolean isTimed;

    @ColumnInfo(name="is_distanced")
    public boolean hasEnding;

}
