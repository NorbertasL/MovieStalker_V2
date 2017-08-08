package moviestalkercom.red_spark.redsparkdev.moviestalker;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import moviestalkercom.red_spark.redsparkdev.moviestalker.fragments.TopMoviesFragment;

public class MainActivity extends AppCompatActivity implements TopMoviesFragment.OnFragmentInteractionListener{

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();
        //checking for save instance state
        if(savedInstanceState == null) {


            //adding the fragment
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, new TopMoviesFragment())
                    .commit();
        }
    }


    // TODO: 08-Aug-17
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
