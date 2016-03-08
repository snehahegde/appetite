package com.appetite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DishDetailsActivity extends AppCompatActivity {

    ImageView chefDishPic;
    TextView chefIngredients,chefQuantity;
    EditText quantityInput;
    String quantityOrdered,chefName,chefDishName,chefDishIngredients,chefDishQuantity;
    Button orderButton;
    Firebase mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_details);


        chefDishPic = (ImageView)findViewById(R.id.chefDishPic);
        chefDishPic.setImageResource(R.drawable.caesarsalad);

        Bundle nameBundle = getIntent().getExtras();
        chefName = nameBundle.getString("chefName");

        Bundle dishNameBundle = getIntent().getExtras();
        chefDishName = dishNameBundle.getString("chefDish");

        chefIngredients = (TextView) findViewById(R.id.chefIngredients);
        Bundle bundle = getIntent().getExtras();
        chefDishIngredients = bundle.getString("chefIngredients");
        chefIngredients.setText(chefDishIngredients);

        chefQuantity = (TextView) findViewById(R.id.chefQuantity);
        Bundle bdle = getIntent().getExtras();
        chefDishQuantity = bdle.getString("chefQuantity");
        chefQuantity.setText(chefDishQuantity);

        quantityInput = (EditText)findViewById(R.id.etUserQuantity);


        orderButton = (Button)findViewById(R.id.btOrder);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

    }
    public void placeOrder(){
        quantityOrdered = quantityInput.getText().toString();

        mRef = new Firebase("https://app-etite.firebaseio.com/" + chefName);

        Map<String, Object> quantityOrderedMap = new HashMap<String, Object>();
        quantityOrderedMap.put(chefDishName + "/quantityOrdered" , quantityOrdered);

        mRef.updateChildren(quantityOrderedMap);
        showAlertDialog(DishDetailsActivity.this);

        }

    //alert dialog box
    public void showAlertDialog(DishDetailsActivity dishDetailsActivity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(dishDetailsActivity);
        alertDialog.setTitle("Order Confirmation");
        alertDialog.setMessage("Your order has been placed");
        //positive response
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });


        alertDialog.create().show();

    }

    }
