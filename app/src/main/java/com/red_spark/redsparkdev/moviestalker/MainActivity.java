package com.red_spark.redsparkdev.moviestalker;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.red_spark.redsparkdev.moviestalker.data.Constants;
import com.red_spark.redsparkdev.moviestalker.data.ItemData;
import com.red_spark.redsparkdev.moviestalker.data.MovieData;
import com.red_spark.redsparkdev.moviestalker.data.SeriesData;
import com.red_spark.redsparkdev.moviestalker.data.database.DbHelper;
import com.red_spark.redsparkdev.moviestalker.data.database.FavContract;
import com.red_spark.redsparkdev.moviestalker.fragments.ItemDetailFragment;
import com.red_spark.redsparkdev.moviestalker.fragments.ThumbnailFragment;
import com.red_spark.redsparkdev.moviestalker.fragments.adapters.MainFragmentPagerAdapter;
import com.red_spark.redsparkdev.moviestalker.network.GetMovieDataInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.red_spark.redsparkdev.moviestalker.data.Constants.DATA_TYPE.MOVIES;

public class MainActivity extends AppCompatActivity implements ThumbnailFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();


    @BindView(R.id.errorText)TextView mErrorView;
    @BindView(R.id.progressBar)ProgressBar mProgressBar;
    @BindView(R.id.pager) ViewPager mPager;
    @BindView(R.id.detail_fragment_container) FrameLayout detailFragmentContainer;

    MainFragmentPagerAdapter mAdapter;
    FragmentManager mFragmentManger;
    ItemDetailFragment itemDetailFragment;

    private ItemData movieData;
    private ItemData tvSeriesData;
    private List<MovieData> favMovieData;
    private SeriesData favSeriesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManger = getSupportFragmentManager();
        mPager.setVisibility(View.VISIBLE);
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);




        //checking for save instance state
        if(savedInstanceState == null) {

            buildTab(MOVIES);
            buildTab(Constants.DATA_TYPE.SERIES);
            buildFavTab();

        }else{
            movieData = (ItemData) savedInstanceState
                    .getSerializable(Constants.DATA_TYPE.MOVIES.getTag());
            tvSeriesData = (ItemData) savedInstanceState
                    .getSerializable(Constants.DATA_TYPE.SERIES.getTag());

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constants.DATA_TYPE.MOVIES.getTag(), movieData);
        outState.putSerializable(Constants.DATA_TYPE.SERIES.getTag(), tvSeriesData);
    }

    @Override
    public void onThumbnailClick(int position, Constants.DATA_TYPE fragmentType, Drawable thumbnailImage) {
        switch (fragmentType){
            case MOVIES:
                openDetailsFragment(movieData.getResults().get(position), thumbnailImage);break;
            case SERIES:
                openDetailsFragment(tvSeriesData.getResults().get(position), thumbnailImage);break;

        }

                LogHelp.print(TAG, fragmentType+"Clicked on : "+position);

    }

    @Override
    public void onError() {
       // mFragmentManager.beginTransaction().remove(topMoviesFragment).commit();
       // mErrorView.setVisibility(View.VISIBLE);
       // mProgressBar.setVisibility(View.GONE);

    }
    private void buildFavTab(){
        DbHelper dbHelper = new DbHelper(this);

        convertData(dbHelper.getList());


    }

    private void buildTab(final Constants.DATA_TYPE tabType){
        //Creating retrofit builder instance
        final Retrofit.Builder builder =  new Retrofit.Builder()
                //adding a base url that will be quarried
                .baseUrl(Constants.MOVIE_BASE_URL)
                //specifying what converter we will use
                //since we are getting data in json format well use GSON
                .addConverterFactory(GsonConverterFactory.create());

        //Creating the actual retrofit object
        Retrofit retrofit = builder.build();
        //Instance of the retrofit interface
        GetMovieDataInterface networkInterface = retrofit.create(GetMovieDataInterface.class);

        //Calling the action on the interface(we only have one and it's a @GET request)
        //We also specify the variables of the url



        String url = Constants.MOVIE_BASE_URL
                + tabType.getTag()
                + "/top_rated?api_key="
                + getString(R.string.api_key);
        Call<ItemData> networkCall = networkInterface
                .movieList(url);
        LogHelp.print(TAG, url);

        mProgressBar.setVisibility(View.VISIBLE);
        //executing the request asynchronously
        networkCall.enqueue(new Callback<ItemData>() {

            @Override
            public void onResponse(Call<ItemData> call, Response<ItemData> response) {

                switch (tabType){
                    case MOVIES:
                        movieData = response.body(); break;
                    case SERIES:
                        tvSeriesData = response.body(); break;
                }

                //Removing the progress bar since we have loaded the data
                mProgressBar.setVisibility(View.GONE);

                //get(0) since there should only be one list item in the response
                if(response.body() != null){
                    mErrorView.setVisibility(View.GONE);

                    ArrayList<String> thumbnails = extractThumbnails(response.body());
                    FragmentManager mFragmentManager = getSupportFragmentManager();
                    List<Fragment> fragments = mFragmentManager.getFragments();
                    ThumbnailFragment movieFragment = (ThumbnailFragment)fragments.get(tabType.getPosition());
                    movieFragment.update(thumbnails, tabType);





                }else{
                    LogHelp.print(TAG, "response.body() == null");
                    //mErrorView.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onFailure(Call<ItemData> call, Throwable t) {
                mErrorView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                LogHelp.print(TAG, "onFailure");
            }

            private ArrayList<String> extractThumbnails(ItemData data){
                ArrayList<String> thumbnails = new ArrayList<>();
                for(ItemData.Result result: data.results){
                    thumbnails.add(result.getPoster_path());
                }
                return thumbnails;
            }
        });

    }
    private void openDetailsFragment(ItemData.Result itemDetails, Drawable image){
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BUNDLE_KEY.DATA, itemDetails);
        bundle.putSerializable(Constants.BUNDLE_KEY.THUMBNAIL, (Serializable)image);
        itemDetailFragment = new ItemDetailFragment();
        itemDetailFragment.setArguments(bundle);

        mPager.setVisibility(View.GONE);
        detailFragmentContainer.setVisibility(View.VISIBLE);

        mFragmentManger.beginTransaction()
                .replace(R.id.detail_fragment_container, itemDetailFragment, Constants.BUNDLE_KEY.LAYOUT)
                .commit();

    }

    @Override
    public void onBackPressed() {
        if( mPager.getVisibility() == View.GONE){
            mPager.setVisibility(View.VISIBLE);
            detailFragmentContainer.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }
    }

    private void convertData(Cursor[] cursor){
        while(cursor[0].moveToNext()){
            MovieData movieData = new MovieData();
            movieData.id = cursor[0].getInt(
                    cursor[0].getColumnIndexOrThrow(FavContract.FavMovieEntry.COLUMN_NETWORK_ID)
            );
            // TODO: 16-Aug-17 finish loading in all the data
        }

    }
}
