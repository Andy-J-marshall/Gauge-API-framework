package com.thoughtworks.gauge.maven.steps;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.endpoints.GetFixture;
import com.thoughtworks.gauge.maven.utils.Request;
import org.junit.Assert;

import static com.thoughtworks.gauge.maven.utils.BaseSteps.FIXTURE_BY_ID_ENDPOINT;
import static com.thoughtworks.gauge.maven.utils.BaseSteps.STATUS_CODE;

public class FixtureIdDeleteSteps {
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();
    private Request request = new Request();
    private GetFixture getFixture = new GetFixture();

    @Step("Delete last created fixture by last fixture ID")
    public void deleteFixtureById() throws UnirestException {
        String fixtureId = getFixture.findFixtureId();
        String url = FIXTURE_BY_ID_ENDPOINT + fixtureId;
        request.deleteRequest(url);
    }

    @AfterScenario(tags = "CleanupAfterTest")
    public void cleanUpStep() throws UnirestException {
        FindFixtureByIdSteps findFixtureByIdSteps = new FindFixtureByIdSteps();
        findFixtureByIdSteps.findLastCreatedId();
        deleteFixtureById();
        int statusCode = (int) dataStore.get(STATUS_CODE);
        Assert.assertEquals("Fixture cleanup was unsuccessful", 200, statusCode);
    }
}
