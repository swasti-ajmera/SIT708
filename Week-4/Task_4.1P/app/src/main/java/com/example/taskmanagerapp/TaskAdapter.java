package com.example.taskmanagerapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private AppDatabase db;

    public TaskAdapter(List<Task> tasks, AppDatabase db) {
        this.tasks = tasks;
        this.db = db;
    }

    public void setTasks(List<Task> newTasks) {
        tasks = newTasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task t = tasks.get(position);
        holder.txtTitle.setText(t.title);
        holder.txtDueDate.setText("Due: " + t.dueDate);

        // Set up the click listener for the task item (to edit task)
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), AddEditTaskActivity.class);
            i.putExtra("task_id", t.id);
            v.getContext().startActivity(i);
        });

        // Set up the delete button listener
        holder.btnDelete.setOnClickListener(v -> {
            // Delete task from database
            db.taskDao().delete(t);

            // Remove task from list and notify adapter
            tasks.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(v.getContext(), "Task deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDueDate;
        Button btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDueDate = itemView.findViewById(R.id.txtDueDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);  // Bind the delete button
        }
    }
}
