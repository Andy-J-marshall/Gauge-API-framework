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
    private static final String FIXTURE_BODY = "fixture body";
    private Request request = new Request();
    private Gson gson = new Gson();
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();

    public void postWithFixtureBody(Fixture fixtureBody) throws UnirestException {
        String body = gson.toJson(fixtureBody);
        dataStore.put(FIXTURE_BODY, fixtureBody);
        request.postRequest(FIXTURE_ENDPOINT, body);
    }

    public Fixture createFixtureWithPresetData() {
        FixtureStatus fixtureStatus = FixtureStatus.builder()
                .displayed(true)
                .suspended(false)
                .build();

        Team homeTeam = Team.builder()
                .association("HOME")
                .name("Barcelona")
                .teamId(idGeneratedAsString())
                .build();

        Team awayTeam = Team.builder()
                .association("AWAY")
                .name("Barnet")
                .teamId(idGeneratedAsString())
                .build();

        List<Team> teams = addTeamsToList(homeTeam, awayTeam);

        FootballFullState footballFullState = FootballFullState.builder()
                .homeTeam("Barcelona")
                .awayTeam("Barnet")
                .finished(true)
                .gameTimeInSeconds(5400)
                .period("first half")
                .startDateTime("2018-07-22T10:49:38.655Z")
                .started(true)
                .teams(teams)
                .build();

        return Fixture.builder()
                .footballFullState(footballFullState)
                .fixtureId(idGeneratedAsString())
                .fixtureStatus(fixtureStatus)
                .build();
    }

    public List<Team> addTeamsToList(Team homeTeam, Team awayTeam) {
        List<Team> teams = new ArrayList<>();
        teams.add(homeTeam);
        teams.add(awayTeam);
        return teams;
    }

    public FixtureStatus fixtureStatus(Boolean displayed, Boolean suspended) {
        FixtureStatus fixtureStatus = FixtureStatus.builder()
                .displayed(displayed)
                .suspended(suspended)
                .build();
        return fixtureStatus;
    }

    public Team team(String association, String name) {
        Team team = Team.builder()
                .association(association)
                .name(name)
                .teamId(idGeneratedAsString())
                .build();
        return team;
    }

    public Fixture fixture(FootballFullState footballFullState, FixtureStatus fixtureStatus) {
        Fixture fixture = Fixture.builder()
                .footballFullState(footballFullState)
                .fixtureId(idGeneratedAsString())
                .fixtureStatus(fixtureStatus)
                .build();
        return fixture;
    }

    public FootballFullState footballFullState(String homeTeam, String awayTeam, Boolean finished, int gameTimeInSeconds, String period, String startDateTime, Boolean started, List<Team> team) {
        FootballFullState footballFullState = FootballFullState.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .finished(finished)
                .gameTimeInSeconds(gameTimeInSeconds)
                .period(period)
                .startDateTime(startDateTime)
                .started(started)
                .teams(team)
                .build();
        return footballFullState;
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
