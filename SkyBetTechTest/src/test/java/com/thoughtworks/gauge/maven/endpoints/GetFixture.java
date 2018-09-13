package com.thoughtworks.gauge.maven.endpoints;

import com.google.gson.Gson;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.responses.Fixture;

import static com.thoughtworks.gauge.maven.utils.BaseSteps.POST_BODY;

public class GetFixture {
    private Gson gson = new Gson();
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();

    public String findFixtureId() {
        String stringPostBody = (String) dataStore.get(POST_BODY);
        Fixture postBody = gson.fromJson(stringPostBody, Fixture.class);
        return postBody.getFixtureId();
    }
}