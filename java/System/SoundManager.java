package com.valeriogiocondi.user.proverbsapp.System;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {

    private MediaPlayer mediaPlayer;
    SystemSettings systemSettings;
    Context parentContext;


    public SoundManager(Context context) {

        this.parentContext = context;
        systemSettings = new SystemSettings(context);
    }

    public void set(String resource) {

        systemSettings.set("sound", resource);
        play();
    }

    public void play() {

        if (isSoundEnabled()) {

            if (mediaPlayer != null)
                mediaPlayer.stop();

            mediaPlayer = MediaPlayer.create(parentContext, parentContext.getResources().getIdentifier(systemSettings.get("sound"), "raw", parentContext.getPackageName()));
            mediaPlayer.start();
        }
    }

    public boolean isSoundEnabled() {

        return systemSettings.get("sound_enabled").equals("1");
    }
}
