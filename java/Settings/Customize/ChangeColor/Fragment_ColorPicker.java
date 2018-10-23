package com.valeriogiocondi.user.proverbsapp.Settings.Customize.ChangeColor;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;

import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorPreference;
import com.valeriogiocondi.user.proverbsapp.R;
import com.valeriogiocondi.user.proverbsapp.System.SystemSettings;

public class Fragment_ColorPicker extends PreferenceFragment implements ColorPickerDialogListener {

    SystemSettings systemSettings;

    public Fragment_ColorPicker() {

        systemSettings = new SystemSettings(this.getContext());
    }

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.color_preferences);

        systemSettings = new SystemSettings(Fragment_ColorPicker.this.getContext());

        ColorPreference colorPreference = (ColorPreference) findPreference("default_color");
        colorPreference.saveValue(Color.parseColor(systemSettings.get("color")));
        colorPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override public boolean onPreferenceChange(Preference preference, Object newValue) {

                if ("default_color".equals(preference.getKey())) {

                    String newColor = Integer.toHexString((int) newValue);
                    systemSettings.set("color", "#"+newColor);
                }

                return true;
            }
        });

    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}