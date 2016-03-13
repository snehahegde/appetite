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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

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
    Firebase mRef,menuRef;
    ImageView image;
    String imageEncoded;
    ListView menulistView;
    List<Menu> menuList;
    ImageButton searchBtn;
    String searchDish;
    EditText searchWord;
    MenulistAdapter menuListAdapter;
    Login signOut;
    public GoogleApiClient mGoogleApiClient;
    String itemName,itemPrice,itemImage,itemCuisine;
    Menu menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menulist_layout);
        Firebase.setAndroidContext(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        searchBtn =(ImageButton) (findViewById(R.id.searchB));
        searchWord = (EditText)(findViewById(R.id.searchW));
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = searchWord.getText().toString();
                searchContent(keyWord);
                Log.d("QWERTY", keyWord);
            }
        });


        mRef = new Firebase("https://app-etite.firebaseio.com/menulist");
        menuList = new ArrayList<Menu>();
        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    if (postSnapshot.child("item_name").getValue() != null && postSnapshot.child("item_price").getValue() != null && postSnapshot.child("imageEncoded") != null) {
                        itemName = postSnapshot.child("item_name").getValue().toString();
                        itemPrice = postSnapshot.child("item_price").getValue().toString();
                        itemImage = postSnapshot.child("imageEncoded").getValue().toString();
                        itemCuisine = postSnapshot.child("cuisine").getValue().toString();
                        menuList.add(new Menu(itemName, itemPrice, itemImage,itemCuisine));
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
                menuItems = menuList.get(position);
                if (MainActivity.cookModule) {
                    menuRef = new Firebase("https://app-etite.firebaseio.com/" + Login.userName);
                    menuRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(menuItems.getItemName())) {
                                Intent chefMenuDetails = new Intent(MenuList.this, ChefMenuInfo.class);
                                chefMenuDetails.putExtra("menu_name", menuItems.getItemName());
                                startActivity(chefMenuDetails);
                            } else {
                                Intent chefMenuDetails = new Intent(MenuList.this, ChefMenuItemActivity.class);
                                chefMenuDetails.putExtra("item_name", menuItems.getItemName());
                                startActivity(chefMenuDetails);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                } else if (MainActivity.eatModule) {
                    Intent chefsEnrolled = new Intent(MenuList.this, ChefsEnrolledActivity.class);
                    chefsEnrolled.putExtra("menuName", menuItems.getItemName());
                    System.out.println("EATMENU: " + menuItems.getItemName());
                    startActivity(chefsEnrolled);
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
                    if (postSnapshot.child("item_name").getValue() != null && postSnapshot.child("item_price").getValue() != null && postSnapshot.child("imageEncoded") != null && postSnapshot.child("cuisine") != null) {
                        String itemName = postSnapshot.child("item_name").getValue().toString();
                        String itemPrice = postSnapshot.child("item_price").getValue().toString();
                        String itemImage = postSnapshot.child("imageEncoded").getValue().toString();
                        String itemCuisine = postSnapshot.child("cuisine").getValue().toString();
                        if (itemName.equals(searchDish) || itemCuisine.equals(searchDish)) {
                            menuList.add(new Menu(itemName, itemPrice, itemImage, itemCuisine));
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
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            //mStatusTextView.setText("");
                            Intent returnToLogin = new Intent(getApplicationContext(),Login.class);
                            startActivity(returnToLogin);
                        }
                    });
            Intent logOutIntent = new Intent(this,MainActivity.class);
            logOutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logOutIntent);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

}
