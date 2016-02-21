package com.appetite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kavi on 2/12/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String ID_COLUMN = "ID_NO";
    public static final String NAME = "name";
    public static final String PHONE_NO = "phone";
    public static final String ADDRESS = "address";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String DATABASE_NAME = "AppetiteDB";
    public static final String TABLE_NAME = "userInfo_Table";

    String password;


    public static final int database_version = 1;
    public String CREATE_USER_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s number," +
                    "  %s text," +
                    "  %s text," +
                    "  %s text)",
            TABLE_NAME, ID_COLUMN, NAME,PHONE_NO,ADDRESS,USERNAME,PASSWORD);

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, database_version);
        Log.d("Database operations", "Database created");
    }
    @Override
    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_USER_TABLE);
        Log.d("Database operations", "Table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public String getSinlgeEntry(String userName)
    {
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT username,password FROM "+ TABLE_NAME +" WHERE username = ?";

            Cursor cursor = db.rawQuery(query, new String[]{userName});
            if (cursor.getCount() > 0) // UserName Not Exist
            {
                if(cursor.moveToFirst() && cursor != null){
                    do {
                        password = cursor.getString(1);
                        Log.d("blah blah",password);

                    }while(cursor.moveToNext());
                }


            }
             return password;

        }



}
