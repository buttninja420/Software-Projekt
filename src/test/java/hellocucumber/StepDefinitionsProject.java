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

    @Given("a Project {string} with a project leader with UID {string}")
    public void aProjectWithAProjectLeaderWithUID(String projectName, String UID) {
        app.addProject(projectName);
        tmProject =  app.getProject(projectName);
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
        assertEquals(name,tmProject.getProjectleader());

        throw new io.cucumber.java.PendingException();
    }
}
