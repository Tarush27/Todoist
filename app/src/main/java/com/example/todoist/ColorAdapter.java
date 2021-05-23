package com.example.todoist;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    List<Integer> color = Arrays.asList(Color.MAGENTA, Color.GREEN, Color.rgb(195, 155, 211),
            Color.rgb(233, 118, 81), Color.rgb(233, 221, 104),
            Color.rgb(133, 146, 158));
    private final ColorSelectedCallback colorSelectedCallback;

    public ColorAdapter(ColorSelectedCallback colorSelectedCallback) {
        this.colorSelectedCallback = colorSelectedCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.v1.setBackgroundResource(R.drawable.list_drawable);
        holder.v1.setOnClickListener(v -> colorSelectedCallback.onColorSelected(color.get(position)));
        GradientDrawable gd = (GradientDrawable) holder.v1.getBackground();
        gd.setColor(color.get(position));
    }

    @Override
    public int getItemCount() {
        return color.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View v1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            v1 = itemView.findViewById(R.id.v1);
        }
    }
}
