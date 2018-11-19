package com.dhiraj.GeekTrust.CricketChallenge;

import com.dhiraj.GeekTrust.CricketChallenge.model.TeamProbabilityMatrix;
import com.dhiraj.GeekTrust.CricketChallenge.model.Teams;
import com.dhiraj.GeekTrust.CricketChallenge.client.TournamentClient;
import javafx.util.Pair;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static junit.framework.TestCase.assertTrue;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@FixMethodOrder(MethodSorters.DEFAULT)

public final class TournamentClientTest {

    private final static List<String> weatherType = Arrays.asList("Clear","Cloudy");
    private final static List<String> dayType = Arrays.asList("Day", "Night");
    private final int count = 1;

    private boolean isTossCorrect(Teams teams, int weatherIndex, int dayIndex, String result){
        boolean isBat = result.contains("bats");
        boolean isBowl = result.contains("bowls");
        if (teams == Teams.Lengaburu) {
            if(weatherIndex == 0)
                return isBat;
            else if(dayIndex == 0)
                    return isBat;
            else
                    return isBowl;
        }
        else{
            if(weatherIndex == 1)
                return isBat;
            else if(dayIndex == 0)
                    return isBowl;
            else
                    return isBat;
        }

    }

    @Test
    public void testToss()  {
        Random random = new Random(count);
        int weatherIndex =  random.nextInt(weatherType.size());
        int dayIndex =  random.nextInt(dayType.size());
        System.out.println("Input: "+weatherType.get(weatherIndex) +" "+dayType.get(dayIndex));
        Teams teams;
        if(random.nextFloat() < 0.5f){
            teams = Teams.Lengaburu;
        }
        else{
            teams = Teams.Enchai;
        }

        TournamentClient tournamentClient = new TournamentClient();
        //A new Random object with same seed, will produce exactly same output.
        String result = tournamentClient.getTossResult(new Random(count));
        System.out.println(result);
        assertTrue(isTossCorrect(teams, weatherIndex, dayIndex, result));
    }

    @Test
    public void testSuperOverMatch()  {
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
        TeamProbabilityMatrix teamProbabilityMatrix1 = new TeamProbabilityMatrix(lengaburuNames, lengaburuProbMatrix);
        TeamProbabilityMatrix teamProbabilityMatrix2 = new TeamProbabilityMatrix(enchaiNames, enchaiProbMatrix);
        TournamentClient tournamentClient = new TournamentClient(new Pair<Teams, TeamProbabilityMatrix>(Teams.Lengaburu, teamProbabilityMatrix1),
                new Pair<Teams,TeamProbabilityMatrix>(Teams.Enchai, teamProbabilityMatrix2));
        for(int index = 0; index < count; index++)
            assertTrue(tournamentClient.playSuperOverMatch());
    }


    @Test
    public void testChaseMatch() {
        final  String [] lengaburuNames = {"Kirat Boli","N.S Nodhi","R Rumrah","Shashi Henra"};
        int [][] lengaburuProbMatrix = {
                {5, 30, 25, 10, 15, 1, 9, 5},
                {10, 40, 20, 5,10, 1, 4, 10},
                {20, 30, 15, 5, 5, 1, 4, 20},
                {30, 25, 5, 0, 5, 1, 4, 30}
        };
        TournamentClient tournamentClient = new TournamentClient(new Pair<Teams, TeamProbabilityMatrix>(Teams.Lengaburu, new TeamProbabilityMatrix(lengaburuNames, lengaburuProbMatrix)));
        for(int index = 0; index < count; index++)
            assertTrue(tournamentClient.chaseMatch(4,40));
    }

}
