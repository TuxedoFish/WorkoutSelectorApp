package com.example.workoutselector.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.workoutselector.R;
import com.example.workoutselector.backend.db.databases.AppDatabase;
import com.example.workoutselector.backend.db.entities.SettingTable;
import com.example.workoutselector.backend.db.migrations.Migrations;
import com.example.workoutselector.backend.firebase.FirebaseWorkoutData;
import com.example.workoutselector.backend.firebase.FirebaseWorkoutLoader;
import com.example.workoutselector.frontend.components.HorizontalWorkoutScrollerView;
import com.example.workoutselector.frontend.components.IEventEnd;
import com.example.workoutselector.backend.firebase.WorkoutDAO;
import com.example.workoutselector.utils.DummyData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.workoutselector.utils.Constants.DEBUGGING;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        IEventEnd, FirebaseWorkoutLoader {

    // Selected area to target
    private boolean hasSelected;
    private String muscleGroup;

    // Equipment available
    private List<SettingTable> equipment;

    // Spinning animation
    private Spinner spinner;
    private Button spinButton;
    private HorizontalWorkoutScrollerView workoutScroller;

    // Button to change equipment
    private Button chooseEquipmentButton;

    // Local SQL holding the settings
    private AppDatabase localDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the workout scroller view
        workoutScroller = (HorizontalWorkoutScrollerView) findViewById(R.id.horizontalSpinner);
        workoutScroller.setEventEnd(this);

        // Fetch the spinner object
        spinner = (Spinner) findViewById(R.id.muscleGroupSelector);

        // Set the spin button listener
        spinButton = (Button) findViewById(R.id.spinButton);
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutScroller.setValueRandom(new Random().nextInt(workoutScroller.getNumberOfWorkouts()), new Random().nextInt(10) + 5);
                spinButton.setEnabled(false);
            }
        });

        // Set the choose equipment button up
        chooseEquipmentButton = (Button) findViewById(R.id.selectEquipmentButton);
        chooseEquipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toChangeEquipment = new Intent(MainActivity.this, SettingsActivity.class);
                toChangeEquipment.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(toChangeEquipment);
            }
        });

        // Get an instance of the SQL tables
        localDB = Migrations.getInstance(this).db;

        // Load the settings in
        equipment = localDB.appDao().getSettings();

        // TODO: Only call these functions occassionally and cache data
        // Load the workouts from firebase
        FirebaseWorkoutData.loadWorkouts(this);

        // Load target areas from firebase
        FirebaseWorkoutData.loadTargetAreas(this);

        // Load the equipment list from firebase
        FirebaseWorkoutData.loadEquipment(this);
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
        // TODO: Deal with what happens if they select nothing
    }

    @Override
    public void eventEnd(int result, int count) {
        // When the spinning ends
        spinButton.setEnabled(true);
    }

    @Override
    public void onWorkoutsLoadedSuccess(ArrayList<WorkoutDAO> workouts) {
        // Update the current workouts
        workoutScroller.setListOfExercises(workouts);
    }

    @Override
    public void onWorkoutsLoadedFailure() {
        // TODO: Deal with firebase errors
    }

    @Override
    public void onTargetsLoadedFailure() {
        // TODO: Deal with no targets being loaded
    }

    @Override
    public void onTargetsLoadedSuccess(List<String> targets) {
        // Apply the loaded targets to the drop down menu
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item,
                        targets); //selected item will look like a spinner set from XML
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public void onEquipmentLoadedFailure() {
        // TODO: Deal with no equipment being found
    }

    @Override
    public void onEquipmentLoadedSuccess(List<String> newEquipment) {

        // Check to add new types of equipment
        for(int i=0; i<newEquipment.size(); i++) {

            String e = newEquipment.get(i);

            if(!isEquipmentPresent(e)) {
                // There is a new piece of equipment so add it set to false
                localDB.appDao().insertSettings(new SettingTable(e, false));
            }
        }

        // Check for old equipment to remove
        for(int i=0; i<equipment.size(); i++) {

            String name = equipment.get(i).name;

            if(!newEquipment.contains(name)) {
                // There is an old piece of equipment not on firebase
                localDB.appDao().deleteSettingWithID(equipment.get(i).id);
            }
        }
    }

    private boolean isEquipmentPresent(String equipmentName) {
        for(int i=0; i<equipment.size(); i++) {
            if(equipment.get(i).name.equals(equipmentName)) {
                return true;
            }
        }

        return false;
    }
}
