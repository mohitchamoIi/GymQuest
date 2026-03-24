import javax.swing.*;
import java.awt.event.*;

public class GymUI {
    JFrame frame;
    JLabel label;
    JButton button;

    User user;

    public GymUI(User user) {
        this.user = user;

        frame = new JFrame("GymQuest");
        frame.setSize(300, 200);
        frame.setLayout(null);

        label = new JLabel(getStatus());
        label.setBounds(50, 30, 200, 30);

        button = new JButton("Add Workout");
        button.setBounds(70, 80, 150, 30);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String exercise = JOptionPane.showInputDialog("Exercise:");
                int reps = Integer.parseInt(JOptionPane.showInputDialog("Reps:"));
                int sets = Integer.parseInt(JOptionPane.showInputDialog("Sets:"));

                Workout w = new Workout(exercise, reps, sets);
                int xp = XPSystem.calculateXP(w);

                user.addXP(xp);

                label.setText(getStatus());

                FileManager.saveUser(user);
            }
        });

        frame.add(label);
        frame.add(button);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private String getStatus() {
        return "XP: " + user.xp + " | Level: " + user.level;
    }
}