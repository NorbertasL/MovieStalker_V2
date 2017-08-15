package com.red_spark.redsparkdev.moviestalker.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.red_spark.redsparkdev.moviestalker.R;
import com.red_spark.redsparkdev.moviestalker.data.Constants;
import com.red_spark.redsparkdev.moviestalker.data.ItemData;
import com.red_spark.redsparkdev.moviestalker.data.database.DbHelper;
import com.red_spark.redsparkdev.moviestalker.network.GlideApp;

public class ItemDetailFragment extends Fragment {

    private Unbinder unbinder;
    private ItemData.Result data;

    DbHelper dbHelper;
    int id;

    @BindView(R.id.iv_backDrop)ImageView mBackDrop;
    @BindView(R.id.tv_Title)TextView mTitle;
    @BindView(R.id.tv_disc)TextView mDisc;
    @BindView(R.id.ratingText)TextView mRatingText;
    @BindView(R.id.releaseDate)TextView mReleaseDate;
    @BindView(R.id.buttonAddToFav)FloatingActionButton mBtAddToFav;

    public ItemDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_item_detail, container, false);
        dbHelper = new DbHelper(getContext());

        unbinder = ButterKnife.bind(this, rootView);

        if(getArguments() != null){
            data = (ItemData.Result) getArguments().getSerializable(Constants.BUNDLE_KEY.DATA);
            mTitle.setText(data.getTitle());
            mDisc.setText(data.getOverview());
        }

        String backdropUrl = Constants.IMAGE_BASE_URL+Constants.BACKDROP_SIZE.W300.getUrlTag()+data.getBackdrop_path();
        GlideApp.with(getContext()).load(backdropUrl).into(mBackDrop);

        mRatingText.setText(data.getVote_average() + "/10");
        mReleaseDate.setText("Release Date:\n"+data.getRelease_date());
        id = Integer.valueOf(data.getID());
        if(dbHelper.checkIfInDatabase(data.getDataType(), id)){
            mBtAddToFav.setImageResource(android.R.drawable.star_big_on);
        }



        mBtAddToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if items is fov it will be removed from fov else it will be added
                if(dbHelper.checkIfInDatabase(data.getDataType(), id))
                    removeItemFromDatabase();
                else
                    addItemToDatabase();



            }
        });
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private void addItemToDatabase(){
        if(dbHelper.addRow(data.getDataType(), data)){
            Toast.makeText(getContext(), "Item Added", Toast.LENGTH_SHORT).show();
            mBtAddToFav.setImageResource(android.R.drawable.star_big_on);
        }else{
            Toast.makeText(getContext(), "Failed To Add Item", Toast.LENGTH_SHORT).show();
        }
    }
    private void removeItemFromDatabase(){
        if(dbHelper.removeRow(data.getDataType(), id)){
            Toast.makeText(getContext(), "Item Removed", Toast.LENGTH_SHORT).show();
            mBtAddToFav.setImageResource(android.R.drawable.star_big_off);
        }else{
            Toast.makeText(getContext(), "Failed To Remove Item", Toast.LENGTH_SHORT).show();
        }

    }


}
