package com.thoughtworks.gauge.maven.steps;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.endpoints.GetFixture;
import com.thoughtworks.gauge.maven.responses.Fixture;
import com.thoughtworks.gauge.maven.utils.Request;
import org.junit.Assert;

import static com.thoughtworks.gauge.maven.utils.BaseSteps.GET_FIXTURES_ENDPOINT;
import static com.thoughtworks.gauge.maven.utils.BaseSteps.RESPONSE_BODY;

public class GetFixtureSteps {
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();
    private Request request = new Request();
    private GetFixture getFixture = new GetFixture();

    @Step("Get all fixtures")
    public void getFixtures() throws UnirestException {
        String url = GET_FIXTURES_ENDPOINT;
        request.getRequest(url);
    }

    @Step("Assert 3 fixtures are returned in the response and they have a fixtureId")
    public void assertFixtureId() {
        Fixture[] lastResponse = (Fixture[]) dataStore.get(RESPONSE_BODY);
        Assert.assertEquals("Three fixtures were expected in the response", 3, lastResponse.length);

        for (Fixture fixture : lastResponse) {
            String fixtureId = fixture.getFixtureId();
            Assert.assertNotNull(fixtureId);
            Assert.assertNotEquals("", fixtureId);
        }
    }

    @Step("Assert the last created Fixture has team ID of HOME in the first object")
    public void assertTeamIdInLastFixture() {
        Fixture[] lastResponse = (Fixture[]) dataStore.get(RESPONSE_BODY);
        String fixtureId = getFixture.findFixtureId();

        for (Fixture fixture : lastResponse) {
            if (fixtureId.equals(fixture.getFixtureId())) {
                String homeTeam = fixture.getFootballFullState().getTeams().get(0).getTeamId();
                Assert.assertEquals("First object in Teams list does not have a TeamId value of HOME", "HOME", homeTeam);
            }
        }
    }

    @Step("Check the last created fixture ID no longer exists")
    public void assertFixtureIdDeleted() {
        Fixture[] lastResponse = (Fixture[]) dataStore.get(RESPONSE_BODY);
        String fixtureId = getFixture.findFixtureId();

        for (Fixture fixture : lastResponse) {
            if (fixtureId.equals(fixture.getFixtureId())) {
                Assert.fail();
                Gauge.writeMessage(String.format("fixtureId %s has not been deleted correctly", fixtureId));
            }
        }
    }

    @Step("Check there are <numberOfGoals> goals in the last created fixture ID")
    public void assertNumberOfGoals(int numberOfGoals) {
        Fixture[] lastResponse = (Fixture[]) dataStore.get(RESPONSE_BODY);
        String fixtureId = getFixture.findFixtureId();

        for (Fixture fixture : lastResponse) {
            if (fixtureId.equals(fixture.getFixtureId())) {
                Assert.assertEquals("Number of goals is not correct", numberOfGoals, fixture.getFootballFullState().getGoals().size());
            }
        }
    }
}

