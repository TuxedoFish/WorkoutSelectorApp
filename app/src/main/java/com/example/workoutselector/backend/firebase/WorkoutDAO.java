package com.example.workoutselector.backend.firebase;

import com.example.workoutselector.frontend.adapters.Exercise;

import java.util.List;

public class WorkoutDAO {

    private String title, author, repetitions;
    private List<String> equipment, targets;
    private List<Exercise> exercises;

    public WorkoutDAO(String title, String author,
                      List<Exercise> exercises, List<String> equipment,
                      List<String> targets, String repetitions) {
        this.title = title;
        this.author = author;
        this.exercises = exercises;
        this.equipment = equipment;
        this.targets = targets;
        this.repetitions = repetitions;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }
}
