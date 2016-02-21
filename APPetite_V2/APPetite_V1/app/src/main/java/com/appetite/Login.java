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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = (EditText)findViewById(R.id.etUsername);
        etPassword = (EditText)findViewById(R.id.etPassword);
        bLogin = (Button)findViewById(R.id.bLogin);
        tvRegisterLink = (TextView)findViewById(R.id.tvRegisterLink);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);
    }
    private void query() {

        SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();
        String where = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {DatabaseHelper.ID_COLUMN, DatabaseHelper.NAME,DatabaseHelper.PHONE_NO,DatabaseHelper.ADDRESS, DatabaseHelper.USERNAME,DatabaseHelper.PASSWORD};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, resultColumns, where, whereArgs, groupBy, having, order);

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(0);
                name = cursor.getString(1);
                phone_no = cursor.getInt(2);
                address = cursor.getString(3);
                username = cursor.getString(4);
                password = cursor.getString(5);

                Log.d("USER_INFO", String.format("%s,%s,%s,%s,%s,%s", id, name, phone_no, address, username, password));
            }while(cursor.moveToNext());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:
                userName = etUserName.getText().toString();
                userPwd = etPassword.getText().toString();

                databaseHelper = new DatabaseHelper(this);

                query();

                // fetch the Password form database for respective user name
                String storedPassword=databaseHelper.getSinlgeEntry(userName);
                //Log.d("storedPwd: " , storedPassword);
                // check if the Stored password matches with  Password entered by user
                if(userPwd.equals(storedPassword))
                {
                    Toast.makeText(getBaseContext(), "Congrats: Login Successful", Toast.LENGTH_SHORT).show();

                    /**Intent menuList = new Intent(this, MenuList.class);
                    menuList.putExtra("uname", userName);
                    startActivity(menuList);*/
                Intent dishIntent = new Intent(Login.this,DishDetailsActivity.class);
                    startActivity(dishIntent);

                }
                else if(!userPwd.equals(storedPassword))
                {
                    Toast.makeText(getBaseContext(), "Username or password does not match", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.tvRegisterLink:

                startActivity(new Intent(this,Register.class));


                break;
        }
    }
}
