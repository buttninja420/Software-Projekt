package dtu.example.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    protected List<User> Users = new ArrayList<User>();
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    public App(){}

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public int registerUser(String UID){
        
        for (User user : Users){
            if(user.getUID().equals(UID)){
                
                return -1;
            }


        }
        Users.add(new User(UID)); //create a new user with the giving UID and add to user-list

        return 0; //0 for succes
    }
    public List<User> getUsers(){
        return Users;
    }

    public List<String> getUserUIDs(){
        List<String> UIDs = new ArrayList<String>();
        for (User user : Users){
            UIDs.add(user.getUID());
        }
        return UIDs;
    }

    public User getUserWithUID(String UID){
        for (User user : Users) {
            if (user.getUID().equals(UID)) {
                return user;
            }
        }
        return null;
    }
}