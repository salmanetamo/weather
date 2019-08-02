package com.salmane.weather.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.salmane.weather.R;
import com.salmane.weather.models.Day;
import com.salmane.weather.models.Location;

import java.io.InputStream;

/**
 * Activity class to handle and display weather information for a specific day.
 *
 * @author Salmane Tamo | Student ID: S1719038
 *
 * */
public class DayActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String LOCATION = "com.salmane.weather.activities.LOCATION";
    public static final String DAY_INDEX = "com.salmane.weather.activities.DAY_INDEX";

    private Day day;
    private Location location;

    private TextView textDayName;
    private TextView textTemperature;
    private TextView textDescription;
    private TextView textHumidity;
    private TextView textUvRisk;
    private TextView textWindSpeed;
    private TextView textPressure;
    private TextView textMaxTemperature;
    private TextView textMinTemperature;
    private TextView textVisibility;
    private TextView textWindDirection;

    private ImageView imageDescription;

    private Button buttonNextDay;
    private Button buttonPreviousDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get location info from intent
        readDayInfo();

        //Initializing widgets to update
        textDayName = (TextView) findViewById(R.id.text_name);
        textTemperature = (TextView) findViewById(R.id.text_temperature);
        textDescription = (TextView) findViewById(R.id.text_description);
        textHumidity = (TextView) findViewById(R.id.text_humidity_value);
        textUvRisk = (TextView) findViewById(R.id.text_uv_risk_value);
        textWindSpeed = (TextView) findViewById(R.id.text_wind_speed_value);
        textPressure = (TextView) findViewById(R.id.text_pressure_value);
        textMaxTemperature = (TextView) findViewById(R.id.text_max_temperature_value);
        textMinTemperature = (TextView) findViewById(R.id.text_min_temperature_value);
        textVisibility = (TextView) findViewById(R.id.text_visibility_value);
        textWindDirection = (TextView) findViewById(R.id.text_wind_direction_value);

        imageDescription = (ImageView) findViewById(R.id.image_description);

        buttonNextDay = (Button) findViewById(R.id.button_next_day);
        buttonPreviousDay = (Button) findViewById(R.id.button_previous_day);

        //Setting values to be displayed
        displayDayInfo();
    }

    private void displayDayInfo() {
        textDayName.setText(day.getName());
        textTemperature.setText(day.getMinTemperature());
        textDescription.setText(day.getDescription());
        textHumidity.setText(day.getHumidity());
        textUvRisk.setText(day.getUvRisk());
        textWindSpeed.setText(day.getWindSpeed());
        textPressure.setText(day.getPressure());
        textMaxTemperature.setText(day.getMaxTemperature());
        textMinTemperature.setText(day.getMinTemperature());
        textVisibility.setText(day.getVisibility());
        textWindDirection.setText(day.getWindDirection());

        new DownloadImageTask(imageDescription).execute(day.getImageUrl());

        buttonNextDay.setOnClickListener(this);
        buttonPreviousDay.setOnClickListener(this);
        if(this.day.getIndex() > 1){
            buttonNextDay.setEnabled(false);
        }

        if(this.day.getIndex() < 1){
            buttonPreviousDay.setEnabled(false);
        }
    }

    private void readDayInfo() {
        Intent intent = getIntent();
        int dayIndex = intent.getIntExtra(this.DAY_INDEX, 0);
        this.location = intent.getParcelableExtra(LOCATION);
        this.day = location.getDays()[dayIndex];
    }

    @Override
    public void onClick(View view) {
        if(view == buttonNextDay){
            Intent intent = new Intent(this, this.getClass());

            if(this.day.getIndex() < 2){
                intent.putExtra(this.LOCATION, this.location);
                intent.putExtra(this.DAY_INDEX, this.day.getIndex() + 1);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No next day", Toast.LENGTH_SHORT).show();
            }
        } else if(view == buttonPreviousDay){
            Intent intent = new Intent(this, this.getClass());

            if(this.day.getIndex() > 0){
                intent.putExtra(this.LOCATION, this.location);
                intent.putExtra(this.DAY_INDEX, this.day.getIndex() - 1);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No previous day", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView image;
        public DownloadImageTask(ImageView image) {
            this.image = image;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlString = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urlString).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            if(result == null){
                result = ((BitmapDrawable) getResources().getDrawable(R.drawable.no_image, image.getContext().getTheme())).getBitmap();
            }

            Bitmap imageRounded = Bitmap.createBitmap(result.getWidth(), result.getHeight(), result.getConfig());
            Canvas canvas = new Canvas(imageRounded);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(result, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawRoundRect((new RectF(0, 0, result.getWidth(), result.getHeight())), 10, 10, paint);

            //Setting the image to rounded image
            image.setImageBitmap(imageRounded);
        }
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

