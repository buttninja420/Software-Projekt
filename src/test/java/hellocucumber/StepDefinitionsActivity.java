package hellocucumber;
import dtu.example.ui.*;
import io.cucumber.java.en.*;
import java.util.Date;
//import io.cucumber.java.en_scouse.An;

//import java.util.ArrayList;
//import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsActivity {
    App app = new App();
    protected Activity activity1 = null;
    protected User user1 = null;
    @Given("an activity with name {string} and a project leader")
    public void anActivityWithAProjectLeader(String activityName) {
        activity1.setTitle(activityName);

    }
    @When("an employee with UID {string} requests to join an activity")
    public void anEmployeeWithUIDRequestsToJoinAnActivity(String string) {
        app.registerUser(string);
        activity1.assignUser(app.getUserWithUID(string));
    }
    @Then("the employee with UID {string} is added to the activity")
    public void theEmployeeWithUIDIsAddedToTheActivity(String string) {
        assertEquals(app.getUsers(),activity1.getAssignedUsers());
    }

    @Given("an activity with name {string} and a project leader and free timeslots")
    public void an_activity_with_a_project_leader_and_free_timeslots(String activityName) {
        activity1 = new Activity();
    }

    @When("the project leader with UID {string} registers an employee with UID {string} for the activity")
    public void the_project_leader_with_UID_registers_an_employee_with_UID_for_the_activity(String projectLeader, String employee) {
        app.registerUser(employee);
        activity1.assignUser(app.getUserWithUID(employee));
    }

    @Then("the employee with UID {string} is registered for the activity")
    public void the_employee_with_UID_is_registered_for_the_activity(String employee) {
        assertEquals(app.getUsers(),activity1.getAssignedUsers());
    }

    @Given("an activity with name {string} and start date {string} and end date {string}")
    public void an_activity_with_name_and_start_date_and_end_date(String activityName, String startDate, String endDate) {
        activity1 = new Activity();
        activity1.getTitle();
        activity1.getStartDate();
        activity1.getEndDate();
    }

    @When("the project leader with UID {string} sets the dates to {string} and {string}")
    public void the_project_leader_with_UID_sets_the_dates_to_and(String projectLeader, Date newStartDate, Date newEndDate) {
        activity1.setStartDate(newStartDate);
        activity1.setEndDate(newEndDate);
    }

    @Then("the activity with name {string} now has start date {string} and end date {string}")
    public void the_activity_with_name_now_has_start_date_and_end_date(String activity, String newStartDate, String newEndDate) {
        assertEquals(newStartDate, activity1.getStartDate());
        assertEquals(newEndDate, activity1.getEndDate());
    }

    @Given("an activity with name {string}")
    public void an_activity_with_name(String activityName) {
        activity1 = new Activity();
    }

    @When("an employee with UID {string} who has {int} ongoing activites tries to join the activity with name {string}")
    public void an_employee_with_UID_who_has_ongoin_activites_tries_to_join_the_activity(String employee, int ongoingActivities, String activity) {
        app.registerUser(employee);
        if (ongoingActivities < User.maxActivities) {
            activity1.assignUser(app.getUserWithUID(employee));
            app.getUserWithUID(employee).getActivities().add(activity1);
        } else {
            System.out.println("Error: The employee has reached the maximum number of ongoing activities.");

        }
    }

    @Then("the employee with UID {string} is not added to the activity")
    public void the_employee_with_UID_is_not_added_to_the_activity(String s) {
        assertNotEquals(app.getUsers(),activity1.getAssignedUsers());
    }

    @Given("an activity with name {string} and an employee with UID {string}")
    public void an_activity_with_name_and_an_employee_with_UID(String activityName, String employee) {
        activity1 = new Activity();
        app.registerUser(employee);
        activity1.assignUser(app.getUserWithUID(employee));
    }

    @When("the project leader with UID {string} removes the employee with UID {string} from the activity")
    public void the_project_leader_with_UID_removes_the_employee_with_UID_from_the_activity(String projectLeader, String employee) {
        activity1.getAssignedUsers().remove(app.getUserWithUID(employee));
        app.getUserWithUID(employee).getActivities().remove(activity1);
    }

    @Then("the employee with UID {string} is removed from the activity")
    public void the_employee_with_UID_is_removed_from_the_activity(String s) {
        assertNotEquals(app.getUsers(),activity1.getAssignedUsers());
    }

    @Given("an activity with name {string} with {int} hours registered")
    public void an_activity_with_name_with_hours_registered(String activityName, int registeredTime) {
        activity1 = new Activity();
        activity1.setRecordedTime(registeredTime);
    }

    @When("an employee adds {int} hour to the registered work time")
    public void an_employee_changes_the_registered_time_from_hours_to_hours(int registeredTime, int addedTime) {
        activity1.addTime(addedTime);  
    }

    @Then("the activity with name {string} now has {int} hours of registered time")
    public void the_activity_with_name_now_has_hours_of_registered_time(String activity, int Time) {
        assertEquals(Time, activity1.getRecordedTime());
    }

}
