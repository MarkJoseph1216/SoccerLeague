package com.sports.footballcage.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sports.footballcage.Model.Teams;
import com.sports.footballcage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SportsViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    ArrayList<Teams> getSports;

    public SportsViewPagerAdapter(Context context, ArrayList<Teams> getSports) {
        this.context = context;
        this.getSports = getSports;
    }

    @Override
    public int getCount() {
        return getSports.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.sports_banner_viewpager_items, null);
        ImageView imageSports = (ImageView) view.findViewById(R.id.imgBanners);

        for (int index = 0; index < 5; index++) {
            Teams teams = getSports.get(position);
            Picasso.with(context).load(teams.getStrStadiumThumb()).into(imageSports);
        }
        (container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}