import javax.swing.*;
import java.awt.*;

public class LoginUI {

    JFrame frame;
    JTextField nameField;
    JButton loginBtn;

    public LoginUI() {
        frame = new JFrame("GymQuest");
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(20, 20, 20));

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(320, 260));
        card.setBackground(new Color(35, 35, 35));
        card.setLayout(new GridLayout(4, 1, 15, 15));

        JLabel title = new JLabel("GymQuest", JLabel.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        nameField = new JTextField();
        nameField.setBorder(BorderFactory.createTitledBorder("Enter Name"));

        loginBtn = new JButton("Start");

        loginBtn.addActionListener(e -> login());

        card.add(title);
        card.add(nameField);
        card.add(new JLabel());
        card.add(loginBtn);

        panel.add(card);
        frame.add(panel);

        frame.setVisible(true);
    }

    private void login() {
        try {
            String name = nameField.getText().trim();

            double weight = Double.parseDouble(JOptionPane.showInputDialog("Enter weight (kg):"));
            double height = Double.parseDouble(JOptionPane.showInputDialog("Enter height (m):"));

            double bmi = BMICalculator.calculateBMI(weight, height);
            String goal = BMICalculator.getGoal(bmi);

            User user = DBManager.loadUser(name);
            user.bmi = bmi;
            user.goal = goal;

            DBManager.saveUser(user);

            JOptionPane.showMessageDialog(frame,
                    "BMI: " + String.format("%.2f", bmi) + "\nGoal: " + goal);

            frame.dispose();
            new ModernUI(user);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }
}