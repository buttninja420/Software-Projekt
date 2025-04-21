package hellocucumber;
import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsActivity {
    protected Activity activity1 = null;
    protected User user1 = null;
    protected List<User> finalAnswer = new ArrayList<User>();

    @Given("an activity")
    public void anActivity() {
        activity1 = new Activity();

    }
    @When("an employee with UID {string} requests to join an activity")
    public void anEmployeeWithUIDRequestsToJoinAnActivity(String string) {
        user1 = new User(string);
        finalAnswer.add(user1);
        activity1.assignedUser(user1);
    }
    @Then("the employee with UID {string} is added to the activity")
    public void theEmployeeWithUIDIsAddedToTheActivity(String string) {
        assertEquals(finalAnswer,activity1.getAssignedUser());
    }
}
