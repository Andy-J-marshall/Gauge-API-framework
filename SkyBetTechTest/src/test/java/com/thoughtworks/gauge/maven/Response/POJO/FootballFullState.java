package com.thoughtworks.gauge.maven.Response.POJO;

import java.util.List;

public class FootballFullState {
    private String period;
    private List<Team> teams = null;
    private String awayTeam;
    private Boolean finished;
    private Boolean started;
    private List<Object> redCards = null;
    private List<Object> possibles = null;
    private Integer gameTimeInSeconds;
    private List<Object> corners = null;
    private String startDateTime;
    private String homeTeam;
    private List<Object> yellowCards = null;
    private List<Goal> goals = null;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public List<Object> getRedCards() {
        return redCards;
    }

    public void setRedCards(List<Object> redCards) {
        this.redCards = redCards;
    }

    public List<Object> getPossibles() {
        return possibles;
    }

    public void setPossibles(List<Object> possibles) {
        this.possibles = possibles;
    }

    public Integer getGameTimeInSeconds() {
        return gameTimeInSeconds;
    }

    public void setGameTimeInSeconds(Integer gameTimeInSeconds) {
        this.gameTimeInSeconds = gameTimeInSeconds;
    }

    public List<Object> getCorners() {
        return corners;
    }

    public void setCorners(List<Object> corners) {
        this.corners = corners;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public List<Object> getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(List<Object> yellowCards) {
        this.yellowCards = yellowCards;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

}