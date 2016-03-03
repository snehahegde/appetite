package com.appetite;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class ChefMenuItemActivity extends AppCompatActivity {

    String itemName,chefName;
    EditText et_quantity;
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

        et_quantity = (EditText) findViewById(R.id.et_quantityLabel);
        bt_post = (Button) findViewById(R.id.postButton);

        postChefDetails();



    }
    public void postChefDetails(){

        bt_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login();

                quantity = Integer.parseInt(et_quantity.getText().toString());
                itemName = getIntent().getExtras().getString("item_name");
                chefName = Login.uname;
                System.out.println(chefName + " " + itemName + " " + quantity);

                //mRef = new Firebase("https://app-etite.firebaseio.com/" + chefName);
                //mRef.child(itemName).setValue(new ChefMenuItem(quantity));

            }
        });

    }


}
