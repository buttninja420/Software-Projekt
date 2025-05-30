package dtu.example.ui;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Project {
    private List<Activity> activities = new ArrayList<>();
    public List<User> assignedUsers = new ArrayList<>();

    private User leader = null;
    public int projectNumber = 0;
    public int maxTime;
    private static int idCounter;
    private String name ="";
    private LocalDate startDate;
    private LocalDate endDate;

    private int budgetedTime;
    private int recordedtime;

    //Jeppe
    public Project(String Name) {
        this.projectNumber = Integer.parseInt(String.valueOf(LocalDate.now().getYear()) +  String.valueOf(idCounter));
        idCounter++;
        this.name = Name;
    }
    
    //Jeppe
    public int setProjectLeader(User user) {
        if (this.leader == null){
            this.leader = user;
            return 0;
        }
        else {
            return -1;
        }       
        }
    
    //Jeppe
    public User getProjectleader(){
        return this.leader;
    }

    //Frank
    public List<Activity> getActivities() {
        return this.activities;
    }

    //Frank
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    //Jeppe
    public int getAssignedTime() {
        int totalAssignedTime = 0;
        for (Activity activity : activities) {
            totalAssignedTime += activity.getRecordedTime();
        }
        return totalAssignedTime;
    }

    //Jeppe
    public int getBudgetedTime() {
        return budgetedTime;
    }

    //Jeppe
    public void setBudgetedTime(int budgetedTime) {
        this.budgetedTime = budgetedTime;
    }

    //Kelvin
    public int getRecordedTime() {
        return recordedtime;
    }

    //Kelvin
    public void setRecordedTime(int recordedtime) {
        this.recordedtime = recordedtime;
    }

    //Frank
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
    } else {
        throw new IllegalArgumentException("The added time exceeds the budgeted time.");
    }
}
   
    //Frank
    public String getName(){
        return this.name;
    }

    //Kelvin
    public LocalDate getStartDate() {
        return startDate;
    }

    //Kelvin
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

    //Kelvin
    public void editDate(LocalDate newStartDate, LocalDate newEndDate) {
        if (newStartDate != null && newEndDate != null && newStartDate.isAfter(newEndDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
        this.startDate = newStartDate;
        this.endDate = newEndDate;
    }

    //Kelvin
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Report of project: " + name + "\n\n");
                report.append("Project start date: ").append(
            (startDate != null) ? startDate.toString() : "Not set"
        ).append(" | End date: ").append(
            (endDate != null) ? endDate.toString() : "Not set"
        ).append("\n");
        report.append("Project leader: " + leader.getUID() + "\n");
        report.append("Status of activities:\n");


        report.append("--------------\n");
    
        if (activities.isEmpty()) {
            report.append("No activities yet.\n");
            report.append("Start date: Not set | End date: Not set\n");
            report.append("Time status - Budgeted: 0, Recorded time: 0\n");
            report.append("--------------\n");

            return report.toString();
            
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
                      .append(", Recorded time: ").append(recordedTime).append(" hours\n");
                report.append("Employees: ");
                for (User user : activity.getAssignedUsers()){
                    report.append(user.getUID() + ", ");
                }
                report.replace(report.length()-2, report.length(), "");
                report.append("\n");
                report.append("--------------\n");
            }
        }
    
        int totalTime = getAssignedTime();
        report.append("Total recorded time: ").append(totalTime).append(" hours\n");
    
        return report.toString();
    }
    

}
