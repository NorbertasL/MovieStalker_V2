package com.red_spark.redsparkdev.moviestalker;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.red_spark.redsparkdev.moviestalker.data.Constants;
import com.red_spark.redsparkdev.moviestalker.data.ImageStorage;
import com.red_spark.redsparkdev.moviestalker.data.ItemData;
import com.red_spark.redsparkdev.moviestalker.data.MovieData;
import com.red_spark.redsparkdev.moviestalker.data.SeriesData;
import com.red_spark.redsparkdev.moviestalker.data.database.DbHelper;
import com.red_spark.redsparkdev.moviestalker.data.database.FavContract;
import com.red_spark.redsparkdev.moviestalker.fragments.FavThumbnailFragment;
import com.red_spark.redsparkdev.moviestalker.fragments.ItemDetailFragment;
import com.red_spark.redsparkdev.moviestalker.fragments.ThumbnailFragment;
import com.red_spark.redsparkdev.moviestalker.fragments.adapters.MainFragmentPagerAdapter;
import com.red_spark.redsparkdev.moviestalker.network.FetchItemData;

import static com.red_spark.redsparkdev.moviestalker.data.Constants.DATA_TYPE.MOVIES;
import static com.red_spark.redsparkdev.moviestalker.data.Constants.DATA_TYPE.SERIES;

public class MainActivity extends AppCompatActivity implements ThumbnailFragment.OnFragmentInteractionListener, FavThumbnailFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();


    @BindView(R.id.errorText)TextView mErrorView;
    @BindView(R.id.progressBar)ProgressBar mProgressBar;
    @BindView(R.id.pager) ViewPager mPager;
    @BindView(R.id.detail_fragment_container) FrameLayout detailFragmentContainer;

    MainFragmentPagerAdapter mAdapter;
    FragmentManager mFragmentManger;
    ItemDetailFragment itemDetailFragment;

    private List<ItemData> movieData;
    private List<ItemData> tvSeriesData;
    private List<MovieData> favMovieData;
    private SeriesData favSeriesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieData = new ArrayList<>();
        tvSeriesData = new ArrayList<>();
        favMovieData= new ArrayList<>();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManger = getSupportFragmentManager();
        mPager.setVisibility(View.VISIBLE);
        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);






        //checking for save instance state
        if(savedInstanceState == null) {
            buildTab(MOVIES, 0);
            buildTab(Constants.DATA_TYPE.SERIES, 0);


        }else{
            movieData = savedInstanceState.getParcelableArrayList(MOVIES.getTag());
            tvSeriesData = savedInstanceState.getParcelableArrayList(SERIES.getTag());
            if(movieData == null)
                buildTab(MOVIES, 0);
            if(tvSeriesData == null)
                buildTab(Constants.DATA_TYPE.SERIES, 0);

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.DATA_TYPE.MOVIES.getTag(), (ArrayList<? extends Parcelable>) movieData);
        outState.putParcelableArrayList(Constants.DATA_TYPE.SERIES.getTag(), (ArrayList<? extends Parcelable>)tvSeriesData);
    }

    @Override
    public void onThumbnailClick(int position, Constants.DATA_TYPE fragmentType, Drawable thumbnailImage) {
        switch (fragmentType){
            case MOVIES:
                openDetailsFragment(calculatePos(position, fragmentType), thumbnailImage);break;
            case SERIES:
                openDetailsFragment(calculatePos(position, fragmentType), thumbnailImage);break;

        }

    }

    @Override
    public void requestMoreData(ThumbnailFragment fragment, Constants.DATA_TYPE dataType) {
        switch (dataType){
            case MOVIES:
                buildTab(dataType, movieData.size()+1);
                break;
            case SERIES:
                buildTab(dataType, tvSeriesData.size()+1);
                break;

        }


    }

    @Override
    public void onFragmentCreated(FavThumbnailFragment fragment) {
        getFavData();
        fragment.update(favMovieData);
    }

    @Override
    public void onError() {
       // mFragmentManager.beginTransaction().remove(topMoviesFragment).commit();
       // mErrorView.setVisibility(View.VISIBLE);
       // mProgressBar.setVisibility(View.GONE);

    }

    private void getFavData(){
        DbHelper dbHelper = new DbHelper(this);
        Cursor[] cursors = dbHelper.getList();
        convertData(cursors);
    }
    private void buildTab(final Constants.DATA_TYPE tabType, int pageNum){
        Bundle queryBundle = new Bundle();
        queryBundle.putString(Constants.BUNDLE_KEY.NETWORK.LIST, "top_rated");
        queryBundle.putSerializable(Constants.BUNDLE_KEY.NETWORK.TYPE, tabType);
        if(pageNum>0)
            queryBundle.putInt(Constants.BUNDLE_KEY.NETWORK.PAGE_NUM, pageNum);

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<ItemData> itemLoader = loaderManager.getLoader(tabType.getLoaderID());
        if(itemLoader == null){
            loaderManager.initLoader(tabType.getLoaderID(),
                    queryBundle,
                    new FetchItemData(this));
        }else{
            loaderManager.restartLoader(tabType.getLoaderID(),
                    queryBundle,
                    new FetchItemData(this));
        }



    }
    private void openDetailsFragment(ItemData.Result itemDetails, Drawable image){

        ImageStorage.saveImage(ImageStorage.ImageType.TEMP, image, Constants.BUNDLE_KEY.THUMBNAIL);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BUNDLE_KEY.DATA, itemDetails);
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
        favMovieData.clear();
        while(cursor[0].moveToNext()){
            MovieData movieData = new MovieData();
            movieData.id = cursor[0].getInt(
                    cursor[0].getColumnIndexOrThrow(FavContract.FavMovieEntry.COLUMN_NETWORK_ID)
            );
            favMovieData.add(movieData);
            // TODO: 16-Aug-17 finish loading in all the data
        }

    }
    public void setData(ItemData data){
        switch (data.getDataType()){
            case MOVIES:
                movieData.add(data);
                break;
            case SERIES:
                tvSeriesData.add(data);
                break;
        }
        ArrayList<String> thumbnails = extractThumbnails(data);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = mFragmentManager.getFragments();
        ThumbnailFragment movieFragment = (ThumbnailFragment)fragments.get(data.getDataType().getPosition());
        movieFragment.update(thumbnails, data.getDataType());

    }


    private ArrayList<String> extractThumbnails(ItemData data){
        ArrayList<String> thumbnails = new ArrayList<>();
        for(ItemData.Result result: data.results){
            thumbnails.add(result.getPoster_path());
        }
        return thumbnails;
    }
    private ItemData.Result calculatePos(int pos, Constants.DATA_TYPE dataType){
        int count = 0;
        int page = 0;
        List<ItemData> loopObject;
        switch (dataType){
            case MOVIES:
                loopObject = movieData;
                break;
            case SERIES:
                loopObject = tvSeriesData;
                break;
            default:
                return null;

        }
        ItemData.Result returnRes = null;
        for(ItemData data: loopObject){
            if(pos < data.getResults().size()) {
                return data.getResults().get(pos);

            }else if(pos < (count+=data.getResults().size())){
                return  data.getResults().get(pos-(count-data.getResults().size()));

            }


        }
        return null;
    }
}
