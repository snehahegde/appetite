package com.appetite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;

public class ChefMenuInfo extends AppCompatActivity {

    TextView menuName,menuIngredients,menuQuantity,menuQuantityOrdered;
    ImageView cheffMenuImg;
    Firebase mRef;
    String qOrdered,chefMenus,quantity,ingredients,chef_menu;
    public GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_menu_info);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

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
                if(dataSnapshot.child("quantity").getValue()!=null & dataSnapshot.child("ingredients").getValue()!=null) {
                    System.out.println("987654321 : " + "retrieving quantity ordered and remaining quantity from chefMenuInfo class");
                    quantity = dataSnapshot.child("quantity").getValue().toString();
                    ingredients = dataSnapshot.child("ingredients").getValue().toString();
                    if (dataSnapshot.child("quantityOrdered").getValue() == null) {
                        qOrdered = "0";
                    } else {
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


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("The read failed: ", firebaseError.getMessage());

            }
        });

        cheffMenuImg = (ImageView)findViewById(R.id.chef_menuImg);
        cheffMenuImg.setImageResource(R.drawable.salmon_slaw);


    }
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        if (MainActivity.cookModule){
            getMenuInflater().inflate(R.menu.menu_cook, menu);}
        else{
            getMenuInflater().inflate(R.menu.menu_eat, menu);
        }
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
        else if(id == R.id.action_reviews){
            String chefName = Login.userName;
            Intent reviewsIntent = new Intent(this,ReviewsCookModuleActivity.class);
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
                            Intent returnToLogin = new Intent(getApplicationContext(), MainActivity.class);
                            returnToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(returnToLogin);
                            finish();
                            MainActivity.cookModule = false;
                            MainActivity.eatModule = false;


                        }
                    });
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
