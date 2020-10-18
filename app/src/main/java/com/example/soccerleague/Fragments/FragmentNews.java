package com.example.soccerleague.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soccerleague.Adapters.NewsAdapter.NewsAdapter;
import com.example.soccerleague.Interface.APIService;
import com.example.soccerleague.News;
import com.example.soccerleague.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FragmentNews extends Fragment {

    RecyclerView recyclerViewNews;
    NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_news, container, false);
        recyclerViewNews = view.findViewById(R.id.recyclerViewNews);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewNews.setLayoutManager(layoutManager);
        getNewsDetails();
        return view;
    }

    private void getNewsDetails(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIService.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
            APIService api = retrofit.create(APIService.class);
            Call<News> call = api.getNewsDetails();

            call.enqueue(new Callback<News>() {

                @Override
                public void onResponse(Call<News> call, retrofit2.Response<News> response) {
                    List<News.Channel.Item> items = response.body().getChannel().getItem();

                    newsAdapter = new NewsAdapter(getContext(), items);
                    recyclerViewNews.setAdapter(newsAdapter);
                }
                @Override
                public void onFailure(Call<News> call, Throwable t) {
                }
            });
    }
}
