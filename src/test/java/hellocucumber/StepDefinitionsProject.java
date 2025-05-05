package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsProject {
    Exception thrownException;
    App app = new App();
    Project tmProject;
    int totalAssignedTime = 0;
    String report;
    //private Project tmProject;
    
    @Given("an app that exist")
    public void an_app_that_exist() {
        app = new App();
    }
    
    @When("a project with name {string} is created")
    public void a_project_with_name_is_created(String projectName) {
        app.addProject(projectName);
        tmProject = app.getProject(projectName);
        //System.out.println("project with name: " + projectName + "added!");
    }

    @Then("the project with name {string} exists")
    public void the_project_with_name_exists(String projectName) {
        assertEquals(projectName, tmProject.getName());
    }
    

    @Given("a Project {string} with a project leader with UID {string}")
    public void aProjectWithAProjectLeaderWithUID(String projectName, String UID) {
        app.addProject(projectName);
        tmProject =  app.getProject(projectName);
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

    @Given("a Project with name {string}")
    public void a_Project_with_name(String projectName) {
        app.addProject(projectName);
        tmProject = app.getProject(projectName);
    }

    @When("an employee who is not the project leader adds new activity with name {string}")
    public void an_employee_who_is_not_the_project_leader_adds_new_activity_with_name(String activityName) {
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(activityName);
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

    @Given("a Project with name {string} and activity named {string}")
    public void a_Project_with_name_and_activity_named(String ProjectName, String ActivityName) {
        // Write code here that turns the phrase above into concrete actions
        tmProject = new Project(ProjectName);
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(ActivityName);
    }

    @When("an employee with UID {string} registers time for the activity {string} with {int} hours")
    public void an_employee_with_UID_registers_time_for_the_activity_with_hours(String UID, String ActivityName, int WorkHours) {
        app.registerUser(UID);
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(ActivityName);
        activity1.setRecordedTime(WorkHours);
    }

    @And("activity {string} has {int} hours worked")
    public void activity_has_hours_worked(String ActivityName, int RegisteredHours) {
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(ActivityName);
        activity1.setRecordedTime(RegisteredHours);

    }

    @And("activity {string} has {int} available hours")
    public void activity_has_available_hours(String activityName, int budgettedHours) {
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(activityName);
        activity1.setBudgetedTime(budgettedHours);
        //System.out.println("activity with name: " + s + "added!");
    }

    @Then("activity {string} has {int} hours worked and {int} hours available")
    public void activity_has_hours_worked_and_hours_available(String activityName, int workHours, int availableHours) {
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(activityName);
        activity1.setRecordedTime(workHours);
        activity1.setBudgetedTime(availableHours);
    }

    @Given("a Project with name {string} and activity named {string} without a project leader")
    public void a_Project_with_name_and_activity_named_without_a_project_leader(String projectName, String activityName) {
        app = new App();
        tmProject = new Project(projectName);
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(activityName);
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
    public void a_Project_with_name_and_activity_with_name_with_hours_worked(String projectName, String acitivty1Name, int hoursWorked) {
        tmProject = new Project(projectName);
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(acitivty1Name);
        activity1.setRecordedTime(hoursWorked);
    }

    @And("activity with name {string} with {int} hour worked")
    public void activity_with_name_with_hour_worked(String activity2Name, int hoursWorked) {
        Activity activity2 = new Activity();
        tmProject.addActivity(activity2);
        activity2.setTitle(activity2Name);
        activity2.setRecordedTime(hoursWorked);
        
    }

    @And("activity with name {string} with {int} hours worked")
    public void activity_with_name_with_hours_worked(String activity3Name, int hoursWorked) {
        Activity activity3 = new Activity();
        tmProject.addActivity(activity3);
        activity3.setTitle(activity3Name);
        activity3.setRecordedTime(hoursWorked);
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
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(activityName);
        activity1.setRecordedTime(hoursWorked);
        activity1.setStartDate(LocalDate.of(startYear, startMonth, startDay));
        activity1.setEndDate(LocalDate.of(endYear, endMonth, endDay));
    }

    @And("activity with name {string} with {int} hours worked with start Date {int} {int} {int} and End date {int} {int} {int}")
    public void activity_with_name_with_hours_worked_with_start_Date_and_End_date(String activityName, int hoursWorked, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        Activity activity2 = new Activity();
        tmProject.addActivity(activity2);
        activity2.setTitle(activityName);
        activity2.setRecordedTime(hoursWorked);
        activity2.setStartDate(LocalDate.of(startYear, startMonth, startDay));
        activity2.setEndDate(LocalDate.of(endYear, endMonth, endDay));
    }


    @And("activity with name {string} with {int} hour worked with start Date {int} {int} {int} and End date {int} {int} {int}")
    public void activity_with_name_with_hour_worked_with_start_Date_and_End_date(String activityName, int hoursWorked, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        Activity activity3 = new Activity();
        tmProject.addActivity(activity3);
        activity3.setTitle(activityName);
        activity3.setRecordedTime(hoursWorked);
        activity3.setStartDate(LocalDate.of(startYear, startMonth, startDay));
        activity3.setEndDate(LocalDate.of(endYear, endMonth, endDay));
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
   
}
