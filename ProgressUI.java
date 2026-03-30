import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

import javax.swing.*;

public class ProgressUI {

    public ProgressUI() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(20, "XP", "Day 1");
        dataset.addValue(50, "XP", "Day 2");
        dataset.addValue(80, "XP", "Day 3");

        JFreeChart chart = ChartFactory.createLineChart(
                "Progress", "Days", "XP", dataset
        );

        ChartFrame frame = new ChartFrame("Progress Graph", chart);
        frame.setSize(500, 400);
        frame.setVisible(true);
    }
}