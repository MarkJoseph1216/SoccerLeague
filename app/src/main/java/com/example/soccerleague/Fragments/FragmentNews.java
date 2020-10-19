package com.example.soccerleague.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.example.soccerleague.Adapters.NewsAdapter.NewsAdapter;
import com.example.soccerleague.Interface.APIService;
import com.example.soccerleague.News;
import com.example.soccerleague.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FragmentNews extends Fragment {

    RecyclerView recyclerViewNews;
    NewsAdapter newsAdapter;
    AVLoadingIndicatorView loadingBanner;
    TextView txtLoadingNews;
    PullRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_news, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        txtLoadingNews = view.findViewById(R.id.txtLoadingNews);
        loadingBanner = view.findViewById(R.id.loadingBanner);
        recyclerViewNews = view.findViewById(R.id.recyclerViewNews);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewNews.setLayoutManager(layoutManager);
        getNewsDetails();

        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setNestedScrollingEnabled(true);

        swipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerViewNews.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(true);
                getNewsDetails();
            }
        });
    }

    private void getNewsDetails() {
        try {
            txtLoadingNews.setVisibility(View.VISIBLE);
            loadingBanner.setVisibility(View.VISIBLE);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
            APIService api = retrofit.create(APIService.class);
            Call<News> call = api.getNewsDetails();

            call.enqueue(new Callback<News>() {

                @Override
                public void onResponse(Call<News> call, retrofit2.Response<News> response) {

                    txtLoadingNews.setVisibility(View.GONE);
                    loadingBanner.setVisibility(View.GONE);
                    recyclerViewNews.setVisibility(View.VISIBLE);
                    swipeRefresh.setRefreshing(false);

                    List<News.Channel.Item> items = response.body().getChannel().getItem();
                    newsAdapter = new NewsAdapter(getContext(), items);
                    recyclerViewNews.setAdapter(newsAdapter);
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                }
            });
        } catch (Exception e){
            e.getMessage();
        }
    }
}
