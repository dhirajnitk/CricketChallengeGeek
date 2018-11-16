package com.dhiraj.GeekTrust.service;

import com.dhiraj.GeekTrust.util.Team;
import com.dhiraj.GeekTrust.util.Teams;
import javafx.util.Pair;
import java.io.IOException;
public class CricketTournament {

    private Toss toss;

    public CricketTournament(){
        toss = new Toss();
    }

    public Pair<Teams,Teams>  evaluateToss(Pair<Teams, Teams> teamsPair, String ... inputs){

        return toss.getTossResult(teamsPair, inputs);

    }

    public void playMatch(Team team1, Team team2, int overs) throws IOException {
        Inning inning1 = new Inning(team1, overs , Inning.Type.FIRST);
        inning1.playInning();
        Inning inning2 = new Inning(inning1, team2, overs);
        Result result= inning2.playInning();
        ScoreCard scoreCard = new ScoreCard(result,inning2);
        scoreCard.printScoreCard();
        Commentary.printCommentary();

    }

    public void chaseInning(Team team, int overs, int runs) throws IOException {
        Inning dummy = new Inning(null, overs, Inning.Type.FIRST );
        dummy.setTargetScore(runs);
        Inning inning2 = new Inning(dummy, team, overs);
        Result result= inning2.playInning();
        ScoreCard scoreCard = new ScoreCard(result,inning2);
        scoreCard.printScoreCard();
        Commentary.printCommentary();

    }

    
}
