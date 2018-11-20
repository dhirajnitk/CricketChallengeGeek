package com.dhiraj.GeekTrust.CricketChallenge.client;

import com.dhiraj.GeekTrust.CricketChallenge.model.PlayerProbabilityMatrix;
import com.dhiraj.GeekTrust.CricketChallenge.model.Team;
import com.dhiraj.GeekTrust.CricketChallenge.model.Teams;
import com.dhiraj.GeekTrust.CricketChallenge.service.CricketTournament;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.*;

public class TournamentClient {

    private final static Logger logger = LoggerFactory.getLogger(TournamentClient.class);
    private final static List<String> weatherType = Arrays.asList("Clear","Cloudy");
    private final static List<String> dayType = Arrays.asList("Day", "Night");

    private final CricketTournament  cricketTournament;
    private final Map<Teams, List<String>> teamsDetails;
    private final PlayerProbabilityMatrix playerProbabilityMatrix;



    public TournamentClient(Pair<Teams,Pair<List<String>, int[][]>> ... teamMatrix){
        cricketTournament = new CricketTournament();
        teamsDetails  = new HashMap<>();
        playerProbabilityMatrix = new PlayerProbabilityMatrix();
        for(int index = 0; index < teamMatrix.length; index++) {
            teamsDetails.put(teamMatrix[index].getKey(), teamMatrix[index].getValue().getKey());
            playerProbabilityMatrix.addProbabilityMap(teamMatrix[index].getValue().getKey(), teamMatrix[index].getValue().getValue());
        }
    }

    private Pair<Teams,Teams> getToss(Random random){
        Pair<Teams,Teams> teamsPair = new Pair<Teams,Teams>(Teams.Lengaburu, Teams.Enchai);
        int weatherIndex =  random.nextInt(weatherType.size());
        int dayIndex =  random.nextInt(dayType.size());
        return  cricketTournament.evaluateToss(random, teamsPair, weatherType.get(weatherIndex), dayType.get(dayIndex));
    }

    public String getTossResult(Random random){
        Pair<Teams,Teams> tossResult = getToss(random);
        String batFormat = "%s wins toss and bats";
        String bowlFormat = "%s wins toss and bowls";
        if(tossResult.getKey()  == tossResult.getValue())
            return String.format(batFormat, tossResult.getKey().name());
        return String.format(bowlFormat, tossResult.getKey().name());
    }

    public  boolean  playSuperOverMatch(){
        Pair<Teams,Teams> tossResult = getToss(new Random());
        Teams teams1, teams2;

        if(tossResult.getKey() == tossResult.getValue()){
            teams1 = tossResult.getKey();
            int index = Teams.values().length - 1 - Teams.valueOf(teams1.name()).ordinal();
            teams2 = Teams.values()[index];
        }
        else{
            teams1 = tossResult.getValue();
            teams2 = tossResult.getKey();
        }

        Team team1 = new Team(teams1.name(),teamsDetails.get(teams1));
        Team team2 = new Team(teams2.name(),teamsDetails.get(teams2));
        try {
            cricketTournament.playMatch(team1, team2, 1, playerProbabilityMatrix);
        } catch (IOException | CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;

    }

    public  boolean chaseMatch(int overs, int runsToWin)  {
        if(teamsDetails.isEmpty())
           return false;
        Map.Entry<Teams, List<String>> details = teamsDetails.entrySet().stream().findFirst().get();
        Team team = new Team(details.getKey().name(), details.getValue());
        try {
            cricketTournament.chaseInning(team, overs, playerProbabilityMatrix, runsToWin);
        }
        catch (IOException| CloneNotSupportedException e ) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

}


