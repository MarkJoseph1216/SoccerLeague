package com.example.soccerleague.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soccerleague.Adapters.LeagueAdapter.LeagueAdapter;
import com.example.soccerleague.Interface.APIService;
import com.example.soccerleague.Model.League.ResponseCountry;
import com.example.soccerleague.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentLeague extends Fragment {

    RecyclerView recyclerViewList;
    LeagueAdapter leagueAdapter;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_league, container, false);
        init(view);
        getSportsList();
        return view;
    }

    private void init(View view){
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));

        relativeLayout = view.findViewById(R.id.relativeLayout);
        recyclerViewList = view.findViewById(R.id.recyclerViewList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewList.setLayoutManager(layoutManager);
    }

    public void getSportsList() {
        relativeLayout.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL_SCHEDULES)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService api = retrofit.create(APIService.class);
        Call<ResponseCountry> call = api.getSportsList("England");
        call.enqueue(new Callback<ResponseCountry>() {

            @Override
            public void onResponse(Call<ResponseCountry> call, retrofit2.Response<ResponseCountry> response) {
                List<ResponseCountry.Country> getCountryDetails = response.body().getResponseCountrys();
                leagueAdapter = new LeagueAdapter(getContext(), getCountryDetails);
                recyclerViewList.setAdapter(leagueAdapter);
                relativeLayout.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<ResponseCountry> call, Throwable t) {
                Log.d("GetERROR", String.valueOf(t.getMessage()));
            }
        });
    }
}
