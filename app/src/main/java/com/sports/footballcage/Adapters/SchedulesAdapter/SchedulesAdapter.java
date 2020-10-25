package com.sports.footballcage.Adapters.SchedulesAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.sports.footballcage.Activities.TeamInformation;
import com.sports.footballcage.Model.Schedules.Schedules;
import com.sports.footballcage.R;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class SchedulesAdapter extends RecyclerView.Adapter<SchedulesAdapter.ViewHolder> {

    private List<Schedules.Event> itemsDetails;
    private LayoutInflater mInflater;
    public Context context;

    public SchedulesAdapter(Context context, List<Schedules.Event> itemsDetails) {
        this.context = context;
        this.itemsDetails = itemsDetails;
    }

    @Override
    public SchedulesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.recyclerview_schedules_item, parent, false);
        return new SchedulesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SchedulesAdapter.ViewHolder holder, int position) {
        final Schedules.Event event = itemsDetails.get(position);

        holder.txtEventName.setText(event.getStrFilename());
        holder.txtSeason.setText("Season: " + event.getStrSeason());
        holder.txtHomeTeam.setText("Home Team: " + event.getStrHomeTeam());
        holder.txtAwayTeam.setText("Away Team: " + event.getStrAwayTeam());
        holder.txtDescription.setText("Description: \n\n" + event.getStrDescriptionEN());
        holder.txtStatus.setText("Status: " + event.getStrStatus());
        holder.txtResponseCountry.setText("ResponseCountry: " + event.getStrResponseCountry());
        holder.txtVenueStadium.setText("Stadium Venue: " + event.getStrVenue());
        holder.txtTwitterFeeds.setText("Twitter: " + event.getStrTweet1());
        holder.loadingImage.setVisibility(View.VISIBLE);

        Picasso.with(context).load(event.getStrThumb()).into(holder.imgSchedules, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.loadingImage.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError() {

                    }
                });

        if (position == itemsDetails.size() -1) {
            holder.viewSeparator.setVisibility(View.GONE);
        }

        holder.txtTwitterFeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, TeamInformation.class);
                    intent.putExtra("webURL", event.getStrTweet1());
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Can't open the url.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSchedules;
        TextView txtEventName, txtSeason, txtHomeTeam, txtAwayTeam, txtDescription, txtStatus, txtResponseCountry, txtVenueStadium, txtTwitterFeeds;
        View viewSeparator;
        AVLoadingIndicatorView loadingImage;

        ViewHolder(View itemView) {
            super(itemView);

            imgSchedules = itemView.findViewById(R.id.imgSchedules);
            txtEventName = itemView.findViewById(R.id.txtEventName);
            txtSeason = itemView.findViewById(R.id.txtSeason);
            txtHomeTeam = itemView.findViewById(R.id.txtHomeTeam);
            txtAwayTeam = itemView.findViewById(R.id.txtAwayTeam);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtResponseCountry = itemView.findViewById(R.id.txtResponseCountry);
            txtVenueStadium = itemView.findViewById(R.id.txtVenueStadium);
            txtTwitterFeeds = itemView.findViewById(R.id.txtTwitterFeeds);
            viewSeparator = itemView.findViewById(R.id.viewSeparator);
            loadingImage =itemView.findViewById(R.id.loadingImage);
        }
    }
}
