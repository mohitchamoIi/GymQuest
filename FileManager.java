import java.io.*;

public class FileManager {

    public static void saveUser(User user) {
        try {
            FileWriter fw = new FileWriter("data.txt");
            fw.write(user.name + "," + user.xp + "," + user.level);
            fw.close();
        } catch (Exception e) {
            System.out.println("Error saving data");
        }
    }

    public static User loadUser() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data.txt"));
            String line = br.readLine();
            br.close();

            if (line != null) {
                String[] parts = line.split(",");
                User user = new User(parts[0]);
                user.xp = Integer.parseInt(parts[1]);
                user.level = Integer.parseInt(parts[2]);
                return user;
            }
        } catch (Exception e) {
            System.out.println("No previous data found");
        }

        return new User("Player");
    }
}