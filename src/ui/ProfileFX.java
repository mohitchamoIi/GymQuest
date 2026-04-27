package ui;

import backend.BadgeSystem;
import backend.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ProfileFX {

    private final User user;
    private final Stage stage;

    public ProfileFX(User user) {
        this.user = user;
        this.stage = new Stage();
    }

    public void show() {

        VBox root = new VBox(18);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F5F7FA;");

        Label title = new Label("👤 My Profile");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-color: #E0E0E0;"
        );

        Label name = createInfoLabel(
                "Name: " + user.name
        );

        Label bmi = createInfoLabel(
                "BMI: " + String.format("%.2f", user.bmi)
        );

        Label goal = createInfoLabel(
                "Goal: " + user.goal
        );

        Label level = createInfoLabel(
                "Level: " + user.level
        );

        Label xp = createInfoLabel(
                "XP: " + user.xp
        );

        Label streak = createInfoLabel(
                "🔥 Streak: " + user.streak + " days"
        );

        Label badge = createInfoLabel(
                "🏅 Badge: " + BadgeSystem.getBadge(user.level)
        );
        badge.setTextFill(Color.web("#0078D7"));

        card.getChildren().addAll(
                name,
                bmi,
                goal,
                level,
                xp,
                streak,
                badge
        );

        root.getChildren().addAll(
                title,
                card
        );

        Scene scene = new Scene(root, 500, 500);

        stage.setTitle("Profile");
        stage.setScene(scene);
        stage.show();
    }

    private Label createInfoLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", 16));
        return label;
    }
}