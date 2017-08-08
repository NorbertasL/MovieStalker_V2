package moviestalkercom.red_spark.redsparkdev.moviestalker.network;

import java.util.ArrayList;

import moviestalkercom.red_spark.redsparkdev.moviestalker.data.MovieData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Red_Spark on 08-Aug-17.
 *  * Part of Retrofit implementation
 */

public interface GetMovieDataInterface {
    @GET("/3/movie/top_rated?api_key={api_key}")
    Call<ArrayList<MovieData>> movieList(@Path("api_key") String apiKey);
}