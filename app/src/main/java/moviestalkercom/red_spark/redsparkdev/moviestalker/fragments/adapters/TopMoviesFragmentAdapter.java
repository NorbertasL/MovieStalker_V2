package moviestalkercom.red_spark.redsparkdev.moviestalker.fragments.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;
import moviestalkercom.red_spark.redsparkdev.moviestalker.R;

/**
 * Created by Red_Spark on 08-Aug-17.
 */

public class TopMoviesFragmentAdapter extends RecyclerView.Adapter<TopMoviesFragmentAdapter.MyViewHolder>{
    private LayoutInflater inflater;
    private List<String> mThumbnails = Collections.emptyList();
    private OnClickListener mOnClickListener;

    public TopMoviesFragmentAdapter(Context context, List<String> thumbnails, OnClickListener onClickListener){
        inflater = LayoutInflater.from(context);
        mThumbnails = thumbnails;

        if (onClickListener instanceof OnClickListener) {
            mOnClickListener = onClickListener;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnClickListener");
        }

    }
    public interface OnClickListener{void onItemClick(View view, int position);}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(mThumbnails.get(position));

    }

    @Override
    public int getItemCount() {
        return mThumbnails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_Test);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onItemClick(v, getAdapterPosition());
        }
    }

}
