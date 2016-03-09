package com.appetite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ChefMenuInfo extends AppCompatActivity {

    TextView menuName,menuIngredients,menuQuantity,menuQuantityOrdered;
    ImageView cheffMenuImg;
    Firebase mRef;
    String qOrdered,chefMenus,quantity,ingredients,chef_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_menu_info);

        String chefName = Login.userName;
        Bundle bundle = getIntent().getExtras();
        chef_menu = bundle.getString("menu_name");
        menuName = (TextView) findViewById(R.id.chef_menu);
        menuName.setText(chef_menu);

        System.out.println("ChefNaMe: " + chefName + " " + "ChefMeNu: " + chef_menu);
        mRef = new Firebase("https://app-etite.firebaseio.com/" + chefName + "/" + chef_menu);

        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                quantity = dataSnapshot.child("quantity").getValue().toString();
                ingredients = dataSnapshot.child("ingredients").getValue().toString();
                if(dataSnapshot.child("quantityOrdered").getValue() == null) {
                            qOrdered="0";
                        }else{
                            qOrdered = dataSnapshot.child("quantityOrdered").getValue().toString();
                        }
                System.out.println("Quan: " + quantity + " " + "Ing: " + ingredients + " " + "Orders: " + qOrdered);

                menuIngredients = (TextView) findViewById(R.id.view_ingredients);
                menuIngredients.setText(ingredients);

                menuQuantity = (TextView) findViewById(R.id.view_quantity);
                menuQuantity.setText(quantity);

                menuQuantityOrdered = (TextView) findViewById(R.id.view_quantityOrdered);
                menuQuantityOrdered.setText(qOrdered);

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("The read failed: ", firebaseError.getMessage());

            }
        });

        cheffMenuImg = (ImageView)findViewById(R.id.chef_menuImg);
        cheffMenuImg.setImageResource(R.drawable.caesarsalad);


    }

}
