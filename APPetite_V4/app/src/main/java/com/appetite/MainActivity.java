package com.appetite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ImageButton bEat,bCook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bEat = (ImageButton) findViewById(R.id.bEat);
        bEat.setBackgroundColor(0xFFFFB300);
        bCook = (ImageButton) findViewById(R.id.bCook);
        bCook.setBackgroundColor(0xFFFFB300);
        //0xFFe0ab18

        bEat.setOnClickListener(this);
        bCook.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.bEat:

                startActivity(new Intent(this, Login.class));

                break;
            case R.id.bCook:

                startActivity(new Intent(this,Login.class));

                break;
        }

    }
}
