import java.io.FileWriter;

public class ReportGenerator {

    public static void generate(User user) {
        try {
            FileWriter fw = new FileWriter(user.name + "_report.txt");

            fw.write("GymQuest Report\n");
            fw.write("Name: " + user.name + "\n");
            fw.write("Level: " + user.level + "\n");
            fw.write("XP: " + user.xp + "\n");
            fw.write("BMI: " + user.bmi + "\n");
            fw.write("Goal: " + user.goal + "\n");
            fw.write("Streak: " + user.streak + "\n");

            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}