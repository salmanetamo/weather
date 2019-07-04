package com.salmane.weather.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.salmane.weather.R;
import com.salmane.weather.models.Location;

public class LocationActivity extends AppCompatActivity {
    public static final String LOCATION = "com.salmane.weather.activities.LOCATION";

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //Get location info from intent
        readLocationInfo();

        //Getting views to update
        TextView textLocationName = (TextView) findViewById(R.id.text_location_name);
        TextView textCountry = (TextView) findViewById(R.id.text_country);
        TextView textTemperature = (TextView) findViewById(R.id.text_temperature);

        //Setting location data to be displayed on the screen
        displayLocation(textLocationName, textCountry, textTemperature);
    }

    private void displayLocation(TextView textLocationName, TextView textCountry, TextView textTemperature) {
        //Setting information to display
        textLocationName.setText(location.getName());

        textCountry.setText(location.getCountry());

        textTemperature.setText("" + location.getTemperature() + "\u2103");
    }

    private void readLocationInfo() {
        Intent intent = getIntent();
        location = intent.getParcelableExtra(this.LOCATION);
    }
}
