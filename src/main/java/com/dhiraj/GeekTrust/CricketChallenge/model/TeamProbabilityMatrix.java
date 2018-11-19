package com.dhiraj.GeekTrust.CricketChallenge.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TeamProbabilityMatrix {

    private List<String> names;
    private int [][] prbMatrix;

    public TeamProbabilityMatrix(String [] names, int [][] prbMatrix){
        this.names = Arrays.asList(names);
        this.prbMatrix = prbMatrix;
    }

    public List<String> getNames() {
        return names.stream().collect(Collectors.toList());
    }

    int[][] getPrbMatrix() {
        return prbMatrix;
    }
}
