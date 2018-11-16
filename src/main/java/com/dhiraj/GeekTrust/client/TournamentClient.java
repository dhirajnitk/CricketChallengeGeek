package com.dhiraj.GeekTrust.client;

import com.dhiraj.GeekTrust.service.CricketTournament;
import com.dhiraj.GeekTrust.util.Team;
import com.dhiraj.GeekTrust.util.Teams;
import com.dhiraj.GeekTrust.util.TossType;
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

    private Pair<Teams,Teams> getToss(Random random){
        Pair<Teams,Teams> teamsPair = new Pair<Teams,Teams>(Teams.Lengaburu, Teams.Enchai);
        int weatherIndex =  random.nextInt(weatherType.size());
        int dayIndex =  random.nextInt(dayType.size());
        return  cricketTournament.evaluateToss(teamsPair, weatherType.get(weatherIndex), dayType.get(dayIndex));
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
        final  String [] lengaburuNames = {"Kirat Boli","N.S Nodhi"};
        final  String [] enchaiNames = {"DB Vellyers","H Mamla"};
        final int [][] lengaburuProbMatrix  = {
                {5, 10, 25, 10, 25, 1, 14, 10},
                {5, 15, 15, 10, 20, 1, 19, 15}
        };
        final int [][] enchaiProbMatrix = {
                {5, 10, 25, 10, 25, 1, 14, 10},
                {10, 15, 15, 10, 20,1, 19, 10}
        };
        final Map<Teams, Pair<String[],int[][]>> teamsPairMap  = new HashMap<>();
        teamsPairMap.put(Teams.Lengaburu, new Pair<>(lengaburuNames, lengaburuProbMatrix));
        teamsPairMap.put(Teams.Enchai, new Pair<>(enchaiNames, enchaiProbMatrix));
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
        Team team1 = new Team(teams1.name(),teamsPairMap.get(teams1).getKey(), teamsPairMap.get(teams1).getValue());
        Team team2 = new Team(teams2.name(),teamsPairMap.get(teams2).getKey(), teamsPairMap.get(teams2).getValue());
        try {
            cricketTournament.playMatch(team1, team2,1);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;

    }

    public  boolean chaseMatch(int overs, int runsToWin)  {
        final  String [] lengaburuNames = {"Kirat Boli","N.S Nodhi","R Rumrah","Shashi Henra"};
        int [][] lengaburuProbMatrix = {
                {5, 30, 25, 10, 15, 1, 9, 5},
                {10, 40, 20, 5,10, 1, 4, 10},
                {20, 30, 15, 5, 5, 1, 4, 20},
                {30, 25, 5, 0, 5, 1, 4, 30}
        };

        Team team = new Team("Lengaburu",lengaburuNames, lengaburuProbMatrix);
        try {
            cricketTournament.chaseInning(team, overs, runsToWin);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

}


