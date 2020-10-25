package com.sports.footballcage.Adapters.NewsAdapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.sports.footballcage.Activities.TeamInformation;
import com.sports.footballcage.News;
import com.sports.footballcage.R;

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
        holder.txtNewsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, TeamInformation.class);
                    intent.putExtra("webURL", newsDetails.getLink());
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Can't open the url.", Toast.LENGTH_SHORT).show();
                }
            }
        });

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