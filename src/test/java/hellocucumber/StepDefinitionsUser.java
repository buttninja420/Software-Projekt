package hellocucumber;

import dtu.example.ui.*;
import io.cucumber.java.en.*;
import io.cucumber.java.en_scouse.An;
import java.time.LocalDate;

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

    @And("user is not registered to any activity")
    public void user_is_not_registered_to_any_activity() {
        assertEquals(testUser.getActivities().size(), 0);
    }

    @When("user checks availability in time span {int} {int} {int} to {int} {int} {int}")
    public void user_checks_availability_in_time_span(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        LocalDate start = LocalDate.of(startYear, startMonth, startDay);
        LocalDate end = LocalDate.of(endYear, endMonth, endDay);
        testUser.getAvailabilityDate(start, end);
    }


    @Then("user with UID: {string} is available in time span {int} {int} {int} to {int} {int} {int}")
    public void user_with_UID_is_available_in_time_span(String s, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        LocalDate start = LocalDate.of(startYear, startMonth, startDay);
        LocalDate end = LocalDate.of(endYear, endMonth, endDay);
        assertTrue(testUser.getAvailabilityDate(start, end));
    } 


    @When ("user registers {int} hours worked")
    public void user_with_UID_registers_hours_worked(int hours){
        testUser.registerTime(hours);
    }

    @Then ("user with UID: {string} has {int} hours worked today")
    public void user_with_UID_has_hours_worked_today(String UID, int hours){
        assertEquals(testUser.getHoursToday(), hours);
    }

    

    
}
