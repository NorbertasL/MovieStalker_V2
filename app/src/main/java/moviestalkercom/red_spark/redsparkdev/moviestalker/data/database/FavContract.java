package moviestalkercom.red_spark.redsparkdev.moviestalker.data.database;

import android.provider.BaseColumns;

/**
 * Created by Red_Spark on 11-Aug-17.
 *
 */

public class FavContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FavContract() {}

    public static class FavEntry implements BaseColumns{

        public final static String TABLE_NAME = "Favorite_Table";
        public final static String COLUMN_NTWORK_ID = "network_id";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_ORIGINAL_TITLE = "original_title";
        public final static String COLUMN_VOTE_COUNT = "vote_count";
        public final static String COLUMN_VIDEO_LIST_PATH = "video_list_path";
        public final static String COLUMN_VOTE_AVERAGE = "vote_average";
        public final static String COLUMN_POPULARITY = "popularity";
        public final static String COLUMN_POSTER_PATH = "poster_path";
        public final static String COLUMN_POSTER_LOCAL_PATH = "poster_local_path";
        public final static String COLUMN_ORIGINAL_LANQUAGE = "original_language";
        public final static String COLUMN_GENRE_IDS = "genre_ids";
        public final static String COLUMN_BACKDROP_PATH = "backdrop_path";
        public final static String COLUMN_BACKDROP_LOCAL_PATH = "backdrop_local_path";
        public final static String COLUMN_ADULT_RATED = "adult_rated";
        public final static String COLUMN_OVERVIEW = "overview";
        public final static String COLUMN_RELEASE_DATE = "release_date";

    }

}
