package com.red_spark.redsparkdev.moviestalker.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Red_Spark on 10-Aug-17.
 */

public class FavItemData {
    public String getVote_count() {return vote_count;}
    public String getId() {return id;}
    public String getVideo() {return video;}
    public String getVote_average() {return vote_average;}
    public String getTitle() {return title;}
    public String getPopularity() {return popularity;}
    public String getPoster_path() {return poster_path;}
    public String getOriginal_language() {return original_language;}
    public String getOriginal_title() {return original_title;}
    public List<String> getGenre_ids() {return genre_ids;}
    public String getBackdrop_path() {return backdrop_path;}
    public String getAdult() {return adult;}
    public String getOverview() {return overview;}
    public String getRelease_date() {return release_date;}
    public String getDatabaseId() {return databaseId;}
    public String getLocal_poster_path() {return local_poster_path;}
    public String getLocal_backdrop_path() {return local_backdrop_path;}

    String vote_count;
    String id;
    String video;
    String vote_average;
    String title;
    String popularity;
    String poster_path;
    String original_language;
    String original_title;
    List<String> genre_ids = new ArrayList<>();
    String backdrop_path;
    String adult;
    String overview;
    String release_date;
    String databaseId;
    String local_poster_path;
    String local_backdrop_path;

}
