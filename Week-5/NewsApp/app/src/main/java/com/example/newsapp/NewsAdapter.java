package com.example.newsapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<NewsItem> newsList;

    public NewsAdapter(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem item = newsList.get(position);
        holder.newsTitle.setText(item.getTitle());
        holder.newsDesc.setText(item.getDescription());
        holder.newsImage.setImageResource(item.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NewsDetailActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("desc", item.getDescription());
            intent.putExtra("image", item.getImageResId());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle, newsDesc;
        ImageView newsImage;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDesc = itemView.findViewById(R.id.newsDesc);
            newsImage = itemView.findViewById(R.id.newsImage);
        }
    }
}
