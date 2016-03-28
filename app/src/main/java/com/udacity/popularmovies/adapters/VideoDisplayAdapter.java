package com.udacity.popularmovies.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.popularmovies.R;
import com.udacity.popularmovies.entities.VideoResults;
import com.udacity.popularmovies.utils.CropSquareTransformation;

import java.util.List;

/**
 * Created by vaibhav on 20/2/16.
 */
public class VideoDisplayAdapter extends RecyclerView.Adapter<VideoDisplayAdapter.ViewHolder> {

    List<VideoResults> videoResultses;
    Fragment context;
    CropSquareTransformation cropSquareTransformation;

    public VideoDisplayAdapter(Fragment context, List<VideoResults> movieOrgResults) {
        this.context = context;
        this.videoResultses = movieOrgResults;
        cropSquareTransformation = new CropSquareTransformation((AppCompatActivity) context.getActivity());
    }

    @Override
    public VideoDisplayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_video_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoDisplayAdapter.ViewHolder holder, int position) {


        VideoResults videoResults = this.videoResultses.get(position);
        Picasso.with(context.getActivity())
                .load(context.getActivity().getString(R.string.you_tube_image_url) + videoResults.getKey() + "/default.jpg")
                .into(holder.poster, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        holder.poster.setBackgroundResource(R.mipmap.ic_launcher);

                    }
                });
        holder.textView.setText(videoResults.getName());
    }

    @Override
    public int getItemCount() {
        return videoResultses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ImageView poster;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.video_thumbnail);
            textView = (TextView) itemView.findViewById(R.id.trailer_title);

            itemView.setOnClickListener(v -> startVideo(videoResultses.get(getAdapterPosition()).getKey()));
        }

        private void startVideo(String videoID) {
            // default youtube app
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoID));
            context.getActivity().startActivity(i);
        }
    }
}
