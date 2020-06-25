package com.liversedge.workoutselector.frontend.adapters;

public class DescriptionItem implements WorkoutElement {

    private int id;
    private String description;

    public DescriptionItem(int id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String getName() {
        return String.valueOf(id) + ") " + description;
    }

    @Override
    public String getDuration() {
        return "";
    }

    @Override
    public String getImageName() {
        return "hidden";
    }

    @Override
    public Boolean shouldCenter() {
        return true;
    }
}
