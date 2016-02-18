package com.example.kavi.firebasetest;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


public class MainActivity extends AppCompatActivity {

    TextView fireData;
    Firebase mRef;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        fireData = (TextView) findViewById(R.id.firedata);
        image = (ImageView) findViewById(R.id.imgView);

        mRef = new Firebase("https://glowing-inferno-7535.firebaseio.com/appetite");

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //BlogPost post = postSnapshot.getValue(BlogPost.class);

                    Log.d("Fetched values: ", postSnapshot.child("birthYear").getValue() + " - " + postSnapshot.child("fullName").getValue() + " - " +
                            postSnapshot.child("ImgPath").getValue());
                    if (postSnapshot.child("fullName").getValue() != null && postSnapshot.child("ImgPath").getValue() != null) {
                        fireData.setText(postSnapshot.child("fullName").getValue().toString());

                        String imgString = postSnapshot.child("ImgPath").getValue().toString();
                        byte[] imageAsBytes = Base64.decode(imgString.getBytes(), 0);
                        image.setImageBitmap(BitmapFactory.decodeByteArray(
                                imageAsBytes, 0, imageAsBytes.length));
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
