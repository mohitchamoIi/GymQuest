import javax.swing.*;

public class ProfileUI {

    public ProfileUI(User user) {

        JFrame f = new JFrame("Profile");
        f.setSize(300, 300);

        JTextArea area = new JTextArea();

        area.setText(
                "Name: " + user.name +
                        "\nLevel: " + user.level +
                        "\nXP: " + user.xp +
                        "\nBMI: " + user.bmi +
                        "\nGoal: " + user.goal +
                        "\nStreak: " + user.streak
        );

        f.add(area);
        f.setVisible(true);
    }
}