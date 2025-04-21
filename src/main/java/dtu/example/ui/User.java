package dtu.example.ui;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String UID;
    private List<Activity> Activities = new ArrayList<Activity>();
    private static int maxActivities = 20; 

    public User(String UID){
        this.UID = UID;
    }

    public String getUID(){
        return UID;
    }

    public Boolean getAvailability(){
        if (Activities.size() < maxActivities){
            return true;
        } else {return false;}
    }

    public List<Activity> getActivities(){
        return Activities;
    }

    public void assignActivityDONOTUSE(Activity activity){ //maybe delete
        Activities.add(activity);
    }
}
