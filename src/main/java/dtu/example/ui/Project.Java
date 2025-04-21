package dtu.example.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Project {
    Private List<Activity> Activities = new ArrayList<Activity>();
    Private List<User> assignedUsers = new ArrayList<User>();

    private User Leader = Null;
    private int projectNumber;
    private int maxTime;
    private static int idCounter;

    public Project(){
        this.ProjectNumber = idCounter++;
        
    }

    public void setProjectLeader(User user){
        this.Leader = user;
    } 
    
    public List<Activity> getActivities(){
        return this.Activities;
    }
    
    public void addActivity(Activity activity){
        Activities.add(activity);
    }

    public int getAssignedTime(){
        for ()

    }

    public String generateReport(){
        String report = "";

        report = report + "status of activities:\n";
        for (Activity activity : Activities) {
            String activityStatus = "--------------\n";
            activityStatus += activity.getTitle() + "\n";
            activityStatus += "Start week: " + activity.getStartWeek() + " | End week: " + activity.getEndWeek() + "\n";
            activityStatus += "Time status - Budgeted: " + activity.getBudgetedTime() + ", Assigned: " + activity.getAssignedTime() + "\n";
    
            report += activityStatus;
        }

        return report;
    }
}


