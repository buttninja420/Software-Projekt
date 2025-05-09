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

    
    public Project(String Name) {
        this.projectNumber = idCounter++;
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
        } else { // 2
            for (Activity activity : activities) { // 3
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
    
    

    public String getName(){
        return this.name;
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
    

}
