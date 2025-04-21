package hellocucumber;
import dtu.example.ui.*;
import io.cucumber.java.en.*;
//import io.cucumber.java.en_scouse.An;

//import java.util.ArrayList;
//import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsActivity {
    App app = new App();
    protected Activity activity1 = null;
    protected User user1 = null;
    @Given("an activity")
    public void anActivity() {
        activity1 = new Activity();

    }
    @When("an employee with UID {string} requests to join an activity")
    public void anEmployeeWithUIDRequestsToJoinAnActivity(String string) {
        app.registerUser(string);
        activity1.assignUser(app.getUserWithUID(string));
    }
    @Then("the employee with UID {string} is added to the activity")
    public void theEmployeeWithUIDIsAddedToTheActivity(String string) {
        assertEquals(app.getUsers(),activity1.getAssignedUser());
    }
}
