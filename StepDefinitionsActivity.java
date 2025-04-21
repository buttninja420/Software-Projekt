package hellocucumber;
import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsActivity {
    protected Activity activity1 = null;
    protected User
    @Given("an activity")
    public void anActivityWithAProjectLeader() {
        activity1 = new Activity();

    }
    @When("an employee with UID {string} requests to join an activity")
    public void anEmployeeWithUIDRequestsToJoinAnActivity(String string) {
        activity1.assignedUser()
    }
    @Then("the employee with UID {string} is added to the activity")
    public void theEmployeeWithUIDIsAddedToTheActivity(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
