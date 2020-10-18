package com.example.soccerleague.Interface;

import com.example.soccerleague.News;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    String BASE_URL = "https://www.allarsenal.com/";

    @GET("feed/")
    Call<News> getNewsDetails();
}
