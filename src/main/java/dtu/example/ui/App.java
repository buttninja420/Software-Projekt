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
        // Tilføj nogle eksempelprojekter
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
            int row = 1; // start under overskriften

            for (Project project : projects) {
                Project currentProject = project;
                Button button = new Button(currentProject.getName());
                button.setPrefHeight(60);
                button.setPrefWidth(200);
                button.setOnAction(event -> projectEditorWindow(currentProject));

                projectListBox.add(button, column, row);

                column++;
                if (column > 1) { // maks 2 kolonner
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

        Scene scene = new Scene(layout, 300, 200);
        projectEditorWindow.setScene(scene);
        projectEditorWindow.show();
    }
}
