import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GymUI {

    JFrame frame;
    JPanel sidebar, mainPanel;
    JLabel statusLabel;
    JProgressBar progressBar;

    User user;

    public GymUI(User user) {
        this.user = user;

        frame = new JFrame("GymQuest");
        frame.setSize(750, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ================= SIDEBAR =================
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(170, 480));
        sidebar.setBackground(new Color(20, 20, 20));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton dashboardBtn = createSidebarButton("🏠 Dashboard");
        JButton workoutBtn = createSidebarButton("🏋 Workout");
        JButton historyBtn = createSidebarButton("📊 History");
        JButton leaderboardBtn = createSidebarButton("🏆 Leaderboard");

        sidebar.add(dashboardBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(workoutBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(historyBtn);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(leaderboardBtn);

        // ================= MAIN PANEL =================
        mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(380, 240));
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // STATUS
        statusLabel = new JLabel(getStatus(), JLabel.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // PROGRESS BAR
        progressBar = new JProgressBar(0, user.level * 100);
        progressBar.setValue(user.xp);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(0, 120, 215));
        progressBar.setPreferredSize(new Dimension(320, 25));
        progressBar.setMaximumSize(new Dimension(320, 25));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // BUTTON
        JButton addWorkoutBtn = new RoundedButton("Start Workout");
        addWorkoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addWorkoutBtn.setBackground(new Color(0, 120, 215));
        addWorkoutBtn.setForeground(Color.WHITE);
        addWorkoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // SPACING
        card.add(statusLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(progressBar);
        card.add(Box.createVerticalStrut(25));
        card.add(addWorkoutBtn);

        mainPanel.add(card);

        // ================= ADD =================
        frame.add(sidebar, BorderLayout.WEST);
        frame.add(mainPanel, BorderLayout.CENTER);

        // ================= ACTIONS =================
        addWorkoutBtn.addActionListener(e -> handleWorkout());
        historyBtn.addActionListener(e -> new HistoryUI(user.name));
        leaderboardBtn.addActionListener(e -> new LeaderboardUI());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ================= SIDEBAR BUTTON =================
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(150, 45));

        btn.setFocusPainted(false);
        btn.setBackground(new Color(35, 35, 35));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // HOVER EFFECT
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(60, 60, 60));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(35, 35, 35));
            }
        });

        return btn;
    }

    // ================= GRADIENT BACKGROUND =================
    class GradientPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(240, 242, 245),
                    0, getHeight(), new Color(220, 225, 230)
            );

            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // ================= ROUNDED BUTTON =================
    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            super.paintComponent(g);
        }
    }

    // ================= LOGIC =================
    private void handleWorkout() {
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

            DBManager.saveWorkout(user, w, xp);
            DBManager.saveUser(user);

            statusLabel.setText(getStatus());
            progressBar.setMaximum(user.level * 100);
            progressBar.setValue(user.xp);

            String achievement = Achievement.check(user);
            if (!achievement.equals("")) {
                JOptionPane.showMessageDialog(frame, achievement);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private String getStatus() {
        int requiredXP = user.level * 100;
        return "Level " + user.level + " | XP " + user.xp + "/" + requiredXP;
    }
}