package moviestalkercom.red_spark.redsparkdev.moviestalker.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moviestalkercom.red_spark.redsparkdev.moviestalker.R;
import moviestalkercom.red_spark.redsparkdev.moviestalker.data.Constants;
import moviestalkercom.red_spark.redsparkdev.moviestalker.fragments.adapters.TopMoviesFragmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopMoviesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class TopMoviesFragment extends Fragment{


    //Used by butterknife to set views to null
    private Unbinder unbinder;
    @BindView(R.id.rv_movieGrid)
    RecyclerView mRecyclerView;

    private List<String> thumbnails;
    private RecyclerView.LayoutManager mLayoutManager;
    private int spanCount = 2;//setting the span count for the grid view
    private TopMoviesFragmentAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    public TopMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_top_movies, container, false);
        //butterknife set up
        unbinder = ButterKnife.bind(this, rootView);

        if(getArguments() != null){
            thumbnails = getArguments().getStringArrayList(Constants.BUNDLE_KEY.THUMBNAIL);
        }else {
            // TODO: 09-Aug-17 Error handeling
            mListener.onError();
        }

        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TopMoviesFragmentAdapter();

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onThumbnailClick(Uri uri);
        void onError();
    }

}
