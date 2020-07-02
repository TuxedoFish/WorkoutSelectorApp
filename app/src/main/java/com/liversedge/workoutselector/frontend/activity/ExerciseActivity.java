package com.liversedge.workoutselector.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.backend.db.databases.AppDatabase;
import com.liversedge.workoutselector.backend.db.entities.Exercise;
import com.liversedge.workoutselector.backend.db.entities.ExerciseGroupTable;
import com.liversedge.workoutselector.backend.db.entities.WorkoutTable;
import com.liversedge.workoutselector.backend.db.migrations.Migrations;
import com.liversedge.workoutselector.frontend.adapters.ExerciseItem;
import com.liversedge.workoutselector.frontend.videos.YoutubeVideoFragment;
import com.liversedge.workoutselector.utils.ExerciseImageIds;
import com.liversedge.workoutselector.utils.ImageHelper;

import static com.liversedge.workoutselector.utils.Constants.INTENT_EXERCISE_ID;

public class ExerciseActivity extends AppCompatActivity {

    // Youtube video set elements
    private YouTubePlayerView exerciseVideoView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;

    // UI Elements
    private TextView exerciseNameText, exerciseDurationText,
            exerciseDurationUnitText,
            authorNameText, equipmentText;
    private ImageView authorImage;

    // SQL related
    private AppDatabase localDB;
    private Exercise exercise;
    private ExerciseGroupTable exerciseGroup;
    private WorkoutTable workout;
    private ExerciseItem exerciseItem;

    // Exercise to image and video id helper
    ExerciseImageIds exerciseImageIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Gets an instance of the database
        localDB = Migrations.getInstance(this).db;

        // Gets the exercise information
        Intent intent = getIntent();
        Integer exerciseID = intent.getIntExtra(INTENT_EXERCISE_ID, -1);
        exercise = localDB.appDao().getExerciseByID(exerciseID).get(0);
        exerciseGroup = localDB.appDao().getExerciseGroupsByGroupID(exercise.groupID).get(0);
        workout = localDB.appDao().getWorkoutByID(exercise.workoutID).get(0);

        // Create rounded author image
        // TODO: Get profile images
        RoundedBitmapDrawable authorDrawable = ImageHelper.getRoundedImage(R.drawable.lucaprofile, 10, this);

        // Get the video ID
        exerciseImageIds = new ExerciseImageIds();
        final String videoID = exerciseImageIds.getVideoByExerciseName(exercise.name);
        exerciseItem = new ExerciseItem(
                exercise.name,
                String.valueOf(exercise.duration),
                exercise.isTimed,
                exercise.hasEnding,
                exercise.ending,
                exercise.id
        );

        // Create the video
        // Create Youtube Fragment instance by passing a Youtube Video ID
        YoutubeVideoFragment youtubeFragment =
                YoutubeVideoFragment.newInstance(videoID);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.exerciseInstructionVideo, youtubeFragment).commit();

        // Get the UI Elements
        exerciseNameText = findViewById(R.id.timeElapsedDescription);
        exerciseDurationText = findViewById(R.id.workoutCurrentTime);
        exerciseDurationUnitText = findViewById(R.id.exerciseLengthUnitText);
        authorNameText = findViewById(R.id.authorName);
        equipmentText = findViewById(R.id.equipmentText);
        authorImage = findViewById(R.id.authorImageView);

        // Set the UI Elements
        setExerciseName(exercise.name);
        if(exercise.isTimed) {
            setExerciseDuration(exerciseItem.getDuration());
            setExerciseDurationUnit("");
        } else {
            setExerciseDuration(String.valueOf(exercise.duration));
            setExerciseDurationUnit(exerciseItem.getEnding());
        }
        setAuthorName(workout.author);
        String equipment=exercise.equipment.equals("none") ? "No equipment needed" : exercise.equipment;
        setEquipmentText(equipment);
        setAuthorImage(authorDrawable);
    }

    private void setAuthorImage(RoundedBitmapDrawable drawable) { authorImage.setImageDrawable(drawable); }

    private void setEquipmentText(String equipment) {
        equipmentText.setText(equipment);
    }

    private void setAuthorName(String authorName) {
        authorNameText.setText(authorName);
    }

    private void setExerciseName(String exerciseName) {
        exerciseNameText.setText(exerciseName);
    }

    private void setExerciseDuration(String exerciseDuration) {
        exerciseDurationText.setText(exerciseDuration);
    }

    private void setExerciseDurationUnit(String exerciseDurationUnit) {
        exerciseDurationUnitText.setText(exerciseDurationUnit);
    }

}
