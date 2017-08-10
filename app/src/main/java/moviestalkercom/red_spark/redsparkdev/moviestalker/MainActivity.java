package moviestalkercom.red_spark.redsparkdev.moviestalker;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import moviestalkercom.red_spark.redsparkdev.moviestalker.data.Constants;
import moviestalkercom.red_spark.redsparkdev.moviestalker.data.ItemData;
import moviestalkercom.red_spark.redsparkdev.moviestalker.fragments.ThumbnailFragment;
import moviestalkercom.red_spark.redsparkdev.moviestalker.fragments.adapters.MainFragmentPagerAdapter;
import moviestalkercom.red_spark.redsparkdev.moviestalker.network.GetMovieDataInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ThumbnailFragment.OnFragmentInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName();




    private static String FRAGMENT_TAG = "Top_Movie_Fragment";
    private FragmentManager mFragmentManager;

    @BindView(R.id.errorText)TextView mErrorView;
    @BindView(R.id.progressBar)ProgressBar mProgressBar;

    MainFragmentPagerAdapter mAdapter;
    ViewPager mPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogHelp.print(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);








        /**
        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.goto_first);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        button = (Button)findViewById(R.id.goto_last);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(2);
            }
        });
         */
        mFragmentManager = getSupportFragmentManager();

        mAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        //checking for save instance state
        if(savedInstanceState == null) {

           // mProgressBar.setVisibility(View.VISIBLE);
            requestMovieData(0);
            requestMovieData(1);


        }

    }


    @Override
    public void onThumbnailClick(Uri uri) {
        // TODO: 09-Aug-17

    }

    @Override
    public void onError() {
       // mFragmentManager.beginTransaction().remove(topMoviesFragment).commit();
       // mErrorView.setVisibility(View.VISIBLE);
       // mProgressBar.setVisibility(View.GONE);

    }

    private void requestMovieData(int tabNumber){
        LogHelp.print(TAG, "requestMovieData for tab:"+tabNumber);

        final int tab = tabNumber;
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

        // TODO: 10-Aug-17 fix this up, only done for testing
        String type;
        switch(tabNumber){
            case 0:
                type = "movie";
                break;
            case 1:
                type = "tv";
                break;
            default:
                type = "";
        }

        String url = Constants.MOVIE_BASE_URL
                + type
                + "/top_rated?api_key="
                + getString(R.string.api_key);
        Call<ItemData> networkCall = networkInterface
                .movieList(url);

        mProgressBar.setVisibility(View.VISIBLE);
        //executing the request asynchronously
        networkCall.enqueue(new Callback<ItemData>() {

            @Override
            public void onResponse(Call<ItemData> call, Response<ItemData> response) {

                //Removing the progress bar since we have loaded the data
                mProgressBar.setVisibility(View.GONE);

                //get(0) since there should only be one list item in the response
                if(response.body() != null){
                    ArrayList<String> thumbnails = extractThumbnails(response.body());
                    List<Fragment> fragments = mFragmentManager.getFragments();
                    ThumbnailFragment movieFragment = (ThumbnailFragment)fragments.get(tab);
                    movieFragment.update(thumbnails);




                    mErrorView.setVisibility(View.GONE);
                }else{
                     mErrorView.setVisibility(View.VISIBLE);
                }



            }

            @Override
            public void onFailure(Call<ItemData> call, Throwable t) {
                mErrorView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
            }

            private ArrayList<String> extractThumbnails(ItemData data){
                ArrayList<String> thumbnails = new ArrayList<>();
                for(ItemData.Result result: data.results){
                    thumbnails.add(result.getPosterPath());
                }
                return thumbnails;
            }
        });

    }
}
