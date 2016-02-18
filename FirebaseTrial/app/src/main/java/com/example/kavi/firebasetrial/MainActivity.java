package com.example.kavi.firebasetrial;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

    TextView item_name,item_price;
    Firebase mRef;
    ImageView image;
    String imageEncoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        item_name = (TextView) findViewById(R.id.item_name);
        item_price = (TextView) findViewById(R.id.item_price);
        image = (ImageView) findViewById(R.id.imgView);



            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.carrotcake_icon);//your image
            ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
            bmp.recycle();
            byte[] byteArray = bYtE.toByteArray();
            imageEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);



        mRef = new Firebase("https://app-etite.firebaseio.com/menulist");


//        Firebase alanRef = mRef.child("user1");
//        Map<String, Object> nickname = new HashMap<String, Object>();
//        nickname.put("nickname", "Alan The Machine");
//        alanRef.updateChildren(nickname);
//
//        Firebase graceRef = mRef.child("user1");
//        Map<String, Object> graceNickname = new HashMap<String, Object>();
//        graceNickname.put("nickname", "Amazing Grace");
//        graceRef.updateChildren(graceNickname);

//        mRef = new Firebase("https://glowing-inferno-7535.firebaseio.com/appetite");
//        Firebase alanRef = mRef.child("users").child("user1");
//        BlogPost alan = new BlogPost("Image value");
//        alanRef.setValue(alan);

//        Firebase alanRef = mRef.child("menulist").child("item1");
//        Map<String, Object> imageMap = new HashMap<String, Object>();
//        imageMap.put("ImgEncoded", "Image encoded here");
//        alanRef.updateChildren(imageMap);
//
//        Firebase menuRef = mRef.child("menulist");
//        Map<String, Object> imageMap = new HashMap<String, Object>();
//        imageMap.put("item1/ImgEncoded", imageEncoded);
//        imageMap.put("item2/ImgEncoded", imageEncoded);
//        menuRef.updateChildren(imageMap);

//        Firebase menuRef = mRef.child("menulist");
        Map<String, Object> imagesMap = new HashMap<String, Object>();
        imagesMap.put("item1/imageEncoded", imageEncoded);
        imagesMap.put("item2/imageEncoded", imageEncoded);
        mRef.updateChildren(imagesMap);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //BlogPost post = postSnapshot.getValue(BlogPost.class);

                   // String value = (String) postSnapshot.getValue();
                    System.out.println("KEY:" + postSnapshot.getKey());
                    System.out.println("KEY length:" + postSnapshot.getKey().length());
                    System.out.println("count: " + postSnapshot.getChildrenCount());
                    System.out.println("DATA: " + postSnapshot.child("item_name").getValue() + "-" + postSnapshot.child("item_price").getValue());
//                        Log.d("Fetched values: ", postSnapshot.child("item_name").getValue() + " - " + postSnapshot.child("item_price").getValue() + " - " +
//                                postSnapshot.child("ImgPath").getValue());


                    if(postSnapshot.child("item_name").getValue()!=null && postSnapshot.child("item_price").getValue()!=null) {
                        item_name.setText(postSnapshot.child("item_name").getValue().toString());
                        item_price.setText(postSnapshot.child("item_price").getValue().toString());


                        String imgString = postSnapshot.child("imageEncoded").getValue().toString();
                        byte[] imageAsBytes = Base64.decode(imgString.getBytes(), 0);
                        image.setImageBitmap(BitmapFactory.decodeByteArray(
                                imageAsBytes, 0, imageAsBytes.length));
//


                        setImages(postSnapshot.getKey());
                        System.out.println("K-count" + postSnapshot.getKey().length());


                    }


                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("The read failed: " , firebaseError.getMessage());

            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               //Log.d("Count: " , ((int) dataSnapshot.getChildrenCount());

//                Map<String, Object> images = new HashMap<String, Object>();
//                images.put("user1/imgPath", "Alan The Machine");
//                images.put("user2/imgPath", "Amazing Grace");
//                mRef.updateChildren(images);



//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                   //BlogPost post = postSnapshot.getValue(BlogPost.class);
//                    String key = postSnapshot.getKey();
//                    String value = (String) postSnapshot.getValue();
//                    System.out.println("KEY:" + key + " " + "VALUE:" + value);
//                    System.out.println("count: " + postSnapshot.getChildrenCount());
//                    System.out.println("DATA: " + postSnapshot.child("item_name").getValue() + "-" + postSnapshot.child("item_price").getValue());
////                        Log.d("Fetched values: ", postSnapshot.child("item_name").getValue() + " - " + postSnapshot.child("item_price").getValue() + " - " +
////                                postSnapshot.child("ImgPath").getValue());
//
////                    if(postSnapshot.child("fullName").getValue()!=null && postSnapshot.child("ImgPath").getValue()!=null) {
////                        item_name.setText(postSnapshot.child("fullName").getValue().toString());
////
////                        String imgString = postSnapshot.child("ImgPath").getValue().toString();
////                        byte[] imageAsBytes = Base64.decode(imgString.getBytes(), 0);
////                        image.setImageBitmap(BitmapFactory.decodeByteArray(
////                                imageAsBytes, 0, imageAsBytes.length));
////
////                        String key = postSnapshot.getKey();
////                        setImages(key);
////                        System.out.println("K-count" + key.length());
////
////
////                    }
//
//
//                }


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
    public void setImages(String key){

        System.out.println("Key vals: " + key);
        System.out.println("Key count: " + key.length());

        int[] icons = {R.drawable.chococake_icon, R.drawable.redvelvet_icon};


//       for(int i = 1; i < key.length(); i++) {
//
        for(int j = 1;j < icons.length; j++) {
            System.out.println("Images length:" + icons.length);
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(), icons[i]);//your image
//            ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
//            bmp.recycle();
//            byte[] byteArray = bYtE.toByteArray();
//            imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//            Firebase alanRef = mRef.child("users");
//            Map<String, Object> imageMap = new HashMap<String, Object>();
//            imageMap.put(key + "/ImgPath", imageFile);
//            // alanRef.updateChildren(imageMap);
//            System.out.println("Image Map: " + imageMap);
        }
//       }
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
