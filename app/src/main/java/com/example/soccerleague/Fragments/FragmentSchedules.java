package com.example.soccerleague.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.soccerleague.Adapters.SchedulesAdapter.SchedulesAdapter;
import com.example.soccerleague.Interface.APIService;
import com.example.soccerleague.Model.Schedules.Schedules;
import com.example.soccerleague.R;

import java.util.List;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentSchedules extends Fragment {

    SchedulesAdapter schedulesAdapter;
    RecyclerView recyclerViewSchedules;
    ShimmerLayout shimmerLayout;
    LinearLayout linearShimmerLayout;
    SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_schedules, container, false);
        init(view);
        getSchedules();
        return view;
    }

    private void init(View view){
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));

        linearShimmerLayout = view.findViewById(R.id.linearShimmerLayout);
        recyclerViewSchedules = view.findViewById(R.id.recyclerViewSchedules);
        shimmerLayout = view.findViewById(R.id.shimmerLayout);
        swipeRefresh = view.findViewById(R.id.swipeRefresh);

        shimmerLayout.startShimmerAnimation();
        linearShimmerLayout.setVisibility(View.VISIBLE);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                getSchedules();
            }
        });
    }

    public void getSchedules() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL_SCHEDULES)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService api = retrofit.create(APIService.class);
        Call<Schedules> call = api.getSchedules("4328");
        call.enqueue(new Callback<Schedules>() {
            @Override
            public void onResponse(Call<Schedules> call, retrofit2.Response<Schedules> response) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerViewSchedules.setLayoutManager(layoutManager);

                List<Schedules.Event> schedules = response.body().getEvents(); // Data fetch from request
                schedulesAdapter = new SchedulesAdapter(getContext(), schedules);
                recyclerViewSchedules.setAdapter(schedulesAdapter);
                shimmerLayout.stopShimmerAnimation();
                linearShimmerLayout.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<Schedules> call, Throwable t) {
            }
        });
    }
}
