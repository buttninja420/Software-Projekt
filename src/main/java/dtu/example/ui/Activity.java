package dtu.example.ui;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Activity {
    private int budgetedTime;
    private int recordedtime;
    public int addTime;
    private List<User> assignedUsers = new ArrayList<User>();
    public LocalDate startDate;
    public LocalDate endDate;
    public Boolean fixed;
    public int maxusers;
    public String title;

    public Project project;

    public Activity(String title, Project project){
        this.title = title;
        this.project = project;
    }

    public Project getProject() {
        return this.project;
    }

    public Activity(String title){
        this.title = title;
    }
    
    public int getBudgetedTime() {
        return budgetedTime;
    }
    public void setBudgetedTime(int budgetedTime) {
        this.budgetedTime = budgetedTime;
    }
    public int getRecordedTime() {
        return recordedtime;
    }
    public void setRecordedTime(int recordedtime) {
        this.recordedtime = recordedtime;
    }

    public void addTime(int addTime) {
        assert addTime > 0 : "Precondition failed: Cannot add negative time.";
        assert addTime != 0 : "Precondition failed: Cannot add zero time.";

        int oldRecorded = recordedtime;

        if (budgetedTime >= recordedtime + addTime) {
            recordedtime += addTime;
            assert recordedtime == oldRecorded + addTime : "Postcondition failed: Time not added correctly.";
        } else {
            assert recordedtime == oldRecorded : "Postcondition failed: recordedtime should not change when over budget.";
            throw new IllegalArgumentException("The added time exceeds the budgeted time.");
        }
    }

    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    public int assignUser(User user) {
        if (user.getAvailability(this)){
            this.assignedUsers.add(user);
            user.assignActivityDONOTUSE(this);
            return 0;
        }
        return -1;
    }

    public int unassignUser(User user){
        if(assignedUsers.remove(user)){
            user.removeActivityDONOTUSE(this);
            return 0;
        }else{
            return -1;
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Boolean getFixed() {
        return fixed;
    }
    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }
    public int getMaxUsers() {
        return maxusers;
    }
    public void setMaxUsers(int maxusers) {
        this.maxusers = maxusers;
    }
    public String getTitle() {
        return title;
    }
    public void editTitle(String title) {
        this.title = title;
    }
    
    public void editBudgetedTime(int newTime){ // ændrer budgettet time
        budgetedTime = newTime; 
    }
    public void editDate(LocalDate newStartDate, LocalDate newEndDate){
        endDate = newEndDate; 
        startDate = newStartDate;
    }

}
