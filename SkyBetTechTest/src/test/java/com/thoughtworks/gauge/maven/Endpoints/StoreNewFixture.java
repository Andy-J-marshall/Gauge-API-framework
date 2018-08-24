package com.thoughtworks.gauge.maven.Endpoints;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.Response.POJO.*;
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

    public Fixture createFixture() {
        return Fixture.builder()
                .footballFullState(footballFullState())
                .fixtureId(randomlyGeneratedIdAsString())
                .fixtureStatus(fixtureStatus())
                .build();
    }

    public Fixture createFixture(FootballFullState footballFullState, FixtureStatus fixtureStatus) {
        return Fixture.builder()
                .footballFullState(footballFullState)
                .fixtureId(randomlyGeneratedIdAsString())
                .fixtureStatus(fixtureStatus)
                .build();
    }

    public FixtureStatus fixtureStatus(Boolean displayed, Boolean suspended) {
        return FixtureStatus.builder()
                .displayed(displayed)
                .suspended(suspended)
                .build();
    }

    public FixtureStatus fixtureStatus() {
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

    public List<Team> addTeams(String homeTeam, String awayTeam) {
        List<Team> teams = new ArrayList<>();
        teams.add(team("HOME", homeTeam));
        teams.add(team("AWAY", awayTeam));
        return teams;
    }

    public List<Team> addTeams() {
        Team homeTeam = homeTeam();
        Team awayTeam = awayTeam();
        return addTeams(homeTeam, awayTeam);
    }

    public Team team(String association, String name) {
        return Team.builder()
                .association(association)
                .name(name)
                .teamId(randomlyGeneratedIdAsString())
                .build();
    }

    public Team homeTeam() {
        return Team.builder()
                .association("HOME")
                .name("Barnet")
                .teamId(randomlyGeneratedIdAsString())
                .build();
    }

    public Team awayTeam() {
        return Team.builder()
                .association("AWAY")
                .name("Real Madrid")
                .teamId(randomlyGeneratedIdAsString())
                .build();
    }

    public List<Goal> addGoals() {
        List<Goal> goals = new ArrayList<>();
        goals.add(goal());
        goals.add(goal());
        return goals;
    }

    public List<Goal> addGoals(Goal goal) {
        List<Goal> goals = new ArrayList<>();
        goals.add(goal);
        return goals;
    }

    public List<Goal> addGoals(int numberOfGoals) {
        List<Goal> goals = new ArrayList<>();
        for (int i=0; i<numberOfGoals; i++) {
            goals.add(goal());
        }
        return goals;
    }

    public Goal goal() {
        return Goal.builder()
                .clockTime(randomlyGeneratedTimeInSeconds())
                .confirmed(true)
                .id(randomlyGeneratedId())
                .ownGoal(false)
                .penalty(false)
                .period("First Half")
                .playerId(randomlyGeneratedId())
                .teamId(randomlyGeneratedIdAsString())
                .build();
    }

    public Goal goal(int clockTime, boolean confirmed, boolean ownGoal, boolean penalty, String period) {
        return Goal.builder()
                .clockTime(clockTime)
                .confirmed(confirmed)
                .id(randomlyGeneratedId())
                .ownGoal(ownGoal)
                .penalty(penalty)
                .period(period)
                .playerId(randomlyGeneratedId())
                .teamId(randomlyGeneratedIdAsString())
                .build();
    }

    public FootballFullState footballFullState() {
        return FootballFullState.builder()
                .homeTeam("Barcelona")
                .awayTeam("Barnet")
                .finished(true)
                .gameTimeInSeconds(5400)
                .period("First Half")
                .startDateTime("2018-07-22T10:49:38.655Z")
                .started(true)
                .teams(addTeams())
                .goals(addGoals())
                .build();
    }

    public FootballFullState footballFullState(String homeTeam, String awayTeam, Boolean finished, int gameTimeInSeconds, String period, String startDateTime, Boolean started, List<Team> team, List<Goal> goal) {
        return FootballFullState.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .finished(finished)
                .gameTimeInSeconds(gameTimeInSeconds)
                .period(period)
                .startDateTime(startDateTime)
                .started(started)
                .teams(team)
                .goals(goal)
                .build();
    }

    private Integer randomlyGeneratedId() {
        Random random = new Random();
        return random.nextInt(90000) + 90003;
    }

    private String randomlyGeneratedIdAsString() {
        return randomlyGeneratedId().toString();
    }

//    private int randomlyGeneratedId() {
//        Random random = new Random();
//        return random.nextInt() + 3;
//    }
//
//    private String randomlyGeneratedIdAsString() {
//        int id = randomlyGeneratedId();
//        Integer integer = id;
//        return integer.toString();
//    }

    private int randomlyGeneratedTimeInSeconds() {
        Random random = new Random();
        return random.nextInt(5399) + 1;
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
