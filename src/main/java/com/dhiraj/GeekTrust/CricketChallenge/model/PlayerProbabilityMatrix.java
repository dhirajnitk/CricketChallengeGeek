package com.dhiraj.GeekTrust.CricketChallenge.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class PlayerProbabilityMatrix {

    private Map<String, int  []> cumProbMatrix;

    public PlayerProbabilityMatrix(){
        cumProbMatrix = new HashMap<>();
    }

    public void addProbabilityMap(TeamProbabilityMatrix teamProbabilityMatrix ){
        setCumSums(teamProbabilityMatrix.getNames(), teamProbabilityMatrix.getPrbMatrix() );
    }

    private void setCumSums(List<String> names, int [][] probMatrix){
        IntStream.range(0,probMatrix.length).forEach(index-> {
            AtomicInteger sum = new AtomicInteger(0);
            cumProbMatrix.put(names.get(index), Arrays.stream(probMatrix[index]).map(sum::addAndGet).toArray());
        });
    }

    public int [] getPlayerCumProb(String name){
        return cumProbMatrix.get(name);
    }

}
