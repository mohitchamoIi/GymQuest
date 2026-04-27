package backend;

public class ExerciseSuggestion {

    public static String getSuggestion(String goal) {
        if (goal.equals("Bulk")) {
            return "Focus on Strength Training and Compound Lifts";
        } else if (goal.equals("Cut")) {
            return "Focus on Cardio, HIIT and Calorie Deficit";
        }
        return "Balanced Workout + Light Cardio";
    }
}