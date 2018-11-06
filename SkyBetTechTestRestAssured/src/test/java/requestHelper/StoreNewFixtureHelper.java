package requestHelper;

import pojos.createFixture.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StoreNewFixtureHelper {

    public String createFixtureAndReturnFixtureId() {
        PostRequests postRequests = new PostRequests();
        Fixture fixture = createFixtureBody();
        postRequests.postNewFixtureRequest(fixture, 200);
        return fixture.getFixtureId();
    }

    private Fixture createFixtureBody() {
        return Fixture.builder()
                .fixtureId(randomlyGeneratedIdAsString())
                .fixtureStatus(fixtureStatus())
                .footballFullState(footballFullState())
                .build();
    }

    public Fixture createFixtureBody(FootballFullState footballFullState, FixtureStatus fixtureStatus) {
        return Fixture.builder()
                .fixtureId(randomlyGeneratedIdAsString())
                .fixtureStatus(fixtureStatus)
                .footballFullState(footballFullState)
                .build();
    }

    private FixtureStatus fixtureStatus(Boolean displayed, Boolean suspended) {
        return FixtureStatus.builder()
                .displayed(displayed)
                .suspended(suspended)
                .build();
    }

    private FixtureStatus fixtureStatus() {
        return FixtureStatus.builder()
                .displayed(true)
                .suspended(true)
                .build();
    }

    private List<Team> addTeams(Team homeTeam, Team awayTeam) {
        List<Team> teams = new ArrayList<Team>();
        teams.add(homeTeam);
        teams.add(awayTeam);
        return teams;
    }

    private List<Team> addTeams(String homeTeam, String awayTeam) {
        List<Team> teams = new ArrayList<Team>();
        teams.add(team("HOME", homeTeam));
        teams.add(team("AWAY", awayTeam));
        return teams;
    }

    private List<Team> addTeams() {
        Team homeTeam = homeTeam();
        Team awayTeam = awayTeam();
        return addTeams(homeTeam, awayTeam);
    }

    private Team team(String association, String name) {
        return Team.builder()
                .association(association)
                .name(name)
                .teamId(association.toUpperCase())
                .build();
    }

    private Team homeTeam() {
        return Team.builder()
                .association("HOME")
                .name("Barnet")
                .teamId("HOME")
                .build();
    }

    private Team awayTeam() {
        return Team.builder()
                .association("AWAY")
                .name("Real Madrid")
                .teamId("AWAY")
                .build();
    }

    private List<Goal> addGoals() {
        List<Goal> goals = new ArrayList<Goal>();
        goals.add(goal());
        goals.add(goal());
        return goals;
    }

    private List<Goal> addGoals(Goal goal) {
        List<Goal> goals = new ArrayList<Goal>();
        goals.add(goal);
        return goals;
    }

    private List<Goal> addGoals(int numberOfGoals) {
        List<Goal> goals = new ArrayList<Goal>();
        for (int i = 0; i < numberOfGoals; i++) {
            goals.add(goal());
        }
        return goals;
    }

    private Goal goal() {
        return Goal.builder()
                .clockTime(randomlyGeneratedTimeInSeconds())
                .confirmed(true)
                .id(randomlyGeneratedIdAsString())
                .ownGoal(false)
                .penalty(false)
                .period("First Half")
                .playerId(randomlyGeneratedIdAsString())
                .teamId("HOME")
                .build();
    }

    private Goal goal(int clockTime, boolean confirmed, boolean ownGoal, boolean penalty, String period, String teamId) {
        return Goal.builder()
                .clockTime(clockTime)
                .confirmed(confirmed)
                .id(randomlyGeneratedIdAsString())
                .ownGoal(ownGoal)
                .penalty(penalty)
                .period(period)
                .playerId(randomlyGeneratedIdAsString())
                .teamId(teamId)
                .build();
    }

    private FootballFullState footballFullState() {
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

    private FootballFullState footballFullState(String homeTeam, String awayTeam, Boolean finished, int gameTimeInSeconds, String period, String startDateTime, Boolean started, List<Team> team, List<Goal> goal) {
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

    private int randomlyGeneratedTimeInSeconds() {
        Random random = new Random();
        return random.nextInt(5399) + 1;
    }

    private String calculatePeriod(int timeInMinutes) {
        return timeInMinutes >= 46 ? "second half" : "first half";
    }

    private Boolean calculateGameFinished(int timeInMinutes) {
        return timeInMinutes >= 90;
    }

    private Boolean calculateGameStarted(int timeInMinutes) {
        return timeInMinutes > 0;
    }
}
