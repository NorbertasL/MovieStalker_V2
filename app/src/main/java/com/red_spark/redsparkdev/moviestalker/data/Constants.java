package com.red_spark.redsparkdev.moviestalker.data;

/**
 * Created by Red_Spark on 08-Aug-17.
 */

public class Constants {
    public final static String MOVIE_BASE_URL = "https://api.themoviedb.org/";
    public final static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public final static String TEST_TAG = "TEST";

    public static class BUNDLE_KEY{
        public static final String THUMBNAIL = "THUMBNAIL_KEY";
        public static final String LAYOUT = "LAYOUT_KEY";
        public static final String POSITION = "POSITION_KEY";
        public static final String DATA = "DATA_KEY";
        public static class NETWORK{
            public static final String TYPE = "type";
            public static final String LIST = "list";
            public static final String PAGE_NUM = "pager_number";
        }
    }
    public static class LOADER_IDS{
        public static final int MAIN_ACTIVITY_MOVIES = 1;
        public static final int MAIN_ACTIVITY_SERIES = 2;

    }
    public enum POSTER_SIZE{
        W95("w92"),
        w154("w154"),
        W185("w185"),
        W342("w342"),
        W500("w500"),
        W780("w780"),
        ORIGINAL("original");
        String urlTag;
        POSTER_SIZE(String urlTag) {
            this.urlTag = urlTag;
        }
        public String getUrlTag(){
            return urlTag;
        }

    }
    public enum BACKDROP_SIZE{
        W300("w300"),
        W780("w780"),
        W1280("1280"),
        ORIGINAL("original");
        String urlTag;
        BACKDROP_SIZE(String urlTag){this.urlTag = urlTag;}
        public String getUrlTag(){return urlTag;}

    }

    public enum DATA_TYPE {MOVIES("movie", 0, LOADER_IDS.MAIN_ACTIVITY_MOVIES),
        SERIES("tv", 1, LOADER_IDS.MAIN_ACTIVITY_SERIES),
        FAVORITES("fav", 2, -1);
        String tag;
        int position;
        int loaderID;
        DATA_TYPE(String tag, int position, int loaderID){
            this.tag = tag; this.position = position; this.loaderID = loaderID;
        }
        public String getTag(){
            return tag;
        }
        public int getPosition(){
            return position;
        }
        public int getLoaderID(){return loaderID;}




    }

}
