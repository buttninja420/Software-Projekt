package dtu.example.ui;
import javafx.scene.control.DatePicker;
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
import javafx.scene.control.ListView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;


/**
 * JavaFX App
 */
public class App extends Application {

    private User loginUID;
    private static Scene scene;
    protected List<User> Users = new ArrayList<>();
    protected List<Project> projects = new ArrayList<>();
    private GridPane projectListBox; 
    

    @Override
    public void start(Stage stage) throws IOException {
        //Opretter "huba"
        Users.add(new User("huba"));

        for (int i = 1; i < 50; i++) {
            Users.add(new User("Use"+i));
        }

        showLoginWindow(stage);
    }


    public App() {}

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private void showLoginWindow(Stage primaryStage) {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
    
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
    
        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
    
        TextField userIdField = new TextField();
        userIdField.setPromptText("Enter User ID");
    
        Button loginButton = new Button("Sign In");
        loginButton.setOnAction(e -> {
            String userId = userIdField.getText().trim();
            if (!userId.isEmpty()) {
                
                if (registerUser(userId) == -1) {
                    loginUID = getUserWithUID(userId);
                    loginStage.close();
                    startMainWindow(primaryStage);
                } else {
                    showErrorPopup("User doesn't exists. Please try again.");
                }
                
            }
        });
    
        layout.getChildren().addAll(welcomeLabel, userIdField, loginButton);
    
        Scene scene = new Scene(layout, 300, 200);
        loginStage.setScene(scene);
        loginStage.show();
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

    private void myProjectWindow(User currentUser) {
    Stage myProjectsWindow = new Stage();
    myProjectsWindow.setTitle("My Activities");

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10));
    layout.setAlignment(Pos.TOP_LEFT);

    Label label1 = new Label("Here you can see your activities:");
    label1.setPrefWidth(280);

    ListView<String> activityList = new ListView<>();
    for (Activity a : currentUser.getActivities()) {
        activityList.getItems().add(a.toString()); 
    }

    Button closeButton = new Button("Close");
    closeButton.setOnAction(e -> myProjectsWindow.close());

    layout.getChildren().addAll(label1, activityList, closeButton);

    Scene scene = new Scene(layout, 400, 300);
    myProjectsWindow.setScene(scene);
    myProjectsWindow.show();
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
        projectEditorWindow.setTitle("Edit Project " + project.getName());
    
