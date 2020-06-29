package com.liversedge.workoutselector.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity implements IEventEnd, FirebaseWorkoutLoader {

    // Equipment available
    private List<SettingTable> equipment;

    // Spinning animation
    private Button spinButton;
    private HorizontalWorkoutScrollerView workoutScroller;

    // Button to change equipment
    private ConstraintLayout optionsHolder;
    private boolean optionsVisible = true;
    private Button chooseEquipmentButton;
    private Button toggleOptionsButton;

    // Local SQL holding the settings
    private AppDatabase localDB;

    // Nacho type views for the muscle group
    private NachoTextView muscleGroupsView;

    //nachoTextView.setAdapter(adapter);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the workout scroller view
        workoutScroller = (HorizontalWorkoutScrollerView) findViewById(R.id.horizontalSpinner);
        workoutScroller.setEventEnd(this);

        // Fetch the spinner object
        muscleGroupsView = (NachoTextView) findViewById(R.id.muscleGroupSelector);

        // Fetch the options holder and button
        optionsHolder = (ConstraintLayout) findViewById(R.id.optionsHolder);
        toggleOptionsButton = (Button) findViewById(R.id.showVideoButton);

        // Set the spin button listener
        spinButton = (Button) findViewById(R.id.spinButton);
        spinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the scroller
                updateScroller();

                // Show the scroller
                workoutScroller.setVisibility(View.VISIBLE);

                // Hide the options
                if(optionsVisible) {
                    // Animate the options out
                    AnimationHandler.slide_up(optionsHolder);
                    optionsVisible = false;
                }

                workoutScroller.setValueRandom(new Random().nextInt(workoutScroller.getNumberOfWorkouts()), new Random().nextInt(10) + 5);
                spinButton.setEnabled(false);
            }
        });

        // Set the options toggle button listener
        toggleOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(optionsVisible) {
                    AnimationHandler.slide_up(optionsHolder);
                } else {
                    AnimationHandler.slide_down(optionsHolder);
                }
                optionsVisible = !optionsVisible;
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
        // Load the workouts from firebase only if there are none locally
        boolean shouldReload = localDB.appDao().getAllWorkouts().size() == 0;
        if(shouldReload || FORCE_RELOAD) {
            // Clear all the old data
            localDB.appDao().deleteWorkouts();
            localDB.appDao().deleteExerciseGroups();
            localDB.appDao().deleteExercises();

            FirebaseWorkoutData.loadWorkouts(this);
        }

        // Load target areas from firebase
        shouldReload = localDB.appDao().getGroups().size() == 0;
        if(shouldReload || FORCE_RELOAD) {
            // Clear out old data
            localDB.appDao().deleteGroups();

            FirebaseWorkoutData.loadTargetAreas(this);
        } else {
            List<GroupTable> groups = localDB.appDao().getGroups();
            updateGroupTags(groups);
        }

        // Load the equipment list from firebase
        shouldReload = localDB.appDao().getSettings().size() == 0;
        if(shouldReload || FORCE_RELOAD) {
            // Clear old data
            localDB.appDao().deleteSettings();

            FirebaseWorkoutData.loadEquipment(this);
        }

        // Hide the exercise view
        workoutScroller.setVisibility(View.GONE);
    }

    private void updateScroller() {

        // Get the workouts filtered out
        List<WorkoutTable> workoutTables = localDB.appDao().getAllWorkouts();
        ArrayList<WorkoutDAO> workouts = new ArrayList<>();

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
        List<String> tags = muscleGroupsView.getChipValues();
        boolean containsTag = false;

        for(int i=0; i< workout.areaTags.size(); i++) {
            String area =  workout.areaTags.get(i).toLowerCase();
            containsTag = containsTag | tags.contains(area);
        }

        // If it passes all these tests then return true
        return containsTag;
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
        spinButton.setEnabled(true);
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

        // Using custom element
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new String[] {});

        // Apply the adapter to the spinner
        muscleGroupsView.setAdapter(adapter);

        // Set all options as selected

        muscleGroupsView.setText(targets);

        // Set dropdown to always appear
        muscleGroupsView.setThreshold(0);
        muscleGroupsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muscleGroupsView.showDropDown();
            }
        });

        // Change the suggestions based on which ones are present
        muscleGroupsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                // Filter the suggestions based on what is there
                ArrayList<String> suggestions = new ArrayList<>();
                List<String> values = muscleGroupsView.getChipValues();

                for(int i=0; i<targets.size(); i++) {
                    String suggestion = targets.get(i);

                    if(!values.contains(suggestion)) {
                        suggestions.add(suggestion);
                    }
                }

                // Using custom element
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, suggestions);

                // Apply the adapter to the spinner
                muscleGroupsView.setAdapter(adapter);
            }
        });
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
