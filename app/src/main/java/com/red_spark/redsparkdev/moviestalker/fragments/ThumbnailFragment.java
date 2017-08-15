package com.red_spark.redsparkdev.moviestalker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.red_spark.redsparkdev.moviestalker.LogHelp;
import com.red_spark.redsparkdev.moviestalker.R;
import com.red_spark.redsparkdev.moviestalker.data.Constants;
import com.red_spark.redsparkdev.moviestalker.fragments.adapters.ThumbnailFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ThumbnailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ThumbnailFragment extends Fragment implements ThumbnailFragmentAdapter.OnClickListener{

    private final String TAG = ThumbnailFragment.class.getSimpleName();

    //Used by butterknife to set views to null
    private Unbinder unbinder;
    @BindView(R.id.rv_movieGrid)
    RecyclerView mRecyclerView;

    private List<String> thumbnails;
    private Constants.DATA_TYPE fragmentType;
    private GridLayoutManager mLayoutManager;


    private int spanCount = 2;//setting the span count for the grid view

    private ThumbnailFragmentAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    public ThumbnailFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_movies, container, false);
        //butterknife set up
        unbinder = ButterKnife.bind(this, rootView);

        LogHelp.print(TAG, rootView.getWidth()+"");

        /**
        ImageView imageView = new ImageView(this.getContext());
        imageView.setAdjustViewBounds(true);
        imageView.setMaxWidth();
         **/

        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ThumbnailFragmentAdapter(getContext(), thumbnails, this);
        mRecyclerView.setAdapter(mAdapter);

        if(savedInstanceState != null){
            update(savedInstanceState.getStringArrayList(Constants.BUNDLE_KEY.THUMBNAIL)
                    ,(Constants.DATA_TYPE) savedInstanceState.getSerializable(Constants.BUNDLE_KEY.POSITION) );
            mLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(Constants.BUNDLE_KEY.LAYOUT));
        }





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
    public void onItemClick(int position) {
        mListener.onThumbnailClick(position, fragmentType);
    }




    public interface OnFragmentInteractionListener {
        void onThumbnailClick(int position, Constants.DATA_TYPE fragmentType);
        void onError();
    }
    public void update( List<String> thumbnails, Constants.DATA_TYPE tabType){
        this.thumbnails = thumbnails;
        this.fragmentType = tabType;
        mAdapter.setData(thumbnails);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(thumbnails != null) {
            outState.putStringArrayList(Constants.BUNDLE_KEY.THUMBNAIL, (ArrayList) thumbnails);
            outState.putParcelable(Constants.BUNDLE_KEY.LAYOUT, mLayoutManager.onSaveInstanceState());
            outState.putSerializable(Constants.BUNDLE_KEY.POSITION, fragmentType);
        }
    }

}
