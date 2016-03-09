package com.appetite;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class ChefUserMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Firebase locRef;
    String chef_name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_user_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        String stringUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=Santa+Clara,CA&destination=San+Francisco,CA&key=AIzaSyCZOwTuuqtZCpy0SS_Y_KERUnZtwXl_MuE";
//        Kavitha's part - chef's location
//        Bundle bundle = getIntent().getExtras();
//        chef_name = bundle.getString("chef_name");
//
//        locRef = new Firebase("https://app-etite.firebaseio.com/userInfo/chef");
//        locRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                String location = dataSnapshot.child(chef_name).child("address").getValue().toString();
//                String latitude = dataSnapshot.child(chef_name).child("latitude").getValue().toString();
//                String longitude = dataSnapshot.child(chef_name).child("longitude").getValue().toString();
//
//                System.out.println("LOC: " + " " + location + " "+ latitude + " " +longitude);
//
//            }

//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            new RequestHandler(this).execute(stringUrl);
        } else {
            // display error
            Log.d("no connection", "No network connnection available");
        }

    }

    public void requestCompletedCallBack(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONObject route = jObject.getJSONArray("routes").getJSONObject(0);
            JSONObject overviewPolyline = route.getJSONObject("overview_polyline");
            String line = overviewPolyline.getString("points");
            List<LatLng> decodedPath = PolyUtil.decode(line);

            mMap.addPolyline(new PolylineOptions().addAll(decodedPath));

            JSONObject bounds =  route.getJSONObject("bounds");
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            //Adding the bounds from the JSON to the buider
            LatLng latLngNorthEast = new LatLng(bounds.getJSONObject("northeast").getDouble("lat"), bounds.getJSONObject("northeast").getDouble("lng"));
            builder.include(latLngNorthEast);
            LatLng latLngSouthWest = new LatLng(bounds.getJSONObject("southwest").getDouble("lat"), bounds.getJSONObject("southwest").getDouble("lng"));
            builder.include(latLngSouthWest);
            LatLngBounds bound = builder.build();

            //To move the camera to the bouds built
            int padding = 40;
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bound, padding);
            mMap.moveCamera(cu);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e ) {
            e.printStackTrace();

        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        String LINE = "rvumEis{y[}DUaBGu@EqESyCMyAGGZGdEEhBAb@DZBXCPGP]Xg@LSBy@E{@SiBi@wAYa@AQGcAY]I]KeBm@_Bw@cBu@ICKB}KiGsEkCeEmBqJcFkFuCsFuCgB_AkAi@cA[qAWuAKeB?uALgB\\eDx@oBb@eAVeAd@cEdAaCp@s@PO@MBuEpA{@R{@NaAHwADuBAqAGE?qCS[@gAO{Fg@qIcAsCg@u@SeBk@aA_@uCsAkBcAsAy@AMGIw@e@_Bq@eA[eCi@QOAK@O@YF}CA_@Ga@c@cAg@eACW@YVgDD]Nq@j@}AR{@rBcHvBwHvAuFJk@B_@AgAGk@UkAkBcH{@qCuAiEa@gAa@w@c@o@mA{Ae@s@[m@_AaCy@uB_@kAq@_Be@}@c@m@{AwAkDuDyC_De@w@{@kB_A}BQo@UsBGy@AaA@cLBkCHsBNoD@c@E]q@eAiBcDwDoGYY_@QWEwE_@i@E}@@{BNaA@s@EyB_@c@?a@F}B\\iCv@uDjAa@Ds@Bs@EyAWo@Sm@a@YSu@c@g@Mi@GqBUi@MUMMMq@}@SWWM]C[DUJONg@hAW\\QHo@BYIOKcG{FqCsBgByAaAa@gA]c@I{@Gi@@cALcEv@_G|@gAJwAAUGUAk@C{Ga@gACu@A[Em@Sg@Y_AmA[u@Oo@qAmGeAeEs@sCgAqDg@{@[_@m@e@y@a@YIKCuAYuAQyAUuAWUaA_@wBiBgJaAoFyCwNy@cFIm@Bg@?a@t@yIVuDx@qKfA}N^aE@yE@qAIeDYaFBW\\eBFkANkANWd@gALc@PwAZiBb@qCFgCDcCGkCKoC`@gExBaVViDH}@kAOwAWe@Cg@BUDBU`@sERcCJ{BzFeB";

        List<LatLng> decodedPath = PolyUtil.decode(LINE);

        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.8256, 151.2395), 12));*/
    }
}


