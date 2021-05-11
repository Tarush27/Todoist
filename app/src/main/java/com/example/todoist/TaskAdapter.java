package com.example.todoist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.google.android.material.card.MaterialCardView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private List<TaskModel> taskModelList;

    public TaskAdapter(List<TaskModel> taskModelList) {
        this.taskModelList = taskModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_model,parent,false);
        return new ViewHolder(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel taskModel = taskModelList.get(position);
        holder.title.setText(taskModel.getTitle());
        holder.note.setText(taskModel.getNote());
    }

    public void updateAdapter(){
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        MaterialCardView mCardView;
        TextView title;
        TextView note;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.mCardView);
            title = itemView.findViewById(R.id.title);
            note = itemView.findViewById(R.id.note);
            itemView.setOnLongClickListener(v -> {
                Toast.makeText(itemView.getContext(), "item clicks", Toast.LENGTH_SHORT).show();
                return true;
            });
        }

//        @Override
//        public boolean onLongClick(View v) {
//            Toast.makeText(v.getContext(), "item clicks", Toast.LENGTH_SHORT).show();
//                return true;
//        }
    }
}
