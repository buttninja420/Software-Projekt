package dtu.example.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    protected List<User> Users = new ArrayList<>();
    protected List<Project> projects = new ArrayList<>();
    private GridPane projectListBox; 

    @Override
    public void start(Stage stage) throws IOException {

        projects.add(new Project("1234"));
        projects.add(new Project("2345"));
        projects.add(new Project("3456"));
        projects.add(new Project("4567"));

        HBox mainLay = new HBox(120);
        mainLay.setPadding(new Insets(20));
        mainLay.setAlignment(Pos.TOP_LEFT);

        VBox VenstreBlok = new VBox(30);
        VenstreBlok.setAlignment(Pos.TOP_LEFT);

        Label infoLabel = new Label("Press here to make a new project:");

        Button ProjektKnap = new Button("Make a NEW project");
        ProjektKnap.setPrefHeight(60);
        ProjektKnap.setPrefWidth(200);

        VenstreBlok.getChildren().addAll(infoLabel, ProjektKnap);

        projectListBox = new GridPane();
        projectListBox.setHgap(20);
        projectListBox.setVgap(20);
        projectListBox.setAlignment(Pos. TOP_LEFT); 

        ProjektKnap.setOnAction(event -> {
            newProjectWindow();
        });

        opdaterProjektListe(); 

        mainLay.getChildren().addAll(VenstreBlok, projectListBox);

        scene = new Scene(mainLay, 800, 480);
        stage.setScene(scene);
        stage.show();
    }

    public App() {}

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

    public int registerUser(String UID) {
        for (User user : Users) {
            if (user.getUID().equals(UID)) {
                return -1;
            }
        }
        Users.add(new User(UID));
        return 0;
    }

    public List<User> getUsers() {
        return Users;
    }

    public List<String> getUserUIDs() {
        List<String> UIDs = new ArrayList<>();
        for (User user : Users) {
            UIDs.add(user.getUID());
        }
        return UIDs;
    }

    public User getUserWithUID(String UID) {
        for (User user : Users) {
            if (user.getUID().equals(UID)) {
                return user;
            }
        }
        return null;
    }

    public Project getProject(String projectName) {
        for (Project project : projects) {
            if (project.getName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }

    public void addProject(String projectName) {
        projects.add(new Project(projectName));
    }

    private void newProjectWindow() {
        Stage newProjectWindow = new Stage();
        newProjectWindow.setTitle("New project");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.TOP_LEFT);

        Label label1 = new Label("Here you can make a new project. Start with giving \nthe new project a name and then you can edit the \nproject on the main window.");
        label1.setPrefWidth(280);
        Label nameLabel = new Label("Name of new project:");
        TextField newName = new TextField();
        Button createButton = new Button("Opret");

        createButton.setOnAction(e -> {
            String navn = newName.getText().trim();
            if (!navn.isEmpty()) {
                addProject(navn);
                opdaterProjektListe();
                newProjectWindow.close();
            }
        });

        layout.getChildren().addAll(label1, nameLabel, newName, createButton);

        Scene scene = new Scene(layout, 300, 200);
        newProjectWindow.setScene(scene);
        newProjectWindow.show();
    }

    private void opdaterProjektListe() {
        projectListBox.getChildren().clear();

        projectListBox.getChildren().clear();

        Label titel = new Label("Projects");
        projectListBox.add(titel, 0,0);

        if (projects.isEmpty()) {
            Label noProject = new Label("You have zero working projects");
            projectListBox.add(noProject, 0,1);
        } else {
            int column = 0;
            int row = 1; 

            for (Project project : projects) {
                Project currentProject = project;
                Button button = new Button(currentProject.getName());
                button.setPrefHeight(60);
                button.setPrefWidth(200);
                button.setOnAction(event -> projectEditorWindow(currentProject));

                projectListBox.add(button, column, row);

                column++;
                if (column > 1) { 
                    column = 0;
                    row++;
                }
            }
        }
    }


    private void projectEditorWindow(Project project) {
        Stage projectEditorWindow = new Stage();
        projectEditorWindow.setTitle("Edit Projekt " + project.getName());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        Label label1 = new Label("Report of projects");
        layout.getChildren().add(label1);
        layout.getChildren().add(new Label(project.generateReport()));

        String pl = "No current project leader";
        try {
            pl = project.getProjectleader().getUID();
        } catch (java.lang.NullPointerException e1){}

        HBox projectLeaderBox = new HBox();
        
        Label projectLeaderLabel = new Label("Project leader:");
        projectLeaderLabel.setPrefWidth(120);
        TextField projectLeaderTF = new TextField(pl);
        projectLeaderTF.setOnKeyTyped(event -> project.setProjectLeader(this.getUserWithUID(projectLeaderTF.getText())));
        
        projectLeaderBox.getChildren().addAll(projectLeaderLabel,projectLeaderTF);
        projectLeaderBox.autosize();
        layout.getChildren().add(projectLeaderBox);


        VBox.setMargin(projectLeaderBox, new Insets(0, 0, 25, 0)); 
        
        Label activityLabel = new Label("Activities of project " + project.getName());
        Button addActivityButton = new Button("+add activity");
        
        addActivityButton.setOnAction(event -> {
            Stage inputWindow = new Stage();
            inputWindow.setTitle("Add new activity");
        
            VBox popupLayout = new VBox(10);
            layout.setPadding(new Insets(10));
            layout.setAlignment(Pos.CENTER);
        
            Label prompt = new Label("Enter activity name:");
            TextField inputField = new TextField();
            Button confirmButton = new Button("Create");
        
            confirmButton.setOnAction(e -> {
                String title = inputField.getText().trim();
                if (!title.isEmpty()) {
                    project.addActivity(new Activity(title));
                    inputWindow.close();
                    projectEditorWindow.close();
                    projectEditorWindow(project); 
                }
            });
        
            popupLayout.getChildren().addAll(prompt, inputField, confirmButton);
            Scene scene = new Scene(popupLayout, 250, 150);            
            inputWindow.setScene(scene);
            inputWindow.initOwner(addActivityButton.getScene().getWindow()); 
            inputWindow.show();
        });
        
        
        HBox activityBox = new HBox(10); 
        activityBox.setAlignment(Pos.CENTER_LEFT);
        activityBox.getChildren().addAll(activityLabel, addActivityButton);
        
        layout.getChildren().add(activityBox);
        VBox.setMargin(activityBox, new Insets(10, 0, 0, 0));      

        
        for (Activity activity : project.getActivities()){
            Button button = new Button(activity.getTitle());
            button.setOnAction(event -> activityEditorWindow(activity));
            layout.getChildren().add(button);
        }
        //getActivities

        Scene scene = new Scene(layout, 350, 400);
        projectEditorWindow.setScene(scene);
        projectEditorWindow.show();
        
    }

    public void activityEditorWindow(Activity activity){
        Stage activityEditorWindow = new Stage();
        activityEditorWindow.setTitle("Edit activity " + activity.getTitle());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        //addTime
        HBox addTimeBox = new HBox();          
        TextField addTimeTF = new TextField("0");
        Button addTimeButton = new Button("Add time");
        addTimeButton.setOnAction(event -> activity.addTime(Integer.valueOf(addTimeTF.getText())));
        addTimeBox.getChildren().addAll(addTimeTF,addTimeButton);
        layout.getChildren().add(addTimeBox);


        //getBudgetedTime
        HBox BudgetedTimeBox = new HBox();        
        Label BudgetedTimeLabel = new Label("Budgeted Time:");
        BudgetedTimeLabel.setPrefWidth(120);
        String BT = "Nan";
        try {
            BT = Integer.toString(activity.getBudgetedTime());
        } catch (java.lang.NullPointerException e1){}        
        TextField BudgetedTimeTF = new TextField(BT);
        BudgetedTimeTF.setOnKeyTyped(event -> activity.setBudgetedTime(Integer.valueOf(BudgetedTimeTF.getText())));
        BudgetedTimeBox.getChildren().addAll(BudgetedTimeLabel,BudgetedTimeTF);
        layout.getChildren().add(BudgetedTimeBox);

        //getRecordedTime
        HBox RecordedTimeBox = new HBox();        
        Label RecordedTimeLabel = new Label("Recorded Time:");
        RecordedTimeLabel.setPrefWidth(120);
        String RT = "Nan";
        try {
            RT = Integer.toString(activity.getRecordedTime());
        } catch (java.lang.NullPointerException e1){}
        TextField RecordedTimeTF = new TextField(RT);
        RecordedTimeTF.setOnKeyTyped(event -> activity.setBudgetedTime(Integer.valueOf(RecordedTimeTF.getText())));
        RecordedTimeBox.getChildren().addAll(RecordedTimeLabel,RecordedTimeTF);
        layout.getChildren().add(RecordedTimeBox);

        //getAssignedUsers
        //assignUser
        //unassignUser
        //getStartDate
        //getEndDate
        //getFixed
        //getMaxUsers
        //editDate

        Scene scene = new Scene(layout, 300, 200);
        activityEditorWindow.setScene(scene);
        activityEditorWindow.show();
    }
}
