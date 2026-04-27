package ui;
import backend.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class LoginFX extends Application {

    private TextField nameField;
    private TextField weightField;
    private TextField heightField;

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GymQuest");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("GymQuest");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#1E1E2F"));

        nameField = new TextField();
        nameField.setPromptText("Enter Name");

        weightField = new TextField();
        weightField.setPromptText("Weight (kg)");

        heightField = new TextField();
        heightField.setPromptText("Height (m)");

        Button btn = new Button("Start Journey");
        btn.setOnAction(e -> login(stage));

        root.getChildren().addAll(title, nameField, weightField, heightField, btn);

        Scene scene = new Scene(root, 500, 400);

        scene.getStylesheets().add(
                getClass().getResource("/styles/style.css").toExternalForm()
        );

        stage.setTitle("GymQuest Login");
        stage.setScene(scene);
        stage.show();
    }

    private void login(Stage stage) {
        try {
            String name = nameField.getText().trim();
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());

            if (name.isEmpty()) {
                showAlert("Enter your name");
                return;
            }

            // Direct BMI Calculation
            double bmi = BMICalculator.calculateBMI(weight, height);
            String goal = BMICalculator.getGoal(bmi);

            // Create fresh user object
            User user = new User(name);

            user.bmi = bmi;
            user.goal = goal;

            // Optional DB save for XP/level only
            DBManager.saveUser(user);

            showAlert(
                    "BMI: " + String.format("%.2f", bmi) +
                            "\nGoal: " + goal
            );

            stage.close();

            new DashboardFX(user).show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Invalid Input!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}