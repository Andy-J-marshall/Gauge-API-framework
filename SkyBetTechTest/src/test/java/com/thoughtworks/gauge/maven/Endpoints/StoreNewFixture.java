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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.thoughtworks.gauge.maven.Utils.BaseSteps.FIXTURE_ENDPOINT;

public class StoreNewFixture {
    public static final String FIXTURE_BODY = "fixture body";
    private Request request = new Request();
    private Gson gson = new Gson();
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();

    @Step("Store a new fixture for a completed match with preset data")
    public void storeNewFixtureDefaultValues() throws UnirestException {
        Fixture fixtureBody = createFixtureWithPresetData();
        String body = gson.toJson(fixtureBody);
        dataStore.put(FIXTURE_BODY, fixtureBody);
        request.postRequest(FIXTURE_ENDPOINT, body);
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

    private Fixture createFixtureWithPresetData() {
        FixtureStatus fixtureStatus = fixtureStatus(true, true);
        Team homeTeam = team("HOME", "Barcelona");
        Team awayTeam = team("AWAY", "Barnet");

        List<Team> teams = new ArrayList<>();
        teams.add(homeTeam);
        teams.add(awayTeam);

        FootballFullState footballFullState = footballFullState("Barcelona", "Barnet", true, 5400, "first half", "2018-07-22T10:49:38.655Z", true, teams);

        return fixture(footballFullState, fixtureStatus);
    }

    private String idGeneratedAsString() {
        Random random = new Random();
        Integer randomInt = random.nextInt() + 3;
        return randomInt.toString();
    }
}
