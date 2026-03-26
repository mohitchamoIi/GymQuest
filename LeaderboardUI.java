import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class LeaderboardUI {

    JFrame frame;
    JTable table;

    public LeaderboardUI() {
        frame = new JFrame("Leaderboard");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        String[] columns = {"Name", "Level", "XP"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            ResultSet rs = DBManager.getLeaderboard();

            while (rs.next()) {
                String name = rs.getString("name");
                int level = rs.getInt("level");
                int xp = rs.getInt("xp");

                model.addRow(new Object[]{name, level, xp});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane);
        frame.setVisible(true);
    }
}