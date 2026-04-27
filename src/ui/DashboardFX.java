package ui;

import backend.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DashboardFX {

    private final User user;
    private final Stage stage;

    public DashboardFX(User user) {
        this.user = user;
        this.stage = new Stage();
    }

    public void show() {

        BorderPane root = new BorderPane();

        // ================= SIDEBAR =================

        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(230);
        sidebar.setStyle("-fx-background-color: #1E1E2F;");

        Label brand = new Label("GYMQUEST");
        brand.setTextFill(Color.WHITE);
        brand.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));

        Label subtitle = new Label("Level Up Your Fitness 💪");
        subtitle.setTextFill(Color.LIGHTGRAY);
        subtitle.setFont(Font.font("Segoe UI", 13));

        Button dashboardBtn = createSideButton("🏠 Dashboard");
        Button workoutBtn = createSideButton("🔥 AI Workout");
        Button leaderboardBtn = createSideButton("🏆 Leaderboard");
        Button historyBtn = createSideButton("📊 History");
        Button profileBtn = createSideButton("👤 Profile");
        Button progressBtn = createSideButton("📈 Progress");

        // ================= BUTTON ACTIONS =================

        workoutBtn.setOnAction(e ->
                new WorkoutSessionFX(user).show()
        );

        leaderboardBtn.setOnAction(e ->
                new LeaderboardFX().show()
        );

        historyBtn.setOnAction(e ->
                new HistoryFX(user.name).show()
        );

        profileBtn.setOnAction(e ->
                new ProfileFX(user).show()
        );

        progressBtn.setOnAction(e ->
                new ProgressFX(user).show()
        );

        dashboardBtn.setOnAction(e ->
                showDashboardAlert()
        );

        sidebar.getChildren().addAll(
                brand,
                subtitle,
                dashboardBtn,
                workoutBtn,
                leaderboardBtn,
                historyBtn,
                profileBtn,
                progressBtn
        );

        // ================= MAIN CONTENT =================

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(25));
        mainContent.setStyle("-fx-background-color: #F5F7FA;");

        Label welcome = new Label(
                "Welcome, " + user.name + " 👋"
        );
        welcome.setFont(
                Font.font("Segoe UI", FontWeight.BOLD, 26)
        );

        Label subText = new Label(
                "Your smart AI-powered fitness journey starts here."
        );
        subText.setFont(Font.font("Segoe UI", 14));
        subText.setTextFill(Color.GRAY);

        VBox header = new VBox(5, welcome, subText);

        // ================= STATS CARDS =================

        HBox cards = new HBox(20);

        VBox bmiCard = createCard(
                "BMI",
                String.format("%.2f", user.bmi),
                "Goal: " + user.goal
        );

        VBox levelCard = createCard(
                "LEVEL",
                String.valueOf(user.level),
                "XP: " + user.xp
        );

        VBox streakCard = createCard(
                "STREAK",
                String.valueOf(user.streak),
                "days 🔥"
        );

        cards.getChildren().addAll(
                bmiCard,
                levelCard,
                streakCard
        );

        // ================= AI WORKOUT CARD =================

        VBox aiCard = new VBox(12);
        aiCard.setPadding(new Insets(20));

        aiCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-color: #E0E0E0;"
        );

        Label aiTitle = new Label(
                "🔥 AI Workout Session"
        );
        aiTitle.setFont(
                Font.font("Segoe UI", FontWeight.BOLD, 18)
        );

        Label aiText = new Label(
                "Use live camera detection for pushup tracking,\n" +
                        "combo rewards and smart XP gain."
        );
        aiText.setWrapText(true);
        aiText.setFont(
                Font.font("Segoe UI", 14)
        );

        Button startAIWorkout = new Button(
                "Start AI Workout"
        );

        startAIWorkout.setStyle(
                "-fx-background-color: #2563EB;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;"
        );

        startAIWorkout.setOnAction(e ->
                new WorkoutSessionFX(user).show()
        );

        aiCard.getChildren().addAll(
                aiTitle,
                aiText,
                startAIWorkout
        );

        mainContent.getChildren().addAll(
                header,
                cards,
                aiCard
        );

        // ================= FINAL =================

        root.setLeft(sidebar);
        root.setCenter(mainContent);

        Scene scene = new Scene(root, 1150, 700);

        stage.setTitle("GymQuest Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    // ================= DASHBOARD ALERT =================

    private void showDashboardAlert() {
        Alert alert = new Alert(
                Alert.AlertType.INFORMATION,
                "You are already on Dashboard 🚀"
        );
        alert.show();
    }

    // ================= SIDEBAR BUTTON =================

    private Button createSideButton(String text) {

        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);

        btn.setStyle(
                "-fx-background-color: #2A2A40;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8;"
        );

        return btn;
    }

    // ================= CARD =================

    private VBox createCard(
            String title,
            String value,
            String subtitle
    ) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setPrefWidth(230);

        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-color: #E0E0E0;"
        );

        Label titleLabel = new Label(title);
        titleLabel.setFont(
                Font.font("Segoe UI", FontWeight.BOLD, 15)
        );

        Label valueLabel = new Label(value);
        valueLabel.setFont(
                Font.font("Segoe UI", FontWeight.BOLD, 28)
        );

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setFont(
                Font.font("Segoe UI", 13)
        );
        subtitleLabel.setTextFill(Color.GRAY);

        card.getChildren().addAll(
                titleLabel,
                valueLabel,
                subtitleLabel
        );

        return card;
    }
}