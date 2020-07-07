package com.liversedge.workoutselector.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.utils.TimeFormatter;

import static com.liversedge.workoutselector.utils.Constants.INTENT_TIME_TAKEN;
import static com.liversedge.workoutselector.utils.Constants.INTENT_WORKOUT_ID;

public class FinishedWorkoutActivity extends AppCompatActivity {

    // UI components
    private TextView timeTakenText;
    private Button returnHomeButton;
    private ImageView completeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_workout);

        // Gets the exercise information
        Intent intent = getIntent();
        float timeTaken = intent.getFloatExtra(INTENT_TIME_TAKEN, 0.0f);

        // Display how long the workout took
        timeTakenText = findViewById(R.id.workoutTimeTaken);
        timeTakenText.setText(TimeFormatter.convertMilliSecondsToString(timeTaken));

        // Return home button
        returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Return home
                Intent toHome = new Intent(FinishedWorkoutActivity.this, MainActivity.class);
                toHome.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(toHome);
            }
        });

        // Set the image
        completeImageView = (ImageView) findViewById(R.id.completeImageView);
        completeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_fitnessdrawing));
    }
}
