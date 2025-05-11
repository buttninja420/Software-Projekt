package hellocucumber;
import dtu.example.ui.*;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.*;

import java.time.LocalDate;


//import io.cucumber.java.en_scouse.An;

//import java.util.ArrayList;
//import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsActivity {
    App app = new App();
    Activity activity1;
    Project project1;
    User projectLeader;
    User employee;

    //Theoretical predfined start-/end date
    LocalDate startDate = LocalDate.of(2023, 10, 1);
    LocalDate endDate = LocalDate.of(2023, 11, 1);
    protected User user1 = null;
    String errorMessage = null;
    String errorMessage2 = null;
    String errorMessage3 = null;
    @Given("an activity with name {string} and a project leader")
    public void anActivityWithAProjectLeader(String activityName) {
        project1 = new Project("Project1");
        project1.setStartDate(startDate);
        project1.setEndDate(endDate);

        activity1 = new Activity(activityName, project1);
        activity1.setStartDate(startDate);
        activity1.setEndDate(endDate);
        project1.addActivity(activity1);

    }
    @When("an employee with UID {string} requests to join an activity")
    public void anEmployeeWithUIDRequestsToJoinAnActivity(String string) {
        app.registerUser(string);
        activity1.assignUser(app.getUserWithUID(string));
    }
    @Then("the employee with UID {string} is added to the activity")
    public void theEmployeeWithUIDIsAddedToTheActivity(String string) {
        assertTrue(activity1.getAssignedUsers().contains(app.getUserWithUID(string)));
    }

    @Given("an activity with name {string} and a project leader with UID {string} and free timeslots")
    public void an_activity_with_a_project_leader_and_free_timeslots(String activityName, String UID) {
        project1 = new Project("Project1");
        project1.setStartDate(startDate);
        project1.setEndDate(endDate);
        app.registerUser(UID);
        projectLeader = new User(UID);
        project1.setProjectLeader(projectLeader);

        activity1 = new Activity(activityName, project1);
        activity1.setStartDate(startDate);
        activity1.setEndDate(endDate);
        project1.addActivity(activity1);
    }

    @When("the project leader with UID {string} registers an employee with UID {string} for the activity")
    public void the_project_leader_with_UID_registers_an_employee_with_UID_for_the_activity(String leaderUID, String employeeUID) {
        app.registerUser(employeeUID);
        projectLeader = project1.getProjectleader();
        employee = app.getUserWithUID(employeeUID);
        activity1.assignUserAsProjectLeader(project1, projectLeader, employee);
    }

    @Then("the employee with UID {string} is registered for the activity")
    public void the_employee_with_UID_is_registered_for_the_activity(String employee) {
        assertTrue(activity1.getAssignedUsers().contains(app.getUserWithUID(employee)));
    }

    @Given("an activity with name {string} and start date {int} {int} {int} and end date {int} {int} {int}")
    public void an_activity_with_name_and_start_date_and_end_date(String activityName, int StartYear , int StartMonth, int StartDay, int EndYear, int EndMonth, int EndDay) {
        activity1 = new Activity(activityName);

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
        activity1 = new Activity(activityName);
        activity1.setStartDate(startDate);
        activity1.setEndDate(endDate);
    }

    @When("an employee with UID {string} who has {int} ongoing activites tries to join the activity with name {string}")
    public void an_employee_with_UID_who_has_ongoing_activites_tries_to_join_the_activity(String employee, int ongoingActivities, String activity) {
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
        project1 = new Project("Project1");
        project1.setStartDate(startDate);
        project1.setEndDate(endDate);

        activity1 = new Activity(activityName, project1);
        activity1.setStartDate(startDate);
        activity1.setEndDate(endDate);
        project1.addActivity(activity1);

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

    @Given("an activity with name {string} with {int} hours budgeted and {int} hours registered")
    public void an_activity_with_name_with_hours_registered(String activityName, int budgetedTime, int registeredTime) {
        activity1 = new Activity(activityName);
        activity1.setBudgetedTime(budgetedTime);
        activity1.setRecordedTime(registeredTime);
    }

    @When("an employee adds {int} hour to the activity registered work time")
    public void an_employee_adds_hour_to_the_activity_registered_work_time(int addedTime) {
        activity1.addTime(addedTime);  
    }

    @Then("the activity with name {string} now has {int} hours of registered time")
    public void the_activity_with_name_now_has_hours_of_registered_time(String activity, int Time) {
        assertEquals(Time, activity1.getRecordedTime());
    }

    @Given("an activity with name {string} with {int} hours budgeted")
    public void an_activity_with_name_with_hours_budgeted(String activityName, int budgetedHours) {
        activity1 = new Activity(activityName);
        activity1.setStartDate(startDate);
        activity1.setEndDate(endDate);
        activity1.setBudgetedTime(budgetedHours);
    }

    @When("the project leader checks the budgeted hours for activity to be {int} hours")
    public void the_project_leader_checks_the_budgeted_hours_for_activity_to_be_hours(int budgetedHours) {
        budgetedHours = activity1.getBudgetedTime();
    }

    @And("the project leader checks the registered hours for activity to be {int} hours")
    public void the_project_leader_checks_the_registered_hours_for_activity_to_be_hours(int registeredHours) {
        activity1.setRecordedTime(registeredHours);
    }

    @Then("the project leader sees that the budgeted hours for activity is {int} and that the registered hours is {int}")
    public void the_project_leader_sees_that_the_budgeted_hours_for_activity_is_and_that_the_registered_hours_is(int budgetedHours, int registeredHours) {
        assertEquals(budgetedHours, activity1.getBudgetedTime());
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

    @When("the project leader want to add {int} hours to {int} hours budgeted time and changes the start date to {int} {int} {int} and end date to {int} {int} {int} and change the name to {string}")
    public void the_project_leader_want_to_add_hours_to_hours_budgeted_time_and_changes_the_start_date_to_and_end_date_to(int addTime, int budgetedTime, int StartYear , int StartMonth, int StartDay, int EndYear, int EndMonth, int EndDay, String NewName) {
        activity1.setBudgetedTime(budgetedTime); 
        int newTime = budgetedTime + addTime;
        activity1.editBudgetedTime(newTime);
        activity1.editTitle(NewName);
        activity1.editDate(LocalDate.of(StartYear, StartMonth, StartDay), LocalDate.of(EndYear, EndMonth, EndDay));

        assertEquals(activity1.getBudgetedTime(), newTime);
    }


    @Then("the activity with name {string} now has budgeted time set to {int} hours and start date {int} {int} {int} and end date {int} {int} {int} and name {string}")
    public void the_activity_with_name_now_has_budgeted_time_set_to_hours_and_start_date_and_end_date(String activityName, int budgetedTime, int StartYear , int StartMonth, int StartDay, int EndYear, int EndMonth, int EndDay, String newName) {
        assertEquals(activity1.getBudgetedTime(), budgetedTime);
        assertEquals(activity1.getStartDate(), LocalDate.of(StartYear, StartMonth, StartDay));
        assertEquals(activity1.getEndDate(), LocalDate.of(EndYear, EndMonth, EndDay));
        assertEquals(activity1.getTitle(), newName);
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
        try {
        activity1.addTime(addHours);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the activity with name {string} does not add the registered time")
    public void the_activity_with_name_does_not_add_the_registered_time(String activityName) {
        assertEquals("The added time exceeds the budgeted time.", errorMessage);        
    }

    @When("the user tries to log {int} hours for activity")
    public void the_user_tries_to_log_hours_for_activity(int i) {
        try {
            activity1.addTime(i);
        } catch (IllegalArgumentException | AssertionError e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the user is not able to log negative hours for activity")
    public void the_user_is_not_able_to_log_negative_hours_for_activity() {
        assertEquals("Precondition failed: Cannot add negative time.", errorMessage);
    }

    @Given("an activity with name {string} and start date {int} {int} {int} and end date {int} {int} {int} and a project leader")
    public void an_activity_with_name_and_start_date_and_end_date_and_a_project_leader(String s, int i, int i2, int i3, int i4, int i5, int i6) {
        activity1 = new Activity(s);
        activity1.setStartDate(LocalDate.of(i, i2, i3));
        activity1.setEndDate(LocalDate.of(i4, i5, i6));
    }

    @When("the project leader tries to set start date to {int} {int} {int} for activity")
    public void the_project_leader_tries_to_set_start_date_to_for_activity(int i, int i2, int i3) {
        LocalDate startDate = LocalDate.of(i, i2, i3);
        try {
            activity1.setStartDate(startDate);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }

    @And("the project leader tries to set end date to {int} {int} {int} for activity")
    public void the_project_leader_tries_to_set_end_date_to_for_activity(int i, int i2, int i3) {
        LocalDate endDate = LocalDate.of(i, i2, i3);
        try {
            activity1.setEndDate(endDate);
        } catch (IllegalArgumentException e) {
            errorMessage3 = e.getMessage();
        }
    }

    @And("the project leader tries to add {int} hours for activity")
    public void the_project_leader_tries_to_add_hours_for_activity(int i) {
        try {
            activity1.addTime(i);
        } catch (IllegalArgumentException e) {
            errorMessage2 = e.getMessage();
        }
    }

    @Then("the project leader is not able to set start date to {int} {int} {int} and add {int} hours for activity")
    public void the_project_leader_is_not_able_to_set_start_date_to_and_add_hours_for_activity(int i, int i2, int i3, int i4) {
        assertEquals("Start date cannot be after end date.", errorMessage);
        assertEquals("Precondition failed: Cannot add zero time.", errorMessage2);
        assertEquals("End date cannot be before start date.", errorMessage3);
        assertNotEquals(LocalDate.of(i, i2, i3), activity1.getStartDate());
    }

    @Given("an activity with name {string} with start date {int} {int} {int} and end date {int} {int} {int} and a project leader")
    public void an_activity_with_name_with_start_date_and_end_date_and_a_project_leader(String s, int i, int i2, int i3, int i4, int i5, int i6) {
        activity1 = new Activity(s);
        activity1.setStartDate(LocalDate.of(i, i2, i3));
        activity1.setEndDate(LocalDate.of(i4, i5, i6));
    }

    @When("the project leader tries to edit start date to {int} {int} {int} and end date to {int} {int} {int} for activity")
    public void the_project_leader_tries_to_edit_start_date_to_and_end_date_to_for_activity(int i, int i2, int i3, int i4, int i5, int i6) {
        LocalDate startDate = LocalDate.of(i, i2, i3);
        LocalDate endDate = LocalDate.of(i4, i5, i6);
        try {
            activity1.editDate(startDate, endDate);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the activity with name {string} does not change start date to {int} {int} {int} and end date to {int} {int} {int} for activity")
    public void the_activity_with_name_does_not_change_start_date_to_and_end_date_to_for_activity(String s, int i, int i2, int i3, int i4, int i5, int i6) {
        assertEquals("Start date cannot be after end date.", errorMessage);
        assertNotEquals(LocalDate.of(i, i2, i3), activity1.getStartDate());
        assertNotEquals(LocalDate.of(i4, i5, i6), activity1.getEndDate());
    }

}
