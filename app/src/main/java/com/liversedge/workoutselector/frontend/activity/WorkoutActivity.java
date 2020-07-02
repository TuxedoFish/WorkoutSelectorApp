package com.liversedge.workoutselector.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.backend.db.databases.AppDatabase;
import com.liversedge.workoutselector.backend.db.entities.Exercise;
import com.liversedge.workoutselector.backend.db.entities.ExerciseGroupTable;
import com.liversedge.workoutselector.backend.db.migrations.Migrations;
import com.liversedge.workoutselector.frontend.adapters.workout.DescriptionExerciseItem;
import com.liversedge.workoutselector.frontend.adapters.workout.IWorkoutExercise;
import com.liversedge.workoutselector.frontend.adapters.workout.WorkoutExerciseAdapter;
import com.liversedge.workoutselector.frontend.adapters.workout.WorkoutExerciseItem;
import com.liversedge.workoutselector.utils.ImageHelper;
import com.liversedge.workoutselector.utils.TimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.liversedge.workoutselector.utils.Constants.DEBUG_TAG;
import static com.liversedge.workoutselector.utils.Constants.INTENT_TIME_TAKEN;
import static com.liversedge.workoutselector.utils.Constants.INTENT_WORKOUT_ID;

public class WorkoutActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    // UI elements
    private ImageView authorImage;

    // Exercises view
    private RecyclerView exercisesView;
    private WorkoutExerciseAdapter workoutExerciseAdapter;
    private List<Exercise> exercises;
    private Button pauseExerciseButton, stopWorkoutButton;
    private TextView timeElapsedDescription, workoutCurrentTime;

    // SQL Database
    private AppDatabase localDB;

    // Timer in milliseconds
    private boolean paused = true;
    private float currentTime = 0.0f;
    private int updateDelta = (int) Math.floor(1f/60f*1000f);
    private boolean hasStarted = false;
    private int nextToSay = 3;

    // Text to speech stuff
    private TextToSpeech mTts;
    private static final int MY_DATA_CHECK_CODE = 1005;

    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                mTts = new TextToSpeech(this, this);

                // Set up speech
                mTts.setLanguage(Locale.UK);
                paused = false;
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // Check for text to speech capability
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

        // Gets an instance of the database
        localDB = Migrations.getInstance(this).db;

        // Gets the exercise information
        Intent intent = getIntent();
        Integer workoutID = intent.getIntExtra(INTENT_WORKOUT_ID, -1);
        exercises = localDB.appDao().getExercisesByWorkoutID(workoutID);

        // Create rounded author image
        // TODO: Get profile images
        RoundedBitmapDrawable authorDrawable = ImageHelper.getRoundedImage(R.drawable.lucaprofile, 10, this);
        authorImage = findViewById(R.id.authorImageView);
        setAuthorImage(authorDrawable);

        // Set up workout view
        ArrayList<IWorkoutExercise> exerciseItems = new ArrayList<>();
        int currentGroupID = -1;

        for(int i=0; i<exercises.size(); i++) {
            Exercise newExercise = exercises.get(i);
            WorkoutExerciseItem newExerciseItem = new WorkoutExerciseItem(newExercise, false);

            if(newExercise.groupID != currentGroupID) {
                ExerciseGroupTable group = localDB.appDao().getExerciseGroupsByGroupID(newExercise.groupID).get(0);
                DescriptionExerciseItem newGroupItem = new DescriptionExerciseItem(group.description,
                        (int) Math.round(group.repetitions));
                exerciseItems.add(newGroupItem);
                currentGroupID = newExercise.groupID;
            }

            exerciseItems.add(newExerciseItem);
        }

        // Text views for timing the overall workout
        timeElapsedDescription = findViewById(R.id.timeElapsedDescription);
        workoutCurrentTime = findViewById(R.id.workoutCurrentTime);

        // Setting up the list of exercises
        exercisesView = findViewById(R.id.workoutExerciseHolder);
        workoutExerciseAdapter = new WorkoutExerciseAdapter(this, getSupportFragmentManager(),
                exerciseItems, new WorkoutExerciseAdapter.WorkoutUpdates() {
            @Override
            public void finishWorkout() {
                stopWorkout();
            }

            @Override
            public void speakNewWord(String word) {
                mTts.speak(String.valueOf(word), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        // Changing the layout type so that it displays properly
        LinearLayoutManager exercisesLayoutManager = new LinearLayoutManager(this);
        exercisesView.setLayoutManager(exercisesLayoutManager);
        // Attach the adapter
        exercisesView.setAdapter(workoutExerciseAdapter);

        // Start countdown timer
        timeElapsedDescription.setText(R.string.countdown_description);

        // Next exercise button
        pauseExerciseButton = findViewById(R.id.pauseExerciseButton);
        pauseExerciseButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_24px, 0, 0, 0);
        pauseExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO pause workout
                paused = !paused;

                if(paused) {
                    // Show play button
                    mTts.speak("workout paused", TextToSpeech.QUEUE_FLUSH, null);
                    pauseExerciseButton.setText(R.string.play_button);
                    pauseExerciseButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_arrow_24px, 0, 0, 0);
                } else {
                    // Show pause button
                    mTts.speak("Resume workout", TextToSpeech.QUEUE_FLUSH, null);
                    pauseExerciseButton.setText(R.string.pause_exercise);
                    pauseExerciseButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_24px, 0, 0, 0);

                }
            }
        });

        stopWorkoutButton = findViewById(R.id.stopExerciseButton);
        stopWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopWorkout();
            }
        });

        // Setup update timer function
        final Handler handler = new Handler();
        Timer timer = new Timer();

        // The starting count down timer
        currentTime = 4100f;

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            if(!paused) {
                                update(updateDelta);
                            }
                        } catch (Exception e) {
                            // TODO: Do something with timer error
                            Log.d(DEBUG_TAG, "Exception WorkoutActivity.onCreate() Line 119");
                        }
                    }
                });
            }
        };

        timer.schedule(doAsynchronousTask, 0, updateDelta); //execute in every 10 minutes
    }

    private void stopWorkout() {

        // Vocalise end of workout
        mTts.speak("End of workout", TextToSpeech.QUEUE_FLUSH, null);

        // Navigate to workout over screen
        Intent intent = new Intent(WorkoutActivity.this, FinishedWorkoutActivity.class);
        intent.putExtra(INTENT_TIME_TAKEN, currentTime);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);

    }

    private void update(float delta) {
        // Increase or decrease the time based on where it is
        currentTime += hasStarted ? delta : -delta;

        // If it has not started yet but the countdown has finished
        if(!hasStarted && currentTime < 0.0f) {
            // Start countdown timer
            if(workoutExerciseAdapter.isTimedWorkout()) {
                String instruction = exercises.get(0).name + " " + " for " +
                        exercises.get(0).duration + " seconds";

                mTts.speak(instruction, TextToSpeech.QUEUE_ADD, null);
                timeElapsedDescription.setText(R.string.workout_time_remaining);
            } else {
                timeElapsedDescription.setText(R.string.workout_time_elapsed_description);
            }

            hasStarted = true;
            currentTime = 0.0f;
        }

        // If hasnt started and should say something
        if(!hasStarted) {
            if(currentTime < (nextToSay + 1)*1000) {
                if(nextToSay == 0) {
                    mTts.speak("Start workout", TextToSpeech.QUEUE_ADD, null);
                } else {
                    mTts.speak(String.valueOf(nextToSay), nextToSay==3 ? TextToSpeech.QUEUE_FLUSH : TextToSpeech.QUEUE_ADD, null);
                }

                nextToSay --;
            }
        }

        // If the exercise has started then update the adapter
        if(hasStarted) { workoutExerciseAdapter.update(delta); }

        // Update the timer
        if(workoutExerciseAdapter.isTimedWorkout() && hasStarted) {
            workoutCurrentTime.setText(TimeFormatter.convertMilliSecondsToString(workoutExerciseAdapter.getTimeRemaining()));
        } else {
            workoutCurrentTime.setText(TimeFormatter.convertMilliSecondsToString(currentTime));
        }
    }

    private void setAuthorImage(RoundedBitmapDrawable drawable) { authorImage.setImageDrawable(drawable); }

    @Override
    public void onInit(int i) {

    }
}
