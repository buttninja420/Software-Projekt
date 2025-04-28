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
    @Given("an activity with a project leader")
    public void anActivityWithAProjectLeader() {
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

    @Given("an activity with a project leader and free timeslots")
    public void an_activity_with_a_project_leader_and_free_timeslots() {
        activity1 = new Activity();
    }

    @When("the project leader with UID {string} registers an employee with UID {string} for the activity")
    public void the_project_leader_with_UID_registers_an_employee_with_UID_for_the_activity(String s, String s2) {
        app.registerUser(s2);
        activity1.assignUser(app.getUserWithUID(s2));
    }

    @Then("the employee with UID {string} is registered for the activity")
    public void the_employee_with_UID_is_registered_for_the_activity(String s) {
        assertEquals(app.getUsers(),activity1.getAssignedUser());
    }

}
