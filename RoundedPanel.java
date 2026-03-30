import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }
}