package com.salmane.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * This class stores information about the different locations whose weather info is displayed.
 *
 * @author Salmane Tamo | Student ID: S1719038
 *
 * */
public class Location implements Parcelable {
    private String name;
    private String countryCode;
    private String longitude;
    private String latitude;
    private Location nextLocation;
    private Day[] days;

    public Location(String name, String countryCode, String longitude, String latitude, Location nextLocation, Day[] days){
        this.name = name;
        this.countryCode = countryCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.nextLocation = nextLocation;
        this.days = days;
    }

    public Location(String name, String countryCode, String longitude, String latitude, Day[] days){
        this.name = name;
        this.countryCode = countryCode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.nextLocation = null;
        this.days = days;
    }

    private Location(Parcel source){
        this.name = source.readString();
        this.countryCode = source.readString();
        this.longitude = source.readString();
        this.latitude = source.readString();
        this.nextLocation = source.readParcelable(this.getClass().getClassLoader());
        this.days = new Day[3];
        source.readTypedArray(days, Day.CREATOR);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Day[] getDays() {
        return days;
    }

    public void setDays(Day[] days) {
        this.days = days;
    }

    public Location getNextLocation() {
        return nextLocation;
    }

    public void setNextLocation(Location nextLocation) {
        this.nextLocation = nextLocation;
    }

    /**
     * Function from the Parcelable interface
     * */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Function from the Parcelable interface
     * */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.countryCode);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeParcelable(this.nextLocation, 0);
        dest.writeTypedArray(this.days, 0);
    }

    //Static variable used by Parcelable interface
    public final static Parcelable.Creator<Location> CREATOR =
            new Parcelable.Creator<Location>(){

                @Override
                public Location createFromParcel(Parcel source) {
                    return new Location(source);
                }

                @Override
                public Location[] newArray(int size) {
                    return new Location[size];
                }
            };
}
