package com.gottibujiku.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Util class for handling various static methods
 *
 * @author Newton Bujiku
 * @since August 2014
 */
public class Util {

    private static  final String WHATSAPP = "com.whatsapp";
    public static final String WHATSAPP_CURRENT_VERSION ="current_version" ;
    public static final String  WHATSAPP_LATEST_VERSION = "latest_version";
    public static final String  DOWNLOAD_LINK = "http://www.whatsapp.com/android/current/WhatsApp.apk";
    public static  final  String CHROME="com.android.chrome";

    /**
     *
     * This method fetches the version of the currently installed whatsapp on a device
     *
     * @param context -context of the application/activity
     * @return version of the current whatsapp installation
     */
    public static String getWhatsappVersion(Context context) {

        String whatsappVersion=null;

        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(WHATSAPP,0);

            whatsappVersion = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            whatsappVersion =" WHATSAPP IS NOT INSTALLED";
        }


        return whatsappVersion;
    }

    /**
     * Launches a browser so that the user can download the latest version of whatsapp
     * @param context - -context of the application/activity
     */
    public static  void downloadNewVersion(Context context){

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(DOWNLOAD_LINK));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
           context.getPackageManager().getPackageInfo(CHROME,0);
            intent.setPackage(CHROME);
            context.startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            context.startActivity(Intent.createChooser(intent,"Open With : "));
        }



    }

    /**
     *  Prepares an intent for download and returns it
     *
     * @param context--context of the application/activity
     * @return an intent to launch a browser
     */
    public static  Intent getDownloadIntent(Context context){

       Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(DOWNLOAD_LINK));
        try {
            context.getPackageManager().getPackageInfo(CHROME,0);
            intent.setPackage(CHROME);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            context.startActivity(Intent.createChooser(intent,"Open With : "));
        }

       return intent;

    }

}
