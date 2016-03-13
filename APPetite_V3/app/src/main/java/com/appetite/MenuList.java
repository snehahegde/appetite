package com.appetite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kavi on 2/13/16.
 */
public class MenuList extends AppCompatActivity {

    TextView welcometxt;
    TextView item_name, item_price;
    Firebase mRef;
    ImageView image;
    String imageEncoded;
    ListView menulistView;
    List<Menu> menuList;
    MenulistAdapter menuListAdapter;
    Map<String, Object> imageMap;
    int count = 0;
    Bitmap scaledImg;
   // int[] icons = {R.drawable.chococake_icon, R.drawable.redvelvet_icon, R.drawable.raspberrycake_icon, R.drawable.carrotcake_icon, R.drawable.cheesecake_icon,R.drawable.eclair_icon,R.drawable.walnutcake_icon};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menulist_layout);
        Firebase.setAndroidContext(this);


        welcometxt = (TextView) findViewById(R.id.welcomeUser);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("uname");
        welcometxt.setText("Hi " + message + "!!");


        mRef = new Firebase("https://app-etite.firebaseio.com/menulist");

        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                menuList = new ArrayList<Menu>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    if (postSnapshot.child("item_name").getValue() != null && postSnapshot.child("item_price").getValue() != null && postSnapshot.child("imageEncoded") != null) {

                        count++;

                        String itemName = postSnapshot.child("item_name").getValue().toString();
                        String itemPrice = postSnapshot.child("item_price").getValue().toString();

                        setImages(postSnapshot.getKey(), count);

                        String imgString = postSnapshot.child("imageEncoded").getValue().toString();
//                        byte[] imageAsBytes = Base64.decode(imgString.getBytes(), 0);
//                        image.setImageBitmap(BitmapFactory.decodeByteArray(
//                                imageAsBytes, 0, imageAsBytes.length));

                        menuList.add(new Menu(itemName, itemPrice, imgString));
                    }
                    menuList();
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("The read failed: ", firebaseError.getMessage());

            }
        });

    }
    public void menuList(){

        menulistView = (ListView) findViewById(R.id.listView);

        menuListAdapter = new MenulistAdapter(this, R.layout.menulist_rowlayout, menuList);
        menulistView.setAdapter(menuListAdapter);

        menulistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Menu menuItems = menuList.get(position);

                Intent chefMenuDetails = new Intent(MenuList.this, ChefMenuItemActivity.class);
                chefMenuDetails.putExtra("item_name", menuItems.getItemName());
                startActivity(chefMenuDetails);


            }
        });

    }
    public void setImages(String key, int count){

        int[] icons = {R.drawable.chococake_icon, R.drawable.redvelvet_icon,R.drawable.raspberrycake_icon,R.drawable.carrotcake_icon,R.drawable.cheesecake_icon,R.drawable.eclair_icon,R.drawable.walnutcake_icon};


            //  System.out.println("Images length:" + icons.length);
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), icons[count % 7]);//your image
            ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
            bmp.recycle();
            byte[] byteArray = bYtE.toByteArray();
            imageEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            imageMap = new HashMap<String, Object>();
            imageMap.put(key + "/imageEncoded", imageEncoded);
            mRef.updateChildren(imageMap);


//
//        for(int j = 0;j <icons.length; j++) {
//            //  System.out.println("Images length:" + icons.length);
//            Bitmap bmp = BitmapFactory.decodeResource(getResources(), icons[j]);//your image
//            ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
//            bmp.recycle();
//            byte[] byteArray = bYtE.toByteArray();
//            imageEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//            imageMap = new HashMap<String, Object>();
//            imageMap.put(key + "/imageEncoded", imageEncoded);
//            mRef.updateChildren(imageMap);
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
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
