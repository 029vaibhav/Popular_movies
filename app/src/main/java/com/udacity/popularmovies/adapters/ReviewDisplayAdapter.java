package com.udacity.popularmovies.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.popularmovies.R;
import com.udacity.popularmovies.entities.ReviewResult;
import com.udacity.popularmovies.utils.CropSquareTransformation;

import java.util.List;

/**
 * Created by vaibhav on 20/2/16.
 */
public class ReviewDisplayAdapter extends RecyclerView.Adapter<ReviewDisplayAdapter.ViewHolder> {

    List<ReviewResult> reviewResults;
    Fragment context;
    CropSquareTransformation cropSquareTransformation;

    public ReviewDisplayAdapter(Fragment context, List<ReviewResult> reviewResults) {
        this.context = context;
        this.reviewResults = reviewResults;
        cropSquareTransformation = new CropSquareTransformation((AppCompatActivity) context.getActivity());
    }

    @Override
    public ReviewDisplayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_review_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewDisplayAdapter.ViewHolder holder, int position) {

        ReviewResult reviewResult = this.reviewResults.get(position);
        holder.author.setText(reviewResult.getAuthor());
        holder.content.setText(reviewResult.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView author;
        public TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.author);
            content = (TextView) itemView.findViewById(R.id.content);
        }


    }
}
