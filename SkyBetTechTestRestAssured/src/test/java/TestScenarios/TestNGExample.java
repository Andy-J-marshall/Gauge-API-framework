package TestScenarios;

import PostHelpers.StoreNewFixtureHelper;
import Requests.responses.Fixture;
import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TestNGExample {

    public final static String BASE_URL = "http://localhost:3000";

    private Gson gson = new Gson();
    private StoreNewFixtureHelper storeNewFixtureHelper = new StoreNewFixtureHelper();
    private RequestHelper requestHelper = new RequestHelper();


    @Test
    public void fixtureIdsAreReturned() {
        ValidatableResponse response = requestHelper.getFixturesRequest(200);
        response.and().body("fixtureId.size()", is(equalTo(3)))
                .and().body("fixtureId", everyItem(notNullValue()));
    }

    @Test
    public void newlyAddedFixtureHasTeamIdOfHome() {
        Fixture requestBody = storeNewFixtureHelper.createFixture();
        String fixtureId = getLastCreatedFixtureId(requestBody);
        requestHelper.postNewFixtureRequest(requestBody, 200);

        Response response = when().get(BASE_URL + "/fixtures");
        Fixture[] responseBody = gson.fromJson(response.body().asString(), Fixture[].class);

        for (Fixture fixture : responseBody) {
            if (fixture.getFixtureId().equalsIgnoreCase(fixtureId)) {
                assertThat(fixture.getFootballFullState().getTeams().get(0).getTeamId(), is(equalToIgnoringCase("HOME")));
            }
        }
    }

    @Test
    public void deleteLastCreatedFixture() {
        Fixture requestBody = storeNewFixtureHelper.createFixture();
        requestHelper.postNewFixtureRequest(requestBody, 200);

        String fixtureId = getLastCreatedFixtureId(requestBody);
        requestHelper.deleteFixtureRequest(fixtureId, 200);
        requestHelper.getSpecificFixtureIdRequest(fixtureId, 404);

        ValidatableResponse response = requestHelper.getFixturesRequest(200);
        response.and().body("fixtureId.size()", is(equalTo(3)))
                .and().body("fixtureId", not(arrayContaining(fixtureId)));
    }

    private String getLastCreatedFixtureId(Fixture requestBody) {
        return requestBody.getFixtureId();
    }

    //    @AfterScenario(tags = "CleanupAfterTest")
//    public void cleanUpStep() {
//        Fixture requestBody = storeNewFixtureHelper.createFixture();
//        String fixtureId = getLastCreatedFixtureId(requestBody);
//
//        String deleteUrl = String.format(BASE_URL + "/fixture/%s", fixtureId);
//        given().contentType("application/json")
//                .when().delete(deleteUrl)
//                .then().statusCode(200);
//    }
}