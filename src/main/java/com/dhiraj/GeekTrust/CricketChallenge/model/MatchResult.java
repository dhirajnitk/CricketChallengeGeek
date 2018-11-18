package com.dhiraj.GeekTrust.CricketChallenge.model;

public class MatchResult {

    private Team winningTeam;
    private Team losingTeam;
    private int ballRemaining = -1;
    private int wicketRemaining = -1;
    private int lossRuns = -1;
    private boolean isDraw;

    public MatchResult(Team winningTeam, Team losingTeam, int wicketRemaining, int ballRemaining){
        this.winningTeam = winningTeam;
        this.losingTeam = losingTeam;
        this.wicketRemaining = wicketRemaining;
        this.ballRemaining = ballRemaining;
    }

    public MatchResult(Team winningTeam, Team losingTeam, int lossRuns){
          this.winningTeam = winningTeam;
          this.losingTeam = losingTeam;
          this.lossRuns = lossRuns;
    }

    public MatchResult(){
        this.isDraw = true;
    }

    public String getWinningTeam() {
        if(winningTeam!= null)
            return winningTeam.getTeamName();
        return null;
    }

    public String getLosingTeam() {
        if(losingTeam != null)
            return losingTeam.getTeamName();
        return null;
    }

    public int getBallRemaining() {
        return ballRemaining;
    }

    public int getLossRuns() {
        return lossRuns;
    }

    public int getWicketRemaining() {
        return wicketRemaining;
    }

    public boolean isDraw(){
        return isDraw;
    }
}
