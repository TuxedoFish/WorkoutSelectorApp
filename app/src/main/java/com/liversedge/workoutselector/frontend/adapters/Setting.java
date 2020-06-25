package com.liversedge.workoutselector.frontend.adapters;

public class Setting {

    private String text;
    private int id;
    private boolean state;

    public Setting(String text, int id, boolean state) {
        this.text = text;
        this.id = id;
        this.state = state;
    }

    public Setting(String text, int id) {
        this.text = text;
        this.id = id;
        this.state = false;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
