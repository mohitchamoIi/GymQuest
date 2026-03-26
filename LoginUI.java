import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginUI {

    JFrame frame;
    JTextField nameField;
    JButton loginBtn;

    public LoginUI() {
        frame = new JFrame("GymQuest");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // MAIN PANEL
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(20, 20, 20));

        // CARD PANEL
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(320, 260));
        card.setBackground(new Color(35, 35, 35));
        card.setLayout(new GridLayout(4, 1, 15, 15));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // TITLE
        JLabel title = new JLabel("🏋 GymQuest", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        // TEXT FIELD
        nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createTitledBorder("Enter Name"));
        nameField.setToolTipText("Enter your name");

        // BUTTON
        loginBtn = new JButton("Start");
        loginBtn.setBackground(new Color(0, 120, 215));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);

        // HOVER EFFECT
        loginBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginBtn.setBackground(new Color(0, 150, 255));
            }

            public void mouseExited(MouseEvent e) {
                loginBtn.setBackground(new Color(0, 120, 215));
            }
        });

        // ACTION
        loginBtn.addActionListener(e -> login());

        // ENTER KEY SUPPORT
        frame.getRootPane().setDefaultButton(loginBtn);

        // ADD COMPONENTS
        card.add(title);
        card.add(nameField);
        card.add(new JLabel());
        card.add(loginBtn);

        panel.add(card);
        frame.add(panel);

        frame.setVisible(true);
    }

    private void login() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Enter your name");
            return;
        }

        User user = DBManager.loadUser(name);

        frame.dispose(); // close login
        new GymUI(user); // open main app
    }
}