package com.sports.footballcage.Adapters.LeagueAdapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.sports.footballcage.Model.League.ResponseCountry;
import com.sports.footballcage.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.ViewHolder> {

    private List<ResponseCountry.Country> itemsDetails;
    private LayoutInflater mInflater;
    public Context context;
    View view;

    public LeagueAdapter(Context context, List<ResponseCountry.Country> itemsDetails) {
        this.context = context;
        this.itemsDetails = itemsDetails;
    }

    @Override
    public LeagueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       view = mInflater.inflate(R.layout.recyclerview_league_items, parent, false);
        return new LeagueAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LeagueAdapter.ViewHolder holder, final int position) {
        final ResponseCountry.Country country = itemsDetails.get(position);

        holder.txtLeagueName.setText(country.getStrLeague());

        if (country.getStrLeagueAlternate().equals("")) {
            holder.txtLeagueAlternate.setText("No League Division");
        } else {
            holder.txtLeagueAlternate.setText(country.getStrLeagueAlternate());
        }

        Picasso.with(context).load(country.getStrBadge()).into(holder.imgBadge);
        holder.txtGender.setText("Gender: " + country.getStrGender());
        holder.txtSport.setText("Sport: " + country.getStrSport());
        holder.txtInformedYear.setText("Informed Year: " + country.getIntFormedYear());
        holder.txtVersusTeams.setText("League Team Names: " + country.getStrNaming());
        holder.txtDescription.setText(Html.fromHtml("Description: \n\n") + country.getStrDescriptionEN());

        if (country.getStrLogo() == null) {
            holder.imgLeagueLogo.setVisibility(View.GONE);
        } else {
            Picasso.with(context).load(country.getStrLogo()).into(holder.imgLeagueLogo);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.relativeLayoutItems.getVisibility() == View.GONE) {
                    holder.relativeLayoutItems.setVisibility(View.VISIBLE);
                    holder.imgDropdown.setBackgroundResource(R.drawable.arrow_up);
                } else if (holder.relativeLayoutItems.getVisibility() == View.VISIBLE) {
                    holder.relativeLayoutItems.setVisibility(View.GONE);
                    holder.imgDropdown.setBackgroundResource(R.drawable.arrow_down);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtLeagueName, txtLeagueAlternate, txtGender, txtSport, txtInformedYear, txtVersusTeams, txtDescription;
        ImageView imgDropdown;
        RelativeLayout relativeLayoutItems;
        ImageView imgBadge;
        CircleImageView imgLeagueLogo;

        ViewHolder(View itemView) {
            super(itemView);

            txtGender = itemView.findViewById(R.id.txtGender);
            txtSport = itemView.findViewById(R.id.txtSport);
            txtLeagueName = itemView.findViewById(R.id.txtLeagueName);
            txtLeagueAlternate = itemView.findViewById(R.id.txtLeagueAlternate);
            imgDropdown = itemView.findViewById(R.id.imgDropdown);
            relativeLayoutItems = itemView.findViewById(R.id.relativeLayoutItems);
            imgBadge = itemView.findViewById(R.id.imgBadge);
            imgLeagueLogo = itemView.findViewById(R.id.imgLeagueLogo);
            txtInformedYear = itemView.findViewById(R.id.txtInformedYear);
            txtVersusTeams = itemView.findViewById(R.id.txtVersusTeams);
            txtDescription = itemView.findViewById(R.id.txtDescription);

        }
    }
}
