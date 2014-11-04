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
package com.gottibujiku.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Util class for handling various static methods
 *
 * @author Newton Bujiku
 * @since August 2014
 */
public class Util {

    private static final String WHATSAPP = "com.whatsapp";
    public static final String WHATSAPP_CURRENT_VERSION = "current_version";
    public static final String WHATSAPP_LATEST_VERSION = "latest_version";
    public static final String DOWNLOAD_LINK = "http://www.whatsapp.com/android/current/WhatsApp.apk";
    public static final String CHROME = "com.android.chrome";

    /**
     * This method fetches the version of the currently installed whatsapp on a device
     *
     * @param context -context of the application/activity
     * @return version of the current whatsapp installation
     */
    public static String getWhatsappVersion(Context context) {

        String whatsappVersion = null;

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(WHATSAPP, 0);

            whatsappVersion = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            whatsappVersion = " WHATSAPP IS NOT INSTALLED";
        }


        return whatsappVersion;
    }

    /**
     * Launches a browser so that the user can download the latest version of whatsapp
     *
     * @param context - -context of the application/activity
     */
    public static void downloadNewVersion(Context context) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(DOWNLOAD_LINK));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.getPackageManager().getPackageInfo(CHROME, 0);
            intent.setPackage(CHROME);
            context.startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            context.startActivity(Intent.createChooser(intent, "Open With : "));
        }


    }

    /**
     * Prepares an intent for download and returns it
     *
     * @param context--context of the application/activity
     * @return an intent to launch a browser
     */
    public static Intent getDownloadIntent(Context context) {

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(DOWNLOAD_LINK));
        try {
            context.getPackageManager().getPackageInfo(CHROME, 0);
            intent.setPackage(CHROME);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            context.startActivity(Intent.createChooser(intent, "Open With : "));
        }

        return intent;

    }

    /**
     * This method does comparison between two whatsapp versions.
     * it checks the numbers between . so as to ensure a version  like
     * 2.11.236 is not latest when compared to 2.13.246
     *
     * A fix to what was used before to compare versions by checking their
     * differences assuming always versions from whatsapp.com are latest
     *
     * Not the best algorithm though it works
     *
     * @param installedVersion - the version of installed WhatsApp
     * @param testingVersion - the version retrieved from whatsapp.com
     * @return true if installed version is latest,false otherwise
     */
    public static  boolean installedIsLatestVersion(String installedVersion,String testingVersion)
            throws  Exception{

        if(installedVersion==null || testingVersion==null){
            //throw an exception if one of the arguments is null
            //we don't wanna tokenize a null string that would be catastrophic
            throw new Exception();
        }
        //Split the versions using the points
        StringTokenizer tokenOne = new StringTokenizer(installedVersion,".");
        StringTokenizer tokenTwo = new StringTokenizer(testingVersion,".");

        //these will hold the separated values for comparison
        ArrayList<String> arrayOne = new ArrayList<String>(),arrayTwo= new ArrayList<String>();

        while(tokenOne.hasMoreTokens()){

            arrayOne.add(tokenOne.nextToken());//add tokens to the first array
        }


        while (tokenTwo.hasMoreTokens()){
            arrayTwo.add(tokenTwo.nextToken());//add tokens to the second array
        }

        int one,two;

        for (int i=0;i<arrayOne.size();++i){

            one = Integer.parseInt(arrayOne.get(i));
            two = Integer.parseInt(arrayTwo.get(i));

            if (one>two){
                return true;
                //return true if any of the tokens from installed version is greater than that
                //of the testing version
            }


        }
        return false;

    }

}
