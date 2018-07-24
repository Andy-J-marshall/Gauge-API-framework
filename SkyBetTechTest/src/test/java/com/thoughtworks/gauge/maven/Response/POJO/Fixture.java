package com.thoughtworks.gauge.maven.Response.POJO;

public class Fixture {
    private FootballFullState footballFullState;
    private String fixtureId;
    private FixtureStatus fixtureStatus;

    public FootballFullState getFootballFullState() {
        return footballFullState;
    }

    public void setFootballFullState(FootballFullState footballFullState) {
        this.footballFullState = footballFullState;
    }

    public String getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(String fixtureId) {
        this.fixtureId = fixtureId;
    }

    public FixtureStatus getFixtureStatus() {
        return fixtureStatus;
    }

    public void setFixtureStatus(FixtureStatus fixtureStatus) {
        this.fixtureStatus = fixtureStatus;
    }
}