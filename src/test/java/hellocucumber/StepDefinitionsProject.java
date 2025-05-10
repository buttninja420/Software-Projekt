package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_old.Ac;
import io.cucumber.java.en_scouse.An;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsProject {
    Exception thrownException;
    App app = new App();
    Project tmProject;
    Activity activity1;
    int totalAssignedTime = 0;
    String report;
    String errorMessage = null;
    
    @Given("an app that exist")
    public void an_app_that_exist() {
        app = new App();
    }
    
    @When("a project with name {string} is created")
    public void a_project_with_name_is_created(String projectName) {
        tmProject = new Project(projectName);
    }

    @Then("the project with name {string} exists")
    public void the_project_with_name_exists(String projectName) {
        assertEquals(projectName, tmProject.getName());
    }

    @Given("a Project with name {string}")
    public void a_Project_with_name(String projectName) {
        tmProject = new Project(projectName);
    }

    @When("an employee who is not the project leader adds new activity with name {string}")
    public void an_employee_who_is_not_the_project_leader_adds_new_activity_with_name(String activityName) {
        Activity activity1 = new Activity(activityName);
        tmProject.addActivity(activity1);
    }

    @Then("the Project has an activity named {string}")
    public void the_Project_has_an_activity_named(String activityName) {
        boolean found = false;
        for (Activity activity : tmProject.getActivities()) {
            if (activity.getTitle().equals(activityName)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @When("the project has {int} available hours and {int} hours worked")
    public void the_project_has_available_hours_and_hours_worked(int availableHours, int workedHours) {
        tmProject.setBudgetedTime(availableHours);
        tmProject.setRecordedTime(workedHours);
    }

    @And("an employee with UID {string} registers time for project {string} with {int} hours")
    public void an_employee_with_UID_registers_time_for_project_with_hours(String UID, String projectName, int hours) {
        app.registerUser(UID);
        tmProject.addTime(hours);
    }

    @And("the project has {int} hours worked")
    public void the_project_has_hours_worked(int hoursWorked) {
        tmProject.setRecordedTime(hoursWorked);
    }

    @And("the project has {int} available hours")
    public void the_project_has_available_hours(int availableHours) {
        tmProject.setBudgetedTime(availableHours);
    }

    @Then("project {string} has {int} hours worked and {int} hours available")
    public void project_has_hours_worked_and_hours_available(String projectName, int workedHours, int availableHours) {
        assertEquals(workedHours, tmProject.getRecordedTime());
        assertEquals(availableHours, tmProject.getBudgetedTime());
    }

    @Given("a Project with name {string} and activity named {string} without a project leader")
    public void a_Project_with_name_and_activity_named_without_a_project_leader(String projectName, String activityName) {
        app = new App();
        tmProject = new Project(projectName);
        Activity activity1 = new Activity(activityName);
        tmProject.addActivity(activity1);
    }

    @When("an employee with UID {string} is registered to the project takes the role as project leader")
    public void an_employee_with_UID_is_registered_to_the_project_takes_the_role_as_project_leader(String UID) {
        app.registerUser(UID);
        tmProject.setProjectLeader(app.getUserWithUID(UID));
        //System.out.println("project leader with UID: " + UID + "added!");
    }

    @Then("the Project with name {string} has a project leader with the UID {string}")
    public void the_Project_with_name_has_a_project_leader_with_the_UID(String projectName, String leaderUID) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(leaderUID, tmProject.getProjectleader().getUID());
    }

    @Given("a Project with name {string} and activity with name {string} with {int} hours worked")
    public void a_Project_with_name_and_activity_with_name_with_hours_worked(String projectName, String activityName, int hoursWorked) {
        tmProject = new Project(projectName);
        Activity activity1 = new Activity(activityName);
        tmProject.addActivity(activity1);
        tmProject.setRecordedTime(hoursWorked);
    }

    @And("activity with name {string} with {int} hour worked")
    public void activity_with_name_with_hour_worked(String activityName, int hoursWorked) {
        Activity activity2 = new Activity(activityName);
        tmProject.addActivity(activity2);
        tmProject.setRecordedTime(hoursWorked);
        
    }

    @And("activity with name {string} with {int} hours worked")
    public void activity_with_name_with_hours_worked(String activityName, int hoursWorked) {
        Activity activity3 = new Activity(activityName);
        tmProject.addActivity(activity3);
        tmProject.setRecordedTime(hoursWorked);
    }

    @When("the project leader checks the total assigned time across all activities")
    public void the_project_leader_checks_the_total_assigned_time_across_all_activities() {
        totalAssignedTime = tmProject.getAssignedTime();

    }


    @Then("the total assigned time is {int} hours")
    public void the_total_assigned_time_is_hours(int totalHours) {
        assertEquals(totalHours, totalAssignedTime);
    }

    @Given("a Project with name {string} and activity with name {string} with {int} hours worked with start Date {int} {int} {int} and End date {int} {int} {int}")
    public void a_Project_with_name_and_activity_with_name_with_hours_worked_with_start_Date_and_End_date(String projectName, String activityName, int hoursWorked, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        tmProject = new Project(projectName);
        Activity activity1 = new Activity(activityName);
        tmProject.addActivity(activity1);
        tmProject.setRecordedTime(hoursWorked);
        tmProject.setStartDate(LocalDate.of(startYear, startMonth, startDay));
        tmProject.setEndDate(LocalDate.of(endYear, endMonth, endDay));
    }

    @And("activity with name {string} with {int} hours worked with start Date {int} {int} {int} and End date {int} {int} {int}")
    public void activity_with_name_with_hours_worked_with_start_Date_and_End_date(String activityName, int hoursWorked, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        Activity activity2 = new Activity(activityName);
        tmProject.addActivity(activity2);
        tmProject.setRecordedTime(hoursWorked);
        tmProject.setStartDate(LocalDate.of(startYear, startMonth, startDay));
        tmProject.setEndDate(LocalDate.of(endYear, endMonth, endDay));
    }


    @And("activity with name {string} with {int} hour worked with start Date {int} {int} {int} and End date {int} {int} {int}")
    public void activity_with_name_with_hour_worked_with_start_Date_and_End_date(String activityName, int hoursWorked, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        Activity activity3 = new Activity(activityName);
        tmProject.addActivity(activity3);
        tmProject.setRecordedTime(hoursWorked);
        tmProject.setStartDate(LocalDate.of(startYear, startMonth, startDay));
        tmProject.setEndDate(LocalDate.of(endYear, endMonth, endDay));
    }

    @When("the project leader generates a report for the project")
    public void the_project_leader_generates_a_report_for_the_project() {
        report = tmProject.generateReport();
    }

    @Then("the report contains the total assigned time of {int} hours and Start date {int} {int} {int} and End date {int} {int} {int}")
    public void the_report_contains_the_total_assigned_time_of_hours_and_Start_date_and_End_date(int totalHours, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
        LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
        assertTrue(report.contains("Status of activities:"));
        assertTrue(report.contains("Total assigned time: " + totalHours + " hours"));
        assertTrue(report.contains("Start date: " + startDate));
        assertTrue(report.contains("End date: " + endDate)); 
    }

    
    @Given("a project with name {string} with {int} hours budgeted and {int} hours registered")
    public void an_activity_with_name_with_hours_registered(String projectName, int budgetedTime, int registeredTime) {
        tmProject = new Project(projectName);
        tmProject.setBudgetedTime(budgetedTime);
        tmProject.setRecordedTime(registeredTime);
    }

    @When("an employee adds {int} hours to the registered work time")
    public void an_employee_changes_the_registered_time_from_hours_to_hours(int addedTime) {
        tmProject.addTime(addedTime);  
    }

    @Then("the project with name {string} does not add the registered time of {int} hours")
    public void the_project_with_name_does_not_add_the_registered_time_of_hours(String projectName, int addedTime) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tmProject.addTime(addedTime);
        });

        assertEquals("The added time exceeds the budgeted time.", exception.getMessage());
    }

    @Then("the project with name {string} now has {int} hours of registered time")
    public void the_activity_with_name_now_has_hours_of_registered_time(String activity, int Time) {
        assertEquals(Time, tmProject.getRecordedTime());
    }


    @Given("a Project {string} with a project leader with UID {string}")
    public void aProjectWithAProjectLeaderWithUID(String projectName, String UID) {
        app.addProject(projectName);
        tmProject =  new Project(projectName);
        app.registerUser(UID);
        tmProject.setProjectLeader(app.getUserWithUID(UID));
    }

    @When("an employee with UID {string} tries to become project leader")
    public void anEmployeeWithUIDTriesToBecomeProjectLeader(String name){
        tmProject.setProjectLeader(app.getUserWithUID(name));
    }

    @Then("the employee with UID {string} is not project leader")
    public void theemployeewithUIDisnotprojectleader(String name){
        assertNotEquals(name,tmProject.getProjectleader());
    }


    @Given("a project with name {string} with {int} hours budgeted")
    public void a_project_with_name_with_hours_budgeted(String projectName, int budgetedHours){
        tmProject = new Project(projectName);
        tmProject.setBudgetedTime(budgetedHours);
    }

    @When("the project leader checks the budgeted hours to be {int} hours")
    public void the_project_leader_checks_the_budgeted_hours(int budgetedHours) {
        budgetedHours = tmProject.getBudgetedTime();
    }

    @And("the project leader checks the registered hours to be {int} hours")
    public void the_project_leader_checks_the_registered_hours(int registeredHours) {
        registeredHours = tmProject.getRecordedTime();
    }

    @Then("the project with name {string} has {int} hours budgeted")
    public void the_project_with_name_has_hours_budgeted(String projectName, int budgetedHours) {
        assertEquals(budgetedHours, tmProject.getBudgetedTime());
    }

    @Then("the project leader sees that the budgeted hours is {int} and that the registered hours is {int}")
    public void the_project_leader_sees_that_the_budgeted_hours_is_and_that_the_registered_hours_is(int budgetedHours, int registeredHours) {
        assertEquals(budgetedHours, tmProject.getBudgetedTime());
        assertEquals(registeredHours, tmProject.getRecordedTime());
    }

    @When("an employee adds {int} hour to the registered work time")
    public void an_employee_adds_hour_to_the_registered_work_time(int registeredHours) {
        tmProject.addTime(registeredHours);
    }

    @Given("a project {string} and activity with name {string} and an employee with UID {string}")
    public void a_project_and_activity_with_name_and_an_employee_with_UID(String project, String activity, String employee){
        tmProject = new Project(project);
        tmProject.setStartDate(LocalDate.of(2024, 1, 1));
        tmProject.setEndDate(LocalDate.of(2024, 12, 31));

        activity1 = new Activity(activity, tmProject);
        tmProject.addActivity(activity1);

        app.registerUser(employee);
        activity1.assignUser(app.getUserWithUID(employee));

    }

    @When("the project leader want to set budgeted time to {int} hours from {int} hours and change the name to {string}")
    public void the_project_leader_want_to_set_budgeted_time_to_hours_from_hours_and_change_the_name_to(int newTime, int oldTime, String newName) {
        tmProject.setBudgetedTime(newTime);
        activity1.editTitle(newName);
    }

    @Then("the project {string} now has budgeted time {int} hours and activity with name {string}")
    public void the_project_now_has_budgeted_time_hours_and_activity_with_name(String projectName, int budgetedTime, String activityName) {
        assertEquals(projectName, tmProject.getName());
        assertEquals(budgetedTime, tmProject.getBudgetedTime());
        assertEquals(activityName, activity1.getTitle());
    }

    @Given("a project with name {string} and a project leader")
    public void a_project_with_name_and_a_project_leader(String projectName) {
        tmProject = new Project(projectName);
        app.addProject(projectName);
        app.registerUser("projectLeader");
        tmProject.setProjectLeader(app.getUserWithUID("projectLeader"));
    }

    @When("the user tries to log {int} hours")
    public void the_user_tries_to_log_hours(int inputHours) {
        try {
            tmProject.addTime(inputHours);
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the user is not able to log negative hours")
    public void the_user_is_not_able_to_log_negative_hours() {
        assertEquals("The added time exceeds the budgeted time.", errorMessage);
    }

    @When("the project leader tries to set start date to {int} {int} {int} and end date to {int} {int} {int}")
    public void the_project_leader_tries_to_set_start_date_to_and_end_date_to(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        try {
            tmProject.setStartDate(LocalDate.of(startYear, startMonth, startDay));
            tmProject.setEndDate(LocalDate.of(endYear, endMonth, endDay));
        } catch (IllegalArgumentException e) {
            errorMessage = e.getMessage();
        }
    }

    @Then("the project leader is not able to set start date to {int} {int} {int} and end date to {int} {int} {int}")
    public void the_project_leader_is_not_able_to_set_start_date_to_and_end_date_to(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        assertEquals("Start date cannot be after end date.", errorMessage);
    }
   
    
}

