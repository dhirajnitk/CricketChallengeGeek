package com.dhiraj.GeekTrust.model;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Team {

    private String teamName;
    private List<String> playerNames;
    public Team(String name, String[] names) {
        teamName = name;
        playerNames = Arrays.asList(names);

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
