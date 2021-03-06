package com.liversedge.workoutselector.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.backend.db.databases.AppDatabase;
import com.liversedge.workoutselector.backend.db.entities.GroupTable;
import com.liversedge.workoutselector.backend.db.entities.SettingTable;
import com.liversedge.workoutselector.backend.db.entities.WorkoutTable;
import com.liversedge.workoutselector.backend.db.migrations.Migrations;
import com.liversedge.workoutselector.backend.firebase.FirebaseWorkoutData;
import com.liversedge.workoutselector.backend.firebase.FirebaseWorkoutLoader;
import com.liversedge.workoutselector.frontend.components.HorizontalWorkoutScrollerView;
import com.liversedge.workoutselector.frontend.components.IEventEnd;
import com.liversedge.workoutselector.backend.firebase.WorkoutDAO;
import com.hootsuite.nachos.NachoTextView;
import com.liversedge.workoutselector.utils.AnimationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.liversedge.workoutselector.utils.Constants.DEBUGGING;
import static com.liversedge.workoutselector.utils.Constants.DEBUG_TAG;
import static com.liversedge.workoutselector.utils.Constants.FORCE_RELOAD;
import static com.liversedge.workoutselector.utils.Constants.INTENT_EXERCISE_ID;
import static com.liversedge.workoutselector.utils.Constants.INTENT_WORKOUT_ID;
import static com.liversedge.workoutselector.utils.Constants.INTENT_WORKOUT_ID_IS_PRESENT;

public class MainActivity extends AppCompatActivity implements IEventEnd, FirebaseWorkoutLoader {

    // Equipment available
    private List<SettingTable> equipment;

    // Spinning animation
    private Button spinButton;
    private HorizontalWorkoutScrollerView workoutScroller;

    // Button to change equipment
    private ConstraintLayout optionsHolder;
    private boolean optionsVisible = true;
    private Button chooseEquipmentButton, toggleOptionsButton, startWorkoutButton;

    // Local SQL holding the settings
    private AppDatabase localDB;

    // Current state
    private ArrayList<WorkoutDAO> workouts;
    private int workoutID;

    // Top app bar
    MaterialToolbar topAppBar;

    // Initial start up
    private boolean hasLoaded = false;
    private TextView loadingTextView;
    private int workoutsLoaded = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the start workout button
        startWorkoutButton = findViewById(R.id.startWorkoutButton);
        startWorkoutButton.setVisibility(View.GONE);

        startWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);

                // Pass in information about the current workout and exercises
                intent.putExtra(INTENT_WORKOUT_ID, workoutID);

                startActivity(intent);
            }
        });

        // Get the workout scroller view
        workoutScroller = (HorizontalWorkoutScrollerView) findViewById(R.id.horizontalSpinner);
        workoutScroller.setEventEnd(this);

        // Get an instance of the SQL tables
        localDB = Migrations.getInstance(this).db;

        // Load the settings in
        equipment = localDB.appDao().getSettings();

        // Hide the exercise view
        workoutScroller.setVisibility(View.GONE);

        // Loading text view
        loadingTextView = findViewById(R.id.loadingTextView);

        // Whether or not to return to workout I was last on
        Intent intent = getIntent();
        boolean shouldReturn = intent.getBooleanExtra(INTENT_WORKOUT_ID_IS_PRESENT, false);
        Integer intentWorkoutID = intent.getIntExtra(INTENT_WORKOUT_ID, -1);

        // TODO: Only call these functions occassionally and cache data
        // Load the workouts from firebase only if there are none locally
        boolean shouldReload = localDB.appDao().getAllWorkouts().size() == 0;
        if(shouldReload || FORCE_RELOAD) {
            // Clear all the old data
            localDB.appDao().deleteWorkouts();
            localDB.appDao().deleteExerciseGroups();
            localDB.appDao().deleteExercises();

            FirebaseWorkoutData.loadWorkouts(this, this);
        } else {
            // Loaded
            hasLoaded = true;

            // Update the list of workouts
            updateScroller();

            // Show the scroller and button
            loadingTextView.setVisibility(View.GONE);
            workoutScroller.setVisibility(View.VISIBLE);
            startWorkoutButton.setVisibility(View.VISIBLE);

            // Spin the workouts
            if(shouldReturn) {
                // Attempt to set it to the workout id and if that doesn't work then re-spin
                if(!workoutScroller.setValueFixedWorkoutID(intentWorkoutID)) {
                    workoutScroller.setValueRandom(new Random().nextInt(workoutScroller.getNumberOfWorkouts()), new Random().nextInt(10) + 5);
                }
            } else {
                workoutScroller.setValueRandom(new Random().nextInt(workoutScroller.getNumberOfWorkouts()), new Random().nextInt(10) + 5);
            }
        }

        // Load target areas from firebase
        shouldReload = localDB.appDao().getGroups().size() == 0;
        if(shouldReload || FORCE_RELOAD) {
            // Clear out old data
            localDB.appDao().deleteGroups();

            FirebaseWorkoutData.loadTargetAreas(this);
        } else {
            List<GroupTable> groups = localDB.appDao().getGroups();
//            updateGroupTags(groups);
        }

        // Load the equipment list from firebase
        shouldReload = localDB.appDao().getSettings().size() == 0;
        if(shouldReload || FORCE_RELOAD) {
            // Clear old data
            localDB.appDao().deleteSettings();

            FirebaseWorkoutData.loadEquipment(this);
        }

        // Set up the top navigation bar
        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBar);
