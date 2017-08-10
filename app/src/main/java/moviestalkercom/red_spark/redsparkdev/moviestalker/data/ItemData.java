package moviestalkercom.red_spark.redsparkdev.moviestalker.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Red_Spark on 08-Aug-17.
 *  * JSON data converted into a java class via GSON
 */

public class ItemData {
    public String page;
    public String total_results;
    public String total_pages;

    public List<Result> results = new ArrayList<>();
    public class Result {
        String vote_count;
        String id;
        String video;
        String vote_average;
        String title;
        String popularity;
        private String poster_path;
        String original_language;
        String original_title;
        List<String> genre_ids = new ArrayList<>();
        String backdrop_path;
        String adult;
        String overview;
        String release_date;

        public String getPosterPath(){
            return poster_path;
        }
    }

}
