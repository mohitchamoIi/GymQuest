package backend;

import java.time.LocalDate;

public class User {
    public String name;
    public int xp;
    public int level;

    public double bmi;
    public String goal;

    public int streak;
    public LocalDate lastDate;

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

        while (this.xp >= level * 100) {
            this.xp -= level * 100;
            this.level++;
        }
    }
}