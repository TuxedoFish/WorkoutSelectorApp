package com.example.workoutselector.components;

public class WorkoutDAO {

    private String title, author;
    private String[] exercises;

    public WorkoutDAO(String title, String author, String[] exercises) {
        this.title = title;
        this.author = author;
        this.exercises = exercises;
    }

    public String getTitle() {
        return title;
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

    public String[] getExercises() {
        return exercises;
    }

    public void setExercises(String[] exercises) {
        this.exercises = exercises;
    }
}
