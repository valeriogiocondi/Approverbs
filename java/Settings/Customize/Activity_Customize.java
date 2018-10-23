package com.valeriogiocondi.user.proverbsapp.Settings.Customize;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.valeriogiocondi.user.proverbsapp.R;
import com.valeriogiocondi.user.proverbsapp.Settings.Customize.ChangeColor.Activity_ChangeColor;
import com.valeriogiocondi.user.proverbsapp.Settings.Customize.ChangeSound.Activity_ChangeSounds;
import com.valeriogiocondi.user.proverbsapp.System.RealPathUtil;
import com.valeriogiocondi.user.proverbsapp.System.SystemSettings;

import static com.valeriogiocondi.user.proverbsapp.System.ViewCustomActivityHandler.buttonHandler;

public class Activity_Customize extends AppCompatActivity {

    ConstraintLayout colorPickerImage;
    ConstraintLayout changeBackgroundImage;
    ConstraintLayout changeSoundsImage;
    SystemSettings systemSettings;
    Intent intent;
    Uri selectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_layout);


        systemSettings = new SystemSettings(this);

        buttonHandler(this);

        colorPickerImage = (ConstraintLayout) findViewById(R.id.change_color_layout);
        colorPickerImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Customize.this, Activity_ChangeColor.class);
                Activity_Customize.this.startActivity(intent);
            }
        });
        changeBackgroundImage = (ConstraintLayout) findViewById(R.id.change_background_layout);
        changeBackgroundImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /*
                *   INTENT PER LA SCELTA DELLE IMMAGINI PRESENTI NELLA SDCARD
                */

                intent = new Intent()
                        .setType("image/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);

            }
        });
        changeSoundsImage = (ConstraintLayout) findViewById(R.id.change_sounds_layout);
        changeSoundsImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Customize.this, Activity_ChangeSounds.class);
                Activity_Customize.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123 && resultCode == RESULT_OK)
            getRealPath(data.getData());
    }

    private void getRealPath(Uri file) {

        selectedFile = file;
        checkPermission();
    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    123);

        } else {

            saveFilePath();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case 123: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    saveFilePath();

                } else {

                    checkPermission();
                }
                return;
            }
        }
    }

    public void saveFilePath() {

        ContentResolver cr = this.getContentResolver();
        String mime = cr.getType(selectedFile);
        String realPath = "";

        // SDK < API11
        if (Build.VERSION.SDK_INT < 11)
            realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, selectedFile);

            // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
            realPath = RealPathUtil.getRealPathFromURI_API11to18(this, selectedFile);

            // SDK > 19 (Android 4.4)
        else
            realPath = RealPathUtil.getRealPathFromURI_API19(this, selectedFile);

        if (mime.contains("image"))
            systemSettings.set("background", realPath);
    }
}

