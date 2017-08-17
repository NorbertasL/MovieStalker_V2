package com.red_spark.redsparkdev.moviestalker.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.red_spark.redsparkdev.moviestalker.R;
import com.red_spark.redsparkdev.moviestalker.data.Constants;
import com.red_spark.redsparkdev.moviestalker.data.MovieData;
import com.red_spark.redsparkdev.moviestalker.fragments.adapters.FavThumbnailFragmentAdapter;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Red_Spark on 16-Aug-17.
 *
 */

public class FavThumbnailFragment extends Fragment implements FavThumbnailFragmentAdapter.OnClickListener {
    private final String TAG = FavThumbnailFragment.class.getSimpleName();

    //Used by butterknife to set views to null
    private Unbinder unbinder;
    @BindView(R.id.rv_movieGrid)
    RecyclerView mRecyclerView;

    private List<MovieData> mMovieData;

    private FavThumbnailFragmentAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    public FavThumbnailFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top_movies, container, false);
        //butterknife set up
        unbinder = ButterKnife.bind(this, rootView);

        int spanCount = 2;
        GridLayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavThumbnailFragmentAdapter(getContext(), mMovieData, this);
        mRecyclerView.setAdapter(mAdapter);
        mListener.onFragmentCreated(this);



        // TODO: 16-Aug-17 write restore state code

        return rootView;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onItemClick(MovieData movieData) {



    }
    public interface OnFragmentInteractionListener {
        void onThumbnailClick(int position, Constants.DATA_TYPE fragmentType, Drawable thumbnailImage);
        void onFragmentCreated(FavThumbnailFragment fragment);
        void onError();
    }

    public void update( List<MovieData> movieData){
        this.mMovieData = movieData;
        mAdapter.setData(movieData);
    }

}
