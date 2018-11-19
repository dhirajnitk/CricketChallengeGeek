package com.dhiraj.GeekTrust.CricketChallenge.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Team implements  Cloneable{

    private String teamName;
    private List<String> playerNames;
    public Team(String name, List<String> names) {
        teamName = name;
        playerNames = names;

    }
    public Team(String name, String [] names){
        teamName = name;
        playerNames = Arrays.asList(names);
    }
    public Object clone() throws CloneNotSupportedException {
        Team team = (Team)super.clone();
        team.teamName = teamName;
        team.playerNames = playerNames.stream().collect(Collectors.toList());
        return team;
    }
    public int getSize(){
        return playerNames.size();
    }

    public String getPlayerName(int index){
        return playerNames.get(index);
    }

    public String getTeamName() {
        return teamName;
    }
}
