package com.example.workoutselector.backend.firebase;

public class ExerciseDAO {
    private int id, duration, group_id, workout_id;
    private boolean timed, distanced;
    private String equipment, group_major, group_minor,
        name, weight;

    public ExerciseDAO(int id, int duration, boolean timed, String equipment, String group_major,
                       String group_minor, String name, String weight, boolean distanced,
                       int group_id, int workout_id) {
        this.id = id;
        this.duration = duration;
        this.timed = timed;
        this.equipment = equipment;
        this.group_major = group_major;
        this.group_minor = group_minor;
        this.name = name;
        this.weight = weight;
        this.distanced = distanced;
        this.group_id = group_id;
        this.workout_id = workout_id;
    }

    public boolean isDistanced() {
        return distanced;
    }

    public void setDistanced(boolean distanced) {
        this.distanced = distanced;
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
