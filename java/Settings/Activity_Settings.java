package com.valeriogiocondi.user.proverbsapp.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.valeriogiocondi.user.proverbsapp.R;
import com.valeriogiocondi.user.proverbsapp.Settings.Customize.Activity_Customize;
import com.valeriogiocondi.user.proverbsapp.System.SystemSettings;


public class Activity_Settings extends AppCompatActivity {

    Spinner periodUpdateSpinner;
    Spinner languageSpinner;
    ImageView backToPreviousActivity;
    Switch soundsEnabledSwitch;
    ConstraintLayout customizeThemeLayout;
    SystemSettings systemSettings;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        systemSettings = new SystemSettings(this);

        buttonHandler();
        periodUploadManager();
        languageManager();
        soundsEnabledManager();
        customizeThemeManager();
    }

    private void periodUploadManager() {

        periodUpdateSpinner = (Spinner) findViewById(R.id.period_update_spinner);

        switch (systemSettings.get("period_upload")) {

            case "1": {

                periodUpdateSpinner.setSelection(0);
                break;
            }

            case "2": {

                periodUpdateSpinner.setSelection(1);
                break;
            }

            case "4": {

                periodUpdateSpinner.setSelection(2);
                break;
            }

            case "6": {

                periodUpdateSpinner.setSelection(3);
                break;
            }

            case "12": {

                periodUpdateSpinner.setSelection(4);
                break;
            }

            default: {

                periodUpdateSpinner.setSelection(1);
            }
        }

        periodUpdateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch ((int) (id)) {

                    case 0: {

                        systemSettings.set("period_upload", "1");
                        break;
                    }

                    case 1: {

                        systemSettings.set("period_upload", "2");
                        break;
                    }

                    case 2: {

                        systemSettings.set("period_upload", "4");
                        break;
                    }

                    case 3: {

                        systemSettings.set("period_upload", "6");
                        break;
                    }

                    case 4: {

                        systemSettings.set("period_upload", "12");
                        break;
                    }

                    default: {

                        systemSettings.set("period_upload", "1");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void languageManager() {

        languageSpinner = (Spinner) findViewById(R.id.language_spinner);

        switch (systemSettings.get("language")) {

            case "italian": {

                languageSpinner.setSelection(0);
                break;
            }

            case "english": {

                languageSpinner.setSelection(1);
                break;
            }

            case "german": {

                languageSpinner.setSelection(2);
                break;
            }

            case "latin": {

                languageSpinner.setSelection(3);
                break;
            }

            case "spanish": {

                languageSpinner.setSelection(4);
                break;
            }

            case "chinese": {

                languageSpinner.setSelection(5);
                break;
            }

            default: {

                languageSpinner.setSelection(0);
            }
        }

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch ((int) (id)) {

                    case 0: {

                        systemSettings.set("language", "italian");
                        break;
                    }

                    case 1: {

                        systemSettings.set("language", "english");
                        break;
                    }

                    case 2: {

                        systemSettings.set("language", "german");
                        break;
                    }

                    case 3: {

                        systemSettings.set("language", "latin");
                        break;
                    }

                    case 4: {

                        systemSettings.set("language", "spanish");
                        break;
                    }

                    case 5: {

                        systemSettings.set("language", "chinese");
                        break;
                    }

                    default: {

                        systemSettings.set("language", "italian");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void soundsEnabledManager() {

        soundsEnabledSwitch = (Switch) findViewById(R.id.switch_sounds);

        if (systemSettings.get("sound_enabled").equals("1"))
            soundsEnabledSwitch.setChecked(true);
        else
            soundsEnabledSwitch.setChecked(false);

        soundsEnabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    systemSettings.set("sound_enabled", "1");
                else
                    systemSettings.set("sound_enabled", "0");
            }
        });
    }

    private void customizeThemeManager() {

        customizeThemeLayout = (ConstraintLayout) findViewById(R.id.customize_layout);
        customizeThemeLayout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                intent = new Intent(Activity_Settings.this, Activity_Customize.class);
                Activity_Settings.this.startActivity(intent);
            }
        });
    }

    private void buttonHandler() {

        backToPreviousActivity = (ImageView) findViewById(R.id.back);
        backToPreviousActivity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                onBackPressed();
            }
        });
    }
}