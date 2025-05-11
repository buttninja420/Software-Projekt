package dtu.example.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    private HashMap<LocalDate, Integer> workHistory = new HashMap<LocalDate, Integer>();
    private int dailyWorkTime = 8;
    private String UID;
    private List<Activity> Activities = new ArrayList<Activity>();
    public static int maxActivities = 20; 

    public User(String UID){
        this.UID = UID;
    }

    public int getDailyWorkTime(){
        return dailyWorkTime;
    }

    public void setDailyWorkTime(int newDailyWorkTime){
        dailyWorkTime = newDailyWorkTime;
    }

    public String getUID(){
        return UID;
    }

    public Boolean getAvailability(Activity possibleActivity){
        //precondition
        assert possibleActivity != null;
        
        LocalDate start = possibleActivity.getStartDate();
        LocalDate end = possibleActivity.getEndDate();
        
        //precondition
        assert start != null && end != null;
        
        //precondition
        assert !end.isBefore(start);
    
        int count = 0;
        for (Activity activity : Activities){
            if (activity.getStartDate().isBefore(end) ||  activity.getEndDate().isAfter(start)){
                count++;
            } 
        }
        if (count < maxActivities){
            return true;
        } else {
            return false;
        }        
    }
    
    

    
    public boolean getAvailabilityDate(LocalDate start, LocalDate end) {
        int count = 0;
        for (Activity activity : Activities) {
            // Hvis aktiviteten overlapper med det angivne tidsrum
            if (!(activity.getProject().getEndDate().isBefore(start) || activity.getProject().getStartDate().isAfter(end))) {
                count++;
            }
        }
        return count < 20;
    }
    
    

    public List<Activity> getActivities(){
        return Activities;
    }

    public void assignActivity(Activity activity){ //maybe delete
        Activities.add(activity);
    }

    public void removeActivity(Activity activity){
        Activities.remove(activity);
    }

    public int getMaxActivities(){
        return maxActivities;
    }


    public void registerTime(int timeRegistered){ //updates time today
        LocalDate today = LocalDate.now();
        makeSureDateExists(today);
        int newtime = workHistory.get(today) + timeRegistered;
        if (newtime < 0){
            throw new Error("cant have less than 0 hours registered per day");
        }else{
            workHistory.put(today, newtime);
        }

    } 
    
    //         LocalDate date = LocalDate.of(2025, 5, 5);
    public void registerTime(int timeRegistered, LocalDate date){
        makeSureDateExists(date);
        int prevTimeWorked = workHistory.get(date);
        int newtime = prevTimeWorked + timeRegistered;
        if(newtime < 0){
            throw new Error("cant have less than 0 hours registered per day");
        } else{
            workHistory.put(date,newtime);
        }

    }

    public String showWorkToday(){
        LocalDate today = LocalDate.now();
        makeSureDateExists(today);
        int timeWorked = workHistory.get(today);
        return "Date: " + today.toString() + "Time worked: " + timeWorked + "/" + dailyWorkTime;  
    }

    public String showWorkDate(LocalDate date){
        makeSureDateExists(date);
        int timeWorked = workHistory.get(date);
        return "Date: " + date.toString() + "Time worked: " + timeWorked + "/" + dailyWorkTime;
    }

    public void makeSureDateExists(LocalDate date){ //checks if date exists and if it doesnt create a new date entry.
        if (workHistory.get(date) == null){
            workHistory.put(date,0);
        }

    }

    public int getHoursToday() {
        LocalDate today = LocalDate.now();
        makeSureDateExists(today);
        return workHistory.get(today);
    }
}
