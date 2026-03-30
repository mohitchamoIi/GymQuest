import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class LeaderboardUI {

    public LeaderboardUI(String currentUser) {

        JFrame frame = new JFrame("Leaderboard");
        frame.setSize(400, 400);

        String[] cols = {"Rank", "Name", "Level", "XP"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        try {
            ResultSet rs = DBManager.getLeaderboard();

            int rank = 1;
            int rowIndex = 0;

            while (rs.next()) {
                String name = rs.getString("name");

                model.addRow(new Object[]{
                        rank++,
                        name,
                        rs.getInt("level"),
                        rs.getInt("xp")
                });

                if (name.equals(currentUser)) {
                    rowIndex = rank - 2;
                }
            }

            JTable table = new JTable(model);
            table.setRowSelectionInterval(rowIndex, rowIndex);

            frame.add(new JScrollPane(table));

        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }
}