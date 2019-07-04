package com.salmane.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Location implements Parcelable {
    private String name;
    private String country;
    private int temperature;

    public Location(String name, String country, int temperature){
        this.name = name;
        this.country = country;
        this.temperature = temperature;
    }

    private Location(Parcel source){
        this.name = source.readString();
        this.country = source.readString();
        this.temperature = source.readInt();
    }

    public static ArrayList<Location> getLocations(){
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location("Niamey Yantala", "Niger", 45));
        locations.add(new Location("Tripoli, Khadafi's country", "Lybia", 49));
        locations.add(new Location("Helsinki, Casa de Papel", "Finland", -18));
        locations.add(new Location("Ouagadougou Tiendrebeogo", "Burkina", 3));
        locations.add(new Location("Ottawa Montreal", "Canada", -8));
        locations.add(new Location("Antananarivo", "Madagascar", -18));
        locations.add(new Location("Port Louis", "Mauritius", 22));
        locations.add(new Location("Mombasa", "Kenya", 32));

        return locations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
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
        dest.writeString(this.country);
        dest.writeInt(this.temperature);
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
