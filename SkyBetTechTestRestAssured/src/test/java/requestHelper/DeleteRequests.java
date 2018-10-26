package requestHelper;

import static io.restassured.RestAssured.given;
import static testScenarios.AllTestsToSplit.BASE_URL;

public class DeleteRequests {

    public void deleteFixtureRequest(String fixtureId, int expectedStatusCode) {
        String url = String.format(String.format("%s/fixture/%%s", BASE_URL), fixtureId);
        given().contentType("application/json")
                .when().delete(url)
                .then().statusCode(expectedStatusCode);
    }
}
