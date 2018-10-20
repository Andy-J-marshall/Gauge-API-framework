package TestScenarios;

import PostHelpers.StoreNewFixtureHelper;
import Requests.responses.Fixture;
import Requests.responses.FootballFullState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestNGExample {

    private Gson gson = new Gson();
    private StoreNewFixtureHelper storeNewFixtureHelper = new StoreNewFixtureHelper();

    @Test
    public void fixtureIdsAreReturned() {
        given().contentType("application/json")
                .when().get("http://localhost:3000/fixtures")
                .then().body("fixtureId.size()", is(equalTo(3)))
                .and().body("fixtureId", everyItem(notNullValue()))
                .statusCode(200);
    }

    @Test
    public void newlyAddedFixtureHasTeamIdOfHome() {
        Fixture requestBody = storeNewFixtureHelper.createFixture();
        String fixtureId = requestBody.getFixtureId();
        given().contentType("application/json")
                .body(gson.toJson(requestBody))
                .when().post("http://localhost:3000/fixture")
                .then().statusCode(200);

        Response response = when().get("http://localhost:3000/fixtures");
        String expectedTeamIdFromResponse = String.format("find {it.fixtureId = %S}.footballFullState.teams[0].teamId", fixtureId);
        response.then().body(expectedTeamIdFromResponse, equalTo("HOME"));
    }


    @Test
    public void newlyAddedFixtureHasTeamIdOfHome2() {
        Fixture requestBody = storeNewFixtureHelper.createFixture();
        String fixtureId = requestBody.getFixtureId();
        given().contentType("application/json")
                .body(gson.toJson(requestBody))
                .when().post("http://localhost:3000/fixture")
                .then().statusCode(200);

        Response response = when().get("http://localhost:3000/fixtures");

        System.out.println(response.getBody().asString());


        Fixture[] fixture1 = gson.fromJson(response.getBody().asString(), Fixture[].class);




        JsonPath jsonpath = response.jsonPath();
        List<JsonObject> jsonObjects = jsonpath.getJsonObject("");



        for (JsonObject jsonObject : jsonObjects) {
//            Fixture fixture1 = gson.fromJson(jsonObject, Fixture.class);
//            if (fixture1.getFixtureId().equalsIgnoreCase(fixtureId)) {
//                assertThat(fixture1.getFootballFullState().getTeams().get(0).getTeamId(), equals("HOME"));
//            }
        }
    }

    @Test
    public void deleteLastCreatedFixture() {
        //create fixture
        Fixture requestBody = storeNewFixtureHelper.createFixture();
        String fixtureId = requestBody.getFixtureId();
        given().contentType("application/json")
                .body(gson.toJson(requestBody))
                .when().post("http://localhost:3000/fixture")
                .then().statusCode(200);

        //delete fixture
        String deleteUrl = String.format("http://localhost:3000/fixture/%s", fixtureId);
        given().contentType("application/json")
                .when().delete(deleteUrl)
                .then().statusCode(200);

        //DO get

        when().get(String.format("http://localhost:3000/fixture/%s", fixtureId))
                .then().statusCode(404);

        when().get("http://localhost:3000/fixtures")
                .then().body("fixtureId", not(arrayContaining(fixtureId)))
                .and().body("fixtureId.size()", is(equalTo(3)));
    }

//    @AfterScenario(tags = "CleanupAfterTest")
    public void cleanUpStep() {
        Fixture requestBody = storeNewFixtureHelper.createFixture();
        String fixtureId = requestBody.getFixtureId();

        String deleteUrl = String.format("http://localhost:3000/fixture/%s", fixtureId);
        given().contentType("application/json")
                .when().delete(deleteUrl)
                .then().statusCode(200);
    }
}