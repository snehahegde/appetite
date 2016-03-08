package com.appetite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class ChefMenuItemActivity extends AppCompatActivity {

    String itemName,chefName,location,ingredients;
    EditText et_quantity;
    EditText et_ingredients;
    int quantity;
    Button bt_post;
    Firebase mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_menu_item);

        //view item name
        TextView caption = (TextView) findViewById(R.id.view_itemName);
        caption.setText(getIntent().getExtras().getString("item_name"));
        caption.setTextColor(Color.BLACK);
        et_ingredients = (EditText) findViewById(R.id.et_ingredients);
        et_quantity = (EditText) findViewById(R.id.et_quantityLabel);
        bt_post = (Button) findViewById(R.id.postButton);

        postChefDetails();



    }
    public void postChefDetails(){

        bt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ingredients = et_ingredients.getText().toString();
                quantity = Integer.parseInt(et_quantity.getText().toString());
                itemName = getIntent().getExtras().getString("item_name");
                chefName = Login.userName;
                System.out.println(chefName + " " + itemName + " " + quantity + " " + ingredients);

                mRef = new Firebase("https://app-etite.firebaseio.com/" + chefName);
                mRef.child(itemName).setValue(new ChefMenuItem(ingredients, quantity));

                Intent chefMenuList = new Intent(ChefMenuItemActivity.this,ChefMenuInfo.class);
                chefMenuList.putExtra("menu_name",itemName);
                startActivity(chefMenuList);



            }
        });

    }


}
