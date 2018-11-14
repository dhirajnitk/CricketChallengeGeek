package com.dhiraj.GeekTrust;
import com.dhiraj.GeekTrust.client.PlayTournament;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

@FixMethodOrder(MethodSorters.DEFAULT)
public final class PlayTournamentTest {

    @Test
    public void testToss()  {

        PlayTournament playTournament = new PlayTournament();
        System.out.println(playTournament.getTossResult());

    }

    @Test
    public void testSuperOverMatch() throws IOException {
        PlayTournament playTournament = new PlayTournament();
        System.out.println(playTournament.playSuperOverMatch());
    }


    @Test
    public void testChaseMatch() throws IOException {
        PlayTournament playTournament = new PlayTournament();
        System.out.println(playTournament.chase4OverMatch());
    }

}
