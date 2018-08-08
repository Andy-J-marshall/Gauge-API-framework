package com.thoughtworks.gauge.maven.Endpoints;

import com.google.gson.Gson;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.Response.POJO.Fixture;
import com.thoughtworks.gauge.maven.Utils.Request;

import static com.thoughtworks.gauge.maven.Utils.BaseSteps.FIXTURE_BY_ID_ENDPOINT;
import static com.thoughtworks.gauge.maven.Utils.BaseSteps.POST_BODY;

public class FindFixtureById {
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();
    private Gson gson = new Gson();
    private Request request = new Request();


    @Step("Find fixture by ID <Id>")
    public void findFixtureById(String id) throws UnirestException {
        String url = FIXTURE_BY_ID_ENDPOINT + id;
        request.getRequest(url);
    }

    @Step("Find the most recently created fixture by ID")
    public void findLastCreatedId() throws UnirestException {
        String stringPostBody = (String) dataStore.get(POST_BODY);
        Fixture postBody = gson.fromJson(stringPostBody, Fixture.class);
        String id = postBody.getFixtureId();
        findFixtureById(id);
    }
}