//        topAppBar.setNavigationOnClickListener {
//            // Handle navigation icon press
//        }
//
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem item) {
                 switch(item.getItemId()) {
                     case R.id.search:

                         // Set has loaded
                         hasLoaded = true;

                         // Update the list of workouts
                         updateScroller();

                         // Show the scroller and button
                         loadingTextView.setVisibility(View.GONE);
                         workoutScroller.setVisibility(View.VISIBLE);
                         startWorkoutButton.setVisibility(View.VISIBLE);

                         // Select a new random workout
                         workoutScroller.setValueRandom(new Random().nextInt(workoutScroller.getNumberOfWorkouts()), new Random().nextInt(10) + 5);
                         return true;

                     case R.id.equipment:
                         // Move to equipment page
                         Intent toChangeEquipment = new Intent(MainActivity.this, SettingsActivity.class);
                         toChangeEquipment.putExtra(INTENT_WORKOUT_ID, workoutID);
                         startActivity(toChangeEquipment);
                         return true;

//                     case R.id.filter:
//                         // TODO: Create filter screen
//                         return true;
                 }

                 return false;
             }
        });
    }

    private void updateScroller() {

        // Get the workouts filtered out
        List<WorkoutTable> workoutTables = localDB.appDao().getAllWorkouts();
        workouts = new ArrayList<>();

        // Convert these to the access objects from the SQL
        for(int i=0; i<workoutTables.size(); i++) {

            WorkoutTable workout = workoutTables.get(i);

            if(isWorkoutPossible(workout)) {
                workouts.add(
                    new WorkoutDAO(
                            workout.title,
                            workout.author,
                            workout.authorURL,
                            workout.equipmentTags,
                            workout.areaTags,
                            workout.id,
                            localDB
                    )
                );
            }
        }

        // Update the list of views on the scroller
        workoutScroller.setListOfExercises(workouts);
        if(DEBUGGING) {
            Log.d(DEBUG_TAG, "Number of workouts filtered: " + workouts.size() + " / " + workoutTables.size());
        }
    }

    /**
     * Filters out workouts that I do not have either
     * the equipment for, or that I am not looking for
     *
     * @param workout
     * @return
     */
    private boolean isWorkoutPossible(WorkoutTable workout) {
        // Filter out the workout if it has equipment we do not have
        List<String> equipment = workout.equipmentTags;
        for(int i=0; i<equipment.size(); i++) {
            String name = equipment.get(0);
            SettingTable setting = getEquipmentSetting(name.toLowerCase());

            // If this setting does not exist
            if(setting == null) {
                // Use this to check if there is equipment that is used in the data but not in the app
                if(DEBUGGING) {
                    Log.d(DEBUG_TAG, "Missing equipment from app: " + name);
                }
                return false;
            }
            // If I do not have this piece of equipment
            if(!setting.activated) {
                return false;
            }
        }

        // Filter out equipment which does not contain any of the
        // tags we have put in
//        List<String> tags = muscleGroupsView.getChipValues();
//        boolean containsTag = false;
//
//        for(int i=0; i< workout.areaTags.size(); i++) {
//            String area =  workout.areaTags.get(i).toLowerCase();
//            containsTag = containsTag | tags.contains(area);
//        }

        // If it passes all these tests then return true
        return true;
    }

    /**
     * Used to find close matches to dumbells from dumbell e.g
     *
     * @param settingName
     * @return
     */
    private SettingTable getEquipmentSetting(String settingName) {

        // Define the length here
        int n = settingName.length();

        // Try the actual word
        List<SettingTable> settings = localDB.appDao().getSettingByName(settingName);
        if(settings.size() != 0) { return settings.get(0); }

        // Try taking -s of last word i.e
        // push ups => push up
        if(settingName.endsWith("s")) {
            settings = localDB.appDao().getSettingByName(settingName.substring(0, n-1));
            if(settings.size() != 0) { return settings.get(0); }
        }
        // Same with ending in -es i.e
        // crunches => crunch
        if(settingName.endsWith("es")) {
            settings = localDB.appDao().getSettingByName(settingName.substring(0, n-2));
            if(settings.size() != 0) { return settings.get(0); }
        }

        return null;
    }

    @Override
    public void eventEnd(int result, int count) {
        // When the spinning ends
//        spinButton.setEnabled(true);
        workoutID = workouts.get(result).getWorkoutID();
    }

    @Override
    public void onTargetsLoadedFailure() {
        // TODO: Deal with no targets being loaded
    }

    @Override
    public void onTargetsLoadedSuccess(final List<String> targets) {
        ArrayList<GroupTable> groups = new ArrayList<>();
        for(int i=0; i<targets.size(); i++) {
            groups.add(new GroupTable(targets.get(i)));
        }
        localDB.appDao().insertAllGroups(groups);
        updateGroupTags(groups);
    }

    public void updateGroupTags(final List<GroupTable> groups) {
        // Apply the loaded targets to the drop down menu
        // Create an ArrayAdapter using the string array and a default spinner layout

        final ArrayList<String> targets = new ArrayList<>();
        for(int i=0; i<groups.size(); i++) {
            targets.add(groups.get(i).name);
        }
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

    @Override
    public void onWorkoutLoaded() {

        // Count number of workouts
        workoutsLoaded ++;

        final int LOAD_EVERY = 3;
        if(workoutsLoaded % LOAD_EVERY == 0) {
            // Update the list of workouts
            updateScroller();
        }

        if(workoutsLoaded > 50 && !hasLoaded) {

            // Has now loaded
            hasLoaded = true;

            // Update the list of workouts
            updateScroller();

            // Show the scroller and button
            loadingTextView.setVisibility(View.GONE);
            workoutScroller.setVisibility(View.VISIBLE);
            startWorkoutButton.setVisibility(View.VISIBLE);

            // Select a new random workout
            workoutScroller.setValueRandom(new Random().nextInt(workoutScroller.getNumberOfWorkouts()), new Random().nextInt(10) + 5);
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
