public class Achievement {

    public static String check(User user) {
        if (user.level == 2) return "🏆 Beginner Unlocked!";
        if (user.level == 5) return "🔥 Intermediate Unlocked!";
        if (user.level == 10) return "💪 Pro Athlete!";
        return "";
    }
}