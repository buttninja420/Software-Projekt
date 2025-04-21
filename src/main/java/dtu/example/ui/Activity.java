package dtu.example.ui;
import java.util.Date;

public class Activity {
    private int activity_ID;
    private int budgeted_Time;
    private int recorded_time;
    public int add_Time;
    private User assigned_User;
    public Date start_Date;
    public Date end_Date;
    public Boolean fixed;
    public int max_users;
    public String Title;

//int activity_ID, int budgeted_Time, int recorded_time, User assigned_User, Date start_Date, Date end_Date, Boolean fixed, int max_users, String Title
    public Activity(int activity_ID, int budgeted_Time, int recorded_time, User assigned_User, Date start_Date, Date end_Date, Boolean fixed, int max_users, String Title) {
        this.activity_ID = activity_ID;
        this.budgeted_Time = budgeted_Time;
        this.recorded_time = recorded_time;
        this.assigned_User = assigned_User;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.fixed = fixed;
        this.max_users = max_users;
        this.Title = Title;
    }
    
    public Activity(){}


    
    public int getBudgeted_Time() {
        return budgeted_Time;
    }
    public void setBudgeted_Time(int budgeted_Time) {
        this.budgeted_Time = budgeted_Time;
    }
    public int getRecorded_time() {
        return recorded_time;
    }
    public void setRecorded_time(int recorded_time) {
        this.recorded_time = recorded_time;
    }
    public void addTime(int add_Time) {

        // Check if the added time exceeds the budgeted time

        if (budgeted_Time >= recorded_time + add_Time) {
            recorded_time = recorded_time + add_Time;
        } else {
            System.out.println("Error: The added time exceeds the budgeted time for the activity.");
        }

    }
    public User getAssigned_User() {
        return assigned_User;
    }
    public void setAssigned_User(User assigned_User) {
        this.assigned_User = assigned_User;
    }
    public Date getStart_Date() {
        return start_Date;
    }
    public void setStart_Date(Date start_Date) {
        this.start_Date = start_Date;
    }
    public Date getEnd_Date() {
        return end_Date;
    }
    public void setEnd_Date(Date end_Date) {
        this.end_Date = end_Date;
    }
    public Boolean getFixed() {
        return fixed;
    }
    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }
    public int getMax_users() {
        return max_users;
    }
    public void setMax_users(int max_users) {
        this.max_users = max_users;
    }
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    
    public void edit_Budgettet_Time(User new_Time){ // ændrer budgettet time

    }
    public void edit_Date(Date new_EndDate, Date new_StartDate){}
}
