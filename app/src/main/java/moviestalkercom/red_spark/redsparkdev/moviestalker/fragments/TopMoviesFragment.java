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
public class TopMoviesFragment extends Fragment {

    //Used by butterknife to set views to null
    private Unbinder unbinder;
    @BindView(R.id.errorText)
    TextView mErrorView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void setView(String viewType, Boolean visible){
        switch (viewType){
            case Constants.VIEW_TYPE.ERROR:
                if(visible){
                    mErrorView.setVisibility(View.VISIBLE);
                    //just making sure the other views are gone
                    mProgressBar.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                }else{
                    mErrorView.setVisibility(View.GONE);
                }
                break;
            case Constants.VIEW_TYPE.PROGRESS_BAR:
                if(visible){
                    mProgressBar.setVisibility(View.VISIBLE);
                    //Making sure other views are not visable
                    mErrorView.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                }else{
                    mProgressBar.setVisibility(View.GONE);
                }
                break;
            case Constants.VIEW_TYPE.MAIN_DISPLAY:
                if(visible){
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mErrorView.setVisibility(View.GONE);
                    mProgressBar.setVisibility(View.GONE);
                }else{
                    mRecyclerView.setVisibility(View.GONE);
                }
                break;
        }

    }
    public void setThumbnails(List<String> posters){
        if(posters != null || !posters.isEmpty())
            thumbnails = posters;
        else
            setView(Constants.VIEW_TYPE.ERROR, true);
    }
}
