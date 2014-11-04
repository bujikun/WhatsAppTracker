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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gottibujiku.fragments.AboutDialog;
import com.gottibujiku.util.Util;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * This class serves as the first screen that the user is greeted with when they
 * open the application
 *
 * @author Newton Bujiku
 * @since August 2014
 */

public class MainActivity extends Activity {


    private boolean resumed;//for checking if the activity is in front and running
    private TextView tv;
    private Button button;
    private TextView currentVersion, latestVersion;//display versions
    private String version;
    private SharedPreferences prefs;
    private Resources resources;
    private static final String INFO = "Now you can easily keep track of the latest versions " +
            "of WhatsApp as they are uploaded to the WhatsApp's official website.";
    private static final String VERSION = "version";

    private String whatsappVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up the screen
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.info);
        button = (Button) findViewById(R.id.download);
        currentVersion = (TextView) findViewById(R.id.current_version);
        latestVersion = (TextView) findViewById(R.id.latest_version);

        latestVersion.setText("Latest Version : loading...");
        resources = getResources();

        //get ready to tint the system bars
        SystemBarTintManager tintManager = new SystemBarTintManager(this);

        tintManager.setTintColor(resources.getColor(R.color.action_bar));

        //tint system bars
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintEnabled(true);

        tintManager.setTintDrawable(getResources().getDrawable(R.drawable.action_bar));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //alert the user to wait if they click the download button
                //while checking whatsapp versions
                if (latestVersion.getText().equals("Latest Version : loading...")) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Checking...Please Wait.",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                if (Util.getWhatsappVersion(getApplicationContext()).equals(version)) {


                    //if the latest whatsapp version at whatsapp.com is the same as the one
                    //installed  display toast

                    Toast.makeText(
                            getApplicationContext(),
                            "You have the latest version already...Take A Chill Pill!",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;

                }

                //if not then let's get the latest version
                Util.downloadNewVersion(getApplicationContext());


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean notificationEnabled = prefs.getBoolean(resources.getString(
                R.string.enable_notification_key), true);

        //set default refresh rate to 12 hours
        String rate = prefs.getString(resources.getString(R.string.refresh_rate_key), "12");

        String autoDownload, notification;


        notification = (notificationEnabled) ? "Notifications are enabled." : "Notifications are disabled.";

        tv.setText(INFO + "\n\n" + "\n" + notification + "\nRefresh rate is set to " + rate + " Hours.");


    }

    @Override
    protected void onResume() {
        super.onResume();

        //start task to check the latest version at whatsapp.com
        TrackerTask task = new TrackerTask();
        task.execute();

        currentVersion.setText("Current Version : " + Util.getWhatsappVersion(getApplicationContext()));
        //set resumed to true so that when the task finishes it doesnt bring up the paused activity
        //and intefere with user experience
        resumed = true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        resumed = false;//the activity is no longer active
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;

            case R.id.action_about:
                AboutDialog dialog = new AboutDialog();
                dialog.show(getFragmentManager(), "About Dialog");
                return true;

        }

        return super.onOptionsItemSelected(item);

    }


    public class TrackerTask extends AsyncTask<Void, Void, String> {

        //This task checks for the latest version number of whatsapp from it's official
        //website and updates the UI with new information
        String version;

        @Override
        protected String doInBackground(Void... params) {

            try {
                //connect to whatsapp.com
                Document doc = Jsoup.connect("http://www.whatsapp.com/android").get();

                Elements element = doc.select("p.version");


                Elements link = doc.select("a.button");

                version = element.text().substring(8);//+"\n"+link.attr("href") ;
                //   version = "Current Version : " + version.substring(8);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return version;
        }


        @Override
        protected void onPostExecute(String version) {
            super.onPostExecute(version);

            if (resumed && version != null) {
                //if the activity is in front and there was internet connection
                latestVersion.setText("Latest Version : " + version);
                MainActivity.this.version = version;
                TrackerApplication.setHasRun();
            } else {
                latestVersion.setText("No internet connection...");
            }


        }
    }
}
