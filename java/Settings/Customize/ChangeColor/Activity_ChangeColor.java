package com.valeriogiocondi.user.proverbsapp.Settings.Customize.ChangeColor;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.valeriogiocondi.user.proverbsapp.R;
import com.valeriogiocondi.user.proverbsapp.System.SystemSettings;

import static com.valeriogiocondi.user.proverbsapp.System.ViewCustomActivityHandler.buttonHandler;

public class Activity_ChangeColor extends AppCompatActivity {

    SystemSettings systemSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);


        systemSettings = new SystemSettings(this);

        buttonHandler(this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.color_picker_fragment, new Fragment_ColorPicker());
        fragmentTransaction.commit();
    }
}

