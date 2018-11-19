package com.dhiraj.GeekTrust.CricketChallenge.client;

import com.dhiraj.GeekTrust.CricketChallenge.model.PlayerProbabilityMatrix;
import com.dhiraj.GeekTrust.CricketChallenge.model.Team;
import com.dhiraj.GeekTrust.CricketChallenge.model.TeamProbabilityMatrix;
import com.dhiraj.GeekTrust.CricketChallenge.model.Teams;
import com.dhiraj.GeekTrust.CricketChallenge.service.CricketTournament;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.*;

public class TournamentClient {

    private static final Logger logger = LoggerFactory.getLogger(TournamentClient.class);
    private CricketTournament  cricketTournament = new CricketTournament();
    private final static List<String> weatherType = Arrays.asList("Clear","Cloudy");
    private final static List<String> dayType = Arrays.asList("Day", "Night");
    private final Map<Teams, TeamProbabilityMatrix> teamsDetails  = new HashMap<>();
    private PlayerProbabilityMatrix playerProbabilityMatrix;

    public TournamentClient(Pair<Teams,TeamProbabilityMatrix> ... team){
        playerProbabilityMatrix = new PlayerProbabilityMatrix();
        for(int index = 0; index < team.length; index++) {
            teamsDetails.put(team[index].getKey(), team[index].getValue());
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
        playerProbabilityMatrix.addProbabilityMap(teamsDetails.get(teams1));
        playerProbabilityMatrix.addProbabilityMap(teamsDetails.get(teams2));
        Team team1 = new Team(teams1.name(),teamsDetails.get(teams1).getNames());
        Team team2 = new Team(teams2.name(),teamsDetails.get(teams2).getNames());
        try {
            cricketTournament.playMatch(team1, team2, 1, playerProbabilityMatrix);
        } catch (IOException | CloneNotSupportedException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;

    }

    public  boolean chaseMatch(int overs, int runsToWin)  {
        Set<Map.Entry<Teams, TeamProbabilityMatrix>> teamDetailsSet = teamsDetails.entrySet();
        Optional<Map.Entry<Teams, TeamProbabilityMatrix>>teamDetailsOpt = teamDetailsSet.stream().findFirst();
        if(!teamDetailsOpt.isPresent())
            return false;
        Map.Entry<Teams, TeamProbabilityMatrix> teamDetailsEntry = teamDetailsOpt.get();
        playerProbabilityMatrix.addProbabilityMap(teamDetailsEntry.getValue());
        Team team = new Team(teamDetailsEntry.getKey().name(),teamDetailsEntry.getValue().getNames());
        try {
            cricketTournament.chaseInning(team, overs, playerProbabilityMatrix, runsToWin);
        } catch (IOException| CloneNotSupportedException e ) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

}


