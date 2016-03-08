package com.appetite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageButton searchBtn;
    String searchDish;
    EditText searchWord;
    MenulistAdapter menuListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menulist_layout);
        Firebase.setAndroidContext(this);

        searchBtn =(ImageButton) (findViewById(R.id.searchB));
        searchWord = (EditText)(findViewById(R.id.searchW));
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /**  try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+14086204112", null,"Testing if the SMS intent is working or not." , null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                }

                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }*/

                String keyWord = searchWord.getText().toString();
                searchContent(keyWord);
            }
        });
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("uname");



        mRef = new Firebase("https://app-etite.firebaseio.com/menulist");
        menuList = new ArrayList<Menu>();
        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    if (postSnapshot.child("item_name").getValue() != null && postSnapshot.child("item_price").getValue() != null && postSnapshot.child("imageEncoded") != null) {
                        String itemName = postSnapshot.child("item_name").getValue().toString();
                        String itemPrice = postSnapshot.child("item_price").getValue().toString();
                        String itemImage = postSnapshot.child("imageEncoded").getValue().toString();
                        String itemCuisine = postSnapshot.child("cuisine").getValue().toString();
                        menuList.add(new Menu(itemName, itemPrice,itemImage,itemCuisine));
                    }

                }
                menuList();
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
                if(MainActivity.cookModule) {
                    Intent chefMenuDetails = new Intent(MenuList.this, ChefMenuItemActivity.class);
                    chefMenuDetails.putExtra("item_name", menuItems.getItemName());
                    startActivity(chefMenuDetails);
                }else if(MainActivity.eatModule){
                    Intent dishDetails = new Intent(MenuList.this,DishDetailsActivity.class);
                    startActivity(dishDetails);
                }

            }
        });

    }

    public void searchContent(String word){
        searchDish=word;
        menuList.clear();
        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.child("item_name").getValue() != null && postSnapshot.child("item_price").getValue() != null && postSnapshot.child("imageEncoded") != null&& postSnapshot.child("cuisine") != null) {
                        String itemName = postSnapshot.child("item_name").getValue().toString();
                        String itemPrice = postSnapshot.child("item_price").getValue().toString();
                        String itemImage = postSnapshot.child("imageEncoded").getValue().toString();
                        String itemCuisine = postSnapshot.child("cuisine").getValue().toString();
                        if(itemName.equals(searchDish) ||itemCuisine.equals(searchDish)){
                            menuList.add(new Menu(itemName, itemPrice, itemImage,itemCuisine));
                            }
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
        if (id == R.id.action_home) {
            return true;
        }
        else if (id==R.id.action_profile){
            return true;
        }
        else if(id == R.id.action_reviews){
            Intent reviewsIntent = new Intent(this,ReviewsCookModuleActivity.class);
            String chefName = "Monica";
            reviewsIntent.putExtra("chef", chefName);
            startActivity(reviewsIntent);
            return true;
        }
        else if (id==R.id.action_logOut){
            Intent logOutIntent = new Intent(this,MainActivity.class);
            logOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logOutIntent);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
