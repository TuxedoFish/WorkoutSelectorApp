package com.example.workoutselector.utils;

import com.example.workoutselector.backend.firebase.WorkoutDAO;
import com.example.workoutselector.frontend.adapters.Exercise;

import java.util.ArrayList;

public class DummyData {

    public static WorkoutDAO getDummyWorkout() {

        String[] equipmentArray = {
                "Mat"
        };

        String[] targetAreasArray = {
                "Arms",
                "Legs"
        };

        ArrayList<String> equipment = new ArrayList<>();
        for(int i=0; i<equipmentArray.length; i++) { equipment.add(equipmentArray[i]); }

        ArrayList<String> targets = new ArrayList<>();
        for(int i=0; i<targetAreasArray.length; i++) { targets.add(targetAreasArray[i]); }

        ArrayList<Exercise> exerciseFakeList = new ArrayList<>();
        exerciseFakeList.add(new Exercise("Crunches", false, "5"));
        exerciseFakeList.add(new Exercise("Crunches", false, "5"));
        exerciseFakeList.add(new Exercise("Crunches", false, "5"));
        exerciseFakeList.add(new Exercise("Crunches", false, "5"));

        WorkoutDAO workout = new WorkoutDAO(
                "All body workout",
                "Harry Liversedge",
                exerciseFakeList,
                equipment,
                targets,
                "5"
        );

        return workout;
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
