public class BadgeSystem {

    public static String getBadge(int level) {
        if (level >= 10) return "Pro Athlete 🏆";
        if (level >= 5) return "Intermediate 💪";
        return "Beginner 🔰";
    }
}