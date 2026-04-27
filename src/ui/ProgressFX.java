package ui;

import backend.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ProgressFX {

    private final User user;
    private final Stage stage;

    public ProgressFX(User user) {
        this.user = user;
        this.stage = new Stage();
    }

    public void show() {

        VBox root = new VBox(20);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #F5F7FA;");

        Label title = new Label("📈 Progress Analytics");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));

        // ================= AXIS =================

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Workout Days");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("XP");

        LineChart<String, Number> lineChart =
                new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("XP Growth");

        // ================= DATA =================

        XYChart.Series<String, Number> series =
                new XYChart.Series<>();

        series.setName("XP Progress");

        // Dummy progressive data for demo
        // Later can be replaced with DB data

        series.getData().add(new XYChart.Data<>("Day 1", 20));
        series.getData().add(new XYChart.Data<>("Day 2", 45));
        series.getData().add(new XYChart.Data<>("Day 3", 70));
        series.getData().add(new XYChart.Data<>("Day 4", 95));
        series.getData().add(new XYChart.Data<>("Day 5", user.xp));

        lineChart.getData().add(series);

        root.getChildren().addAll(
                title,
                lineChart
        );

        Scene scene = new Scene(root, 800, 550);

        stage.setTitle("Progress");
        stage.setScene(scene);
        stage.show();
    }
}