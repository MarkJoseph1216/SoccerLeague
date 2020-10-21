package com.example.soccerleague.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.soccerleague.Adapters.DashboardHomeAdapter;
import com.example.soccerleague.Adapters.SportsViewPagerAdapter;
import com.example.soccerleague.Environment.Variables;
import com.example.soccerleague.Fragments.FragmentHome;
import com.example.soccerleague.JSON.HttpHandler;
import com.example.soccerleague.Model.Teams;
import com.example.soccerleague.Model.TeamsInformation;
import com.example.soccerleague.R;
import com.wang.avi.AVLoadingIndicatorView;

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

    ScrollView scrollView;
    WebView webView;
    AVLoadingIndicatorView loadingWebView;
    TextView txtError;

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
        scrollView = findViewById(R.id.scrollView);
        txtError = findViewById(R.id.txtError);
        loadingWebView = findViewById(R.id.loadingWebView);

        try {
            Intent intent = getIntent();
            String teamId = intent.getStringExtra("teamID");
            String webURL = intent.getStringExtra("webURL");

            if (teamId == null) {
                loadWebViewURL(webURL);
            } else {
                executor = Executors.newSingleThreadExecutor();
                getTeamInformation = new GetTeamInformation();
                getTeamInformation.executeOnExecutor(executor, teamId);
            }
        } catch (Exception e) {
            e.getMessage();
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadWebViewURL(String url){
        if (!url.contains("https://")) {
            url = "https://" + url;
        }
        webView = findViewById(R.id.webView);
        webView.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        loadingWebView.setVisibility(View.VISIBLE);
        txtError.setVisibility(View.GONE);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.setWebViewClient(new WebViewClient()  {
             @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                 view.loadUrl(url);
                 return true;
            }
            @Override
            @TargetApi(Build.VERSION_CODES.N)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (view.getProgress() >= 100) {
                    loadingWebView.setVisibility(View.GONE);
                }
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                txtError.setVisibility(View.VISIBLE);
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Ignore SSL certificate errors
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
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