package com.appetite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ChefsEnrolledActivity extends AppCompatActivity {

    Firebase mRef, picRef;
    TextView itemName;
    TextView topLabel;
    Boolean displayList;
    String chef_menu,chefs_enrolled,chef_ingredients,quantity_avbl;
    List<ChefsEnrolledList> chefsEnrolledDetails;
    Map<String,ChefMenuDetails>  chefMenuDetailsMap = new HashMap<String, ChefMenuDetails>();
    //ChefMenuDetails chefMenuDetails;
    ListView chefsEnrolledlistView;
    ChefsEnrolledListAdapter chefsEnrolledListAdapter;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefs_enrolled);



        chefsEnrolledDetails = new ArrayList<ChefsEnrolledList>();
//        itemName = (TextView) findViewById(R.id.tv_menuName);
        Bundle bundle = getIntent().getExtras();
        chef_menu = bundle.getString("menuName");
        System.out.println("MENU: " + chef_menu);

        topLabel = (TextView)findViewById(R.id.chefsEnrolledLabel);
        topLabel.setText("Your chefs' for " + chef_menu + "!");

//        itemName.setText(chef_menu);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://app-etite.firebaseio.com/chefsEnrolled/");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                chefMenuDetailsMap.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    System.out.println("MAIN CHILDREN: " + postSnapshot.hasChild(chef_menu));

                    if (postSnapshot.hasChild(chef_menu)) {
                        chefs_enrolled = postSnapshot.getKey();
                            if(chefs_enrolled!=null && Integer.parseInt(dataSnapshot.child(chefs_enrolled).child(chef_menu).child("quantity").getValue().toString())>0){
                                //displayList =true;
                                chef_ingredients = dataSnapshot.child(chefs_enrolled).child(chef_menu).child("ingredients").getValue().toString();
                                quantity_avbl = dataSnapshot.child(chefs_enrolled).child(chef_menu).child("quantity").getValue().toString();
                                String food_image;
                                if(dataSnapshot.child(chefs_enrolled).child(chef_menu).child("foodImg").getValue() != null) {
                                    food_image = dataSnapshot.child(chefs_enrolled).child(chef_menu).child("foodImg").getValue().toString();
                                } else {
                                    food_image = "";
                                }

                                ChefMenuDetails chefMenuDetails = new ChefMenuDetails(chef_ingredients, quantity_avbl, food_image);
                                chefMenuDetailsMap.put(chefs_enrolled, chefMenuDetails);
                            System.out.println("Chef_details: " + chefs_enrolled + " " + chef_ingredients + " " + quantity_avbl);

                            System.out.println("CHEF_LIST: " + chefMenuDetailsMap);

                        }


                    } else {

                    }
                }

                chefsList();

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    public void chefsList(){
        System.out.println("chef list");
        picRef = new Firebase("https://app-etite.firebaseio.com/userInfo/chef");
        picRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                System.out.println("CHEF_MAP: " + chefMenuDetailsMap);
                chefsEnrolledDetails.clear();

                Iterator myVeryOwnIterator = chefMenuDetailsMap.keySet().iterator();
                while(myVeryOwnIterator.hasNext()) {
                    key=(String)myVeryOwnIterator.next();

                    String chefpicUrl = dataSnapshot.child(key).child("pic").getValue().toString();
                    ChefMenuDetails value=chefMenuDetailsMap.get(key);
                    System.out.println("Key: " + key + " Ingre: " + " " + value.getIngredients() + "Quan: " + " " + value.getQuantity() + " " + "Photo:" + " " + chefpicUrl);

                    chefsEnrolledDetails.add(new ChefsEnrolledList(key, chefpicUrl, value.getIngredients(), value.getQuantity(), value.getFoodImg()));
                    System.out.println("FINAL LIST: " + chefsEnrolledDetails);

                        chefsEnrolledList();

                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
    public void chefsEnrolledList(){

        chefsEnrolledlistView = (ListView) findViewById(R.id.chefsEnrolledlistView);

        chefsEnrolledListAdapter = new ChefsEnrolledListAdapter(this, R.layout.activity_chefs_enrolled_list_adapter, chefsEnrolledDetails);
        chefsEnrolledlistView.setAdapter(chefsEnrolledListAdapter);

        chefsEnrolledlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChefsEnrolledList chefsEnrolledList = chefsEnrolledDetails.get(position);

                Intent chefDishInfo = new Intent(ChefsEnrolledActivity.this, DishDetailsActivity.class);
                System.out.println("CNAME: " + chefsEnrolledList.getChefName());
                chefDishInfo.putExtra("chefName", chefsEnrolledList.getChefName());
                chefDishInfo.putExtra("chefDish", chef_menu);
                chefDishInfo.putExtra("chefIngredients", chefsEnrolledList.getIngredients());
                chefDishInfo.putExtra("chefQuantity", chefsEnrolledList.getQuantity());
                chefDishInfo.putExtra("foodImage", chefsEnrolledList.getFoodImage());
                startActivity(chefDishInfo);


            }
        });


    }
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            Intent chefsLocator = new Intent(ChefsEnrolledActivity.this,MapActivity.class);
            chefsLocator.putExtra("menu_name",chef_menu);
            startActivity(chefsLocator);
        }

        return super.onOptionsItemSelected(item);
    }


}
