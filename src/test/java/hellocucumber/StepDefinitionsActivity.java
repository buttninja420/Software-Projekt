package hellocucumber;
import dtu.example.ui.*;
import io.cucumber.java.en.*;

import java.time.LocalDate;


//import io.cucumber.java.en_scouse.An;

//import java.util.ArrayList;
//import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsActivity {
    App app = new App();
    Activity activity1 = new Activity();
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

    @Given("an activity with name {string} and start date {int} {int} {int} and end date {int} {int} {int}")
    public void an_activity_with_name_and_start_date_and_end_date(String activityName, int StartYear , int StartMonth, int StartDay, int EndYear, int EndMonth, int EndDay) {
        activity1 = new Activity();
        activity1.getTitle();

        activity1.setStartDate(LocalDate.of(StartYear, StartMonth, StartDay));        
        activity1.setEndDate(LocalDate.of(EndYear, EndMonth, EndDay));

        activity1.getStartDate();
        activity1.getEndDate();
    }

    @When("the project leader with UID {string} sets the dates to {int} {int} {int} and {int} {int} {int}")
    public void the_project_leader_with_UID_sets_the_dates_to_and(String projectLeader, int StartYear , int StartMonth, int StartDay, int EndYear, int EndMonth, int EndDay) {
        activity1.setStartDate(LocalDate.of(StartYear, StartMonth, StartDay));        
        activity1.setEndDate(LocalDate.of(EndYear, EndMonth, EndDay));
    }


    @Then("the activity with name {string} now has start date {int} {int} {int} and end date {int} {int} {int}")
    public void the_activity_with_name_now_has_start_date_and_end_date(String activity, int StartYear , int StartMonth, int StartDay, int EndYear, int EndMonth, int EndDay) {
        assertEquals(LocalDate.of(StartYear, StartMonth, StartDay), activity1.getStartDate());
        assertEquals(LocalDate.of(EndYear, EndMonth, EndDay), activity1.getEndDate());
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
        activity1.unassignUser(app.getUserWithUID(employee));
    }

    @Then("the employee with UID {string} is removed from the activity")
    public void the_employee_with_UID_is_removed_from_the_activity(String s) {
        assertNotEquals(app.getUsers(),activity1.getAssignedUsers());
    }

    @Given("an activity with name {string} with {int} hours budgetted and {int} hours registered")
    public void an_activity_with_name_with_hours_registered(String activityName, int budgettedTime, int registeredTime) {
        activity1 = new Activity();
        activity1.setBudgetedTime(budgettedTime);
        activity1.setRecordedTime(registeredTime);
    }

    @When("an employee adds {int} hour to the registered work time")
    public void an_employee_changes_the_registered_time_from_hours_to_hours(int addedTime) {
        activity1.addTime(addedTime);  
    }

    @Then("the activity with name {string} now has {int} hours of registered time")
    public void the_activity_with_name_now_has_hours_of_registered_time(String activity, int Time) {
        assertEquals(Time, activity1.getRecordedTime());
    }

    @Given("an activity with name {string} with {int} hours budgetted")
    public void an_activity_with_name_with_hours_budgetted(String activityName, int budgettedHours) {
        activity1 = new Activity();
        activity1.setTitle(activityName);
        activity1.setBudgetedTime(budgettedHours);
    }

    @When("the project leader checks the budgetted hours to be {int} hours")
    public void the_project_leader_checks_the_budgetted_hours(int budgettedHours) {
        budgettedHours = activity1.getBudgettedTime();
    }

    @And("the project leader checks the registered hours to be {int} hours")
    public void the_project_leader_checks_the_registered_hours_to_be_hours(int registeredHours) {
        activity1.setRecordedTime(registeredHours);
    }

    @Then("the project leader sees that the budgetted hours is {int} and that the registered hours is {int}")
    public void the_project_leader_sees_that_the_budgetted_hours_is_and_that_the_registered_hours_is(int budgettedHours, int registeredHours) {
        assertEquals(budgettedHours, activity1.getBudgettedTime());
        assertEquals(registeredHours, activity1.getRecordedTime());
    }

    @When("the project leader sets the max users to {int}")
    public void the_project_leader_sets_the_max_users_to(int maxUser) {
        activity1.setMaxUsers(maxUser);
    }

    @Then("the activity with name {string} now has max users set to {int}")
    public void the_activity_with_name_now_has_max_users_set_to(String activityName, int maxUsers) {
        assertEquals(activity1.getMaxUsers(), maxUsers);
    }

    @When("the project leader want to add {int} hours to {int} hours budgetted time and changes the start date to {int} {int} {int} and end date to {int} {int} {int}")
    public void the_project_leader_want_to_add_hours_to_hours_budgetted_time_and_changes_the_start_date_to_and_end_date_to(int addTime, int budgettedTime, int StartYear , int StartMonth, int StartDay, int EndYear, int EndMonth, int EndDay) {
        activity1.setBudgetedTime(budgettedTime); 
        int newTime = budgettedTime + addTime;
        activity1.editBudgetedTime(newTime);
        activity1.editDate(LocalDate.of(StartYear, StartMonth, StartDay), LocalDate.of(EndYear, EndMonth, EndDay));

        assertEquals(activity1.getBudgettedTime(), newTime);
    }


    @Then("the activity with name {string} now has budgetted time set to {int} hours and start date {int} {int} {int} and end date {int} {int} {int}")
    public void the_activity_with_name_now_has_budgetted_time_set_to_hours_and_start_date_and_end_date(String activityName, int budgettedTime, int StartYear , int StartMonth, int StartDay, int EndYear, int EndMonth, int EndDay) {
        assertEquals(activity1.getBudgettedTime(), budgettedTime);
        assertEquals(activity1.getStartDate(), LocalDate.of(StartYear, StartMonth, StartDay));
        assertEquals(activity1.getEndDate(), LocalDate.of(EndYear, EndMonth, EndDay));
    }

    @When("the project leader sets activity to be fixed")
    public void the_project_leader_sets_activity_to_be_fixed() {
        activity1.setFixed(true);
    }

    @Then("the activity with name {string} is now fixed")
    public void the_activity_with_name_is_now_fixed(String s) {
        assertTrue(activity1.getFixed());
    }

    @When("an employee adds {int} hours to the registered work time")
    public void an_employee_adds_hours_to_the_registered_work_time(int addHours) {
        activity1.addTime(addHours);
    }

    @Then("the activity with name {string} returns an error message {string}")
    public void the_activity_with_name_returns_an_error_message(String activityName, String errorMessage) {
        assertEquals("Error: The added time exceeds the Budgetted time for the activity.", errorMessage);
        
    }

}
