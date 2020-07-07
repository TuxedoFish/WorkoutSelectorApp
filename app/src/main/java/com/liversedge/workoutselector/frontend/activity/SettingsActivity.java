package com.liversedge.workoutselector.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.appbar.MaterialToolbar;
import com.liversedge.workoutselector.R;
import com.liversedge.workoutselector.backend.db.databases.AppDatabase;
import com.liversedge.workoutselector.backend.db.entities.SettingTable;
import com.liversedge.workoutselector.backend.db.migrations.Migrations;
import com.liversedge.workoutselector.frontend.adapters.Setting;
import com.liversedge.workoutselector.frontend.adapters.SettingsListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.liversedge.workoutselector.utils.Constants.INTENT_WORKOUT_ID;
import static com.liversedge.workoutselector.utils.Constants.INTENT_WORKOUT_ID_IS_PRESENT;

public class SettingsActivity extends AppCompatActivity {

    private Button confirmButton;

    private RecyclerView settingsView;

    private ArrayList<Setting> settings;
    private SettingsListAdapter settingsListAdapter;

    // Local SQL holding the settings
    private AppDatabase localDB;

    // Top app bar
    private MaterialToolbar topAppBar;

    // To store in the intent back
    private int workoutID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get components
        settingsView = (RecyclerView)findViewById(R.id.settingsList);

        // Grab a database instance
        localDB = Migrations.getInstance(this).db;

        // Whether or not to return to workout I was last on
        Intent intent = getIntent();
        workoutID = intent.getIntExtra(INTENT_WORKOUT_ID, -1);

        // Set up the top navigation bar
        topAppBar = (MaterialToolbar) findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to home again
                Intent toHome = new Intent(SettingsActivity.this, MainActivity.class);
                toHome.putExtra(INTENT_WORKOUT_ID, workoutID);
                toHome.putExtra(INTENT_WORKOUT_ID_IS_PRESENT, true);
                startActivity(toHome);
            }
        });

        // Set the confirm button
        confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update settings
                for(int i=0; i<settings.size(); i++) {
                    Setting item = settingsListAdapter.getSetting(i);
                    localDB.appDao().updateSetting(item.getId(), item.isState());
                }

                // Navigate to home again
                Intent toHome = new Intent(SettingsActivity.this, MainActivity.class);
                toHome.putExtra(INTENT_WORKOUT_ID, workoutID);
                toHome.putExtra(INTENT_WORKOUT_ID_IS_PRESENT, true);
                startActivity(toHome);
            }
        });

        // Load in the current equipment state
        loadInEquipment();
    }

    private void loadInEquipment() {

        // Load the equipment from local
        List<SettingTable> equipment = localDB.appDao().getSettings();

        settings = new ArrayList<>();

        // Convert to DAO
        for(int i=0; i<equipment.size(); i++) {
            settings.add(new Setting(equipment.get(i).name, equipment.get(i).id));
            settings.get(i).setState(equipment.get(i).activated);
        }

        // Set up adapter for list
        settingsListAdapter = new SettingsListAdapter(this, settings);
        settingsView.setAdapter(settingsListAdapter);
        settingsView.setLayoutManager(new LinearLayoutManager(this));
    }
}
