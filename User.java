public class User {
    String name;
    int xp;
    int level;

    public User(String name) {
        this.name = name;
        this.xp = 0;
        this.level = 1;
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