package dtu.example.ui;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Activity {
    protected int activityID;
    protected int BudgettedTime;
    protected int recordedTime;
    public int addTime;
    protected List<User> assignedUsers = new ArrayList<User>();
    public Date startDate;
    public Date endDate;
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
        Title = title;
    }
    
    public void editBudgettetTime(User newTime){ // ændrer budgettet time

    }
    public void editDate(Date newEndDate, Date newStartDate){}
}
