package com.salmane.weather.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.salmane.weather.activities.DayActivity;
import com.salmane.weather.fragments.FragmentDay;
import com.salmane.weather.models.Day;
import com.salmane.weather.models.Location;

/**
 * PagerAdapter class to handle and display day specific weather information in fragments.
 *
 * @author Salmane Tamo | Student ID: S1719038
 *
 * */
public class LocationPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentDay[] fragments;

    public LocationPagerAdapter(FragmentManager supportFragmentManager, Location location){
        super(supportFragmentManager);
        fragments = new FragmentDay[3];
        for(int i = 0; i < location.getDays().length; i++){
            Bundle bundle = new Bundle();
            bundle.putParcelable(DayActivity.LOCATION, location);
            bundle.putInt(DayActivity.DAY_INDEX, i);
            FragmentDay fragment = new FragmentDay();
            fragment.setArguments(bundle);
            fragments[i] = fragment;
        }
    }

    @Override
    public Fragment getItem(int index) {
        return fragments[index];
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int index){
        Location location = fragments[index].getArguments().getParcelable(DayActivity.LOCATION);
        int dayIndex = fragments[index].getArguments().getInt(DayActivity.DAY_INDEX);
        return location.getDays()[dayIndex].getName();
    }
}
