package dtu.example.ui;

import javafx.scene.control.DatePicker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.layout.ColumnConstraints;
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
    private GridPane rightGrid;

    @Override

    // Louise
    public void start(Stage stage) throws IOException {
        Users.add(new User("huba"));

        for (int i = 1; i < 50; i++) {
            Users.add(new User("Use" + i));
        }

        showLoginWindow(stage);
    }

    // Louise
    public App() {
    }

    // Nikolaj
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    // Nikolaj
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // Louise
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
            String userId = userIdField.getText().trim().toLowerCase();
            if (!userId.isEmpty()) {

                if (getUserUIDs().contains(userId)) {
                    LoginUser = getUserWithUID(userId);
                    loginStage.close();
                    startMainWindow(primaryStage);
                } else {
                    showErrorPopup("Error: User doesn't exists. \nPlease try again", true);
                }

            }
        });

        layout.getChildren().addAll(welcomeLabel, userIdField, loginButton);

        Scene scene = new Scene(layout, 300, 200);
        loginStage.setScene(scene);
        loginStage.show();
    }

    // Nikolaj
    public static void main(String[] args) {
        launch();
    }

    // Louise
    public int registerUser(String UID) {
        for (User user : Users) {
            if (user.getUID().equals(UID)) {
                return -1;
            }
        }
        Users.add(new User(UID));
        return 0;
    }

    // Nikolaj
    public List<User> getUsers() {
        return Users;
    }

    // Nikolaj
    public List<String> getUserUIDs() {
        List<String> UIDs = new ArrayList<>();
        for (User user : Users) {
            UIDs.add(user.getUID());
        }
        return UIDs;
    }

    // Nikolaj
    public User getUserWithUID(String UID) {
        for (User user : Users) {
            if (user.getUID().equals(UID.toLowerCase())) {
                return user;
            }
        }
        return null;
    }

    // Nikolaj
    public Project getProject(String projectName) {
        for (Project project : projects) {
            if (project.getName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }

    // Nikolaj
    public void addProject(String projectName) {
        projects.add(new Project(projectName));
    }

    // Louise
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
            titleLabel.setPrefWidth(150);
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

    // Louise
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

    // Nikolaj
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

    // Louise
    private void projectEditorWindow(Project project) {
        Stage projectEditorWindow = new Stage();
        projectEditorWindow.setTitle("Edit Project " + project.getName());

        HBox mainLayout = new HBox(50);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);

        VBox leftBox = new VBox(15);
        leftBox.setAlignment(Pos.TOP_LEFT);
        DatePicker startDatePicker = new DatePicker(project.getStartDate());
        if (project.getStartDate() == null
                || (project.getProjectleader() != null && project.getProjectleader().equals(LoginUser))) {
            startDatePicker.setDisable(false);
        } else {
            startDatePicker.setDisable(true);
        }
        startDatePicker.setOnAction(event -> {
            LocalDate selectedDate = startDatePicker.getValue();

            if (project.getStartDate() != null &&
                    (project.getProjectleader() == null || !project.getProjectleader().equals(LoginUser))) {
                showErrorPopup("Error: Only the project leader can \nchange the start date", true);
                startDatePicker.setValue(project.getStartDate());
                return;
            }

            if (project.getEndDate() != null && selectedDate.isAfter(project.getEndDate())) {
                showErrorPopup("Error: Start date cannot be after end date. \nPick a new date", true);
                startDatePicker.setValue(project.getStartDate());
                return;
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
                showErrorPopup("Error: Only the project leader can change\n the end date", true);
                endDatePicker.setValue(project.getEndDate());
                return;
            }

            if (project.getStartDate() != null && selectedDate.isBefore(project.getStartDate())) {
                showErrorPopup("Error: End date cannot be before start date. \nPick a new date", true);
                endDatePicker.setValue(project.getEndDate());
                return;
            } else {
                project.setEndDate(selectedDate);

            }
        });

        leftBox.setTranslateX(30);

        Label headerLabel = new Label("Project " + project.getName());
        headerLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Label reportLabel = new Label("Reports of projects:");

        HBox projectLeaderBox = new HBox(10);
        Label projectLeaderLabel = new Label("Project leader:");

        ComboBox<String> projectLeaderDropdown = new ComboBox<>();
        for (User user : Users) {
            projectLeaderDropdown.getItems().add(user.getUID());
        }
        projectLeaderDropdown.setPrefWidth(150);

        if (project.getProjectleader() != null) {
            projectLeaderDropdown.setValue(project.getProjectleader().getUID());
            projectLeaderDropdown.setDisable(true);
        } else {
            projectLeaderDropdown.setPromptText("Select project leader");
        }

        Button projectLeaderButton = new Button("Assign project leader");
        projectLeaderButton.setOnAction(event -> {
            String selectedLeader = projectLeaderDropdown.getValue();
            if (selectedLeader != null && !selectedLeader.isEmpty()) {
                User user = getUserWithUID(selectedLeader);
                if (user != null) {
                    project.setProjectLeader(user);
                    projectLeaderDropdown.setValue(selectedLeader);
                    projectLeaderDropdown.setDisable(true);
                } else {
                    showErrorPopup("Error: Selected user does not exist", true);
                }
            } else {
                showErrorPopup("Error: Please select a project leader", true);
            }
        });

        projectLeaderBox.getChildren().addAll(projectLeaderLabel, projectLeaderDropdown, projectLeaderButton);

        leftBox.getChildren().addAll(
                headerLabel,
                // reportLabel,
                new Label("Start date:"), startDatePicker,
                new Label("End date:"), endDatePicker,
                projectLeaderBox);

        if (project.getProjectleader() != null && project.getProjectleader().equals(LoginUser)) {
            Button reportButton = new Button("Generate Report");
            reportButton.setOnAction(event -> {
                String report = project.generateReport();
                showReportWindow(report);
            });
            leftBox.getChildren().add(reportButton);
        }

        rightGrid = new GridPane();

        rightGrid.setHgap(20);
        rightGrid.setVgap(10);
        rightGrid.setAlignment(Pos.TOP_LEFT);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.LEFT);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.CENTER);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHalignment(HPos.RIGHT);

        rightGrid.getColumnConstraints().addAll(col1, col2, col3);

        rightGrid.getChildren().clear();

        HBox activityHeader = new HBox(10);
        Label activitiesLabel = new Label("Activities of project:");
        Button addActivityButton = new Button("+ Add activity");
        activityHeader.getChildren().addAll(activitiesLabel, addActivityButton);
        activityHeader.setAlignment(Pos.CENTER_LEFT);

        rightGrid.add(activityHeader, 0, 0, 3, 1);

        rightGrid.add(new Label("Activity Name"), 0, 1);
        rightGrid.add(new Label("Users"), 1, 1);
        rightGrid.add(new Label("Dates"), 2, 1);

        int row = 2;
        for (Activity activity : project.getActivities()) {
            Button activityButton = new Button(activity.getTitle());
            activityButton.setOnAction(e -> activityEditorWindow(activity));

            Label userCountLabel = new Label(String.valueOf(activity.getAssignedUsers().size()));

            String startDate = (activity.getStartDate() != null) ? activity.getStartDate().toString() : "-";
            String endDate = (activity.getEndDate() != null) ? activity.getEndDate().toString() : "-";
            Label dateLabel = new Label(startDate + " | " + endDate);

            rightGrid.add(activityButton, 0, row);
            rightGrid.add(userCountLabel, 1, row);
            rightGrid.add(dateLabel, 2, row);

            row++;
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
                    int newRow = project.getActivities().size() + 1;
                    rightGrid.add(activityButton, 0, newRow);
                    rightGrid.add(new Label("0"), 1, newRow);
                    rightGrid.add(new Label("- | -"), 2, newRow);

                    opdaterProjektAktiviteter(project);

                    inputWindow.close();
                }
            });

            popupLayout.getChildren().addAll(prompt, inputField, confirmButton);
            Scene scene = new Scene(popupLayout, 250, 150);
            inputWindow.setScene(scene);
            inputWindow.show();
        });

        mainLayout.getChildren().addAll(leftBox, rightGrid);
        HBox.setMargin(leftBox, new Insets(0, 0, 0, 10));

        Scene scene = new Scene(mainLayout, 800, 500);
        projectEditorWindow.setScene(scene);
        projectEditorWindow.show();
    }

    // Louise
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

                if (budget <= 0) {
                    showErrorPopup("Error: Budgeted time must be a positive integer", true);
                    budgetedTimeTF.clear();
                    return;
                }

                if (activity.getBudgetedTime() == 0) {
                    activity.setBudgetedTime(budget);
                    saveBudgetButton.setVisible(false);
                    budgetedTimeTF.setEditable(false);
                } else if (activity.getProject().getProjectleader() != null &&
                        activity.getProject().getProjectleader().equals(LoginUser)) {
                    activity.setBudgetedTime(budget);
                    saveBudgetButton.setVisible(false);
                    budgetedTimeTF.setEditable(false);
                } else {
                    showErrorPopup("Error: Only the project leader can change \nthe budgeted time after it's set",
                            true);
                }
            } catch (NumberFormatException e) {
                showErrorPopup("Error: Invalid number entered. \nPlease enter a valid integer", true);
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
        recordedTimeTF.setEditable(false);
        recordedTimeTF.setStyle("-fx-control-inner-background: #E0E0E0;");
        recordedTimeBox.getChildren().addAll(recordedTimeLabel, recordedTimeTF);
        layout.getChildren().add(recordedTimeBox);

        HBox addTimeBox = new HBox(10);
        addTimeBox.setAlignment(Pos.CENTER);
        final TextField addTimeTF = new TextField();
        addTimeTF.setPromptText("Enter time");
        Button addTimeButton = new Button("Add recorded time");
        Button assignUserButton = new Button("Assign user");
        Button unassignUserButton = new Button("Unassign user");
        Button assignSelf = new Button();
        if (activity.getAssignedUsers().contains(LoginUser)) {
            assignSelf.setText("Assign/Unassign self");
        } else {
            assignSelf.setText("Assign/Unassign self");
        }
        Button seeAssignedUsersButton = new Button("See assigned users");

        HBox manageUsersBox = new HBox(10);
        manageUsersBox.setAlignment(Pos.CENTER);
        manageUsersBox.getChildren().addAll(assignUserButton, unassignUserButton);

        seeAssignedUsersButton.setOnAction(event -> {
            Stage assignedUsersWindow = new Stage();
            assignedUsersWindow.setTitle("Assigned Users for " + activity.getTitle());

            VBox assignedLayout = new VBox(10);
            assignedLayout.setPadding(new Insets(10));
            assignedLayout.setAlignment(Pos.CENTER_LEFT);

            Label header = new Label("Users assigned to activity:");
            assignedLayout.getChildren().add(header);

            for (User user : activity.getAssignedUsers()) {
                assignedLayout.getChildren().add(new Label(user.getUID()));
            }

            Button closeButton = new Button("Close");
            closeButton.setOnAction(e -> assignedUsersWindow.close());
            assignedLayout.getChildren().add(closeButton);

            Scene scene = new Scene(assignedLayout, 300, 300);
            assignedUsersWindow.setScene(scene);
            assignedUsersWindow.show();
        });

        unassignUserButton.setOnAction(event -> {
            Stage unassignUserStage = new Stage();
            unassignUserStage.setTitle("Unassign User from Activity");

            VBox unassignLayout = new VBox(10);
            unassignLayout.setPadding(new Insets(10));
            unassignLayout.setAlignment(Pos.CENTER);

            Label label = new Label("Select User to Unassign:");
            ComboBox<String> userDropdown = new ComboBox<>();

            for (User user : activity.getAssignedUsers()) {
                userDropdown.getItems().add(user.getUID());
            }

            Button confirmButton = new Button("Unassign");

            confirmButton.setOnAction(e -> {
                String selectedUID = userDropdown.getValue();
                if (selectedUID != null) {
                    User user = getUserWithUID(selectedUID);
                    if (user != null) {
                        activity.unassignUser(user);
                        showErrorPopup("User unassigned successfully", false);
                        unassignUserStage.close();
                    }
                }
            });

            unassignLayout.getChildren().addAll(label, userDropdown, confirmButton);

            Scene scene = new Scene(unassignLayout, 300, 150);
            unassignUserStage.setScene(scene);
            unassignUserStage.show();
        });

        assignUserButton.setOnAction(event -> {
            if (activity.getProject().getProjectleader() != null &&
                    activity.getProject().getProjectleader().equals(LoginUser)) {

                Stage assignUserStage = new Stage();
                assignUserStage.setTitle("Assign User to Activity");

                VBox assignLayout = new VBox(10);
                assignLayout.setPadding(new Insets(10));
                assignLayout.setAlignment(Pos.CENTER);

                Label label = new Label("Select from available users:");
                ComboBox<String> userDropdown = new ComboBox<>();

                for (User user : getAllAvailableUsers(activity)) {
                    if (!activity.getAssignedUsers().contains(user)) {
                        userDropdown.getItems()
                                .add(user.getUID() + "     Current activities: " + user.getWorkLoad(activity));
                    }
                }

                userDropdown.setPrefWidth(200);

                Button confirmButton = new Button("Assign");

                confirmButton.setOnAction(e -> {
                    String selectedEntry = userDropdown.getValue();
                    if (selectedEntry != null && !selectedEntry.isEmpty()) {
                        String selectedUserId = selectedEntry.substring(0, 4);
                        User user = getUserWithUID(selectedUserId);

                        if (activity.getAssignedUsers().contains(user)) {
                            showErrorPopup("Error: User is already assigned to this activity.", true);
                            return;
                        }

                        int result = activity.assignUser(user);
                        if (result == 0) {
                            showErrorPopup("User assigned successfully", false);
                            assignUserStage.close();
                        } else {
                            showErrorPopup("Error: User cannot be assigned (availability or already assigned)", true);
                        }
                    } else {
                        showErrorPopup("Error: Please select a user", true);
                    }
                });

                assignLayout.getChildren().addAll(label, userDropdown, confirmButton);

                Scene scene = new Scene(assignLayout, 300, 150);
                assignUserStage.setScene(scene);
                assignUserStage.show();

            } else {
                showErrorPopup("Error: Only the Project Leader can assign users", true);
            }
        });

        assignSelf.setOnAction(event -> {
            if (activity.getAssignedUsers().contains(LoginUser)) {

                activity.getAssignedUsers().remove(LoginUser);
                showErrorPopup("You have been unassigned from this activity.", false);
                assignSelf.setText("Assign/Unassign self");
            } else {

                if (activity.getProject().getProjectleader() != null
                        && activity.getProject().getProjectleader().equals(LoginUser)) {

                    int result = activity.assignUser(LoginUser);
                    if (result == 0) {
                        showErrorPopup("You have been assigned to this activity", false);

                    } else {
                        showErrorPopup("Error: You are not available or already assigned", true);
                    }
                } else if (activity.getProject().getStartDate() != null && activity.getProject().getEndDate() != null) {

                    int result = activity.assignUser(LoginUser);
                    if (result == 0) {
                        showErrorPopup("You have been assigned to this activity", false);

                    } else {
                        showErrorPopup("Error: You are not available or already assigned", true);
                    }
                } else {
                    showErrorPopup(
                            "Error: You can only join an activity \nwhen both the project and activity\n have dates",
                            true);
                }

            }

            opdaterProjektAktiviteter(activity.getProject());
        });

        if (activity.getStartDate() == null && activity.getEndDate() == null) {
            assignSelf.setDisable(true);
            assignUserButton.setDisable(true);
        }

        addTimeButton.setOnAction(event -> {
            try {
                int addedTime = Integer.parseInt(addTimeTF.getText().trim());

                if (addedTime < 0) {
                    showErrorPopup("Error: Time cannot be negative", true);
                    return;
                }

                if (activity.getRecordedTime() + addedTime > activity.getBudgetedTime()) {
                    showErrorPopup(
                            "Error: You cannot record more time \nthan the budgeted time. Contact your \nprojectleader to edit budgeted time",
                            true);
                    return;
                }

                activity.addTime(addedTime);
                LoginUser.registerTime(addedTime);
                recordedTimeTF.setText(String.valueOf(activity.getRecordedTime()));
                addTimeTF.clear();

            } catch (NumberFormatException e) {
                showErrorPopup("Error: Please enter a valid number", true);
                addTimeTF.clear();
            }
        });

        addTimeBox.getChildren().addAll(addTimeTF, addTimeButton);
        layout.getChildren().add(addTimeBox);

        // date
        DatePicker activityStartDatePicker = new DatePicker(activity.getStartDate());
        DatePicker activityEndDatePicker = new DatePicker(activity.getEndDate());

        if (activity.getProject().getStartDate() == null || activity.getProject().getEndDate() == null) {
            activityStartDatePicker.setDisable(true);
            activityEndDatePicker.setDisable(true);
        } else {
            activityStartDatePicker.setDisable(false);
            activityEndDatePicker.setDisable(false);
        }

        activityStartDatePicker.setOnAction(e -> {
            LocalDate selectedDate = activityStartDatePicker.getValue();
            LocalDate projectStart = activity.getProject().getStartDate();
            LocalDate projectEnd = activity.getProject().getEndDate();

            if (projectStart != null && selectedDate.isBefore(projectStart)) {
                showErrorPopup("Error: Start date cannot be before project start date", true);
                activityStartDatePicker.setValue(activity.getStartDate());
                return;
            }
            if (projectEnd != null && selectedDate.isAfter(projectEnd)) {
                showErrorPopup("Error: Start date cannot be after project end date", true);
                activityStartDatePicker.setValue(activity.getStartDate());
                return;
            }
            if (activity.getEndDate() != null && selectedDate.isAfter(activity.getEndDate())) {
                showErrorPopup("Error: Start date cannot be after activity end date", true);
                activityStartDatePicker.setValue(activity.getStartDate());
            } else {
                activity.setStartDate(selectedDate);
                opdaterProjektAktiviteter(activity.getProject());

            }

            if (activity.getStartDate() != null && activity.getEndDate() != null) {
                assignSelf.setDisable(false);
                assignUserButton.setDisable(false);
            }
        });

        activityEndDatePicker.setOnAction(e -> {
            LocalDate selectedDate = activityEndDatePicker.getValue();
            LocalDate projectStart = activity.getProject().getStartDate();
            LocalDate projectEnd = activity.getProject().getEndDate();

            if (projectStart != null && selectedDate.isBefore(projectStart)) {
                showErrorPopup("Error: End date cannot be before project start date", true);
                activityEndDatePicker.setValue(activity.getEndDate());
                return;
            }
            if (projectEnd != null && selectedDate.isAfter(projectEnd)) {
                showErrorPopup("Error: End date cannot be after project end date", true);
                activityEndDatePicker.setValue(activity.getEndDate());
                return;
            }
            if (activity.getStartDate() != null && selectedDate.isBefore(activity.getStartDate())) {
                showErrorPopup("Error: End date cannot be before activity start date", true);
                activityEndDatePicker.setValue(activity.getEndDate());
            } else {
                activity.setEndDate(selectedDate);
                opdaterProjektAktiviteter(activity.getProject());

            }

            if (activity.getStartDate() != null && activity.getEndDate() != null) {
                assignSelf.setDisable(false);
                assignUserButton.setDisable(false);
            }
        });

        Label infoLabel = new Label();
        infoLabel.setStyle("-fx-font-size: 12px;");
        layout.getChildren().add(infoLabel);

        HBox activityDatePickers = new HBox(10);
        activityDatePickers.setAlignment(Pos.CENTER_LEFT);
        activityDatePickers.getChildren().addAll(
                new Label("Start:"), activityStartDatePicker,
                new Label("End:"), activityEndDatePicker);

        if (activity.getProject().getStartDate() == null || activity.getProject().getEndDate() == null) {
            activityStartDatePicker.setDisable(true);
            activityEndDatePicker.setDisable(true);
            infoLabel.setText("Projektet mangler start og slut dato \nfør man kan sætte på aktiviteten");
            infoLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        } else {
            activityStartDatePicker.setDisable(false);
            activityEndDatePicker.setDisable(false);
            infoLabel.setText("Her kan du sætte start date og enddate på aktiviteten");
            infoLabel.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
        }

        layout.getChildren().add(activityDatePickers);

        if (activity.getProject().getProjectleader() != null
                && activity.getProject().getProjectleader().equals(LoginUser)) {
            layout.getChildren().addAll(seeAssignedUsersButton, manageUsersBox, assignSelf);
        } else {
            layout.getChildren().addAll(assignSelf);
        }

        Scene scene = new Scene(layout, 400, 400);
        activityEditorWindow.setScene(scene);
        activityEditorWindow.show();
    }

    // Louise
    public void showErrorPopup(String message, boolean isCritical) {
        Stage errorStage = new Stage();
        errorStage.setTitle("");

        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));

        Label label = new Label(message);
        if (isCritical) {
            label.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        } else {
            label.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
        }

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> errorStage.close());

        box.getChildren().addAll(label, okButton);
        Scene scene = new Scene(box, 300, 150);
        errorStage.setScene(scene);
        errorStage.show();
    }

    // Louise
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

        Button userInfoButton = new Button("Show work hours");
        userInfoButton.setPrefHeight(60);
        userInfoButton.setPrefWidth(200);

        userInfoButton.setOnAction(event -> {
            userInfoWindow(LoginUser);
        });

        VenstreBlok.getChildren().addAll(myUserLabel, infoLabel, ProjektKnap, myActivitiesButton,
                addFixedAtivitiesButton, userInfoButton);

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

    // Nikolaj
    private void opdaterProjektAktiviteter(Project project) {
        rightGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) >= 2);

        int row = 2;
        for (Activity activity : project.getActivities()) {
            Button activityButton = new Button(activity.getTitle());
            activityButton.setOnAction(e -> activityEditorWindow(activity));

            Label userCountLabel = new Label(String.valueOf(activity.getAssignedUsers().size()));

            String startDate = (activity.getStartDate() != null) ? activity.getStartDate().toString() : "-";
            String endDate = (activity.getEndDate() != null) ? activity.getEndDate().toString() : "-";
            Label dateLabel = new Label(startDate + " | " + endDate);

            rightGrid.add(activityButton, 0, row);
            rightGrid.add(userCountLabel, 1, row);
            rightGrid.add(dateLabel, 2, row);

            row++;
        }
    }

    // Nikolaj & kelvin
    private void userInfoWindow(User currUser) {
        Stage Infostage = new Stage();
        Infostage.setTitle(currUser.getUID() + " Work hours");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER);

        ListView<String> activityList = new ListView<>();

        HashMap<LocalDate, Integer> map = currUser.getWorkHistory();

        currUser.registerTime(2, LocalDate.now().plusDays(5));
        currUser.registerTime(8, LocalDate.now().plusDays(7));
        currUser.registerTime(2, LocalDate.now().plusDays(9));
        for (int i = 0; i < 100; i++) {
            currUser.registerTime(2, LocalDate.now().plusDays(9 + i));
        }

        for (Map.Entry<LocalDate, Integer> entry : map.entrySet()) {
            LocalDate key = entry.getKey();
            // Label titleLabel = new Label(currUser.showWorkDate(key));
            // titleLabel.setPrefWidth(150);

            // String row = new HBox(10, titleLabel);
            // row.setAlignment(Pos.CENTER);
            activityList.getItems().add(currUser.showWorkDate(key));
        }

        activityList.setItems(activityList.getItems().sorted());

        layout.getChildren().add(activityList);

        Scene scene = new Scene(layout, 500, 400);
        Infostage.setScene(scene);
        Infostage.show();
    }

    private void showReportWindow(String reportContent) {
        Stage reportStage = new Stage();
        reportStage.setTitle("Project Report");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.TOP_LEFT);

        Label reportLabel = new Label(reportContent);
        reportLabel.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> reportStage.close());

        layout.getChildren().addAll(reportLabel, closeButton);

        Scene scene = new Scene(layout, 500, 400);
        reportStage.setScene(scene);
        reportStage.show();
    }

    // Louise
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
                    newActivity.editDate(startDatePicker.getValue(), endDatePicker.getValue());
                    newActivity.assignUser(LogedinUser);
                    newActivity.fixed = true;

                    inputWindow.close();
                } else {
                    showErrorPopup("Error: Start date cannot be after end date", true);
                }

            } else {
                showErrorPopup("Error: Activity must have a title please add one", true);
            }
        });

        popupLayout.getChildren().addAll(prompt, inputField, DatepickersBox, confirmButton);
        Scene scene = new Scene(popupLayout, 250, 150);
        inputWindow.setScene(scene);
        inputWindow.show();
    }

    // Nikolaj
    private List<User> getAllAvailableUsers(Activity activity) {
        List<User> AvailableUsers = new ArrayList<>();
        for (User user : Users) {
            if (user.getAvailability(activity)) {
                AvailableUsers.add(user);
            }
        }
        return AvailableUsers;
    }
}