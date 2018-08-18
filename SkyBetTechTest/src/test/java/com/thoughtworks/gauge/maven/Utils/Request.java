package com.thoughtworks.gauge.maven.Utils;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.datastore.DataStore;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.maven.Response.POJO.Fixture;

import static com.thoughtworks.gauge.maven.Utils.BaseSteps.*;

public class Request {
    private DataStore dataStore = DataStoreFactory.getScenarioDataStore();
    private Gson gson = new Gson();

    public void deleteRequest(String url) throws UnirestException {
        HttpResponse<String> response = Unirest.delete(HOST + url)
                .asString();
        int statusCode = response.getStatus();
        dataStore.put(STATUS_CODE, statusCode);
    }

    public void getRequest(String url) throws UnirestException {
        HttpResponse<String> response = Unirest.get(HOST + url)
                .asString();
        Integer statusCode = response.getStatus();
        dataStore.put(STATUS_CODE, statusCode);
        if (!statusCode.equals(404)) {
            if (url.endsWith(GET_FIXTURES_ENDPOINT)) {
                Fixture[] responseBody = gson.fromJson(response.getBody(), Fixture[].class);
                dataStore.put(RESPONSE_BODY, responseBody);
            } else {
                Fixture responseBody = gson.fromJson(response.getBody(), Fixture.class);
                dataStore.put(RESPONSE_BODY, responseBody);
            }
        }
    }

    public void postRequest(String url, String body) throws UnirestException {
        HttpResponse<String> response = Unirest.post(HOST + url)
                .header("Content-Type", "application/json")
                .body(body)
                .asString();
        logRequestInReports(url, body);
        dataStore.put(STATUS_CODE, response.getStatus());
        dataStore.put(POST_BODY, body);
    }

    private void logRequestInReports(String url, String requestBody) {
        Gauge.writeMessage("Request url: " + url);
        System.out.println("Request url: " + url);

        Gauge.writeMessage("Request body: " + requestBody);
        System.out.println("Request body: " + requestBody);
    }
}
