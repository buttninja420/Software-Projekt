package dtu.example.ui;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Activity {
    private int budgetedTime = 0;
    private int recordedtime;
    public int addTime;
    private List<User> assignedUsers = new ArrayList<User>();
    private LocalDate startDate;
    private LocalDate endDate;
    public Boolean fixed;
    public int maxusers;
    public String title;

    public Project project;

    //Jeppe
    public Activity(String title, Project project){
        this.title = title;
        this.project = project;
    }

    //Jeppe
    public Project getProject() {
        return this.project;
    }

    //Frank
    public Activity(String title){
        this.title = title;
    }
    
    //Jeppe
    public int getBudgetedTime() {
        return budgetedTime;
    }

    //Jeppe
    public void setBudgetedTime(int budgetedTime) {
        this.budgetedTime = budgetedTime;
    }

    //Frank
    public int getRecordedTime() {
        return recordedtime;
    }

    //Frank
    public void setRecordedTime(int recordedtime) {
        this.recordedtime = recordedtime;
    }

    //Jeppe
    public void addTime(int addTime) {
    if (addTime < 0) {
        throw new IllegalArgumentException("Precondition failed: Cannot add negative time.");
    }
    if (addTime == 0) {
        throw new IllegalArgumentException("Precondition failed: Cannot add zero time.");
    }

    if (budgetedTime >= recordedtime + addTime) {
        recordedtime += addTime;
    } else {
        throw new IllegalArgumentException("The added time exceeds the budgeted time.");
    }
    }

    //Jeppe
    public List<User> getAssignedUsers() {
        return assignedUsers;
    }

    //Jeppe
    public int assignUser(User user) {
        if (user.getAvailability(this)){
            this.assignedUsers.add(user);
            user.assignActivityDONOTUSE(this);
            return 0;
        }
        return -1;
    }

    public int assignUserAsProjectLeader(Project projectName, User projectLeader, User user) {
        if (projectName.getProjectleader() == projectLeader) {
            if (user.getAvailability(this)) {
                this.assignedUsers.add(user);
                user.assignActivityDONOTUSE(this);
                return 0;
            }
        }
        return -1;
    }

    //Frank
    public int unassignUser(User user){
        if(assignedUsers.remove(user)){
            user.removeActivityDONOTUSE(this);
            return 0;
        }else{
            return -1;
        }
    }

    //Jeppe
    public LocalDate getStartDate() {
        return startDate;
    }

    //Jeppe
    public void setStartDate(LocalDate startDate) {
        if (this.endDate != null && startDate.isAfter(this.endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        this.startDate = startDate;
    }
    
    //Frank
    public LocalDate getEndDate() {
        return endDate;
    }

    //Frank
    public void setEndDate(LocalDate endDate) {
        if (this.startDate != null && endDate.isBefore(this.startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        this.endDate = endDate;
    }
    
    //Frank
    public Boolean getFixed() {
        return fixed;
    }

    //Frank
    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    //Jeppe
    public int getMaxUsers() {
        return maxusers;
    }

    //Frank
    public void setMaxUsers(int maxusers) {
        this.maxusers = maxusers;
    }

    //Jeppe
    public String getTitle() {
        return title;
    }

    //Jeppe
    public void editTitle(String title) {
        this.title = title;
    }
    
    //Frank
    public void editBudgetedTime(int newTime){ // ændrer budgettet time
        budgetedTime = newTime; 
    }

    //Frank
    public void editDate(LocalDate newStartDate, LocalDate newEndDate) {
        if (newStartDate != null && newEndDate != null && newStartDate.isAfter(newEndDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        this.startDate = newStartDate;
        this.endDate = newEndDate;
    }
    

}
