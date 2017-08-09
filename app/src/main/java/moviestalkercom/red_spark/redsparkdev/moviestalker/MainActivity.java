package moviestalkercom.red_spark.redsparkdev.moviestalker;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

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

    private ArrayList<MovieData> mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        //checking for save instance state
        if(savedInstanceState == null) {


            //adding the fragment
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new TopMoviesFragment(), FRAGMENT_TAG)
                    .commit();

            //Creating retrofit builder instance
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

            //Calling the action on the interface(we only have one and it's a @GET request)
            //We also specify the variables of the url
            Call<ArrayList<MovieData>> networkCall = networkInterface
                    .movieList(this.getResources().getString(R.string.api_key));

            final TopMoviesFragment fragment =  (TopMoviesFragment)mFragmentManager
                    .findFragmentByTag(FRAGMENT_TAG);
            //displaying progress bar
            fragment.setView(Constants.VIEW_TYPE.PROGRESS_BAR, true);
            //executing the request asynchronously
            networkCall.enqueue(new Callback<ArrayList<MovieData>>() {

                @Override
                public void onResponse(Call<ArrayList<MovieData>> call, Response<ArrayList<MovieData>> response) {

                    fragment.setView(Constants.VIEW_TYPE.MAIN_DISPLAY, true);
                    //Storing the data
                    mMovieData = response.body();

                    sendData();

                }

                @Override
                public void onFailure(Call<ArrayList<MovieData>> call, Throwable t) {
                    //showing a network error
                    fragment.setView(Constants.VIEW_TYPE.ERROR, true);

                }

                private void sendData(){
                    //only need to send the poster_paths for the thumbnails
                    List<String> thumbnails = new ArrayList<>();
                    for (MovieData.Result result: mMovieData.get(0).results){
                        thumbnails.add(result.getPosterPath());
                    }
                    //we send the string to the fragment
                    fragment.setThumbnails(thumbnails);

                }
            });

        }
    }


    // TODO: 08-Aug-17
    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
