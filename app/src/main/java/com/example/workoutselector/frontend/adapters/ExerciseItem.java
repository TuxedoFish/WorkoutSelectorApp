package com.example.workoutselector.frontend.adapters;

public class ExerciseItem implements WorkoutElement{

    public String name, duration;
    public boolean isTimed, isDistance;

    public ExerciseItem(String name, String duration, boolean isTimed, boolean isDistance) {
        this.name = name;
        this.duration = duration;
        this.isTimed = isTimed;
        this.isDistance = isDistance;
    }

    private String convertToTime(String duration) {
        int seconds = Integer.parseInt(duration);

        if(seconds <= 60) {
            return seconds + " s";
        } else {
            int minutes = seconds % 60;
            seconds = seconds - (minutes * 60);
            return seconds==0 ? (minutes + " m") : (minutes + " m " + seconds + " s");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDuration() {
        if(isTimed) {
            return convertToTime(duration);
        } else {
            if(isDistance) {
                return duration + " m";
            } else {
                return duration;
            }

        }
    }

    @Override
    public String getImageName() {
        return name;
    }
}
