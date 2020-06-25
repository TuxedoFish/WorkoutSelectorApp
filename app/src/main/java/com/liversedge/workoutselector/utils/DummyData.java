package com.liversedge.workoutselector.utils;

import com.liversedge.workoutselector.backend.firebase.WorkoutDAO;

public class DummyData {

    public static WorkoutDAO getDummyWorkout() {
        // TODO: Implement tests
        return null;
    }

    public static String[] getEquipment() {
        String[] areas = {
                "Dumbell",
                "Ropes",
                "Bench",
                "Barbell",
                "Mat",
                "Pull up bar"
        };

        return areas;
    }

}
