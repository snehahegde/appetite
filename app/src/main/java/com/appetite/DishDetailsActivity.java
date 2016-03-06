package com.appetite;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.sort;

public class DishDetailsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private Dish dish;
    private Chef chef;
    private List<Chef> chefs;
    DishHelperClass dishHelperClass;
    //private HashMap<String, LatLng> chefsLocations;
    private GoogleApiClient mGoogleApiClient;
    private double mLatitude;
    private double mLongitude;
    private ArrayList<LatLng> pinLocations;
    //private HashMap<>
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.appetite.R.layout.activity_dish_details);
        Toolbar toolbar = (Toolbar) findViewById(com.appetite.R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(com.appetite.R.id.dishImageView);
        imageView.setImageResource(com.appetite.R.drawable.commoncaesarsalad);

        listView = (ListView) findViewById(com.appetite.R.id.chefListView);

        dishHelperClass = DishHelperClass.Create();
        dish = dishHelperClass.getDish("Caeser salad");

        TextView textView = (TextView) findViewById(com.appetite.R.id.dishName);
        textView.setText(dish.getName());

        chefs = dish.getChefsEnrolled();

        if(mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .enableAutoManage(this, this)
                    .build();
        }

        pinLocations = new ArrayList<LatLng>();
        for (Chef chef : chefs) {
            //create a geofence
            if ( true || (chef.getLatitude() > (mLatitude - 0.15)) && (chef.getLatitude() < (mLatitude + 0.15))) {
                if (true || (chef.getLongitude() > (mLongitude - 0.15)) && (chef.getLongitude() < (mLongitude + 0.15))) {
                    //find the distance between the chef and the user
                    //double distance = Math.sqrt((Math.pow(mLatitude - chef.getLatitude(), 2)) + (Math.pow(mLongitude - chef.getLongitude(), 2)));
                    LatLng newPinLocation = new LatLng(chef.getLatitude(), chef.getLongitude());
                    pinLocations.add(newPinLocation);
                    //LatLng newPinLocation = new LatLng(42.3598, 71.0921);
                    pinLocations.add(newPinLocation);
                }
            }
        }

        for(LatLng location : pinLocations) {
            Log.d("lat", String.valueOf(location.latitude));
            Log.d("longitude", String.valueOf(location.longitude));
        }


        //listView.setAdapter(new ChefAdapter(this, com.appetite.R.layout.chef_single_row, chefs));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DishDetailsActivity.this, ChefDetailsActivity.class);
                intent.putExtra("ChefName",chefs.get(position).getChefName());
                intent.putExtra("DishName",dish.getName());
                startActivity(intent);
            }
        });



        final TextView ingredientsDescription = (TextView)findViewById(com.appetite.R.id.ingredientsTextView);
        final ImageButton expandDown = (ImageButton)findViewById(com.appetite.R.id.expandDownButton);
        expandDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandDown.setVisibility(View.INVISIBLE);
                ingredientsDescription.setMaxLines(5);
                ingredientsDescription.setText(dish.getIngredients());
            }
        });

        Button launchMapButton = (Button)findViewById(com.appetite.R.id.mapbutton);
        launchMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DishDetailsActivity.this, MapActivity.class);
                //ArrayList<LatLng> pinLocationList = getPinLocations();
                //intent.putParcelableArrayListExtra("pinLocationExtra", ArrayList<LatLng> pinLocationList);
                //intent.putExtra("pinLocationExtra", pinLocations);
                intent.putExtra("chefsEnrolled", (Serializable) chefs);
                startActivity(intent);
            }
        });
    }

    //converts address to location
    /*private LatLng getLocationFromAddress(String address) {
        return;
    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.appetite.R.menu.menu_dish_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.appetite.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
            Log.d("mylatitude", String.valueOf(mLatitude));
            Log.d("mylongitude", String.valueOf(mLongitude));
        }

        //gets the sorted list
        sort(chefs, new Chef.DistanceComparator(mLatitude, mLongitude));
        listView.setAdapter(new ChefAdapter(this, com.appetite.R.layout.chef_single_row, chefs, mLatitude, mLongitude));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
