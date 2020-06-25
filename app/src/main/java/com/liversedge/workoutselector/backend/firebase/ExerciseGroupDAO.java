package com.liversedge.workoutselector.backend.firebase;

import com.liversedge.workoutselector.backend.db.databases.AppDatabase;
import com.liversedge.workoutselector.backend.db.entities.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseGroupDAO {

    private String description, duration;
    private int group_id, workout_id;
    private double repetitions;
    private List<ExerciseDAO> exercises = new ArrayList<>();

    public ExerciseGroupDAO(String description, String duration, double repetitions,
                            int group_id, int workout_id, AppDatabase db) {
        this.description = description;
        this.duration = duration;
        this.repetitions = repetitions;
        this.group_id = group_id;
        this.workout_id = workout_id;

        // Grab the exercises associated with the group
        List<Exercise> exerciseTables = db.appDao().getExercisesByGroupID(group_id);
        for(int i=0; i<exerciseTables.size(); i++) {
            Exercise exercise = exerciseTables.get(i);

            exercises.add(
                new ExerciseDAO(
                    exercise.id,
                    exercise.duration,
                    exercise.isTimed,
                    exercise.equipment,
                    exercise.groupMajor,
                    exercise.groupMinor,
                    exercise.name,
                    exercise.weight,
                    exercise.hasEnding,
                    exercise.groupID,
                    exercise.workoutID,
                    exercise.ending
                )
            );
        }
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getWorkout_id() {
        return workout_id;
    }

    public void setWorkout_id(int workout_id) {
        this.workout_id = workout_id;
    }

    public double getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(double repetitions) {
        this.repetitions = repetitions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<ExerciseDAO> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDAO> exercises) {
        this.exercises = exercises;
    }
}
