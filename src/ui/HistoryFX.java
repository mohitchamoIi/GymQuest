package ui;

import backend.DBManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class HistoryFX {

    private Stage stage;
    private String userName;

    public HistoryFX(String userName) {
        this.userName = userName;
        this.stage = new Stage();
    }

    public void show() {

        VBox root = new VBox(20);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #F5F7FA;");

        Label title = new Label("📊 Workout History");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));

        TableView<WorkoutData> table = new TableView<>();

        // ================= COLUMNS =================

        TableColumn<WorkoutData, String> exerciseCol =
                new TableColumn<>("Exercise");
        exerciseCol.setCellValueFactory(
                new PropertyValueFactory<>("exercise")
        );
        exerciseCol.setPrefWidth(180);

        TableColumn<WorkoutData, Integer> repsCol =
                new TableColumn<>("Reps");
        repsCol.setCellValueFactory(
                new PropertyValueFactory<>("reps")
        );
        repsCol.setPrefWidth(120);

        TableColumn<WorkoutData, Integer> setsCol =
                new TableColumn<>("Sets");
        setsCol.setCellValueFactory(
                new PropertyValueFactory<>("sets")
        );
        setsCol.setPrefWidth(120);

        TableColumn<WorkoutData, Integer> xpCol =
                new TableColumn<>("XP");
        xpCol.setCellValueFactory(
                new PropertyValueFactory<>("xp")
        );
        xpCol.setPrefWidth(120);

        table.getColumns().addAll(
                exerciseCol,
                repsCol,
                setsCol,
                xpCol
        );

        // ================= LOAD DATA =================

        try {
            ResultSet rs = DBManager.getWorkoutHistory(userName);

            while (rs.next()) {
                table.getItems().add(
                        new WorkoutData(
                                rs.getString("exercise"),
                                rs.getInt("reps"),
                                rs.getInt("sets"),
                                rs.getInt("xp")
                        )
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        root.getChildren().addAll(
                title,
                table
        );

        Scene scene = new Scene(root, 700, 500);

        stage.setTitle("Workout History");
        stage.setScene(scene);
        stage.show();
    }

    // ================= MODEL CLASS =================

    public static class WorkoutData {

        private String exercise;
        private int reps;
        private int sets;
        private int xp;

        public WorkoutData(
                String exercise,
                int reps,
                int sets,
                int xp
        ) {
            this.exercise = exercise;
            this.reps = reps;
            this.sets = sets;
            this.xp = xp;
        }

        public String getExercise() {
            return exercise;
        }

        public int getReps() {
            return reps;
        }

        public int getSets() {
            return sets;
        }

        public int getXp() {
            return xp;
        }
    }
}