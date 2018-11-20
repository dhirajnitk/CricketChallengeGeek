package com.dhiraj.GeekTrust.CricketChallenge;

import com.dhiraj.GeekTrust.CricketChallenge.model.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static junit.framework.TestCase.assertTrue;

import java.io.IOException;
import java.util.Arrays;

@FixMethodOrder(MethodSorters.DEFAULT)
public class InningTest {

    final private String [] lengaburuNames = {"Kirat Boli","N.S Nodhi"};
    final private  String [] enchaiNames = {"DB Vellyers","H Mamla"};
    final private int [][] lengaburuProbMatrix  = {
            {5, 10, 25, 10, 25, 1, 14, 10},
            {5, 15, 15, 10, 20, 1, 19, 15}
    };
    final private int [][] enchaiProbMatrix = {
            {5, 10, 25, 10, 25, 1, 14, 10},
            {10, 15, 15, 10, 20,1, 19, 10}
    };
    final private PlayerProbabilityMatrix playerProbabilityMatrix;

    public InningTest(){
        playerProbabilityMatrix = new PlayerProbabilityMatrix();
        playerProbabilityMatrix.addProbabilityMap(Arrays.asList(lengaburuNames), lengaburuProbMatrix);
        playerProbabilityMatrix.addProbabilityMap(Arrays.asList(enchaiNames), enchaiProbMatrix);
    }

    @Test
    public void testFirstInning() throws IOException {

        Inning inning1 = new Inning(new Team(Teams.Lengaburu.name(), lengaburuNames), 1 , playerProbabilityMatrix, Inning.Type.FIRST);
        inning1.playInning();
        assertTrue(inning1.getRunsToWin() == 5);
    }


    @Test
    public void testBothInning() throws IOException {
        Inning inning1 = new Inning(new Team(Teams.Lengaburu.name(),lengaburuNames), 1 , playerProbabilityMatrix, Inning.Type.FIRST);
        inning1.playInning();
        Inning inning2 = new Inning(inning1, new Team(Teams.Enchai.name(),enchaiNames), 1, playerProbabilityMatrix);
        MatchResult result= inning2.playInning();
        assertTrue(result.getWinningTeam().equals(Teams.Enchai.name()));
        assertTrue(result.getLosingTeam().equals(Teams.Lengaburu.name()));
        assertTrue(result.isDraw() == false);

    }
}
