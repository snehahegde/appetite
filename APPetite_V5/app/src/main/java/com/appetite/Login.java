package com.appetite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class Login extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    EditText etUserName, etPassword;
    TextView tvRegisterLink;
    String name,address,username,password;
    int id,phone_no;
    String userPwd;
    public static String userName;
    DatabaseHelper databaseHelper;
    Firebase chefRef,customerRef;
    String uname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        etUserName = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLogin = (Button)findViewById(R.id.bLogin);
        tvRegisterLink = (TextView)findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

                case R.id.bLogin:
                    if(MainActivity.cookModule == true) {
                        chefRef = new Firebase("https://app-etite.firebaseio.com/userInfo/chef");
                        userName = etUserName.getText().toString();
                        userPwd = etPassword.getText().toString();
                        chefRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                    uname = postSnapshot.child("username").getValue().toString();

                                    String pwd = postSnapshot.child("password").getValue().toString();
                                    if (uname.equals(userName) & pwd.equals(userPwd)) {
                                        displayMenu();
                                        etUserName.setText("");
                                        etPassword.setText("");

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Username and password do not match",Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }else if(MainActivity.eatModule == true){

                        customerRef = new Firebase("https://app-etite.firebaseio.com/userInfo/customer");
                        userName = etUserName.getText().toString();
                        userPwd = etPassword.getText().toString();
                        customerRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    uname = data.child("username").getValue().toString();

                                    String pwd = data.child("password").getValue().toString();
                                    if (uname.equals(userName) & pwd.equals(userPwd)) {
                                        displayMenu();
                                        etUserName.setText("");
                                        etPassword.setText("");

                                    }else{
                                        Toast.makeText(getApplicationContext(),"Username and password do not match",Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });
                    }

                break;
            case R.id.tvRegisterLink:

                if(MainActivity.cookModule == true){

                    startActivity(new Intent(this, ChefRegister.class));

                }else if(MainActivity.eatModule == true) {

                    startActivity(new Intent(this, CustomerRegister.class));
                }

                break;
        }
    }
    public void displayMenu(){
        Intent menuList = new Intent(this, MenuList.class);
        menuList.putExtra("uname", userName);
        startActivity(menuList);
    }

}


