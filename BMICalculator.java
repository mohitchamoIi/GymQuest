public class BMICalculator {

    public static double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }

    public static String getCategory(double bmi) {
        if (bmi < 18.5) return "Underweight → Bulk";
        else if (bmi < 24.9) return "Normal → Maintain";
        else return "Overweight → Cut";
    }
}