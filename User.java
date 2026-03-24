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

        if (this.xp >= 100) {
            this.level++;
            this.xp = this.xp - 100;
        }
    }
}