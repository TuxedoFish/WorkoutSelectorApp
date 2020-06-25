package com.liversedge.workoutselector.frontend.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;

import com.liversedge.workoutselector.R;

public class SettingsItemView extends LinearLayout {
    
    TextView settingsText;
    ImageView icon;
    Switch settingsSwitch;

    public SettingsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.item_setting, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingsItem);

        CharSequence title = typedArray.getText(R.styleable.SettingsItem_settingsText);
        Drawable icon = typedArray.getDrawable(R.styleable.SettingsItem_settingsIconImage);
        int color = typedArray.getColor(R.styleable.SettingsItem_settingsIconColor, Color.BLACK);

        typedArray.recycle();

        initComponents();

        setSettingsText(title);
        setIcon(icon, color);
    }

    private void initComponents() {
        settingsText = (TextView) findViewById(R.id.settingItemText);
        icon = (ImageView) findViewById(R.id.settingItemImage);
        settingsSwitch = (Switch) findViewById(R.id.settingItemSwitch);
    }

    public void addListener(CompoundButton.OnCheckedChangeListener onChange) {
        settingsSwitch.setOnCheckedChangeListener(onChange);
    }

    public void setSwitchEnabled(boolean enabled) {
        settingsSwitch.setEnabled(enabled);
    }

    public void setSwitchState(boolean state) {
        settingsSwitch.setChecked(state);
    }

    public Drawable getIcon() {
        return icon.getDrawable();
    }

    public void setIcon(Drawable image, int value) {
        Drawable wrappedDrawable = DrawableCompat.wrap(image);
        DrawableCompat.setTint(wrappedDrawable, value);

        icon.setBackgroundDrawable(wrappedDrawable);
    }

    public CharSequence getsettingsText() {
        return settingsText.getText();
    }

    public void setSettingsText(CharSequence value) {
        settingsText.setText(value);
    }
}

