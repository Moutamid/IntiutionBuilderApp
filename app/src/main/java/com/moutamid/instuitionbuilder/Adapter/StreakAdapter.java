package com.moutamid.instuitionbuilder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moutamid.instuitionbuilder.R;
import com.moutamid.instuitionbuilder.config.StreakModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StreakAdapter extends RecyclerView.Adapter<StreakAdapter.GalleryPhotosViewHolder> {


    Context ctx;
    List<StreakModel> videoModelList;

    public StreakAdapter(Context ctx, List<StreakModel> videoModelList) {
        this.ctx = ctx;
        this.videoModelList = videoModelList;
    }

    @NonNull
    @Override
    public GalleryPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.memory_layout, parent, false);
        return new GalleryPhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {
        StreakModel videoModel = videoModelList.get(position);
        holder.text.setText(videoModel.text);
        holder.image.setImageResource(videoModel.image);
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView image;

        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.image);

        }
    }
    private List<StreakModel> removeDuplicates(List<StreakModel> inputList) {
        Set<StreakModel> uniqueModels = new HashSet<>(inputList);
        return new ArrayList<>(uniqueModels);
    }}
