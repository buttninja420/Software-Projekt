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
    public Project tmProject;
    public User testUser;
    public Activity activity;
    public String errorMessage; 
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
        try{
            testUser.registerTime(hours);
        }catch(Error e){
            errorMessage = e.getMessage();
        }

    }

    @Then ("user with UID: {string} has {int} hours worked today")
    public void user_with_UID_has_hours_worked_today(String UID, int hours){
        assertEquals(hours, testUser.getHoursToday());
    }

    @When("user defines daily work time to be {int} hours")
    public void user_defines_daily_work_time_to_be_hours(int dailyWorkTime) {
        testUser.setDailyWorkTime(dailyWorkTime);
    }

    @Then("user with UID: {string} has daily work time of {int} hours")
    public void user_with_UID_has_daily_work_time_of_hours(String UID, int workTime) {
        assertEquals(testUser.getDailyWorkTime(), workTime);
    }

    @And("user is registered to {int} activities in time span {int} {int} {int} to {int} {int} {int}")
    public void user_is_registered_to_activities_in_time_span_to(int activeActivities, int StartYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        LocalDate start = LocalDate.of(StartYear, startMonth, startDay);
        LocalDate end = LocalDate.of(endYear, endMonth, endDay);
        tmProject = new Project("Project1");
        tmProject.setStartDate(start);
        tmProject.setEndDate(end);

        for (int i = 0; i < activeActivities; i++){
            activity = new Activity("Activity" + i, tmProject);
            activity.setStartDate(start);
            activity.setEndDate(end);
            activity.assignUser(testUser);

            testUser.getActivities().add(activity);
        }
    }


    @Then("user with UID: {string} has reached max activities and is not able to register for activity in time span {int} {int} {int} to {int} {int} {int}")
    public void user_with_UID_has_reached_max_activities_and_is_not_able_to_register_for_activity_in_time_span_to(String UID, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        LocalDate start = LocalDate.of(startYear, startMonth, startDay);
        LocalDate end = LocalDate.of(endYear, endMonth, endDay);
        assertFalse(testUser.getAvailabilityDate(start, end));
        assertTrue(testUser.getActivities().size() >= testUser.getMaxActivities());
        
    }

    @When("user tries to register for activity with start date {int} {int} {int} and end date {int} {int} {int}")
    public void user_tries_to_register_for_activity_with_start_date_and_end_date(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        LocalDate start = LocalDate.of(startYear, startMonth, startDay);
        LocalDate end = LocalDate.of(endYear, endMonth, endDay);
        activity = new Activity("Activity1", tmProject);
        activity.setStartDate(start);
        activity.setEndDate(end);
        activity.assignUser(testUser);
        testUser.getActivities().add(activity);
    }

    @Then("user with UID: {string} is not able to register for activity with start date {int} {int} {int} and end date {int} {int} {int}")
    public void user_with_UID_is_not_able_to_register_for_activity_with_start_date_and_end_date(String UID, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        assertFalse(testUser.getAvailability(activity));
    }

    @And("user has {int} hours worked today")
    public void user_has_hours_worked_today(int workedHours) {
        try{
            testUser.registerTime(workedHours);
        }catch(Error e){
            errorMessage = e.getMessage();
        }

    }

    @And("user registers {int} hours today and {int} hours on date {int} {int} {int}")
    public void user_registers_hours_today_and_hours_on_date(int registerHours, int otherHours, int Year, int Month, int Day) {
        testUser.registerTime(registerHours);
        LocalDate date = LocalDate.of(Year, Month, Day);
        testUser.registerTime(otherHours, date);
    }

    @When("user checks work time today and on date {int} {int} {int}")
    public void user_checks_work_time_today_and_on_date(int Year, int Month, int Day) {
        LocalDate date = LocalDate.of(Year, Month, Day);
        testUser.showWorkToday();
        testUser.showWorkDate(date);
    }

    @Then("user with UID: {string} has {int} hours worked today and {int} hours on date {int} {int} {int}")
    public void user_with_UID_has_hours_worked_today_and_hours_on_date(String UID, int workedHours, int otherHours, int Year, int Month, int Day) {
        LocalDate date = LocalDate.of(Year, Month, Day);
        assertEquals(testUser.getHoursToday(), workedHours);
        assertEquals(testUser.showWorkDate(date), "Date: " + date.toString() + "Time worked: " + otherHours + "/" + 8);
    }
    
    @When("user checks availability for activity with start date {int} {int} {int} and end date {int} {int} {int}")
    public void user_checks_availability_for_activity_with_start_date_and_end_date(int i, int i2, int i3, int i4, int i5, int i6) {
        LocalDate start = LocalDate.of(i, i2, i3);
        LocalDate end = LocalDate.of(i4, i5, i6);
        tmProject = new Project("Project1");
        tmProject.setStartDate(start);
        tmProject.setEndDate(end);

        activity = new Activity("Activity1", tmProject);
        activity.setStartDate(start);
        activity.setEndDate(end);

        testUser.getActivities().add(activity);
        activity.assignUser(testUser);
    }

    @Then("user with UID: {string} is available for activity with start date {int} {int} {int} and end date {int} {int} {int}")
    public void user_with_UID_is_available_for_activity_with_start_date_and_end_date(String s, int i, int i2, int i3, int i4, int i5, int i6) {
        assertTrue(testUser.getActivities().contains(activity));
    }

    
    @Given ("user with UID: {string} has {int} hours worked")
    public void set_hours_Worked_for_user_with_UID(String UID, int hoursWorked){
        app.getUserWithUID(UID).registerTime(hoursWorked);
    }

    @Then( "An error: {string} is thrown")
        public void assert_correct_error_thrown(String correctErrorMessage){
        assertEquals(correctErrorMessage, errorMessage);
    }
    


}
