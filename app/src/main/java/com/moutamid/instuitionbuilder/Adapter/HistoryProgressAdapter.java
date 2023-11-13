package com.moutamid.instuitionbuilder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.instuitionbuilder.Home.ScoreRankingActivity;
import com.moutamid.instuitionbuilder.Home.StatChartActivity;
import com.moutamid.instuitionbuilder.Model.UserDetails;
import com.moutamid.instuitionbuilder.R;

import java.util.List;

public class HistoryProgressAdapter extends RecyclerView.Adapter<HistoryProgressAdapter.GalleryPhotosViewHolder> {


    Context ctx;
    List<UserDetails> videoModelList;

    public HistoryProgressAdapter(Context ctx, List<UserDetails> videoModelList) {
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
//        int i = Integer.parseInt((videoModel.getProgress()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, StatChartActivity.class);
                intent.putExtra("key", videoModel.getKey());
                intent.putExtra("progress", rating);
//                intent.putExtra("score", i);
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

        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }
}
