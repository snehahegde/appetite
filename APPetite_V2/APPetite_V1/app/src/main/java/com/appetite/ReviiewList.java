package com.appetite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ReviiewList extends AppCompatActivity {
    List<Reviews> cap;
    CustomAdapter ca;
    Reviews r;
    public static final int REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        cap = new ArrayList();
        ListView lview = (ListView) findViewById(R.id.listView);
        query("Monica");
        ca = new CustomAdapter(this, R.layout.custom_row, cap);
        lview.setAdapter(ca);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Intent detailIntent = new Intent(ReviiewList.this, ReviewDetailActivity.class);
                //detailIntent.putExtra("ca", (cap.get(position));
               // startActivity(detailIntent);

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ReviiewList.this, AddReviewActivity.class);
                startActivityForResult(i, REQUEST);
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                String chef= extras.getString("chef");
                String user = extras.getString("user");
                String rating = extras.getString("rating");
                String review = extras.getString("review");
                String date =  extras.getString("date");
                cap.add(new Reviews(chef,user,review,rating,date));
                ca.notifyDataSetChanged();
            }

        }
    }
    private void query(String chefName ) {
        SQLiteDatabase db = new ReviewDbHelper(this).getWritableDatabase();
        String where = ReviewDbHelper.CHEF_COLUMN + "=?";
        String[] whereArgs ={chefName} ;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {ReviewDbHelper.ID_COLUMN,ReviewDbHelper.CHEF_COLUMN, ReviewDbHelper.USER_COLUMN,ReviewDbHelper.REVIEW_COLUMN,ReviewDbHelper.RATING_COLUMN,ReviewDbHelper.DATE_COLUMN};
        Cursor cursor = db.query(ReviewDbHelper.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
           // String chef = cursor.getString(1);
            String user = cursor.getString(2);
            String review = cursor.getString(3);
            String rating = cursor.getString(4);
            String date = cursor.getString(5);
            cap.add(new Reviews(chefName,user,review,rating,date));
        }
    }

}
