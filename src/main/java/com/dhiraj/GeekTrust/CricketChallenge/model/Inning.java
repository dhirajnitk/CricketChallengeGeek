package com.dhiraj.GeekTrust.CricketChallenge.model;

import javafx.util.Pair;
import java.io.IOException;
import java.util.*;

public class Inning  implements  Cloneable{

    private Team team;
    private PlayerProbabilityMatrix playerProbabilityMatrix;
    public enum Type { FIRST, SECOMD}
    private Type type;
    private  int overs ;
    private  int runsToWin;
    private int runs;
    private Inning firstInning;
    private  int wicketsLeft;
    private  Pair<Integer,Integer>[] strikeRate ;
    private Set<Integer> outPlayerSet;
    private Map<Boolean,Integer> currentPlayers;
    private  int nextPlayerIndex;
    private int ballRemaining;
    private Commentary commentary;
    private CricketProperties cricketProperties;
    private Random random;

    public Inning(Inning firstInning, Team team, int overs, PlayerProbabilityMatrix playerProbabilityMatrix) throws IOException {
        this(team, overs, playerProbabilityMatrix, Type.SECOMD);
        this.runsToWin = firstInning.runsToWin;
        this.firstInning = firstInning;
        if(firstInning.commentary == null)
            this.commentary = new Commentary(cricketProperties);
        else
            this.commentary = firstInning.commentary;
    }

