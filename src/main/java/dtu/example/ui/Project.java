package dtu.example.ui;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Project {
    private List<Activity> activities = new ArrayList<>();
    private List<User> assignedUsers = new ArrayList<>();

    private User leader = null;
    private int projectNumber = 0;
    private int maxTime;
    private static int idCounter;
    private String name ="";
    private LocalDate startDate;
    private LocalDate endDate;

    private int budgetedTime;
    private int recordedtime;

    
    public Project(String Name) {
        this.projectNumber = Integer.parseInt(String.valueOf(LocalDate.now().getYear()) +  String.valueOf(idCounter));
        idCounter++;
        this.name = Name;
    }
    
    public int setProjectLeader(User user) {
        if (this.leader == null){
            this.leader = user;
            return 0;
        }
        else {
            return -1;
        }       
        }
    
    public User getProjectleader(){
        return this.leader;
    }

    public List<Activity> getActivities() {
        return this.activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public int getAssignedTime() {
        int totalAssignedTime = 0;
        for (Activity activity : activities) {
            totalAssignedTime += activity.getRecordedTime();
        }
        return totalAssignedTime;
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
    if (addTime < 0) {
        throw new IllegalArgumentException("Precondition failed: Cannot add negative time.");
    }
    if (addTime == 0) {
        throw new IllegalArgumentException("Precondition failed: Cannot add zero time.");
    }

    int oldRecorded = recordedtime;

    if (budgetedTime >= recordedtime + addTime) {
        recordedtime += addTime;
        assert recordedtime == oldRecorded + addTime : "Postcondition failed: Time not added correctly.";
    } else {
        assert recordedtime == oldRecorded : "Postcondition failed: recordedtime should not change when over budget.";
        throw new IllegalArgumentException("The added time exceeds the budgeted time.");
    }
}
   

    public String getName(){
        return this.name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        if (this.endDate != null && startDate.isAfter(this.endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (this.startDate != null && endDate.isBefore(this.startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date.");
        }
        this.endDate = endDate;
    }

    public void editDate(LocalDate newStartDate, LocalDate newEndDate) {
        if (newStartDate != null && newEndDate != null && newStartDate.isAfter(newEndDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        this.startDate = newStartDate;
        this.endDate = newEndDate;
    }

    public String generateReport() {
        assert true;
        StringBuilder report = new StringBuilder();
        report.append("Status of activities:\n");
        report.append("Project start date: ").append(
            (startDate != null) ? startDate.toString() : "Not set"
        ).append(" | End date: ").append(
            (endDate != null) ? endDate.toString() : "Not set"
        ).append("\n");

        report.append("--------------\n");
    
        if (activities.isEmpty()) { // 1
            report.append("No activities yet.\n");
            report.append("Start date: Not set | End date: Not set\n");
            report.append("Time status - Budgeted: 0, Assigned: 0\n");
            report.append("--------------\n");
        } else {
            for (Activity activity : activities) {
                String startDate = (activity.getStartDate() != null) ? activity.getStartDate().toString() : "Not set";
                String endDate = (activity.getEndDate() != null) ? activity.getEndDate().toString() : "Not set";
                int budgetedTime = activity.getBudgetedTime();
                int recordedTime = activity.getRecordedTime();
    
                report.append(activity.getTitle()).append("\n");
                report.append("Start date: ").append(startDate)
                      .append(" | End date: ").append(endDate).append("\n");
                report.append("Time status - Budgeted: ").append(budgetedTime)
                      .append(", Assigned: ").append(recordedTime).append("\n");
                report.append("--------------\n");
            }
        }
    
        int totalTime = getAssignedTime();
        report.append("Total assigned time: ").append(totalTime).append(" hours\n");
    
        return report.toString();
    }
    

}
