package com.thoughtworks.gauge.maven.Endpoints;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.Utils.Request;
import com.thoughtworks.gauge.maven.Response.POJO.Fixture;
import org.junit.Assert;

import static com.thoughtworks.gauge.maven.Utils.BaseSteps.*;

public class FixtureIdDelete {
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();
    private Gson gson = new Gson();
    private Request request = new Request();

    @Step("Delete last created fixture by last fixture ID")
    public void deleteFixtureById() throws UnirestException {
        String stringPostBody = (String) dataStore.get(POST_BODY);
        Fixture postBody = gson.fromJson(stringPostBody, Fixture.class);
        String id = postBody.getFixtureId();

        String url = FIXTURE_BY_ID_ENDPOINT + id;
        request.deleteRequest(url);
    }

    @AfterScenario(tags = "CleanupRequired")
    public void cleanUpStep() throws UnirestException {
        FindFixtureById findFixtureById = new FindFixtureById();
        findFixtureById.findLastCreatedId();
        deleteFixtureById();
        int statusCode = (int) dataStore.get(STATUS_CODE);
        Assert.assertEquals("Status code was not 200",200, statusCode);
    }
}
