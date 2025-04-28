package dtu.example.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private List<Project> projekter = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
        HBox mainLay = new HBox(120);
        mainLay.setPadding(new Insets(20));
        mainLay.setAlignment(Pos.TOP_LEFT);

        VBox VenstreBlok = new VBox(30);
        VenstreBlok.setAlignment(Pos.TOP_LEFT);

        Label infoLabel = new Label("Tryk på følgende knap for at lave et nyt projekt:");

        Button ProjektKnap = new Button("Lav et NYT PROJEKT");
        ProjektKnap.setPrefHeight(60);
        ProjektKnap.setPrefWidth(200);

        VenstreBlok.getChildren().addAll(infoLabel, ProjektKnap);

        VBox HøjreKnap = new VBox(30);
        HøjreKnap.setAlignment(Pos.TOP_LEFT);

        Label ProjektLabel = new Label("Projects");
        HøjreKnap.getChildren().add(ProjektLabel);

        mainLay.getChildren().addAll(VenstreBlok, HøjreKnap);

        Scene scene = new Scene(mainLay, 800, 480);
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
    public Project getProject(String projectName){
        for (Project project : projekter){
            return project;
        }
        return null;
    }
    public void addProject(String projectName){
        projekter.add(new Project(projectName));
    }

    
}





