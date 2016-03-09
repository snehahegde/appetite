package com.appetite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.HashMap;
import java.util.Map;

public class DishDetailsActivity extends AppCompatActivity {

    ImageView chefDishPic;
    TextView chefIngredients,chefQuantity;
    EditText quantityInput;
    String quantityOrdered,chefName,chefDishName,chefDishIngredients,chefDishQuantity;
    Button orderButton,reviewBtn;
    Firebase mRef;
    public static int PRICE = 5;
    int remainingQuantity = 0;
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
        reviewBtn = (Button)findViewById(R.id.btRevs);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent revIntent = new Intent(DishDetailsActivity.this,ReviiewList.class);
                revIntent.putExtra("chefName",chefName);
                startActivity(revIntent);
            }
        });

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

        remainingQuantity = Integer.parseInt(chefDishQuantity) - Integer.parseInt(quantityOrdered);
        System.out.println("DIFF: " + remainingQuantity);
        chefQuantity.setText(String.valueOf(remainingQuantity));

        mRef = new Firebase("https://app-etite.firebaseio.com/" + chefName);
        Map<String, Object> quantityOrderedMap = new HashMap<String, Object>();
        quantityOrderedMap.put(chefDishName + "/quantityOrdered", quantityOrdered);
        quantityOrderedMap.put(chefDishName + "/quantity", remainingQuantity);

        mRef.updateChildren(quantityOrderedMap);



        showAlertDialog(DishDetailsActivity.this);


        }

    //alert dialog box
    public void showAlertDialog(DishDetailsActivity dishDetailsActivity) {
        int quantity = Integer.parseInt(quantityOrdered);
        int totalPrice = PRICE * quantity;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(dishDetailsActivity);
        alertDialog.setTitle("Order Confirmation");
        alertDialog.setMessage("Price of your order is $" + totalPrice + ".");
        //positive response
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SmsManager smsMngr = SmsManager.getDefault();
                smsMngr.sendTextMessage("+14089212442", null, "You have received and order from " + Login.userName + " for" + quantityInput + " " + chefDishName + ".", null, null);
                Toast.makeText(getApplicationContext(), "Order placed!", Toast.LENGTH_LONG).show();
                chefQuantity.setText("");

            }
        });
        //negative response
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.create().show();



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
            Intent chefUserMap = new Intent(DishDetailsActivity.this,ChefUsersMapActivity.class);
            chefUserMap.putExtra("chef_name",chefName);
            startActivity(chefUserMap);
        }

        return super.onOptionsItemSelected(item);
    }




}
