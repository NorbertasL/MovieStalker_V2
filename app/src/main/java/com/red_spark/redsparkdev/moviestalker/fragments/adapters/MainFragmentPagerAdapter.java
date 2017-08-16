package com.red_spark.redsparkdev.moviestalker.fragments.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.red_spark.redsparkdev.moviestalker.fragments.FavThumbnailFragment;
import com.red_spark.redsparkdev.moviestalker.fragments.ThumbnailFragment;

/**
 * Created by Red_Spark on 10-Aug-17.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter{
    private static final String TAG = MainFragmentPagerAdapter.class.getSimpleName();
    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private ThumbnailFragment frag1;
    private ThumbnailFragment frag2;
    private FavThumbnailFragment frag3;



    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                frag1 = new ThumbnailFragment();
               return frag1;
            case 1:
                frag2 =new ThumbnailFragment();
                return frag2;
            case 2:
                frag3 = new FavThumbnailFragment();
                return frag3;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        //LogHelp.print(TAG, "getCount");
        return 3;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Movies";
            case 1:
                return "TV Series";
            case 2:
                return "Favorites";
            default:
                return null;
        }



        // super.getPageTitle(position);
    }
    public Fragment getFragment(){
        return frag3;
    }
}
