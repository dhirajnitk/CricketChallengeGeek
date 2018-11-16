package com.dhiraj.GeekTrust.service;

import com.dhiraj.GeekTrust.util.Team;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ScoreCard {

    private String scoreFileName;
    private FileWriter scoreFile;
    private PrintWriter scorePrint;
    private Result result;
    private Inning secondInning;
    private CricketProperties cricketProperties;

    public ScoreCard(Result result,Inning secondInning) throws IOException {
        this.result = result;
        this.secondInning = secondInning;
        this.cricketProperties = Commentary.getCricketProperties();
        scoreFileName = cricketProperties.getProperty("scoreCardFileName");
        scoreFile = new FileWriter(scoreFileName,false);
        if(scorePrint== null)
            scorePrint = new PrintWriter(scoreFile);
    }

    private void commonWriteScoreCard(Inning inning){

        Team team = inning.getTeam();
        for(int index = 0; index <team.getSize(); index++){
            if(inning.isOutPlayer(index))
                scorePrint.println(team.getPlayerName(index)+" - "+ inning.getStrikeRate(index).getValue()+"("+ inning.getStrikeRate(index).getKey()+" balls)");
            else if(inning.isCurrentPlayer(index))
                scorePrint.println(team.getPlayerName(index)+" - "+inning.getStrikeRate(index).getValue()+"*("+ inning.getStrikeRate(index).getKey()+" balls)");
        }

    }

    private void publishScoreCard() throws IOException {

        if (result.isDraw()) {
            scorePrint.println(cricketProperties.getProperty("tieFormat"));
        }
        else {
            if (result.getWinningTeam() != null) {
                String winFormat;
                if (result.getLossRuns() > 0) {
                    winFormat = result.getWinningTeam() + " won by %d runs";
                    scorePrint.println(String.format(winFormat, result.getLossRuns()));
                } else {
                    if(secondInning.getFirstInning().getTeam() != null) {
                        winFormat = result.getWinningTeam() + " won with %d balls remaining";
                        scorePrint.println(String.format(winFormat, result.getBallRemaining()));
                    }
                    else{
                        winFormat = result.getWinningTeam() + " "+cricketProperties.getProperty("winFormat");
                        scorePrint.println(String.format(winFormat, result.getWicketRemaining(), result.getBallRemaining()));
                    }

                }
            } else {
                String lossFormat = result.getLosingTeam() + " "+cricketProperties.getProperty("lossFormat");
                scorePrint.println(String.format(lossFormat, result.getLossRuns()));
            }
        }

        if(secondInning.getFirstInning().getTeam() != null) {
            scorePrint.println();
            scorePrint.println(secondInning.getFirstInning().getTeam().getTeamName());
            commonWriteScoreCard(secondInning.getFirstInning());
        }
            scorePrint.println();
        if(secondInning.getFirstInning().getTeam() != null) {
            scorePrint.println(secondInning.getTeam().getTeamName());
        }
            commonWriteScoreCard(secondInning);
            cleanUpScoreCard();
        }

    private void cleanUpScoreCard() throws IOException {
        scorePrint.close();
        scoreFile.close();
    }


    public void printScoreCard() throws IOException {
        publishScoreCard();
        List<String> lines = Files.readAllLines(Paths.get(scoreFileName));
        lines.forEach(System.out::println);
        System.out.println();

    }

}
