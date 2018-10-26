package requestHelper;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static testScenarios.AllTestsToSplit.BASE_URL;

public class GetRequests {

    public ValidatableResponse getFixturesRequest(int expectedStatusCode) {
        String url = String.format("%s/fixtures", BASE_URL);
        return given().contentType("application/json")
                .when().get(url)
                .then().statusCode(expectedStatusCode);
    }

    public void getSpecificFixtureIdRequest(String fixtureId, int expectedStatusCode) {
        String url = String.format("%s/fixture/%s", BASE_URL, fixtureId);
        given().contentType("application/json")
                .when().get(url)
                .then().statusCode(expectedStatusCode);
    }
}
