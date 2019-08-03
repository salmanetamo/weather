package com.salmane.weather.fragments;

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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.salmane.weather.R;
import com.salmane.weather.activities.DayActivity;
import com.salmane.weather.models.Day;
import com.salmane.weather.models.Location;

import java.io.InputStream;

/**
 * This fragment is used to handle and display of day specific weather info in a single location
 * activity.
 *
 * @author Salmane Tamo | Student ID: S1719038
 *
 * */
public class FragmentDay extends Fragment implements View.OnClickListener {

    private Location location;
    protected Day day;
    protected Button buttonSeeMore;
    protected TextView textTemperature;
    protected TextView textDescription;
    protected TextView textHumidity;
    protected TextView textUvRisk;
    protected TextView textWindSpeed;
    protected TextView textPressure;
    protected ImageView imageDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_day, container, false);

        this.location = this.getArguments().getParcelable(DayActivity.LOCATION);
        this.day = location.getDays()[this.getArguments().getInt(DayActivity.DAY_INDEX)];

        buttonSeeMore = (Button) view.findViewById(R.id.button_see_more);
        buttonSeeMore.setOnClickListener(this);

        textTemperature = (TextView) view.findViewById(R.id.text_temperature);
        textDescription = (TextView) view.findViewById(R.id.text_description);
        textHumidity = (TextView) view.findViewById(R.id.text_humidity_value);
        textUvRisk = (TextView) view.findViewById(R.id.text_uv_risk_value);
        textWindSpeed = (TextView) view.findViewById(R.id.text_wind_speed_value);
        textPressure = (TextView) view.findViewById(R.id.text_pressure_value);

        textTemperature.setText(day.getMinTemperature());
        textDescription.setText(day.getDescription());
        textHumidity.setText(day.getHumidity());
        textUvRisk.setText(day.getUvRisk());
        textWindSpeed.setText(day.getWindSpeed());
        textPressure.setText(day.getPressure());

        imageDescription = (ImageView) view.findViewById(R.id.image_description);
        System.out.println("url of image " +day.getImageUrl() );
        new DownloadImageTask(imageDescription).execute(day.getImageUrl());

        // Inflate the layout for this fragment
        return view;
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
                result = ((BitmapDrawable) getResources().getDrawable(R.drawable.no_image, getContext().getTheme())).getBitmap();
            }
            Bitmap.Config config = result.getConfig() != null ? result.getConfig() : Bitmap.Config.ARGB_8888;
            Bitmap imageRounded = Bitmap.createBitmap(result.getWidth(), result.getHeight(), config);
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
    public void onClick(View view){
        if(view == buttonSeeMore){
            Intent intent = new Intent(this.getContext(), DayActivity.class);
            intent.putExtra(DayActivity.LOCATION, this.location);
            intent.putExtra(DayActivity.DAY_INDEX, this.day.getIndex());
            this.startActivity(intent);
        }
    }

    public Day getDay() {
        return this.day;
    }
}
