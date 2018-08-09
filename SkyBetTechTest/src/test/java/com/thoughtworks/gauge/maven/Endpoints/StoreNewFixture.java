package com.thoughtworks.gauge.maven.Endpoints;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.thoughtworks.gauge.Step;
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
    public static final String FIXTURE_BODY = "fixture body";
    private Request request = new Request();
    private Random random = new Random();
    private Gson gson = new Gson();
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();


    @Step("Store a new fixture")
    public void storeNewFixtureDefaultValues() throws UnirestException {
        Fixture fixtureBody = createFixtureWithPresetData();
        String body = gson.toJson(fixtureBody);
        dataStore.put(FIXTURE_BODY, fixtureBody);
        request.postRequest(FIXTURE_ENDPOINT, body);
    }

    private FixtureStatus fixtureStatus(Boolean displayed, Boolean suspended) {
        FixtureStatus fixtureStatus = new FixtureStatus();
        fixtureStatus.setDisplayed(displayed);
        fixtureStatus.setSuspended(suspended);
        return fixtureStatus;
    }

    private Team team(String association, String name, String teamId) {
        Team team = new Team();
        team.setAssociation(association);
        team.setName(name);
        team.setTeamId(teamId);
        return team;
    }

    private Fixture createFixtureWithPresetData() {
        FixtureStatus fixtureStatus = fixtureStatus(true, true);
//        Team homeTeam = team("HOME", "Barcelona", "HOME");
//        Team awayTeam = team()

        Team homeTeam = new Team();
        homeTeam.setAssociation("HOME");
        homeTeam.setName("Barcelona");
        homeTeam.setTeamId("HOME");

        Team awayTeam = new Team();
        awayTeam.setAssociation("AWAY");
        awayTeam.setName("Barnet");
        awayTeam.setTeamId("AWAY");

        List<Team> team = new ArrayList<>();
        team.add(homeTeam);
        team.add(awayTeam);

        FootballFullState footballFullState = new FootballFullState();
        footballFullState.setHomeTeam("Barcelona");
        footballFullState.setAwayTeam("Barnet");
        footballFullState.setFinished(true);
        footballFullState.setGameTimeInSeconds(5400);
        footballFullState.setPeriod("first half");
        footballFullState.setStartDateTime("2018-07-22T10:49:38.655Z");
        footballFullState.setStarted(true);
        footballFullState.setTeams(team);

        Fixture fixture = new Fixture();
        fixture.setFootballFullState(footballFullState);
        fixture.setFixtureId(String.valueOf(3 + random.nextInt(1000)));
        fixture.setFixtureStatus(fixtureStatus);

        return fixture;
    }
}
