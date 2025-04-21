package dtu.example.ui;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity {
    private int activityID;
    private int budgetedTime;
    private int recordedtime;
    public int addTime;
    private List<User> assignedUsers = new ArrayList<User>();
    public Date startDate;
    public Date endDate;
    public Boolean fixed;
    public int maxusers;
    public String Title;

//int activityID, int budgetedTime, int recordedtime, User assignedUser, Date startDate, Date endDate, Boolean fixed, int maxusers, String Title
    public Activity(int activityID, int budgetedTime, int recordedtime, List<User> assignedUsers, Date startDate, Date endDate, Boolean fixed, int maxusers, String Title) {
        this.activityID = activityID;
        this.budgetedTime = budgetedTime;
        this.recordedtime = recordedtime;
        this.assignedUsers = assignedUsers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.fixed = fixed;
        this.maxusers = maxusers;
        this.Title = Title;
    }
    
    public Activity(){}
    
    public int getBudgetedTime() {
        return budgetedTime;
    }
    public void setBudgetedTime(int budgetedTime) {
        this.budgetedTime = budgetedTime;
    }
    public int getRecordedtime() {
        return recordedtime;
    }
    public void setRecordedtime(int recordedtime) {
        this.recordedtime = recordedtime;
    }
    public void addTime(int addTime) {

        // Check if the added time exceeds the budgeted time

        if (budgetedTime >= recordedtime + addTime) {
            recordedtime = recordedtime + addTime;
        } else {
            System.out.println("Error: The added time exceeds the budgeted time for the activity.");
        }

    }
    public List<User> getAssignedUser() {
        return assignedUsers;
    }
    public void assignedUser(User user) {
        this.assignedUsers.add(user);
        user.assignActivityDONOTUSE(this);
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public Boolean getFixed() {
        return fixed;
    }
    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }
    public int getMaxusers() {
        return maxusers;
    }
    public void setMaxusers(int maxusers) {
        this.maxusers = maxusers;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    
    public void editBudgettetTime(User newTime){ // ændrer budgettet time

    }
    public void editDate(Date newEndDate, Date newStartDate){}
}
