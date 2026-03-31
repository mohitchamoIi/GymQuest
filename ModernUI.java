import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModernUI {

    JFrame frame;
    JPanel sidebar, topBar, mainPanel;
    CardLayout cardLayout;

    User user;

    JButton dashboardBtn, workoutBtn, historyBtn, leaderboardBtn;

    public ModernUI(User user) {
        this.user = user;

        frame = new JFrame("GymQuest Pro");
        frame.setSize(950, 600);
        frame.setLayout(new BorderLayout());

        // SIDEBAR
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(180, 600));
        sidebar.setBackground(new Color(25, 25, 35));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        dashboardBtn = createNav("🏠 Dashboard");
        workoutBtn = createNav("🏋 Workout");
        historyBtn = createNav("📊 History");
        leaderboardBtn = createNav("🏆 Leaderboard");

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(dashboardBtn);
        sidebar.add(workoutBtn);
        sidebar.add(historyBtn);
        sidebar.add(leaderboardBtn);

        // TOP BAR
        topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);

        JLabel title = new JLabel("Welcome, " + user.name);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        topBar.add(title, BorderLayout.WEST);

        // MAIN
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createDashboard(), "dashboard");
        mainPanel.add(createWorkoutPanel(), "workout");

        // ACTIONS
        dashboardBtn.addActionListener(e -> switchTab("dashboard", dashboardBtn));
        workoutBtn.addActionListener(e -> switchTab("workout", workoutBtn));
        historyBtn.addActionListener(e -> new HistoryUI(user.name));
        leaderboardBtn.addActionListener(e -> new LeaderboardUI(user.name));

        setActive(dashboardBtn);

        frame.add(sidebar, BorderLayout.WEST);
        frame.add(topBar, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // NAV BUTTON
    private JButton createNav(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(160, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.setBackground(new Color(25, 25, 35));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

        return btn;
    }

    private void setActive(JButton active) {
        JButton[] buttons = {dashboardBtn, workoutBtn, historyBtn, leaderboardBtn};
        for (JButton b : buttons) b.setBackground(new Color(25, 25, 35));
        active.setBackground(new Color(0, 120, 215));
    }

    private void switchTab(String name, JButton btn) {
        cardLayout.show(mainPanel, name);
        setActive(btn);
    }

    // DASHBOARD
    private JPanel createDashboard() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createCard("👤 Profile",
                "BMI: " + String.format("%.2f", user.bmi),
                "Goal: " + user.goal,
                "Badge: " + BadgeSystem.getBadge(user.level)));

        panel.add(createProgressCard());

        panel.add(createCard("🎯 Suggestion",
                ExerciseSuggestion.getSuggestion(user.goal),
                "",
                "🔥 Challenge: 50 Pushups"));

        panel.add(createCard("📈 Rank",
                "Your Rank: #" + DBManager.getUserRank(user),
                "",
                ""));

        panel.add(createCard("🔥 Streak",
                "Days: " + user.streak,
                "",
                ""));

        JButton reportBtn = new JButton("Export Report");
        reportBtn.addActionListener(e -> {
            ReportGenerator.generate(user);
            JOptionPane.showMessageDialog(frame, "Report Saved!");
        });

        panel.add(reportBtn);

        return panel;
    }

    private JPanel createCard(String title, String l1, String l2, String l3) {
        JPanel card = new JPanel(new GridLayout(4, 1));
        card.setBorder(BorderFactory.createTitledBorder(title));

        card.add(new JLabel(l1));
        card.add(new JLabel(l2));
        card.add(new JLabel(l3));

        return card;
    }

    private JPanel createProgressCard() {
        JPanel card = new JPanel(new GridLayout(4, 1));
        card.setBorder(BorderFactory.createTitledBorder("📊 Progress"));

        JProgressBar bar = new JProgressBar(0, user.level * 100);
        bar.setValue(user.xp);
        bar.setStringPainted(true);

        card.add(bar);
        card.add(new JLabel("Level " + user.level));
        card.add(new JLabel("XP " + user.xp));

        return card;
    }

    private JPanel createWorkoutPanel() {
        JPanel panel = new JPanel();

        JButton start = new JButton("Start Workout");

        start.addActionListener(e -> handleWorkout());

        panel.add(start);
        return panel;
    }

    // 🔥 STEP 5 INCLUDED HERE
    private void handleWorkout() {
        try {
            String[] ex = {"Pushups", "Squats", "Pullups"};

            String e = (String) JOptionPane.showInputDialog(
                    frame, "Exercise", "Workout",
                    JOptionPane.QUESTION_MESSAGE, null, ex, ex[0]);

            int r = Integer.parseInt(JOptionPane.showInputDialog("Reps"));
            int s = Integer.parseInt(JOptionPane.showInputDialog("Sets"));

            // VALIDATION
            if (r <= 0 || s <= 0) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
                return;
            }

            Workout w = new Workout(e, r, s);
            int xp = XPSystem.calculateXP(w);

            int bonus = ExerciseSuggestion.checkChallenge(e, r);
            xp += bonus;

            user.addXP(xp);

            // 🔥 STREAK UPDATE
            StreakManager.updateStreak(user);

            DBManager.saveWorkout(user, w, xp);
            DBManager.saveUser(user);

            // 🔥 XP POPUP
            JOptionPane.showMessageDialog(frame, "+" + xp + " XP 🎉");

            if (bonus > 0) {
                JOptionPane.showMessageDialog(frame, "🔥 Challenge Completed!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input");
        }
    }
}