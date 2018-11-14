package com.dhiraj.GeekTrust.service;

import com.dhiraj.GeekTrust.util.Team;
import com.dhiraj.GeekTrust.util.Teams;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CricketTournament {
    private final static List<String> weatherType = Arrays.asList("Clear","Cloudy");
    private final static List<String> dayType = Arrays.asList("Day", "Night");
    private Toss toss;
    public CricketTournament(){
        toss = new Toss();
    }
    public String evaluateToss(){
        int weatherIndex =  new Random().nextInt(weatherType.size());
        int dayIndex =  new Random().nextInt(dayType.size());
        return toss.getTossResult(Teams.Lengaburu, weatherType.get(weatherIndex), dayType.get(dayIndex));
    }

    public boolean  playMatch(Team team1, Team team2, int overs) throws IOException {
        Inning inning1 = new Inning(team1, overs , Inning.Type.FIRST);
        inning1.playInning();
        Inning inning2 = new Inning(inning1, team2, overs);
        Result result= inning2.playInning();
        ScoreCard scoreCard = new ScoreCard(result,inning2);
        scoreCard.printScoreCard();
        Commentary.printCommentary();
        return true;
    }

    public boolean chaseSingleInning(Team team, int overs, int runs) throws IOException {
        Inning dummy = new Inning(null, overs, Inning.Type.FIRST );
        dummy.setTargetScore(runs);
        Inning inning2 = new Inning(dummy, team, overs);
        Result result= inning2.playInning();
        ScoreCard scoreCard = new ScoreCard(result,inning2);
        scoreCard.printScoreCard();
        Commentary.printCommentary();
        return true;

    }

    
}
