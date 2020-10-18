package com.example.soccerleague.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.soccerleague.Adapters.DashboardHomeAdapter;
import com.example.soccerleague.Adapters.SportsViewPagerAdapter;
import com.example.soccerleague.Environment.Variables;
import com.example.soccerleague.JSON.HttpHandler;
import com.example.soccerleague.Model.Teams;
import com.example.soccerleague.R;
import com.google.android.material.appbar.AppBarLayout;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FragmentHome extends Fragment {

    public static AsyncTask<String, String, ArrayList<Teams>> getTeams;
    public static Executor executor;

    ArrayList<Teams> getAllTeams = new ArrayList<>();

    //View Pager Dots
    public static LinearLayout sliderDots;
    private int dotscount;
    private ImageView[] dots;
    ViewPager viewPagerSports;

    RecyclerView recycleview_home;
    DashboardHomeAdapter dashboardHomeAdapter;
    AppBarLayout appBarLayout;
    EditText edtSearchItems;

    TextView txtLoadingDetails;
    AVLoadingIndicatorView loadingBanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_community, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getActivity().getWindow().setStatusBarColor(Color.WHITE);

        txtLoadingDetails = view.findViewById(R.id.txtLoadingDetails);
        loadingBanner = view.findViewById(R.id.loadingBanner);
        edtSearchItems = view.findViewById(R.id.edtSearchItems);
        appBarLayout = view.findViewById(R.id.appBarLayout);
        recycleview_home = view.findViewById(R.id.recycleview_home);
        sliderDots = view.findViewById(R.id.SliderDots);
        viewPagerSports = view.findViewById(R.id.viewPagerSports);
        executor = Executors.newSingleThreadExecutor();
        getTeams = new GetTeams();
        getTeams.executeOnExecutor(executor);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    edtSearchItems.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corner_background_gray));
                } else if (verticalOffset == 0) {
                    edtSearchItems.setBackground(getActivity().getResources().getDrawable(R.drawable.rounded_corner_background));
                } else if (verticalOffset <= -250){

                }
            }
        });

        edtSearchItems.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text){
        ArrayList<Teams> temp = new ArrayList();
        for(Teams teams: getAllTeams){
            if(teams.getStrTeam().toLowerCase().contains(text.toLowerCase())){
                temp.add(teams);
            }
        }
        //Update Recyclerview
        dashboardHomeAdapter.updateList(temp);
    }

    private class GetTeams extends AsyncTask<String, String, ArrayList<Teams>> {
        @Override
        protected void onPreExecute() {
            loadingBanner.setVisibility(View.VISIBLE);
            txtLoadingDetails.setVisibility(View.VISIBLE);
        }

        protected ArrayList<Teams> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(Variables.teamsURL);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObjectSports = new JSONObject(jsonStr);
                        JSONArray jsonArraySports = jsonObjectSports.getJSONArray("teams");

                        for (int i = 0; i < jsonArraySports.length(); i++) {
                            JSONObject index = jsonArraySports.getJSONObject(i);

                            String idTeam = index.getString("idTeam");
                            String strTeam = index.getString("strTeam");
                            String strAlternate = index.getString("strAlternate");
                            String strStadiumThumb = index.getString("strStadiumThumb");
                            String strStadiumDescription = index.getString("strStadiumDescription");
                            String strStadiumLocation = index.getString("strStadiumLocation");
                            String strWebsite = index.getString("strWebsite");
                            String strFacebook = index.getString("strFacebook");
                            String strInstagram = index.getString("strInstagram");
                            String strYoutube = index.getString("strYoutube");
                            String strDescriptionEN = index.getString("strDescriptionEN");
                            String strTeamBadge = index.getString("strTeamBadge");
                            String strTeamJersey = index.getString("strTeamJersey");
                            String strTeamLogo = index.getString("strTeamLogo");
                            String strTeamFanart3 = index.getString("strTeamFanart3");
                            String strTeamFanart4 = index.getString("strTeamFanart4");
                            getAllTeams.add(new Teams(idTeam, strTeam, strAlternate, strStadiumThumb, strStadiumDescription, strStadiumLocation, strWebsite, strFacebook, strInstagram, strYoutube, strDescriptionEN, strTeamBadge, strTeamJersey, strTeamLogo, strTeamFanart3, strTeamFanart4));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return getAllTeams;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return getAllTeams;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList<Teams> result) {

            try {
                loadingBanner.setVisibility(View.GONE);
                txtLoadingDetails.setVisibility(View.GONE);

                SportsViewPagerAdapter viewPagerAdapter = new SportsViewPagerAdapter(getContext(), result);
                viewPagerSports.setAdapter(viewPagerAdapter);
                dotscount = 5;
                dots = new ImageView[dotscount];

                for (int i = 0; i < dotscount; i++) {
                    dots[i] = new ImageView(getContext());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(6, 0, 6, 0);

                    sliderDots.addView(dots[i], params);
                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));

                viewPagerSports.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }
                    @Override
                    public void onPageSelected(int position) {
                        try {
                            for (int i = 0; i < dotscount; i++) {
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));
                            }
                            dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new MyTimerTask(), 2500, 4500);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recycleview_home.setLayoutManager(layoutManager);

                dashboardHomeAdapter = new DashboardHomeAdapter(getContext(), result);
                recycleview_home.setAdapter(dashboardHomeAdapter);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (viewPagerSports.getCurrentItem() == 0) {
                                viewPagerSports.setCurrentItem(1, true);
                                Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.splash);
                                viewPagerSports.startAnimation(animation1);
                            } else if (viewPagerSports.getCurrentItem() == 1) {
                                viewPagerSports.setCurrentItem(2, true);
                            } else if (viewPagerSports.getCurrentItem() == 2) {
                                viewPagerSports.setCurrentItem(3, true);
                                Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.splash);
                                viewPagerSports.startAnimation(animation1);
                            } else if (viewPagerSports.getCurrentItem() == 3) {
                                viewPagerSports.setCurrentItem(4, true);
                            } else {
                                viewPagerSports.setCurrentItem(0, true);
                                Animation animation1 = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                                viewPagerSports.startAnimation(animation1);
                            }
                            return;
                        } catch (Exception e) {
                            e.getMessage();
                        }
                    }
                });
            }
        }
    }
}
