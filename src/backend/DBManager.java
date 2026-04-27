package backend;
import java.sql.*;


public class DBManager {

    static final String URL = "jdbc:mysql://localhost:3306/gymquest";
    static final String USER = "root";
    static final String PASSWORD = "Gym1234";

    // ================= CONNECTION =================
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ================= LOAD USER =================
    public static User loadUser(String name) {
        User user = null;

        try {
            Connection con = getConnection();

            String sql = "SELECT * FROM users WHERE name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(name);

                user.xp = rs.getInt("xp");
                user.level = rs.getInt("level");
                user.bmi = rs.getDouble("bmi");
                user.goal = rs.getString("goal");
                user.streak = rs.getInt("streak");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    // ================= SAVE USER =================
    public static void saveUser(User user) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "REPLACE INTO users (name, xp, level, bmi, goal, streak, last_date) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            ps.setString(1, user.name);
            ps.setInt(2, user.xp);
            ps.setInt(3, user.level);
            ps.setDouble(4, user.bmi);
            ps.setString(5, user.goal);
            ps.setInt(6, user.streak);

            if (user.lastDate != null)
                ps.setDate(7, Date.valueOf(user.lastDate));
            else
                ps.setDate(7, null);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= SAVE WORKOUT =================
    public static void saveWorkout(User user, Workout workout, int xp) {
        try {
            Connection con = getConnection();

            String sql = "INSERT INTO workout_history (name, exercise, reps, sets, xp) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.name);
            ps.setString(2, workout.exercise);
            ps.setInt(3, workout.reps);
            ps.setInt(4, workout.sets);
            ps.setInt(5, xp);

            ps.executeUpdate();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= HISTORY =================
    public static ResultSet getWorkoutHistory(String userName) {
        try {
            Connection con = getConnection();

            String sql = "SELECT exercise, reps, sets, xp FROM workout_history WHERE name = ? ORDER BY id DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, userName);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ================= LEADERBOARD =================
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

    // ================= USER RANK (FIXED) =================
    public static int getUserRank(User user) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT COUNT(*) + 1 AS user_rank FROM users WHERE level > ?"
            );

            ps.setInt(1, user.level);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("user_rank");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}