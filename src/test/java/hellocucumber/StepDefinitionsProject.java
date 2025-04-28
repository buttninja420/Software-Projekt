// package hellocucumber;

// import dtu.example.ui.*;
// import io.cucumber.java.en.*;
// import io.cucumber.java.en_scouse.An;

// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

<<<<<<< HEAD
// public class StepDefinitionsProject {
//     Exception thrownException;
//     private App app;
//     @Given("An app")
//     public void anApp() {
//         // Initialize app and user logic here
//          app = new App();
//          // Store UID to use later in the test
//     }
//     @Given("a Project {string} with a project leader with UID {string}")
//     public void aProjectWithAProjectLeaderWithUID(String projectName, String UID) {
//         app.addProject(projectName);
//         app.get
//         throw new io.cucumber.java.PendingException();
//     }
//     @When("an employee with UID {string} tries to become project leader")
//     public void anEmployeeWithUIDTriesToBecomeProjectLeader(String string) {
//         // Write code here that turns the phrase above into concrete actions
//         throw new io.cucumber.java.PendingException();
//     }
//     @When("Project leader with UID {string} already exists on project")
//     public void projectLeaderWithUIDAlreadyExistsOnProject(String string) {
//         // Write code here that turns the phrase above into concrete actions
//         throw new io.cucumber.java.PendingException();
//     }
//     @Then("the employee with UID {string} is not project leader")
//     public void theemployeewithUIDisnotprojectleader(String string) {
//         // Write code here that turns the phrase above into concrete actions
//         throw new io.cucumber.java.PendingException();
//     }
// }
=======
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
>>>>>>> 73fc6eede713a61b922dc15a202e6bfe9f116b25
