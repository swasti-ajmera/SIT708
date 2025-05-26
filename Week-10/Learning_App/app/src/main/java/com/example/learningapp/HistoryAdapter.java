package com.example.learningapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    List<HistoryItem> historyList;

    public HistoryAdapter(List<HistoryItem> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem item = historyList.get(position);
        holder.questionText.setText(item.question);

        String[] optionLabels = {"A", "B", "C", "D"};

        for (int i = 0; i < item.options.size(); i++) {
            String label = optionLabels[i];
            String optionText = item.options.get(i);
            TextView optionView = holder.optionViews[i];

            optionView.setText(label + ". " + optionText);
            optionView.setTextColor(Color.BLACK);

            // Highlight selected and correct answers
            if (label.equals(item.selected)) {
                if (item.selected.equals(item.correct)) {
                    optionView.setTextColor(Color.GREEN);
                } else {
                    optionView.setTextColor(Color.RED);
                }
            } else if (label.equals(item.correct)) {
                optionView.setTextColor(Color.GREEN);
            }
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView questionText;
        TextView[] optionViews = new TextView[4];

        public HistoryViewHolder(View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            optionViews[0] = itemView.findViewById(R.id.optionA);
            optionViews[1] = itemView.findViewById(R.id.optionB);
            optionViews[2] = itemView.findViewById(R.id.optionC);
            optionViews[3] = itemView.findViewById(R.id.optionD);
        }
    }
}
