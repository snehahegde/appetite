package com.appetite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChefMenulist extends MainActivity {

    TextView displayLabel;
    String chefName;
    Firebase mRef;
    List<ChefList> chefMenuList;
    ListView chefMenulistView;
    ChefMenulistAdapter chefMenulistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_menulist);

        Firebase.setAndroidContext(this);


        displayLabel = (TextView) findViewById(R.id.listLabel);
        displayLabel.setText("Menu list completed...");

        chefName = Login.userName;
        mRef = new Firebase("https://app-etite.firebaseio.com/" + chefName);

        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chefMenuList = new ArrayList<ChefList>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String chefMenus = postSnapshot.getKey();
                    System.out.println("chefMenu: " + chefMenus);

                    if (postSnapshot.child("quantity").getValue() != null && postSnapshot.child("ingredients").getValue() != null) {



                        int quantity = Integer.valueOf(postSnapshot.child("quantity").getValue().toString());
                        String ingredients = postSnapshot.child("ingredients").getValue().toString();


                       // String imgString = postSnapshot.child("imageEncoded").getValue().toString();


                    chefMenuList.add(new ChefList(chefMenus,quantity,ingredients));
                    }
                      chefMenuList();
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("The read failed: ", firebaseError.getMessage());

            }
        });

    }
    public void chefMenuList(){

        chefMenulistView = (ListView) findViewById(R.id.chefMenulistView);

        chefMenulistAdapter = new ChefMenulistAdapter(this, R.layout.cheflist_rowlayout, chefMenuList);
        chefMenulistView.setAdapter(chefMenulistAdapter);

        chefMenulistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChefList chefList = chefMenuList.get(position);

                Intent chefMenuInfo = new Intent(ChefMenulist.this, ChefMenuInfo.class);
                chefMenuInfo.putExtra("menu_name", chefList.getMenuName());
                chefMenuInfo.putExtra("menu_ingredients",chefList.getIngredients());
                startActivity(chefMenuInfo);


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
            startActivity(reviewsIntent);
            String chefName = "Monica";
            reviewsIntent.putExtra("chef",chefName);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
