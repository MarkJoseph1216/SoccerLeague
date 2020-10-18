package com.example.soccerleague.Adapters.NewsAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.soccerleague.News;
import com.example.soccerleague.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News.Channel.Item> itemsDetails;
    private LayoutInflater mInflater;
    public Context context;

    public NewsAdapter(Context context, List<News.Channel.Item> itemsDetails) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.itemsDetails = itemsDetails;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_news_items, parent, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        final News.Channel.Item newsDetails = itemsDetails.get(position);
        holder.txtTitle.setText(newsDetails.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemsDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;

        ViewHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}