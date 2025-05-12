package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
//import io.cucumber.java.en_scouse.An;

//import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsApp {
    Exception thrownException;
    private App app;
    @Given("An app")
    public void anApp() {
        app = new App();
    }

    @When("A new user registers and enters a UID {string}")
    public void aNewUserRegistersAndEntersAUID(String uid) {
        app.registerUser(uid);
    }

    @Then("A user with the name {string} exists")
    public void aUserWithTheNameExists(String expectedUid) {

        // List<String> userUIDS = app.getUserUIDs();
        // Boolean foundUID = false;
        // if (!(userUIDS.isEmpty())){
        //     for (String uid : userUIDS){
        //         if (uid.equals(expectedUid)){
        //             foundUID = true;
        //             break;
        //         }
        //     }
        // }
        assertTrue(app.getUserUIDs().contains(expectedUid)); // This assertion checks if the UID matches
    }


    @Given("An app with a user with UID: {string}")
    public void anAppAndANewUserWithTheUID(String uid){
        app = new App();
        app.registerUser(uid);
    }
    protected int result;
    @When("A new user registers and enters an existing UID {string}")
    public void aNewUserRegistersAndEntersAnExistingUID(String uid) {
        result = app.registerUser(uid);
    }
    @Then("registration fails")
    public void registrationFails() {
        assertEquals(-1, result);
    }
}