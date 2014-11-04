/*
 Copyright (C) 2014 Newton Bujiku

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
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

    private static final String FIRST_RUN = "first_run";
    public static final String TRACKER = "tracker";

    private static SharedPreferences prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = getApplicationContext().getSharedPreferences(TRACKER, 0);


        Intent intent = new Intent(getApplicationContext(), TrackerService.class);

        startService(intent);//start the background service
        TrackerReceiver.setAlarm(getApplicationContext());


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        setHasRun();
    }

    public static boolean firstRun() {

        return prefs.getBoolean(FIRST_RUN, true);
    }

    public static void setHasRun() {

        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(FIRST_RUN, false);

        editor.commit();
    }

}
