import java.time.LocalDate;

public class StreakManager {

    public static void updateStreak(User user) {

        LocalDate today = LocalDate.now();

        if (user.lastDate == null) {
            user.streak = 1;
        } else if (user.lastDate.plusDays(1).equals(today)) {
            user.streak++;
        } else if (!user.lastDate.equals(today)) {
            user.streak = 1;
        }

        user.lastDate = today;
    }
}