package com.example.workoutselector.frontend.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.workoutselector.R;

public class SettingsViewHolder {

    View base;

    Switch settingsSwitch;
    TextView settingsText;
    ImageView settingsIcon;

    public SettingsViewHolder(View base) {
        this.base = base;
    }

    public Switch getSwitch() {
        if (settingsSwitch == null) {
            settingsSwitch = (Switch) base.findViewById(R.id.settingItemSwitch);
        }
        return settingsSwitch;
    }

    public TextView getSettingsText() {
        if (settingsText == null) {
            settingsText = (TextView) base.findViewById(R.id.settingItemText);
        }
        return settingsText;
    }

    public ImageView getSettingsIcon() {
        if (settingsIcon == null) {
            settingsIcon = (ImageView) base.findViewById(R.id.settingItemImage);
        }
        return settingsIcon;
    }

}
