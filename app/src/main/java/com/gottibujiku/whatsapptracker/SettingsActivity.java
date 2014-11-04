package com.gottibujiku.whatsapptracker;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.os.Build;
import android.view.MenuItem;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Settings activity for enabling notifications and changing refresh rate
 *
 * @author Newton Bujiku
 * @since August 2014
 */
public class SettingsActivity extends Activity {


    private ActionBar actionBar;
    private static final String SETTINGS="Settings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set up the action bar
        actionBar = getActionBar();
        actionBar.setTitle(SETTINGS);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){

            case android.R.id.home:
                finish();
                return true;

        }



        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends PreferenceFragment {



        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            //Tint the system bars

            SystemBarTintManager tintManager = new SystemBarTintManager(getActivity());
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setTintColor(getResources().getColor(R.color.action_bar));

            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

              getActivity().findViewById(android.R.id.content).setPadding(
                      config.getPixelInsetRight(), config.getPixelInsetTop(true),
                      config.getPixelInsetRight(), 0);

            }

            return ;
        }
    }
}
