package com.example.retrofitwithimdb.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitwithimdb.R;
import com.example.retrofitwithimdb.models.MovieResults;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerAdapter extends  RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>{
    private List<MovieResults> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private AdapterView.OnItemClickListener clickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        clickListener = listener;
    }

    // data is passed into the constructor
    public MovieRecyclerAdapter(Context context, List<MovieResults> data, ItemClickListener list) {
        this.mInflater = LayoutInflater.from(context);
//        this.mData = data;
        this.mClickListener = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_recyceler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String title = mData.get(position).getTitle();
        holder.myTextView.setText(title);

    }

    @Override
    public int getItemCount() {
        if(mData == null)
            return 0;
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.resultsRow);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    String getItem(int id) {
        return mData.get(id).toString();
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    public void addItems(ArrayList<MovieResults> names){
        mData = names;
        notifyDataSetChanged();
    }

}
