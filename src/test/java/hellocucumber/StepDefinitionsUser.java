package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsUser {
    App app = new App();
    public User testUser;
    @Given("An app with a user with UID: {string} exists")
    public void An_app_with_a_user_with_UID_exists(String s) {
        testUser = new User(s);
        String testUID = testUser.getUID();
        app.registerUser(testUID);
        testUser = app.getUserWithUID(testUID);

    }

    @Then("user with UID: {string} is available")
    public void user_with_UID_is_available(String s) {

        assertTrue(testUser.getAvailability());
    }


    @When ("user registers {int} hours worked")
    public void user_with_UID_registers_hours_worked(int hours){
        testUser.registerTime(hours);
    }

    @Then ("user with UID: {string} has {int} hours worked today")
    public void user_with_UID_has_hours_worked_today(int hours){
        
        assertEquals(testUser.getHoursToday(), hours);
    }

}
