package com.red_spark.redsparkdev.moviestalker.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.red_spark.redsparkdev.moviestalker.data.database.FavContract.FavEntry;



/**
 * Created by Red_Spark on 11-Aug-17.
 */

public class DbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "movie_stalker.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
       final String CREATE_SQL = "CREATE TABLE "+ FavEntry.TABLE_NAME+ " ("
               + FavEntry.COLUMN_NETWORK_ID+" INTEGER NOT NULL UNIQUE, "
               + FavEntry.COLUMN_TITLE+" TEXT NOT NULL, "
               + FavEntry.COLUMN_ORIGINAL_TITLE+" TEXT, "
               + FavEntry.COLUMN_VOTE_COUNT+" INTEGER, "
               + FavEntry.COLUMN_VIDEO_LIST_PATH+" TEXT, "
               + FavEntry.COLUMN_VOTE_AVERAGE+" REAL, "
               + FavEntry.COLUMN_POPULARITY+" REAL, "
               + FavEntry.COLUMN_POSTER_PATH+" TEXT, "
               + FavEntry.COLUMN_POSTER_LOCAL_PATH+ " TEXT, "
               + FavEntry.COLUMN_ORIGINAL_LANQUAGE+ " TEXT, "
               + FavEntry.COLUMN_GENRE_IDS+" TEXT, "
               + FavEntry.COLUMN_BACKDROP_PATH+" TEXT, "
               + FavEntry.COLUMN_BACKDROP_LOCAL_PATH+" TEXT, "
               + FavEntry.COLUMN_ADULT_RATED+" TEXT, "
               + FavEntry.COLUMN_OVERVIEW+" TEXT, "
               + FavEntry.COLUMN_RELEASE_DATE+" TEXT);";

        db.execSQL(CREATE_SQL);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+FavEntry.TABLE_NAME);
        onCreate(db);

    }
}
