package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitionsUser {
    App app = new App();
    @Given("An app with a user with UID: {string} exists")
    public void An_app_with_a_user_with_UID_exists(String s) {
        app.registerUser(s);
    }

    @Then("user with UID: {string} is available")
    public void user_with_UID_is_available(String s) {
        User user = app.getUserWithUID(s);
        assertTrue(user.getAvailability());
    }
}
