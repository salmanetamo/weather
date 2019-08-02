package com.salmane.weather.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.salmane.weather.R;
import com.salmane.weather.adapters.LocationListAdapter;
import com.salmane.weather.data.DataHandler;
import com.salmane.weather.models.Location;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Activity class to handle and display all locations as a list.
 * This is the launcher activity of the app.
 *
 * @author Salmane Tamo | Student ID: S1719038
 *
 * */
public class LocationListActivity extends AppCompatActivity {

    ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_locations);

        try {
            locations = DataHandler.getLocations();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        LocationListAdapter locationListAdapter = new LocationListAdapter(locations);

        recyclerView.setAdapter(locationListAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_day, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings not available", Toast.LENGTH_SHORT).show();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