        HBox mainLayout = new HBox(50);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);
    
        VBox leftBox = new VBox(15);
        leftBox.setAlignment(Pos.TOP_LEFT);
        DatePicker startDatePicker = new DatePicker(project.getStartDate());
        startDatePicker.setOnAction(event -> {
            LocalDate selectedDate = startDatePicker.getValue();
            if (project.getEndDate() != null && selectedDate.isAfter(project.getEndDate())) {
                showErrorPopup("Start date cannot be after end date. Pick a new date");
                startDatePicker.setValue(project.getStartDate());
            } else {
                project.setStartDate(selectedDate);
            }
        });
        
        DatePicker endDatePicker = new DatePicker(project.getEndDate());
        endDatePicker.setOnAction(event -> {
            LocalDate selectedDate = endDatePicker.getValue();
            if (project.getStartDate() != null && selectedDate.isBefore(project.getStartDate())) {
                showErrorPopup("End date cannot be before start date. Pick a new date");
                endDatePicker.setValue(project.getEndDate());
            } else {
                project.setEndDate(selectedDate);
            }
        });
        

        leftBox.setTranslateX(30);
        
        Label headerLabel = new Label("Project " + project.getName());
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Label reportLabel = new Label("Reports of projects:");
        

        Label startDateLabel = new Label("Start date: Not set");
        Label endDateLabel = new Label("End date: Not set");
        
        HBox projectLeaderBox = new HBox(10);
        Label projectLeaderLabel = new Label("Project leader:");
        final TextField projectLeaderTF = new TextField("No current");

        Button projectLeaderButton = new Button("Assign project leader");
        projectLeaderButton.setOnAction(event -> {
            String newLeader = projectLeaderTF.getText().trim();
            if (!newLeader.isEmpty() && newLeader.length() == 4) {
                project.setProjectLeader(getUserWithUID(newLeader));
                projectLeaderTF.setText(newLeader);
                projectLeaderTF.setEditable(false);
            }
        });


        projectLeaderTF.setPrefWidth(150);
        projectLeaderBox.getChildren().addAll(projectLeaderLabel, projectLeaderTF, projectLeaderButton);

        
        
        leftBox.getChildren().addAll(
            headerLabel, 
            reportLabel, 
            new Label("Start date:"), startDatePicker,
            new Label("End date:"), endDatePicker,
            projectLeaderBox
        );
        
        VBox rightBox = new VBox(15);
        rightBox.setAlignment(Pos.TOP_LEFT);
        rightBox.setTranslateY(45);
    
        HBox activityHeader = new HBox(10);
        Label activitiesLabel = new Label("Activities of project:");
        Button addActivityButton = new Button("+ Add activity");
        activityHeader.getChildren().addAll(activitiesLabel, addActivityButton);
        activityHeader.setAlignment(Pos.CENTER_LEFT);

        addActivityButton.setOnAction(event -> {
            Stage inputWindow = new Stage();
            inputWindow.setTitle("Add New Activity");
    
            VBox popupLayout = new VBox(10);
            popupLayout.setPadding(new Insets(10));
            popupLayout.setAlignment(Pos.CENTER);
    
            Label prompt = new Label("Enter activity name:");
            TextField inputField = new TextField();
            Button confirmButton = new Button("Create");
    
            confirmButton.setOnAction(e -> {
                String title = inputField.getText().trim();
                if (!title.isEmpty()) {
                    project.addActivity(new Activity(title, project));
                    inputWindow.close();
                    projectEditorWindow.close();
                    projectEditorWindow(project);
                }
            });
    
            popupLayout.getChildren().addAll(prompt, inputField, confirmButton);
            Scene scene = new Scene(popupLayout, 250, 150);
            inputWindow.setScene(scene);
            inputWindow.show();
        });
    
        rightBox.getChildren().add(activityHeader);

        for (Activity activity : project.getActivities()) {
            Button activityButton = new Button(activity.getTitle());
            activityButton.setOnAction(e -> activityEditorWindow(activity));
            rightBox.getChildren().add(activityButton);
        }
        
    
        mainLayout.getChildren().addAll(leftBox, rightBox);
        HBox.setMargin(leftBox, new Insets(0, 0, 0, 10));  
    
        Scene scene = new Scene(mainLayout, 600, 400);
        projectEditorWindow.setScene(scene);
        projectEditorWindow.show();
    }

    
    public void activityEditorWindow(Activity activity) {
        Stage activityEditorWindow = new Stage();
        activityEditorWindow.setTitle("Edit activity " + activity.getTitle());
    
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);
    
        Label headerLabel = new Label("Activity " + activity.getTitle());
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        layout.getChildren().add(headerLabel);
    
        Label instructionLabel = new Label("Enter budgeted time first for your activity and then your recorded\ntime below:");
        layout.getChildren().add(instructionLabel);
    
        HBox budgetedTimeBox = new HBox(10);
        budgetedTimeBox.setAlignment(Pos.CENTER_LEFT);
        Label budgetedTimeLabel = new Label("Budgeted Time:");
        final TextField budgetedTimeTF = new TextField(String.valueOf(activity.getBudgetedTime()));
        budgetedTimeTF.setOnKeyReleased(event -> {
            try {
                int budget = Integer.parseInt(budgetedTimeTF.getText().trim());
                activity.setBudgetedTime(budget);
            } catch (NumberFormatException ignored) {}
        });
        budgetedTimeBox.getChildren().addAll(budgetedTimeLabel, budgetedTimeTF);
        layout.getChildren().add(budgetedTimeBox);
    
        HBox recordedTimeBox = new HBox(10);
        recordedTimeBox.setAlignment(Pos.CENTER_LEFT);
        Label recordedTimeLabel = new Label("Recorded Time:");
        final TextField recordedTimeTF = new TextField(String.valueOf(activity.getRecordedTime()));
        recordedTimeTF.setEditable(false);
        recordedTimeBox.getChildren().addAll(recordedTimeLabel, recordedTimeTF);
        layout.getChildren().add(recordedTimeBox);
    
        HBox addTimeBox = new HBox(10);
        addTimeBox.setAlignment(Pos.CENTER);
        final TextField addTimeTF = new TextField();
        addTimeTF.setPromptText("Enter time");
        Button addTimeButton = new Button("Add recorded time");
        Button assignUserButton = new Button("Assign user");
        Button assignSelf = new Button("Assign");
    
        addTimeButton.setOnAction(event -> {
            try {
                int addedTime = Integer.parseInt(addTimeTF.getText().trim());
                if (addedTime < 0) {
                    showErrorPopup("Time cannot be negative.");
                    return;
                }
                activity.addTime(addedTime);
                recordedTimeTF.setText(String.valueOf(activity.getRecordedTime()));
                addTimeTF.clear();
            } catch (NumberFormatException e) {
                showErrorPopup("Please enter a valid number.");
                addTimeTF.clear();
            }
        });
    
        addTimeBox.getChildren().addAll(addTimeTF, addTimeButton);
        layout.getChildren().add(addTimeBox);
        if (activity.getProject().getProjectleader().equals(loginUID) && activity.getProject().getProjectleader() != null) {
            layout.getChildren().add(assignUserButton);
        } else {
            layout.getChildren().add(assignSelf);
        }
    
        Scene scene = new Scene(layout, 400, 300);
        activityEditorWindow.setScene(scene);
        activityEditorWindow.show();
    }
    
    
    public void showErrorPopup(String message) {
        Stage errorStage = new Stage();
        errorStage.setTitle("Input Error");
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        Label label = new Label(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> errorStage.close());
        box.getChildren().addAll(label, okButton);
        Scene scene = new Scene(box, 250, 100);
        errorStage.setScene(scene);
        errorStage.show();
    }

    private void startMainWindow(Stage stage) {
        projects.add(new Project("Project1"));
        projects.add(new Project("Project2"));
        projects.add(new Project("Project3"));
        projects.add(new Project("Porject4"));



        Button myProjectsButton = new Button("My Activities");
        myProjectsButton.setPrefHeight(60);
        myProjectsButton.setPrefWidth(200);

        myProjectsButton.setOnAction(event -> {
            myProjectWindow(loginUID);
        });


        Label myUserLabel = new Label("Logged in as: " + loginUID.getUID());


        HBox mainLay = new HBox(120);
        mainLay.setPadding(new Insets(20));
        mainLay.setAlignment(Pos.TOP_LEFT);
        


        VBox VenstreBlok = new VBox(30);
        VenstreBlok.setAlignment(Pos.TOP_LEFT);

        Label infoLabel = new Label("Press here to make a new project:");

        Button ProjektKnap = new Button("Make a NEW project");
        ProjektKnap.setPrefHeight(60);
        ProjektKnap.setPrefWidth(200);

        VenstreBlok.getChildren().addAll(myUserLabel, infoLabel, ProjektKnap, myProjectsButton);

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
    
    
}



        //getAssignedUsers
        //assignUser
        //unassignUser
        //getFixed
        //getMaxUsers