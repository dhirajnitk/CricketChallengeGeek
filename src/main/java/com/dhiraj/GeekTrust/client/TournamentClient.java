package com.dhiraj.GeekTrust.client;

import com.dhiraj.GeekTrust.service.CricketTournament;
import com.dhiraj.GeekTrust.util.Team;
import com.dhiraj.GeekTrust.util.Teams;
import com.dhiraj.GeekTrust.util.TossType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class TournamentClient {

    private static final Logger logger = LoggerFactory.getLogger(TournamentClient.class);
    private CricketTournament  cricketTournament = new CricketTournament();

    public String getTossResult(Teams teams, String ... inputs){
        int tossResult = cricketTournament.evaluateToss(teams, inputs);
        String outputFormat = "%s wins toss and %s";
        return String.format(outputFormat, teams.toString(),  TossType.values()[tossResult].name().toLowerCase());

    }
    public  boolean  playSuperOverMatch(){
        final  String [] lengaburuNames = {"Kirat Boli","N.S Nodhi"};
        final  String [] enchaiNames = {"DB Vellyers","H Mamla"};
        int [][] lengaburuProbMatrix  = {
                {5, 10, 25, 10, 25, 1, 14, 10},
                {5, 15, 15, 10, 20, 1, 19, 15}
        };
        int [][] enchaiProbMatrix = {
                {5, 10, 25, 10, 25, 1, 14, 10},
                {10, 15, 15, 10, 20,1, 19, 10}
        };

        Team team1 = new Team("Lengaburu",lengaburuNames, lengaburuProbMatrix);
        Team team2 = new Team("Enchai",enchaiNames, enchaiProbMatrix);
        try {
            cricketTournament.playMatch(team1, team2,1);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;

    }
    public  boolean chase4OverMatch()  {
        final  String [] lengaburuNames = {"Kirat Boli","N.S Nodhi","R Rumrah","Shashi Henra"};
        int [][] lengaburuProbMatrix = {
                {5, 30, 25, 10, 15, 1, 9, 5},
                {10, 40, 20, 5,10, 1, 4, 10},
                {20, 30, 15, 5, 5, 1, 4, 20},
                {30, 25, 5, 0, 5, 1, 4, 30}
        };

        Team team = new Team("Lengaburu",lengaburuNames, lengaburuProbMatrix);
        try {
            cricketTournament.chaseSingleInning(team, 4, 40);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

}


