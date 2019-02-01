package com.barebrains.gyanith19;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class gyanith19 extends Application {
    @Override
    public void onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate();

    }
}
