package com.thoughtworks.gauge.maven.steps;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.maven.endpoints.StoreNewFixture;
import com.thoughtworks.gauge.maven.responses.Fixture;
import com.thoughtworks.gauge.maven.responses.FixtureStatus;
import com.thoughtworks.gauge.maven.responses.FootballFullState;

import java.time.LocalDateTime;

public class StoreNewFixtureSteps {

    private StoreNewFixture storeNewFixture = new StoreNewFixture();

    @Step("Store a new fixture for a completed match with preset data")
    public void storeNewFixtureDefaultValues() throws UnirestException {
        Fixture fixtureBody = storeNewFixture.createFixture();
        storeNewFixture.postWithFixtureBody(fixtureBody);
    }

    @Step("Store a new fixture for a completed match with home team <homeTeam> and away team <awayTeam>")
    public void storeNewFixtureSpecificTeams2(String homeTeamName, String awayTeamName) throws UnirestException {
        FootballFullState footballFullState = storeNewFixture.footballFullState();
        footballFullState.setHomeTeam(homeTeamName);
        footballFullState.setAwayTeam(awayTeamName);
        footballFullState.setTeams(storeNewFixture.addTeams(homeTeamName, awayTeamName));
        FixtureStatus fixtureStatus = storeNewFixture.fixtureStatus();
        storeNewFixture.postWithFixtureBody(storeNewFixture.createFixture(footballFullState, fixtureStatus));
    }

    @Step("Store a new Fixture for a match between home team <homeTeam> and away team <awayTeam> in the minute <timeInMinutes>")
    public void storeNewFixtureIncompleteMatch(String homeTeamName, String awayTeamName, int timeInMinutes) throws UnirestException {
        FootballFullState footballFullState = storeNewFixture.footballFullState();
        footballFullState.setHomeTeam(homeTeamName);
        footballFullState.setAwayTeam(awayTeamName);
        footballFullState.setTeams(storeNewFixture.addTeams(homeTeamName, awayTeamName));
        footballFullState.setStarted(storeNewFixture.calculateGameStarted(timeInMinutes));
        footballFullState.setStartDateTime(LocalDateTime.now().toString());
        footballFullState.setPeriod(storeNewFixture.calculatePeriod(timeInMinutes));
        footballFullState.setGameTimeInSeconds(timeInMinutes * 60);
        footballFullState.setFinished(storeNewFixture.calculateGameFinished(timeInMinutes));
        FixtureStatus fixtureStatus = storeNewFixture.fixtureStatus();
        storeNewFixture.postWithFixtureBody(storeNewFixture.createFixture(footballFullState, fixtureStatus));
    }

    @Step("Store a new fixture for a completed match with <numberOfGoals> goals")
    public void storeNewFixtureSpecificTeams2(int numberOfGoals) throws UnirestException {
        FootballFullState footballFullState = storeNewFixture.footballFullState();
        footballFullState.setGoals(storeNewFixture.addGoals(numberOfGoals));
        FixtureStatus fixtureStatus = storeNewFixture.fixtureStatus();
        storeNewFixture.postWithFixtureBody(storeNewFixture.createFixture(footballFullState, fixtureStatus));
    }
}
