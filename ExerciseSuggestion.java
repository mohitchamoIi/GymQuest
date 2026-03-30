public class ExerciseSuggestion {

    public static String getSuggestion(String goal) {
        if (goal.equals("Bulk")) {
            return "Focus on Strength Training (Pushups, Squats)";
        } else if (goal.equals("Cut")) {
            return "Focus on Cardio (Running, HIIT)";
        } else {
            return "Balanced Workout + Light Cardio";
        }
    }

    public static int checkChallenge(String exercise, int reps) {
        if (exercise.equals("Pushups") && reps >= 50) {
            return 100;
        }
        if (exercise.equals("Squats") && reps >= 50) {
            return 100;
        }
        return 0;
    }
}