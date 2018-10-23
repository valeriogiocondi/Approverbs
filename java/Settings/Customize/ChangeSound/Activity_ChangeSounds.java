package com.valeriogiocondi.user.proverbsapp.Settings.Customize.ChangeSound;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.valeriogiocondi.user.proverbsapp.R;
import com.valeriogiocondi.user.proverbsapp.System.SoundManager;
import com.valeriogiocondi.user.proverbsapp.System.SystemSettings;

import static com.valeriogiocondi.user.proverbsapp.System.ViewCustomActivityHandler.buttonHandler;

public class Activity_ChangeSounds extends AppCompatActivity {

    RadioGroup soundsRadioGroup;
    RadioButton soundRadioButton;
    SystemSettings systemSettings;
    String name;
    SoundManager soundManager;
    int[] resources = {
            R.raw.bark,
            R.raw.broken_glass,
            R.raw.cannon,
            R.raw.cha,
            R.raw.clacson,
            R.raw.cow,
            R.raw.ding,
            R.raw.doorbell,
            R.raw.gong,
            R.raw.old_telephone_ring,
            R.raw.referee,
            R.raw.toc_toc
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_sounds);


        systemSettings = new SystemSettings(this);
        soundManager = new SoundManager(this);

        buttonHandler(this);
        radioGroupManager();
    }

    private void radioGroupManager() {

        soundsRadioGroup = (RadioGroup) findViewById(R.id.sounds_radiogroup);
        soundsRadioGroup.setOrientation(RadioGroup.VERTICAL);
        for(int i=0,n=resources.length; i<n; i++){

            name = getResources().getResourceEntryName(resources[i]);
            soundRadioButton  = new RadioButton(this);

            if (name.equals(systemSettings.get("sound")))
                soundRadioButton.setChecked(true);

            name = name.substring(0, 1).toUpperCase()+name.substring(1, name.length()).replace("_", " ");

            soundRadioButton.setId(i+1);
            soundRadioButton.setText(name);
            soundRadioButton.setTextSize(18);
            soundRadioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            soundRadioButton.setBackgroundResource(R.drawable.xml_sounds_change_radiobutton);
            soundsRadioGroup.addView(soundRadioButton);
        }

        soundsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                String sound = getResources().getResourceEntryName(resources[checkedId-1]);
                soundManager.set(sound);
            }
        });
    }
}