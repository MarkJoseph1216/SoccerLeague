package com.example.soccerleague;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.soccerleague.Fragments.FragmentHome;
import com.example.soccerleague.Fragments.FragmentLeague;
import com.example.soccerleague.Fragments.FragmentNews;
import com.example.soccerleague.Fragments.FragmentSchedules;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    FrameLayout frameLayout;

    String fragmentStatus = "";
    int exitCount = 0;

    // BOTTOM NAVIGATION LISTENER
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (fragmentStatus.equals("Home")) {
                        exitCount = 0;
                    } else {
                        fragmentStatus = "Home";
                        exitCount = 0;
                        FragmentHome FragmentHome = new FragmentHome();
                        FragmentManager managerhome = getSupportFragmentManager();
                        managerhome.beginTransaction().replace(R.id.dashboard_frameLayout, FragmentHome, FragmentHome.getTag()).commit();
                    }
                    return true;

                case R.id.navigation_schedules:
                    if (fragmentStatus.equals("Community")) {
                        exitCount = 0;
                    } else {
                        fragmentStatus = "Community";
                        exitCount = 0;
                        FragmentSchedules fragmentSchedules = new FragmentSchedules();
                        FragmentManager managerSchedules = getSupportFragmentManager();
                        managerSchedules.beginTransaction().replace(R.id.dashboard_frameLayout, fragmentSchedules, fragmentSchedules.getTag()).commit();
                    }
                    return true;
                case R.id.navigation_news:
                    if (fragmentStatus.equals("News")) {
                        exitCount = 0;
                    } else {
                        fragmentStatus = "News";
                        exitCount = 0;
                        FragmentNews fragmentNews = new FragmentNews();
                        FragmentManager managerNews = getSupportFragmentManager();
                        managerNews.beginTransaction().replace(R.id.dashboard_frameLayout, fragmentNews, fragmentNews.getTag()).commit();
                    }
                    return true;

                case R.id.navigation_league:
                    if (fragmentStatus.equals("League")) {
                        exitCount = 0;
                    } else {
                        fragmentStatus = "League";
                        exitCount = 0;
                        FragmentLeague fragmentList = new FragmentLeague();
                        FragmentManager managerList = getSupportFragmentManager();
                        managerList.beginTransaction().replace(R.id.dashboard_frameLayout, fragmentList, fragmentList.getTag()).commit();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        frameLayout = (FrameLayout) findViewById(R.id.dashboard_frameLayout);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bottomNavigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void onBackPressed() {
        if (exitCount == 0) {
            exitCount++;
            Toast.makeText(this, "Press back to exit.", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }
}