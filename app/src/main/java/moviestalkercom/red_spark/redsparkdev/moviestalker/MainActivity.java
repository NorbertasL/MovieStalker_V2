package moviestalkercom.red_spark.redsparkdev.moviestalker;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import moviestalkercom.red_spark.redsparkdev.moviestalker.data.Constants;
import moviestalkercom.red_spark.redsparkdev.moviestalker.data.MovieData;
import moviestalkercom.red_spark.redsparkdev.moviestalker.fragments.TopMoviesFragment;
import moviestalkercom.red_spark.redsparkdev.moviestalker.network.GetMovieDataInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements TopMoviesFragment.OnFragmentInteractionListener{

    private static String FRAGMENT_TAG = "Top_Movie_Fragment";
    private FragmentManager mFragmentManager;

    private MovieData mMovieData;

    @BindView(R.id.errorText)TextView mErrorView;
    @BindView(R.id.progressBar)ProgressBar mProgressBar;

    TopMoviesFragment topMoviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        //checking for save instance state
        if(savedInstanceState == null) {

            mProgressBar.setVisibility(View.VISIBLE);

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
            Call<ArrayList<MovieData>> networkCall = networkInterface
                    .movieList(this.getResources().getString(R.string.api_key));

            final TopMoviesFragment fragment =  (TopMoviesFragment)mFragmentManager
                    .findFragmentByTag(FRAGMENT_TAG);
            //displaying progress bar
            //executing the request asynchronously
            networkCall.enqueue(new Callback<ArrayList<MovieData>>() {

                @Override
                public void onResponse(Call<ArrayList<MovieData>> call, Response<ArrayList<MovieData>> response) {

                    //Removing the progress bar since we have loaded the data
                    mProgressBar.setVisibility(View.GONE);

                    //get(0) since there should only be one list item in the response
                    mMovieData = response.body().get(0);
                    ArrayList<String> thumbnails = extractThumbnails(mMovieData);
                    //adding the fragment
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(Constants.BUNDLE_KEY.THUMBNAIL, thumbnails);

                    topMoviesFragment = new TopMoviesFragment();
                    topMoviesFragment.getArguments();

                    mFragmentManager.beginTransaction()
                            .add(R.id.fragment_container
                                    , topMoviesFragment
                                    , FRAGMENT_TAG)
                            .commit();

                }

                @Override
                public void onFailure(Call<ArrayList<MovieData>> call, Throwable t) {
                    mErrorView.setVisibility(View.VISIBLE);
                }

                private ArrayList<String> extractThumbnails(MovieData data){
                    ArrayList<String> thumbnails = new ArrayList<>();
                    for(MovieData.Result result: data.results){
                        thumbnails.add(result.getPosterPath());
                    }
                    return thumbnails;
                }
            });

        }
    }


    @Override
    public void onThumbnailClick(Uri uri) {
        // TODO: 09-Aug-17

    }

    @Override
    public void onError() {
        mFragmentManager.beginTransaction().remove(topMoviesFragment).commit();
        mErrorView.setVisibility(View.VISIBLE);

    }
}