    public Inning(Team team, int overs, PlayerProbabilityMatrix playerProbabilityMatrix, Type type) throws IOException {
        this.team = team;
        this.overs = overs;
        this.type = type;
        if(team!= null) {
            this.playerProbabilityMatrix = playerProbabilityMatrix;
            this.cricketProperties = new CricketProperties();
            this.commentary = new Commentary(cricketProperties);
            int seed = Integer.valueOf(cricketProperties.getProperty("seed"));
            if(seed == -1)
                this.random = new Random(System.currentTimeMillis());
            else
                this.random  = new Random(seed);
            currentPlayers = new HashMap<>();
            currentPlayers.put(true, 0);
            currentPlayers.put(false, 1);
            outPlayerSet = new HashSet<>();
            if(team.getSize() > 0){
                strikeRate = new Pair[team.getSize()];
                for (int index = 0; index < team.getSize(); index++) {
                    strikeRate[index] = new Pair<>(0, 0);
                }
            }
            wicketsLeft = team.getSize() - 1;
            nextPlayerIndex = 2;
        }

    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    private void swapPlayer(){
        Integer player1Index = currentPlayers.get(true);
        Integer player2Index = currentPlayers.get(false);
        currentPlayers.put(false, player1Index);
        currentPlayers.put(true, player2Index);

    }

    private boolean removeCurrentPlayer(){
        int outPlayerIndex = currentPlayers.remove(true);
        outPlayerSet.add(outPlayerIndex);
        if(nextPlayerIndex == team.getSize())
            return false;
        currentPlayers.put(true, nextPlayerIndex);
        nextPlayerIndex++;
        wicketsLeft--;
        return true;
    }

    private  boolean updateStatistics(int playerIndex, int shotIndex, String ball){
        Integer ballsPlayed = strikeRate[playerIndex].getKey();
        Integer runsScored = strikeRate[playerIndex].getValue();
        String scoreFormat = cricketProperties.getProperty("scoreFormat");
        String outFormat = cricketProperties.getProperty("outFormat");
        boolean matchRunning = true;
        commentary.addCommentary("\n");
        switch(shotIndex){
            case 0:
            case 2:
            case 4:
            case 6:
                runsScored+=shotIndex;
                runs+=shotIndex;
                commentary.addCommentary(String.format(scoreFormat,ball, team.getPlayerName(playerIndex), shotIndex));
                break;
            case 1:
            case 3:
            case 5:
                runsScored+=shotIndex;
                runs+=shotIndex;
                swapPlayer();
                commentary.addCommentary(String.format(scoreFormat,ball, team.getPlayerName(playerIndex), shotIndex));
                break;
            case 7:
                commentary.addCommentary(String.format(outFormat,ball, team.getPlayerName(playerIndex)));
                matchRunning= removeCurrentPlayer();

        }
        ballsPlayed+=1;
        strikeRate[playerIndex] = new Pair<>(ballsPlayed,runsScored);
        return matchRunning;

    }

    private  boolean playShot(String ballIndex){
        int playerIndex= currentPlayers.get(true);
        int score = (int)(random.nextFloat()*100);
        int shotIndex = playerProbabilityMatrix.getPlayerScore(team.getPlayerName(playerIndex), score);
        return updateStatistics(playerIndex, shotIndex, ballIndex);
    }

    private  Pair<Boolean, Integer> playOver(int overIndex){
        for(int ball = 0; ball < 6; ball++){
            if(!playShot(""+overIndex+"."+(ball+1))) {
                return new Pair<>(false, null);
            }
            else if (runsToWin <= runs) {
                return new Pair<>(true, 6 - ball - 1);
            }
        }
        return new Pair<>(null, null);
    }

    public void setTargetScore(int score){
        runsToWin = score;
    }

    public int getRunsToWin() {
        return runsToWin;
    }


    private MatchResult makeTarget()  {
        commentary.addCommentary("Sample commentary\n\n");
        commentary.addCommentary(team.getTeamName()+" innings\n");
        for(int over = 0; over<overs; over++){
            Pair<Boolean, Integer> overResult = playOver(over);
            if(overResult.getKey()!= null  && !overResult.getKey()) {
                commentary.addCommentary(team.getTeamName()+" all out");
                break;
            }
            if(over!=overs-1) {
                commentary.addCommentary("\n");
                swapPlayer();
            }
        }
        setTargetScore(runs +1);
        commentary.addCommentary("\n");
        return null;

    }

    private MatchResult chaseTarget() throws IOException {
        commentary.addCommentary("\n");
        String outputFormat = cricketProperties.getProperty("outputFormat");
        if(firstInning.strikeRate == null)
            commentary.addCommentary("Sample commentary\n\n");
        else
            commentary.addCommentary(team.getTeamName()+" innings\n");
        Pair<Boolean, Integer> overResult;
        for(int over = 0; over<overs; over++){
            if(firstInning.strikeRate == null || overs > 1){
                commentary.addCommentary("\n");
                commentary.addCommentary(String.format(outputFormat, overs - over, runsToWin - runs));
                commentary.addCommentary("\n");
            }
            overResult = playOver(over);
            if(overResult.getKey()!= null) {
                if (overResult.getKey()) {
                    commentary.addCommentary(team.getTeamName()+" wins");
                    ballRemaining =  overResult.getValue()+ (overs - over -1)*6;

                }
                else {
                    commentary.addCommentary(team.getTeamName()+" all out");
                }
                break;
            }
            else if(runs == runsToWin - 1 ){
                commentary.addCommentary(cricketProperties.getProperty("tieFormat"));
                break;
            }
            else if (over == overs -1){
                commentary.addCommentary(team.getTeamName()+" loses");
                break;
            }
            if(over!=overs-1) {
                commentary.addCommentary("\n");
                swapPlayer();
            }
        }

        commentary.cleanUpCommentary();
        return getWinner();

    }

    private MatchResult getWinner(){
        MatchResult result = null;
        if(type != Type.SECOMD){
            return result;
        }
        if(runs >= runsToWin){
            result = new MatchResult(team, firstInning.team, wicketsLeft, ballRemaining);
        }
        else if(runs < runsToWin -1){
            result = new MatchResult(firstInning.team, team, runsToWin - runs - 1);
        }
        else{
            result = new MatchResult();
        }
        return result;

    }

    public MatchResult playInning() throws IOException {
        if(type == Type.FIRST)
            return makeTarget();
        else
            return chaseTarget();
    }

     Pair<Integer, Integer> getStrikeRate(int index) {
        return strikeRate[index];
    }
     //deep copy in package specific getters not worth.
     Inning getFirstInning() throws CloneNotSupportedException {
        return (Inning) firstInning.clone();
    }

     Team getTeam() throws CloneNotSupportedException {
        if(team!= null)
            return (Team) team.clone();
        return null;
    }

    boolean isOutPlayer(int index){
        return outPlayerSet.contains(index);
    }


    boolean isCurrentPlayer(int index){
        return currentPlayers.containsValue(index);
    }

}
