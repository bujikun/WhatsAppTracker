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

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {

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
