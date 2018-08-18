package com.thoughtworks.gauge.maven.Steps;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.maven.Endpoints.StoreNewFixture;
import com.thoughtworks.gauge.maven.Response.POJO.Fixture;
import com.thoughtworks.gauge.maven.Response.POJO.FixtureStatus;
import com.thoughtworks.gauge.maven.Response.POJO.FootballFullState;
import com.thoughtworks.gauge.maven.Response.POJO.Team;

import java.time.LocalDateTime;
import java.util.List;

public class StoreNewFixtureSteps {

    private StoreNewFixture storeNewFixture = new StoreNewFixture();

    @Step("Store a new fixture for a completed match with preset data")
    public void storeNewFixtureDefaultValues() throws UnirestException {
        Fixture fixtureBody = storeNewFixture.createFixtureWithPresetData();
        storeNewFixture.postWithFixtureBody(fixtureBody);
    }

    @Step("Store a new fixture for a completed match with home team <homeTeam> and away team <awayTeam>")
    public void storeNewFixtureSpecificTeams(String homeTeamName, String awayTeamName) throws UnirestException {
        FixtureStatus fixtureStatus = storeNewFixture.fixtureStatus(true, false);
        List<Team> teams = storeNewFixture.addTeamsToList(
                storeNewFixture.team("HOME", homeTeamName),
                storeNewFixture.team("AWAY", awayTeamName)
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

        Fixture fixtureBody = storeNewFixture.fixture(footballFullState, fixtureStatus);
        storeNewFixture.postWithFixtureBody(fixtureBody);
    }

    @Step("Store a new fixture for a match between  home team <homeTeam> and away team <awayTeam> in the minute <timeInMinutes>")
    public void storeNewFixtureIncompleteMatch(String homeTeamName, String awayTeamName, int timeInMinutes) throws UnirestException {
        FixtureStatus fixtureStatus = storeNewFixture.fixtureStatus(true, false);
        List<Team> teams = storeNewFixture.addTeamsToList(
                storeNewFixture.team("HOME", homeTeamName),
                storeNewFixture.team("AWAY", awayTeamName));

        FootballFullState footballFullState = FootballFullState.builder()
                .homeTeam(homeTeamName)
                .awayTeam(awayTeamName)
                .finished(storeNewFixture.calculateGameFinished(timeInMinutes))
                .gameTimeInSeconds(timeInMinutes * 60)
                .period(storeNewFixture.calculatePeriod(timeInMinutes))
                .startDateTime(LocalDateTime.now().toString())
                .started(storeNewFixture.calculateGameStarted(timeInMinutes))
                .teams(teams)
                .build();

        Fixture fixtureBody = storeNewFixture.fixture(footballFullState, fixtureStatus);
        storeNewFixture.postWithFixtureBody(fixtureBody);
    }
}
