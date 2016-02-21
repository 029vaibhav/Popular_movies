package com.udacity.popularmovies.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.entities.MovieOrgResults;
import com.udacity.popularmovies.fragments.DisplayFragment;
import com.udacity.popularmovies.utils.CropSquareTransformation;

import java.util.List;

/**
 * Created by vaibhav on 20/2/16.
 */
public class MoviesDisplayAdapter extends RecyclerView.Adapter<MoviesDisplayAdapter.ViewHolder> {

    List<MovieOrgResults> movieOrgResults;
    Fragment context;
    CropSquareTransformation cropSquareTransformation;

    public MoviesDisplayAdapter(Fragment context, List<MovieOrgResults> movieOrgResults) {
        this.context = context;
        this.movieOrgResults = movieOrgResults;
        cropSquareTransformation = new CropSquareTransformation((AppCompatActivity) context.getActivity());
    }

    @Override
    public MoviesDisplayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesDisplayAdapter.ViewHolder holder, int position) {


        MovieOrgResults movieOrgResults = this.movieOrgResults.get(position);
        String poster_path = movieOrgResults.getPoster_path();
        Picasso.with(context.getActivity())
                .load(context.getString(R.string.image_url) + poster_path)
                .transform(cropSquareTransformation)
                .into(holder.poster, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.poster.setBackgroundResource(R.mipmap.ic_launcher);

                    }
                });
    }

    @Override
    public int getItemCount() {
        return movieOrgResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView poster;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster_image);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(v -> intentToDisplayFragment());
        }

        private void intentToDisplayFragment() {

            Fragment displayFragment = DisplayFragment.newInstance(movieOrgResults.get(getAdapterPosition()));
            context.getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, displayFragment, "displayFragment").addToBackStack(null).commit();


        }
    }
}
