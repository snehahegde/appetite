package com.appetite;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewDbHelper extends SQLiteOpenHelper {
    public static final String ID_COLUMN = "id";
    public static final String CHEF_COLUMN = "chef";
    public static final String USER_COLUMN = "user";
    public static final String REVIEW_COLUMN = "review";
    public static final String RATING_COLUMN="rating";
    public static final String DATE_COLUMN = "date";
    public static final String DATABASE_TABLE = "REVIEW_TABLE1";
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "  %s integer primary key autoincrement, " +
                    "  %s text," +
                    "  %s text," +
                    "  %s text," +
                    "  %s text," +
                    "  %s text)",
            DATABASE_TABLE, ID_COLUMN, CHEF_COLUMN, USER_COLUMN, REVIEW_COLUMN,RATING_COLUMN,DATE_COLUMN);

    public ReviewDbHelper(Context context) {
        super(context, DATABASE_TABLE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
}
