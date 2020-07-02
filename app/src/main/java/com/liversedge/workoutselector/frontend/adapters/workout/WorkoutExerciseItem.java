package com.liversedge.workoutselector.frontend.adapters.workout;

import com.liversedge.workoutselector.backend.db.entities.Exercise;

public class WorkoutExerciseItem implements IWorkoutExercise {

    private Exercise exercise;
    private boolean isVideoShown, nextState, isActive;
    private float timeTaken = 0f;

    public WorkoutExerciseItem(Exercise exercise, boolean isVideoShown) {
        this.exercise = exercise;
        this.isVideoShown = isVideoShown;
        this.nextState = isVideoShown;
    }

    @Override
    public boolean isNextState() {
        return nextState;
    }

    @Override
    public void setNextState(boolean nextState) {
        this.nextState = nextState;
    }

    @Override
    public Exercise getExercise() {
        return exercise;
    }

    @Override
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public boolean isVideoShown() {
        return isVideoShown;
    }

    @Override
    public void setVideoShown(boolean videoShown) {
        isVideoShown = videoShown;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public float getTimeTaken() {
        return timeTaken;
    }

    @Override
    public void setTimeTaken(float timeTaken) {
        this.timeTaken = timeTaken;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
