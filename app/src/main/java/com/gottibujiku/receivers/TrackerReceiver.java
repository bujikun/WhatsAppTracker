package com.gottibujiku.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gottibujiku.services.TrackerService;
import com.gottibujiku.whatsapptracker.R;

/**
 * Starts the TrackerService when the alarm goes off
 *  @author Newton Bujiku
 * @since August 2014
 */
public class TrackerReceiver extends BroadcastReceiver {
    public TrackerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        context.startService(new Intent(context, TrackerService.class));


    }

    public static void setAlarm(Context context){

        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //set default preferences if the user has touched them yet
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String str = preferences.getString(
                context.getResources().getString(R.string.refresh_rate_key),
                "12"
        );

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                new Intent(context,TrackerReceiver.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        manager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                Long.parseLong(str)*3600*1000,
                pendingIntent

        );

    }

}
