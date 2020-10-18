package com.example.soccerleague.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.soccerleague.Adapters.DashboardHomeAdapter;
import com.example.soccerleague.Adapters.SportsViewPagerAdapter;
import com.example.soccerleague.Environment.Variables;
import com.example.soccerleague.Fragments.FragmentHome;
import com.example.soccerleague.JSON.HttpHandler;
import com.example.soccerleague.Model.Teams;
import com.example.soccerleague.Model.TeamsInformation;
import com.example.soccerleague.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TeamInformation extends AppCompatActivity {

    ImageView imgHeader, imgBack, imgTeamLogo, imageStatium;
    TextView txtDescription, txtAlternateName, txtLocation, txtStadiumDescription, txtLeagueName, txtFormedYear;
    RelativeLayout relativeLayout;

    public static AsyncTask<String, String, ArrayList<TeamsInformation>> getTeamInformation;
    public static Executor executor;

    ArrayList<TeamsInformation> getTeamInformations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_information);

        init();
    }

    private void init(){

        txtLeagueName = findViewById(R.id.txtLeagueName);
        txtFormedYear = findViewById(R.id.txtFormedYear);
        imageStatium = findViewById(R.id.imageStatium);
        txtStadiumDescription = findViewById(R.id.txtStadiumDescription);
        relativeLayout = findViewById(R.id.relativeLayout);
        txtLocation = findViewById(R.id.txtLocation);
        txtAlternateName = findViewById(R.id.txtAlternateName);
        imgBack = findViewById(R.id.imgBack);
        imgTeamLogo = findViewById(R.id.imgTeamLogo);
        imgHeader = findViewById(R.id.imgHeader);
        txtDescription = findViewById(R.id.txtDescription);

        Intent intent = getIntent();
        String teamId = intent.getStringExtra("teamID");

        executor = Executors.newSingleThreadExecutor();
        getTeamInformation = new GetTeamInformation();
        getTeamInformation.executeOnExecutor(executor, teamId);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class GetTeamInformation extends AsyncTask<String, String, ArrayList<TeamsInformation>> {
        @Override
        protected void onPreExecute() {
            relativeLayout.setVisibility(View.VISIBLE);
        }

        protected ArrayList<TeamsInformation> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                HttpHandler sh = new HttpHandler();
                String jsonStr = sh.makeServiceCall(Variables.teamInformationURL + params[0]);

                if (jsonStr != null) {
                    try {
                        JSONObject jsonObjectSports = new JSONObject(jsonStr);
                        JSONArray jsonArraySports = jsonObjectSports.getJSONArray("teams");

                        for (int i = 0; i < jsonArraySports.length(); i++) {
                            JSONObject index = jsonArraySports.getJSONObject(i);

                            String strTeam = index.getString("strTeam");
                            String strAlternate = index.getString("strAlternate");
                            String intFormedYear = index.getString("intFormedYear");
                            String strLeague2 = index.getString("strLeague2");
                            String strStadiumThumb = index.getString("strStadiumThumb");
                            String strStadiumDescription = index.getString("strStadiumDescription");
                            String strStadiumLocation = index.getString("strStadiumLocation");
                            String strDescriptionEN = index.getString("strDescriptionEN");
                            String strTeamBadge = index.getString("strTeamBadge");
                            String strTeamLogo = index.getString("strTeamLogo");
                            String strTeamJersey = index.getString("strTeamJersey");
                            String strTeamFanart4 = index.getString("strTeamFanart4");

                            getTeamInformations.add(new TeamsInformation(strTeam, strAlternate, intFormedYear, strLeague2, strStadiumThumb, strStadiumDescription, strStadiumLocation, strDescriptionEN, strTeamBadge, strTeamLogo, strTeamJersey, strTeamFanart4));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return getTeamInformations;
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
                return getTeamInformations;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList<TeamsInformation> result) {
            try {
                txtAlternateName.setText(result.get(0).getStrAlternate());
                relativeLayout.setVisibility(View.GONE);

                Glide.with(TeamInformation.this).load(result.get(0).getStrTeamLogo()).into(imgTeamLogo);
                Glide.with(TeamInformation.this).load(result.get(0).getStrTeamFanart4()).into(imgHeader);
                Glide.with(TeamInformation.this).load(result.get(0).getStrStadiumThumb()).into(imageStatium);

                txtLocation.setText(result.get(0).getStrStadiumLocation());
                txtDescription.setText(result.get(0).getStrDescriptionEN());
                txtStadiumDescription.setText(result.get(0).getStrStadiumDescription());
                txtFormedYear.setText(result.get(0).getIntFormedYear());
                txtLeagueName.setText(result.get(0).getStrLeague2());
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}