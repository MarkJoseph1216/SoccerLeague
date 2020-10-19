package com.example.soccerleague.Interface;

import com.example.soccerleague.Model.League.ResponseCountry;
import com.example.soccerleague.Model.Schedules.Schedules;
import com.example.soccerleague.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    String BASE_URL = "https://www.allarsenal.com/";
    String BASE_URL_SCHEDULES = "https://www.thesportsdb.com/api/v1/json/1/";

    @GET("feed/")
    Call<News> getNewsDetails();

    @GET("eventspastleague.php")
    Call<Schedules> getSchedules(@Query("id") String id);

    @GET("search_all_leagues.php")
    Call<ResponseCountry> getSportsList(@Query("c") String ResponseCountry);
}
