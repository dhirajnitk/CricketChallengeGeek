package com.dhiraj.GeekTrust.client;

import com.dhiraj.GeekTrust.service.CricketTournament;
import com.dhiraj.GeekTrust.util.Team;

import java.io.IOException;

public class PlayTournament {
    private CricketTournament  cricketTournament = new CricketTournament();

    public boolean getTossResult(){
        cricketTournament.evaluateToss();
        return true;

    }
    public  boolean  playSuperOverMatch() throws IOException {
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
        cricketTournament.playMatch(team1, team2,1);
        return true;

    }
    public  boolean chase4OverMatch() throws IOException {
        final  String [] lengaburuNames = {"Kirat Boli","N.S Nodhi","R Rumrah","Shashi Henra"};
        int [][] lengaburuProbMatrix = {
                {5, 30, 25, 10, 15, 1, 9, 5},
                {10, 40, 20, 5,10, 1, 4, 10},
                {20, 30, 15, 5, 5, 1, 4, 20},
                {30, 25, 5, 0, 5, 1, 4, 30}
        };

        Team team = new Team("Lengaburu",lengaburuNames, lengaburuProbMatrix);
        cricketTournament.chaseSingleInning(team, 4, 40);
        return true;
    }

}


