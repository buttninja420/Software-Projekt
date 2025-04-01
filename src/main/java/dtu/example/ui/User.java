package dtu.example.ui;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String UID;

    public User(String UID){
        this.UID = UID;
        List<Activity> Activities = new ArrayList<Activity>();

    }
    public String getUID(){
        return UID;

    }
}
