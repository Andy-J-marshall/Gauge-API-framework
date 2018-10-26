package requestHelper;

import pojos.createFixture.Fixture;

import static io.restassured.RestAssured.given;
import static testScenarios.AllTestsToSplit.BASE_URL;

public class PostRequests {

    public void postNewFixtureRequest(Fixture body, int expectedStatusCode) {
        String url = String.format("%s/fixture", BASE_URL);
        given().contentType("application/json")
                .body(body)
                .when().post(url)
                .then().statusCode(expectedStatusCode);
    }
}
