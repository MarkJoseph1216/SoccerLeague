package com.example.soccerleague.Adapters.NewsAdapter;

import android.content.Context;
import android.text.Html;
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
        this.context = context;
        this.itemsDetails = itemsDetails;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.recycleview_news_items, parent, false);
        return new NewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        final News.Channel.Item newsDetails = itemsDetails.get(position);
        holder.txtTitle.setText("News Title: "+ newsDetails.getTitle());
        holder.txtPubDate.setText("Publish Date: " + newsDetails.getPubDate());
        holder.txtDescription.setText(Html.fromHtml("Description: " + newsDetails.getDescription()));
        holder.txtNewsLink.setText(newsDetails.getLink());

        if (position == itemsDetails.size() -1) {
            holder.viewSeparator.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return itemsDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtPubDate, txtDescription, txtNewsLink;
        View viewSeparator;

        ViewHolder(View itemView) {
            super(itemView);

            txtNewsLink = itemView.findViewById(R.id.txtNewsLink);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPubDate = itemView.findViewById(R.id.txtPubDate);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            viewSeparator = itemView.findViewById(R.id.viewSeparator);
        }
    }
}