package com.red_spark.redsparkdev.moviestalker.data.database;

import android.provider.BaseColumns;

/**
 * Created by Red_Spark on 11-Aug-17.
 *
 */

public class FavContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FavContract() {}

    public static class FavMovieEntry{

        public final static String TABLE_NAME = "Favorite_Movie_Table";

        public final static String COLUMN_NETWORK_ID = "network_id";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_ORIGINAL_TITLE = "original_title";
        public final static String COLUMN_VOTE_COUNT = "vote_count";
        public final static String COLUMN_VIDEO_LIST_PATH = "video_list_path";
        public final static String COLUMN_VOTE_AVERAGE = "vote_average";
        public final static String COLUMN_POPULARITY = "popularity";
        public final static String COLUMN_POSTER_PATH = "poster_path";
        public final static String COLUMN_POSTER_LOCAL_PATH = "poster_local_path";
        public final static String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public final static String COLUMN_BACKDROP_PATH = "backdrop_path";
        public final static String COLUMN_BACKDROP_LOCAL_PATH = "backdrop_local_path";
        public final static String COLUMN_ADULT_RATED = "adult_rated";
        public final static String COLUMN_OVERVIEW = "overview";
        public final static String COLUMN_RELEASE_DATE = "release_date";

    }
    public static class FavSeriesEntry{

        public final static String TABLE_NAME = "Favorite_Series_Table";

        public final static String COLUMN_NETWORK_ID = FavMovieEntry.COLUMN_NETWORK_ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_ORIGINAL_NAME = "original_name";
        public final static String COLUMN_VOTE_COUNT = "vote_count";
        public final static String COLUMN_VIDEO_LIST_PATH = "video_list_path";
        public final static String COLUMN_VOTE_AVERAGE = "vote_average";
        public final static String COLUMN_POPULARITY = "popularity";
        public final static String COLUMN_POSTER_PATH = "poster_path";
        public final static String COLUMN_POSTER_LOCAL_PATH = "poster_local_path";
        public final static String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public final static String COLUMN_BACKDROP_PATH = "backdrop_path";
        public final static String COLUMN_BACKDROP_LOCAL_PATH = "backdrop_local_path";
        public final static String COLUMN_ADULT_RATED = "adult_rated";
        public final static String COLUMN_OVERVIEW = "overview";
        public final static String COLUMN_AIR_DATE = "air_date";

    }
    //No need for base columns
    public static class GenreEntry{
        public final static String TABLE_NAME = "Favorite_Genre_Table";

        public final static String GENRE_ID = "genre_id";
        //storing the name so we can generate lists from this table based on genre
        public final static String NAME = "name";
        public final static String TYPE = "type";//is it a movie or a series
        public final static String ITEM_ID = "item_id";//ID of the item in their respected table
        //LOCAL_POSTER_PATH so we can generate thumbnail lists
        public final static String LOCAL_POSTER_PATH = "local_poster_path";
    }

}
