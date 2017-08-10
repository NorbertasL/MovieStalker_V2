package moviestalkercom.red_spark.redsparkdev.moviestalker.network;

import moviestalkercom.red_spark.redsparkdev.moviestalker.data.ItemData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Red_Spark on 08-Aug-17.
 *  * Part of Retrofit implementation
 */

public interface GetMovieDataInterface {
    @GET
    Call<ItemData> movieList(@Url String url);
}
