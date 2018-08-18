package com.thoughtworks.gauge.maven.Utils;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import org.junit.Assert;

public class BaseSteps {
    public static final String HOST = "http://localhost:3000";
    public static final String FIXTURE_BY_ID_ENDPOINT = "/fixture/";
    public static final String FIXTURE_ENDPOINT = "/fixture";
    public static final String GET_FIXTURES_ENDPOINT = "/fixtures";
    public static final String STATUS_CODE = "response";
    public static final String RESPONSE_BODY = "responseBody";
    public static final String POST_BODY = "postBody";

    DataStore dataStore = DataStoreFactory.getScenarioDataStore();

    @Step("Response is returned with status code <statusCode>")
    public void statusCode(int expectedStatusCode) {
        int statusCode = (int) dataStore.get(STATUS_CODE);
        Assert.assertEquals("status code is not as expected", expectedStatusCode, statusCode);
    }
}
