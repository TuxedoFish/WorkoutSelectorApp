package com.example.workoutselector.frontend.adapters;

public class Exercise {

    public String name, duration;
    public boolean isTimed;

    public Exercise(String name, boolean isTimed, String duration) {
        this.name = name;
        this.isTimed = isTimed;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public void setTimed(boolean timed) {
        isTimed = timed;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
