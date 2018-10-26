package testScenarios;

import io.restassured.response.ValidatableResponse;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import requestHelper.DeleteRequests;
import requestHelper.GetRequests;

import java.util.List;

public class BaseTest {
    private GetRequests getRequests = new GetRequests();
    private DeleteRequests deleteRequests = new DeleteRequests();

    @BeforeSuite
    public void cleanupFixturesBeforeTestExecution() {
        // This ensures any added fixtures are deleted before we run the tests
        findAndDeleteAllAddedFixtures();
    }

    @AfterSuite
    public void cleanupFixturesAfterTestExecution() {
        // This ensures any added fixtures are deleted after we run the tests
        findAndDeleteAllAddedFixtures();
    }

    private void findAndDeleteAllAddedFixtures() {
        ValidatableResponse response = getRequests.getFixturesRequest(200);
        List<String> fixtureIdList = response.extract().path("fixtureId");
        for (String fixtureIdString : fixtureIdList) {
            int fixtureId = Integer.parseInt(fixtureIdString);
            if (fixtureId > 3) {
                deleteRequests.deleteFixtureRequest(fixtureIdString, 200);
            }
        }
    }
}
