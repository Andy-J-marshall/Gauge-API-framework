package com.thoughtworks.gauge.maven.Endpoints;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.maven.Utils.Request;
import com.thoughtworks.gauge.maven.Response.POJO.Fixture;
import com.thoughtworks.gauge.maven.Response.POJO.FixtureStatus;
import com.thoughtworks.gauge.maven.Response.POJO.FootballFullState;
import com.thoughtworks.gauge.maven.Response.POJO.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.thoughtworks.gauge.maven.Utils.BaseSteps.FIXTURE_ENDPOINT;

public class StoreNewFixture {
    private Request request = new Request();
    private Random random = new Random();

    @Step("Store a new fixture")
    public void storeNewFixture() throws UnirestException {
        Gson gson = new Gson();
        String url = FIXTURE_ENDPOINT;
        Fixture fixtureBody = createFixtureWithPresetData();
        String body = gson.toJson(fixtureBody);
        request.postRequest(url, body);
    }

    private Fixture createFixtureWithPresetData() {
        FixtureStatus fixtureStatus = new FixtureStatus();
        fixtureStatus.setDisplayed(true);
        fixtureStatus.setSuspended(true);

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
