package com.appetite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ChefMenuInfo extends AppCompatActivity {

    TextView menuName,menuIngredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_menu_info);

        menuName = (TextView) findViewById(R.id.chef_menu);
        Bundle bundle = getIntent().getExtras();
        String chef_menu = bundle.getString("menu_name");
        menuName.setText(chef_menu);

        menuIngredients = (TextView) findViewById(R.id.view_ingredients);
        Bundle bdle = getIntent().getExtras();
        String menu_ingredients = bdle.getString("menu_ingredients");
        menuIngredients.setText(menu_ingredients);

    }

}
