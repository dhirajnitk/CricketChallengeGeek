package com.dhiraj.GeekTrust.CricketChallenge.service;

import com.dhiraj.GeekTrust.CricketChallenge.model.*;
import javafx.util.Pair;
import java.io.IOException;
import java.util.Random;

public class CricketTournament {

    private Toss toss;

    public CricketTournament(){
        toss = new Toss();
    }

    public Pair<Teams,Teams>  evaluateToss(Random random, Pair<Teams, Teams> teamsPair, String ... inputs){

        return toss.getTossResult(random, teamsPair, inputs);

    }

    public void playMatch(Team team1, Team team2,  int overs, PlayerProbabilityMatrix playerProbability) throws IOException, CloneNotSupportedException {
        Inning inning1 = new Inning(team1, overs , playerProbability, Inning.Type.FIRST);
        inning1.playInning();
        Inning inning2 = new Inning(inning1, team2, overs, playerProbability);
        MatchResult result= inning2.playInning();
        ScoreCard scoreCard = new ScoreCard(result,inning2);
        scoreCard.printScoreCard();
        Commentary.printCommentary();

    }

    public void chaseInning(Team team, int overs, PlayerProbabilityMatrix playerProbability, int runs) throws IOException, CloneNotSupportedException {
        Inning dummy = new Inning(null, overs, playerProbability, Inning.Type.FIRST );
        dummy.setTargetScore(runs);
        Inning inning2 = new Inning(dummy, team, overs, playerProbability);
        MatchResult result= inning2.playInning();
        ScoreCard scoreCard = new ScoreCard(result,inning2);
        scoreCard.printScoreCard();
        Commentary.printCommentary();

    }

    
}
