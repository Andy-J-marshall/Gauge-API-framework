package com.thoughtworks.gauge.maven.steps;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.maven.endpoints.GetFixture;
import com.thoughtworks.gauge.maven.utils.Request;

import static com.thoughtworks.gauge.maven.utils.BaseSteps.FIXTURE_BY_ID_ENDPOINT;

public class FindFixtureByIdSteps {
    GetFixture getFixture = new GetFixture();
    private Request request = new Request();

    @Step("Find fixture by ID <Id>")
    public void findFixtureById(String id) throws UnirestException {
        String url = FIXTURE_BY_ID_ENDPOINT + id;
        request.getRequest(url);
    }

    @Step("Find the most recently created fixture by ID")
    public void findLastCreatedId() throws UnirestException {
        String fixtureId = getFixture.findFixtureId();
        findFixtureById(fixtureId);
    }
}
