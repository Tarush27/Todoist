package com.example.todoist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private final List<TaskModel> taskModelList;
    private final ToolbarOverlapCallBack toolbarOverlapCallBack;
    private DeleteNoteInterface deleteNoteInterface;

    public TaskAdapter(List<TaskModel> taskModelList, ToolbarOverlapCallBack toolbarOverlapCallBack) {
        this.taskModelList = taskModelList;
        this.toolbarOverlapCallBack = toolbarOverlapCallBack;
//        this.deleteNoteInterface = deleteNoteInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_model, parent, false);
        return new ViewHolder(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskModel taskModel = taskModelList.get(position);
        holder.title.setText(taskModel.getTitle());
        holder.note.setText(taskModel.getNote());
        holder.mCardView.setCardBackgroundColor(taskModel.getColor());
        holder.mCardView.setStrokeColor(taskModel.getBorderColor());
        holder.mCardView.setStrokeWidth(4);
        holder.mCardView.setOnLongClickListener(v -> {
            toolbarOverlapCallBack.onNoteLongClick(position);
            return true;
        });
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarOverlapCallBack.onNoteSingleClick(position);
//                deleteNoteInterface.DeleteNote(position);
            }
        });

    }

    public void updateAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView mCardView;
        TextView title;
        TextView note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCardView = itemView.findViewById(R.id.mCardView);
            title = itemView.findViewById(R.id.title);
            note = itemView.findViewById(R.id.note);

        }
    }


}
