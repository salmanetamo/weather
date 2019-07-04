package com.salmane.weather.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.salmane.weather.R;
import com.salmane.weather.adapters.LocationListAdapter;
import com.salmane.weather.models.Location;

import java.util.ArrayList;

public class LocationListActivity extends AppCompatActivity {

    ArrayList<Location> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_locations);

        locations = Location.getLocations();

        LocationListAdapter locationListAdapter = new LocationListAdapter(locations);

        recyclerView.setAdapter(locationListAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
