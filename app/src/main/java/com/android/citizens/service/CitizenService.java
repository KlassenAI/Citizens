package com.android.citizens.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.android.citizens.model.Citizen;
import com.android.citizens.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class CitizenService extends Service {

    private static final String DEFAULT_PERIOD = "10";
    private static final String DEFAULT_MIN_AGE = "22";
    private static final String DEFAULT_MAX_AGE = "35";

    private final IBinder binder = new CitizenBinder();
    private Thread myThread = new MyThread();
    private List<Citizen> mCitizenList;

    public List<Citizen> getCitizenList() {
        return mCitizenList;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!myThread.isAlive())
            myThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        if (!myThread.isInterrupted()) myThread.interrupt();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private List<Citizen> getCitizens() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        int minAge = Integer.parseInt(preferences.getString("min_age", DEFAULT_MIN_AGE));
        int maxAge = Integer.parseInt(preferences.getString("max_age", DEFAULT_MAX_AGE));
        int size = (int) (Math.random() * 91) + 10;
        List<Citizen> citizens = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            citizens.add(Citizen.getRandom(minAge, maxAge));
            Log.d("MyTag", citizens.get(i).toString());
        }
        return citizens;
    }

    public class CitizenBinder extends Binder {
        public CitizenService getService() {
            return CitizenService.this;
        }
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            while(true) {
                try {
                    SharedPreferences preferences = PreferenceManager
                            .getDefaultSharedPreferences(getApplicationContext());
                    int period = 10;
                    // int period = Integer.parseInt(preferences.getString("period", DEFAULT_PERIOD));
                    mCitizenList = getCitizens();
                    sleep(period * 1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
