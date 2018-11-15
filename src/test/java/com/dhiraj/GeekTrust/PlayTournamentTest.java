package com.dhiraj.GeekTrust;

import com.dhiraj.GeekTrust.client.PlayTournament;
import com.dhiraj.GeekTrust.util.Teams;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static junit.framework.TestCase.assertTrue;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@FixMethodOrder(MethodSorters.DEFAULT)
public final class PlayTournamentTest {

    private final static List<String> weatherType = Arrays.asList("Clear","Cloudy");
    private final static List<String> dayType = Arrays.asList("Day", "Night");
    private final int count = new Random().nextInt(10);

    private boolean isTossCorrect(Teams teams, int weatherIndex, int dayIndex, String result){
        boolean isBat = result.contains("bats");
        boolean isBowl = result.contains("bowls");
        if (teams == Teams.Lengaburu) {
            if(weatherIndex == 0)
                return isBat;
            else{
                if(dayIndex == 0)
                    return isBat;
                else
                    return isBowl;
            }
        }
        else{
            if(weatherIndex == 1)
                return isBat;
            else{
                if(dayIndex == 0)
                    return isBowl;
                else
                    return isBat;
            }
        }

    }

    @Test
    public void testToss()  {
        int weatherIndex =  new Random().nextInt(weatherType.size());
        int dayIndex =  new Random().nextInt(dayType.size());
        PlayTournament playTournament = new PlayTournament();
        System.out.println("Input: "+weatherType.get(weatherIndex) +" "+dayType.get(dayIndex));
        Random r = new Random(System.currentTimeMillis());
        Teams teams;
        if(r.nextFloat() < 0.5f){
            teams = Teams.Lengaburu;
        }
        else{
            teams = Teams.Enchai;
        }

        String result = playTournament.getTossResult(teams, weatherType.get(weatherIndex), dayType.get(dayIndex));
        System.out.println(result);
        assertTrue(isTossCorrect(teams, weatherIndex, dayIndex, result));
    }

    @Test
    public void testSuperOverMatch()  {
        PlayTournament playTournament = new PlayTournament();
        for(int index = 0; index < count; index++)
            assertTrue(playTournament.playSuperOverMatch());
    }


    @Test
    public void testChaseMatch() {
        PlayTournament playTournament = new PlayTournament();
        for(int index = 0; index < count; index++)
            assertTrue(playTournament.chase4OverMatch());
    }

}
