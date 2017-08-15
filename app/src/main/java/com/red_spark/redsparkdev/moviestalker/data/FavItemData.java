package com.red_spark.redsparkdev.moviestalker.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Red_Spark on 10-Aug-17.
 */

public class FavItemData {
    public int getVote_count() {return vote_count;}
    public int getId() {return id;}
    public String getVideo() {return video;}
    public double getVote_average() {return vote_average;}
    public String getTitle() {return title;}
    public double getPopularity() {return popularity;}
    public String getPoster_path() {return poster_path;}
    public String getOriginal_language() {return original_language;}
    public String getOriginal_title() {return original_title;}
    public List<String> getGenre_ids() {return genre_ids;}
    public String getBackdrop_path() {return backdrop_path;}
    public String getAdult() {return adult;}
    public String getOverview() {return overview;}
    public String getRelease_date() {return release_date;}
    public int getDatabaseId() {return databaseId;}
    public String getLocal_poster_path() {return local_poster_path;}
    public String getLocal_backdrop_path() {return local_backdrop_path;}

    int vote_count;
    int id;
    String video;
    double vote_average;
    String title;
    double popularity;
    String poster_path;
    String original_language;
    String original_title;
    List<String> genre_ids = new ArrayList<>();
    String backdrop_path;
    String adult;
    String overview;
    String release_date;
    int databaseId;
    String local_poster_path;
    String local_backdrop_path;

}
