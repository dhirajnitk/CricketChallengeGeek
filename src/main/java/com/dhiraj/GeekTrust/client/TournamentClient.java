package com.dhiraj.GeekTrust.client;

import com.dhiraj.GeekTrust.model.PlayerProbabilityMatrix;
import com.dhiraj.GeekTrust.service.CricketTournament;
import com.dhiraj.GeekTrust.model.Team;
import com.dhiraj.GeekTrust.model.Teams;
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
    private final Map<Teams, Pair<String[],int[][]>> teamsDetails  = new HashMap<>();
    private PlayerProbabilityMatrix playerProbabilityMatrix;

    public TournamentClient(Pair<Teams,String []> team1,int [][] prbMatrix1,  Pair<Teams,String []> team2, int [][] prbMatrix2){
        teamsDetails.put(team1.getKey(), new Pair<String[],int [][] >(team1.getValue(), prbMatrix1));
        teamsDetails.put(team2.getKey(), new Pair<String[],int [][] >(team2.getValue(), prbMatrix2));
        playerProbabilityMatrix = new PlayerProbabilityMatrix();
    }

    public TournamentClient(Pair<Teams,String []> team,int [][] prbMatrix){
        teamsDetails.put(team.getKey(), new Pair<String[],int [][]>(team.getValue(), prbMatrix));
        playerProbabilityMatrix = new PlayerProbabilityMatrix();
    }
    public TournamentClient(){

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
        playerProbabilityMatrix.addProbabilityMap(teamsDetails.get(teams1).getKey(), teamsDetails.get(teams1).getValue());
        playerProbabilityMatrix.addProbabilityMap(teamsDetails.get(teams2).getKey(), teamsDetails.get(teams2).getValue());
        Team team1 = new Team(teams1.name(),teamsDetails.get(teams1).getKey());
        Team team2 = new Team(teams2.name(),teamsDetails.get(teams2).getKey());
        try {
            cricketTournament.playMatch(team1, team2, playerProbabilityMatrix, 1);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;

    }

    public  boolean chaseMatch(int overs, int runsToWin)  {
        Set<Map.Entry<Teams, Pair<String[],int[][]>>> teamDetailsSet = teamsDetails.entrySet();
        Optional<Map.Entry<Teams, Pair<String[],int[][]>>>teamDetailsOpt = teamDetailsSet.stream().findFirst();
        if(!teamDetailsOpt.isPresent())
            return false;
        Map.Entry<Teams, Pair<String[],int[][]>> teamDetailsEntry = teamDetailsOpt.get();
        playerProbabilityMatrix.addProbabilityMap(teamDetailsEntry.getValue().getKey(), teamDetailsEntry.getValue().getValue());
        Team team = new Team(teamDetailsEntry.getKey().name(),teamDetailsEntry.getValue().getKey());
        try {
            cricketTournament.chaseInning(team, overs,playerProbabilityMatrix, runsToWin);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

}


