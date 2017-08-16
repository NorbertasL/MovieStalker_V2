package com.red_spark.redsparkdev.moviestalker.network;

import com.red_spark.redsparkdev.moviestalker.data.ItemData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Red_Spark on 08-Aug-17.
 *  * Part of Retrofit implementation
 */

public interface GetMovieDataInterface {
    @GET("/3/{type}/{list}")
    Call<ItemData> itemList(
            @Path("type")String type,
            @Path("list")String list,
            @Query("api_key") String key);
}
