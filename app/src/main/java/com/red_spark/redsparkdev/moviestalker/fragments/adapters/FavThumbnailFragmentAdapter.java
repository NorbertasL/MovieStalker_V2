package com.red_spark.redsparkdev.moviestalker.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.red_spark.redsparkdev.moviestalker.R;
import com.red_spark.redsparkdev.moviestalker.data.Constants;
import com.red_spark.redsparkdev.moviestalker.data.ImageStorage;
import com.red_spark.redsparkdev.moviestalker.data.MovieData;
import com.red_spark.redsparkdev.moviestalker.network.GlideApp;
import java.io.File;
import java.util.List;

/**
 * Created by Red_Spark on 16-Aug-17.
 */

public class FavThumbnailFragmentAdapter extends RecyclerView.Adapter<FavThumbnailFragmentAdapter.MyViewHolder>{
    private static final String TAG = FavThumbnailFragmentAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private List<MovieData> mMovieData;
    private OnClickListener mOnClickListener;


    public FavThumbnailFragmentAdapter(Context context, List<MovieData> movieData, OnClickListener onClickListener) {
        inflater = LayoutInflater.from(context);
        this.mMovieData = movieData;

        if (onClickListener instanceof OnClickListener) {
            mOnClickListener = onClickListener;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnClickListener");
        }

    }

    public interface OnClickListener {
        void onItemClick(MovieData movieData);
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        File file = ImageStorage.getImage(ImageStorage.ImageType.THUMBNAIL,
                Constants.DATA_TYPE.MOVIES.getTag()+mMovieData.get(position).id);
        GlideApp.with(holder.itemView.getContext()).load(file)
                .placeholder(R.drawable.placeholder_thumbnail).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        if (mMovieData != null)
            return mMovieData.size();
        return 0;

    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnail;


        public MyViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onItemClick(mMovieData.get(getAdapterPosition()));
        }
    }

    public void setData(List<MovieData> movieData) {
        this.mMovieData = movieData;
        notifyDataSetChanged();
    }
}




