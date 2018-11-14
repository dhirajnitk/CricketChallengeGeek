package com.dhiraj.GeekTrust.util;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Team {
    private String teamName;
    private  String [] playerNames;
    private int [][] cumProbMatrix;
    public Team(String name, String[] names, int[][] probMatrix) {
        teamName = name;
        playerNames = names.clone();
        cumProbMatrix = getCumSums(probMatrix);
    }
    private int [][] getCumSums( int [][] probMatrix){
        int [][] cumSumProbMatrix = new int [probMatrix.length][probMatrix[0].length];
        IntStream.range(0,probMatrix.length).forEach(index-> {
            AtomicInteger sum = new AtomicInteger(0);
            cumSumProbMatrix[index] = Arrays.stream(probMatrix[index]).map(sum::addAndGet).toArray();
        });
        return cumSumProbMatrix;
    }

    public int getSize(){
        return playerNames.length;
    }
    public String getPlayerName(int index){
        return playerNames[index];
    }
    public int [] getPlayerCumProb(int index){
        return cumProbMatrix[index];
    }

    public String getTeamName() {
        return teamName;
    }
}
