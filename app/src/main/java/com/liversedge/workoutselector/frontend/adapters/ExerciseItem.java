package com.liversedge.workoutselector.frontend.adapters;

public class ExerciseItem implements WorkoutElement {

    private Integer ID;
    private String name, duration, ending;
    private boolean isTimed, hasEnding;

    public ExerciseItem(String name, String duration, boolean isTimed, boolean hasEnding,
                        String ending, Integer ID) {
        this.name = name;
        this.duration = duration;
        this.isTimed = isTimed;
        this.hasEnding = hasEnding;
        this.ending = ending;
        this.ID = ID;

        if(this.ending.contains("N/A")) {
            this.ending = "Reps";
        }
    }

    private String convertToTime(String duration) {
        int seconds = Integer.parseInt(duration);

        if(seconds <= 60) {
            return seconds + " s";
        } else {
            int minutes = (int) Math.floor(seconds / 60);
            seconds = seconds - (minutes * 60);
            return seconds==0 ? (minutes + " mins") : (minutes + " mins " + seconds + " s");
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
            if(hasEnding) {
                return duration + " " + ending;
            } else if (duration.equals("")) {
                return "";
            } else {
                return duration + " reps";
            }

        }
    }

    @Override
    public String getImageName() {
        return name;
    }

    @Override
    public Boolean shouldCenter() {
        return false;
    }

    @Override
    public Integer getID() {
        return ID;
    }

    public String getEnding() {
        return ending;
    }
}
