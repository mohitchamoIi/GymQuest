package backend;

public class BMICalculator {

    public static double calculateBMI(double weight, double height) {
        if (height > 3) {
            height = height / 100.0;
        }

        if (height <= 0) {
            return 0;
        }

        return weight / (height * height);
    }

    public static String getGoal(double bmi) {
        if (bmi < 18.5) return "Bulk";
        else if (bmi < 24.9) return "Maintain";
        return "Cut";
    }
}