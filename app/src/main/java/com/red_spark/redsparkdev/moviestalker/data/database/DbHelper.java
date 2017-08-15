package com.red_spark.redsparkdev.moviestalker.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.red_spark.redsparkdev.moviestalker.data.FavItemData;
import com.red_spark.redsparkdev.moviestalker.data.ItemData;
import com.red_spark.redsparkdev.moviestalker.data.database.FavContract.FavEntry;

import java.util.ArrayList;
import java.util.List;


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
               + FavEntry.COLUMN_RELEASE_DATE+" TEXT, "
               + FavEntry.COLUMN_TYPE+" TEXT);";

        db.execSQL(CREATE_SQL);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+FavEntry.TABLE_NAME);
        onCreate(db);
    }
    public boolean addData(ItemData.Result data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavEntry.COLUMN_NETWORK_ID, Integer.parseInt(data.getID()));
        contentValues.put(FavEntry.COLUMN_TITLE, data.getTitle());
        contentValues.put(FavEntry.COLUMN_ORIGINAL_TITLE, data.getOriginal_title());
        contentValues.put(FavEntry.COLUMN_VOTE_COUNT, Integer.parseInt(data.getVote_count()));
        contentValues.put(FavEntry.COLUMN_VIDEO_LIST_PATH, data.getVideo());
        contentValues.put(FavEntry.COLUMN_VOTE_AVERAGE, Double.parseDouble(data.getVote_average()));
        contentValues.put(FavEntry.COLUMN_POPULARITY, Double.parseDouble(data.getPopularity()));
        contentValues.put(FavEntry.COLUMN_POSTER_PATH, data.getPoster_path());
        contentValues.put(FavEntry.COLUMN_ORIGINAL_LANQUAGE, data.getOriginal_language());
        contentValues.put(FavEntry.COLUMN_BACKDROP_PATH, data.getBackdrop_path());
        contentValues.put(FavEntry.COLUMN_ADULT_RATED, data.getAdult());
        contentValues.put(FavEntry.COLUMN_OVERVIEW, data.getOverview());
        contentValues.put(FavEntry.COLUMN_RELEASE_DATE, data.getRelease_date());
        contentValues.put(FavEntry.COLUMN_TYPE, data.getDataType().toString());
        contentValues.put(FavEntry.COLUMN_GENRE_IDS, arrayToLongString(data.getGenre_ids()));

        long newRowId = db.insert(FavEntry.TABLE_NAME, null, contentValues);
        if(newRowId != -1){
            return true;
        }
        return false;
    }
    String arrayToLongString(List<String> array) {
        StringBuilder sb = new StringBuilder();
        for(String s: array){
            sb.append(s);
            sb.append("/t");
        }
        return sb.toString();

    }
}
