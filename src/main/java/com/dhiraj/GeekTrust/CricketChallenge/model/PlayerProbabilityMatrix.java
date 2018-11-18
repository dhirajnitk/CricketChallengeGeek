package com.dhiraj.GeekTrust.CricketChallenge.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class PlayerProbabilityMatrix {

    private Map<String, int  []> cumProbMatrix;

    public PlayerProbabilityMatrix(){
        cumProbMatrix = new HashMap<>();
    }

    public void addProbabilityMap(String[] names, int[][] probMatrix ){
        setCumSums(names, probMatrix );
    }

    private void setCumSums( String[] names, int [][] probMatrix){
        IntStream.range(0,probMatrix.length).forEach(index-> {
            AtomicInteger sum = new AtomicInteger(0);
            cumProbMatrix.put(names[index], Arrays.stream(probMatrix[index]).map(sum::addAndGet).toArray());
        });
    }

    public int [] getPlayerCumProb(String name){
        return cumProbMatrix.get(name);
    }

}
