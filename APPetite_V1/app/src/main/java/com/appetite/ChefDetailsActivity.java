package com.appetite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ChefDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.appetite.R.layout.activity_chef_details);
        Toolbar toolbar = (Toolbar) findViewById(com.appetite.R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        //String chefName = intent.getCharArrayExtra("ChefName").toString();
        String dishName = intent.getCharSequenceExtra("DishName").toString();
        String chefName = intent.getCharSequenceExtra("ChefName").toString();

        TextView textView = (TextView)findViewById(com.appetite.R.id.chefName);
        textView.setText(dishName + " " + "By" + " " + chefName);

        ImageView imageView = (ImageView)findViewById(com.appetite.R.id.chefImage);
        imageView.setImageResource(com.appetite.R.drawable.caesarsalad);

        Button orderButton = (Button)findViewById(com.appetite.R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChefDetailsActivity.this, OrderDetailsActivity.class);
                startActivity(intent);
            }
        });
        Button reviewButton = (Button)findViewById(R.id.reviewBtn);
        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewInt = new Intent(ChefDetailsActivity.this,ReviiewList.class);
                startActivity(reviewInt);
            }
        });
    }

}
