package com.red_spark.redsparkdev.moviestalker.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Red_Spark on 08-Aug-17.
 *  * JSON data converted into a java class via GSON
 */

public class ItemData implements Serializable{

    public String getPage() {return page;}
    public String getTotal_results() {return total_results;}
    public String getTotal_pages() {return total_pages;}
    public List<Result> getResults() {return results;}

    private String page;
    private String total_results;
    private String total_pages;

    public Constants.DATA_TYPE getDataType() {return dataType;}
    public void setDataType(Constants.DATA_TYPE dataType) {this.dataType = dataType;}
    private Constants.DATA_TYPE dataType;

    public List<Result> results = new ArrayList<>();
    public class Result implements Serializable{
        public String getVote_count() {return vote_count;}
        public String getID() {return id;}
        public String getVideo() {return video;}
        public String getVote_average() {return vote_average;}
        public String getTitle() {
            //If the object is holding tv_series data the title is store in the name field;
            if(title == null || title.isEmpty())
                return name;
            return title;
        }
        public String getPopularity() {return popularity;}
        public String getPoster_path() {return poster_path;}
        public String getOriginal_language() {return original_language;}
        public String getOriginal_title() {
            if(original_title == null || original_title.isEmpty())
                return original_name;
            return original_title;
        }
        public List<String> getGenre_ids() {return genre_ids;}
        public String getBackdrop_path() {return backdrop_path;}
        public String getAdult() {return adult;}
        public String getOverview() {return overview;}
        public String getRelease_date() {
            if(release_date == null || release_date.isEmpty())
                return first_air_date;
            return release_date;
        }
        public Constants.DATA_TYPE getDataType(){
            if(title == null || title.isEmpty())
                return Constants.DATA_TYPE.SERIES;
            return Constants.DATA_TYPE.MOVIES;
        }

        String vote_count;
        String id;
        String video;
        String vote_average;
        String title;//used in movies
        String name;//used in tv_series
        String popularity;
        String poster_path;
        String original_language;
        String original_title;
        String original_name;
        List<String> genre_ids = new ArrayList<>();
        String backdrop_path;
        String adult;
        String overview;
        String release_date;
        String first_air_date;

    }

}
