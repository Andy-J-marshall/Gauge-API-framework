package testScenarios;

import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import requestHelper.DeleteRequests;
import requestHelper.GetRequests;
import requestHelper.StoreNewFixtureHelper;

import java.util.List;

public class BaseTest {
    protected final static String BASE_URL = "http://localhost:3000";
    protected GetRequests getRequests = new GetRequests();
    protected DeleteRequests deleteRequests = new DeleteRequests();
    protected Gson gson = new Gson();
    protected StoreNewFixtureHelper storeNewFixtureHelper = new StoreNewFixtureHelper();

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
