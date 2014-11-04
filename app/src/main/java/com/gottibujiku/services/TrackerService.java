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
package com.gottibujiku.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;

import com.gottibujiku.util.Util;
import com.gottibujiku.whatsapptracker.R;
import com.gottibujiku.whatsapptracker.TrackerApplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * A background service to handle peferences and send notifications to the user
 *
 * @author Newton Bujiku
 * @since August 2014
 */
public class TrackerService extends IntentService {

    private String version;
    private static final String URL = "http://www.whatsapp.com/android";
    private SharedPreferences customPrefs;
    private SharedPreferences defaultPrefs;
    private boolean autoDownloadEnabled;
    private boolean notificationEnabled;


    public TrackerService() {
        super("TrackerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {


        defaultPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        notificationEnabled = defaultPrefs.getBoolean(getResources().
                getString(R.string.enable_notification_key), true);

        try {
            Document doc = Jsoup.connect(URL).get();

            Elements element = doc.select("p.version");

            Elements link = doc.select("a.button");

            version = element.text().substring(8);//+"\n"+link.attr("href") ;
            //   version = "Current Version : " + version.substring(8);


        } catch (Exception e) {
            e.printStackTrace();
        }


        customPrefs = getApplicationContext().getSharedPreferences(
                TrackerApplication.TRACKER, 0);


        //potential for future changes
        //could be set to commit new version each time the app is started

        addWhatsAppVersionToPrefs();


        // prefs.getString(VERSION, version);

        if (!(customPrefs.getString(Util.WHATSAPP_CURRENT_VERSION,
                Util.getWhatsappVersion(getApplicationContext())).equals(version)) && (version != null)) {

            SharedPreferences.Editor editor = customPrefs.edit();
            editor.putString(Util.WHATSAPP_LATEST_VERSION, version);
            editor.commit();

            // sendNotification(version);
            if (notificationEnabled) {

                sendNotification(version);

            }


        }


    }

    private void addWhatsAppVersionToPrefs() {
        SharedPreferences.Editor editor = customPrefs.edit();
        editor.putString(Util.WHATSAPP_CURRENT_VERSION,
                Util.getWhatsappVersion(getApplicationContext()));
        editor.commit();
    }


    /**
     * send notification to the user asking them if they want to download the latest version
     *
     * @param version -version number to be displayed
     */
    private void sendNotification(String version) {

        Intent intent = Util.getDownloadIntent(getApplicationContext());
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT

        );


        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Notification.BigTextStyle style = new Notification.BigTextStyle();
        builder.setStyle(style);
        builder.setAutoCancel(true);
        builder.setContentTitle("New WhatsApp Version");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentText("Version " + version + " is now available.");
        builder.setContentIntent(pendingIntent);
        builder.setSubText("Click to download");
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());


    }


}


