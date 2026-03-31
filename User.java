import java.time.LocalDate;

public class User {
    String name;
    int xp;
    int level;

    double bmi;
    String goal;

    int streak;
    LocalDate lastDate;

    public User(String name) {
        this.name = name;
        this.xp = 0;
        this.level = 1;
        this.bmi = 0;
        this.goal = "";
        this.streak = 0;
        this.lastDate = null;
    }

    public void addXP(int gainedXP) {
        this.xp += gainedXP;

        int requiredXP = level * 100;

        while (this.xp >= requiredXP) {
            this.xp -= requiredXP;
            this.level++;
            requiredXP = level * 100;
        }
    }
}