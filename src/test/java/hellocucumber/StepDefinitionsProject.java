package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsProject {
    Exception thrownException;
    App app = new App();
    Project tmProject;
    //private Project tmProject;
    
    @Given("a project")
    public void a_project(String projectName) {
        tmProject = new Project(projectName);
    }

    @When("a project with name {string} is created")
    public void a_project_with_name_is_created(String projectName) {
        app.addProject(projectName);
        //System.out.println("project with name: " + projectName + "added!");
    }

    @Then("the project with name {string} exists")
    public void the_project_with_name_exists(String projectName) {
        assertEquals(projectName, tmProject.getName());
    }
    

    @Given("a Project {string} with a project leader with UID {string}")
    public void aProjectWithAProjectLeaderWithUID(String projectName, String UID) {
        // app.addProject(projectName);
        // tmProject =  app.getProject(projectName);
        // app.registerUser(UID);
        // tmProject.setProjectLeader(app.getUserWithUID(UID));
        // throw new io.cucumber.java.PendingException();
        tmProject = new Project(projectName);
        app.addProject(projectName);
        app.registerUser(UID);
        tmProject.setProjectLeader(app.getUserWithUID(UID));
        throw new io.cucumber.java.PendingException();
    }

    @When("an employee with UID {string} tries to become project leader")
    public void anEmployeeWithUIDTriesToBecomeProjectLeader(String name){
        tmProject.setProjectLeader(app.getUserWithUID(name));
        throw new io.cucumber.java.PendingException();
    }

    @Then("the employee with UID {string} is not project leader")
    public void theemployeewithUIDisnotprojectleader(String name){
        assertNotEquals(name,tmProject.getProjectleader());
    }

    @Given("a Project with name {string}")
    public void a_Project_with_name(String projectName) {
        projectName = tmProject.getName();
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
        activity1.setBudgettedTime(budgettedHours);
        //System.out.println("activity with name: " + s + "added!");
    }

    @Then("activity {string} has {int} hours worked and {int} hours available")
    public void activity_has_hours_worked_and_hours_available(String activityName, int workHours, int availableHours) {
        Activity activity1 = new Activity();
        tmProject.addActivity(activity1);
        activity1.setTitle(activityName);
        activity1.setRecordedTime(workHours);
        activity1.setBudgettedTime(availableHours);

        // Write code here that turns the phrase above into concrete actions
    }

    @Given("a Project with name {string} and activity named {string} without a project leader")
    public void a_Project_with_name_and_activity_named_without_a_project_leader(String projectName, String activityName) {
        // Write code here that turns the phrase above into concrete actions
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

}
