package com.example.soccerleague.Environment;

import com.example.soccerleague.Model.Schedules.Schedules;
import com.example.soccerleague.Model.Teams;

import java.util.ArrayList;

public class Variables {
    public static String teamsURL = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=English%20Premier%20League";
    public static String teamInformationURL = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=";

    public static ArrayList<Teams> getTeamsList = new ArrayList<>();
    public static ArrayList<Schedules.Event> getEventList = new ArrayList<>();
}
