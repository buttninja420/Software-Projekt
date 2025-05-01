package dtu.example.ui;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity {
    protected int activityID;
    public int BudgettedTime;
    public int recordedTime;
    public int addTime;
    protected List<User> assignedUsers = new ArrayList<User>();
    public String startDate;
    public String endDate;
    public Boolean fixed;
    public int MaxUsers;
    public String Title;

    
    public Activity(){}
    
    public int getBudgettedTime() {
        return BudgettedTime;
    }
    public void setBudgettedTime(int BudgettedTime) {
        this.BudgettedTime = BudgettedTime;
    }
    public int getRecordedTime() {
        return recordedTime;
    }
    public void setRecordedTime(int recordedTime) {
        this.recordedTime = recordedTime;
    }
    public void addTime(int addTime) {

        // Check if the added time exceeds the Budgetted time
        if (BudgettedTime >= recordedTime + addTime) {
            recordedTime = recordedTime + addTime;
        } else {
            System.out.println("Error: The added time exceeds the Budgetted time for the activity.");
        }

    }
    public List<User> getAssignedUsers() {
        return assignedUsers;
    }
    public void assignUser(User user) {
        this.assignedUsers.add(user);
        user.assignActivityDONOTUSE(this);
    }
    public void unassignUser(User user) {
        this.assignedUsers.remove(user);
        user.removeActivityDONOTUSE(this);
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public Boolean getFixed() {
        return fixed;
    }
    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }
    public int getMaxUsers() {
        return MaxUsers;
    }
    public void setMaxUsers(int MaxUsers) {
        this.MaxUsers = MaxUsers;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        this.Title = title;
    }    
    public void editBudgettetTime(int newTime, int budgettedTime){ // ændrer budgettet time
        BudgettedTime += newTime;
    }
    public void editDate(String newStartDate, String newEndDate){
        startDate = newStartDate;
        endDate = newEndDate;
    }
}
