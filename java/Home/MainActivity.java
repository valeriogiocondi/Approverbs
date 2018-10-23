package com.valeriogiocondi.user.proverbsapp.Home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.valeriogiocondi.user.proverbsapp.Home.LeftMenu.ArrayAdapter_LeftMenu;
import com.valeriogiocondi.user.proverbsapp.R;
import com.valeriogiocondi.user.proverbsapp.Settings.Activity_Settings;
import com.valeriogiocondi.user.proverbsapp.System.SoundManager;
import com.valeriogiocondi.user.proverbsapp.System.SystemSettings;

import java.io.File;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    ConstraintLayout mainLayout;
    ListView menuListView;
    ImageView menuButton;
    ImageView backgroundImage;
    TextView proverbTextView;
    TextView proverbLanguageTextView;
    ConstraintLayout headerLayout;
    Button updateProverbsButton;
    ConstraintLayout left_menu_header;
    PickProverbs proverb;
    SystemSettings systemSettings;
    SharedPreferences prefs;
    SharedPreferences.OnSharedPreferenceChangeListener listener;
    Runnable thread;
    ScheduledThreadPoolExecutor exec;
    SoundManager soundManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        proverbTextView = (TextView) findViewById(R.id.proverb_textview);
        proverbLanguageTextView = (TextView) findViewById(R.id.language_proverb_textview);
        headerLayout = (ConstraintLayout) findViewById(R.id.header);
        updateProverbsButton = (Button) findViewById(R.id.update_proverbs);
        systemSettings = new SystemSettings(this);

        init();
        getProverbsByButton();
    }

    private void init() {

        proverb = new PickProverbs(this);
        soundManager = new SoundManager(this);

        restoreSettings();
        listenerSharedPreferences();
        initProverbsThread();
        leftMenuManager();
    }

    private void restoreSettings() {

        if (systemSettings.get("language").equals(""))
            systemSettings.set("language", "italian");

        if (systemSettings.get("period_upload").equals(""))
            systemSettings.set("period_upload", "1");

        if (systemSettings.get("color").equals(""))
            systemSettings.set("color", "#214785");

        if (systemSettings.get("sound_enabled").equals(""))
            systemSettings.set("sound_enabled", "1");

        if (systemSettings.get("sound").equals(""))
            systemSettings.set("sound", "ding");

        setColor();
        setBackgroundImage();
    }

    private void setBackgroundImage() {

        backgroundImage = (ImageView) findViewById(R.id.background_image);
        File imgFile = new  File(systemSettings.get("background"));

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            backgroundImage.setImageBitmap(myBitmap);
        }
    }

    private void setColor() {

        String color = systemSettings.get("color");
        System.out.println(color);

        headerLayout.setBackgroundColor(Color.parseColor(color));
        updateProverbsButton.setBackgroundColor(Color.parseColor(color));
    }

    private void leftMenuManager() {

        leftMenuHeaderHandler();
        leftMenuHandler();
    }

    private void leftMenuHeaderHandler() {

        menuButton = (ImageView) findViewById(R.id.button_menu);
        menuButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)  {

                leftMenuAnimation();

            }
        });
        left_menu_header = (ConstraintLayout) findViewById(R.id.left_menu_header);
        left_menu_header.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)  {

                leftMenuAnimation();
            }
        });
    }

    private void leftMenuHandler() {

        menuListView = (ListView) findViewById(R.id.menu_listview);
        String[] list = new String[]{"HOME", "IMPOSTAZIONI"};

        ArrayAdapter_LeftMenu menuListAdapter = new ArrayAdapter_LeftMenu(this, list);
        menuListView.setAdapter(menuListAdapter);

        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction frTransaction = fragmentManager.beginTransaction();

                switch (position) {

                    case 0: {

                        break;
                    }
                    case 1: {

                        Intent intent = new Intent(MainActivity.this, Activity_Settings.class);
                        MainActivity.this.startActivity(intent);

                        break;
                    }
                }

                leftMenuAnimation();
                menuListView.setItemChecked(position, true);

            }
        });
    }

    private void leftMenuAnimation() {

        mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        int i=0, n=mainLayout.getWidth()-200;

        if (mainLayout.getX() > 0) {

            for (; i<=n; i++) {

                mainLayout.animate().x(n-i).setDuration(250).start();
            }
        } else {

            for (; i<=n; i++) {

                mainLayout.animate().x(i).setDuration(250).start();
            }
        }
    }

    public void updateViewProverb() {

        proverbTextView.setText(proverb.getProverb());
        proverbLanguageTextView.setText(proverb.getLanguage());
    }

    public void getProverbsByButton() {

        updateProverbsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                updateViewProverb();
                soundManager.play();
            }
        });
    }

    public void listenerSharedPreferences() {

        prefs = this.getSharedPreferences("proverbs_settings", Context.MODE_PRIVATE);

        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {

            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {

                switch (key) {

                    case "background": {

                        setBackgroundImage();
                        break;
                    }
                    case "color": {

                        setColor();
                        break;
                    }
                    case "period_upload": {

                        execProverbsThread();
                        break;
                    }
                    case "sound": {

                        /*
                        *
                        * NON NE ABBIAMO BISOGNO DI AGGIORNARLA, IN QUANTO
                        * IL LISTENER DEL PULSANTE PRENDE IL SUONO DALLA RISORSA GIÁ AGGIORNATA
                        *
                        */
                        break;
                    }
                    default: {
                    }
                }
            }
        };

        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void initProverbsThread() {

        thread = new Thread(new Runnable() {

            @Override
            public void run() {

                runOnUiThread (new Thread(new Runnable() {

                    public void run() {

                        updateViewProverb();
                    }
                }));
            }
        });

        exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(thread, 0, Long.parseLong(systemSettings.get("period_upload")), TimeUnit.HOURS);
    }

    public void execProverbsThread() {

        if (exec != null)
            exec.shutdown();

        exec = new ScheduledThreadPoolExecutor(1);
        exec.scheduleAtFixedRate(thread, 0, Long.parseLong(systemSettings.get("period_upload")), TimeUnit.HOURS);
    }
}
