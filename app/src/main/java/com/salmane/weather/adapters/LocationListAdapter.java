package com.salmane.weather.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.salmane.weather.R;
import com.salmane.weather.activities.LocationActivity;
import com.salmane.weather.activities.LocationListActivity;
import com.salmane.weather.models.Location;

import java.util.List;

/**
 * @author Salmane Tamo | Student ID: S1719038
 *
 * Adapter class to display elements in RecyclerView.
 * */
public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    private List<Location> mLocations;

    public LocationListAdapter(List<Location> locations){
        this.mLocations = locations;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textLocationName;
        public TextView textCountry;
        public TextView textTemperature;
        public ImageView imageBackgroundListItem;
        private Context context;

        public ViewHolder(Context context, View itemView){
            super(itemView);

            this.context = context;
            textLocationName = (TextView) itemView.findViewById(R.id.text_location_name);
            textCountry = (TextView) itemView.findViewById(R.id.text_country);
            textTemperature = (TextView) itemView.findViewById(R.id.text_temperature);
            imageBackgroundListItem = (ImageView) itemView.findViewById(R.id.image_background_list_item);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Location location = mLocations.get(position);
                System.out.println(location.getName() + "clicked");
                Toast.makeText(context, location.getName() + "clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this.context, LocationActivity.class);

                intent.putExtra(LocationActivity.LOCATION, location);

                context.startActivity(intent);
            }
        }
    }

    @NonNull
    @Override
    public LocationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View locationView = inflater.inflate(R.layout.item_location_list, parent, false);

        return new ViewHolder(context, locationView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListAdapter.ViewHolder viewHolder, int position) {
        Location location = mLocations.get(position);

        TextView textLocationName = (TextView) viewHolder.textLocationName;
        textLocationName.setText(location.getName());

        TextView textCountry = (TextView) viewHolder.textCountry;
        textCountry.setText(location.getCountry());

        TextView textTemperature = (TextView) viewHolder.textTemperature;
        textTemperature.setText("" + location.getTemperature() + "\u2103");

        ImageView imageBackgroundListItem = (ImageView) viewHolder.imageBackgroundListItem;
        if(location.getCountry() == "Niger" || location.getCountry() == "Burkina" ){
            imageBackgroundListItem.setBackgroundResource(R.drawable.sunny);
        } else if (location.getCountry() == "Canada" || location.getCountry() == "Finland" ){
            imageBackgroundListItem.setBackgroundResource(R.drawable.snowy);
        } else if(location.getCountry() == "Lybia" || location.getCountry() == "Mauritius" ){
            imageBackgroundListItem.setBackgroundResource(R.drawable.rainy);
        } else {
            imageBackgroundListItem.setBackgroundResource(R.drawable.cloudy);
        }
    }

    @Override
    public int getItemCount() {
        return this.mLocations.size();
    }
}
