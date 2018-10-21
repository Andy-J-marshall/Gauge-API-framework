package TestScenarios;

import Requests.responses.Fixture;
import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;

import static TestScenarios.TestNGExample.BASE_URL;
import static io.restassured.RestAssured.given;

public class RequestHelper {

    Gson gson = new Gson();

    public void deleteFixtureRequest(String fixtureId, int expectedStatusCode) {
        String url = String.format(String.format("%s/fixture/%%s", BASE_URL), fixtureId);
        given().contentType("application/json")
                .when().delete(url)
                .then().statusCode(expectedStatusCode);
    }

    public void postNewFixtureRequest(Fixture body, int expectedStatusCode) {
        String url = String.format("%s/fixture", BASE_URL);
        given().contentType("application/json")
                .body(body)
                .when().post(url)
                .then().statusCode(expectedStatusCode);
    }

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
