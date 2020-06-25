package com.liversedge.workoutselector.backend.firebase;

public class ExerciseDAO {
    private int id, duration, group_id, workout_id;
    private boolean timed, hasEnding;
    private String equipment, group_major, group_minor,
        name, weight, ending;

    public ExerciseDAO(int id, int duration, boolean timed, String equipment, String group_major,
                       String group_minor, String name, String weight, boolean hasEnding,
                       int group_id, int workout_id, String ending) {
        this.id = id;
        this.duration = duration;
        this.timed = timed;
        this.equipment = equipment;
        this.group_major = group_major;
        this.group_minor = group_minor;
        this.name = name;
        this.weight = weight;
        this.hasEnding = hasEnding;
        this.group_id = group_id;
        this.workout_id = workout_id;
        this.ending = ending;
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

    public boolean isHasEnding() {
        return hasEnding;
    }

    public void setHasEnding(boolean hasEnding) {
        this.hasEnding = hasEnding;
    }

    public String getEnding() {
        return ending;
    }

    public void setEnding(String ending) {
        this.ending = ending;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isTimed() {
        return timed;
    }

    public void setTimed(boolean timed) {
        this.timed = timed;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getGroup_major() {
        return group_major;
    }

    public void setGroup_major(String group_major) {
        this.group_major = group_major;
    }

    public String getGroup_minor() {
        return group_minor;
    }

    public void setGroup_minor(String group_minor) {
        this.group_minor = group_minor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
