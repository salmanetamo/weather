package com.salmane.weather.data;

import com.salmane.weather.models.Day;
import com.salmane.weather.models.Location;

import android.os.AsyncTask;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This class is used to pull the weather information and handles the XML parsing of the data.
 * It makes use of the PullParser class for the parsing.
 *
 * @author Salmane Tamo | Student ID: S1719038
 *
 * */
public class XMLParser extends AsyncTask {
    String urlString;

    public XMLParser(String locationId) {
        this.urlString = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/" + locationId;
    }

    public Location parse() throws IOException, XmlPullParserException {

        //Location specific info
        String locationName = "";
        String countryCode = "";
        String imageUrl = "";
        String longitude = "";
        String latitude = "";

        //Initializing the PullParser
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser pullParser = factory.newPullParser();
        pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

        pullParser.setInput(getInputStream(new URL(urlString)), "UTF_8");
        int eventType = pullParser.getEventType();

        int itemsTraversed = 0;
        Day[] days = new Day[3];

        //Traversing the XML data and parsing it
        while(eventType != XmlPullParser.END_DOCUMENT){
            String currentTag = null;

            if(eventType == XmlPullParser.START_TAG){
                currentTag = pullParser.getName();
                if(currentTag.equals("image")){
                    String[] locationNameCountryCodeImageUrl = getLocationNameImageUrl(pullParser);
                    locationName = locationNameCountryCodeImageUrl[0];
                    countryCode = locationNameCountryCodeImageUrl[1];
                    imageUrl = locationNameCountryCodeImageUrl[2];
                } else if(currentTag.equals("item")){
                    String[] fromTitleTag = getFromTitle(pullParser);
                    String name = fromTitleTag[0];
                    String maxTemperature = fromTitleTag[1];
                    String minTemperature = fromTitleTag[2];
                    String description = fromTitleTag[3];

                    String[] fromDescriptionTag = getFromDescription(pullParser);
                    String windDirection = fromDescriptionTag[0];
                    String windSpeed = fromDescriptionTag[1];
                    String visibility = fromDescriptionTag[2];
                    String pressure = fromDescriptionTag[3];
                    String humidity = fromDescriptionTag[4];
                    String uvRisk = fromDescriptionTag[5];

                    String[] coordinates = getCoordinates(pullParser);
                    longitude = coordinates[0];
                    latitude = coordinates[1];

                    days[itemsTraversed] = new Day(itemsTraversed, name, imageUrl, maxTemperature,
                            minTemperature, description, windDirection,
                            windSpeed, visibility, pressure, humidity, uvRisk);
                    itemsTraversed += 1;
                }
            }

            eventType = pullParser.next();
        }

        return new Location(locationName, countryCode, longitude, latitude, days);
    }

    private static String[] getLocationNameImageUrl(XmlPullParser pullParser) throws XmlPullParserException, IOException {
        String[] nameImgUrl = new String[3];

        String name = "";
        String countryCode = "";
        String imageUrl = "";

        int eventType = pullParser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            String currentTag = null;

            if(eventType == XmlPullParser.START_TAG) {
                currentTag = pullParser.getName();
                if (currentTag.equals("title")) {
                    String[] titleText = pullParser.nextText().substring(28).split(", ");
                    name = titleText[0];
                    countryCode = titleText[1];
                } else if (currentTag.equals("url")){
                    imageUrl = pullParser.nextText();
                }
            }
            if(eventType == XmlPullParser.END_TAG && pullParser.getName().equals("image")){
                break;
            }

            eventType = pullParser.next();
        }

        nameImgUrl[0] = name;
        nameImgUrl[1] = countryCode;
        nameImgUrl[2] = imageUrl;
        return nameImgUrl;
    }

    private static String[] getCoordinates(XmlPullParser pullParser) throws XmlPullParserException, IOException {
        String[] coordinates = new String[2];
        String longitude = "";
        String latitude = "";

        int eventType = pullParser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            String currentTag = null;

            if(eventType == XmlPullParser.START_TAG) {
                currentTag = pullParser.getName();
                if (currentTag.equals("georss:point")) {
                    String[] text = pullParser.nextText().split(" ");
                    longitude = text[0];
                    latitude = text[1];
                    break;
                }
            }

            eventType = pullParser.next();
        }

        coordinates[0] = longitude;
        coordinates[1] = latitude;
        return coordinates;
    }

    private static String[] getFromTitle(XmlPullParser pullParser) throws XmlPullParserException, IOException {
        String[] elements = new String[4];

        String name = "";
        String maxTemperature = "N/A";
        String minTemperature = "";
        String description = "";

        int eventType = pullParser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            String currentTag = null;

            if(eventType == XmlPullParser.START_TAG) {
                currentTag = pullParser.getName();
                if (currentTag.equals("title")) {
                    String[] text = pullParser.nextText().split(": ");
                    name = text[0];
                    minTemperature = text[2].split(" ")[0];
                    description = text[1].split(",")[0];
                    if(text.length > 3){
                        maxTemperature = text[2].split(" ")[0];
                    }
                    break;
                }
            }

            eventType = pullParser.next();
        }

        elements[0] = name;
        elements[1] = maxTemperature;
        elements[2] = minTemperature;
        elements[3] = description;

        return elements;
    }

    private static String[] getFromDescription(XmlPullParser pullParser) throws XmlPullParserException, IOException {
        String[] elements = new String[6];
        String windDirection = "";
        String windSpeed = "";
        String visibility = "";
        String pressure = "";
        String humidity = "";
        String uvRisk = "";

        int eventType = pullParser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            String currentTag = null;

            if(eventType == XmlPullParser.START_TAG) {
                currentTag = pullParser.getName();
                if (currentTag.equals("description")) {
                    String[] text = pullParser.nextText().split(",");
                    int index = 0;
                    if(text.length > 9){index = 1;}
                    windDirection = text[index + 1].split(": ")[1];
                    windSpeed = text[index + 2].split(": ")[1];
                    visibility = text[index + 3].split(": ")[1];
                    pressure = text[index + 4].split(": ")[1];
                    humidity = text[index + 5].split(": ")[1];
                    uvRisk = text[index + 6].split(": ")[1];
                    break;
                }
            }

            eventType = pullParser.next();
        }

        elements[0] = windDirection;
        elements[1] = windSpeed;
        elements[2] = visibility;
        elements[3] = pressure;
        elements[4] = humidity;
        elements[5] = uvRisk;

        return elements;
    }

    // Given a string representation of a URL, sets up a connection and gets an input stream.
    private static InputStream getInputStream(URL url) throws IOException {
        return url.openConnection().getInputStream();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            return parse();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }
}
