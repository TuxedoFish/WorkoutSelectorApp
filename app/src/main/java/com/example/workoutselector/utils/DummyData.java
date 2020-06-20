package com.example.workoutselector.utils;

import com.example.workoutselector.components.WorkoutDAO;

public class DummyData {

    public static WorkoutDAO getDummyWorkout() {

        String[] exercises = {
                "10 x lunges",
                "10 x hip thrusts",
                "20 x calf raises",
                "5 x crunches"
        };

        WorkoutDAO workout = new WorkoutDAO("All body workout", "Harry Liversedge", exercises);

        return workout;
    }
}
