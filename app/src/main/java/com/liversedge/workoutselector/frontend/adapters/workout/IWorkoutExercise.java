package com.liversedge.workoutselector.frontend.adapters.workout;

import com.liversedge.workoutselector.backend.db.entities.Exercise;

public interface IWorkoutExercise {

    public boolean isNextState();
    public void setNextState(boolean nextState);

    public Exercise getExercise();
    public void setExercise(Exercise exercise);

    public boolean isVideoShown();
    public void setVideoShown(boolean videoShown);

    public boolean isActive();
    public void setActive(boolean isActive);

    public float getTimeTaken();
    public void setTimeTaken(float timeTaken);

    public String getDescription();
}
