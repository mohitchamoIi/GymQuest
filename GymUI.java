import javax.swing.*;
import java.awt.event.*;

public class GymUI {
    JFrame frame;
    JLabel label;
    JButton button;
    JProgressBar progressBar;

    User user;

    public GymUI(User user) {
        this.user = user;

        frame = new JFrame("GymQuest");
        frame.setSize(350, 250);
        frame.setLayout(null);

        label = new JLabel(getStatus());
        label.setBounds(50, 20, 250, 30);

        button = new JButton("Add Workout");
        button.setBounds(90, 70, 150, 30);

        progressBar = new JProgressBar(0, user.level * 100);
        progressBar.setBounds(50, 130, 250, 25);
        progressBar.setValue(user.xp);
        progressBar.setStringPainted(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String[] exercises = {"Pushups", "Squats", "Pullups"};
                    String exercise = (String) JOptionPane.showInputDialog(
                            frame,
                            "Select Exercise",
                            "Workout",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            exercises,
                            exercises[0]
                    );

                    int reps = Integer.parseInt(JOptionPane.showInputDialog("Reps:"));
                    int sets = Integer.parseInt(JOptionPane.showInputDialog("Sets:"));

                    Workout w = new Workout(exercise, reps, sets);
                    int xp = XPSystem.calculateXP(w);

                    user.addXP(xp);

                    label.setText(getStatus());

                    progressBar.setMaximum(user.level * 100);
                    progressBar.setValue(user.xp);

                    String achievement = Achievement.check(user);
                    if (!achievement.equals("")) {
                        JOptionPane.showMessageDialog(frame, achievement);
                    }

                    FileManager.saveUser(user);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input!");
                }
            }
        });

        frame.add(label);
        frame.add(button);
        frame.add(progressBar);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private String getStatus() {
        int requiredXP = user.level * 100;
        return "Level: " + user.level + " | XP: " + user.xp + "/" + requiredXP;
    }
}