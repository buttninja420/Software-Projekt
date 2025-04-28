package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsProject {
    Exception thrownException;
    private App app;
    private Project tmProject;
    
    @Given("An app")
    public void anApp() {
        // Initialize app and user logic here
         app = new App();
         // Store UID to use later in the test
    }

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

}
