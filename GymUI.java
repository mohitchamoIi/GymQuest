import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GymUI {

    JFrame frame;
    JLabel statusLabel;
    JProgressBar progressBar;
    JButton startBtn, workoutBtn;

    User user;

    public GymUI(User user) {
        this.user = user;

        frame = new JFrame("GymQuest");
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // SIDEBAR (MINIMAL)
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(120, 400));
        sidebar.setBackground(new Color(30, 30, 30));
        sidebar.setLayout(new GridLayout(3, 1));

        JLabel logo = new JLabel("Gym", JLabel.CENTER);
        logo.setForeground(Color.WHITE);

        workoutBtn = new JButton("Workout");
        styleBtn(workoutBtn);

        sidebar.add(logo);
        sidebar.add(workoutBtn);

        // MAIN
        JPanel main = new JPanel();
        main.setBackground(new Color(245, 245, 245));
        main.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(300, 180));
        card.setBackground(Color.WHITE);
        card.setLayout(new GridLayout(3, 1, 10, 10));
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        statusLabel = new JLabel(getStatus(), JLabel.CENTER);

        progressBar = new JProgressBar(0, user.level * 100);
        progressBar.setValue(user.xp);
        progressBar.setStringPainted(true);

        startBtn = new JButton("Start Workout");
        startBtn.setBackground(new Color(0, 120, 215));
        startBtn.setForeground(Color.WHITE);

        card.add(statusLabel);
        card.add(progressBar);
        card.add(startBtn);

        main.add(card);

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(main, BorderLayout.CENTER);

        // ACTIONS (FIXED)
        startBtn.addActionListener(e -> handleWorkout());
        workoutBtn.addActionListener(e -> handleWorkout());

        frame.setVisible(true);
    }

    private void styleBtn(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(50, 50, 50));
        btn.setForeground(Color.WHITE);
    }

    private void handleWorkout() {
        try {
            String[] exercises = {"Pushups", "Squats", "Pullups"};

            String exercise = (String) JOptionPane.showInputDialog(
                    frame,
                    "Exercise",
                    "Workout",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    exercises,
                    exercises[0]
            );

            if (exercise == null) return;

            int reps = Integer.parseInt(JOptionPane.showInputDialog("Reps:"));
            int sets = Integer.parseInt(JOptionPane.showInputDialog("Sets:"));

            Workout w = new Workout(exercise, reps, sets);
            int xp = XPSystem.calculateXP(w);

            user.addXP(xp);

            updateUIData();

            FileManager.saveUser(user);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input");
        }
    }

    private void updateUIData() {
        statusLabel.setText(getStatus());
        progressBar.setMaximum(user.level * 100);
        progressBar.setValue(user.xp);
    }

    private String getStatus() {
        return "Level " + user.level + " | XP " + user.xp + "/" + (user.level * 100);
    }
}