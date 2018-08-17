package com.thoughtworks.gauge.maven.Endpoints;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.Response.POJO.Fixture;
import com.thoughtworks.gauge.maven.Response.POJO.FixtureStatus;
import com.thoughtworks.gauge.maven.Response.POJO.FootballFullState;
import com.thoughtworks.gauge.maven.Response.POJO.Team;
import com.thoughtworks.gauge.maven.Utils.Request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.thoughtworks.gauge.maven.Utils.BaseSteps.FIXTURE_ENDPOINT;

public class StoreNewFixture {
    private static final String FIXTURE_BODY = "fixture body";
    private Request request = new Request();
    private Gson gson = new Gson();
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();


    @Step("Store a new fixture for a completed match with preset data")
    public void storeNewFixtureDefaultValues() throws UnirestException {
        Fixture fixtureBody = createFixtureWithPresetData();
        postWithFixtureBody(fixtureBody);
    }

    @Step("Store a new fixture for a completed match with home team <homeTeam> and away team <awayTeam>")
    public void storeNewFixtureSpecificTeams(String homeTeamName, String awayTeamName) throws UnirestException {
        FixtureStatus fixtureStatus = fixtureStatus(true, false);
        List<Team> teams = addTeamsToList(
                team("HOME", homeTeamName),
                team("AWAY", awayTeamName)
        );

        FootballFullState footballFullState = FootballFullState.builder()
                .homeTeam(homeTeamName)
                .awayTeam(awayTeamName)
                .finished(true)
                .gameTimeInSeconds(5400)
                .period("second half")
                .startDateTime(LocalDateTime.now().toString())
                .started(true)
                .teams(teams)
                .build();

        Fixture fixtureBody = fixture(footballFullState, fixtureStatus);
        postWithFixtureBody(fixtureBody);
    }

    private void postWithFixtureBody(Fixture fixtureBody) throws UnirestException {
        String body = gson.toJson(fixtureBody);
        dataStore.put(FIXTURE_BODY, fixtureBody);
        request.postRequest(FIXTURE_ENDPOINT, body);
    }

    private Fixture createFixtureWithPresetData() {
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

        Fixture fixture = Fixture.builder()
                .footballFullState(footballFullState)
                .fixtureId(idGeneratedAsString())
                .fixtureStatus(fixtureStatus)
                .build();

        return fixture;
    }

    private List<Team> addTeamsToList(Team homeTeam, Team awayTeam) {
        List<Team> teams = new ArrayList<>();
        teams.add(homeTeam);
        teams.add(awayTeam);
        return teams;
    }

    private FixtureStatus fixtureStatus(Boolean displayed, Boolean suspended) {
        FixtureStatus fixtureStatus = FixtureStatus.builder()
                .displayed(displayed)
                .suspended(suspended)
                .build();
        return fixtureStatus;
    }

    private Team team(String association, String name) {
        Team team = Team.builder()
                .association(association)
                .name(name)
                .teamId(idGeneratedAsString())
                .build();
        return team;
    }

    private Fixture fixture(FootballFullState footballFullState, FixtureStatus fixtureStatus) {
        Fixture fixture = Fixture.builder()
                .footballFullState(footballFullState)
                .fixtureId(idGeneratedAsString())
                .fixtureStatus(fixtureStatus)
                .build();
        return fixture;
    }

    private FootballFullState footballFullState(String homeTeam, String awayTeam, Boolean finished, int gameTimeInSeconds, String period, String startDateTime, Boolean started, List<Team> team) {
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
}
