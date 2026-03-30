public class BMICalculator {

    public static double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }

    public static String getGoal(double bmi) {
        if (bmi < 18.5) return "Bulk";
        else if (bmi < 24.9) return "Maintain";
        else return "Cut";
    }
}