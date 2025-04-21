package hellocucumber;

import dtu.example.ui.Project;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsProject {
    
    private Project project;
    @Given("a project")
    public void aProject() {
        // Initialize app and user logic here
        project = new Project();
        // Store UID to use later in the test
    }

    @Given("a Project with a project leader with UID {string}")
    public void a_Project_with_a_project_leader_with_UID(String s) {
        
    }

}
