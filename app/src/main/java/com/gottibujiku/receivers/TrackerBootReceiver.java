package com.gottibujiku.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.gottibujiku.services.TrackerService;

/**
 * This will receive the boot completed broadcast and set an alarm after 6 minutes
 *
 * @author Newton Bujiku
 * @since August 2014
 */
public class TrackerBootReceiver extends BroadcastReceiver {
    public TrackerBootReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startService(new Intent(context, TrackerService.class));
                    TrackerReceiver.setAlarm(context);

                }
            }, 300000);




        }


    }



}
