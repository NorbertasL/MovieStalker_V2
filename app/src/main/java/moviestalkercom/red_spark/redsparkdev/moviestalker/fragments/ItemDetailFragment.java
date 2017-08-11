package moviestalkercom.red_spark.redsparkdev.moviestalker.fragments;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import moviestalkercom.red_spark.redsparkdev.moviestalker.R;
import moviestalkercom.red_spark.redsparkdev.moviestalker.data.Constants;
import moviestalkercom.red_spark.redsparkdev.moviestalker.data.ItemData;

public class ItemDetailFragment extends Fragment {

    private Unbinder unbinder;
    private ItemData.Result data;

    @BindView(R.id.iv_backDrop)ImageView mBackDrop;
    @BindView(R.id.iv_thumbnail)ImageView mThumbnail;
    @BindView(R.id.tv_Title)TextView mTitle;
    @BindView(R.id.tv_disc)TextView mDisc;

    public ItemDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_item_detail, container, false);

        unbinder = ButterKnife.bind(this, rootView);

        if(getArguments() != null){
            data = (ItemData.Result) getArguments().getSerializable(Constants.BUNDLE_KEY.DATA);
            mTitle.setText(data.getTitle());
            mDisc.setText(data.getOverview());
        }


        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
