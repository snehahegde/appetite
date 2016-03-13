package com.appetite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kavi on 2/13/16.
 */
public class MenuList extends AppCompatActivity {

    TextView welcometxt;
    TextView item_name,item_price;
    Firebase mRef;
    ImageView image;
    String imageEncoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menulist_layout);
        Firebase.setAndroidContext(this);



        welcometxt=(TextView)findViewById(R.id.welcomeUser);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("uname");
        welcometxt.setText("Welcome " + message + "!!");

        item_name = (TextView) findViewById(R.id.item_name);
        item_price = (TextView) findViewById(R.id.item_price);
        image = (ImageView) findViewById(R.id.imgView);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.caesarsalad);
        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
        bmp.recycle();
        byte[] byteArray = bYtE.toByteArray();
        imageEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        mRef = new Firebase("https://app-etite.firebaseio.com/menulist");

        Map<String, Object> imagesMap = new HashMap<String, Object>();
        imagesMap.put("item1/imageEncoded", imageEncoded);
        imagesMap.put("item2/imageEncoded", imageEncoded);
        imagesMap.put("item3/imageEncoded", imageEncoded);
        mRef.updateChildren(imagesMap);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    if(postSnapshot.child("item_name").getValue()!=null && postSnapshot.child("item_price").getValue()!=null) {
                        item_name.setText(postSnapshot.child("item_name").getValue().toString());
                        item_price.setText(" "+postSnapshot.child("item_price").getValue().toString());

                        String imgString = postSnapshot.child("imageEncoded").getValue().toString();
                        byte[] imageAsBytes = Base64.decode(imgString.getBytes(), 0);
                        image.setImageBitmap(BitmapFactory.decodeByteArray(
                                imageAsBytes, 0, imageAsBytes.length));
                        image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent dishIntent = new Intent(MenuList.this,DishDetailsActivity.class);
                                startActivity(dishIntent);
                            }
                        });

                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("The read failed: ", firebaseError.getMessage());

            }
        });
    }
}
