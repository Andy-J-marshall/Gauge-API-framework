package com.thoughtworks.gauge.maven.Endpoints;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.Response.POJO.Fixture;
import com.thoughtworks.gauge.maven.Utils.Request;
import org.junit.Assert;

import static com.thoughtworks.gauge.maven.Utils.BaseSteps.*;

public class GetFixtures {
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();
    private Gson gson = new Gson();
    private Request request = new Request();

    @Step("Get all fixtures")
    public void getFixtures() throws UnirestException {
        String url = GET_FIXTURES_ENDPOINT;
        request.getRequest(url);
    }

    @Step("Assert 3 fixtures are returned in the response and they have a fixtureId")
    public void assertFixtureId() {
        Fixture[] lastResponse = (Fixture[]) dataStore.get(RESPONSE_BODY);
        Assert.assertEquals(3, lastResponse.length);

        for (Fixture fixture : lastResponse) {
            String fixtureId = fixture.getFixtureId();
            Assert.assertNotNull(fixtureId);
            Assert.assertNotEquals("", fixtureId);
        }
    }

    @Step("Assert the last created fixture has team ID of HOME in the first object")
    public void assertTeamIdInLastFixture() {
        Fixture[] lastResponse = (Fixture[]) dataStore.get(RESPONSE_BODY);
        String stringPostBody = (String) dataStore.get(POST_BODY);
        Fixture postBody = gson.fromJson(stringPostBody, Fixture.class);
        String fixtureId = postBody.getFixtureId();

        for (Fixture fixture : lastResponse) {
            if (fixtureId.equals(fixture.getFixtureId())) {
                String homeTeam = fixture.getFootballFullState().getTeams().get(0).getTeamId();
                Assert.assertEquals("First object in Teams list does not have a TeamId value of HOME", "HOME", homeTeam);
            }
        }
    }

    @Step("Check the last created fixture ID no longer exists")
    public void assertFixtureIdDeleted() throws UnirestException {
        Fixture[] lastResponse = (Fixture[]) dataStore.get(RESPONSE_BODY);
        String stringPostBody = (String) dataStore.get(POST_BODY);
        Fixture postBody = gson.fromJson(stringPostBody, Fixture.class);
        String fixtureId = postBody.getFixtureId();

        for (Fixture fixture : lastResponse) {
            String responseFixtureId = fixture.getFixtureId();
            if (responseFixtureId.equals(fixtureId)) {
                Assert.fail();
                Gauge.writeMessage(String.format("fixtureId %s has not been deleted correctly", fixtureId));
            }
        }
    }
}

