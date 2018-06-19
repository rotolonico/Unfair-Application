package com.example.android.myapplication;

import android.app.Application;

public class UnfairApplication extends Application {
    boolean sound = true;

    public boolean hasSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }
}
