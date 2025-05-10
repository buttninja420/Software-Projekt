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

    private User LoginUser;
    private static Scene scene;
    protected List<User> Users = new ArrayList<>();
    protected List<Project> projects = new ArrayList<>();
    private GridPane projectListBox;

    @Override
    public void start(Stage stage) throws IOException {
        // Opretter "huba"
        Users.add(new User("huba"));

        for (int i = 1; i < 50; i++) {
            Users.add(new User("Use" + i));
        }

        showLoginWindow(stage);
    }

    public App() {
    }

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

                if (getUserUIDs().contains(userId)) {
                    LoginUser = getUserWithUID(userId);
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

    private void myActivitiesWindow(User currentUser) {
        Stage myActivitiesWindow = new Stage();
        myActivitiesWindow.setTitle("My Activities");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.TOP_LEFT);

        Label label1 = new Label("Here you can see your activities:");
        label1.setPrefWidth(280);

        ListView<HBox> activityList = new ListView<>();

        for (Activity a : currentUser.getActivities()) {
            Label titleLabel = new Label(a.getTitle());
            titleLabel.setPrefWidth(150); // Set column width
            Label dateLabel = new Label(a.getStartDate() + " - " + a.getEndDate());

            HBox row = new HBox(10, titleLabel, dateLabel);
            row.setAlignment(Pos.CENTER);
            activityList.getItems().add(row);
        }

        activityList.setItems(activityList.getItems().sorted());
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> myActivitiesWindow.close());

        layout.getChildren().addAll(label1, activityList, closeButton);

        Scene scene = new Scene(layout, 400, 300);
        myActivitiesWindow.setScene(scene);
        myActivitiesWindow.show();
    }

    private void newProjectWindow() {
        Stage newProjectWindow = new Stage();
        newProjectWindow.setTitle("New project");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.TOP_LEFT);

        Label label1 = new Label(
                "Here you can make a new project. Start with giving \nthe new project a name and then you can edit the \nproject on the main window.");
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
        projectListBox.add(titel, 0, 0);

        if (projects.isEmpty()) {
            Label noProject = new Label("You have zero working projects");
            projectListBox.add(noProject, 0, 1);
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
        if (project.getStartDate() == null || (project.getProjectleader() != null && project.getProjectleader().equals(LoginUser))) {
            startDatePicker.setDisable(false);
        } else {
            startDatePicker.setDisable(true);
        }
        startDatePicker.setOnAction(event -> {
            LocalDate selectedDate = startDatePicker.getValue();
        
            if (project.getStartDate() != null &&
                (project.getProjectleader() == null || !project.getProjectleader().equals(LoginUser))) {
                showErrorPopup("Only the project leader can \nchange the start date.");
                startDatePicker.setValue(project.getStartDate());
                return;
            }
        
            if (project.getEndDate() != null && selectedDate.isAfter(project.getEndDate())) {
                showErrorPopup("Start date cannot be after end date. \nPick a new date");
                startDatePicker.setValue(project.getStartDate());
            } else {
                project.setStartDate(selectedDate);
            }
        });

        DatePicker endDatePicker = new DatePicker(project.getEndDate());
        if (project.getEndDate() == null || 
            (project.getProjectleader() != null && project.getProjectleader().equals(LoginUser))) {
            endDatePicker.setDisable(false);
        } else {
            endDatePicker.setDisable(true);
        }
        endDatePicker.setOnAction(event -> {
            LocalDate selectedDate = endDatePicker.getValue();
    
            if (project.getEndDate() != null &&
                (project.getProjectleader() == null || !project.getProjectleader().equals(LoginUser))) {
                showErrorPopup("Only the project leader can change\n the end date.");
                endDatePicker.setValue(project.getEndDate());
                return;
            }
    
            if (project.getStartDate() != null && selectedDate.isBefore(project.getStartDate())) {
                showErrorPopup("End date cannot be before start date. \nPick a new date");
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
        TextField projectLeaderTF = new TextField();
        if (project.getProjectleader() != null) {
            projectLeaderTF.setText(project.getProjectleader().getUID());
            projectLeaderTF.setDisable(true);
        } else {
            projectLeaderTF.setText("No current");
        }

        Button projectLeaderButton = new Button("Assign project leader");
        projectLeaderButton.setOnAction(event -> {
            String newLeader = projectLeaderTF.getText().trim();
            User user = getUserWithUID(newLeader);

            if (user != null) {
                project.setProjectLeader(user);
                projectLeaderTF.setText(newLeader);
                projectLeaderTF.setEditable(false);
                projectLeaderTF.setDisable(true);
            } else {
                showErrorPopup("User '" + newLeader + "' does not exist. \nCannot assign as project leader.");
            }
        });

        projectLeaderTF.setPrefWidth(150);
        projectLeaderBox.getChildren().addAll(projectLeaderLabel, projectLeaderTF, projectLeaderButton);

        leftBox.getChildren().addAll(
                headerLabel,
                reportLabel,
                new Label("Start date:"), startDatePicker,
                new Label("End date:"), endDatePicker,
                projectLeaderBox);

        VBox rightBox = new VBox(15);
        rightBox.setAlignment(Pos.TOP_LEFT);
        rightBox.setTranslateY(45);

        HBox activityHeader = new HBox(10);
        Label activitiesLabel = new Label("Activities of project:");
        Button addActivityButton = new Button("+ Add activity");
        activityHeader.getChildren().addAll(activitiesLabel, addActivityButton);
        activityHeader.setAlignment(Pos.CENTER_LEFT);

        rightBox.getChildren().add(activityHeader);

        for (Activity activity : project.getActivities()) {
            Button activityButton = new Button(activity.getTitle());
            activityButton.setOnAction(e -> activityEditorWindow(activity));
            rightBox.getChildren().add(activityButton);
        }

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
                    Activity newActivity = new Activity(title, project);
                    project.addActivity(newActivity);

                    Button activityButton = new Button(newActivity.getTitle());
                    activityButton.setOnAction(ev -> activityEditorWindow(newActivity));
                    rightBox.getChildren().add(activityButton);

                    inputWindow.close();
                }
            });

            popupLayout.getChildren().addAll(prompt, inputField, confirmButton);
            Scene scene = new Scene(popupLayout, 250, 150);
            inputWindow.setScene(scene);
            inputWindow.show();
        });

        mainLayout.getChildren().addAll(leftBox, rightBox);
        HBox.setMargin(leftBox, new Insets(0, 0, 0, 10));

        Scene scene = new Scene(mainLayout, 800, 500);
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

        Label instructionLabel = new Label(
                "Enter budgeted time first for your activity and then your recorded\ntime below:");
        layout.getChildren().add(instructionLabel);

        HBox budgetedTimeBox = new HBox(10);
        budgetedTimeBox.setAlignment(Pos.CENTER_LEFT);

        Label budgetedTimeLabel = new Label("Budgeted Time:");
        final TextField budgetedTimeTF = new TextField(String.valueOf(activity.getBudgetedTime()));
        Button saveBudgetButton = new Button("Save");

        boolean isLeader = activity.getProject().getProjectleader() != null &&
                activity.getProject().getProjectleader().equals(LoginUser);

        if (activity.getBudgetedTime() != 0) {
            budgetedTimeTF.setEditable(isLeader);
            saveBudgetButton.setVisible(isLeader);
        } else {
            budgetedTimeTF.setEditable(true);
            saveBudgetButton.setVisible(true);
        }

        saveBudgetButton.setOnAction(event -> {
            try {
                int budget = Integer.parseInt(budgetedTimeTF.getText().trim());
                activity.getProject().setBudgetedTime(budget);
            } catch (NumberFormatException ignored) {
            }
        });
        budgetedTimeBox.getChildren().addAll(budgetedTimeLabel, budgetedTimeTF);
        if (saveBudgetButton.isVisible()) {
            budgetedTimeBox.getChildren().add(saveBudgetButton);
        }
        layout.getChildren().add(budgetedTimeBox);

        HBox recordedTimeBox = new HBox(10);
        recordedTimeBox.setAlignment(Pos.CENTER_LEFT);
        Label recordedTimeLabel = new Label("Recorded Time:");
        final TextField recordedTimeTF = new TextField(String.valueOf(activity.getRecordedTime()));
        recordedTimeBox.getChildren().addAll(recordedTimeLabel, recordedTimeTF);
        layout.getChildren().add(recordedTimeBox);

        HBox addTimeBox = new HBox(10);
        addTimeBox.setAlignment(Pos.CENTER);
        final TextField addTimeTF = new TextField();
        addTimeTF.setPromptText("Enter time");
        Button addTimeButton = new Button("Add recorded time");
        Button assignUserButton = new Button("Assign user");
        Button assignSelf = new Button("Assign self");

        assignUserButton.setOnAction(event -> {
            if (activity.getProject().getProjectleader() != null &&
                    activity.getProject().getProjectleader().equals(LoginUser)) {

                Stage assignUserStage = new Stage();
                assignUserStage.setTitle("Assign User to Activity");

                VBox assignLayout = new VBox(10);
                assignLayout.setPadding(new Insets(10));
                assignLayout.setAlignment(Pos.CENTER);

                Label label = new Label("Enter User ID:");
                TextField userIdField = new TextField();
                Button confirmButton = new Button("Assign");

                confirmButton.setOnAction(e -> {
                    String userId = userIdField.getText().trim();
                    User user = getUserWithUID(userId);
                    if (user != null) {
                        int result = activity.assignUser(user);
                        if (result == 0) {
                            showErrorPopup("User assigned successfully.");
                            assignUserStage.close();
                        } else {
                            showErrorPopup("User cannot be assigned (availability or already assigned).");
                        }
                    } else {
                        showErrorPopup("User does not exist.");
                    }
                });

                assignLayout.getChildren().addAll(label, userIdField, confirmButton);

                Scene scene = new Scene(assignLayout, 300, 150);
                assignUserStage.setScene(scene);
                assignUserStage.show();

            } else {
                showErrorPopup("Only the Project Leader can assign users.");
            }
        });

        assignSelf.setOnAction(event -> {
            int result = activity.assignUser(LoginUser);

            if (activity.getProject().getStartDate() != null && activity.getProject().getEndDate() != null) {

                // Tilmeld bruger her
            } else {
                showErrorPopup("You can only join an activity \nwhen both the project and activity\n have dates.");
            }

            if (result == 0) {
                showErrorPopup("You have been assigned to this activity.");
            } else {
                showErrorPopup("You are not available or already assigned.");
            }

        });

        addTimeButton.setOnAction(event -> {
            try {
                int addedTime = Integer.parseInt(addTimeTF.getText().trim());

                if (addedTime < 0) {
                    showErrorPopup("Time cannot be negative.");
                    return;
                }

                if (activity.getRecordedTime() + addedTime > activity.getBudgetedTime()) {
                    showErrorPopup(
                            "You cannot record more time \nthan the budgeted time. Contact your \nprojectleader to edit budgeted time");
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
        //date
        HBox DatepickersBox = new HBox(15);
        DatepickersBox.setAlignment(Pos.CENTER);
        DatePicker startDatePicker = new DatePicker();

        DatePicker endDatePicker = new DatePicker();
        DatepickersBox.getChildren().addAll(startDatePicker,endDatePicker);
        layout.getChildren().add(DatepickersBox);
        if (activity.getProject().getProjectleader() != null
                && activity.getProject().getProjectleader().equals(LoginUser)) {
            layout.getChildren().addAll(assignUserButton, assignSelf);
        } else {
            layout.getChildren().addAll(assignSelf);

        }

        Scene scene = new Scene(layout, 400, 300);
        activityEditorWindow.setScene(scene);
        activityEditorWindow.show();
    }

    public void showErrorPopup(String message) {
        Stage errorStage = new Stage();
        errorStage.setTitle("");
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        Label label = new Label(message);
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> errorStage.close());
        box.getChildren().addAll(label, okButton);
        Scene scene = new Scene(box, 250, 150);
        errorStage.setScene(scene);
        errorStage.show();
    }

    private void startMainWindow(Stage stage) {
        projects.add(new Project("Make chair"));
        projects.add(new Project("Build table"));
        projects.add(new Project("Get materials"));
        projects.add(new Project("Paint house"));

        Button myActivitiesButton = new Button("My Activities");
        myActivitiesButton.setPrefHeight(60);
        myActivitiesButton.setPrefWidth(200);

        myActivitiesButton.setOnAction(event -> {
            myActivitiesWindow(LoginUser);
        });

        Label myUserLabel = new Label("Logged in as: " + LoginUser.getUID());

        Button addFixedAtivitiesButton = new Button("Add fixed activites");
        addFixedAtivitiesButton.setPrefHeight(60);
        addFixedAtivitiesButton.setPrefWidth(200);

        addFixedAtivitiesButton.setOnAction(event -> {
            addFixedAtivitiesButton(LoginUser);
        });

        HBox mainLay = new HBox(120);
        mainLay.setPadding(new Insets(20));
        mainLay.setAlignment(Pos.TOP_LEFT);

        VBox VenstreBlok = new VBox(30);
        VenstreBlok.setAlignment(Pos.TOP_LEFT);

        Label infoLabel = new Label("Press here to make a new project:");

        Button ProjektKnap = new Button("Make a NEW project");
        ProjektKnap.setPrefHeight(60);
        ProjektKnap.setPrefWidth(200);

        VenstreBlok.getChildren().addAll(myUserLabel, infoLabel, ProjektKnap, myActivitiesButton,
                addFixedAtivitiesButton);

        projectListBox = new GridPane();
        projectListBox.setHgap(20);
        projectListBox.setVgap(20);
        projectListBox.setAlignment(Pos.TOP_LEFT);

        ProjektKnap.setOnAction(event -> {
            newProjectWindow();
        });

        opdaterProjektListe();

        mainLay.getChildren().addAll(VenstreBlok, projectListBox);

        scene = new Scene(mainLay, 800, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void addFixedAtivitiesButton(User LogedinUser) {
        Stage inputWindow = new Stage();
        inputWindow.setTitle("Add New fixed Activity");

        VBox popupLayout = new VBox(10);
        popupLayout.setPadding(new Insets(10));
        popupLayout.setAlignment(Pos.CENTER);

        Label prompt = new Label("Enter activity name:");
        TextField inputField = new TextField();

        HBox DatepickersBox = new HBox(15);
        DatepickersBox.setAlignment(Pos.TOP_LEFT);
        DatePicker startDatePicker = new DatePicker(LocalDate.now());

        DatePicker endDatePicker = new DatePicker(LocalDate.now().plusDays(1));

        DatepickersBox.getChildren().addAll(startDatePicker, endDatePicker);

        Button confirmButton = new Button("Create");

        confirmButton.setOnAction(e -> {
            String title = inputField.getText().trim();
            if (!title.isEmpty()) {
                if (startDatePicker.getValue().isBefore(endDatePicker.getValue())) {
                    Activity newActivity = new Activity(title);
                    newActivity.assignUser(LogedinUser);
                    newActivity.fixed = true;

                    newActivity.editDate(startDatePicker.getValue(), endDatePicker.getValue());

                    inputWindow.close();
                } else {
                    showErrorPopup("Start date cannot be after end date.");
                }

            } else {
                showErrorPopup("Activity must have a title please add one");
            }
        });

        popupLayout.getChildren().addAll(prompt, inputField, DatepickersBox, confirmButton);
        Scene scene = new Scene(popupLayout, 250, 150);
        inputWindow.setScene(scene);
        inputWindow.show();
    }
}