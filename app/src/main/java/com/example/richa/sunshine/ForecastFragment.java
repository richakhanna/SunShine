package com.example.richa.sunshine;

/**
 * Created by richa on 19/7/14.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A Forecast fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {


    private ArrayAdapter<String> mForecastAdapter;
    private ListView listview;
    public final static String EXTRA_MESSAGE = "com.example.richa.sunshine.FORECAST";

    public ForecastFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //Now that we have some dummy forecast data,create an ArrayAdapter
        //The ArrayAdapter will take data from a source(like our dummy  forecast)
        //Use it to populate the ListView it's attached to.

        mForecastAdapter =
                new ArrayAdapter<String>(
                        //The Current context (this fragment's parent activity)
                        getActivity(),
                        //list Item sample resource
                        R.layout.list_item_forecast,
                        //Id of textview to populate
                        R.id.list_item_forecast_textview,
                        // Forecast Data
                        new ArrayList<String>());


        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        //Get a reference to the ListView, and attach this adapter to it.
        listview = (ListView) rootView.findViewById(R.id.listview_forecast);
        listview.setAdapter(mForecastAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String forecast = mForecastAdapter.getItem(i);
                // Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(EXTRA_MESSAGE, forecast);
                startActivity(intent);
            }
        });


        return rootView;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.forecastfragment, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateWeather() {
        FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity(), mForecastAdapter);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //To read the value of the location preference from sharedPref file.
        String location = sharedPref.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));

        weatherTask.execute(location);
    }


    //To display the data according to the current settings whenever fragment starts
    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

}

