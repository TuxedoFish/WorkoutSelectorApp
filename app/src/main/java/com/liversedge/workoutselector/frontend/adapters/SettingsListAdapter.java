package com.liversedge.workoutselector.frontend.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.liversedge.workoutselector.R;

import java.util.List;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

public class SettingsListAdapter extends RecyclerView.Adapter<SettingsListAdapter.ViewHolder>{

    private List<Setting> settings;
    private Context context;
    private View view;
    private ViewHolder viewHolder;

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

    public SettingsListAdapter(Context context, List<Setting> settings) {
        this.settings = settings;
        this.context = context;
    }

    public Setting getSetting(int i) {
        return settings.get(i);
    }

    public void setSettings(List<Setting> settings) {
        this.settings = settings;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView settingText;
        private ImageView settingImage;
        private Switch settingSwitch;

        public ViewHolder(View v){

            super(v);

            settingText = (TextView) v.findViewById(R.id.settingItemText);
            settingSwitch = (Switch) v.findViewById(R.id.settingItemSwitch);
            settingImage = (ImageView) v.findViewById(R.id.settingItemImage);
        }
    }

    @Override
    public SettingsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view = LayoutInflater.from(context).inflate(R.layout.item_setting, parent,false);

        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position){
        final Setting setting = settings.get(position);

        String text = setting.getText();
        boolean state = setting.isState();

        // Capitalize first letter
        String cap = text.substring(0, 1).toUpperCase() + text.substring(1);

        holder.settingText.setText(cap);
        holder.settingSwitch.setChecked(state);

        // Make sure we update the model if the user taps the switch
        holder.settingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                int adapterPosition = holder.getAdapterPosition();

                Setting tapped = settings.get(adapterPosition);
                settings.set(adapterPosition, new Setting(setting.getText(), setting.getId(), isChecked));
            }
        });

    }

    @Override
    public int getItemCount(){
        return settings.size();
    }
}