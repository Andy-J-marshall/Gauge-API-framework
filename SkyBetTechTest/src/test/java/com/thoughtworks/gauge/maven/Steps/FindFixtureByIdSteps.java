package com.thoughtworks.gauge.maven.Steps;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.maven.Endpoints.GetFixture;
import com.thoughtworks.gauge.maven.Utils.Request;

import static com.thoughtworks.gauge.maven.Utils.BaseSteps.FIXTURE_BY_ID_ENDPOINT;

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
