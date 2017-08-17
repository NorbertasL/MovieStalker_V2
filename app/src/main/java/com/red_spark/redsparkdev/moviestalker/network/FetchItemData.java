package com.red_spark.redsparkdev.moviestalker.network;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import com.red_spark.redsparkdev.moviestalker.MainActivity;
import com.red_spark.redsparkdev.moviestalker.R;
import com.red_spark.redsparkdev.moviestalker.data.Constants;
import com.red_spark.redsparkdev.moviestalker.data.ItemData;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Red_Spark on 17-Aug-17.
 */

public class FetchItemData implements LoaderManager.LoaderCallbacks<ItemData>{
    private MainActivity mainActivity;
    private Constants.DATA_TYPE dataType;

    public FetchItemData(MainActivity activity){
        mainActivity=activity;
    }
    @Override
    public Loader<ItemData> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ItemData>(mainActivity){
            private String list;
            private Integer page = null;

            @Override
            protected void onStartLoading() {
                if(args == null){
                    return;
                }
                dataType = (Constants.DATA_TYPE)args.getSerializable(Constants.BUNDLE_KEY.NETWORK.TYPE);
                list = args.getString(Constants.BUNDLE_KEY.NETWORK.LIST);
                if(args.containsKey(Constants.BUNDLE_KEY.NETWORK.PAGE_NUM))
                    page = args.getInt(Constants.BUNDLE_KEY.NETWORK.PAGE_NUM);



                // TODO: 17-Aug-17 make a loading indicator
                forceLoad();
            }

            @Override
            public ItemData loadInBackground() {
                Retrofit.Builder builder =  new Retrofit.Builder()
                        //adding a base url that will be quarried
                        .baseUrl(Constants.MOVIE_BASE_URL)
                        //specifying what converter we will use
                        //since we are getting data in json format well use GSON
                        .addConverterFactory(GsonConverterFactory.create());

                //Creating the actual retrofit object
                Retrofit retrofit = builder.build();
                //Instance of the retrofit interface
                GetMovieDataInterface networkInterface = retrofit.create(GetMovieDataInterface.class);

                Call<ItemData> networkCall = networkInterface.itemList(
                        dataType.getTag(),
                        list,
                        mainActivity.getString(R.string.api_key),
                        page
                );
                try{
                   return networkCall.execute().body();
                }catch (IOException e){
                    // TODO: 17-Aug-17 error here
                    return null;
                }

            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ItemData> loader, ItemData data) {
        data.setDataType(dataType);
        mainActivity.setData(data);


    }

    @Override
    public void onLoaderReset(Loader<ItemData> loader) {

    }
}
