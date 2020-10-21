package com.example.soccerleague.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.soccerleague.Activities.TeamInformation;
import com.example.soccerleague.KKViewPager;
import com.example.soccerleague.Model.Teams;
import com.example.soccerleague.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardHomeAdapter extends RecyclerView.Adapter<DashboardHomeAdapter.ViewHolder> {

    private ArrayList<Teams> itemsDefinition;
    private LayoutInflater mInflater;
    public Context context;
    public HomeCategoriesAdapter homeCategoriesAdapter;

    public DashboardHomeAdapter(Context context, ArrayList<Teams> itemsDefinition) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.itemsDefinition = itemsDefinition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_home_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            holder.teamNames.setText("FOOTBALL TEAMS");
            holder.txtSeeMoreItems.setVisibility(View.GONE);
            holder.cardViewHolder.setVisibility(View.GONE);
            holder.teamLogo.setVisibility(View.GONE);
            holder.homeViewPagerCategories.setVisibility(View.GONE);

            GridLayoutManager  layoutManager = new GridLayoutManager (context,  2, GridLayoutManager.HORIZONTAL, false);
            holder.recycleview_home_items.setLayoutManager(layoutManager);

            homeCategoriesAdapter = new HomeCategoriesAdapter(context, itemsDefinition);
            holder.recycleview_home_items.setAdapter(homeCategoriesAdapter);
        } else {
            final Teams teamNames = itemsDefinition.get(position -1);

            Glide.with(context).load(teamNames.getStrTeamLogo()).into(holder.teamLogoName);
            Glide.with(context).load(teamNames.getStrTeamBadge()).into(holder.teamLogo);
            Glide.with(context)
                    .load(teamNames.getStrTeamFanart3())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            holder.loadingBanner.setVisibility(View.VISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            holder.loadingBanner.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(holder.image_item);

            holder.teamNames.setText(teamNames.getStrTeam());
            holder.txtTeamName.setText(teamNames.getStrTeam());

            SpannableString spanString = new SpannableString(teamNames.getStrWebsite());
            spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
            holder.txtWebSite.setText(spanString);

            if (teamNames.getStrAlternate().equals("")) {
                holder.txtAlternateName.setText("No Alternate Name");
            } else {
                holder.txtAlternateName.setText(teamNames.getStrAlternate());
            }

            holder.imgFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWebURL(teamNames.getStrFacebook());
                }
            });

            holder.imgInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWebURL(teamNames.getStrInstagram());
                }
            });

            holder.imgYoutube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWebURL(teamNames.getStrYoutube());
                }
            });

            holder.txtSeeMoreItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TeamInformation.class);
                    intent.putExtra("teamID", teamNames.getIdTeam());
                    context.startActivity(intent);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TeamInformation.class);
                    intent.putExtra("teamID", teamNames.getIdTeam());
                    context.startActivity(intent);
                }
            });

            holder.txtWebSite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openWebURL(teamNames.getStrWebsite());
                }
            });
        }

        if (position == itemsDefinition.size() -1) {
            holder.lineSeperator.setVisibility(View.INVISIBLE);
        }
    }

    private void openWebURL(String url){
        try {
            Intent intent = new Intent(context, TeamInformation.class);
            intent.putExtra("webURL", url);
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "Can't open the url.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return itemsDefinition.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView teamLogo;
        ViewPager homeViewPagerCategories;
        TextView teamNames, txtSeeMoreItems, txtWebSite;
        View lineSeperator;
        RecyclerView recycleview_home_items;
        ImageView image_item, imgFacebook, imgInstagram, imgYoutube;
        TextView txtTeamName, txtAlternateName;
        RelativeLayout cardViewHolder;
        ImageView teamLogoName;
        AVLoadingIndicatorView loadingBanner;

        ViewHolder(View itemView) {
            super(itemView);

            imgFacebook = itemView.findViewById(R.id.imgFacebook);
            imgInstagram = itemView.findViewById(R.id.imgInstagram);
            imgYoutube = itemView.findViewById(R.id.imgYoutube);

            txtWebSite = itemView.findViewById(R.id.txtWebSite);
            teamLogoName = itemView.findViewById(R.id.teamLogoName);
            teamLogo = itemView.findViewById(R.id.teamLogo);
            txtSeeMoreItems = itemView.findViewById(R.id.txtSeeMoreItems);
            teamNames = itemView.findViewById(R.id.teamNames);
            lineSeperator = itemView.findViewById(R.id.lineSeperator);
            homeViewPagerCategories = itemView.findViewById(R.id.homeViewPagerCategories);
            recycleview_home_items = itemView.findViewById(R.id.recycleview_home_items);

            cardViewHolder = itemView.findViewById(R.id.cardViewHolder);
            image_item = itemView.findViewById(R.id.image_item);
            txtTeamName = itemView.findViewById(R.id.txtTeamName);
            txtAlternateName = itemView.findViewById(R.id.txtAlternateName);

            loadingBanner = itemView.findViewById(R.id.loadingBanner);
        }
    }

    public void updateList(ArrayList<Teams> list){
        itemsDefinition = list;
        notifyDataSetChanged();
    }
}
