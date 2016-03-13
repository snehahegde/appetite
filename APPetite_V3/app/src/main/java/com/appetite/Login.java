package com.appetite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Set;


public class Login extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    EditText etUserName, etPassword;
    TextView tvRegisterLink;
    String name,address,username,password;
    int id,phone_no;
    String userName,userPwd;
    DatabaseHelper databaseHelper;
    Firebase mRef;
    public static String uname;


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
        mRef = new Firebase("https://app-etite.firebaseio.com/userInfo/");
        switch (v.getId()){
            case R.id.bLogin:
                userName = etUserName.getText().toString();
                userPwd = etPassword.getText().toString();
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            uname = postSnapshot.child("username").getValue().toString();

                            String pwd = postSnapshot.child("password").getValue().toString();
                            if (uname.equals(userName) & pwd.equals(userPwd)) {
                                displayMenu();

                            }
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                break;
            case R.id.tvRegisterLink:

                startActivity(new Intent(this,Register.class));


                break;
        }
    }
    public void displayMenu(){
        Intent menuList = new Intent(this, MenuList.class);
        menuList.putExtra("uname", userName);
        startActivity(menuList);
    }

}


