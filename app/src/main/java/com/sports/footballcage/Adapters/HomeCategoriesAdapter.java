package com.sports.footballcage.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sports.footballcage.Activities.TeamInformation;
import com.sports.footballcage.Model.Teams;
import com.sports.footballcage.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    public Context context;

    ArrayList<Teams> teamsArrayList;

    public HomeCategoriesAdapter(Context context, ArrayList<Teams> teamsArrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.teamsArrayList = teamsArrayList;
    }

    @Override
    public HomeCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_categories_item, parent, false);
        return new HomeCategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeCategoriesAdapter.ViewHolder holder, int position) {
        final Teams teams = teamsArrayList.get(position);
        Glide.with(context).load(teams.getStrTeamBadge()).into(holder.imgCategories);
        holder.txtNameCategories.setText(teamsArrayList.get(position).getStrTeam());

        //Getting the last position of the item and adding a margin right of it.
        if (position == teamsArrayList.size() -1) {
            ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) holder.cardViewHolder.getLayoutParams();
            cardViewMarginParams.setMargins(40, 20, 40, 10);
            holder.cardViewHolder.requestLayout();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeamInformation.class);
                intent.putExtra("teamID", teams.getIdTeam());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teamsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNameCategories;
        CardView cardViewHolder;
        CircleImageView imgCategories;

        ViewHolder(View itemView) {
            super(itemView);

            txtNameCategories = itemView.findViewById(R.id.txtNameCategories);
            imgCategories = itemView.findViewById(R.id.imgCategories);
            cardViewHolder = itemView.findViewById(R.id.cardViewHolder);
        }
    }
}