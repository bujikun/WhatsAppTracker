package com.gottibujiku.whatsapptracker;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.gottibujiku.receivers.TrackerReceiver;
import com.gottibujiku.services.TrackerService;

/**
 * The class representing the application itself.
 * It serves is keeping global variables and doing universal operations
 *
 * @author Newton Bujiku
 * @since August 2014
 */
public class TrackerApplication extends Application {

    private static final String FIRST_RUN ="first_run" ;
    public static final String TRACKER="tracker" ;

    private static SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getApplicationContext().getSharedPreferences(TRACKER,0);


        Intent intent = new Intent(getApplicationContext(), TrackerService.class);

        startService(intent);//start the background service
        TrackerReceiver.setAlarm(getApplicationContext());



    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        setHasRun();
    }

    public static boolean firstRun(){

       return prefs.getBoolean(FIRST_RUN,true);
    }

    public static void setHasRun(){

        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(FIRST_RUN,false);

        editor.commit();
    }

}
