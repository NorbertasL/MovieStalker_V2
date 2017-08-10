package moviestalkercom.red_spark.redsparkdev.moviestalker.fragments.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import moviestalkercom.red_spark.redsparkdev.moviestalker.fragments.ThumbnailFragment;

/**
 * Created by Red_Spark on 10-Aug-17.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter{
    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
               return new ThumbnailFragment();
            case 1:
                return new ThumbnailFragment();
            case 2:
                return new ThumbnailFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
