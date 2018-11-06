package testScenarios.fixtures;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import testScenarios.BaseTest;

import static org.hamcrest.Matchers.*;

public class GetFixtureTests extends BaseTest {

    @Test
    public void fixtureIdsAreReturned() {
        ValidatableResponse response = getRequests.getFixturesRequest(200);
        response.and().body("fixtureId.size()", is(equalTo(3)))
                .and().body("fixtureId", everyItem(notNullValue()));
    }
}
