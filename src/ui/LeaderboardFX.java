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

public class LeaderboardFX {

    private Stage stage;

    public LeaderboardFX() {
        stage = new Stage();
    }

    public void show() {

        VBox root = new VBox(20);
        root.setPadding(new Insets(25));
        root.setStyle("-fx-background-color: #F5F7FA;");

        Label title = new Label("🏆 Leaderboard");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));

        TableView<PlayerData> table = new TableView<>();

        // ================= COLUMNS =================

        TableColumn<PlayerData, String> nameCol =
                new TableColumn<>("Name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );
        nameCol.setPrefWidth(200);

        TableColumn<PlayerData, Integer> levelCol =
                new TableColumn<>("Level");
        levelCol.setCellValueFactory(
                new PropertyValueFactory<>("level")
        );
        levelCol.setPrefWidth(150);

        TableColumn<PlayerData, Integer> xpCol =
                new TableColumn<>("XP");
        xpCol.setCellValueFactory(
                new PropertyValueFactory<>("xp")
        );
        xpCol.setPrefWidth(150);

        table.getColumns().addAll(
                nameCol,
                levelCol,
                xpCol
        );

        // ================= LOAD DATA =================

        try {
            ResultSet rs = DBManager.getLeaderboard();

            while (rs.next()) {
                table.getItems().add(
                        new PlayerData(
                                rs.getString("name"),
                                rs.getInt("level"),
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

        Scene scene = new Scene(root, 600, 500);

        stage.setTitle("Leaderboard");
        stage.setScene(scene);
        stage.show();
    }

    // ================= MODEL CLASS =================

    public static class PlayerData {

        private String name;
        private int level;
        private int xp;

        public PlayerData(String name, int level, int xp) {
            this.name = name;
            this.level = level;
            this.xp = xp;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }

        public int getXp() {
            return xp;
        }
    }
}