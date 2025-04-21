package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsApp {

    private App app;
    @Given("An app")
    public void anApp() {
        // Initialize app and user logic here
         app = new App();
         // Store UID to use later in the test
    }

    @When("A new user registers and enters a UID {string}")
    public void aNewUserRegistersAndEntersAUID(String uid) {
        // Simulate registration logic here
        app.registerUser(uid);
        //System.out.println("user with UID: " + uid + "added!");
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


    @Given("An app with a user with UID: {string}")
    public void anAppAndANewUserWithTheUID(String uid){
        app = new App();
        app.registerUser(uid);
    }

    @Then("An error: {string} is thrown")
    public void anErrorIsShown(String expectedErrorMessage){
        boolean exceptionThrown = false;
        try {
            
            app.registerUser("ELLE");  // Trying to register again with the same UID

        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            assertEquals(expectedErrorMessage, e.getMessage());  // Check if the error message matches the expected one
        }
        assertTrue(exceptionThrown,"Expected exception was thrown");
    }
}