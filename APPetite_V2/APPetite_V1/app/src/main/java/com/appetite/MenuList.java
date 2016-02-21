package com.appetite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Kavi on 2/13/16.
 */
public class MenuList extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menulist_layout);
        txt=(TextView)findViewById(R.id.welcomeUser);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("uname");
        txt.setText("Welcome " + message + "!!");
    }
}
