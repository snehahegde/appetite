package com.appetite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Sneha on 2/15/2016.
 */
public class ChefAdapter extends ArrayAdapter<Chef> {

    private List<Chef> chefList;
    private double mLatitude;
    private double mLongitude;

    public ChefAdapter(Context context, int resource, List<Chef> chefList, double latitude, double longitude) {
        super(context, resource, chefList);
        this.chefList = chefList;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public StringBuilder drawStars(int rating) {
        StringBuilder stars = new StringBuilder();
        for ( int i = 0; i < rating; i++ ) {
            stars.append('*');
        }
        return stars;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Chef chef = chefList.get(position);

        View v = convertView;

        if( v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(com.appetite.R.layout.chef_single_row, null);
        }

        //Set the text
        TextView textView = (TextView)v.findViewById(com.appetite.R.id.rowtext);
        textView.setText(chef.getChefName());

        //Set the review stars
        TextView reviewTextView = (TextView)v.findViewById(com.appetite.R.id.rowreview);
        reviewTextView.setText(drawStars(chef.getRating()));

        //Set the distance

        double chefLattitude= chef.getLatitude();
        double chefLongitude = chef.getLongitude();

        double distance = Math.sqrt((Math.pow(mLatitude - chefLattitude, 2) + (Math.pow(mLongitude - chefLongitude, 2))));
        TextView distanceTextView = (TextView)v.findViewById(R.id.rowdistance);
        //String distanceStr = String.valueOf(distance*69);
        String distanceStr = String.format("%.2f mi", distance * 69);
        distanceTextView.setText(distanceStr);

        return v;
    }

}
