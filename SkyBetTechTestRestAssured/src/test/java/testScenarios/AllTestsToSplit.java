package testScenarios;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import pojos.createFixture.Fixture;
import requestHelper.DeleteRequests;
import requestHelper.GetRequests;
import requestHelper.PostRequests;
import requestHelper.StoreNewFixtureHelper;

import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class AllTestsToSplit extends BaseTest {

    public final static String BASE_URL = "http://localhost:3000";

    private Gson gson = new Gson();
    private StoreNewFixtureHelper storeNewFixtureHelper = new StoreNewFixtureHelper();
    private PostRequests postRequests = new PostRequests();
    private GetRequests getRequests = new GetRequests();
    private DeleteRequests deleteRequests = new DeleteRequests();

    @Test
    public void fixtureIdsAreReturned() {
        ValidatableResponse response = getRequests.getFixturesRequest(200);
        response.and().body("fixtureId.size()", is(equalTo(3)))
                .and().body("fixtureId", everyItem(notNullValue()));
    }

    @Test
    public void newlyAddedFixtureHasTeamIdOfHome() {
        Fixture requestBody = storeNewFixtureHelper.createFixture();
        String fixtureId = requestBody.getFixtureId();
        postRequests.postNewFixtureRequest(requestBody, 200);

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
        postRequests.postNewFixtureRequest(requestBody, 200);

        String fixtureId = requestBody.getFixtureId();
        deleteRequests.deleteFixtureRequest(fixtureId, 200);
        getRequests.getSpecificFixtureIdRequest(fixtureId, 404);

        ValidatableResponse response = getRequests.getFixturesRequest(200);
        response.body("fixtureId.size()", is(equalTo(3)))
                .and().body("fixtureId", not(arrayContaining(fixtureId)));
    }
}


// TO DO
//find a nice way to use a datastore kind of thing. Use a
//split tests and code up more
//Use testng annotations and find way to run it in better way
//deal with environment config
//organise code