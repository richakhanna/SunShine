package com.example.richa.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MyActivity extends ActionBarActivity {

    private final String LOG_TAG= MyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if(id==R.id.action_map){
            openPreferredLocationInMap();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //To read the value of the location from sharedPref file.
        String location = sharedPref.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));


        //Using the Uri scheme provided in Common Intent doc under Map section

        Uri geolocation=Uri.parse("geo:0,0?").buildUpon()
                         .appendQueryParameter("q",location)
                         .build();
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(geolocation);

        //We only start the activity if it resolves successfully
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }else{
            Log.d(LOG_TAG,"Couldn't call " + location );
        }

    }

}
