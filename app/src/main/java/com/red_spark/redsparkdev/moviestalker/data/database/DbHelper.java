package com.red_spark.redsparkdev.moviestalker.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.red_spark.redsparkdev.moviestalker.data.Constants;
import com.red_spark.redsparkdev.moviestalker.data.ItemData;
import com.red_spark.redsparkdev.moviestalker.data.MovieData;
import com.red_spark.redsparkdev.moviestalker.data.database.FavContract.FavMovieEntry;
import com.red_spark.redsparkdev.moviestalker.data.database.FavContract.FavSeriesEntry;
import com.red_spark.redsparkdev.moviestalker.data.database.FavContract.GenreEntry;
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
       final String CREATE_MOVIE_SQL = "CREATE TABLE "+ FavMovieEntry.TABLE_NAME+ " ("
               + FavMovieEntry.COLUMN_NETWORK_ID+" INTEGER NOT NULL UNIQUE, "
               + FavMovieEntry.COLUMN_TITLE+" TEXT NOT NULL, "
               + FavMovieEntry.COLUMN_ORIGINAL_TITLE+" TEXT, "
               + FavMovieEntry.COLUMN_VOTE_COUNT+" INTEGER, "
               + FavMovieEntry.COLUMN_VIDEO_LIST_PATH+" TEXT, "
               + FavMovieEntry.COLUMN_VOTE_AVERAGE+" REAL, "
               + FavMovieEntry.COLUMN_POPULARITY+" REAL, "
               + FavMovieEntry.COLUMN_POSTER_PATH+" TEXT, "
               + FavMovieEntry.COLUMN_POSTER_LOCAL_PATH+ " TEXT, "
               + FavMovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, "
               + FavMovieEntry.COLUMN_BACKDROP_PATH+" TEXT, "
               + FavMovieEntry.COLUMN_BACKDROP_LOCAL_PATH+" TEXT, "
               + FavMovieEntry.COLUMN_ADULT_RATED+" TEXT, "
               + FavMovieEntry.COLUMN_OVERVIEW+" TEXT, "
               + FavMovieEntry.COLUMN_RELEASE_DATE+" TEXT);";
        db.execSQL(CREATE_MOVIE_SQL);

       final String CREATE_SERIES_SQL = "CREATE TABLE "+ FavSeriesEntry.TABLE_NAME+ " ("
                + FavSeriesEntry.COLUMN_NETWORK_ID+" INTEGER NOT NULL UNIQUE, "
                + FavSeriesEntry.COLUMN_NAME+" TEXT NOT NULL, "
                + FavSeriesEntry.COLUMN_ORIGINAL_NAME+" TEXT, "
                + FavSeriesEntry.COLUMN_VOTE_COUNT+" INTEGER, "
                + FavSeriesEntry.COLUMN_VIDEO_LIST_PATH+" TEXT, "
                + FavSeriesEntry.COLUMN_VOTE_AVERAGE+" REAL, "
                + FavSeriesEntry.COLUMN_POPULARITY+" REAL, "
                + FavSeriesEntry.COLUMN_POSTER_PATH+" TEXT, "
                + FavSeriesEntry.COLUMN_POSTER_LOCAL_PATH+ " TEXT, "
                + FavSeriesEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, "
                + FavSeriesEntry.COLUMN_BACKDROP_PATH+" TEXT, "
                + FavSeriesEntry.COLUMN_BACKDROP_LOCAL_PATH+" TEXT, "
                + FavSeriesEntry.COLUMN_ADULT_RATED+" TEXT, "
                + FavSeriesEntry.COLUMN_OVERVIEW+" TEXT, "
                + FavSeriesEntry.COLUMN_AIR_DATE+" TEXT);";
        db.execSQL(CREATE_SERIES_SQL);


        /*
        final String CREATE_GENRE_SQL =  "CREATE TABLE "+ GenreEntry.TABLE_NAME+ " ("
                + GenreEntry.GENRE_ID+" INTEGER NOT NULL , "
                + GenreEntry.NAME+" TEXT, "
                + GenreEntry.TYPE+" TEXT, "
                + GenreEntry.ITEM_ID+" INTEGER, "
                + GenreEntry.LOCAL_POSTER_PATH+" TEXT);";
        db.execSQL(CREATE_GENRE_SQL);
        */


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ FavMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ FavSeriesEntry.TABLE_NAME);
        onCreate(db);
    }
    public boolean addRow(Constants.DATA_TYPE type, ItemData.Result data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long newRowId;

        switch (type){
            case MOVIES:
                newRowId = addMovies(db, contentValues, data);
                break;
            case SERIES:
                newRowId = addSeries(db, contentValues, data);
                break;
            default:
                newRowId = -1;
        }

        // TODO: 15-Aug-17 add genre as a new table
        if(newRowId != -1){
            return true;
        }
        return false;
    }
    public boolean removeRow(Constants.DATA_TYPE type, int networkID){
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = "";
        String columnName = "";
        switch (type) {
            case MOVIES:
                tableName = FavMovieEntry.TABLE_NAME;
                columnName = FavMovieEntry.COLUMN_NETWORK_ID;
                break;
            case SERIES:
                tableName = FavSeriesEntry.TABLE_NAME;
                columnName = FavSeriesEntry.COLUMN_NETWORK_ID;
                break;
            default:
                Log.e(DbHelper.class.getSimpleName(), "DID NOT SPECIFY DATA TYPE");
                return false;
        }
            String selection = columnName + " LIKE ?";
            String [] selectionArgs = {Integer.toString(networkID)};

            if(db.delete(tableName, selection, selectionArgs) > 0)
                return true;
            else
                return false;
    }
    public Cursor[] getList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor[] returnCursor = new Cursor[2];

        returnCursor[0] = db.query(
                FavMovieEntry.TABLE_NAME,
                null,//we want all the columns
                null,//we want all rows
                null,
                null,
                null,
                null);

        returnCursor[1] = db.query(
                FavSeriesEntry.TABLE_NAME,
                null,//we want all the columns
                null,//we want all rows
                null,
                null,
                null,
                null);

        return returnCursor;
    }
    public boolean checkIfInDatabase(Constants.DATA_TYPE type, int networkID){
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = "";
        String columnName = "";


        switch (type){
            case MOVIES:
                tableName = FavMovieEntry.TABLE_NAME;
                columnName = FavMovieEntry.COLUMN_NETWORK_ID;
                break;
            case SERIES:
                tableName = FavSeriesEntry.TABLE_NAME;
                columnName = FavSeriesEntry.COLUMN_NETWORK_ID;
                break;
            default:
                Log.e(DbHelper.class.getSimpleName(), "DID NOT SPECIFY DATA TYPE");
                return false;
        }
        String [] projection = {
                columnName
        };
        String selection = columnName + " = ?";
        String [] selectionArgs = {Integer.toString(networkID)};

        Cursor cursor = db.query(
              tableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        return cursor.moveToNext();
    }
    String arrayToLongString(List<String> array) {
        StringBuilder sb = new StringBuilder();
        for(String s: array){
            sb.append(s);
            sb.append("/t");
        }
        return sb.toString();

    }

    private long addMovies(SQLiteDatabase db, ContentValues contentValues, ItemData.Result data){

        contentValues.put(FavMovieEntry.COLUMN_NETWORK_ID, Integer.parseInt(data.getID()));
        contentValues.put(FavMovieEntry.COLUMN_TITLE, data.getTitle());
        contentValues.put(FavMovieEntry.COLUMN_ORIGINAL_TITLE, data.getOriginal_title());
        contentValues.put(FavMovieEntry.COLUMN_VOTE_COUNT, Integer.parseInt(data.getVote_count()));
        contentValues.put(FavMovieEntry.COLUMN_VIDEO_LIST_PATH, data.getVideo());
        contentValues.put(FavMovieEntry.COLUMN_VOTE_AVERAGE, Double.parseDouble(data.getVote_average()));
        contentValues.put(FavMovieEntry.COLUMN_POPULARITY, Double.parseDouble(data.getPopularity()));
        contentValues.put(FavMovieEntry.COLUMN_POSTER_PATH, data.getPoster_path());
        contentValues.put(FavMovieEntry.COLUMN_ORIGINAL_LANGUAGE, data.getOriginal_language());
        contentValues.put(FavMovieEntry.COLUMN_BACKDROP_PATH, data.getBackdrop_path());
        contentValues.put(FavMovieEntry.COLUMN_ADULT_RATED, data.getAdult());
        contentValues.put(FavMovieEntry.COLUMN_OVERVIEW, data.getOverview());
        contentValues.put(FavMovieEntry.COLUMN_RELEASE_DATE, data.getRelease_date());

        return db.insert(FavMovieEntry.TABLE_NAME, null, contentValues);
    }

    private long addSeries(SQLiteDatabase db, ContentValues contentValues, ItemData.Result data){
        contentValues.put(FavSeriesEntry.COLUMN_NETWORK_ID, Integer.parseInt(data.getID()));
        contentValues.put(FavSeriesEntry.COLUMN_NAME, data.getTitle());
        contentValues.put(FavSeriesEntry.COLUMN_ORIGINAL_NAME, data.getOriginal_title());
        contentValues.put(FavSeriesEntry.COLUMN_VOTE_COUNT, Integer.parseInt(data.getVote_count()));
        contentValues.put(FavSeriesEntry.COLUMN_VIDEO_LIST_PATH, data.getVideo());
        contentValues.put(FavSeriesEntry.COLUMN_VOTE_AVERAGE, Double.parseDouble(data.getVote_average()));
        contentValues.put(FavSeriesEntry.COLUMN_POPULARITY, Double.parseDouble(data.getPopularity()));
        contentValues.put(FavSeriesEntry.COLUMN_POSTER_PATH, data.getPoster_path());
        contentValues.put(FavSeriesEntry.COLUMN_ORIGINAL_LANGUAGE, data.getOriginal_language());
        contentValues.put(FavSeriesEntry.COLUMN_BACKDROP_PATH, data.getBackdrop_path());
        contentValues.put(FavSeriesEntry.COLUMN_ADULT_RATED, data.getAdult());
        contentValues.put(FavSeriesEntry.COLUMN_OVERVIEW, data.getOverview());
        contentValues.put(FavSeriesEntry.COLUMN_AIR_DATE, data.getRelease_date());
        
        return db.insert(FavSeriesEntry.TABLE_NAME, null, contentValues);
    }

}
