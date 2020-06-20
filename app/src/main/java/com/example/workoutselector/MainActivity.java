package com.example.workoutselector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.workoutselector.components.HorizontalWorkoutScrollerView;
import com.example.workoutselector.components.IEventEnd;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, IEventEnd {

    private boolean hasSelected;
    private String muscleGroup;

    private Button spinButton;
    private HorizontalWorkoutScrollerView workoutScroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Get workout types from firebase
        ArrayList<String> spinnerArray = getMuscleGroups();
        // Fetch the spinner object
        Spinner spinner = (Spinner) findViewById(R.id.muscleGroupSelector);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item,
                        spinnerArray); //selected item will look like a spinner set from XML
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Get the workout scroller view
        workoutScroller = (HorizontalWorkoutScrollerView) findViewById(R.id.horizontalSpinner);
        workoutScroller.setEventEnd(this);

        // Set the spin button listener
        spinButton = (Button) findViewById(R.id.spinButton);
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutScroller.setValueRandom(new Random().nextInt(2), new Random().nextInt(10) + 5);
            }
        });
    }

    private ArrayList<String> getMuscleGroups() {
        ArrayList<String> muscleGroups = new ArrayList<>();

        muscleGroups.add("Arms");
        muscleGroups.add("Back");
        muscleGroups.add("Legs");

        return muscleGroups;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        // Selected an item so we can start
        hasSelected = true;
        muscleGroup = parent.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void eventEnd(int result, int count) {
        // When the spinning ends
    }
}
