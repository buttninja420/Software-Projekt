package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    private App app;
    @Given("An app")
    public void anAppAndANewUserWithTheUID() {
        // Initialize app and user logic here
         app = new App();
         // Store UID to use later in the test
    }

    @When("A new user registers and enters a UID {string}")
    public void aNewUserRegistersAndEntersAUID(String uid) {
        // Simulate registration logic here
        app.registerUser(uid);
        System.out.println("user with UID: " + uid + "added!");
    }

    @Then("A user with the name {string} exists")
    public void aUserWithTheNameExists(String expectedUid) {
        // Validate that the user with the given UID exists
        List<String> userUIDS = app.getUsers();
        Boolean foundUID = false;
        if (!(userUIDS.isEmpty())){
            for (String uid : userUIDS){
                if (uid.equals(expectedUid)){
                    foundUID = true;
                    break;
                }
            }
        }

        assertEquals(foundUID, true); // This assertion checks if the UID matches
    }
}