package com.red_spark.redsparkdev.moviestalker.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Red_Spark on 15-Aug-17.
 */

public class MovieData {
    public String vote_count;
    public int id;
    public String video;
    public String vote_average;
    public String title;
    public String popularity;
    public String poster_path;
    public String local_poster_path;
    public String original_language;
    public String original_title;
    public List<String> genre_ids = new ArrayList<>();
    public String backdrop_path;
    public boolean adult;
    public String overview;
    public String release_date;
}
