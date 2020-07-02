package com.liversedge.workoutselector.frontend.adapters.workout;

import com.liversedge.workoutselector.backend.db.entities.Exercise;

public class DescriptionExerciseItem implements IWorkoutExercise {

    private String description;
    private int repetitions;

    public DescriptionExerciseItem(String description, int repetitions) {
        this.description = description;
        this.repetitions = repetitions;
    }

    public int getRepetitions() {
        return repetitions;
    }

    @Override
    public boolean isNextState() {
        return false;
    }

    @Override
    public void setNextState(boolean nextState) {

    }

    @Override
    public Exercise getExercise() {
        return null;
    }

    @Override
    public void setExercise(Exercise exercise) {

    }

    @Override
    public boolean isVideoShown() {
        return false;
    }

    @Override
    public void setVideoShown(boolean videoShown) {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void setActive(boolean isActive) {

    }

    @Override
    public float getTimeTaken() {
        return 0;
    }

    @Override
    public void setTimeTaken(float timeTaken) {

    }

    @Override
    public String getDescription() {
        return description;
    }
}
