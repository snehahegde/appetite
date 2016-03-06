package com.appetite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DishDetailsActivity extends AppCompatActivity {
    private Dish dish;
    private Chef chef;
    private List<Chef> chefs;
    DishHelperClass dishHelperClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.appetite.R.layout.activity_dish_details);


        ImageView imageView = (ImageView)findViewById(com.appetite.R.id.dishImageView);
        imageView.setImageResource(com.appetite.R.drawable.commoncaesarsalad);

        ListView listView = (ListView)findViewById(com.appetite.R.id.chefListView);

        dishHelperClass = DishHelperClass.Create();
        dish = dishHelperClass.getDish("Caeser salad");

        TextView textView = (TextView) findViewById(com.appetite.R.id.dishName);
        textView.setText(dish.getName());

        chefs = dish.getChefsEnrolled();

        listView.setAdapter(new ChefAdapter(this, com.appetite.R.layout.chef_single_row, chefs));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DishDetailsActivity.this, ChefDetailsActivity.class);
                intent.putExtra("ChefName",chefs.get(position).getChefName());
                intent.putExtra("DishName",dish.getName());
                startActivity(intent);
            }
        });



        final TextView ingredientsDescription = (TextView)findViewById(com.appetite.R.id.ingredientsTextView);
        final ImageButton expandDown = (ImageButton)findViewById(com.appetite.R.id.expandDownButton);
        expandDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandDown.setVisibility(View.INVISIBLE);
                ingredientsDescription.setMaxLines(5);
                ingredientsDescription.setText(dish.getIngredients());
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.appetite.R.menu.menu_dish_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.appetite.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
