package com.appetite;

/**
 * Created by Pravallika on 2/15/2016.
*/

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AddReviewActivity extends AppCompatActivity {
    EditText rev;
    String ratingValue;
    String user= "Andrew";
    String chef="Monica";
    ContentValues newValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        rev=(EditText) findViewById(R.id.review);
        RatingBar rat=(RatingBar)findViewById(R.id.ratingBar);
        rat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingValue = String.valueOf(rating);
            }
        });
        Button save = (Button) findViewById(R.id.button);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String review = rev.getText().toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String date = sdf.format(new Date());
                        insert(chef, user, review, ratingValue,date);
                        Intent i = new Intent();
                        i.putExtra("user", user);
                        i.putExtra("review", review);
                        i.putExtra("rating", ratingValue);
                        i.putExtra("date",date);
                        setResult(RESULT_OK, i);
                        finish();

                    }
                });
    }

            private void insert(String chef, String user, String review, String rating,String date) {
                SQLiteDatabase db = new ReviewDbHelper(this).getWritableDatabase();
                newValues = new ContentValues();
                newValues.put(ReviewDbHelper.CHEF_COLUMN, chef);
                newValues.put(ReviewDbHelper.USER_COLUMN, user);
                newValues.put(ReviewDbHelper.REVIEW_COLUMN, review);
                newValues.put(ReviewDbHelper.RATING_COLUMN, rating);
                newValues.put(ReviewDbHelper.DATE_COLUMN,date);
                db.insert(ReviewDbHelper.DATABASE_TABLE, null, newValues);
            }

        }
