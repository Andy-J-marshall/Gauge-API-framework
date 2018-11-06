package testScenarios.fixtures;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import testScenarios.BaseTest;

import static org.hamcrest.Matchers.*;

public class DeleteFixtures extends BaseTest {
    @Test
    public void deleteLastCreatedFixture() {
        String fixtureId = storeNewFixtureHelper.createFixtureAndReturnFixtureId();
        deleteRequests.deleteFixtureRequest(fixtureId, 200);
        getRequests.getSpecificFixtureIdRequest(fixtureId, 404);

        ValidatableResponse response = getRequests.getFixturesRequest(200);
        response.body("fixtureId.size()", is(equalTo(3)))
                .and().body("fixtureId", not(arrayContaining(fixtureId)));
    }
}
