package com.example.workoutselector.backend.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "workouts")
public class WorkoutTable {

    public WorkoutTable(String title, String author, String authorURL,
                        List<String> equipmentTags, List<String> areaTags) {
        this.title = title;
        this.author = author;
        this.authorURL = authorURL;
        this.equipmentTags = equipmentTags;
        this.areaTags = areaTags;
    }

    @PrimaryKey (autoGenerate = true)
    @NonNull
    public int id;

    /**
     *  WORKOUT
     */

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="author")
    public String author;

    @ColumnInfo(name="author_url")
    public String authorURL;

    @ColumnInfo(name="equipment_tags")
    public List<String> equipmentTags;

    @ColumnInfo(name="area_tags")
    public List<String> areaTags;

}
