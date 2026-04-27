package ui;

import backend.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class WorkoutSessionFX {

    private final User user;
    private final Stage stage;

    private Label pushupLabel;
    private Label comboLabel;
    private Label xpLabel;
    private Label statusLabel;

    public WorkoutSessionFX(User user) {
        this.user = user;
        this.stage = new Stage();
    }

    public void show() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0F172A;");

        // ================= TOP =================

        VBox topSection = new VBox(8);
        topSection.setPadding(new Insets(25));

        Label title = new Label("🔥 AI Workout Mode");
        title.setTextFill(Color.WHITE);
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        Label subtitle = new Label(
                "Camera-based Pushup Detection + Combo XP System"
        );
        subtitle.setTextFill(Color.LIGHTGRAY);
        subtitle.setFont(Font.font("Segoe UI", 14));

        topSection.getChildren().addAll(title, subtitle);

        // ================= CENTER CAMERA PANEL =================

        VBox cameraPanel = new VBox(15);
        cameraPanel.setAlignment(Pos.CENTER);
        cameraPanel.setPadding(new Insets(30));

        cameraPanel.setStyle(
                "-fx-background-color: #1E293B;" +
                        "-fx-background-radius: 18;"
        );

        Label camTitle = new Label("📷 LIVE CAMERA TRACKING");
        camTitle.setTextFill(Color.WHITE);
        camTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));

        statusLabel = new Label("Ready to start workout...");
        statusLabel.setTextFill(Color.LIGHTGRAY);
        statusLabel.setFont(Font.font("Segoe UI", 14));

        pushupLabel = new Label("Pushups: 0");
        pushupLabel.setTextFill(Color.WHITE);
        pushupLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));

        comboLabel = new Label("Combo: x0");
        comboLabel.setTextFill(Color.web("#38BDF8"));
        comboLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));

        xpLabel = new Label("XP Reward: +0");
        xpLabel.setTextFill(Color.web("#22C55E"));
        xpLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));

        Button startBtn = new Button("▶ Start Session");
        startBtn.setStyle(
                "-fx-background-color: #2563EB;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;"
        );

        Button finishBtn = new Button("🏁 Finish Workout");
        finishBtn.setStyle(
                "-fx-background-color: #16A34A;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;"
        );

        startBtn.setOnAction(e -> startWorkoutSession());
        finishBtn.setOnAction(e -> finishWorkout());

        HBox buttons = new HBox(15, startBtn, finishBtn);
        buttons.setAlignment(Pos.CENTER);

        cameraPanel.getChildren().addAll(
                camTitle,
                statusLabel,
                pushupLabel,
                comboLabel,
                xpLabel,
                buttons
        );

        root.setTop(topSection);
        root.setCenter(cameraPanel);

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("GymQuest AI Workout");
        stage.setScene(scene);
        stage.show();
    }

    // ================= START SESSION =================

    private void startWorkoutSession() {
        try {
            statusLabel.setText("AI Detection Running... Camera Started 📷");

            ProcessBuilder pb = new ProcessBuilder(
                    "python",
                    "ai_pushup_counter.py"
            );

            pb.redirectErrorStream(true);
            pb.start();

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Failed to start camera detection.");
        }
    }

    // ================= FINISH SESSION =================

    private void finishWorkout() {
        try {
            File file = new File("pushup_result.txt");

            if (!file.exists()) {
                showAlert("No workout data found.");
                return;
            }

            BufferedReader reader = new BufferedReader(
                    new FileReader(file)
            );

            int pushups = Integer.parseInt(reader.readLine());
            reader.close();

            if (pushups <= 0) {
                showAlert("No pushups detected.");
                return;
            }

            // Combo System
            int combo = calculateCombo(pushups);

            // XP Formula
            int totalXP = (pushups * 10) + (combo * 20);

            // Update UI labels
            pushupLabel.setText("Pushups: " + pushups);
            comboLabel.setText("Combo: x" + combo);
            xpLabel.setText("XP Reward: +" + totalXP);

            // IMPORTANT → Update user object
            user.addXP(totalXP);

            // Update streak
            StreakManager.updateStreak(user);

            // Save updated user to DB
            DBManager.saveUser(user);

            // Save workout history
            Workout workout = new Workout(
                    "Pushups",
                    pushups,
                    1
            );

            DBManager.saveWorkout(user, workout, totalXP);

            statusLabel.setText("Workout Completed 🎉");

            showAlert(
                    "Workout Complete!\n\n" +
                            "Pushups: " + pushups + "\n" +
                            "Combo: x" + combo + "\n" +
                            "XP Earned: +" + totalXP + "\n" +
                            "Current Level: " + user.level + "\n" +
                            "Current XP: " + user.xp
            );
            new HistoryFX(user.name).show();

            // Refresh Dashboard with updated values
            stage.close();
            new DashboardFX(user).show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error reading workout results.");
        }
    }

    // ================= COMBO SYSTEM =================

    private int calculateCombo(int pushups) {
        if (pushups >= 20) return 5;
        if (pushups >= 15) return 4;
        if (pushups >= 10) return 3;
        if (pushups >= 5) return 2;
        return 1;
    }

    // ================= ALERT =================

    private void showAlert(String msg) {
        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.initOwner(stage); // VERY IMPORTANT
        alert.setTitle("GymQuest");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }
}