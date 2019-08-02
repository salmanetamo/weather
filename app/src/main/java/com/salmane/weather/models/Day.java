package com.salmane.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This model class is used to define all the information that will be displayed in the app.
 *
 * @author Salmane Tamo | Student ID: S1719038
 *
 * */
public class Day implements Parcelable {
    private String name;
    private String imageUrl;
    private String maxTemperature;
    private String minTemperature;
    private String description;
    private String windDirection;
    private String windSpeed;
    private String visibility;
    private String pressure;
    private String humidity;
    private String uvRisk;
    private int index;

    public Day(int index, String name, String imageUrl,String maxTemperature, String minTemperature,
               String description, String windDirection, String windSpeed,String visibility,
               String pressure, String humidity, String uvRisk){

        this.index = index;
        this.name = name;
        this.imageUrl = imageUrl;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.description = description;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
        this.visibility = visibility;
        this.pressure = pressure;
        this.humidity = humidity;
        this.uvRisk = uvRisk;
    }

    private Day(Parcel source){
        this.index = source.readInt();
        this.name = source.readString();
        this.imageUrl = source.readString();
        this.maxTemperature = source.readString();
        this.minTemperature = source.readString();
        this.description = source.readString();
        this.windDirection = source.readString();
        this.windSpeed = source.readString();
        this.visibility = source.readString();
        this.pressure = source.readString();
        this.humidity = source.readString();
        this.uvRisk = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Function from the Parcelable interface
     * */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeString(this.name);
        dest.writeString(this.imageUrl);
        dest.writeString(this.maxTemperature);
        dest.writeString(this.minTemperature);
        dest.writeString(this.description);
        dest.writeString(this.windDirection);
        dest.writeString(this.windSpeed);
        dest.writeString(this.visibility);
        dest.writeString(this.pressure);
        dest.writeString(this.humidity);
        dest.writeString(this.uvRisk);
    }

    //Static variable used by Parcelable interface
    public final static Parcelable.Creator<Day> CREATOR =
            new Parcelable.Creator<Day>(){

                @Override
                public Day createFromParcel(Parcel source) {
                    return new Day(source);
                }

                @Override
                public Day[] newArray(int size) {
                    return new Day[size];
                }
            };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getUvRisk() {
        return uvRisk;
    }

    public void setUvRisk(String uvRisk) {
        this.uvRisk = uvRisk;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
