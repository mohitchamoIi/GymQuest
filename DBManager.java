import java.sql.*;

public class DBManager {

    static final String URL = "jdbc:mysql://localhost:3306/gymquest";
    static final String USER = "root";
    static final String PASSWORD = "Gym1234"; // confidential

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static User loadUser(String name) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE name=?"
            );
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"));
                user.xp = rs.getInt("xp");
                user.level = rs.getInt("level");
                return user;
            }

            // New user
            User user = new User(name);
            saveUser(user);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new User(name);
    }

    public static void saveUser(User user) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "REPLACE INTO users (name, xp, level) VALUES (?, ?, ?)"
            );

            ps.setString(1, user.name);
            ps.setInt(2, user.xp);
            ps.setInt(3, user.level);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ResultSet getLeaderboard() {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT name, level, xp FROM users ORDER BY level DESC, xp DESC LIMIT 10"
            );

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void saveWorkout(User user, Workout w, int xp) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO workouts (name, exercise, reps, sets, xp) VALUES (?, ?, ?, ?, ?)"
            );

            ps.setString(1, user.name);
            ps.setString(2, w.exercise);
            ps.setInt(3, w.reps);
            ps.setInt(4, w.sets);
            ps.setInt(5, xp);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getWorkoutHistory(String name) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT exercise, reps, sets, xp FROM workouts WHERE name=?"
            );

            ps.setString(1, name);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}