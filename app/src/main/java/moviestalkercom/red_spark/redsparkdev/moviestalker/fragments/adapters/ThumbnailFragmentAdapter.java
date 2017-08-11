package moviestalkercom.red_spark.redsparkdev.moviestalker.fragments.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Collections;
import java.util.List;

import moviestalkercom.red_spark.redsparkdev.moviestalker.R;
import moviestalkercom.red_spark.redsparkdev.moviestalker.data.Constants;
import moviestalkercom.red_spark.redsparkdev.moviestalker.network.GlideApp;


/**
 * Created by Red_Spark on 08-Aug-17.
 */

public class ThumbnailFragmentAdapter extends RecyclerView.Adapter<ThumbnailFragmentAdapter.MyViewHolder>{

    private static final String TAG = ThumbnailFragmentAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private List<String> mThumbnails = Collections.emptyList();
    private OnClickListener mOnClickListener;


    public ThumbnailFragmentAdapter(Context context, List<String> thumbnails, OnClickListener onClickListener){
        inflater = LayoutInflater.from(context);
        mThumbnails = thumbnails;

        if (onClickListener instanceof OnClickListener) {
            mOnClickListener = onClickListener;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnClickListener");
        }

    }
    public interface OnClickListener{
        void onItemClick(int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //LogHelp.print(TAG, "onBindViewHolder for pos:" + position);
        String imageUrl =
              Constants.IMAGE_BASE_URL + Constants.POSTER_SIZE.W95.getUrlTag() + mThumbnails.get(position);
        GlideApp.with(holder.itemView.getContext()).load(imageUrl).placeholder(R.drawable.placeholder_thumbnail).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        if(mThumbnails != null)
            return mThumbnails.size();
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
            mOnClickListener.onItemClick(getLayoutPosition());
        }
    }
    public void setData(List<String> thumbnails){
        this.mThumbnails = thumbnails;
        notifyDataSetChanged();
    }

}
