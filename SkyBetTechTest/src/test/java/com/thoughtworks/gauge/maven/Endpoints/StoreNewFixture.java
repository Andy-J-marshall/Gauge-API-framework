package com.thoughtworks.gauge.maven.Endpoints;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.Response.POJO.Fixture;
import com.thoughtworks.gauge.maven.Response.POJO.FixtureStatus;
import com.thoughtworks.gauge.maven.Response.POJO.FootballFullState;
import com.thoughtworks.gauge.maven.Response.POJO.Team;
import com.thoughtworks.gauge.maven.Utils.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.thoughtworks.gauge.maven.Utils.BaseSteps.FIXTURE_ENDPOINT;

public class StoreNewFixture {
    private static final String FIXTURE_BODY = "createFixture body";
    private Request request = new Request();
    private Gson gson = new Gson();
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();

    public void postWithFixtureBody(Fixture fixtureBody) throws UnirestException {
        String body = gson.toJson(fixtureBody);
        dataStore.put(FIXTURE_BODY, fixtureBody);
        request.postRequest(FIXTURE_ENDPOINT, body);
    }

    public Fixture createFixtureWithDefaultData() {
        return Fixture.builder()
                .footballFullState(footballFullStateWithDefaults())
                .fixtureId(idGeneratedAsString())
                .fixtureStatus(fixtureStatusWithDefault())
                .build();
    }

    public Fixture createFixture(FootballFullState footballFullState, FixtureStatus fixtureStatus) {
        return Fixture.builder()
                .footballFullState(footballFullState)
                .fixtureId(idGeneratedAsString())
                .fixtureStatus(fixtureStatus)
                .build();
    }

    public FixtureStatus fixtureStatus(Boolean displayed, Boolean suspended) {
        return FixtureStatus.builder()
                .displayed(displayed)
                .suspended(suspended)
                .build();
    }

    public FixtureStatus fixtureStatusWithDefault() {
        return FixtureStatus.builder()
                .displayed(true)
                .suspended(true)
                .build();
    }

    public List<Team> addTeams(Team homeTeam, Team awayTeam) {
        List<Team> teams = new ArrayList<>();
        teams.add(homeTeam);
        teams.add(awayTeam);
        return teams;
    }

    public List<Team> addTeamsUsingValues(String homeTeam, String awayTeam) {
        List<Team> teams = new ArrayList<>();
        teams.add(team("HOME", homeTeam));
        teams.add(team("AWAY", awayTeam));
        return teams;
    }

    public List<Team> addTeamsWithDefaults() {
        Team homeTeam = homeTeamWithDefault();
        Team awayTeam = awayTeamWithDefault();
        List<Team> teams = new ArrayList<>();
        teams.add(homeTeam);
        teams.add(awayTeam);
        return teams;
    }

    public Team team(String association, String name) {
        return Team.builder()
                .association(association)
                .name(name)
                .teamId(idGeneratedAsString())
                .build();
    }

    public Team homeTeamWithDefault() {
        return Team.builder()
                .association("HOME")
                .name("Barnet")
                .teamId(idGeneratedAsString())
                .build();
    }

    public Team awayTeamWithDefault() {
        return Team.builder()
                .association("AWAY")
                .name("Real Madrid")
                .teamId(idGeneratedAsString())
                .build();
    }

    public FootballFullState footballFullStateWithDefaults() {
        return FootballFullState.builder()
                .homeTeam("Barcelona")
                .awayTeam("Barnet")
                .finished(true)
                .gameTimeInSeconds(5400)
                .period("first half")
                .startDateTime("2018-07-22T10:49:38.655Z")
                .started(true)
                .teams(addTeamsWithDefaults())
                .build();
    }

    public FootballFullState footballFullState(String homeTeam, String awayTeam, Boolean finished, int gameTimeInSeconds, String period, String startDateTime, Boolean started, List<Team> team) {
        return FootballFullState.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .finished(finished)
                .gameTimeInSeconds(gameTimeInSeconds)
                .period(period)
                .startDateTime(startDateTime)
                .started(started)
                .teams(team)
                .build();
    }

    private String idGeneratedAsString() {
        Random random = new Random();
        Integer randomInt = random.nextInt() + 3;
        return randomInt.toString();
    }

    public String calculatePeriod(int timeInMinutes) {
        String period;
        if (timeInMinutes >= 46) {
            period = "Second Half";
        } else {
            period = "First Half";
        }
        return period;
    }

    public Boolean calculateGameFinished(int timeInMinutes) {
        if (timeInMinutes >= 90) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean calculateGameStarted(int timeInMinutes) {
        if (timeInMinutes > 0) {
            return true;
        } else {
            return false;
        }
    }
}
