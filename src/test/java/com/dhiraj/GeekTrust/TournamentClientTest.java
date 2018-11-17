package com.dhiraj.GeekTrust;

import com.dhiraj.GeekTrust.client.TournamentClient;
import com.dhiraj.GeekTrust.model.Teams;
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
    private final int count =  new Random().nextInt(10) + 1 ;
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
        TournamentClient tournamentClient = new TournamentClient();
        for(int index = 0; index < count; index++)
            assertTrue(tournamentClient.playSuperOverMatch());
    }


    @Test
    public void testChaseMatch() {
        TournamentClient tournamentClient = new TournamentClient();
        for(int index = 0; index < count; index++)
            assertTrue(tournamentClient.chaseMatch(4,40));
    }

}
