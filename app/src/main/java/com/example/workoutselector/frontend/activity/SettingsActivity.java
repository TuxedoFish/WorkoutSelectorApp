package com.example.workoutselector.frontend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.workoutselector.R;
import com.example.workoutselector.backend.db.databases.AppDatabase;
import com.example.workoutselector.backend.db.entities.SettingTable;
import com.example.workoutselector.backend.db.migrations.Migrations;
import com.example.workoutselector.frontend.adapters.Setting;
import com.example.workoutselector.frontend.adapters.SettingsListAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private Button confirmButton;

    private ListView settingsView;

    private ArrayList<Setting> settings;
    private SettingsListAdapter settingsListAdapter;

    // Local SQL holding the settings
    private AppDatabase localDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get components
        settingsView = (ListView)findViewById(R.id.settingsList);

        // Set the confirm button
        confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update settings
                for(int i=0; i<settings.size(); i++) {
                    Setting item = settingsListAdapter.getItem(i);
                    localDB.appDao().updateSetting(item.getId(), item.isState());
                }

                // Navigate to home again
                Intent toHome = new Intent(SettingsActivity.this, MainActivity.class);
                toHome.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(toHome);
            }
        });

        // Grab a database instance
        localDB = Migrations.getInstance(this).db;

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
    }
}
