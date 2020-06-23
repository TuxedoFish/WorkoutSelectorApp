package com.example.workoutselector.frontend.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.workoutselector.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsListAdapter extends BaseAdapter  {

    private List<Setting> listOfSettings;
    //private OnAlarmCheckedChangeListener mCallback;

    // Avoid constant allocation
    private View tmpView;
    private SettingsViewHolder mHolder;
    private Setting tmpItem;

    AppCompatActivity context;

    private int[] colors = new int[]{
            R.color.settingColor1,
            R.color.settingColor2,
            R.color.settingColor3,
            R.color.settingColor4,
            R.color.settingColor5,
            R.color.settingColor6,
            R.color.settingColor7,
            R.color.settingColor8
    };

    public SettingsListAdapter(AppCompatActivity context, ArrayList<Setting> listOfSettings) {
        this.context = context;
        this.listOfSettings = listOfSettings;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Setting setting = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
        }

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            tmpView = inflater.inflate(R.layout.item_setting, parent, false);
            mHolder = new SettingsViewHolder(tmpView);
            tmpView.setTag(mHolder);
        }
        else {
            tmpView = convertView;
            mHolder = (SettingsViewHolder) convertView.getTag();
        }

        // Lookup view for data population
        TextView settingText = (TextView) mHolder.getSettingsText();
        ImageView settingIcon = (ImageView) mHolder.getSettingsIcon();
        Switch settingItemSwitch = (Switch) mHolder.getSwitch();

        // Set up the image for the setting
        Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_fitness_center_24px);
        Drawable wrappedDrawable = DrawableCompat.wrap(icon);
        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, colors[position%colors.length]));

        settingIcon.setBackgroundDrawable(wrappedDrawable);

        // Ensure that there is no extra checking of boxes
        settingItemSwitch.setChecked(listOfSettings.get(position).isState());

        // Set onClick
        settingItemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listOfSettings.get(position).setState(b);
                // mCallback.onAlarmStateChanged(listOfSymptoms.get(i), i);
            }
        });

        // Populate the data into the template view using the data object
        settingText.setText(setting.getText());

        // Return the completed view to render on screen
        return tmpView;
    }

    @Override
    public int getCount() {
        return listOfSettings == null ? 0 : listOfSettings.size();
    }

    @Override
    public Setting getItem(int i) {
        return listOfSettings == null ? null : listOfSettings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void clear() {
        listOfSettings.clear();
        notifyDataSetChanged();
    }

    public void refill(List<Setting> listOfAlarms) {
        this.listOfSettings = listOfAlarms;
        notifyDataSetChanged();
    }

    public void toggleAllSwitches() {
//        for (Symptom item : listOfSymptoms) {
//            item.setState(!item.getState());
//        }
//        notifyDataSetChanged();
    }
}
