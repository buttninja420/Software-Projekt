package dtu.example.ui;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private List<Activity> activities = new ArrayList<>();
    private List<User> assignedUsers = new ArrayList<>();

    private User leader = null;
    private int projectNumber;
    private int maxTime;
    private static int idCounter;
    private String name ="";
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
        StringBuilder report = new StringBuilder();
        report.append("Status of activities:\n");

        for (Activity activity : activities) {
            report.append("--------------\n");
            report.append(activity.getTitle()).append("\n");
            report.append("Start week: ").append(activity.getStartDate())
                  .append(" | End week: ").append(activity.getEndDate()).append("\n");
            report.append("Time status - Budgeted: ")
                  .append(activity.getBudgetedTime())
                  .append(", Assigned: ").append(activity.getRecordedTime()).append("\n");
        }

        return report.toString();
    }
    public String getName(){
        return this.name;
    }

}
