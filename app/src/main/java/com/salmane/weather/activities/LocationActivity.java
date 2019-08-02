package com.salmane.weather.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.salmane.weather.R;
import com.salmane.weather.adapters.LocationPagerAdapter;
import com.salmane.weather.data.DataHandler;
import com.salmane.weather.models.Location;

import java.util.concurrent.ExecutionException;

/**
 * Activity class to handle and display weather information for a specific location.
 *
 * @author Salmane Tamo | Student ID: S1719038
 *
 * */
public class LocationActivity extends AppCompatActivity {
    public static final String LOCATION = "com.salmane.weather.activities.LOCATION";

    private Location location;
    private ViewPager viewPagerDayInfo;
    private TabLayout layoutTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get location info from intent
        readLocationInfo();

        //Widgets to manipulate
        viewPagerDayInfo = (ViewPager) findViewById(R.id.view_pager_day_info);
        layoutTabs = (TabLayout) findViewById(R.id.layout_tabs);
        LocationPagerAdapter locationPagerAdapter = new LocationPagerAdapter(getSupportFragmentManager(), this.location);
        viewPagerDayInfo.setAdapter(locationPagerAdapter);
        layoutTabs.setupWithViewPager(viewPagerDayInfo);

        //Getting views to update
        TextView textLocationName = (TextView) findViewById(R.id.text_location_name);
        textLocationName.setText(this.location.getName());
    }


    private void readLocationInfo() {
        Intent intent = getIntent();
        location = intent.getParcelableExtra(this.LOCATION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_location, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings not available", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.next_location:
                Intent intent = new Intent(this, this.getClass());

                if(this.location.getNextLocation() != null){
                    intent.putExtra(this.LOCATION, this.location.getNextLocation());
                    startActivity(intent);
                } else {
                    try {
                        intent.putExtra(this.LOCATION, DataHandler.getLocations().get(0));
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
