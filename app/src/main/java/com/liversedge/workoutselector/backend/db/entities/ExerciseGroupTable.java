package com.liversedge.workoutselector.backend.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_groups")
public class ExerciseGroupTable {

    public ExerciseGroupTable(int workoutID, String description, String duration, Double repetitions) {
        this.workoutID = workoutID;
        this.description = description;
        this.duration = duration;
        this.repetitions = repetitions;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    /**
     *  EXERCISE GROUP
     */

    @ColumnInfo(name="workout_id")
    public int workoutID;

    @ColumnInfo(name="description")
    public String description;

    @ColumnInfo(name="duration")
    public String duration;

    @ColumnInfo(name="repetitions")
    public Double repetitions;
}
