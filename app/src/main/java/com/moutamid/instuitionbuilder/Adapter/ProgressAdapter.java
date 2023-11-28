package com.moutamid.instuitionbuilder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.instuitionbuilder.Home.ScoreRankingActivity;
import com.moutamid.instuitionbuilder.Model.ProgressModel;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;

import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.GalleryPhotosViewHolder> {


    Context ctx;
    List<UserDetails> videoModelList;

    public ProgressAdapter(Context ctx, List<UserDetails> videoModelList) {
        this.ctx = ctx;
        this.videoModelList = videoModelList;
    }

    @NonNull
    @Override
    public GalleryPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.memory_history_layout, parent, false);
        return new GalleryPhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {
        UserDetails videoModel = videoModelList.get(position);
        float maxRating = 5.0f;  // Maximum rating of the RatingBar
        float progress = Float.parseFloat(videoModel.getProgress());
        float rating = (progress / 100.0f) * maxRating;
        holder.ratingBar.setRating(rating);
        holder.ratingBar.setIsIndicator(true);
        if (position % 2 == 0) {
            holder.circle.setImageResource(R.drawable.pie2);
        }
        if (position % 5 == 0) {
            holder.circle.setImageResource(R.drawable.pie3);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, ScoreRankingActivity.class);
                intent.putExtra("key", videoModel.getKey());
                intent.putExtra("progress", rating);
                Stash.put("rating", holder.ratingBar.getRating());
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        RatingBar ratingBar;
        ImageView circle;

        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingBar);

            circle = itemView.findViewById(R.id.circle);

        }
    }
}
