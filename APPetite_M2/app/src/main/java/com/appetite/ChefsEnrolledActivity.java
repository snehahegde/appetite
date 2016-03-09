package com.appetite;

import android.content.Intent;
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
    String chef_menu,chefs_enrolled,chef_ingredients,quantity_avbl;
    List<ChefsEnrolledList> chefsEnrolledDetails;
    Map<String,ChefMenuDetails>  chefMenuDetailsMap = new HashMap<String, ChefMenuDetails>();
    ChefMenuDetails chefMenuDetails;
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
//        itemName.setText(chef_menu);

        mRef = new Firebase("https://app-etite.firebaseio.com");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                chefMenuDetailsMap.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    System.out.println("MAIN CHILDREN: " + postSnapshot.hasChild(chef_menu));

                    if (postSnapshot.hasChild(chef_menu)) {
                        chefs_enrolled = postSnapshot.getKey();
//                        System.out.println("SUB - CHILD: " + postSnapshot.child(chefs_enrolled).getValue().toString());
//                        chef_ingredients = dataSnapshot.child(chefs_enrolled).child(chef_menu).child("ingredients").getValue().toString();
//                        quantity_avbl = dataSnapshot.child(chefs_enrolled).child(chef_menu).child("quantity").getValue().toString();
//                        if(dataSnapshot.child(chefs_enrolled).child(chef_menu).child("ingredients").getValue().toString()!=null && dataSnapshot.child(chefs_enrolled).child(chef_menu).child("quantity").getValue().toString()!=null) {
                            if(chefs_enrolled!=null){
                            System.out.println("chefs_enrolled: " + postSnapshot.getKey());
                            System.out.println("chef_value: " + postSnapshot.getValue());
                            System.out.println("chef_ingredients: " + dataSnapshot.child(chefs_enrolled).child(chef_menu).child("ingredients").getValue().toString());
                            System.out.println("quantity_avbl: " + dataSnapshot.child(chefs_enrolled).child(chef_menu).child("quantity").getValue().toString());

                            chef_ingredients = dataSnapshot.child(chefs_enrolled).child(chef_menu).child("ingredients").getValue().toString();
                            quantity_avbl = dataSnapshot.child(chefs_enrolled).child(chef_menu).child("quantity").getValue().toString();

                                chefMenuDetails = new ChefMenuDetails(chef_ingredients, quantity_avbl);
                                chefMenuDetailsMap.put(chefs_enrolled, chefMenuDetails);
                            System.out.println("Chef_details: " + chefs_enrolled + " " + chef_ingredients + " " + quantity_avbl);

                            System.out.println("CHEF_LIST: " + chefMenuDetailsMap);

                        }


                    } else {

                    }
                }
//                chef_ingredients = dataSnapshot.child(chefs_enrolled).child(chef_menu).child("ingredients").getValue().toString();
//                quantity_avbl = dataSnapshot.child(chefs_enrolled).child(chef_menu).child("quantity").getValue().toString();
//                System.out.println("chef_ingredients: " + dataSnapshot.child(chefs_enrolled).child(chef_menu).child("ingredients").getValue().toString());
//                System.out.println("quantity_avbl: " + dataSnapshot.child(chefs_enrolled).child(chef_menu).child("quantity").getValue().toString());
//                chefMenuDetails = new ChefMenuDetails(chef_ingredients,quantity_avbl);
//                chefMenuDetailsMap.put(chefs_enrolled, chefMenuDetails);
//                System.out.println("CHEF_LIST: " + chefMenuDetailsMap);
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
                    System.out.println("Key: " + key + " Ingre: " + " " + chefMenuDetails.getIngredients() + "Quan: " + " " + chefMenuDetails.getQuantity() + " " + "Photo:" + " " + chefpicUrl);

                    chefsEnrolledDetails.add(new ChefsEnrolledList(key, chefpicUrl, chefMenuDetails.getIngredients(), chefMenuDetails.getQuantity()));
                    System.out.println("FINAL LIST: " + chefsEnrolledDetails);
                    // Toast.makeText(ctx, "Key: " + key + " Value: " + value, Toast.LENGTH_LONG).show();
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
