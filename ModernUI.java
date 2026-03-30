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

        // ================= SIDEBAR =================
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

        // ================= TOP BAR =================
        topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);
        topBar.setPreferredSize(new Dimension(950, 60));

        JLabel title = new JLabel(getGreeting());
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        topBar.add(title, BorderLayout.WEST);

        // ================= MAIN =================
        cardLayout = new CardLayout();
        mainPanel = new GradientPanel();
        mainPanel.setLayout(cardLayout);

        mainPanel.add(createDashboard(), "dashboard");
        mainPanel.add(createWorkoutPanel(), "workout");

        // ================= ACTIONS =================
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

    // ================= GREETING =================
    private String getGreeting() {
        int hour = java.time.LocalTime.now().getHour();
        if (hour < 12) return "Good Morning, " + user.name + " 👋";
        else if (hour < 18) return "Good Afternoon, " + user.name + " 👋";
        else return "Good Evening, " + user.name + " 👋";
    }

    // ================= NAV =================
    private JButton createNav(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(160, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.setBackground(new Color(25, 25, 35));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!btn.getBackground().equals(new Color(0, 120, 215)))
                    btn.setBackground(new Color(50, 50, 70));
            }

            public void mouseExited(MouseEvent e) {
                if (!btn.getBackground().equals(new Color(0, 120, 215)))
                    btn.setBackground(new Color(25, 25, 35));
            }
        });

        return btn;
    }

    private void setActive(JButton active) {
        JButton[] buttons = {dashboardBtn, workoutBtn, historyBtn, leaderboardBtn};
        for (JButton b : buttons) {
            b.setBackground(new Color(25, 25, 35));
        }
        active.setBackground(new Color(0, 120, 215));
    }

    private void switchTab(String name, JButton btn) {
        cardLayout.show(mainPanel, name);
        setActive(btn);
    }

    // ================= DASHBOARD =================
    private JPanel createDashboard() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

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

        return panel;
    }

    // ================= CARD =================
    private JPanel createCard(String title, String l1, String l2, String l3) {
        RoundedPanel card = new RoundedPanel(20);
        card.setLayout(new GridLayout(4, 1, 5, 5));
        card.setBackground(Color.WHITE);

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel line1 = new JLabel(l1);
        JLabel line2 = new JLabel(l2);
        JLabel line3 = new JLabel(l3);

        // Goal color coding
        if (l1.contains("Goal")) {
            if (user.goal.equals("Bulk")) line1.setForeground(new Color(40, 180, 99));
            if (user.goal.equals("Cut")) line1.setForeground(new Color(220, 53, 69));
        }

        card.add(t);
        card.add(line1);
        card.add(line2);
        card.add(line3);

        return card;
    }

    private JPanel createProgressCard() {
        RoundedPanel card = new RoundedPanel(20);
        card.setLayout(new GridLayout(4, 1));
        card.setBackground(Color.WHITE);

        JProgressBar bar = new JProgressBar(0, user.level * 100);
        bar.setValue(user.xp);
        bar.setStringPainted(true);
        bar.setForeground(new Color(0, 120, 215));

        card.add(new JLabel("📊 Progress"));
        card.add(bar);
        card.add(new JLabel("Level " + user.level));
        card.add(new JLabel("XP " + user.xp));

        return card;
    }

    // ================= WORKOUT =================
    private JPanel createWorkoutPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        JButton start = new JButton("Start Workout");

        start.setBackground(new Color(0, 120, 215));
        start.setForeground(Color.WHITE);

        start.addActionListener(e -> handleWorkout());

        panel.add(start);
        return panel;
    }

    // ================= LOGIC =================
    private void handleWorkout() {
        try {
            String[] ex = {"Pushups", "Squats", "Pullups"};

            String e = (String) JOptionPane.showInputDialog(
                    frame, "Exercise", "Workout",
                    JOptionPane.QUESTION_MESSAGE, null, ex, ex[0]);

            int r = Integer.parseInt(JOptionPane.showInputDialog("Reps"));
            int s = Integer.parseInt(JOptionPane.showInputDialog("Sets"));

            Workout w = new Workout(e, r, s);
            int xp = XPSystem.calculateXP(w);

            int bonus = ExerciseSuggestion.checkChallenge(e, r);
            xp += bonus;

            user.addXP(xp);

            DBManager.saveWorkout(user, w, xp);
            DBManager.saveUser(user);

            if (bonus > 0) {
                JOptionPane.showMessageDialog(frame, "🔥 Challenge Completed!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input");
        }
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

    // ================= ROUNDED PANEL =================
    class RoundedPanel extends JPanel {
        int radius;

        RoundedPanel(int r) {
            radius = r;
            setOpaque(false);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        }
    }
}