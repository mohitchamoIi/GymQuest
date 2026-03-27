import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class HistoryUI {

    JFrame frame;

    public HistoryUI(String username) {
        frame = new JFrame("Workout History");
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        String[] columns = {"Exercise", "Reps", "Sets", "XP"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            ResultSet rs = DBManager.getWorkoutHistory(username);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("exercise"),
                        rs.getInt("reps"),
                        rs.getInt("sets"),
                        rs.getInt("xp")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        frame.add(scroll);
        frame.setVisible(true);
    }
}